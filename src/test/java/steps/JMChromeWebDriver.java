package steps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import packages.Context;
import java.time.Duration;


public class JMChromeWebDriver {
    private static WebDriver driver;
    private static final Logger log = LogManager.getLogger(RunnerFile.class.getName());

    static Context context = Context.getInstance();


    public static WebDriver getDriver(){
        if (context.getVariables("driver") == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if((boolean) context.getVariables("headless")){
                options.setHeadless(true);
                options.setExperimentalOption("useAutomationExtension", false);
                options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36");

            }
            if((boolean) context.getVariables("private")){
                options.addArguments("--incognito");
            }
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            context.setVariables("driver", driver);
            options.addArguments("--remote-debugging-port=9222");
            options.addArguments("--no-proxy-server");
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return (WebDriver) context.getVariables("driver");
    }

    public static void quitDriver() {
        if (context.getVariables("driver") != null){
            WebDriver driver = (WebDriver) context.getVariables("driver");
            driver.quit();
            log.info("----- Closing The Browser -----");
        }
    }
}
