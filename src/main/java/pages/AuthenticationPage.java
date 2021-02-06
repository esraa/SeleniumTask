package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Properties;

public class AuthenticationPage extends PageBase {

    // Variables
    private WebDriver driver;
    private Properties properties;

    // Define the By Elements
    By createAccountHeader = By.xpath("//form[@id='create-account_form']/h3");
    By emailCreate = By.id("email_create");
    By submitCreate = By.id("SubmitCreate");
    By emailToLogin = By.id("email");
    By passwordToLogin = By.id("passwd");
    By submitLogin = By.id("SubmitLogin");


    // Functions
    public AuthenticationPage(WebDriver driver, Properties properties) {
        this.driver = driver;
        this.properties = properties;
        setDriver(driver);
    }

    public boolean checkAuthenticationPageIsOpened()
    {

        return isElementDisplayed(createAccountHeader, "Create account");
    }

    public boolean createAccount(String email)
    {
        setElement(emailCreate, email, "email");
        clickElement(submitCreate, "register");

        By createAccountHeader = By.xpath("//div[@id='noSlide']/h1");
        return isElementDisplayed(createAccountHeader, "create account");
    }

    public boolean login(String email, String password)
    {
        setElement(emailToLogin, email, "email");
        setElement(passwordToLogin, password, "password");

        clickElement(submitLogin, "login");

        By welcomeToAccount = By.xpath("//p[@class='info-account']");
        return isElementDisplayed(welcomeToAccount, "welcome");
    }

}

