package steps.widgets_steps;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import packages.ui_widgets.Dropdown;
import packages.ui_widgets.TextField;
import static org.junit.Assert.assertEquals;


public class Steps {
    WebDriver driver = null;

    @Given("^I have open the browser$")
    public void openBrowser() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);

    }

    @When("^I open PlanningInformation Form")
    public void goToGoogle() {
        driver.navigate().to("https://jeronlineforms.jerusalem.muni.il/PlanningInformation");
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
    }

    @Then("Clear {string} text")
    public void clearText(String label) throws InterruptedException {
        TextField textfield = new TextField(label, this.driver);
        textfield.clear();
    }



    @When("I pick {string} in {string}")
    public void iPickIn(String preNumber , String label) throws InterruptedException {
        Dropdown drop = new Dropdown(label, driver);
        drop.clickButton();
        drop.selectElement(preNumber);
        Thread.sleep(2000);

    }
}