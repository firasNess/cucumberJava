package packages.infra;

import io.cucumber.java.an.E;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import packages.RunnerFile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CheckPoint {
    public static HashMap<String, String> resultMap = new HashMap<String, String>();
    private static String PASS = "PASS";
    private static String FAIL = "FAIL";
    private static final Logger log = LogManager.getLogger(RunnerFile.class.getName());


    /***
     * Clears the results hash map
     */
    public static void clearHashMap() {
        System.out.print("Clearing Results Hash Map");
        resultMap.clear();
    }

    /***
     * Set status of the Result Map
     * @param mapKey
     * @param status
     */
    private static void setStatus(String mapKey, String status) {
        resultMap.put(mapKey, status);
        System.out.println(mapKey + " :-> " + resultMap.get(mapKey));
    }

    public static void mark(String testName, boolean result) {
        testName = testName.toLowerCase();
        String mapKey = testName;
        try {
            if (result) {
                setStatus(mapKey, PASS);
            } else {
                setStatus(mapKey, FAIL);
            }
        } catch (Exception e) {
            log.error("Exception Occurred...");
            setStatus(mapKey, FAIL);
            e.printStackTrace();
        }
    }

    //todo: need more fix
    public static void markFinal() {

        Iterator<Map.Entry<String, String>> iterator = resultMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();

            try{
                if (value.equals("FAIL")) {
                    log.info(String.format("The step: %s is Failed", key));
                    Assert.assertEquals(false, true);
                } else {
                    log.info(String.format("The step: %s is Passed", key));
                    Assert.assertEquals(true,true);
                }
            } catch (AssertionError e){
                log.error(e.getMessage());
            }


        }

    }
}












