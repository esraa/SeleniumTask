package base;

import org.apache.log4j.Logger;
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
	private static Logger logger = Logger.getLogger(PageBase.class);

	public void setDriver(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 200);
	}

	public Boolean isElementDisplayed(By byElement, String elementLog) {

		wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
		logger.info(elementLog + " is visible");
		return driver.findElement(byElement).isDisplayed();
	}

	public void setElement(By byElement, String value, String elementLog) {

		boolean isDisplayed = isElementDisplayed(byElement, elementLog);
		if (isDisplayed) {
			WebElement element = driver.findElement(byElement);
			element.clear();
			element.sendKeys(value);
			logger.info("Set " + elementLog + " : " + value);
		}
	}

	public void selectItemFromList(By byElement, String value, String elementLog) {

		new Select(driver.findElement(byElement)).selectByVisibleText(value);
		logger.info("Select from list " + elementLog + " : " + value);
	}

	public void clickElement(By byElement, String elementLog) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
		driver.findElement(byElement).click();
		logger.info("Click on " + elementLog);
	}

	public void clickElement(WebElement element, String elementLog) {
		if(element.isDisplayed())
		{
			element.click();
			logger.info("Click on " + elementLog);
		}
		else {
			logger.error("Cannot Click on " + elementLog);
		}

	}

	public String getElementText(By byElement, String elementLog) {

		String elementText;
		if (isElementDisplayed(byElement, elementLog)) {
			elementText = driver.findElement(byElement).getText();
			logger.info(elementLog + " Actual text is " + elementText);
			return elementText;
		} else {
			logger.error(elementLog + " text is not Displayed");
			return "";
		}
	}

	public void jsClick(By byElement, String elementLog) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", driver.findElement(byElement));
		logger.info("Click on " + elementLog);
	}

	public void hoverElement(WebElement element, String elementLog) {

		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
		logger.info("Hover on " + elementLog);
	}

}
