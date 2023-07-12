package packages.uiwidgets;

import packages.infra.CustomDriver;
import java.util.*;

public class WidgetFactory {
    private static Map<String, Object> widgetMap = new HashMap<>();


    public static Object createWidget(String widgetType, String label, Integer index, CustomDriver driver, String pathLocator, String stepNumber) {
        if(widgetMap.get(widgetType) == null){
            widgetMap.put("TextField", new TextField(label, index, driver, pathLocator, stepNumber));
        }
        return widgetMap.get(widgetType);
    }

    private Object getWidget(String label) {
        // we will use it to get a value from map that contain the label as key, and the object as value
        return widgetMap.containsValue(label);

    }



}



