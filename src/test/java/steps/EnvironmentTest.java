package steps;

import io.cucumber.java.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class EnvironmentTest extends RunnerFile {
    private static final Logger log = LogManager.getLogger(RunnerFile.class.getName());


    @BeforeAll
    //todo: make it before all
    public static void beforeAllSteps() throws InterruptedException {
        JMChromeWebDriver.getDriver();
    }
    @Before
    public void beforeScenario(Scenario scenario) throws InterruptedException {
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

    @AfterAll
    //todo: make it after all
    public static void afterAllSteps() {
        JMChromeWebDriver.quitDriver();

    }

}
