package com.yosi.testscript;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.yosi.base.YosiBaseClass;
import com.yosi.testcases.DataProviderWithExcel;
import com.yosi.util.ExcelUtils;
import com.yosi.util.Log;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;

public class Insurance_Page_TC extends YosiBaseClass{
	YosiBaseClass yosibaseclass;
	BaseTC base_tc;
	DataProviderWithExcel data;


	@BeforeClass
	  public void beforeClass() throws InterruptedException 
	  
	  {
		extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/InsuranceExtentReport.html", true);
		 
		 extent
		                .addSystemInfo("Host Name", "SoftwareTestingMaterial")
		                .addSystemInfo("Environment", "Automation Testing");
		                
		                
		                extent.loadConfig(new File(System.getProperty("user.dir")+"D:\\Automation testing\\YOSI_Desktop_App\\extent-config.xml"));
		  desktop_init();
		  start_paperwork();
		  verify_patient();	
		  patient_info();
		  patient_guarantor();
		
	  }
	  
	  @DataProvider
	  public Object[][] dp() throws Exception{
		 
		 String file_location = "D:/Automation testing/YOSI_Desktop_App/src/main/java/com/yosi/testdata/TestData.xlsx";
		 String SheetName = "P_Insurance_page";
		 
		 Object[][] testObjArray = ExcelUtils.getTableArray(file_location,SheetName);
		  
	     return (testObjArray);
	  }
 @Test  (dataProvider = "dp")

public void  insurance_info_test(String p_companyname, String p_policynumber,String s_insuredfname,String s_insuredlname, String s_insureddob,String s_ssn,
		String s_address1,String s_address2,String s_zipcode)
{
	  
	//INSURANCE INFORMATION PAGE
			System.out.println("INSURANCE PAGE");
			
		
			driver.findElement(By.id("primary_companyname_wrap")).click();
			driver.findElement(By.id("primary_companyname_wrap")).click();
			driver.findElement(By.id("primary_companyname_wrap")).clear();
			driver.findElement(By.xpath("//*[@id=\"primary_companyname\"]")).sendKeys(p_companyname);
			driver.findElement(By.xpath("//*[@id=\"primary_policynumber\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"primary_policynumber\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"primary_policynumber\"]")).sendKeys(p_policynumber);
			
			//
			driver.findElement(By.xpath("//*[@id=\"form_minsurance_info\"]/div[2]/div/div/div[2]/div/div[1]")).click();

		

			driver.findElement(By.xpath("//*[@id=\"secondary_insuredfname\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredfname\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredfname\"]")).sendKeys(s_insuredfname);
			String s_fname=driver.findElement(By.xpath("//*[@id=\"secondary_insuredfname\"]")).getAttribute("value");
			System.out.println(s_fname);
			Log.info("First Name of the patient " +s_fname);
			
			
			String regx = "[a-zA-Z]+\\.?";
		    Pattern f_pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
		    Matcher f_matcher = f_pattern.matcher(s_fname);
		    Boolean f_bol =f_matcher.find();
		    System.out.println(f_matcher.find()); 
			logger = extent.startTest("First Name Validation In INSURANCE  PAGE");
			Log.info(" First Name Validation In PINSURANCE  PAGE");

				
				if ( f_bol == true )
				{
					logger.log(LogStatus.PASS, "The name "+s_fname+"  is contains char only");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "The name "+s_fname+" is Invalid");
				}
				
				 extent.endTest(logger);
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredlname\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredlname\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredlname\"]")).sendKeys(s_insuredlname);
			String s_lname=driver.findElement(By.xpath("//*[@id=\"secondary_insuredlname\"]")).getAttribute("value");
			System.out.println(s_lname);
			Log.info("Last Name of the patient " +s_lname);
			
			
			
		    Pattern l_pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
		    Matcher l_matcher = l_pattern.matcher(s_lname);
		    Boolean l_bol =l_matcher.find();
		    System.out.println(l_matcher.find()); 
			logger = extent.startTest("Last Name Validation In PINSURANCE  PAGE");
			Log.info(" First Name Validation In INSURANCE  PAGE");

				
				if ( l_bol == true )
				{
					logger.log(LogStatus.PASS, "The name "+s_lname+"  is contains char only");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "The name "+s_lname+" is Invalid");
				}
				
				 extent.endTest(logger);
			driver.findElement(By.xpath("//*[@id=\"secondary_insureddob\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_insureddob\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_insureddob\"]")).sendKeys(s_insureddob);
			String s_dob  =driver.findElement(By.xpath("//*[@id=\"secondary_insureddob\"]")).getAttribute("value");
			 System.out.println("DOB"+s_dob);
			
			
			logger = extent.startTest("DOB validation");
			
			String dob_regex = "^((?:0[0-9])|(?:[1-2][0-9])|(?:3[0-1]))/((?:0[1-9])|(?:1[0-2]))/((?:19|20)\\d{2})$";
			
			 
			Pattern dob_pattern = Pattern.compile(dob_regex);
		
		
			    Matcher dob_matcher = dob_pattern.matcher(s_dob);
			    Boolean dob_match=dob_matcher.matches();
			    System.out.println(dob_matcher.matches());
			
			 System.out.println("DOB"+s_dob);
				
				if (dob_match == true )
				{
					logger.log(LogStatus.PASS, "DOB is valid"+s_dob);
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "DOB is invalid"+s_dob);
				}
				
				extent.endTest(logger);
				driver.findElement(By.xpath("//*[@id=\"secondary_genterradio_wrap\"]/div[1]")).click();
				driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).clear();
				//driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).click();
				driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).sendKeys(s_ssn);
				String se_ssn =driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).getAttribute("value");
				System.out.println("SSN"+se_ssn);
			
			
			logger = extent.startTest("SSN Validation");
			
			String ssn_regex = "^[0-9]{3}[-]([0-9]{2})[-]([0-9]{4})$";
			
			 
			Pattern pattern1 = Pattern.compile(ssn_regex);
		
		
			    Matcher matcher1 = pattern1.matcher(se_ssn);
			    Boolean ssn_match=matcher1.matches();
			    System.out.println(matcher1.matches());
			
			    System.out.println("SSN"+se_ssn);
				
				if (ssn_match == true )
				{
					logger.log(LogStatus.PASS, "SSN is valid"+se_ssn);
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "SSN is invalid"+se_ssn);
				}
				
				 extent.endTest(logger);
			
			driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);
			driver.findElement(By.xpath("//*[@id=\"secondary_address1\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_address1\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_address1\"]")).sendKeys(s_address1);
			String s_addr1=driver.findElement(By.xpath("//*[@id=\"secondary_address1\"]")).getAttribute("value");
			driver.findElement(By.xpath("//*[@id=\"secondary_address2\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_address2\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_address2\"]")).sendKeys(s_address2);
			String s_addr2=driver.findElement(By.xpath("//*[@id=\"secondary_address2\"]")).getAttribute("value");
			driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).sendKeys(s_zipcode);
			String s_zip=driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).getAttribute("value");
			Log.info("Secondary Address entered \n" +s_addr1+","+s_addr2+","+s_zip+".");
			
			String zipcode_ele =driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).getAttribute("value");
			 
			logger = extent.startTest("Zipcode Validation In INSURANCE  PAGE");
			
			String zip_regex = "^[0-9]{5}(?:-[0-9]{4})?$";
			Pattern pattern = Pattern.compile(zip_regex);

			    Matcher matcher = pattern.matcher(zipcode_ele);
			    Boolean zip_match=matcher.matches();
			    System.out.println(matcher.matches());
			
			 System.out.println("Zipcode"+zipcode_ele);
				
				if (zip_match == true )
				{
					logger.log(LogStatus.PASS, "The entered "+zipcode_ele+" ZIP Code is valid");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "The entered "+zipcode_ele+" ZIP Code is invalid");
				}
				
				 extent.endTest(logger);
			
			driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
			//driver.findElement(By.xpath("//*[@id=\"form_minsurance_info\"]/div[3]/div/div/div/div[1]")).click();
			
}


@AfterClass
public void afterClass() throws InterruptedException {

	logger = extent.startTest("Insurance page"); 
	 if(driver.findElement(By.xpath("//*[@id=\"form_minsurance_info\"]/div[3]/div/div/div/div[1]")).isDisplayed() == true)
	 {
			driver.findElement(By.xpath("//*[@id=\"form_minsurance_info\"]/div[3]/div/div/div/div[1]")).click();
			logger.log(LogStatus.PASS, "Test Case is Passed");
	 }
	 else
	 {
		 logger.log(LogStatus.FAIL, "Test Case is Failed ");
	 }
	 extent.endTest(logger);
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
//driver.quit();

}


}
