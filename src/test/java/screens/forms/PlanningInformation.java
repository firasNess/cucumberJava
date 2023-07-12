package screens.forms;


import packages.infra.CustomDriver;
import packages.infra.Enums;
import java.util.ArrayList;
import java.util.Arrays;
import static packages.uiwidgets.WidgetFactory.createWidget;

public class PlanningInformation extends BasePage {
    private String pageTitle;
    private String urlPostfix;

    public PlanningInformation(CustomDriver driver, String envType) {
        super(driver, envType);
        this.pageTitle = "PlanningInformation";
        this.urlPostfix = "PlanningInformation";

    }

    @Override
    public String getUrltitle() {
        return this.pageTitle;
    }

    @Override
    public void navigateToPageUrl() {
        this.driver.navigate(this.mainUrl + this.urlPostfix);
    }

    @Override
    public void fillNeedToWaitElement() {
        this.mainElementsToWaitWhenLoad.add("שם פרטי");
    }

    @Override
    public void createPageWidgets() {
        widgets.put("שם פרטי", createWidget("TextField", "שם פרטי",1, this.driver, "", String.valueOf(Enums.StepNumber.FIRST)));
    }
}

