package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Properties;

public class ProductsPage extends PageBase {

    // Variables
    private WebDriver driver;
    private Properties properties;
    private WebDriverWait wait;

    // Define the By Elements
    By productListBy = By.xpath("//ul[@class='product_list grid row']/li");

    // Functions
    public ProductsPage(WebDriver driver, Properties properties) {
        this.driver = driver;
        this.properties = properties;
        setDriver(driver);
        wait = new WebDriverWait(driver, 200);
    }


    public boolean selectMoreDetailsForProduct(String product)
    {
        List<WebElement> productsList = driver.findElements(productListBy);
        int i=0;
        for (WebElement webElement : productsList) {
            WebElement productItem = webElement.findElement(By.xpath("descendant::div[@class='right-block']//a[@title='"+product+"']"));
            if (productItem.getText().equalsIgnoreCase(product)) {
                hoverElement(productItem);
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//ul[@class='product_list grid row']/li["+(i+1)+"]//div[@class='right-block']//a[contains(@class, 'lnk_view')]")));

                WebElement more = webElement.findElement(By.xpath("descendant::div[@class='right-block']//a[contains(@class, 'lnk_view')]"));
                clickElement(more);
                break;
            }
            i++;
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'pb-center-column')]/h1")));
        WebElement productTitle = driver.findElement(By.xpath("//div[contains(@class, 'pb-center-column')]/h1"));
        return productTitle.getText().trim().equalsIgnoreCase(product);

    }
}

