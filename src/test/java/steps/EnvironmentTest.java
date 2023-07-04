package steps;

import io.cucumber.java.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import packages.Context;

public class EnvironmentTest extends RunnerFile {
    private static final Logger log = LogManager.getLogger(RunnerFile.class.getName());
    static Context context = Context.getInstance();

    @Before
    //todo: make it before all
    public static void beforeAllSteps() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        context.setVariables("driver", driver);
        Thread.sleep(1000);
    }
    @Before
    public void beforeScenario(Scenario scenario) {
        log.info("Before scenario: " + scenario.getName());
        // Additional setup logic for each scenario
    }

    @After
    public void afterScenario(Scenario scenario) {
        log.info("After scenario: " + scenario.getName());
        // Additional cleanup logic for each scenario
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) {
        //String step = ((TestCase) ((TestCaseState) scenario.delegate).testCase).testSteps.get(scenario.getLine()).getStepText();
        log.info("----- Start Step - {step.name} -----");
        // Additional setup logic for each step
    }

    @AfterStep
    public void afterStep() {
        log.info("----- End Step - {step.name} -----");
        // Additional cleanup logic for each step
    }

    @After
    //todo: make it after all
    public static void afterAllSteps() {
        if (context.getVariables("driver") != null){
            WebDriver driver = (WebDriver) context.getVariables("driver");
            driver.quit();
            log.info("----- Closing The Browser -----");
        }

    }

}
