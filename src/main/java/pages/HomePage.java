package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Properties;

public class HomePage extends PageBase {

    // Variables
    private WebDriver driver;
    private Properties properties;
    private WebDriverWait wait;

    // Define the By Elements
    By signIn = By.cssSelector("a[class='login']");
    By categoryListBy = By.xpath("//div[@id='block_top_menu']/ul/li");
    By categoryTitleBy = By.xpath("//div[@class='cat_desc']/span");

    // Functions
    public HomePage(WebDriver driver, Properties properties) {
        this.driver = driver;
        this.properties = properties;
        setDriver(driver);
        wait = new WebDriverWait(driver, 200);
    }

    public void clickOnSignIn()
    {
        clickElement(signIn, "sign in");

    }

    public boolean selectItemFromMenu(String category, String subcategory)
    {
        List<WebElement> categoriesList = driver.findElements(categoryListBy);

        for (WebElement webElement : categoriesList) {
            WebElement cat = webElement.findElement(By.xpath("descendant::a"));
            if (cat.getText().equalsIgnoreCase(category)) {
                hoverElement(cat, category);
                WebElement subCat = webElement.findElement(By.xpath("descendant::ul/li/a[@title='" + subcategory + "']"));
                clickElement(subCat, subcategory);
                break;
            }
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(categoryTitleBy));
        WebElement catDesc = driver.findElement(categoryTitleBy);
        return catDesc.getText().trim().equalsIgnoreCase(subcategory);

    }
}

