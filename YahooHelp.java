package YahooDemo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;

public class YahooHelp {
	
	By HelpButton   = By.xpath("//a[text()='Help']");
	By SearchBar = By.xpath("//input[@id='searchInput']");
	By SubmitSearch = By.xpath("//input[@id='search-submit']");
	By ListOfSearch = By.xpath("//div[@id='help-articles']/article/h1");
	String Title1 = "Fix issues with Yahoo Account Key";
	String Title2 = "Add, change, or remove a recovery method";
	String Title3 = "Add two-step verification for extra security";
	String Title4 = "Set up, use, and manage Yahoo Account Key to sign in without a password";
	String Title5 = "Fix problems with iOS apps";
	String Title6 = "Sign up for a Yahoo account";
	String Title7 = "Get help from Yahoo customer support";
	String Title8 = "Why do I need to provide recovery contact info?";
	String Title9 = "Fix problems signing into your Yahoo account";
	String Title10 = "Secure your Yahoo account ";
	WebDriver driver;
	public void setUp() {
		

		System.setProperty("webdriver.edge.driver", "C:\\SeleniumDriver\\edge\\msedgedriver.exe");
		driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://login.yahoo.com/?.intl=in");
		driver.findElement(HelpButton).click();
		
		// Verify Page Title
		String actualTitle = driver.getTitle();
		String expectedTitle = "Help for your Yahoo Account";
		Assert.assertEquals(actualTitle,expectedTitle,"Page title doesnt match");
        System.out.println("The page title has been successfully verified");
        
        //Search Login by Phone
        driver.findElement(SearchBar).sendKeys("Login by Phone");
        driver.findElement(SubmitSearch).click();
        String actualTitleAfterSearch = driver.getTitle();
        String expectedTitleAfterSearch = "Helpcentral | Login+by+Phone - Search Results";
        Assert.assertEquals(actualTitleAfterSearch,expectedTitleAfterSearch,"Page title doesnt match");
        System.out.println("The page title has been successfully verified");
        
        // Verify all search list
        VerifyAllSearchList();
 
	}
	
	public void  VerifyAllSearchList() {
		String[] expected = {Title1,Title2,Title3,Title4,Title5,Title6,Title7,Title8,Title9,Title10};
		List<WebElement> allOptions = driver.findElements(ListOfSearch);

		//Validate if Search result is displayed corresponding to the string which was searched
		for (int i = 0; i < expected.length; i++) {
		    String optionValue = allOptions.get(i).getText();
		    if (optionValue.equals(expected[i])) {
		        System.out.println("passed on: " + optionValue);
		    } else {
		        System.out.println("failed on: " + optionValue);
		    }
		}
	}
}
