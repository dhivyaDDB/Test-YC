package com.yosi.testscript;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.yosi.base.YosiBaseClass;
import com.yosi.util.Log;

import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;

public class Policy_Page_TC extends YosiBaseClass {
	
 
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
  }
  
  
	@Test (priority = 0)
	public void policy1_page()

	{
		System.out.println("POILCY PAGE ");
		Log.info("POILCY PAGE ");
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);

		driver.findElement(By.xpath("//*[@id=\"dynamicinfo_38\"]/div[2]/div/div/a[1]")).click();
						
		
	}

	@Test (priority = 1)
	public void policy2_page() throws InterruptedException

	{
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		WebElement doc = driver.findElement(By.xpath("//*[@id=\"dynamicinfo_39\"]/div[2]/div/div/a[2]"));
		Actions action =    new Actions(driver);
		Thread.sleep(2000);
		action.click(doc).perform();	
	}

  @AfterClass
  public void afterClass() throws InterruptedException {
	  
	  	  signature_page();
	  	  driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS); 
	  	
	  	  extent.flush();
	  	  extent.close();
	  	  driver.quit();
	  close();
  }

}
