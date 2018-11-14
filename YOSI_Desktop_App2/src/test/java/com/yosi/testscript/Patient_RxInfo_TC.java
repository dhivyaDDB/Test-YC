package com.yosi.testscript;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.yosi.base.YosiBaseClass;
import com.yosi.testcases.DataProviderWithExcel;
import com.yosi.util.Log;

import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;

public class Patient_RxInfo_TC extends YosiBaseClass {
	
	
	YosiBaseClass yosibaseclass;
	BaseTC base_tc;
	DataProviderWithExcel data;
	ExtentReports extent;
	ExtentTest logger;
	
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
	  }

  @Test
  public void patient_rx_info() {
	  
	  System.out.println("PATIENT RX INFO");
		Log.info("PATIENT RX INFO");
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"select_medication1_wrap\"]")).click();
		
		List<WebElement> medication_list = driver.findElements(By.xpath("//div[@class='option']"));
		 Log.info("Medication list");
		/*for(WebElement e : medication_list) {
			 Log.info(e.getText());
			}*/
		Random ran = new Random();
		int i =2;
		//i=ran.nextInt();
		driver.manage().timeouts().implicitlyWait(15000,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"select_medication1_wrap\"]/div[1]/div[2]/div/div["+i+"]")).click();
		Log.info("Medication Selected : \n"+driver.findElement(By.xpath("//*[@id=\"select_medication1_wrap\"]/div[1]/div[2]/div/div["+i+"]")).getText());
		
		
		
		driver.findElement(By.xpath("//*[@id=\"add_medi_data\"]")).click();
		
  }
 
  @AfterClass
  public void afterClass() throws InterruptedException {
	  
	  logger = extent.startTest("\"PATIENT RX INFO page"); 
		 if(driver.findElement(By.xpath("//*[@id=\"pharmacy\"]/div[7]/div/input")).isDisplayed() == true)
		 {
				driver.findElement(By.xpath("//*[@id=\"pharmacy\"]/div[7]/div/input")).click();
				logger.log(LogStatus.PASS, "Test Case is Passed");
		 }
		 else
		 {
			 logger.log(LogStatus.FAIL, "Test Case is Failed ");
			 
		 }
		 extent.endTest(logger);
		
		  addition_page();
		  medical_history_page();
		  phq_page();
		  gad_page();
		  encounter_page();
		  ros_page();
		  policy1_page();
		  policy2_page();
		  signature_page();
		  driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS); 
		 
		  extent.flush();
		  extent.close();
		  driver.quit();
	  
  }

}
