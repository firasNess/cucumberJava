package packages.uiwidgets;
import org.openqa.selenium.*;

import java.time.Duration;


public class TextField extends BaseWidget{

    public TextField(String label, WebDriver driver) {
        super(label, driver);
        this.webElement = getLocator(label, driver);
    }
    public WebElement getLocator(String label, WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String value = String.format("//label[contains(text(), '%s')]/following-sibling::input",label);
        return driver.findElement(By.xpath(value));
    }

    public String getTextValue(){ return this.webElement.getAttribute("value"); }

    public boolean validate_text(String expected_text){ return this.getTextValue().equals(expected_text); }

    public void setText(String text) { this.webElement.sendKeys(text); }

    public void clear( ) { this.webElement.clear(); }

}
