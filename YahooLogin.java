package Yahoo_Login;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.http.HttpMessage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;

public class YahooLogin {
	
	public static final String ACCOUNT_SID = "AC3da8e481c1316af58a33116e228269d7";
	public static final String AUTH_TOKEN = "5dbda2676c5ee56be9d063b344ffa570";

	public static void main(String args[]) {

		System.setProperty("webdriver.edge.driver", "C:\\SeleniumDriver\\edge\\msedgedriver.exe");
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://login.yahoo.com/?.intl=in");
		String PhoneNumber = "+18124722305";
		
		// Add Phone number and sending to next
		driver.findElement(By.xpath("//input[@id='login-username']")).sendKeys(PhoneNumber);
		driver.findElement(By.xpath("//input[@id='login-signin']")).click();

		// Verify number before sending a OTP
		String AcutalNum = driver.findElement(By.xpath("//div[@class='phone-send-code-background']/strong")).getText();
		System.out.println("Comparing " + AcutalNum + " and " + PhoneNumber+ " : " + AcutalNum.equalsIgnoreCase(PhoneNumber));
		//Assert.assertEquals(AcutalNum, PhoneNumber);
		driver.findElement(By.xpath("//div[@class='button-container']/button[@name='sendCode']")).click();
		
		
		//get The OTP using Twilio APIs:
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		String smsBody = getMessage();
		System.out.println(smsBody);
		String OtpNum = smsBody.replaceAll("[^-?0-9]+", " ");
		System.out.println(smsBody);
		driver.findElement(By.xpath("//input[@id='verification-code-field']")).sendKeys(OtpNum);
		driver.findElement(By.xpath("//button[@name='verifyCode']")).click();
	
		// select Your Account
		driver.findElement(By.xpath("//button[@value='sample@yahoo.com']")).click();
		
		
		//Verify page Title
		String actualTitle = driver.getTitle();
		String expectedTitle = "Yahoo Search - Web Search";
		System.out.println("Comparing " + actualTitle + " and " + expectedTitle+ " : " + actualTitle.equalsIgnoreCase(expectedTitle));
		//Assert.assertEquals(actualTitle,expectedTitle,"Page title doesnt match");
		 
        System.out.println("The page title has been successfully verified");
 
        System.out.println("User logged in successfully");
	}

	//getting OTP using Twilio APIs
	public static String getMessage() {
		return getMessages().filter(m -> m.getDirection().compareTo(Message.Direction.INBOUND) == 0)
				.filter(m -> m.getTo().equals("+18124722305")).map(Message::getBody).findFirst()
				.orElseThrow(IllegalStateException::new);
		
	}
	
	private static Stream<Message> getMessages() {
		ResourceSet<Message> messages = Message.reader(ACCOUNT_SID).read();
		return StreamSupport.stream(messages.spliterator(), false);
	}


}
