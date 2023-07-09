package packages.uiwidgets;

import org.openqa.selenium.*;
import packages.infra.CustomDriver;
import packages.infra.Util;




public class TextField extends BaseWidget{
    CustomDriver driver;

    public TextField(String label, CustomDriver driver) {
        super(label);
        this.driver = driver;
        this.webElement = getLocator(label, driver);
    }
    public WebElement getLocator(String label, CustomDriver driver) {
        String value = String.format("xpath=>//label[contains(text(), '%s')]/following-sibling::input",label);
        return driver.getElement(value, label);
    }

    public String getTextValue(){ return this.webElement.getAttribute("value"); }

    public boolean validate_text(String expected_text){
        return Util.verifyTextMatch(driver.getElementAttributeValue(this.webElement,"value"),expected_text);
    }

    public void setText(String text) { driver.sendData(this.webElement, text,this.label,true); }

    public void clear( ) { driver.clearData(this.webElement, label); }

}
