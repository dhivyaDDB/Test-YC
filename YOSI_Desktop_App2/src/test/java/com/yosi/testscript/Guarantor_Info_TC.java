package com.yosi.testscript;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.yosi.base.YosiBaseClass;
import com.yosi.pom.Patient_AddInfo_POM;
import com.yosi.pom.Patient_Info_POM;
import com.yosi.testcases.DataProviderWithExcel;
import com.yosi.util.ExcelUtils;
import com.yosi.util.Log;
import com.yosi.util.Validator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterClass;

public class Guarantor_Info_TC extends YosiBaseClass {
	YosiBaseClass yosibaseclass;
	BaseTC base_tc;
	DataProviderWithExcel data;
	
	  @BeforeClass
	  public void beforeClass() throws InterruptedException 
	  {
		  extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/GuarantorExtentReport.html", true);
			 
			 extent
			                .addSystemInfo("Host Name", "SoftwareTestingMaterial")
			                .addSystemInfo("Environment", "Automation Testing");		                
			                extent.loadConfig(new File(System.getProperty("user.dir")+"D:\\Automation testing\\YOSI_Desktop_App\\extent-config.xml"));
		  desktop_init();
		  start_paperwork();
		  verify_patient();	
		  patient_info();
	  }
	  
	  @DataProvider
	  public Object[][] dp() throws Exception{
		 
		 String file_location = "D:/Automation testing/YOSI_Desktop_App/src/main/java/com/yosi/testdata/TestData.xlsx";
		 String SheetName = "Guantor_info";
		 
		 Object[][] testObjArray = ExcelUtils.getTableArray(file_location,SheetName);
		  
	     return (testObjArray);
	  }
  @Test  (dataProvider = "dp")
  
  public void  patient_guarantor(String s_address1,String s_address2,String s_zipcode) 
  {
	  
	  			// GUARANTOR INFORMATION PAGE
	  			System.out.println("The PATIENT GUARANTOR  page");
		
	  			System.out.println("Radio button values "+driver.findElement(By.id("select_patientsame_access_wrap")).getText());
	  			String g_fname=driver.findElement(By.id("firstname")).getAttribute("value");
	  			System.out.println("Guarantor Fname "+g_fname);
	  			String g_lname = driver.findElement(By.id("lastname")).getAttribute("value");
	  			System.out.println("Guarantor Lname "+g_lname);
	  			System.out.println("Guarantor Relationship "+driver.findElement(By.id("select2-select_emc_relationship-container")).getAttribute("value"));
	  			JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("scroll(0, 500);");
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"form_parentguarantor_info\"]/div/div[8]/div/div/label")).click();			
				driver.findElement(By.id("address1")).click();
				driver.findElement(By.id("address1")).clear();
				driver.findElement(By.id("address1")).sendKeys(s_address1);
				driver.findElement(By.id("address2")).click();
				driver.findElement(By.id("address2")).clear();
				driver.findElement(By.id("address2")).sendKeys(s_address2);
				driver.findElement(By.id("zipcode")).click();
				driver.findElement(By.id("zipcode")).clear();	
				driver.findElement(By.id("zipcode")).sendKeys(s_zipcode);
				String add1 = driver.findElement(By.id("address1")).getAttribute("value");
				String add2 = driver.findElement(By.id("address2")).getAttribute("value");
				String zip =driver.findElement(By.id("zipcode")).getAttribute("value");
				Log.info("Address of the patient "+add1+ "," +add2+","+zip+".");
				
				String zipcode_ele =driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).getAttribute("value");
				 
				logger = extent.startTest("Zipcode Validation In GUARANTOR INFO PAGE");
				
				String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
				Pattern pattern = Pattern.compile(regex);

				    Matcher matcher = pattern.matcher(zipcode_ele);
				    Boolean zip_match=matcher.matches();
				    System.out.println(matcher.matches());
				
				 System.out.println("Zipcode"+zipcode_ele);
					
					if (zip_match == true )
					{
						logger.log(LogStatus.PASS, "The US ZIP-"+zipcode_ele+" Code is valid");
					}
					
					else
					{
						logger.log(LogStatus.FAIL, "The US ZIP-"+zipcode_ele+" Code is invalid");
					}
					
					 extent.endTest(logger);
				
				driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);

				
  }


  @AfterClass
  public void afterClass() throws InterruptedException {
	  		logger = extent.startTest("Guantor Info page"); 
		 if(driver.findElement(By.xpath("//*[@id=\"form_parentguarantor_info\"]/div/div[11]/div/input")).isDisplayed() == true)
		 {
				driver.findElement(By.xpath("//*[@id=\"form_parentguarantor_info\"]/div/div[11]/div/input")).click();
				logger.log(LogStatus.PASS, "Test Case is Passed");
		 }
		 else
		 {
			 logger.log(LogStatus.FAIL, "Test Case is Failed ");
		 }
		 extent.endTest(logger);
		  
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
