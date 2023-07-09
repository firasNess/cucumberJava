package steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import packages.Context;
import packages.infra.CustomDriver;
import packages.uiwidgets.Dropdown;
import packages.uiwidgets.TextField;
import packages.RunnerFile;

import java.time.Duration;

import static org.junit.Assert.assertEquals;


public class Steps {
    static Context context = Context.getInstance();
    CustomDriver customDriver = (CustomDriver) context.getVariables("driver");
    private static final Logger log = LogManager.getLogger(RunnerFile.class.getName());


    @Given("^I open PlanningInformation Form")
    public void goToGoogle() {
        customDriver.navigate("https://jeronlineforms.jerusalem.muni.il/PlanningInformation");
        try{
            Alert alert = customDriver.driver.switchTo().alert();
            alert.accept();
        }
        catch (Exception e){
            log.info("Alert Not available");
        }

    }


    @Then("^Close the chrome$")
    public void closeChrome( ) {
        customDriver.driver.quit();
    }


    @When("I write {string} in {string}")
    public void iWriteIn(String text, String label) {
        TextField textfield = new TextField(label, customDriver.driver);
        textfield.setText(text);
    }

    @Then("Validate {string} text is {string}")
    public void validateTextIs(String label, String text) {
        TextField textfield = new TextField(label, customDriver.driver);
        Boolean checker = textfield.validate_text(text);
        assertEquals(true, checker);
        customDriver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Then("Clear {string} text")
    public void clearText(String label) {
        TextField textfield = new TextField(label, customDriver.driver);
        textfield.clear();
    }



    @When("I pick {string} in {string}")
    public void iPickIn(String preNumber , String label) {
        customDriver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Dropdown drop = new Dropdown(label, customDriver.driver);
        drop.clickButton();
        customDriver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        drop.selectElement(preNumber);



    }
}