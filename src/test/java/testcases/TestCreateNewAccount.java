package testcases;

import java.io.IOException;
import java.lang.reflect.Method;

import base.TestBase;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;
import pages.AuthenticationPage;
import pages.CreateAccountPage;
import pages.HomePage;
import testdata.TestDataReader;
import utils.Utils;

public class TestCreateNewAccount extends TestBase {
	
	//create page objects
	HomePage homePageObj;
	AuthenticationPage authenticationPageObj;
	CreateAccountPage createAccountPageObj;

	
	@BeforeMethod
	  public void beforeMethod(Method method) {

		openBrowser();

		homePageObj = new HomePage(driver, prop);
		authenticationPageObj = new AuthenticationPage(driver, prop);
		createAccountPageObj = new CreateAccountPage(driver, prop);

		test = extent.startTest((method.getName()), method.getName());
	    test.assignAuthor("Esraa Adel");
	    test.assignCategory("Run on " + prop.getProperty(PROPERTY_WEBDRIVER_BROWSER) + " browser");
	    test.log(LogStatus.PASS, "Browser Launched Successfully");
	}
	
	@DataProvider(name = "testCreateNewAccount")
	public Object[][] testCreateNewAccountDataProvider() throws IOException {
		return TestDataReader.fetchData(prop.getProperty("TestDataSheetPath"), "testCreateNewAccount");
	}

	@Test(priority = 1, dataProvider = "testCreateNewAccount")
	public void testCreateNewAccount(String title, String fname, String lname, String email, String password,
									 String addressFirstName, String addressLastName, String address, String city, String state,
									 String postalCode, String phoneNo, String addAlias) {

		homePageObj.clickOnSignIn();

		Assert.assertTrue(authenticationPageObj.checkAuthenticationPageIsOpened(), "Navigation to authentication page is failed");
		test.log(LogStatus.PASS, "Navigation to authentication page done successfully");

		email = Utils.getNewEmail(email);

		Assert.assertTrue(authenticationPageObj.createAccount(email), "Navigation to create an account page is failed");
		test.log(LogStatus.PASS, "Navigation to create an account page done successfully");

		boolean isAccountCreated = createAccountPageObj.createAccount(title, fname, lname, password, addressFirstName, addressLastName, address, city, state, postalCode,
				phoneNo, addAlias);

		Assert.assertTrue(isAccountCreated, "Account creation is failed");
		test.log(LogStatus.PASS, "Account creation is done successfully");

	}

  @AfterMethod
  public void afterMethod(ITestResult result) throws IOException {
	  
	  validateTestResult(result);
	  closeBrowser();
	  extent.endTest(test);
  }

}
