package com.yosi.testscript;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.yosi.base.YosiBaseClass;
import com.yosi.testcases.DataProviderWithExcel;
import com.yosi.util.Log;

import org.testng.annotations.BeforeClass;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;

public class Past_MediHx_TC extends YosiBaseClass{
	
	YosiBaseClass yosibaseclass;
	BaseTC base_tc;
	DataProviderWithExcel data;
	ExtentReports extent;
	ExtentTest logger;
	
	 @BeforeClass
	  public void beforeClass() throws InterruptedException {
		 
		 extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/PastMedicalHxExtentReport.html", true);
		 
		 extent
		                .addSystemInfo("Host Name", "SoftwareTestingMaterial")
		                .addSystemInfo("Environment", "Automation Testing");
		                
		                
		                extent.loadConfig(new File(System.getProperty("user.dir")+"D:\\Automation testing\\YOSI_Desktop_App\\extent-config.xml"));
		 
		 desktop_init();
		  start_paperwork();
		  verify_patient();	
		  patient_info();
		  patient_guarantor();
		  insurance_info();
		  pat_add_info();
		  emergency_contact();
	  }

	
  @Test(priority = 0)
  public void patient_lifestyle() {
	  
	  System.out.println("PATIENT LIFESTYLE");
		Log.info("PATIENT LIFESTYLE");
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		logger = extent.startTest("Patient LifeStyle page"); 
		 if(driver.findElement(By.xpath("//*[@id=\"lifestyle\"]/div[2]/div[4]/div/input")).isDisplayed() == true)
		 {
				driver.findElement(By.xpath("//*[@id=\"lifestyle\"]/div[2]/div[4]/div/input")).click();
				logger.log(LogStatus.PASS, "Test Case is Passed");
		 }
		 else
		 {
			 logger.log(LogStatus.FAIL, "Test Case is Failed ");
		 }
		 extent.endTest(logger);
		 
  }
		
  
  @Test(priority = 1)
  public void patient_past_medical()
  {
	  System.out.println("PATIENT PAST MEDICAL HX");
		Log.info("PATIENT PAST MEDICAL HX");
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//*[@id=\"pastmedicalhistory_add\"]")).click();
		
		List<WebElement> medical_list = driver.findElements(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li"));
		int count1=driver.findElements(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li")).size();
		 Log.info("Past Medicalhistory List \n");
		for(WebElement e : medical_list) {
			 Log.info(e.getText());
			}
		Log.info("Past Medicalhistory Selected \n");
		for (int i=1;i<=5;i++)
		{
			driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li["+i+"]")).click();
			Log.info(driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li["+i+"]")).getText());
		}
		driver.findElement(By.xpath("//*[@id=\"add_medi_data1\"]")).click();
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(0,1000);");

		
	/*	
		
			driver.findElement(By.xpath("//*[@id=\"pastmedicalhistory_add\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"pastmedicalhistory_search\"]")).sendKeys("a");
			driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li[1]")).click();
			driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li[2]")).click();
			driver.findElement(By.xpath("//*[@id=\"add_medi_data1\"]")).click();
		
		
		
			driver.findElement(By.xpath("//*[@id=\"surgicalhistory_add\"]")).click();
			
			driver.findElement(By.xpath("//*[@id=\"select-surgicalhistory\"]/li[4]")).click();
			driver.findElement(By.xpath("//*[@id=\"select-surgicalhistory\"]/li[7]")).click();
			driver.findElement(By.xpath("//*[@id=\"surgicalhistory_dataadd\"]")).click();
			
		
			driver.findElement(By.xpath("//*[@id=\"allergies_add\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"select_allergies\"]/li[6]")).click();
			driver.findElement(By.xpath("//*[@id=\"select_allergies\"]/li[11]")).click();
			driver.findElement(By.xpath("//*[@id=\"allergies_dataadd\"]")).click();
			
		
			driver.findElement(By.xpath("//*[@id=\"familyhistory_add\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"select-familyhistory\"]/li[3]/label")).click();
			//*[@id="fhh_1151"]/li[1]/span[1]
	*/	
		
		 
		
		

 
	
}
  
 
  @AfterClass
  public void afterClass() throws InterruptedException {
	  

	  logger = extent.startTest("PATIENT PAST MEDICAL HX test");
	  if(driver.findElement(By.xpath("//*[@id=\"page_submitbtn\"]/div/input")).isDisplayed() == true)
		 {
				driver.findElement(By.xpath("//*[@id=\"page_submitbtn\"]/div/input")).click();
				logger.log(LogStatus.PASS, "Test Case is Passed");
		 }
		 else
		 {
			 logger.log(LogStatus.FAIL, "Test Case is Failed ");
		 }
		 extent.endTest(logger);
	   patient_rx_info();
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
