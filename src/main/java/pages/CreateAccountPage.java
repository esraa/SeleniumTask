package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.Properties;

public class CreateAccountPage extends PageBase {

    // Variables
    private final WebDriver driver;
    private final Properties properties;

    // Define the By Elements
    By mrTitle = By.id("id_gender1");
    By mrsTitle = By.id("id_gender2");
    By firstNameBy = By.id("customer_firstname");
    By lastNameBy = By.id("customer_lastname");
    By passwordBy = By.id("passwd");
    By addressFirstNameBy = By.id("firstname");
    By addressLastNameBy = By.id("lastname");
    By addressBy = By.id("address1");
    By cityBy = By.id("city");
    By stateBy = By.id("id_state");
    By zipCodeBy = By.id("postcode");
    By phoneMobileBy = By.id("phone_mobile");
    By addAliasBy = By.id("alias");
    By submitBy = By.id("submitAccount");

    // Functions
    public CreateAccountPage(WebDriver driver, Properties properties) {
        this.driver = driver;
        this.properties = properties;
        setDriver(driver);
    }

    public boolean createAccount(String title, String fname, String lname, String password,
                              String addressFirstName, String addressLastName, String address, String city, String state,
                              String postalCode, String phoneNo, String addAlias)
    {
        if (title.equals("Mr")) {
            clickElement(mrTitle, "Mr");
        } else if (title.equals("Mrs")) {
            clickElement(mrsTitle, "Mrs");
        }
        setElement(firstNameBy, fname, "first name");
        setElement(lastNameBy, lname, "last name");
        setElement(passwordBy, password, "password");
        setElement(addressFirstNameBy, addressFirstName, "address first name");
        setElement(addressLastNameBy, addressLastName, "address last name");
        setElement(addressBy, address, "address");
        setElement(cityBy, city, "city");
        selectItemFromList(stateBy, state, "state");
        setElement(zipCodeBy, postalCode, "postal code");
        setElement(phoneMobileBy, phoneNo, "phone number");
        setElement(addAliasBy, addAlias, "address alias");

        clickElement(submitBy, "submit");

        By welcomeToAccount = By.xpath("//p[@class='info-account']");
        return isElementDisplayed(welcomeToAccount, "welcome");
    }

}

