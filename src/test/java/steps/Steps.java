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
import screens.ScreensFactory;
import screens.forms.BasePage;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Steps {
    static Context context = Context.getInstance();
    CustomDriver customDriver = (CustomDriver) context.getVariables("driver");
    private static final Logger log = LogManager.getLogger(RunnerFile.class.getName());
    List<String> requestedScreens = new ArrayList<>();


    @Given("I navigate to {string} Screen")
    public void navigate(String screenName) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        requestedScreens.add(screenName);
        ScreensFactory currentScreen = new ScreensFactory();
        BasePage currentPage = (BasePage) currentScreen.createScreen(requestedScreens, true, false, customDriver, "prod");
        BasePage createdPage = (BasePage) context.getVariables("currentPage");

        if(context.getVariables("currentPage") == null){
            context.setVariables("currentPage", screenName);
        } else if(!createdPage.getUrltitle().equals(currentPage.getUrltitle())) {
            context.setVariables("currentPage", currentPage);
        }
        currentPage.navigateToPageUrl();
        try{
            customDriver.acceptAlert();
        }
        catch (Exception e){
            log.info("Alert Not available");
        }
        Thread.sleep(5000);
    }




//
//    @When("I write {string} in {string}")
//    public void iWriteIn(String text, String label) {
//        TextField textfield = new TextField(label);
//        textfield.setText(text);
//    }



//    //todo: checkPiont method should be in after all
//    @Then("Validate {string} text is {string}")
//    public void validateTextIs(String label, String text) {
//        TextField textfield = new TextField(label, customDriver);
//        CheckPoint.mark(String.format("Validate {%s} text is {%s}", label,text),textfield.validate_text(text));
//
//    }
//
//    @Then("Clear {string} text")
//    public void clearText(String label) {
//        TextField textfield = new TextField(label, customDriver);
//        textfield.clear();
//    }
//
//
//
//    @When("I pick {string} in {string}")
//    public void iPickIn(String preNumber , String label) throws InterruptedException {
//        customDriver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        Dropdown drop = new Dropdown(label, customDriver);
//        drop.clickButton();
//        customDriver.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
//        drop.selectElement(preNumber);
//
//
//
//    }
}