package packages.uiwidgets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import packages.infra.CustomDriver;
import java.util.List;


public class Dropdown extends BaseWidget {

    private CustomDriver driver;

    public Dropdown(String label, CustomDriver driver ) {
        super(label);
        this.driver = driver;
        this.webElement = getLocator(label, driver);
    }

    public WebElement getLocator(String label, CustomDriver driver) {
        String value = String.format("xpath=>//label[contains(text(), '%s')]/following-sibling::div", label);
        return driver.getElement(value, label);
    }

    public void clickButton() {

        WebElement dropDownOpen = this.webElement.findElement(By.xpath("./..//p-dropdown/div"));
        String dropDownExpanded = dropDownOpen.getAttribute("open");
        if (dropDownExpanded == null || dropDownExpanded.equals("false")) {
            dropDownOpen.click();
        }

    }

    public void selectElement(String pre) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver.driver, 15);
        List<WebElement> listOfItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(".//ul//p-dropdownitem")));
        //List<WebElement> listOfItems = driver.driver.findElements(By.xpath(".//ul//p-dropdownitem"));
        for (WebElement item : listOfItems) {
            if (item.getText().equals(pre)) {
                driver.elementClick(item,label);
                break;
            }
        }
    }

}



