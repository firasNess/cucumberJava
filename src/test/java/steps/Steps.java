package steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packages.Context;
import packages.infra.CheckPoint;
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
            customDriver.acceptAlert();
        }
        catch (Exception e){
            log.info("Alert Not available");
        }

    }


    @When("I write {string} in {string}")
    public void iWriteIn(String text, String label) {
        TextField textfield = new TextField(label, customDriver);
        textfield.setText(text);
    }

    //todo: checkPiont method should be in after all
    @Then("Validate {string} text is {string}")
    public void validateTextIs(String label, String text) {
        TextField textfield = new TextField(label, customDriver);
        CheckPoint.mark(String.format("Validate {%s} text is {%s}", label,text),textfield.validate_text(text));

    }

    @Then("Clear {string} text")
    public void clearText(String label) {
        TextField textfield = new TextField(label, customDriver);
        textfield.clear();
    }



    @When("I pick {string} in {string}")
    public void iPickIn(String preNumber , String label) throws InterruptedException {
        customDriver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Dropdown drop = new Dropdown(label, customDriver);
        drop.clickButton();
        customDriver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        drop.selectElement(preNumber);



    }
}