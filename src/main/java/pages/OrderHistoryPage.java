package pages;

import base.PageBase;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Properties;

public class OrderHistoryPage extends PageBase {

    // Variables
    private WebDriver driver;
    private Properties properties;
    private WebDriverWait wait;

    // Define the By Elements
    By ordersListBy = By.xpath("//tbody/tr");

    // Functions
    public OrderHistoryPage(WebDriver driver, Properties properties) {
        this.driver = driver;
        this.properties = properties;
        setDriver(driver);
        wait = new WebDriverWait(driver, 200);
    }

    public boolean checkOrderInOrderHistory(String referenceNo)
    {
        List<WebElement> ordersList = driver.findElements(ordersListBy);

        for (WebElement webElement : ordersList) {
            WebElement orderReferenceNo = webElement.findElement(By.xpath("descendant::td[1]/a"));
            if (orderReferenceNo.getText().equals(referenceNo)) {
                return true;
            }
        }
        return false;
    }

}

