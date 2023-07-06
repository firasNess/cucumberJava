package steps.widgetsteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import packages.Context;
import packages.uiwidgets.Dropdown;
import packages.uiwidgets.TextField;
import steps.RunnerFile;

import static org.junit.Assert.assertEquals;


public class Steps {
    static Context context = Context.getInstance();
    WebDriver driver = (WebDriver) context.getVariables("driver");
    private static final Logger log = LogManager.getLogger(RunnerFile.class.getName());


    @Given("^I open PlanningInformation Form")
    public void goToGoogle() {
        driver.navigate().to("https://jeronlineforms.jerusalem.muni.il/PlanningInformation");
        try{
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
        catch (Exception e){
            log.info("Alert Not available");
        }

    }


    @Then("^Close the chrome$")
    public void closeChrome( ) {
        driver.quit();
    }


    @When("I write {string} in {string}")
    public void iWriteIn(String text, String label) throws InterruptedException {
        TextField textfield = new TextField(label, this.driver);
        textfield.setText(text);
    }

    @Then("Validate {string} text is {string}")
    public void validateTextIs(String label, String text) throws InterruptedException {
        TextField textfield = new TextField(label, this.driver);
        Boolean checker = textfield.validate_text(text);
        assertEquals(true, checker);
        Thread.sleep(2000);
    }

    @Then("Clear {string} text")
    public void clearText(String label) throws InterruptedException {
        TextField textfield = new TextField(label, this.driver);
        textfield.clear();
    }



    @When("I pick {string} in {string}")
    public void iPickIn(String preNumber , String label) throws InterruptedException {
        Thread.sleep(3000);
        Dropdown drop = new Dropdown(label, driver);
        drop.clickButton();
        drop.selectElement(preNumber);
        Thread.sleep(2000);

    }
}