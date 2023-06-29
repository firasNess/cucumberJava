package Screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import steps.RunnerFile;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScreensFactory {
    private Map<String, Object> screens;
    private static final Logger log = LogManager.getLogger(RunnerFile.class.getName());
    public ScreensFactory() {
        screens = new HashMap<>();
    }

    public Object createScreen(List<String> objectsList, boolean storeScreen, boolean forceCreate, Object driver, String envType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Map<String, Class<?>> screensDict = new HashMap<>();
        //screensDict.put("ConfirmationForStructure", confirmation_for_structure.ConfirmationForStructure.class);
        Object screen;

        List<Object> screens = new ArrayList<>();
        for (String screenName : objectsList) {
            if (!this.screens.containsKey(screenName) || forceCreate) {
                log.info(new String(new char[150]).replace("\0", "-"));
                log.info("Creating " + screenName + " screen obj");
                Class<?> screenClass = screensDict.get(screenName);
                screen = screenClass.getDeclaredConstructor(Object.class, String.class).newInstance(driver, envType);
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
