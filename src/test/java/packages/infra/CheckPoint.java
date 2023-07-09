package packages.infra;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import packages.RunnerFile;

import java.util.ArrayList;
import java.util.HashMap;

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

    public static void markFinal() {
        ArrayList<String> resultList = new ArrayList<String>();

        for (String key: resultMap.keySet()) {
            resultList.add(resultMap.get(key));
        }

        for (int i = 0; i < resultList.size(); i++) {
            if (resultList.contains(FAIL)) {
                log.info("Test Method Failed");
                Assert.fail();
            } else {
                log.info("Test Method Successful");
                Assert.assertTrue(true);
            }
        }
    }
}












