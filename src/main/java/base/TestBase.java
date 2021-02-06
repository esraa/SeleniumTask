package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * This is a TestBase class to hold generic functions that used in all test scripts
 *
 */

public class TestBase {

    public static final String PROPERTY_WEBDRIVER_BROWSER = "browser.type";
    public static final String PROPERTY_WEBDRIVER_EXECUTABLE = "webdriver.executablePath";
    public static final String SYSTEM_PROPERTY_CHROME_DRIVER = "webdriver.chrome.driver";
    public static final String PROPERTY_WEB_URL = "web.url";
    
    public WebDriver driver;
    public static Properties prop;
    public static ExtentReports extent;
    public static ExtentTest test;
    public static String log4jConfPath;
    final static Logger logger = Logger.getLogger(TestBase.class);

    private static void initExtentReports() {
    	String currentDate = java.time.LocalDateTime.now().toString(); 
		currentDate = currentDate.replaceAll(":", "_");
		currentDate = currentDate.replaceAll("\\.", "-");
		extent = new ExtentReports(prop.getProperty("testReportPath") + "_"+ currentDate + ".html",true);
        extent.loadConfig(new File(prop.getProperty("extentReportConfig")));
        
    }

	@BeforeSuite
	public void beforeSuite() throws IOException {

        prop = readProperties(System.getProperty("user.dir") + "\\src\\main\\resources\\config\\config.properties");
        initExtentReports();

        log4jConfPath = prop.getProperty("log4jPropertiesPath");
        PropertyConfigurator.configure(log4jConfPath);

    }

	@AfterSuite
	 public void afterSuite() {
		 extent.close();
	 }
    /**
     * Read Properties
     *
     * @param filePath configuration file path
     * @throws IOException in case of file not found
     */
	public static Properties readProperties(String filePath) throws IOException {

        FileInputStream testProperties = new FileInputStream(filePath);
        prop = new Properties();
        prop.load(testProperties);
        logger.info("Properties file is loaded");
        return prop;

	}

    /**
     * Open Browser
     * No-argument
     */
    public void openBrowser() {

        final String browserType = prop.getProperty(PROPERTY_WEBDRIVER_BROWSER, "").trim();
        final String driverExecutable = prop.getProperty(PROPERTY_WEBDRIVER_EXECUTABLE, "").trim();
        final String url = prop.getProperty(PROPERTY_WEB_URL);

        if (browserType.equalsIgnoreCase("chrome")) {
            System.setProperty(SYSTEM_PROPERTY_CHROME_DRIVER, driverExecutable);
            final ChromeOptions options = new ChromeOptions();
            HashMap<String, Object> chromePrefs = new HashMap<>();
	        chromePrefs.put("profile.default_content_settings.popups", 0);
	        chromePrefs.put("safebrowsing.enabled", "true"); 
	        options.addArguments("--start-maximized");
	        options.setExperimentalOption("prefs", chromePrefs);
	        DesiredCapabilities cap = DesiredCapabilities.chrome();
	        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	        cap.setCapability(ChromeOptions.CAPABILITY, options);
            cap.setCapability("resolution", "1024x768");
	        
            driver = new ChromeDriver(options);
        }
        else {
            throw new IllegalArgumentException("Browser " + browserType + " not supported.");
        }

        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
        // Open the URL
        logger.info("Opening Browser [" + browserType + "] with url [" + url + "]");
        driver.get(url);
  
    }

    /**
     * Close Browser
     * No-argument
     */
    public void closeBrowser() {
        // Close browser
        driver.quit();
        logger.info("Closing Browser..");
    }

    /**
     * Take screenshot
     * Save screenshot of failed script with current date in screenshot folder
     *
     * @param screenshotName (script name)
     * @throws IOException in case of file not found
     */
    public String takeScreenShot(String screenshotName) throws IOException {
        String path = System.getProperty("user.dir") + prop.getProperty("screenShotsPath");
        //below line is just to append the date format with the screenshot name to avoid duplicate names
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        //after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination = path + screenshotName + dateName + ".png";
        File finalDestination = new File(destination);

        FileUtils.copyFile(source, finalDestination);

        //Returns the captured file path
        return destination;
    }

    public void validateTestResult(ITestResult result) throws IOException {
		
	    switch (result.getStatus()) {
	    case ITestResult.FAILURE:
	        test.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
			test.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());
		    test.log(LogStatus.FAIL, "Screenshot below: " + test.addScreenCapture(takeScreenShot(result.getMethod().getMethodName())));
			break;
	    case ITestResult.SKIP:
	    	test.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
	        break;
	    case ITestResult.SUCCESS:
	    	test.log(LogStatus.PASS, "Test Case  is passed");
	        break;
	    default:
	        break;
	    }
	 

	}

   
}