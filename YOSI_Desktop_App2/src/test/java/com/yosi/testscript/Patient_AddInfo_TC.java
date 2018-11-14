package com.yosi.testscript;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.yosi.base.YosiBaseClass;
import com.yosi.testcases.DataProviderWithExcel;
import com.yosi.util.ExcelUtils;
import com.yosi.util.Log;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;

public class Patient_AddInfo_TC extends YosiBaseClass{
	ExtentReports extent;
	ExtentTest logger;
	YosiBaseClass yosibaseclass;
	BaseTC base_tc;
	DataProviderWithExcel data;
	
	 @BeforeClass
	  public void beforeClass() throws InterruptedException {
		  
		  desktop_init();
		  start_paperwork();
		  verify_patient();	
		  patient_info();
		  patient_guarantor();
		  insurance_info();
	  }
	 
	  @DataProvider
	  public Object[][] dp() throws Exception{
		 
		 String file_location = "D:/Automation testing/YOSI_Desktop_App/src/main/java/com/yosi/testdata/TestData.xlsx";
		 String SheetName = "Addi_info";
		 
		 Object[][] testObjArray = ExcelUtils.getTableArray(file_location,SheetName);
		 // System.out.println(testObjArray);
	     return (testObjArray);
	   
	  }
 @Test 
  public void patient_add_info() {
	  
	//PATIENT ADDITIONAL INFORMATION
		System.out.println("PATIENT ADDITIONAL INFORMATION");
		Log.info("PATIENT ADDITIONAL INFORMATION");
		driver.findElement(By.xpath("//*[@id=\"select_employed_wrap\"]/div[3]/label")).click();
		
		driver.findElement(By.xpath("//*[@id=\"generalinfo\"]/div[3]/div[2]")).click();
		driver.findElement(By.xpath("//*[@id=\"ssn\"]")).clear();
		driver.findElement(By.xpath("//*[@id=\"ssn\"]")).sendKeys("444444444");
		Log.info("SSN entered : \n"+driver.findElement(By.xpath("//*[@id=\"ssn\"]")).getAttribute("value"));
		String ssn_ele =driver.findElement(By.xpath("//*[@id=\"ssn\"]")).getAttribute("value");
		 System.out.println("SSN"+ssn_ele);
		
		
		logger = extent.startTest("SSN Validation");
		
		String regex1 = "^[0-9]{3}[-]([0-9]{2})[-]([0-9]{4})$";
		
		 
		Pattern p_pattern = Pattern.compile(regex1);
	
	
		    Matcher matcher_ssn = p_pattern.matcher(ssn_ele);
		    Boolean p_ssn_match=matcher_ssn.matches();
		    System.out.println(matcher_ssn.matches());
		
		    System.out.println("SSN"+p_ssn_match);
			
			if (p_ssn_match == true )
			{
				logger.log(LogStatus.PASS, "SSN is valid");
			}
			
			else
			{
				logger.log(LogStatus.FAIL, "SSN is invalid");
			}
			
			 extent.endTest(logger);
			 driver.findElement(By.xpath("//*[@id=\"mobilenumber_home\"]")).click();
			 driver.findElement(By.xpath("//*[@id=\"mobilenumber_home\"]")).clear();
		driver.findElement(By.xpath("//*[@id=\"mobilenumber_home\"]")).sendKeys("(503)4008675");
		Log.info("Mobile number entered : \n"+driver.findElement(By.xpath("//*[@id=\"mobilenumber_home\"]")).getAttribute("value"));
		String mob_number  =driver.findElement(By.xpath("//*[@id=\"mobilenumber_home\"]")).getAttribute("value");
		 System.out.println("Mobile Number"+mob_number);
		
		
		logger = extent.startTest("Mobile number");
		
		String regex11 = "^([0-9]{3})||[0-9]{3}[-][0-9]{4}$";
		
		 
		Pattern pattern11 = Pattern.compile(regex11);
	
	
		    Matcher matcher11 = pattern11.matcher(mob_number);
		    Boolean mob_match=matcher11.matches();
		    System.out.println(matcher11.matches());
		
		 System.out.println("Mobile Number"+mob_number);
			
			if (mob_match == true )
			{
				logger.log(LogStatus.PASS, "Mobile number  is valid");
			}
			
			else
			{
				logger.log(LogStatus.FAIL, "Mobile number is invalid");
			}
			
			 extent.endTest(logger);
		List<WebElement> martial_list = driver.findElements(By.xpath("//*[@id=\"select_maritalstatus\"]"));
		for(WebElement m_list : martial_list) {
			 Log.info("Martial Drop Down value:\n"+m_list.getText());
			}
		Select martial_select = new Select(driver.findElement(By.xpath("//*[@id=\"select_maritalstatus\"]")));
		martial_select.selectByIndex(3);
		driver.manage().timeouts().implicitlyWait(8000,TimeUnit.SECONDS);
	
		
	  
  }
 

  @AfterClass
  public void afterClass() throws InterruptedException {
	  
	  logger = extent.startTest("Patient Additional page"); 
		 if(driver.findElement(By.xpath("//*[@id=\"generalinfo\"]/div[3]/div[7]/div[1]/input")).isDisplayed() == true)
		 {
				driver.findElement(By.xpath("//*[@id=\"generalinfo\"]/div[3]/div[7]/div[1]/input")).click();
				logger.log(LogStatus.PASS, "Test Case is Passed");
		 }
		 else
		 {
			 logger.log(LogStatus.FAIL, "Test Case is Failed ");
		 }
		 extent.endTest(logger);
		 
		 emergency_contact();
		 patient_lifestyle();
		 patient_past_medical();
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
		 
	  driver.quit();
	  extent.flush();
	  extent.close();
  }

  

}
