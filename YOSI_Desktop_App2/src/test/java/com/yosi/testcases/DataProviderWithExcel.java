package com.yosi.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.yosi.pom.Patient_AddInfo_POM;
import com.yosi.util.ExcelUtils;
import com.yosi.util.Log;

public class DataProviderWithExcel {
	public WebDriver driver;
	ExtentReports extent;
	ExtentTest logger;
	Patient_AddInfo_POM patient_info;
	

	Properties prop1 = new Properties();
	JavascriptExecutor jse = (JavascriptExecutor)driver;
	
	@BeforeClass
	  public void beforeClass() {
		
		extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/STMExtentReport.html", true);
		extent
              .addSystemInfo("Host Name", "SoftwareTestingMaterial")
              .addSystemInfo("Environment", "Automation Testing");
              extent.loadConfig(new File(System.getProperty("user.dir")+"D:\\Automation testing\\YOSI_Desktop_App\\extent-config.xml"));
		  
		  File file = new File("D:/Automation testing/AutomationTestingPR/src/main/java/com/yosi/config/config.properties");
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
			
			
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			System.setProperty("driver.chrome.driver","D:/Automation testing/YOSI_Desktop_App/chromedriver.exe");
			driver = new ChromeDriver();
			driver.get(prop1.getProperty("url"));
			logger = extent.startTest("Landing page test");
			if(driver.getTitle().equals("Landing Page"))
			{
				logger.log(LogStatus.PASS, "Navigated to the specified URL");
			}
			else
			{
				logger.log(LogStatus.FAIL, "Test Failed");
			}
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
	  }
	@AfterMethod
	public void getResult(ITestResult result){
		if(result.getStatus() == ITestResult.FAILURE){
			logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());
		}else if(result.getStatus() == ITestResult.SKIP){
			logger.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
		}
		// ending test
		//endTest(logger) : It ends the current test and prepares to create HTML report
		extent.endTest(logger);
	}
	
	  @Test(priority = 0)
	  public void start_paperwork() 
	  {
		  //START PAPERWORK
			String apt_time= driver.findElement(By.xpath("/html/body/div/div[2]/div")).getText();
			
			System.out.println(apt_time);
			Log.info("Appoinment Time" +apt_time);
			logger = extent.startTest("Start Paperwork test");
			  try
	           {
				 
				  driver.findElement(By.className("start_ppr")).click(); 
				  logger.log(LogStatus.PASS, "Test Case is Passed ");
	           } 
	           catch(Exception e)
	           {
	        	   logger.log(LogStatus.FAIL, "Test Case is Failed ");
	           }

			
	  }
	  
	@Test(priority = 1)
	

	public void select_doctor() throws InterruptedException
	{
		
		WebElement doc = driver.findElement(By.xpath("//*[@id=\"form_mchoosedoctor_info\"]/div/ul/li[2]/a"));
		
		Assert.assertTrue(true);
		logger.log(LogStatus.PASS, "Test Case is Passed ");
		Actions action =    new Actions(driver);
	    //action.moveToElement(menu).perform();
	    Thread.sleep(2000);
	    action.click(doc).perform();
	    Log.info("Doctor is selected");
	    driver.manage().timeouts().implicitlyWait(15000,TimeUnit.SECONDS);
		//Select the dependent
	    
	    logger = extent.startTest("Select doctor test");
		  try
         {
			 
			  driver.findElement(By.xpath("//*[@id=\"clkadult\"]/div/div")).click();
			  logger.log(LogStatus.PASS, "Test Case is Passed ");
         } 
         catch(Exception e)
         {
      	   logger.log(LogStatus.FAIL, "Test Case is Failed ");
         }
		
		 Log.info("Adult is clicked");
	}
	
	@Test(priority = 2)

	public void verify_page()
	{
		// VERIFY THE PATIENT
		driver.manage().timeouts().implicitlyWait(8000,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"verify_date\"]")).sendKeys(prop1.getProperty("dob"));
		driver.findElement(By.xpath("//*[@id=\"select_gender_wrap\"]")).click();
		Select se = new Select(driver.findElement(By.xpath("//*[@class=\"yc-input customdd select2-hidden-accessible\"]")));
		if (prop1.getProperty("gender")=="Female")
		{
			se.selectByIndex(2);
		}
		else
		{
			se.selectByIndex(1);
		}
		driver.findElement(By.xpath("//*[@id=\"select_gender_wrap\"]")).click();
		Log.info("Patient is verified");
		logger = extent.startTest("Verify patient test");
		  try
         {
			 
			  driver.findElement(By.xpath("//*[@id=\"proceed_login\"]")).click();
			  logger.log(LogStatus.PASS, "Test Case is Passed ");
         } 
         catch(Exception e)
         {
      	   logger.log(LogStatus.FAIL, "Test Case is Failed ");
         }
		driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);
		
		
		
	}
	
	  @DataProvider
	  public Object[][] dp() throws Exception{
		 
		 String file_location = "D:/Automation testing/YOSI_Desktop_App/src/main/java/com/yosi/testdata/TestData.xlsx";
		 String SheetName = "Patient_info";
		 
		 Object[][] testObjArray = ExcelUtils.getTableArray(file_location,SheetName);
		  
	     return (testObjArray);
	     
	  }
	    
	
	@Test (priority = 3)
	public void patient_info() {
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
				driver.findElement(By.id("address1")).sendKeys("87th floor Kid apart");
				Log.info("Address1 "+driver.findElement(By.id("address1")).getText());
				driver.findElement(By.id("address2")).click();
				driver.findElement(By.id("address2")).clear();	
				driver.findElement(By.id("address2")).sendKeys("TX");
				Log.info("Address1 "+driver.findElement(By.id("address2")).getText());
				driver.findElement(By.id("zipcode")).click();
				driver.findElement(By.id("zipcode")).clear();
				driver.findElement(By.id("zipcode")).sendKeys("10004");
				Log.info("Address1 "+driver.findElement(By.id("zipcode")).getText());
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
				Log.info("PATIENT INFORMATION IS FILLED");
				

				logger = extent.startTest("PATIENT INFORMATION TEST");
			  try
	           {
				 
				  driver.findElement(By.xpath("//form[@id='form_mpersonal_info']/div/div[7]/div/input")).click();	
				  logger.log(LogStatus.PASS, "Test Case is Passed ");
	           } 
	           catch(Exception e)
	           {
	        	   logger.log(LogStatus.FAIL, "Test Case is Failed ");
	           }
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	}
	
	@Test (priority = 4)
	public void patient_guarantor()
	{
		
				// GUARANTOR INFORMATION PAGE
				System.out.println("The PATIENT GUARANTOR  page");
				System.out.println(driver.findElement(By.id("select_patientsame_access_wrap")).getText());
				Log.info("");
				System.out.println(driver.findElement(By.id("firstname")).getText());
				Log.info("First name"+driver.findElement(By.id("firstname")).getText());
				System.out.println(driver.findElement(By.id("lastname")).getText());
				Log.info("Last Name"+driver.findElement(By.id("lastname")).getText());
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

				logger = extent.startTest("PATIENT GUARANTOR test");
			  try
	           {
				 
				  driver.findElement(By.xpath("//*[@id=\"form_parentguarantor_info\"]/div/div[11]/div/input")).click();
				  logger.log(LogStatus.PASS, "Test Case is Passed ");
	           } 
	           catch(Exception e)
	           {
	        	   logger.log(LogStatus.FAIL, "Test Case is Failed ");
	           }
				
				driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);
				
				
	}

	@Test (priority = 5)
	public void insurance_info()
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
		
		driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);
		
		driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"form_minsurance_info\"]/div[3]/div/div/div/div[1]")).click();
		
		
		//PATIENT ADDITIONAL INFORMATION
		driver.findElement(By.xpath("//*[@id=\"select_employed_wrap\"]/div[3]/label")).click();
		
		driver.findElement(By.xpath("//*[@id=\"generalinfo\"]/div[3]/div[2]")).click();
		driver.findElement(By.xpath("//*[@id=\"ssn\"]")).clear();
		driver.findElement(By.xpath("//*[@id=\"ssn\"]")).sendKeys("444444444");
		
		driver.findElement(By.xpath("//*[@id=\"mobilenumber_home\"]")).sendKeys("(503)4008675");
		Select martial_select = new Select(driver.findElement(By.xpath("//*[@id=\"select_maritalstatus\"]")));
		martial_select.selectByIndex(3);
		driver.manage().timeouts().implicitlyWait(8000,TimeUnit.SECONDS);

		logger = extent.startTest("PATIENT ADDITIONAL INFO Test");
		  try
           {
			 
			  driver.findElement(By.xpath("//*[@id=\"generalinfo\"]/div[3]/div[7]/div[1]/input")).click();
			  logger.log(LogStatus.PASS, "Test Case is Passed ");
           } 
           catch(Exception e)
           {
        	   logger.log(LogStatus.FAIL, "Test Case is Failed ");
           }
		
		
		
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
		driver.findElement(By.xpath("//*[@id=\"emc_number\"]")).sendKeys("555555555555");
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"mycontact\"]/div[8]/div/input")).click();
		  try
         {
			 
			  driver.findElement(By.className("start_ppr")).click(); 
			  logger.log(LogStatus.PASS, "Test Case is Passed ");
         } 
         catch(Exception e)
         {
      	   logger.log(LogStatus.FAIL, "Test Case is Failed ");
         }
		
	}

	@Test (priority = 6)
	public void patient_lifestyle()
	{
		System.out.println("PATIENT LIFESTYLE");
		  
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		 driver.findElement(By.xpath("//*[@id='lifestyle']/div[2]/div[4]/div/input")).click();
		//*[@id="lifestyle"]/div[2]/div[4]/div/input
	}
		
		
	
	@Test (priority = 7)
	public void patient_past_medical()
	{System.out.println("PATIENT PAST MEDICAL HX");
	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
/*	Boolean b_pastmed = driver.findElement(By.xpath("//*[@id=\"pastmedicalhistory_add\"]")).isEnabled();
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
	}
	driver.findElement(By.xpath("//*[@id=\"add_medi_data1\"]")).click();
	}

	driver.findElement(By.xpath("//*[@id=\"medicalinfo_edit\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"pastmedicalhistory_search\"]")).sendKeys("b");
	driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li[7]")).click();
	driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li[6]")).click();
	
	
	driver.findElement(By.xpath("//*[@id=\"surgicalhistory_add\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"select-surgicalhistory\"]/li[4]")).click();
	driver.findElement(By.xpath("//*[@id=\"select-surgicalhistory\"]/li[7]")).click();
	driver.findElement(By.xpath("//*[@id=\"surgicalhistory_dataadd\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"allergies_add\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"select_allergies\"]/li[6]")).click();
	driver.findElement(By.xpath("//*[@id=\"select_allergies\"]/li[11]")).click();
	driver.findElement(By.xpath("//*[@id=\"allergies_dataadd\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"familyhistory_none\"]")).click();
	//driver.findElement(By.xpath("//*[@id=\"vaccinations_none\"]")).click();*/
logger = extent.startTest("PATIENT PAST MEDICAL HX TEST");
	  try
     {
		 
		  driver.findElement(By.xpath("//*[@id=\"page_submitbtn\"]/div/input")).click();
		  logger.log(LogStatus.PASS, "Test Case is Passed ");
     } 
     catch(Exception e)
     {
  	   logger.log(LogStatus.FAIL, "Test Case is Failed ");
     }
	
	}



	@Test (priority = 8)
	public void patient_rx_info()

	{
		System.out.println("PATIENT RX INFO");
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"select_medication1_wrap\"]")).click();
		
		List<WebElement> medication_list = driver.findElements(By.xpath("//div[@class='option']"));
	
		Random ran = new Random();
		int i =2;
		
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"select_medication1_wrap\"]/div[1]/div[2]/div/div["+i+"]")).click();
	driver.findElement(By.xpath("//*[@id=\"add_medi_data\"]")).click();
		logger = extent.startTest("PATIENT RX INFO TEST");
		  try
         {
			 
			  driver.findElement(By.xpath("//*[@id=\"pharmacy\"]/div[7]/div/input")).click();
			  logger.log(LogStatus.PASS, "Test Case is Passed ");
         } 
         catch(Exception e)
         {
      	   logger.log(LogStatus.FAIL, "Test Case is Failed ");
         }
		
		
	}

	@Test (priority = 9)
	public void addition_page()

	{
		System.out.println("ADDITIONAL SOCIAL HISTORY");
		
		driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[1]/div/div[1]")).click();
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		logger = extent.startTest("ADDITIONAL SOCIAL HISTORY TEST");
		  try
         {
			 
			  driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[8]/div/input")).click();
			  logger.log(LogStatus.PASS, "Test Case is Passed ");
         } 
         catch(Exception e)
         {
      	   logger.log(LogStatus.FAIL, "Test Case is Failed ");
         }
		
		
	}


	@Test (priority = 10)
	public void medical_history_page()

	{
		//*[@id="form_mdynamicqadone"]/div/div/div[1]/div/div[1]/label
		System.out.println("MEDICAL HISTORY FORM");
		driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[1]/div/div[5]")).click();
		//driver.findElement(By.xpath("//*[@id='form_mdynamicqadone']/div/div/div[5]/div/div[1]/label")).click();
		//jse.executeScript("scroll(0,500);");
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[3]/div/input")).click();
		
	}

	@Test (priority = 11)
	public void phq_page()

	{
		System.out.println("PHQ-9");
		driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[3]/div/div[2]")).click();
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(0,3000);");
		logger = extent.startTest("PHQ-9 TEST");
		  try
         {
			 
				driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[14]/div/input")).click();
			  logger.log(LogStatus.PASS, "Test Case is Passed ");
         } 
         catch(Exception e)
         {
      	   logger.log(LogStatus.FAIL, "Test Case is Failed ");
         }
	
		
	}

	@Test (priority = 12)
	public void gad_page()

	{
		System.out.println("GAD-7");
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(0,2000);");
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[11]/div/input")).click();
		
	}
	@Test (priority = 13)
	public void encounter_page()

	{
		System.out.println("ENCOUNTER QUESTIONNAIRE ");
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(0,1000);");
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[9]/div/input")).click();
		
	}

	@Test (priority = 14)
	public void ros_page()

	{
		
		driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[2]/span/label")).click();
		//driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[9]/span/label")).click();
		//driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[18]/span/label")).click();
		//driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[43]/span/label")).click();
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(0,6000);");
		//*[@id="form_mdynamicqadone"]/div/div/div[51]/div/input
		driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[51]/div/input")).click();
		
	}


	@Test (priority = 15)
	public void policy1_page()

	{
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		//*[@id="dynamicinfo_38"]/div[2]/div/div/a[1]
		driver.findElement(By.xpath("//*[@id=\"dynamicinfo_38\"]/div[2]/div/div/a[1]")).click();
		
	}

	@Test (priority = 16)
	public void policy2_page() throws InterruptedException

	{
		driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
		WebElement doc = driver.findElement(By.xpath("//*[@id=\"dynamicinfo_39\"]/div[2]/div/div/a[2]"));
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

	@Test (priority = 18)
	public void signature_page()

	{

		System.out.println("Signature Page");
		driver.findElement(By.xpath("//*[@id=\"reasonforvisit\"]")).sendKeys("Fever");
		driver.manage().timeouts().implicitlyWait(50000,TimeUnit.SECONDS);
		//*[@id="ctlSignature_Container"]
		driver.findElement(By.xpath("//*[@id=\"ctlSignature\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"ctlSignature\"]")).click();
		JavascriptExecutor jse1 = (JavascriptExecutor)driver;
		jse1.executeScript("scroll(0, 500);");
		driver.findElement(By.xpath("//*[@id=\"addappointment\"]/div/div[11]/div/input")).click();
		
	}


	@AfterTest
	public void endReport(){
		// writing everything to document
		//flush() - to write or update test information to your report. 
              extent.flush();
              //Call close() at the very end of your session to clear all resources. 
              //If any of your test ended abruptly causing any side-affects (not all logs sent to ExtentReports, information missing), this method will ensure that the test is still appended to the report with a warning message.
              //You should call close() only once, at the very end (in @AfterSuite for example) as it closes the underlying stream. 
              //Once this method is called, calling any Extent method will throw an error.
              //close() - To close all the operation
              extent.close();
	}

  
  
  @AfterClass
  public void afterClass() {
	  driver.quit();
	  extent.flush();
	  extent.close();
  }

}
