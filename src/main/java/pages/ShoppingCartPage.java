package pages;

import base.PageBase;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Locale;
import java.util.Properties;

public class ShoppingCartPage extends PageBase {

    // Variables
    private WebDriver driver;
    private Properties properties;
    private WebDriverWait wait;

    // Define the By Elements
    By proceedToCheckoutSummary = By.xpath("//*[contains(@class, 'cart_navigation')]/a[1]");
    By proceedToCheckout = By.xpath("//*[contains(@class, 'cart_navigation')]/button");
    By agreeTerms = By.id("cgv");
    By bankWire = By.className("bankwire");
    By cheque = By.className("cheque");
    By paymentMethodTitleBy = By.xpath("//h3[@class='page-subheading']");
    By backToOrderBy = By.xpath("//*[contains(@class, 'cart_navigation')]/a");
    By orderDetailsBy = By.xpath("//div[@class='box']");

    // Functions
    public ShoppingCartPage(WebDriver driver, Properties properties) {
        this.driver = driver;
        this.properties = properties;
        setDriver(driver);
        wait = new WebDriverWait(driver, 200);
    }

    public String proceedToCartAndConfirmOrder(String payMethod)
    {
        clickElement(proceedToCheckoutSummary);
        clickElement(proceedToCheckout);
        jsClick(agreeTerms);
        clickElement(proceedToCheckout);

        if(payMethod.equals("wire")) {
            clickElement(bankWire);
        } else if (payMethod.equals("cheque")) {
            clickElement(cheque);
        }
        String paymentMethodTitle = getElementText(paymentMethodTitleBy);
        if(paymentMethodTitle.contains(payMethod.toUpperCase())){
            clickElement(proceedToCheckout);
            String orderDetails = getElementText(orderDetailsBy);
            String parsed = StringUtils.substringAfter(orderDetails, "order reference ");
            return parsed.substring(0, parsed.indexOf(' '));
        }
        return null;
    }

    public boolean goToOrderHistory()
    {
        clickElement(backToOrderBy);
        String title = getElementText(By.xpath("//span[@class='navigation_page']"));

        return title.equals("Order history");
    }
}

