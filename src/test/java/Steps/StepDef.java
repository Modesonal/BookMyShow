package Steps;

import Base.BaseUtil;
import com.aventstack.extentreports.util.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.testng.TestNGCucumberRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.cucumber.java.en.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.aventstack.extentreports.util.Assert.*;

public class StepDef extends BaseUtil {

    String currentTab;
    String otp;
    @Given("I am on the BookMyShow homepage")
    public void navigateToHomePage() {
        driver.get("https://in.bookmyshow.com/explore/home/");
    }

    @And("I select Bengaluru as the city")
    public void selectCity() {
        WebElement bengaluruOption = driver.findElement(By.xpath("//span[normalize-space()='Bengaluru']"));
        bengaluruOption.click();
    }

    @When("I click on Sign In")
    public void clickSignIn() {
        WebElement signInButton = driver.findElement(By.xpath("//div[normalize-space()='Sign in']"));
        signInButton.click();
    }

    @And("I click on Continue with Email")
    public void clickContinueWithEmail() {
        WebElement continueWithEmailButton = driver.findElement(By.xpath("//div[contains(text(),'Continue with Email')]"));
        continueWithEmailButton.click();
    }

    @And("I enter {string} and click on continue")
    public void enterEmailAndContinue(String email) {
        WebElement emailField = driver.findElement(By.xpath("//input[@id='emailId']"));
        emailField.sendKeys(email);
        WebElement continueButton = driver.findElement(By.xpath("//button[normalize-space()='Continue']"));
        continueButton.click();
    }

    @And("I go to mailinator")
    public void goToYopmail() {
        driver.get("https://www.mailinator.com/");
    }

    @And("I type {string} and access the inbox")
    public void accessInbox(String email) {
        WebElement emailInput = driver.findElement(By.xpath("//input[@id='search']"));
        emailInput.sendKeys(email);
        WebElement checkInboxButton = driver.findElement(By.xpath("//button[normalize-space()='GO']"));
        checkInboxButton.click();
    }

    @And("I locate the latest email from BookMyShow and fetch the OTP")
    public void fetchOTP() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String mailData= driver.findElement(By.xpath("(//tbody)[2]/tr[1]/td[3]")).getText();
        String pattern = "\\b\\d{6}\\b";

        Pattern otpPattern = Pattern.compile(pattern);
        Matcher matcher = otpPattern.matcher(mailData);

        if (matcher.find()) {
            otp = matcher.group();
            System.out.println("OTP is: " + otp);
        } else {
            System.out.println("No OTP found in the text.");
        }
    }

    @And("I come back to the Sign-In Page and enter the OTP")
    public void enterOTP() {
        driver.switchTo().window(currentTab);
        for (int i = 0; i < otp.length(); i++) {
            char digit = otp.charAt(i);
            String inputXPath = String.format("//div[normalize-space()='Verify your Email Address']//following-sibling::div//following-sibling::div/input[%d]", i + 1);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            By otpInputLocator = By.xpath(inputXPath);
            WebElement otpInput = wait.until(ExpectedConditions.elementToBeClickable(otpInputLocator));
            otpInput.clear();
            otpInput.sendKeys(Character.toString(digit));
        }
        WebElement continueButton = driver.findElement(By.xpath("//button[normalize-space()='Continue']"));
        continueButton.click();
    }

    @Then("I validate that the user is successfully signed in")
    public void validateSignIn() {
        // Implement validation logic to ensure the user is signed in
    }

    @And("I verify that {string} is displayed")
    public void verifyTextIsDisplayed(String text) {
        String actualData = driver.findElement(By.xpath("//div[@class='bwc__sc-1shzs91-3 jfsXdp']//div[2]")).getText();
        org.testng.Assert.assertEquals(text,actualData,"Text does not match the expected value." );
    }

    @And("I open new tab")
    public void iOpenNewTab() {
        ((JavascriptExecutor) driver).executeScript("window.open();");

        // Switch to the new tab
        currentTab = driver.getWindowHandle();
        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(currentTab)) {
                driver.switchTo().window(tab);
                break;
            }
        }
    }

}
