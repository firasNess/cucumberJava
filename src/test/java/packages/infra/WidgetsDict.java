package packages.infra;

import org.openqa.selenium.WebElement;
import packages.uiwidgets.BaseWidget;

import java.util.List;
import java.util.Map;

public class WidgetsDict  {
    Map<String,Object> widgets ;
    CustomDriver driver;
    public WidgetsDict(Map<String,Object> widgets,CustomDriver driver){
        this.widgets = widgets;
        this.driver = driver;
    }

    public WebElement get(String key){
        BaseWidget widget = (BaseWidget) this.widgets.get(key);
        String locator = widget.getLocator();
        List<WebElement> list = driver.getElementList(locator);
        if (list.size() == 1){
            return list.get(0);
        }
        else {
            return list.get(widget.getIndex()-1);
        }
    }

    public void put (String key , Object value){
        this.widgets.put(key,value);
    }
}

