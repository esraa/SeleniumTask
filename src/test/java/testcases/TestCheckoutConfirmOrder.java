package testcases;

import base.TestBase;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import testdata.TestDataReader;

import java.io.IOException;
import java.lang.reflect.Method;

public class TestCheckoutConfirmOrder extends TestBase {
	
	//create page objects
	HomePage homePageObj;
	AuthenticationPage authenticationPageObj;
	CreateAccountPage createAccountPageObj;
	ProductsPage productsPageObj;
	DetailedProductPage detailedProductPageObj;
	ShoppingCartPage shoppingCartPageObj;
	OrderHistoryPage orderHistoryPageObj;
	
	@BeforeMethod
	  public void beforeMethod(Method method) {

		openBrowser();

		homePageObj = new HomePage(driver, prop);
		authenticationPageObj = new AuthenticationPage(driver, prop);
		createAccountPageObj = new CreateAccountPage(driver, prop);
		productsPageObj = new ProductsPage(driver, prop);
		detailedProductPageObj = new DetailedProductPage(driver, prop);
		shoppingCartPageObj = new ShoppingCartPage(driver, prop);
		orderHistoryPageObj = new OrderHistoryPage(driver, prop);

		test = extent.startTest((method.getName()), method.getName());
	    test.assignAuthor("Esraa Adel");
	    test.assignCategory("Run on " + prop.getProperty(PROPERTY_WEBDRIVER_BROWSER) + " browser");
	    test.log(LogStatus.PASS, "Browser Launched Successfully");
	}
	
	@DataProvider(name = "testCheckout")
	public Object[][] testCheckoutDataProvider() throws IOException {
		return TestDataReader.fetchData(prop.getProperty("TestDataSheetPath"), "testCheckout");
	}

	@Test(priority = 1, dataProvider = "testCheckout")
	public void testCheckoutAndConfirmOrder(String email, String password, String category, String subCategory, String product,
							 String size, String color, String paymentMethod) {

		homePageObj.clickOnSignIn();


		Assert.assertTrue(authenticationPageObj.checkAuthenticationPageIsOpened(), "Navigation to authentication page is failed");
		test.log(LogStatus.PASS, "Navigation to authentication page done successfully");

		Assert.assertTrue(authenticationPageObj.login(email, password), "User login is failed");
		test.log(LogStatus.PASS, "User logged in successfully");

		Assert.assertTrue(homePageObj.selectItemFromMenu(category, subCategory), "Selecting "+subCategory+" from "+category+" is not done");
		test.log(LogStatus.PASS, "Selecting "+subCategory+" from "+category+" is done successfully");

		Assert.assertTrue(productsPageObj.selectMoreDetailsForProduct(product), "Clicking on More for the product failed");
		test.log(LogStatus.PASS, "Clicking on More for the product done successfully");

		Assert.assertTrue(detailedProductPageObj.addProductToCart(product, color, size), "Adding to cart is failed");
		test.log(LogStatus.PASS, "Adding to cart after selecting size and color is done successfully");

		Assert.assertTrue(detailedProductPageObj.proceedToCart(), "Going to shopping cart failed");
		test.log(LogStatus.PASS, "Going to shopping cart is done successfully");

		String referenceNo = shoppingCartPageObj.proceedToCartAndConfirmOrder(paymentMethod);

		Assert.assertNotNull(referenceNo, "Order is not added");
		test.log(LogStatus.PASS, "Order is completed successfully");

		Assert.assertTrue(shoppingCartPageObj.goToOrderHistory(), "Going to order history page is failed");
		test.log(LogStatus.PASS, "Going to order history page done successfully");

		Assert.assertTrue(orderHistoryPageObj.checkOrderInOrderHistory(referenceNo), "Order is not placed in order history page");
		test.log(LogStatus.PASS, "Order with the correct reference number is placed in order history page successfully");

	}

  @AfterMethod
  public void afterMethod(ITestResult result) throws IOException {
	  
	  validateTestResult(result);
	  closeBrowser();
	  extent.endTest(test);
  }

}
