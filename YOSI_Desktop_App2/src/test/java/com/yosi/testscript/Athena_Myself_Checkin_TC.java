package com.yosi.testscript;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.yosi.base.YosiBaseClass;
import com.yosi.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Athena_Myself_Checkin_TC extends YosiBaseClass {

	public WebDriver driver;
	public static ExtentReports extent;
	public static ExtentTest logger;
	
	
	Properties prop1 = new Properties();
	JavascriptExecutor jse = (JavascriptExecutor)driver;
	
	@BeforeClass
	 public void beforeClass() {
		  
			File file = new File("D:/Automation testing/YOSI_Desktop_App/src/main/java/com/yosi/config/config.properties");
			FileInputStream fileInput = null;
			try {
				fileInput = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			try {
				prop1.load(fileInput);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/REGExtentReport.html", true);
			extent
			                .addSystemInfo("Host Name", "SoftwareTestingMaterial")
			                .addSystemInfo("Environment", "Automation Testing");
			extent.loadConfig(new File(System.getProperty("user.dir")+"D:\\Automation testing\\YOSI_Desktop_App\\extent-config.xml"));
			
			DOMConfigurator.configure("log4j.xml");
			System.setProperty("driver.chrome.driver","D:\\Automation testing\\AutomationTestingPR\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.get(prop1.getProperty("url"));
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
	  }

 @Test(priority = 0)
 public void start_paperwork_type1() 
 {
	  //START PAPERWORK
		String apt_time= driver.findElement(By.xpath("/html/body/div/div[2]/div")).getText();
		System.out.println(apt_time);
		//Log.info("Appoinment Time" +apt_time);
		
		logger = extent.startTest("Start Paperwork"); 
		 if(driver.findElement(By.className("start_ppr")).isDisplayed())
		 {
			 driver.findElement(By.className("start_ppr")).click();
			logger.log(LogStatus.PASS, "Test Case is Passed");
		 }
		 else
		 {
			 logger.log(LogStatus.FAIL, "Test Case is Failed ");
		 }
		 extent.endTest(logger);
		 
 }

@Test(priority = 1)

public void select_doctor_type1() throws InterruptedException
{
	
	WebElement doc = driver.findElement(By.xpath("//*[@id=\"form_mchoosedoctor_info\"]/div/ul/li[2]/a"));
	Actions action =    new Actions(driver);
   Thread.sleep(2000);
   action.click(doc).perform();
   Log.info("Doctor is selected");
   driver.manage().timeouts().implicitlyWait(15000,TimeUnit.SECONDS);
	//Select the dependent
   
   	logger = extent.startTest("Select Doctor test"); 
	 if(driver.findElement(By.xpath("//*[@id=\"clkadult\"]/div/div")).isDisplayed() == true)
	 {
			driver.findElement(By.xpath("//*[@id=\"clkadult\"]/div/div")).click();
			logger.log(LogStatus.PASS, "Test Case is Passed");
	 }
	 else
	 {
		 logger.log(LogStatus.FAIL, "Test Case is Failed ");
	 }
	 extent.endTest(logger);
	 
	Log.info("Adult is clicked");
}

@Test(priority = 2)

public void verify_page()
{
	// VERIFY THE PATIENT
	 //Log.info("Patient verification Page");
	driver.manage().timeouts().implicitlyWait(8000,TimeUnit.SECONDS);
	driver.findElement(By.xpath("//*[@id=\"verify_date\"]")).sendKeys(prop1.getProperty("dob"));
	driver.findElement(By.xpath("//*[@id=\"select_gender_wrap\"]")).click();
	Select se = new Select(driver.findElement(By.xpath("//*[@class=\"yc-input customdd select2-hidden-accessible\"]")));
	if (prop1.getProperty("gender")=="Male")
	{
		se.selectByIndex(0);
	}
	else
	{
		se.selectByIndex(1);
	}
	driver.findElement(By.xpath("//*[@id=\"select_gender_wrap\"]")).click();
	//Log.info("Patient is verified");
	logger = extent.startTest("Patient Verify page"); 
	 if(driver.findElement(By.xpath("//*[@id=\"proceed_login\"]")).isDisplayed() == true)
	 {
			driver.findElement(By.xpath("//*[@id=\"proceed_login\"]")).click();
			logger.log(LogStatus.PASS, "Test Case is Passed");
	 }
	 else
	 {
		 logger.log(LogStatus.FAIL, "Test Case is Failed ");
	 }
	 extent.endTest(logger);
	 
	
}




@Test (priority = 3)
public void patient_info_type1() {
		//TIENT INFORMATION
			Log.info("PATIENT INFORMATION");
			System.out.println("PATIENT INFORMATION");
			String fname = driver.findElement(By.xpath("//*[@id=\"firstname\"]")).getAttribute("value");
			System.out.println(fname);
			Log.info("Fname of the patient" +fname);
			String lname = driver.findElement(By.xpath("//*[@id=\"lastname\"]")).getAttribute("value");
			System.out.println(lname);
			Log.info("Lname of the patient" +lname);
			String gender = driver.findElement(By.xpath("//*[@id=\"select_gender_option1\"]")).getAttribute("value");
			System.out.println(gender);
			String dob = driver.findElement(By.xpath("//*[@id=\"date\"]")).getAttribute("value");
			System.out.println(dob);
			driver.findElement(By.id("address1")).click();
			driver.findElement(By.id("address1")).clear();
			driver.findElement(By.id("address1")).sendKeys("31, River cross road");
			driver.findElement(By.id("address2")).click();
			driver.findElement(By.id("address2")).clear();	
			driver.findElement(By.id("address2")).sendKeys("Texas");
			driver.findElement(By.id("zipcode")).click();
			driver.findElement(By.id("zipcode")).clear();
			driver.findElement(By.id("zipcode")).sendKeys("10036");
			String zipcode_ele =driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).getAttribute("value");
			String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
			
			 
			Pattern pattern = Pattern.compile(regex);
		
		
			    Matcher matcher = pattern.matcher(zipcode_ele);
			    Boolean zip_match=matcher.matches();
			    System.out.println(matcher.matches());
			
			 System.out.println("Zipcode"+zipcode_ele);
			logger = extent.startTest("Zipcode Validation In PATIENT INFO PAGE");
			
			//Boolean zip_match=zipcodevalidator.zipcode_validate(zipcode_ele);
				   
				
				if (zip_match == true )
				{
					logger.log(LogStatus.PASS, "The specified US ZIP Code is valid");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "The specified US ZIP Code is valid");
				}
				
				 extent.endTest(logger);
			 

			logger = extent.startTest("Patient Info page"); 
			 if(driver.findElement(By.xpath("//*[@id=\"form_mpersonal_info\"]/div/div[9]/div[1]/input")).isDisplayed() == true)
			 {
					driver.findElement(By.xpath("//*[@id=\"form_mpersonal_info\"]/div/div[9]/div[1]/input")).click();	
					logger.log(LogStatus.PASS, "Test Case is Passed");
			 }
			 else
			 {
				 logger.log(LogStatus.FAIL, "Test Case is Failed ");
			 }
			 extent.endTest(logger);
			
			
}

@Test (priority = 4)
public void patient_guarantor_type1()
{
	
			// GUARANTOR INFORMATION PAGE
			System.out.println("The PATIENT GUARANTOR  page");
			System.out.println(driver.findElement(By.id("select_patientsame_access_wrap")).getText());
			System.out.println(driver.findElement(By.id("firstname")).getText());
			System.out.println(driver.findElement(By.id("lastname")).getText());
			System.out.println(driver.findElement(By.id("select2-select_emc_relationship-container")).getText());
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(0, 500);");
			driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
			driver.findElement(By.xpath("//*[@id=\"form_parentguarantor_info\"]/div/div[8]/div/div/label")).click();		
			driver.findElement(By.id("address1")).click();
			driver.findElement(By.id("address1")).clear();
			driver.findElement(By.id("address1")).sendKeys("31,green road");
			driver.findElement(By.id("address2")).click();
			driver.findElement(By.id("address2")).clear();
			driver.findElement(By.id("address2")).sendKeys("Dallas");
			driver.findElement(By.id("zipcode")).click();
			driver.findElement(By.id("zipcode")).clear();	
			driver.findElement(By.id("zipcode")).sendKeys("10036");
			String zipcode_ele =driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).getAttribute("value");
			 System.out.println("Zipcode"+zipcode_ele);
			
			System.out.println("Zipcode"+zipcode_ele);
			logger = extent.startTest("Zipcode Validation In GUARANTOR INFO PAGE");
			
			String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
			
			 
			Pattern pattern = Pattern.compile(regex);
		
		
			    Matcher matcher = pattern.matcher(zipcode_ele);
			    Boolean zip_match=matcher.matches();
			    System.out.println(matcher.matches());
			
			 System.out.println("Zipcode"+zipcode_ele);
				
				if (zip_match == true )
				{
					logger.log(LogStatus.PASS, "The specified US ZIP Code is valid");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "The specified US ZIP Code is valid");
				}
				
				 extent.endTest(logger);
			
			driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);

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
					
}

@Test (priority = 5)
public void insurance_info_type1()
{
	//INSURANCE INFORMATION PAGE
			System.out.println("INSURANCE PAGE");
			
			driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);
			driver.findElement(By.id("primary_companyname_wrap")).click();
			driver.findElement(By.id("primary_companyname_wrap")).click();
			driver.findElement(By.xpath("//*[@id=\"primary_companyname\"]")).sendKeys("Aethna");
			driver.findElement(By.xpath("//*[@id=\"primary_policynumber\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"primary_policynumber\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"primary_policynumber\"]")).sendKeys("DPY9261");
			
			//
			driver.findElement(By.xpath("//*[@id=\"form_minsurance_info\"]/div[2]/div/div/div[2]/div/div[1]")).click();

			System.out.println("Secondary INSURANCE ");
	
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredfname\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredfname\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredfname\"]")).sendKeys("FRED");
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredlname\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredlname\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredlname\"]")).sendKeys("TEST");
			driver.findElement(By.xpath("//*[@id=\"secondary_insureddob\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_insureddob\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_insureddob\"]")).sendKeys("12/12/2007");
			driver.findElement(By.xpath("//*[@id=\"secondary_genterradio_wrap\"]/div[1]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).sendKeys("252525252");
			driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);
			driver.findElement(By.xpath("//*[@id=\"secondary_address1\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_address1\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_address1\"]")).sendKeys("21,kindy Apartment");
			driver.findElement(By.xpath("//*[@id=\"secondary_address2\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_address2\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_address2\"]")).sendKeys("DS");
			driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).sendKeys("10036");
			String s_zipcode_ele =driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).getAttribute("value");
			 System.out.println("Zipcode"+s_zipcode_ele);
			
			System.out.println("Zipcode"+s_zipcode_ele);
			logger = extent.startTest("Zipcode Validation In GUARANTOR INFO PAGE");
			
			String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
			
			 
			Pattern pattern = Pattern.compile(regex);
		
		
			    Matcher matcher = pattern.matcher(s_zipcode_ele);
			    Boolean zip_match=matcher.matches();
			    System.out.println(matcher.matches());
			
			 System.out.println("Zipcode"+s_zipcode_ele);
				
				if (zip_match == true )
				{
					logger.log(LogStatus.PASS, "The specified US ZIP Code is valid");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "The specified US ZIP Code is valid");
				}
				
				 extent.endTest(logger);
			
			
			driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);
			
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
			
			
			
			//PATIENT ADDITIONAL INFORMATION
			System.out.println("PATIENT ADDITIONAL INFORMATION");
			
			driver.findElement(By.xpath("//*[@id=\"select_employed_wrap\"]/div[3]/label")).click();
			
			driver.findElement(By.xpath("//*[@id=\"generalinfo\"]/div[3]/div[2]")).click();
			driver.findElement(By.xpath("//*[@id=\"ssn\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"ssn\"]")).sendKeys("444444444");
			//*[@id="mobilenumber"]
			driver.findElement(By.xpath("//*[@id='mobilenumber']")).sendKeys("(503)4008675");
			Select martial_select = new Select(driver.findElement(By.xpath("//*[@id=\"select_maritalstatus\"]")));
			martial_select.selectByIndex(3);
			driver.manage().timeouts().implicitlyWait(8000,TimeUnit.SECONDS);
			
			//*[@id="generalinfo"]/div[3]/div[6]/div[1]/input
			logger = extent.startTest("Patient Additional page"); 
			 if(driver.findElement(By.xpath("//*[@id='generalinfo']/div[3]/div[6]/div[1]/input")).isDisplayed() == true)
			 {
					driver.findElement(By.xpath("//*[@id='generalinfo']/div[3]/div[6]/div[1]/input")).click();
					logger.log(LogStatus.PASS, "Test Case is Passed");
			 }
			 else
			 {
				 logger.log(LogStatus.FAIL, "Test Case is Failed ");
			 }
			 extent.endTest(logger);
			 extent.flush();
			 extent.close();
			
			
			//EMERGENCY CONTACT
			System.out.println("EMERGENCY CONTACT");
			driver.findElement(By.xpath("//*[@id=\"emc_firstname\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"emc_firstname\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"emc_firstname\"]")).sendKeys("Fredy");
			driver.findElement(By.xpath("//*[@id=\"emc_lastname\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"emc_lastname\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"emc_lastname\"]")).sendKeys("test");
			driver.findElement(By.xpath("//*[@id=\"select2-select_emc_relationship-container\"]")).click();
			Select se = new Select(driver.findElement(By.id("select_emc_relationship")));
			se.selectByIndex(2);
			driver.findElement(By.xpath("//*[@id=\"select2-select_emc_relationship-container\"]")).click();		
			driver.findElement(By.xpath("//*[@id=\"emc_number\"]")).sendKeys("5555555555555");
			driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
			logger = extent.startTest("Emergency page"); //*[@id="mycontact"]/div[8]/div/input
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
			
}

@Test (priority = 6)
public void patient_lifestyle()
{
	System.out.println("PATIENT LIFESTYLE");
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
@Test (priority = 7)
public void patient_past_medical_type1() throws InterruptedException
{
	System.out.println("PATIENT PAST MEDICAL HX");
/*	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	Boolean b_pastmed = driver.findElement(By.xpath("//*[@id=\"pastmedicalhistory_add\"]")).isEnabled();
	if (b_pastmed == true)
	{
	//Yes Button
	driver.findElement(By.xpath("//*[@id=\"pastmedicalhistory_add\"]")).click();
	//select-pastmedicalcondition1
	List<WebElement> medical_list = driver.findElements(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li"));
	int count1=driver.findElements(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li")).size();

	for(WebElement e : medical_list) {
		  System.out.println(e.getText());
		}
	for (int i=1;i<=count1-45;i++)
	{
		driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li["+i+"]")).click();
		//pastmedicalnote_5012
		//driver.findElement(By.id(""))
		
	}
	
	driver.findElement(By.xpath("//*[@id=\"pastmedicalhistory_search\"]")).sendKeys("a");
	driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li[1]")).click();
	driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li[2]")).click();
	driver.findElement(By.xpath("//*[@id=\"add_medi_data1\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"add_medi_data1\"]")).click();
	}

	driver.findElement(By.xpath("//*[@id=\"pastmedicalhistory_search\"]")).sendKeys("a");
	driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li[1]")).click();
	driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li[2]")).click();
	driver.findElement(By.xpath("//*[@id=\"add_medi_data1\"]")).click();
	
	}
	else
	{
	driver.findElement(By.xpath("//*[@id=\"medicalinfo_edit\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"pastmedicalhistory_search\"]")).sendKeys("b");
	driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li[7]")).click();
	driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li[6]")).click();
	
	}
	
	Boolean b_surg = driver.findElement(By.xpath("//*[@id=\"surgicalhistory_add\"]")).isDisplayed();
	if (b_surg == true)
	{
		driver.findElement(By.xpath("//*[@id=\"surgicalhistory_add\"]")).click();
	}
	else
	{
		driver.findElement(By.xpath("//*[@id=\"surgicalhistory_edit\"]")).click();
	}
	//*[@id="surgicalhistory_add"]
	//*[@id="surgicalhistory_add"]
	driver.findElement(By.xpath("//*[@id='surgicalhistory_add']")).click();
	//*[@id="surgicalhistory_search"]
	driver.findElement(By.xpath("//*[@id=\"select-surgicalhistory\"]/li[4]")).click();
	driver.findElement(By.xpath("//*[@id=\"select-surgicalhistory\"]/li[7]")).click();
	driver.findElement(By.xpath("//*[@id=\"surgicalhistory_dataadd\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"allergies_add\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"select_allergies\"]/li[6]")).click();
	driver.findElement(By.xpath("//*[@id=\"select_allergies\"]/li[11]")).click();
	driver.findElement(By.xpath("//*[@id=\"allergies_dataadd\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"familyhistory_none\"]")).click();
	//driver.findElement(By.xpath("//*[@id=\"vaccinations_none\"]")).click();
*/	logger = extent.startTest("Patient Past History page"); 
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
	
}



@Test (priority = 8)
public void patient_rx_info()

{
	System.out.println("PATIENT RX INFO");
	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	driver.findElement(By.xpath("//*[@id=\"select_medication1_wrap\"]")).click();
	
	List<WebElement> medication_list = driver.findElements(By.xpath("//div[@class='option']"));
/*	for(WebElement e : medication_list) {
		  System.out.println(e.getText());
		}*/
	Random ran = new Random();
	int i =2;
	//i=ran.nextInt();
	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	driver.findElement(By.xpath("//*[@id=\"select_medication1_wrap\"]/div[1]/div[2]/div/div["+i+"]")).click();
	//driver.findElement(By.xpath("//*[@id=\"select_medication1_wrap\"]/div[1]/div[2]/div/div["+ran.nextInt()+"]")).click();
	
	
	driver.findElement(By.xpath("//*[@id=\"add_medi_data\"]")).click();
	logger = extent.startTest("Patient Past History page"); 
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
	
	
}

@Test (priority = 9)
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


@Test (priority = 10)
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

@Test (priority = 11)
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

@Test (priority = 12)
public void gad_page()

{
	System.out.println("GAD-7");
	Log.info("GAD-7");
	JavascriptExecutor jse = (JavascriptExecutor)driver;
	jse.executeScript("scroll(0,2000);");
	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	logger = extent.startTest("GAD-7"); 
	// if(driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[11]/div/input")).isDisplayed() == true)
	// {
			driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[11]/div/input")).click();
			//logger.log(LogStatus.PASS, "Test Case is Passed");
	// }
	// else
	// {
	//	 logger.log(LogStatus.FAIL, "Test Case is Failed ");
	// }
	// extent.endTest(logger);
	
	
}
@Test (priority = 13)
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
//VITALS
@Test (priority = 14)
public void vital_page()

{
	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	driver.findElement(By.xpath("//*[@name='Vitals']")).click();
	//driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[3]/div/div[1]/div[1]/div[1]/div")).sendKeys("4",Keys.ENTER);
}






@Test (priority = 15)
public void ros_page()

{
	
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[2]/span/label")).click();
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[4]/div/div[2]/label")).click();
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[7]/div/div[2]/label")).click();
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[10]/div/div[2]/label")).click();
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
	
	
}


@Test (priority = 16)
public void athena_cust_question_page()

{
	
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[4]/div/div[3]/label")).click();
	
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[5]/div/div[1]/label")).click();
	JavascriptExecutor jse = (JavascriptExecutor)driver;
	jse.executeScript("scroll(0,6000);");
	
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[14]/div/input")).click();
	
}

@Test (priority = 17)
public void language_page()

{
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[3]/div/input")).click();
}

@Test (priority = 18)

public void obgyn_question_page()

{
	driver.findElement(By.xpath("//*[@id=\"answer_2588_pckr\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"answer_2588_pckr\"]")).clear();
	driver.findElement(By.xpath("//*[@id=\"answer_2588_pckr\"]")).sendKeys("03/14/2000");
	driver.findElement(By.xpath("//*[@id=\"answer_2589\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"answer_2589\"]")).clear();
	driver.findElement(By.xpath("//*[@id=\"answer_2589\"]")).sendKeys("34");
	driver.findElement(By.xpath("//*[@id=\"answer_2590\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"answer_2590\"]")).clear();
	driver.findElement(By.xpath("//*[@id=\"answer_2590\"]")).sendKeys("67");
	
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[26]/div/input")).click();
	
	
}

@Test (priority = 19)
public void notice_of_privacy_page()

{
	System.out.println("NOTICE_OF_PRIVACY_PRACTICES ");
	Log.info("NOTICE_OF_PRIVACY_PRACTICES ");
	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	driver.findElement(By.xpath("//*[@id=\"agreeNOTICE_OF_PRIVACY_PRACTICES\"]")).click();
					

}

@Test (priority = 20)
public void privacy_policy_page() throws InterruptedException

{
	System.out.println("PRIVACY POLICY ");
	Log.info("PRIVACY POLICY");
	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	
		 WebElement doc = driver.findElement(By.xpath("//*[@id=\"declinePrivacy_Policy\"]"));
			Actions action =    new Actions(driver);
		   Thread.sleep(2000);
		   action.click(doc).perform();	
}

@Test (priority = 21)
public void notice_of_privacy_page2()

{
	System.out.println("NOTICE_OF_PRIVACY_PRACTICES ");
	Log.info("NOTICE_OF_PRIVACY_PRACTICES ");
	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	driver.findElement(By.xpath("//*[@id=\"agreeNOTICE_OF_PRIVACY_PRACTICES\"]")).click();
					

}

@Test (priority = 22)
public void assignment_of_Benefits_page() throws InterruptedException

{
	System.out.println("Assignment_of_Benefits");
	Log.info("Assignment_of_Benefits");
	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	
		 WebElement doc = driver.findElement(By.xpath("//*[@id=\"agreeAssignment_of_Benefits\"]"));
			Actions action =    new Actions(driver);
		   Thread.sleep(2000);
		   action.click(doc).perform();	
}



/*@Test (priority = 17)
public void skip_page()

{
	//CREDIT CARD INFO
	driver.findElement(By.xpath("//*[@id=\"remove_contract\"]")).click();
	
}*/

@Test (priority = 20)
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
	logger = extent.startTest("Signature Page"); 
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
	
}

@AfterClass
 public void afterClass() 
{
	extent.flush();
	extent.close();
	 driver.quit();
}

}
