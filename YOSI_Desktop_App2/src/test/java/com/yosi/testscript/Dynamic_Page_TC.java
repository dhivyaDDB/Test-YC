package com.yosi.testscript;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.yosi.base.YosiBaseClass;
import com.yosi.testcases.DataProviderWithExcel;
import com.yosi.util.Log;

import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterClass;

public class Dynamic_Page_TC extends YosiBaseClass {
	
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
		  patient_rx_info();
		 
	  }
	 @Test (priority = 0)
	 public void addition_page()

	 {
	 	System.out.println("ADDITIONAL SOCIAL HISTORY");
	 	Log.info("ADDITIONAL SOCIAL HISTORY");
	 	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[1]/div/div[1]")).click();
	 	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	 	logger = extent.startTest("Additional Social history page"); 
	 	 if(driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[8]/div/input")).isDisplayed() == true)
	 	 {
	 			driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[8]/div/input")).click();
	 			logger.log(LogStatus.PASS, "Test Case is Passed");
	 	 }
	 	 else
	 	 {
	 		 logger.log(LogStatus.FAIL, "Test Case is Failed ");
	 	 }
	 	 extent.endTest(logger);
	 }
	 


		@Test (priority = 1)
		public void medical_history_page()

		{
			System.out.println("MEDICAL HISTORY FORM");
		Log.info("MEDICAL HISTORY FORM");
		driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[1]/div/div[5]")).click();
		Log.info("Selected value :\n"+driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[1]/div/div[5]")).getText());
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		logger = extent.startTest("Medical History page"); 
		 if(driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[3]/div/input")).isDisplayed() == true)
		 {
				driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[3]/div/input")).click();
				logger.log(LogStatus.PASS, "Test Case is Passed");
		 }
		 else
		 {
			 logger.log(LogStatus.FAIL, "Test Case is Failed ");
		 }
		 extent.endTest(logger);
		
		}

		@Test (priority = 2)
		public void phq_page()

		{
			System.out.println("PHQ-9");
			Log.info("PHQ-9");
			driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[3]/div/div[2]")).click();
			Log.info("Selected value :\n"+driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[3]/div/div[2]")).getText());
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(0,3000);");
			logger = extent.startTest("PHQ page"); 
			 if(driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[14]/div/input")).isDisplayed() == true)
			 {
					driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[14]/div/input")).click();
					logger.log(LogStatus.PASS, "Test Case is Passed");
			 }
			 else
			 {
				 logger.log(LogStatus.FAIL, "Test Case is Failed ");
			 }
			 extent.endTest(logger);
			
			
			
}

		@Test (priority = 3)
		public void gad_page()

		{
			System.out.println("GAD-7");
			Log.info("GAD-7");
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(0,2000);");
			driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
			logger = extent.startTest("GAD-7"); 
			 if(driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[11]/div/input")).isDisplayed() == true)
			 {
					driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[11]/div/input")).click();
					logger.log(LogStatus.PASS, "Test Case is Passed");
			 }
			 else
			 {
				 logger.log(LogStatus.FAIL, "Test Case is Failed ");
			 }
			 extent.endTest(logger);
			
			
}
		@Test (priority = 4)
		public void encounter_page()

		{
			System.out.println("ENCOUNTER QUESTIONNAIRE ");
			Log.info("ENCOUNTER QUESTIONNAIRE ");
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(0,1000);");
			driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);

			logger = extent.startTest("ENCOUNTER QUESTIONNAIRE"); 
			 if(driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[9]/div/input")).isDisplayed() == true)
			 {
					driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[9]/div/input")).click();
					logger.log(LogStatus.PASS, "Test Case is Passed");
			 }
			 else
			 {
				 logger.log(LogStatus.FAIL, "Test Case is Failed ");
			 }
			 extent.endTest(logger);
			
			
}

	/*	@Test (priority = 5)
		public void ros_page()

		{
		
			driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[2]/span/label")).click();
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[9]/span/label")).click();
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[18]/span/label")).click();
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[43]/span/label")).click();
	JavascriptExecutor jse = (JavascriptExecutor)driver;
	jse.executeScript("scroll(0,6000);");
	logger = extent.startTest("ROS Page"); 
	 if(driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[51]/div/input")).isDisplayed() == true)
	 {
			driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[51]/div/input")).click();
			logger.log(LogStatus.PASS, "Test Case is Passed");
	 }
	 else
	 {
		 logger.log(LogStatus.FAIL, "Test Case is Failed ");
	 }
	 extent.endTest(logger);
	
	 extent.close();
			
		}*/



  @AfterClass
  public void afterClass() throws InterruptedException {
	  
	  	
	  	  policy1_page();
	  	  policy2_page();
	  	  signature_page();
	  	  driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS); 
	  	  extent.endTest(logger);
	  	  extent.flush();
	  	  extent.close();
	  	  driver.quit();
	    }
}
