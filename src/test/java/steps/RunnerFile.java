package steps;

import io.cucumber.core.cli.Main;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;
import packages.Context;
import screens.ScreensFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm"},
        monochrome = true,
        glue = "steps"
)



public class RunnerFile {
    private static final Logger log = LogManager.getLogger(RunnerFile.class.getName());
    private static int  exit_code;
    static Context context = Context.getInstance();
    static Map<String, Object> result_path ;

    private static String logLineBuilder(){
        return "-".repeat(150);
    }

    public static Map<String, Object> readOptions(CommandLineParser parser, String[] args, String args_type) throws Exception {
        Options options = new Options();
        options.addOption(Option.builder()
                .longOpt("bdd_args")
                .argName("STRING")
                .hasArg()
                .desc("BDD Args dict")
                .build());
        options.addOption(Option.builder()
                .longOpt("results_path")
                .argName("STRING")
                .hasArg()
                .desc("BDD Args dict")
                .build());

        CommandLine cmd = parser.parse(options, args);
        Map<String, Object> optDict = new HashMap<>();

        if (cmd.hasOption("bdd_args") && args_type == "bdd_args") {
            String bddArgsString = cmd.getOptionValue("bdd_args");

            try {
                optDict = convertStringToMap(bddArgsString);

            } catch (ClassCastException e) {
                log.error("Failed to cast bdd_args to Map<String, Object>.");
                throw new Exception("Failed to cast bdd_args to Map<String, Object>.");
            } catch (Exception e) {
                log.error("Failed to evaluate bdd_args. String is not in dict format.");
                throw new Exception("Failed to evaluate bdd_args. String is not in dict format.");
            }
        }
        else if (cmd.hasOption("results_path") && args_type == "results_path") {
            String bddArgsString = cmd.getOptionValue("results_path");
            optDict.put("results_path", bddArgsString);
        }
        else {
            log.error("Missing bdd_args in arguments passed to the test.");
            throw new Exception("Missing bdd_args in arguments passed to the test.");
        }

        return optDict;
    }

    private static Object parseValue(String valueString) {
        if (valueString.equalsIgnoreCase("null")) {
            return null;
        } else if (valueString.equalsIgnoreCase("true") || valueString.equalsIgnoreCase("false")) {
            return Boolean.valueOf(valueString);
        } else {
            return valueString;
        }
    }


    public static Map<String, Object> convertStringToMap(String input) {
        Map<String, Object> map = new HashMap<>();

        // Remove any leading or trailing whitespace
        input = input.trim();

        // Split the input string into key-value pairs
        String[] pairs = input.split(",");

        // Process each pair and add it to the map
        for (String pair : pairs) {
            // Split the pair into key and value
            String[] keyValue = pair.split(":");

            // Ensure the pair is valid and contains both key and value
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String valueString = keyValue[1].trim();

                // Convert the value string to the appropriate object type
                Object value = parseValue(valueString);

                // Add the key-value pair to the map
                map.put(key, value);
            }
        }

        return map;
    }
    public static void copyHistoryAsideIfExists(String resultFolderPath) throws IOException {
        String tempFolder = System.getenv("TMP") + "/automation";

        Path historyFolder = Path.of(resultFolderPath, "report", "history");
        Path lastHistorySaved = Path.of(tempFolder, "last_history");

        // Remove the last history saved if it exists
        if (Files.exists(lastHistorySaved)) {
            Files.delete(lastHistorySaved);
        }

        // Copy the history folder to the last history saved folder if it exists
        if (Files.exists(historyFolder)) {
            Files.copy(historyFolder, lastHistorySaved, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    static void setup(String[] args){
        CommandLineParser parser = new DefaultParser();


        try {
            Map<String, Object> options = readOptions(parser, args, "bdd_args");
            context.setVariables("opdict", options);

            String feature_file_path = (String) options.get("feature_file_path");
            context.setVariables("feature_file_path", feature_file_path);

            Path feature_file = Paths.get(feature_file_path);
            String feature_file_name = feature_file.getFileName().toString().replaceFirst("[.][^.]+$", "");
            context.setVariables("feature_file_name", feature_file_name);

            Map<String, Object> result = readOptions(parser, args, "results_path");
            context.setVariables("results_path", result);
            context.setVariables("failure_screenshot",  result+"/screenshot_after_failure.png");
            context.setVariables("headless",options.getOrDefault("headless",false).equals(true));
            context.setVariables("private_mode",options.getOrDefault("private_mode",false).equals(true));
            context.setVariables("save_send",options.getOrDefault("save_send",false).equals(true));
            context.setVariables("application",options.getOrDefault("application",false).equals(true));
            context.setVariables("env",options.getOrDefault("env","preprod"));
            copyHistoryAsideIfExists(context.getVariables("results_path").toString().replaceAll("^\\{?results_path=", "").replaceAll("[{}]$", ""));

            //todo:add logging functions
            // # Init logger and report
            // #init_logger_reporter(opt_dict)

            //todo:add screen factory to context
            context.setVariables("screens_manager", new ScreensFactory());

            String mail = "test@gmail.com";
            String temp_mail_api = "tempmail@temp.com";
            if (context.getVariables("env").equals("preprod")){
                context.setVariables("mailbox",mail);
                log.info("TEMP Email will be: "+mail);
            }else {
                context.setVariables("mailbox",temp_mail_api);
                log.info("TEMP Email will be: "+temp_mail_api);
            }
            Map<String, Object> userData = new HashMap<>();
            context.setVariables("user_data",userData);
            context.setVariables("rerun_broken_scenario",options.getOrDefault("rerun",true).equals(true));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    static void test(){
        Map<String, Object> optDict = (Map<String, Object>) context.getVariables("opdict");
        log.info(
                "*** Starting Test: "+ optDict.getOrDefault("browser","chrome")+" "+ optDict.get("feature_file_path")+ " ***");
        boolean failed = true;
        String f = Paths.get((String) optDict.get("feature_file_path")).toString().replace("\\", "/");
        try {
            f = Paths.get(f).getParent().toString();

            result_path = (Map<String, Object>) context.getVariables("results_path");
            Path junitResults = Paths.get((String) result_path.get("results_path")).resolve("junit");
            Files.createDirectories(junitResults);
            String tags_arg = "";
            if (optDict.containsKey("tags")) {
                String toJoin = " --tags ";
                String tagsFromArgs = (String) optDict.get("tags");
                String splitBy = "&";
                if (tagsFromArgs.contains("|")) {
                    toJoin = ",";
                    splitBy = "\\|";
                }
                tags_arg += " --tags " + String.join(toJoin, tagsFromArgs.split(splitBy));
            }
            log.info(tags_arg);
            String args = f + " -p pretty --plugin junit:"+junitResults+"/results.xml"+tags_arg;

            log.debug("^^^^^^^ before calling Configuration(args) ^^^^^^^^");
            String[] options = args.split(" ");

            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(options));
            arrayList.add("--tags");
            arrayList.add("not @in_dev");
            options = arrayList.toArray(new String[arrayList.size()]);

            log.debug("^^^^^^^ after calling Configuration(args) ^^^^^^^^");
            log.debug("^^^^^^^^ calling runner.run() ^^^^^^^^");
            int exitStatus = Main.run(options, Thread.currentThread().getContextClassLoader());
            failed = (exitStatus != 0);
        } catch (Exception e) {
            log.debug("*** Exception: " + e.getMessage());
            log.debug("Traceback: ");
            e.printStackTrace();
        }

        exit_code = 0;
        if (failed) {
            exit_code = 4;
        }

    }
    public static void generateAllureReport() throws IOException, InterruptedException {
        String allureExecutable = ".allure/allure-2.20.1/bin/allure.bat"; // Path to the Allure executable (allure.bat or allure.sh)
        String reportFolderPath = Paths.get((String) result_path.get("results_path")) + "/report";

        // Generate Allure report
        String generateCommand = String.format("%s generate %s --clean -o %s", allureExecutable,Paths.get((String) result_path.get("results_path"))+ "/junit" , reportFolderPath);
        log.info("Generate Allure report command: " + generateCommand);
        Process generateProcess = Runtime.getRuntime().exec(generateCommand);
        generateProcess.waitFor();

        // Open Allure report
        String openCommand = String.format("%s open %s", allureExecutable, reportFolderPath);
        log.info("Open Allure report command: " + openCommand);
    }
    private static void teardown() throws IOException, InterruptedException {
        generateAllureReport();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            exit_code = 0;
            setup(args);
            log.info("*** BDD Setup ended ***");
            log.info(logLineBuilder());
            test();
            log.info("*** BDD Test ended ***");
            log.info(logLineBuilder());
        }catch (Exception e){
            log.error("Test() exited with exception "+ e);
            exit_code =4;
        }
        teardown();
        log.info("finished");
        System.exit(exit_code);
    }




}