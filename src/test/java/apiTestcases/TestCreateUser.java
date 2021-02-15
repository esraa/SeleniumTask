package apiTestcases;

import base.TestBase;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.core.Is;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

public class TestCreateUser extends TestBase {

    @BeforeMethod
    public void beforeMethod(Method method)
    {
        test = extent.startTest((method.getName()), method.getName());
        test.assignAuthor("Esraa Adel");
        test.assignCategory("Automate APIs using Rest Assured");
    }

    @Test
    public static void testCreateUser()
    {
        RestAssured.baseURI = prop.getProperty("APIUrl");
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "Create new user");
        requestParams.put("body", "EsraaAdel");
        requestParams.put("userId", "33");

        request.body(requestParams.toJSONString());
        Response response = request.post();
        test.log(LogStatus.PASS, "Send the request with json body " + requestParams.toJSONString());

        Assert.assertNotNull(response.body().asString(), "Response body is null");
        test.log(LogStatus.PASS, "The response not empty: " + response.body().asString());

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, HttpStatus.SC_CREATED, "Http status code is incorrect");
        test.log(LogStatus.PASS, "Http status code is correct = 201 created");

        response.then().assertThat().body("id", Is.is(101));
        test.log(LogStatus.PASS, "Id in the response is correct = 101");

    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {

        validateTestResult(result);
        extent.endTest(test);
    }

}
