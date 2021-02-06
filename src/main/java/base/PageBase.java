package base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class PageBase {

	WebDriver driver;
	private WebDriverWait wait;

	public void setDriver(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 200);
	}

	public Boolean isElementDisplayed(By byElement) {

		wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
		return driver.findElement(byElement).isDisplayed();
	}

	public void setElement(By byElement, String value) {

		boolean isDisplayed = isElementDisplayed(byElement);
		if (isDisplayed) {
			WebElement element = driver.findElement(byElement);
			element.clear();
			element.sendKeys(value);
		}
	}

	public void selectItemFromList(By byElement, String value) {

		new Select(driver.findElement(byElement)).selectByVisibleText(value);
	}

	public void clickElement(By byElement) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
		driver.findElement(byElement).click();
	}

	public void clickElement(WebElement element) {
		if(element.isDisplayed())
		{
			element.click();
		}
	}

	public String getElementText(By byElement) {

		String elementText;
		if (isElementDisplayed(byElement)) {
			elementText = driver.findElement(byElement).getText();
			return elementText;
		} else {
			return "";
		}
	}

	public void jsClick(By byElement) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", driver.findElement(byElement));
	}

	public void hoverElement(WebElement element) {

		Actions action = new Actions(driver);
		action.moveToElement(element).perform();

	}

}
