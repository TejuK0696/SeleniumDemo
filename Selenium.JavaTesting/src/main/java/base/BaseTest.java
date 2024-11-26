package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import AutomationTest.AssignmentTest;


public class BaseTest {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.fitpeo.com/");
        driver.manage().window().fullscreen();
    }
    
    @Test
    public void Testing() throws InterruptedException{
    	AssignmentTest testPage = new AssignmentTest(driver);
    	testPage.HomePageHeader("Revenue Calculator");
    	testPage.scrollToBottom(0.1);
    	testPage.SliderImplementation(820);
    	testPage.UpdateSliderFromTextField(560);
    	testPage.ValidateTheSliderUser(560);
    	testPage.ToSelectCPT();
    	testPage.validateTotalReimbursement("$270000");
	
    }
    
  
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
