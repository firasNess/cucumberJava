package packages;

import packages.infra.CheckPoint;
import packages.infra.JMChromeWebDriver;
import io.cucumber.java.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EnvironmentTest extends RunnerFile {
    private static final Logger log = LogManager.getLogger(RunnerFile.class.getName());



    @BeforeAll
    public static void beforeAllSteps() {
        JMChromeWebDriver.getDriver();
    }

    @AfterAll
    public static void afterAllSteps() {
        CheckPoint.markFinal();
        JMChromeWebDriver.quitDriver();

    }





}
