package packages.uiwidgets;

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


    public abstract String getLocator();

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    public abstract int getIndex();



    protected WebElement getWebElement() {
        return webElement;
    }

}

