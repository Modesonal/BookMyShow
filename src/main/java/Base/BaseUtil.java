package Base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaseUtil {
    // Driver variables
    public static WebDriver driver;
    public static JavascriptExecutor js;
    public static WebDriverWait wait;

    public static String scenarioName;

    // StepDef variables to allow transfer of information between StepDef functions
    // Variables to track location and movement
    public static String currentLogin;
    public static String currentApp;
    // Values used by Step Definitions
    public static Map<String, String> valueStore = new HashMap<>();
    public static HashMap<String, String> editedValues = new HashMap<>();

    public static String workOrderNumber;
    public static int rowCount;
    public static int gridRecords;
    // Date time variables
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");

    // Class initializers



    // filePath is currently set to C:\Users\<USER_NAME>. Feel free to change it
    // filePath is used to determine where screenshots and reports are saved on your system
    public static String filePath = System.getProperty("user.home") + "\\Desktop\\QA-Archetype-Files";

    // Location of attachment on your system
    public static String attachPath(String relativePath) {
        File file = new File(relativePath);
        return file.getAbsolutePath();
    }

    // Folder where files downloaded during tests will be stored
    public static String dLFolder = attachPath("src/test/resources/downloads");
    public static String attachLocation = attachPath("src/test/resources/attachments/sir_fluffington.jpg");
    // File name of the attachment with the type stripped out (i.e., If it's my-attachment.jpg, just use "my-attachment")
    public static String attachName = "sir_fluffington";
        public static boolean pageLoaded() {
        // wait for jQuery to load and catch if jQuery doesn't exist
        ExpectedCondition<Boolean> jQueryLoad = driver -> {

            try {
                wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
                return true;
            } catch (TimeoutException e) {
                System.err.println("Timeout exception occurred for pageLoaded");
                return false;
            }
        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver -> js.executeScript("return document.readyState").toString().equals("complete");

        try {
            return wait.until(jQueryLoad) && wait.until(jsLoad);
        } catch (TimeoutException e) {
            System.err.println("Timeout exception occurred for pageLoaded");
        }
        return false;

    }
    /**
     * This method allows users to introduce a static wait in their automation scripts without the need to add explicit steps in the feature files.
      * @param seconds The number of seconds to wait. Must be a positive integer.
     */
    public static void waitForSeconds(int seconds) {
         int timeToWait = seconds * 1000;
            try {
             Thread.sleep(2000);
         } catch (InterruptedException e) {
             throw new RuntimeException(e);
         }
     }
}
