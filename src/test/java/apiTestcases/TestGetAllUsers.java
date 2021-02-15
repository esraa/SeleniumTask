package apiTestcases;

import base.TestBase;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestGetAllUsers extends TestBase {

    @BeforeMethod
    public void beforeMethod(Method method)
    {
        test = extent.startTest((method.getName()), method.getName());
        test.assignAuthor("Esraa Adel");
        test.assignCategory("Automate APIs using Rest Assured");
    }

    @Test
    public static void testGetAllUsers()
    {
        RestAssured.baseURI = prop.getProperty("APIUrl");
        RequestSpecification request = RestAssured.given();

        ArrayList<Integer> idsList = request.get().then().extract().path("id");

        int usersTotalNo=0;
        for(int a:idsList){

            System.out.println("The id value: "+a);
            usersTotalNo++;
        }
        Assert.assertEquals(usersTotalNo, 100, "Total number of users is incorrect ");
        test.log(LogStatus.PASS, "Total number of users is correct");

    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {

        validateTestResult(result);
        extent.endTest(test);
    }

}
