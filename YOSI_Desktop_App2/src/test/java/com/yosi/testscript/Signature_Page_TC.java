package com.yosi.testscript;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.yosi.base.YosiBaseClass;
import com.yosi.util.Log;

import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterClass;

public class Signature_Page_TC extends YosiBaseClass{
	
	@BeforeClass
	  public void beforeClass() throws InterruptedException {
		  
			 
			  desktop_init();
			  start_paperwork();
			  verify_patient();	
			  patient_info();
			  patient_guarantor();
			  insurance_info();
			  pat_add_info();
			  emergency_contact();
			  patient_lifestyle();
			  patient_past_medical();
			  patient_rx_info();
			  addition_page();
			  medical_history_page();
			  phq_page();
			  gad_page();
			  encounter_page();
			  //ros_page();
			  policy1_page();
			  policy2_page();
	  }
	  
	@Test
	public void signature_page()

	{

		System.out.println("Signature Page");
		Log.info("Signature Page");
		driver.findElement(By.xpath("//*[@id=\"reasonforvisit\"]")).sendKeys("Fever");
		Log.info("Reason entered: \n"+driver.findElement(By.xpath("//*[@id=\"reasonforvisit\"]")).getAttribute("value"));
		driver.manage().timeouts().implicitlyWait(50000,TimeUnit.SECONDS);
		//*[@id="ctlSignature_Container"]
		driver.findElement(By.xpath("//*[@id=\"ctlSignature\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"ctlSignature\"]")).click();
		JavascriptExecutor jse1 = (JavascriptExecutor)driver;
		jse1.executeScript("scroll(0, 500);");
		
		
	}
 

  @AfterClass
  public void afterClass() {
	  
	  logger = extent.startTest("Signature Page Test"); 
		 if(driver.findElement(By.xpath("//*[@id=\"addappointment\"]/div/div[11]/div/input")).isDisplayed() == true)
		 {
				driver.findElement(By.xpath("//*[@id=\"addappointment\"]/div/div[11]/div/input")).click();
				logger.log(LogStatus.PASS, "Test Case is Passed");
		 }
		 else
		 {
			 logger.log(LogStatus.FAIL, "Test Case is Failed ");
		 }
		 extent.endTest(logger);
	  		
	  
	  	  driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS); 
	  	
	  	  extent.flush();
	  	  extent.close();
	  	  driver.quit();
	    }
  

}
