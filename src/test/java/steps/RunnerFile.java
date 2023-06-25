package steps;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.apache.commons.cli.*;
import org.junit.runner.RunWith;
import packages.Context;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm"},
        monochrome = true,
        glue = "steps"
)



public class RunnerFile {

    public static Map<String, Object> readOptions(CommandLineParser parser, String[] args) throws Exception {
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

        if (cmd.hasOption("bdd_args")) {
            String bddArgsString = cmd.getOptionValue("bdd_args");

            try {
                optDict = convertStringToMap(bddArgsString);

            } catch (ClassCastException e) {
                throw new Exception("Failed to cast bdd_args to Map<String, Object>.");
            } catch (Exception e) {
                throw new Exception("Failed to evaluate bdd_args. String is not in dict format.");
            }
        } else {
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


    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Context context = Context.getInstance();

        try {
            Map<String, Object> options = readOptions(parser, args);
            context.setVariables("opdict", options);

            String feature_file_path = (String) options.get("feature_file_path");
            context.setVariables("feature_file_path", feature_file_path);

            Path feature_file = Paths.get(feature_file_path);
            String feature_file_name = feature_file.getFileName().toString().replaceFirst("[.][^.]+$", "");
            context.setVariables("feature_file_name", feature_file_name);










        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


}