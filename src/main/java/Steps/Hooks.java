package Steps;

import Base.BaseUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Hooks extends BaseUtil {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");

    // TestRail variables
    ArrayList<String> idList;
    String currentId;
    boolean skipped = false;

    public Hooks() {
    }

    @Before
    public void InitializeTest(Scenario scenario) {
        scenarioName = scenario.getName();

        // Setting ChromeOptions to change the download folder to the one located in project directory
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("-disable-notifications");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", dLFolder);
        options.setExperimentalOption("prefs", prefs);
        try {
            final Properties config = new Properties();
            config.load(new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "config.properties"));
            if (config.getProperty("headless").equalsIgnoreCase("true")) {
                options.addArguments("--headless");
                options.addArguments("window-size=1920,1080");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
        } catch (IOException | NullPointerException e) {
            System.out.println("Did not find \"headless\" flag. Not running headless.");
        }
        System.out.println("Opening Chrome browser");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    }

    @After
    public void TearDownTest(Scenario scenario) {
        if (scenario.isFailed()) {
            // TakesScreenshot
            String sName = scenarioName.replaceAll("[^\\d\\w]", "") + "_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss"));

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(srcFile, new File("target/screenshots/" + sName + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!skipped) {
//            driver.quit();
        }

    }
}
