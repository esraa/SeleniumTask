package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Properties;

public class DetailedProductPage extends PageBase {

    // Variables
    private WebDriver driver;
    private Properties properties;
    private WebDriverWait wait;

    // Define the By Elements
    By sizeBy = By.id("group_1");
    By addToCartBy = By.xpath("//p[@id='add_to_cart']/button");
    By successMessageBy = By.xpath("//div[contains(@class, 'layer_cart_product ')]/h2");
    By productInConfirmationPage = By.id("layer_cart_product_title");
    By proceedToCheckout = By.xpath("//div[@class='button-container']/a");

    // Functions
    public DetailedProductPage(WebDriver driver, Properties properties) {
        this.driver = driver;
        this.properties = properties;
        setDriver(driver);
        wait = new WebDriverWait(driver, 200);
    }


    public boolean addProductToCart(String product, String color, String size)
    {
        selectItemFromList(sizeBy, size);
        clickElement(By.name(color));
        clickElement(addToCartBy);

        String getSuccessMessage = getElementText(successMessageBy);
        String productValue = getElementText(productInConfirmationPage);
        return (getSuccessMessage.trim().equalsIgnoreCase(properties.getProperty("successMessage")) &&
                productValue.equalsIgnoreCase(product));
    }

    public boolean proceedToCart()
    {
        clickElement(proceedToCheckout);
        return isElementDisplayed(By.id("cart_title"));
    }
}

