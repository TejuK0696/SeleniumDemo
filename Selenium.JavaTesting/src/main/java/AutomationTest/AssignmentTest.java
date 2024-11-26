package AutomationTest;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;


public class AssignmentTest {
	WebDriver driver;
	
	
	public AssignmentTest(WebDriver driver) {
        this.driver = driver;  
	}
	String divTextElement = "//div[text()='%s']";
	String cptCheckbox = "//p[text()='%s']//..//input[@type='checkbox']";

	public void HomePageHeader(String headerName) {
        String xpathHeader = String.format(divTextElement, headerName);
        driver.findElement(By.xpath(xpathHeader)).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
	}
    
	public void scrollToBottom(double value) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollTo(0, document.body.scrollHeight *" + value +")");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
	
	
	public void SliderImplementation(int sliderValueSwipe) {
		WebElement slider = driver.findElement(By.xpath("//span[contains(@class,'MuiSlider-root')]"));
		WebElement sliderTextInput = driver.findElement(By.xpath("//input[@type='number']"));
		
		int sliderWidth = slider.getSize().getWidth();
		int maxSliderValue = Integer.parseInt(sliderTextInput.getAttribute("max"));
        int minSliderValue = Integer.parseInt(sliderTextInput.getAttribute("min"));
		int targetValue = 820;
		int xOffset = (sliderWidth * (targetValue - minSliderValue)) / (maxSliderValue - minSliderValue);

		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].value = arguments[1]", sliderTextInput, sliderValueSwipe);
		
		
		String sliderValue = sliderTextInput.getAttribute("value");
		if (sliderValue.equals("820")) {
			System.out.println("Slider value is successfully updated to " + targetValue + "...!");
			
		}else {
			System.out.println("Failed to set the slider value. Current value:" + sliderValue);
		}
		
	}
	

	public void UpdateSliderFromTextField(int targetValue) {
		WebElement sliderTextInput = driver.findElement(By.xpath("//input[@type='number']"));
		sliderTextInput.click();
		sliderTextInput.clear();
		sliderTextInput.sendKeys(String.valueOf(targetValue));
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
	    js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));", sliderTextInput, targetValue);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		String sliderValue = sliderTextInput.getAttribute("value");
		if (sliderValue.equals(String.valueOf(targetValue))) {
			System.out.println("Slider value is successfully updated to " + targetValue + "...!");
			
		}else {
			System.out.println("Failed to set the slider value. Current value:" + sliderValue);
		}
		
	}
	
	public void ValidateTheSliderUser(int targetValue) {
	    WebElement slider = driver.findElement(By.xpath("//span[contains(@class,'MuiSlider-root')]"));
	    WebElement sliderTextInput = driver.findElement(By.xpath("//input[@type='number']"));
	    WebElement sliderBar = driver.findElement(By.xpath("//span[contains(@class, 'MuiSlider-rail')]"));

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    double sliderBarLeft = Double.parseDouble(js.executeScript("return arguments[0].getBoundingClientRect().left;", sliderBar).toString());
	    double sliderBarWidth = sliderBar.getSize().getWidth();
	    double maxSliderValue = Double.parseDouble(sliderTextInput.getAttribute("max"));
	    double minSliderValue = Double.parseDouble(sliderTextInput.getAttribute("min"));

	    double expectedSliderPosition = sliderBarLeft + (sliderBarWidth * (targetValue - minSliderValue)) / (maxSliderValue - minSliderValue);

	    js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));", sliderTextInput, targetValue);
	    double sliderPosition = Double.parseDouble(js.executeScript("return arguments[0].getBoundingClientRect().left;", slider).toString());

	    if (Math.abs(sliderPosition - expectedSliderPosition) < 1) {  
	        System.out.println("Slider position is correctly updated to reflect the value: " + targetValue);
	        System.out.println("Expected position: " + expectedSliderPosition + ", Actual position: " + sliderPosition);
	    } else {
	        System.out.println("Slider position validation failed.");
	        System.out.println("Expected position: " + expectedSliderPosition + ", Actual position: " + sliderPosition);
	    }


	    int actualValue = Integer.parseInt(sliderTextInput.getAttribute("value").split("\\.")[0]); 
	    if (actualValue == targetValue) {
	        System.out.println("Slider value is correctly updated to: " + targetValue);
	    } else {
	        System.out.println("Slider value validation failed. Expected: " + targetValue + ", Actual: " + actualValue);
	    }
	}
	
	public void CPTCheckBox(String cptValue) {
		String cptXpath = String.format(cptCheckbox, cptValue);
	      driver.findElement(By.xpath(cptXpath)).click();
	}
	
	public void ToSelectCPT() throws InterruptedException {
		scrollToBottom(0.3);
		Thread.sleep(2000);
		CPTCheckBox("CPT-99091");
		CPTCheckBox("CPT-99453");
		CPTCheckBox("CPT-99454");
		scrollToBottom(0.3);
		CPTCheckBox("CPT-99474");
		Thread.sleep(5000);

	}
	
	public void validateTotalReimbursement(String expectedValue) {
	    try {
	        WebElement reimbursementHeader = driver.findElement(By.xpath("(//p[contains(text(),'Total Recurring Reimbursement')])[1]"));

	        String fullText = reimbursementHeader.getText();
	        System.out.println("Full text from header: " + fullText);

	        String actualValue = fullText.replace("Total Recurring Reimbursement for all Patients Per Month:", "").trim();
	        System.out.println("Extracted value: " + actualValue);

	        Assert.assertEquals(actualValue, expectedValue, "Reimbursement value mismatch!");

	        System.out.println("Validation Successful! The displayed reimbursement value is: " + actualValue);
	    } catch (Exception e) {
	        System.out.println("Validation Failed! Unable to verify the reimbursement value.");
	        e.printStackTrace();
	    }
	
	 }
}
