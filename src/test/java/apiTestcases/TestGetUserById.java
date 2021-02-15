package apiTestcases;

import base.TestBase;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.lang.reflect.Method;


public class TestGetUserById extends TestBase {

    @BeforeMethod
    public void beforeMethod(Method method)
    {
        test = extent.startTest((method.getName()), method.getName());
        test.assignAuthor("Esraa Adel");
        test.assignCategory("Automate APIs using Rest Assured");
    }

    @Test(priority = 1)
    public static void testGetValidUserById()
    {
        RestAssured.baseURI = prop.getProperty("APIUrl");
        RequestSpecification request = RestAssured.given();

        Response response = request.get("/35");
        test.log(LogStatus.PASS, "Send get request by valid user id: 35");

        Assert.assertNotNull(response.body().asString(), "Response body is null");
        test.log(LogStatus.PASS, "The response not empty: " + response.body().asString());

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, HttpStatus.SC_OK, "Http status code is incorrect");
        test.log(LogStatus.PASS, "Http status code is correct = 200");


    }

    @Test (priority = 2)
    public void testGetInvalidUser()
    {
        RestAssured.baseURI = prop.getProperty("APIUrl");
        RequestSpecification request = RestAssured.given();
        Response response = request.get("/78789798798");
        test.log(LogStatus.PASS, "Send get request by invalid user id");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, HttpStatus.SC_NOT_FOUND, "Http status code is incorrect");
        test.log(LogStatus.PASS, "Status code is not found = 404");

    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {

        validateTestResult(result);
        extent.endTest(test);
    }

}
