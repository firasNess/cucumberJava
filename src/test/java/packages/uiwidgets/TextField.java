package packages.uiwidgets;

import io.cucumber.java.sl.In;
import org.openqa.selenium.*;
import packages.infra.CustomDriver;
import packages.infra.Util;




public class TextField extends BaseWidget{
    private CustomDriver driver;
    private Integer index = 0;
    private String pathLocator = null;
    private String stepNumber = null;

    public TextField(String label, Integer index, CustomDriver driver, String pathLocator, String stepNumber) {
        super(label);
        this.driver = driver;
        this.index = index;
        this.pathLocator= pathLocator;
        this.stepNumber = stepNumber;
        this.webElement = null;
    }
    public String getLocator() {
        String value = String.format("xpath=>//label[contains(text(), '%s')]/following-sibling::input", this.label);
        return value;
    }

    public String getTextValue(){ return this.webElement.getAttribute("value"); }

    public boolean validate_text(String expected_text){
        return Util.verifyTextMatch(this.driver.getElementAttributeValue(this.webElement,"value"),expected_text);
    }

    public void setText(String text) { this.driver.sendData(this.webElement, text,this.label,true); }

    public void clear( ) { this.driver.clearData(this.webElement, this.label); }



}
