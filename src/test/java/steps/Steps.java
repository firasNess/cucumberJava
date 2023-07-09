package steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import packages.Context;
import packages.uiwidgets.Dropdown;
import packages.uiwidgets.TextField;
import packages.RunnerFile;

import java.time.Duration;

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
    public void iWriteIn(String text, String label) {
        TextField textfield = new TextField(label, this.driver);
        textfield.setText(text);
    }

    @Then("Validate {string} text is {string}")
    public void validateTextIs(String label, String text) {
        TextField textfield = new TextField(label, this.driver);
        Boolean checker = textfield.validate_text(text);
        assertEquals(true, checker);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Then("Clear {string} text")
    public void clearText(String label) {
        TextField textfield = new TextField(label, this.driver);
        textfield.clear();
    }



    @When("I pick {string} in {string}")
    public void iPickIn(String preNumber , String label) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Dropdown drop = new Dropdown(label, driver);
        drop.clickButton();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        drop.selectElement(preNumber);



    }
}