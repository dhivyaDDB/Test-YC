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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;

public class Emergency_Cont_TC extends YosiBaseClass {
	YosiBaseClass yosibaseclass;
	BaseTC base_tc;
	DataProviderWithExcel data;


	@BeforeClass
	  public void beforeClass() throws InterruptedException 
	  {
		extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/EmergencyExtentReport.html", true);
		extent
		                .addSystemInfo("Host Name", "SoftwareTestingMaterial")
		                .addSystemInfo("Environment", "Automation Testing");
		extent.loadConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
		  desktop_init();
		  start_paperwork();
		  verify_patient();	
		  patient_info();
		  patient_guarantor();
		  insurance_info();
		  pat_add_info();
		  emergency_contact();
	  }
	  
	  @DataProvider
	  public Object[][] dp() throws Exception{
		 
		 String file_location = "D:/Automation testing/YOSI_Desktop_App/src/main/java/com/yosi/testdata/TestData.xlsx";
		 String SheetName = "Emergency_Con";
		 
		 Object[][] testObjArray = ExcelUtils.getTableArray(file_location,SheetName);
		  
	     return (testObjArray);
	  }
 @Test  (dataProvider = "dp")

public void  emergency_info_test(String emc_firstname, String emc_lastname, String emc_number)
{
	  

	//EMERGENCY CONTACT
	//EMERGENCY CONTACT
		System.out.println("EMERGENCY CONTACT");
		driver.findElement(By.xpath("//*[@id=\"emc_firstname\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"emc_firstname\"]")).clear();
		driver.findElement(By.xpath("//*[@id=\"emc_firstname\"]")).sendKeys(emc_firstname);
		Log.info("EMC Fname: \n"+driver.findElement(By.xpath("//*[@id=\"emc_firstname\"]")).getAttribute("value"));
		
		driver.findElement(By.xpath("//*[@id=\"emc_lastname\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"emc_lastname\"]")).clear();
		driver.findElement(By.xpath("//*[@id=\"emc_lastname\"]")).sendKeys(emc_lastname);
		Log.info("EMC Lname: \n"+driver.findElement(By.xpath("//*[@id=\"emc_lastname\"]")).getAttribute("value"));
		driver.findElement(By.xpath("//*[@id=\"select2-select_emc_relationship-container\"]")).click();
		Select se = new Select(driver.findElement(By.id("select_emc_relationship")));
		se.selectByIndex(2);
		driver.findElement(By.xpath("//*[@id=\"select2-select_emc_relationship-container\"]")).click();		
		driver.findElement(By.xpath("//*[@id=\"emc_number\"]")).sendKeys(emc_number);
		Log.info("EMC Contact Num : \n"+driver.findElement(By.xpath("//*[@id=\"emc_number\"]")).getAttribute("value"));
		 
		 
		 
			String mob_number1  =driver.findElement(By.xpath("//*[@id=\"emc_number\"]")).getAttribute("value");
			 System.out.println("Mobile Number"+mob_number1);
			
			
			logger = extent.startTest("Mobile number");
			
			String regex12 = "^([0-9]{3})[0-9]{3}-[0-9]{4}$";
			
			 
			Pattern pattern12 = Pattern.compile(regex12);
		
		
			    Matcher matcher12 = pattern12.matcher(mob_number1);
			    Boolean e_mob_match=matcher12.matches();
			    System.out.println(matcher12.matches());
			
			 System.out.println("Mobile Number"+mob_number1);
				
				if (e_mob_match == true )
				{
					logger.log(LogStatus.PASS, "Mobile number  is valid");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "Mobile number is invalid");
				}
				
				 extent.endTest(logger);
		
}


@AfterClass
public void afterClass() throws InterruptedException {
	

	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	logger = extent.startTest("Emergency page"); 
	 if(driver.findElement(By.xpath("//*[@id=\"mycontact\"]/div[8]/div/input")).isDisplayed() == true)
	 {
			driver.findElement(By.xpath("//*[@id=\"mycontact\"]/div[8]/div/input")).click();
			logger.log(LogStatus.PASS, "Test Case is Passed");
	 }
	 else
	 {
		 logger.log(LogStatus.FAIL, "Test Case is Failed ");
	 }
	 extent.endTest(logger);


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
extent.endTest(logger);
extent.flush();
extent.close();
driver.quit();

}


}
