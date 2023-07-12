package packages.infra;

import java.util.Map;

public class WidgetsDict  {
    Map<String,Object> widgets ;
    CustomDriver driver;
    public WidgetsDict(Map<String,Object> widgets,CustomDriver driver){
        this.widgets = widgets;
        this.driver = driver;
    }

    public Object get(String key){
        return this.widgets.get(key);
    }

    public void put (String key , Object value){
        this.widgets.put(key,value);
    }
}

