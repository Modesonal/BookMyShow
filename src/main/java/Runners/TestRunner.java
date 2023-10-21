package Runners;

import io.cucumber.testng.*;

@CucumberOptions(features= {"src/test/resources/Features"},
//
        plugin = {"extentReport.ExtentReport","Base.FailedStepReport:target/failedstep.txt","json:target/cucumber.json","html:target/cucumber-html/index.html"},
        glue = "Steps", dryRun = false,
        monochrome = true,
        tags = "")
//extends AbstractTestNGCucumberTests
public class TestRunner {

}
