package packages.ui_widgets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;


public class Dropdown extends BaseWidget {

    public Dropdown(String label, WebDriver driver) {
        super(label, driver);
        this.webElement = getLocator(label, driver);
    }

    public WebElement getLocator(String label, WebDriver driver) {
        String value = String.format("//label[contains(text(), '%s')]/following-sibling::div", label);
        return driver.findElement(By.xpath(value));
    }

    public void clickButton() {

        WebElement dropDownOpen = this.webElement.findElement(By.xpath("./..//p-dropdown/div"));
        String dropDownExpanded = dropDownOpen.getAttribute("open");
        if (dropDownExpanded == null || dropDownExpanded.equals("false")) {
            dropDownOpen.click();
        }

    }

    public void selectElement(String pre) {
        List<WebElement> listOfItems = driver.findElements(By.xpath(".//ul//p-dropdownitem"));
        for (WebElement item : listOfItems) {
            if (item.getText().equals(pre)) {
                item.click();
                return;
            }
        }
    }

}



