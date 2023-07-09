package packages.uiwidgets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public abstract class BaseWidget {
    protected String label;

    protected WebElement webElement;


    public BaseWidget(String label) {
        this.label = label;
        initWidgetConstants();
    }

    protected void initWidgetConstants() {
        // Implementation specific to each subclass
    }

    //Todo: need more work on it
    protected void setWidgetWebElement(BaseWidget element, WebElement tempElement, int index) {
        if (tempElement == null) {
            WebElement locatedElement = webElement.findElements(element.getLocator()).get(index - 1);
            element.setWebElement(locatedElement);
        } else {
            element.setWebElement(tempElement);
        }
    }

    private By getLocator() {
        return null;
    }

    protected void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    protected WebElement getWebElement() {
        return webElement;
    }

}

