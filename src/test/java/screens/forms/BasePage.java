package screens.forms;

import packages.infra.CustomDriver;
import packages.infra.Enums;
import packages.infra.WidgetsDict;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static packages.uiwidgets.WidgetFactory.createWidget;

public abstract class BasePage {
    protected CustomDriver driver;
    protected String envType;
    protected String mainUrl;
    protected String urlPostfix;
    protected ArrayList mainElementsToWaitWhenLoad;
    WidgetsDict widgets;
    Map<String,Object> map = new HashMap<>();

    protected BasePage(CustomDriver driver, String envType) {
        this.driver = driver;
        this.envType = envType;
        this.mainUrl = (this.envType.equals("preprod")) ? "https://jeronlineforms-test.jerweb.jer/" : "https://jeronlineforms.jerusalem.muni.il/";
        this.urlPostfix = "";
        this.widgets = new WidgetsDict(map, this.driver);
        this.mainElementsToWaitWhenLoad = new ArrayList<>();
        this.createWidgets();
        this.fillNeedToWaitElement();

    }

    public String getUrlPostfix(){
        return this.urlPostfix;
    }

    public void setUrlPostfix(String url){
        this.urlPostfix = url;
    }

    public abstract String getUrltitle();


    protected void fillNeedToWaitElement() {

    }

    protected void createBaseWidgets() {
        this.widgets.put("שם פרטי", createWidget("TextField", "שם פרטי",1, driver, "", String.valueOf(Enums.StepNumber.FIRST)));

    }

    public abstract void navigateToPageUrl();

    public abstract void createPageWidgets();

    protected void createWidgets() {
        createBaseWidgets();
        createPageWidgets();
    }
}
