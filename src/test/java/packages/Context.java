package packages;

import java.util.Map;
import java.util.HashMap;

public class Context {
    private static Context instance;

    private Map<String, Object> variables;

    private Context() {
        variables = new HashMap<>();
    }

    public static Context getInstance() {
        if (instance == null) {
            synchronized (Context.class) {
                if (instance == null) {
                    instance = new Context();
                }
            }
        }
        return instance;
    }

    public Object getVariables(String key) {
        return variables.get(key);
    }

    public void setVariables(String name, Object var) {
        this.variables.put(name, var);
    }
}

