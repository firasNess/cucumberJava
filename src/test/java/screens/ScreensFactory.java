package screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packages.infra.CustomDriver;
import screens.forms.PlanningInformation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScreensFactory {
    private Map<String, Object> screens;
    private static final Logger log = LogManager.getLogger(ScreensFactory.class.getName());
    public ScreensFactory() {
        screens = new HashMap<>();
    }

    public Object createScreen(List<String> requestedScreens, boolean storeScreen, boolean forceCreate, CustomDriver driver, String envType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Map<String, Object> screensDict = new HashMap<>();
        PlanningInformation planningInformation =  new PlanningInformation(driver,envType);
        screensDict.put("PlanningInformation", planningInformation);

        Object screen;

        List<Object> screens = new ArrayList<>();

        if (!(storeScreen == false)) {
            storeScreen = true;
        }

        for (String screenName : requestedScreens) {
            if (!this.screens.containsKey(screenName) || forceCreate) {
                log.info("-".repeat(150));
                log.info("Creating " + screenName + " screen obj");
                screen = screensDict.get(screenName);

                if (storeScreen) {
                    this.screens.put(screenName, screen);
                }
            } else {
                screen = this.screens.get(screenName);
            }
            if (screen != null) {
                screens.add(screen);
            }
        }

        if (screens.size() == 1) {
            return screens.get(0);
        }

        return screens;
    }
}
