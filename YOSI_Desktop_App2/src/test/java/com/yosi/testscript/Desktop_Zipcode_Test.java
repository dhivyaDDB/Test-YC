package com.yosi.testscript;

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
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.yosi.util.*;


public class Desktop_Zipcode_Test {
	
	public WebDriver driver;
	public static ExtentReports extent;
	public static ExtentTest logger;
	
	
	Properties prop1 = new Properties();
	JavascriptExecutor jse = (JavascriptExecutor)driver;
	
	@BeforeClass
	 public void beforeClass() {
		  
			File file = new File("src/main/java/com/yosi/config/config.properties");
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
			extent.loadConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
			
			DOMConfigurator.configure("log4j.xml");
			System.setProperty("driver.chrome.driver","chromedriver.exe");
			driver = new ChromeDriver();
			driver.get(prop1.getProperty("url"));
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
	  }

 @Test(priority = 0)
 public void start_paperwork() 
 {
	  //START PAPERWORK
		String apt_time= driver.findElement(By.xpath("/html/body/div/div[2]/div")).getText();
		Log.info(driver.getTitle());
		Log.info("Appoinment Time \n" +apt_time);
		
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

public void select_doctor() throws InterruptedException
{
	
	WebElement doc = driver.findElement(By.xpath("//*[@id=\"form_mchoosedoctor_info\"]/div/ul/li[2]/a"));
	Actions action =    new Actions(driver);
	Thread.sleep(2000);
	action.click(doc).perform();
	Log.info("Doctor is selected");
   driver.manage().timeouts().implicitlyWait(15000,TimeUnit.SECONDS);

   
   	logger = extent.startTest("Select the Doctor test"); 
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
	 Log.info("Patient verification Page");
	driver.manage().timeouts().implicitlyWait(8000,TimeUnit.SECONDS);
	Log.info("DOG entered: "+prop1.getProperty("dob"));
	Log.info("Gender entered:" +prop1.getProperty("gender"));
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
public void patient_info() throws InterruptedException {
		//PATIENT INFORMATION
			Log.info("PATIENT INFORMATION");
			System.out.println("PATIENT INFORMATION");
			String fname = driver.findElement(By.xpath("//*[@id=\"firstname\"]")).getAttribute("value");
			System.out.println(fname);
			Log.info("First Name of the patient " +fname);
			
			
			String regx = "[a-zA-Z]+\\.?";
		    Pattern f_pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
		    Matcher f_matcher = f_pattern.matcher(fname);
		    Boolean f_bol =f_matcher.find();
		    System.out.println(f_matcher.find()); 
			logger = extent.startTest("First Name Validation In PATIENT INFO PAGE");
			Log.info(" First Name Validation In PATIENT INFO PAGE");

				
				if ( f_bol == true )
				{
					logger.log(LogStatus.PASS, "The name "+fname+"  is contains char only");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "The name "+fname+" is Invalid");
				}
				
				 extent.endTest(logger);
			String lname = driver.findElement(By.xpath("//*[@id=\"lastname\"]")).getAttribute("value");
			System.out.println(lname);
			logger = extent.startTest("Last Name Validation In PATIENT INFO PAGE");
			 Pattern l_pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
			    Matcher l_matcher = l_pattern.matcher(fname);
			    Boolean l_bol =l_matcher.find();
			    System.out.println(l_matcher.find()); 
			Log.info(" Last Name Validation In PATIENT INFO PAGE");

				
				if ( l_bol == true )
				{
					logger.log(LogStatus.PASS, "The name "+lname+"  is contains char only");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "The name "+lname+" is Invalid");
				}
				
				 extent.endTest(logger);
			
			Log.info("Last Name of the patient " +lname);
			
			String gender = driver.findElement(By.xpath("//*[@id=\"select_gender_option1\"]")).getAttribute("value");
			System.out.println("Gender: "+gender);
			Log.info("Gender: "+gender);
			String dob = driver.findElement(By.xpath("//*[@id=\"date\"]")).getAttribute("value");
			System.out.println("DOB: "+dob);
			Log.info("DOB: "+dob);
			
			Log.info("Patient Details \n"+fname+"\n"+lname+"\n"+gender+"\n"+dob);
			driver.findElement(By.id("address1")).click();
			driver.findElement(By.id("address1")).clear();
			driver.findElement(By.id("address1")).sendKeys("31, River cross road");
			driver.findElement(By.id("address2")).click();
			driver.findElement(By.id("address2")).clear();	
			driver.findElement(By.id("address2")).sendKeys("Texas");
			String zip_val =driver.findElement(By.id("zipcode")).getAttribute("value");
			System.out.println(zip_val);
			
			
				driver.findElement(By.id("zipcode")).click();
				driver.findElement(By.id("zipcode")).clear();
				driver.findElement(By.id("zipcode")).sendKeys("10036");
				Select city_select= new Select(driver.findElement(By.xpath("//*[@id=\"select_statecity\"]")));
				city_select.selectByVisibleText("NEW YORK, NY");
				//Boolean p_state_visi = driver.findElement(By.xpath("//")).isDisplayed();
				//if() 
				//{
					
				//}

			
			//PDCS40- city state pop-up
			/*System.out.println("Display "+driver.findElement(By.xpath("//*[@id=\"statecity_Modal\"]")).isDisplayed());
			WebElement doc = driver.findElement(By.xpath("//*[@id=\"statecity_Modal\"]"));
			Actions action =    new Actions(driver);
			Thread.sleep(2000);
			action.moveToElement(doc).build().perform();
			System.out.println("Display "+driver.findElement(By.xpath("//*[@id=\"statecity_Modal\"]")).isDisplayed());
			
			Select city_select= new Select(driver.findElement(By.xpath("//*[@id=\"select_statecity\"]")));
			city_select.selectByIndex(2);
			//*[@id="submitpinfo_statecity"]
			WebElement doc1 = driver.findElement(By.xpath("//*[@id='submitpinfo_statecity']"));
			Actions action1 =    new Actions(driver);
			Thread.sleep(2000);
			
			action1.click(doc1).perform();
			
			*/
				
				
			String add1 = driver.findElement(By.id("address1")).getAttribute("value");
			String add2 = driver.findElement(By.id("address2")).getAttribute("value");
			String zip =driver.findElement(By.id("zipcode")).getAttribute("value");
			Log.info("Address of the patient \n"+add1+ "," +add2+","+zip+".");
			String zipcode_ele =driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).getAttribute("value");
			String regex = "^[0-9]{5}(?:-[0-9]{4})?$";

			Pattern pattern = Pattern.compile(regex);
		
			    Matcher matcher = pattern.matcher(zipcode_ele);
			    Boolean zip_match=matcher.matches();
			    System.out.println(matcher.matches());
			
			System.out.println("Zipcode"+zipcode_ele);
			logger = extent.startTest("Zipcode Validation");

				if (zip_match == true )
				{
					logger.log(LogStatus.PASS, "The US ZIP-"+zipcode_ele+" Code is valid");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "The US ZIP-"+zipcode_ele+" Code is invalid");
				}
				
				 extent.endTest(logger);
			 //if()

					//Assign and Select the dropdown list element
					        /*Select city_Dropdown = new Select(driver.findElement(By.id("select_statecity")));
					       
					        //Get all the option from dropdown list and assign into List
					        List<WebElement> CitylistOption = city_Dropdown.getOptions();
					       
					        // Count the item dropdown list and assign into integer variable
					        int dropdownCount = CitylistOption.size();
							System.out.println(dropdownCount);
							//if (dropdownCount >0)
							//
							//{
*/								//Select city_select= new Select(driver.findElement(By.xpath("//*[@id=\"select_statecity\"]")));
								//city_select.selectByVisibleText("NEW YORK, NY");
								
								/*logger = extent.startTest("Patient Info page"); 
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
						
							else
							{*/
							
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
public void patient_guarantor() throws InterruptedException
{
	
			// GUARANTOR INFORMATION PAGE
			System.out.println("The PATIENT GUARANTOR  page");
			//Log.info("The PATIENT GUARANTOR  page/");
			System.out.println("Radio button values "+driver.findElement(By.id("select_patientsame_access_wrap")).getText());
			System.out.println("Guarantor Fname "+driver.findElement(By.id("firstname")).getAttribute("value"));
			System.out.println("Guarantor Lname "+driver.findElement(By.id("lastname")).getAttribute("value"));
			System.out.println("Guarantor Relationship "+driver.findElement(By.id("select2-select_emc_relationship-container")).getAttribute("value"));
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(0, 500);");
			driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
			Log.info("The PATIENT GUARANTOR  page");
			Log.info("Radio button values"+driver.findElement(By.id("select_patientsame_access_wrap")).getText());
			Log.info("Guarantor Fname"+driver.findElement(By.id("firstname")).getAttribute("value"));
			Log.info("Guarantor Lname "+driver.findElement(By.id("lastname")).getAttribute("value"));
			driver.findElement(By.xpath("//*[@id=\"select_gender_wrap\"]/div[1]/label")).click();
			Log.info("Guarantor Relationship "+driver.findElement(By.xpath("//*[@id=\"select_gender_wrap\"]/div[1]/label")).getAttribute("value"));
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
	/*		
	//PDCS40- city state pop-up
			//*[@id="statecity_Modal"]/div/div
			WebElement doc = driver.findElement(By.xpath("//*[@id=\"statecity_Modal\"]"));
			Actions action =    new Actions(driver);
			Thread.sleep(2000);
			action.moveToElement(doc).build().perform();
			
			System.out.println("Display "+driver.findElement(By.xpath("//*[@id=\"statecity_Modal\"]")).isDisplayed());
			Select city_select= new Select(driver.findElement(By.xpath("//*[@id=\"select_statecity\"]")));
			city_select.selectByIndex(1);
			//*[@id="submitpinfo_statecity"]
			WebElement doc1 = driver.findElement(By.xpath("//*[@id='submitpinfo_statecity']"));
			Actions action1 =    new Actions(driver);
			Thread.sleep(2000);
			
			action1.click(doc1).perform();*/
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

		logger = extent.startTest("Guantor Info page"); 
			 if(driver.findElement(By.xpath("//*[@id=\"form_parentguarantor_info\"]/div[1]/div[11]/div/input")).isDisplayed() == true)
			 {
					driver.findElement(By.xpath("//*[@id=\"form_parentguarantor_info\"]/div[1]/div[11]/div/input")).click();
					logger.log(LogStatus.PASS, "Test Case is Passed");
			 }
			 else
			 {
				 logger.log(LogStatus.FAIL, "Test Case is Failed ");
			 }
			 extent.endTest(logger);
					
}

@Test (priority = 5)
public void insurance_info() throws InterruptedException
{
	//INSURANCE INFORMATION PAGE
			System.out.println("INSURANCE PAGE");
			Log.info("INSURANCE PAGE");
			driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);
			
			driver.findElement(By.xpath("//*[@id=\"form_minsurance_info\"]/div[1]/div/div[2]/div/div/div[1]/label")).click();
			driver.findElement(By.xpath("//*[@id=\"primary_insuredfname\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"primary_insuredfname\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"primary_insuredfname\"]")).sendKeys("Gamly");
			String p_fname=driver.findElement(By.xpath("//*[@id=\"primary_insuredfname\"]")).getAttribute("value");
			Log.info("Secondary Fname entered : \n"+driver.findElement(By.xpath("//*[@id=\"primary_insuredfname\"]")).getAttribute("value"));
			Log.info("First Name of the patient " +p_fname);
			
			
			String p_regx = "[a-zA-Z]+\\.?";
		    Pattern pf_pattern = Pattern.compile(p_regx,Pattern.CASE_INSENSITIVE);
		    Matcher pf_matcher = pf_pattern.matcher(p_fname);
		    Boolean pf_bol =pf_matcher.find();
		    System.out.println(pf_matcher.find()); 
			logger = extent.startTest("Primary First Name Validation In Insurance INFO PAGE");
			Log.info(" Primary First Name Validation In Insurance INFO PAGE");

				
				if ( pf_bol == true )
				{
					logger.log(LogStatus.PASS, "The name "+p_fname+"  is contains char only");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "The name "+p_fname+" is Invalid");
				}
				
				 extent.endTest(logger);
			driver.findElement(By.xpath("//*[@id=\"primary_insuredlname\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"primary_insuredlname\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"primary_insuredlname\"]")).sendKeys("Tests");
			
			String p_lname=driver.findElement(By.xpath("//*[@id=\"primary_insuredlname\"]")).getAttribute("value");
			System.out.println(p_lname);
			Log.info("Last Name of the patient " +p_lname);
			
			
			
		    Pattern pl_pattern = Pattern.compile(p_regx,Pattern.CASE_INSENSITIVE);
		    Matcher pl_matcher = pl_pattern.matcher(p_lname);
		    Boolean pl_bol =pl_matcher.find();
		    System.out.println(pl_matcher.find()); 
			logger = extent.startTest("Primary Last Name Validation In INSURANCE  PAGE");
			Log.info("Primary Last Name Validation In INSURANCE  PAGE");

				
				if ( pl_bol == true )
				{
					logger.log(LogStatus.PASS, "The name "+p_lname+"  is contains char only");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "The name "+p_lname+" is Invalid");
				}
				
				 extent.endTest(logger);
			
		
			Log.info("Primary policy number entered : "+driver.findElement(By.xpath("//*[@id=\"primary_insuredlname\"]")).getAttribute("value"));
			driver.findElement(By.xpath("//*[@id=\"primary_insureddob\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"primary_insureddob\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"primary_insureddob\"]")).sendKeys("12/12/2007");
			Log.info("Secondary DOB entered : \n"+driver.findElement(By.xpath("//*[@id=\"primary_insureddob\"]")).getAttribute("value"));
			String p_dob  =driver.findElement(By.xpath("//*[@id=\"primary_insureddob\"]")).getAttribute("value");
			 System.out.println("DOB"+p_dob);
			
			
			logger = extent.startTest("DOB validation in INSURANCE  PAGE");
			
			String pdob_regex = "^((?:0[0-9])|(?:[1-2][0-9])|(?:3[0-1]))/((?:0[1-9])|(?:1[0-2]))/((?:19|20)\\d{2})$";
			
			 
			Pattern pdob_pattern = Pattern.compile(pdob_regex);
		
		
			    Matcher pdob_matcher = pdob_pattern.matcher(p_dob);
			    Boolean pdob_match=pdob_matcher.matches();
			    System.out.println(pdob_matcher.matches());
			
			 System.out.println("DOB"+p_dob);
				
				if (pdob_match == true )
				{
					logger.log(LogStatus.PASS, "DOB is valid"+p_dob);
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "DOB is invalid"+p_dob);
				}
				
				extent.endTest(logger);
			driver.findElement(By.xpath("//*[@id=\"firstIns_dependent\"]/div[4]/div/div[1]/label")).click();
			Log.info("Secondary Gender entered : \n"+driver.findElement(By.xpath("//*[@id=\"firstIns_dependent\"]/div[4]/div/div[1]/label")).getText());
			driver.findElement(By.xpath("//*[@id=\"primary_ssn\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"primary_ssn\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"primary_ssn\"]")).sendKeys("252525252");
			Log.info("Secondary SSN entered : \n"+driver.findElement(By.xpath("//*[@id=\"primary_ssn\"]")).getAttribute("value"));
			String p_ssn =driver.findElement(By.xpath("//*[@id=\"primary_ssn\"]")).getAttribute("value");
			 System.out.println("SSN"+p_ssn);
			
			
			logger = extent.startTest("SSN Validation in INSURANCE  PAGE");
			
			String pregex = "^[0-9]{3}[-]([0-9]{2})[-]([0-9]{4})$";
			
			 
			Pattern ppattern1 = Pattern.compile(pregex);
		
		
			    Matcher pmatcher1 = ppattern1.matcher(p_ssn);
			    Boolean pssn_match=pmatcher1.matches();
			    System.out.println(pmatcher1.matches());
			
			    System.out.println("SSN"+p_ssn);
				
				if (pssn_match == true )
				{
					logger.log(LogStatus.PASS, "SSN is valid");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "SSN is invalid");
				}
				
				 extent.endTest(logger);
			driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
			
			Select p_relationship = new Select(driver.findElement(By.xpath("//*[@id=\"primary_relationship\"]")));
			p_relationship.selectByIndex(1);
			driver.findElement(By.xpath("//*[@id=\"primary_address1\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"primary_address1\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"primary_address1\"]")).sendKeys("21,kindy Apartment");
			String p_addr1=driver.findElement(By.xpath("//*[@id=\"primary_address1\"]")).getAttribute("value");
			driver.findElement(By.xpath("//*[@id=\"primary_address2\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"primary_address2\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"primary_address2\"]")).sendKeys("DS");
			String p_addr2=driver.findElement(By.xpath("//*[@id=\"primary_address2\"]")).getAttribute("value");
			driver.findElement(By.xpath("//*[@id=\"primary_zipcode\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"primary_zipcode\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"primary_zipcode\"]")).sendKeys("10036");
			
	/*//PDCS40- city state pop-up
			
			WebElement doc = driver.findElement(By.xpath("//*[@id=\"statecity_Modal\"]"));
			Actions action =    new Actions(driver);
			Thread.sleep(2000);
			action.moveToElement(doc).build().perform();
			
			
			Select city_select= new Select(driver.findElement(By.xpath("//*[@id=\"select_statecity\"]")));
			city_select.selectByIndex(0);
			//*[@id="submitpinfo_statecity"]
			WebElement doc1 = driver.findElement(By.xpath("//*[@id='submitpinfo_statecity']"));
			Actions action1 =    new Actions(driver);
			Thread.sleep(2000);
			
			action1.click(doc1).perform();*/
			String p_zip=driver.findElement(By.xpath("//*[@id=\"primary_zipcode\"]")).getAttribute("value");
			Log.info("Secondary Address entered \n" +p_addr1+","+p_addr2+","+p_zip+".");
			
			String pzipcode_ele =driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).getAttribute("value");
			
			 
			logger = extent.startTest("Zipcode Validation in INSURANCE  PAGE");
			
			String pregex1 = "^[0-9]{5}(?:-[0-9]{4})?$";
			Pattern ppattern = Pattern.compile(pregex1);

			    Matcher pmatcher = ppattern.matcher(pzipcode_ele);
			    Boolean pzip_match=pmatcher.matches();
			    System.out.println(pmatcher.matches());
			
			 System.out.println("Zipcode"+pzipcode_ele);
				
				if (pzip_match == true )
				{
					logger.log(LogStatus.PASS, "The entered "+pzipcode_ele+" ZIP Code is valid");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "The entered "+pzipcode_ele+" ZIP Code is invalid");
				}
				
				 extent.endTest(logger);
				 
				 JavascriptExecutor jse = (JavascriptExecutor)driver;
					jse.executeScript("scroll(0,1000);");
			
			driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);

			driver.findElement(By.xpath("//*[@id=\"primary_companyname\"]")).click();
			driver.findElement(By.id("primary_companyname_wrap")).click();
			
			driver.findElement(By.xpath("//*[@id=\"primary_companyname\"]")).sendKeys("Aethna");
			
			Log.info("Primary insurance selected : "+driver.findElement(By.xpath("//*[@id=\"primary_companyname\"]")).getAttribute("value"));
			
			driver.findElement(By.xpath("//*[@id=\"primary_policynumber\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"primary_policynumber\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"primary_policynumber\"]")).sendKeys("DPY9261");
			
			////////****************************** CLICK ON YES BUTTON ********************************************
			driver.findElement(By.xpath("//*[@id=\"form_minsurance_info\"]/div[2]/div/div/div[2]/div/div[1]")).click();

			
			/////////////
			
			///*****************************************************SECONDAY INSURANCE *************************************************\\\\
			System.out.println("Secondary INSURANCE ");
	
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredfname\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredfname\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredfname\"]")).sendKeys("FRED");
			String s_fname=driver.findElement(By.xpath("//*[@id=\"secondary_insuredfname\"]")).getAttribute("value");
			Log.info("Secondary Fname entered : \n"+driver.findElement(By.xpath("//*[@id=\"secondary_insuredfname\"]")).getAttribute("value"));
			Log.info("First Name of the patient " +s_fname);
			
			
			String regx = "[a-zA-Z]+\\.?";
		    Pattern f_pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
		    Matcher f_matcher = f_pattern.matcher(s_fname);
		    Boolean f_bol =f_matcher.find();
		    System.out.println(f_matcher.find()); 
			logger = extent.startTest("First Name Validation In PATIENT INFO PAGE");
			Log.info(" First Name Validation In PATIENT INFO PAGE");

				
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
			driver.findElement(By.xpath("//*[@id=\"secondary_insuredlname\"]")).sendKeys("TEST");
			String s_lname=driver.findElement(By.xpath("//*[@id=\"secondary_insuredlname\"]")).getAttribute("value");
			System.out.println(s_lname);
			Log.info("Last Name of the patient " +s_lname);
			
			
			
		    Pattern l_pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
		    Matcher l_matcher = l_pattern.matcher(s_lname);
		    Boolean l_bol =l_matcher.find();
		    System.out.println(l_matcher.find()); 
			logger = extent.startTest("Last Name Validation In INSURANCE  PAGE");
			Log.info(" Last Name Validation In INSURANCE  PAGE");

				
				if ( l_bol == true )
				{
					logger.log(LogStatus.PASS, "The name "+s_lname+"  is contains char only");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "The name "+s_lname+" is Invalid");
				}
				
				 extent.endTest(logger);
			
			Log.info("Secondary Lname entered : \n"+driver.findElement(By.xpath("//*[@id=\"secondary_insuredlname\"]")).getAttribute("value"));
			driver.findElement(By.xpath("//*[@id=\"secondary_insureddob\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_insureddob\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_insureddob\"]")).sendKeys("12/12/2007");
			Log.info("Secondary DOB entered : \n"+driver.findElement(By.xpath("//*[@id=\"secondary_insureddob\"]")).getAttribute("value"));
			String s_dob  =driver.findElement(By.xpath("//*[@id=\"secondary_insureddob\"]")).getAttribute("value");
			 System.out.println("DOB"+s_dob);
			
			
			logger = extent.startTest("DOB validation in INSURANCE  PAGE");
			
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
			Log.info("Secondary Gender entered : \n"+driver.findElement(By.xpath("//*[@id=\"secondary_genterradio_wrap\"]/div[1]")).getText());
			driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).sendKeys("252525252");
			Log.info("Secondary SSN entered : \n"+driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).getAttribute("value"));
			String s_ssn =driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).getAttribute("value");
			 System.out.println("SSN"+s_ssn);
			
			
			logger = extent.startTest("SSN Validation in INSURANCE  PAGE");
			
			String regex = "^[0-9]{3}[-]([0-9]{2})[-]([0-9]{4})$";
			
			 
			Pattern pattern1 = Pattern.compile(regex);
		
		
			    Matcher matcher1 = pattern1.matcher(s_ssn);
			    Boolean ssn_match=matcher1.matches();
			    System.out.println(matcher1.matches());
			
			    System.out.println("SSN"+s_ssn);
				
				if (ssn_match == true )
				{
					logger.log(LogStatus.PASS, "SSN is valid");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "SSN is invalid");
				}
				
				 extent.endTest(logger);
			driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
			driver.findElement(By.xpath("//*[@id=\"secondary_address1\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_address1\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_address1\"]")).sendKeys("21,kindy Apartment");
			String s_addr1=driver.findElement(By.xpath("//*[@id=\"secondary_address1\"]")).getAttribute("value");
			driver.findElement(By.xpath("//*[@id=\"secondary_address2\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_address2\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_address2\"]")).sendKeys("DS");
			String s_addr2=driver.findElement(By.xpath("//*[@id=\"secondary_address2\"]")).getAttribute("value");
			driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).sendKeys("10036");
			
/*	//PDCS40- city state pop-up
			
			WebElement sdoc = driver.findElement(By.xpath("//*[@id=\"secondary_statecity_Modal\"]"));
			Actions saction =    new Actions(driver);
			Thread.sleep(2000);
			saction.moveToElement(sdoc).build().perform();
			
			
			Select scity_select= new Select(driver.findElement(By.xpath("//*[@id=\"secondary_select_statecity\"]")));
			scity_select.selectByIndex(1);
			//*[@id="submitpinfo_statecity"]
			WebElement sdoc1 = driver.findElement(By.xpath("//*[@id='secondary_submitpinfo_statecity']"));
			Actions saction1 =    new Actions(driver);
			Thread.sleep(2000);
			
			saction1.click(sdoc1).perform();*/
			String s_zip=driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).getAttribute("value");
			Log.info("Secondary Address entered \n" +s_addr1+","+s_addr2+","+s_zip+".");
			
			String szipcode_ele =driver.findElement(By.xpath("//*[@id=\"secondary_zipcode\"]")).getAttribute("value");
			 
			logger = extent.startTest("Zipcode Validation in INSURANCE  PAGE");
			
			String sregex1 = "^[0-9]{5}(?:-[0-9]{4})?$";
			Pattern spattern = Pattern.compile(sregex1);

			    Matcher smatcher = spattern.matcher(szipcode_ele);
			    Boolean szip_match=smatcher.matches();
			    System.out.println(smatcher.matches());
			
			 System.out.println("Zipcode"+szipcode_ele);
				
				if (szip_match == true )
				{
					logger.log(LogStatus.PASS, "The entered "+szipcode_ele+" ZIP Code is valid");
				}
				
				else
				{
					logger.log(LogStatus.FAIL, "The entered "+szipcode_ele+" ZIP Code is invalid");
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
				 driver.findElement(By.xpath("//*[@id=\"mobilenumber\"]")).click();
				 driver.findElement(By.xpath("//*[@id=\"mobilenumber\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"mobilenumber\"]")).sendKeys("(503)4008675");
			Log.info("Mobile number entered : \n"+driver.findElement(By.xpath("//*[@id=\"mobilenumber\"]")).getAttribute("value"));
			String mob_number  =driver.findElement(By.xpath("//*[@id=\"mobilenumber\"]")).getAttribute("value");
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
		
			logger = extent.startTest("Patient Additional page"); 
			 if(driver.findElement(By.xpath("//*[@id=\"submit_generalinfo\"]")).isDisplayed() == true)
			 {
					driver.findElement(By.xpath("//*[@id=\"submit_generalinfo\"]")).click();
					logger.log(LogStatus.PASS, "Test Case is Passed");
			 }
			 else
			 {
				 logger.log(LogStatus.FAIL, "Test Case is Failed ");
			 }
			 extent.endTest(logger);
			 
			
			//EMERGENCY CONTACT
			System.out.println("EMERGENCY CONTACT");
			driver.findElement(By.xpath("//*[@id=\"emc_firstname\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"emc_firstname\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"emc_firstname\"]")).sendKeys("Fredy");
			Log.info("EMC Fname: \n"+driver.findElement(By.xpath("//*[@id=\"emc_firstname\"]")).getAttribute("value"));
			
			driver.findElement(By.xpath("//*[@id=\"emc_lastname\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"emc_lastname\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"emc_lastname\"]")).sendKeys("test");
			Log.info("EMC Lname: \n"+driver.findElement(By.xpath("//*[@id=\"emc_lastname\"]")).getAttribute("value"));
			driver.findElement(By.xpath("//*[@id=\"select2-select_emc_relationship-container\"]")).click();
			Select se = new Select(driver.findElement(By.id("select_emc_relationship")));
			se.selectByIndex(2);
			driver.findElement(By.xpath("//*[@id=\"select2-select_emc_relationship-container\"]")).click();		
			driver.findElement(By.xpath("//*[@id=\"emc_number\"]")).sendKeys("(555)5555555");
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
			
}

@Test (priority = 6)
public void patient_lifestyle()
{
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
@Test (priority = 7)
public void patient_past_medical() throws InterruptedException
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
	logger = extent.startTest("Patient Past History page"); 
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

/*@Test (priority = 14)
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


@Test (priority = 15)
public void policy1_page()

{
	System.out.println("POILCY PAGE ");
	Log.info("POILCY PAGE ");
	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
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
