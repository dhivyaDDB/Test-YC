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
import org.openqa.selenium.Keys;
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
import com.yosi.util.Log;
import com.yosi.util.Validator;

public class Athena_Mydependent_Checkin_TC {
	
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
			
			extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/REG2ExtentReport.html", true);
			extent
			                .addSystemInfo("Host Name", "SoftwareTestingMaterial")
			                .addSystemInfo("Environment", "Automation Testing");
			extent.loadConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
			
			DOMConfigurator.configure("log4j.xml");
			System.setProperty("driver.chrome.driver","chromedriver.exe");
			driver = new ChromeDriver();
			driver.get(prop1.getProperty("my_dependent_invite"));
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
	 if(driver.findElement(By.xpath("//*[@id=\"clkdependant\"]/div/div")).isDisplayed() == true)
	 {
			driver.findElement(By.xpath("//*[@id=\"clkdependant\"]/div/div")).click();
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
	Log.info("DOG entered: "+prop1.getProperty("my_dependent_dob"));
	Log.info("Gender entered:" +prop1.getProperty("my_dependent_gender"));
	driver.findElement(By.xpath("//*[@id=\"verify_date\"]")).sendKeys(prop1.getProperty("my_dependent_dob"));
	driver.findElement(By.xpath("//*[@id=\"select_gender_wrap\"]")).click();
	Select se = new Select(driver.findElement(By.xpath("//*[@class=\"yc-input customdd select2-hidden-accessible\"]")));
	if (prop1.getProperty("my_dependent_gender")=="Male")
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
	
	String zipcode_value =driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).getAttribute("value");
	if(zipcode_value == " ")
	{
		driver.findElement(By.id("zipcode")).click();
		driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).sendKeys("1111",Keys.TAB);
		
		Log.info("Entered zipcode "+zipcode_value);
		
		Boolean zip_code= driver.findElement(By.xpath("//*[@id=\"zipcode-error\"]")).isDisplayed();
		Thread.sleep(2000);
		
		 if(zip_code)
		 {
			 Log.info("It is invalid zipcode");
			 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).clear();
			 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).sendKeys("10004");
			 Log.info("Entered zipcode "+zipcode_value);
			 String city_dropdown = driver.findElement(By.xpath("//*[@id=\"select2-select_statecity-container\"]")).getText();
			 Log.info("City State "+city_dropdown);
			 Thread.sleep(2000);
			 
		 }
		 
		 	driver.manage().timeouts().implicitlyWait(1000,TimeUnit.SECONDS); 
		 	WebElement doc = driver.findElement(By.xpath("//*[@id=\"select_statecity\"]"));
			Actions action =    new Actions(driver);
			Thread.sleep(2000);
			action.click(doc).perform();
			Select city_select = new Select( driver.findElement(By.xpath("//*[@id=\"select_statecity\"]")));
			city_select.selectByValue("other");
			Thread.sleep(2000);
			String City = driver.findElement(By.xpath("//*[@id=\"cityname\"]")).getAttribute("value");
			String state = driver.findElement(By.xpath("//*[@id=\"statename\"]")).getAttribute("value");
			if(City != "" && state!= "" )
			{
				Log.info("The text doent contain any value");
				driver.findElement(By.xpath("//*[@id=\"cityname\"]")).sendKeys("NewYork");
				driver.findElement(By.xpath("//*[@id=\"statename\"]")).sendKeys("NY");
			}
			else
			{
				Log.info("The text  contain value");
			}
			Thread.sleep(2000);
			 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).click();
			 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).clear();
			 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).sendKeys("11111");
			 Log.info("Entered zipcode "+zipcode_value);
			 driver.findElement(By.xpath("//*[@id=\"cityname\"]")).sendKeys("NewYork");
			 driver.findElement(By.xpath("//*[@id=\"statename\"]")).sendKeys("NY");
	}
	else
	{
	driver.findElement(By.id("zipcode")).click();
	driver.findElement(By.id("zipcode")).clear();
	driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).sendKeys("1111",Keys.TAB);
	
	Log.info("Entered zipcode "+zipcode_value);
	
	Boolean zip_code= driver.findElement(By.xpath("//*[@id=\"zipcode-error\"]")).isDisplayed();
	Thread.sleep(2000);
	
	 if(zip_code)
	 {
		 Log.info("It is invalid zipcode");
		 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).clear();
		 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).sendKeys("10004");
		 Log.info("Entered zipcode "+zipcode_value);
		 String city_dropdown = driver.findElement(By.xpath("//*[@id=\"select2-select_statecity-container\"]")).getText();
		 Log.info("City State "+city_dropdown);
		 Thread.sleep(2000);
		 
	 }
	 
	 	driver.manage().timeouts().implicitlyWait(1000,TimeUnit.SECONDS); 
	 	WebElement doc = driver.findElement(By.xpath("//*[@id=\"select_statecity\"]"));
		Actions action =    new Actions(driver);
		Thread.sleep(2000);
		action.click(doc).perform();
		Select city_select = new Select( driver.findElement(By.xpath("//*[@id=\"select_statecity\"]")));
		city_select.selectByValue("other");
		Thread.sleep(2000);
		String City = driver.findElement(By.xpath("//*[@id=\"cityname\"]")).getAttribute("value");
		String state = driver.findElement(By.xpath("//*[@id=\"cityname\"]")).getAttribute("value");
		if(City != "" && state!= "" )
		{
			Log.info("The text doent contain any value");
			driver.findElement(By.xpath("//*[@id=\"cityname\"]")).sendKeys("NewYork");
			driver.findElement(By.xpath("//*[@id=\"statename\"]")).sendKeys("NY");
		}
		else
		{
			Log.info("The text  contain value");
		}
		Thread.sleep(2000);
		 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).click();
		 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).clear();
		 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).sendKeys("11111");
		 Log.info("Entered zipcode "+zipcode_value);
		 driver.findElement(By.xpath("//*[@id=\"cityname\"]")).sendKeys("NewYork");
		 driver.findElement(By.xpath("//*[@id=\"statename\"]")).sendKeys("NY");
}
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
	 

	logger = extent.startTest("Patient Info page"); 
	 if(driver.findElement(By.xpath("//*[@id=\"form_mpersonal_info\"]/div/div[10]/div[1]/input")).isDisplayed() == true)
	 {
			driver.findElement(By.xpath("//*[@id=\"form_mpersonal_info\"]/div/div[10]/div[1]/input")).click();	
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
	
	System.out.println("The Parent/Guardian Information  page");
	Log.info("The Parent1/Guardian Information");
	driver.findElement(By.xpath("//*[@id=\"parentfirstname\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"parentfirstname\"]")).clear();
	driver.findElement(By.xpath("//*[@id=\"parentfirstname\"]")).sendKeys("Dan");
	
	String parent1_fname= driver.findElement(By.xpath("//*[@id=\"parentfirstname\"]")).getAttribute("value");
	
	driver.findElement(By.xpath("//*[@id=\"parentlastname\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"parentlastname\"]")).clear();
	driver.findElement(By.xpath("//*[@id=\"parentlastname\"]")).sendKeys("Test");
	String parent1_lname= driver.findElement(By.xpath("//*[@id=\"parentlastname\"]")).getAttribute("value");
	
	//*[@id="select_gender_wrap"]/div[2]/label
	driver.findElement(By.xpath("//*[@id=\"select_gender_wrap\"]/div[2]/label")).click();
	//String parent1_gender = driver.findElement(By.xpath("//*[@id=\"select_gender_wrap\"]/div[2]/label")).getText();
	
	driver.findElement(By.xpath("//*[@id=\"date\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"date\"]")).clear();
	driver.findElement(By.xpath("//*[@id=\"date\"]")).sendKeys("02/11/1978");
	//String parent1_dob = driver.findElement(By.xpath("//*[@id=\"date\"]")).getAttribute("value");
	Select parent1_relationship = new Select(driver.findElement(By.xpath("//*[@id=\"select_parentemc_relationship\"]")));
	parent1_relationship.selectByIndex(1);
	
	Log.info("The Parent2/Guardian Information");
	driver.findElement(By.xpath("//*[@id=\"parentfirstname2\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"parentfirstname2\"]")).clear();
	driver.findElement(By.xpath("//*[@id=\"parentfirstname2\"]")).sendKeys("Dan");
	
	String parent2_fname= driver.findElement(By.xpath("//*[@id=\"parentfirstname2\"]")).getAttribute("value");
	
	driver.findElement(By.xpath("//*[@id=\"parentlastname2\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"parentlastname2\"]")).clear();
	driver.findElement(By.xpath("//*[@id=\"parentlastname2\"]")).sendKeys("Test");
	String parent2_lname= driver.findElement(By.xpath("//*[@id=\"parentlastname2\"]")).getAttribute("value");
	
	
	driver.findElement(By.xpath("//*[@id=\"select_gender_wrap\"]/div[2]/label")).click();
	
	
	driver.findElement(By.xpath("//*[@id=\"date2\"]")).click();
	
	Select parent2_relationship = new Select(driver.findElement(By.xpath("//*[@id=\"select_parentemc_relationship2\"]")));
	parent2_relationship.selectByIndex(2);
	
	driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);

	logger = extent.startTest("The Parent/Guardian Information  page"); 
	 if(driver.findElement(By.xpath("//*[@id=\"form_parent_info\"]/div/div[12]/div/input")).isDisplayed() == true)
	 {
			driver.findElement(By.xpath("//*[@id=\"form_parent_info\"]/div/div[12]/div/input")).click();
			logger.log(LogStatus.PASS, "Test Case is Passed");
	 }
	 else
	 {
		 logger.log(LogStatus.FAIL, "Test Case is Failed ");
	 }
	 extent.endTest(logger);
			
	 
	 
	 System.out.println("The PATIENT GUARANTOR  page");
	 Log.info("The PATIENT GUARANTOR  page/");
	 
	 Select guarantor_sel = new Select(driver.findElement(By.xpath("//*[@id=\"select_parent_type\"]")));
	 guarantor_sel.selectByIndex(1);
	 
	System.out.println("The PATIENT GUARANTOR  page");
			Log.info("The PATIENT GUARANTOR  page/");
			
			Log.info("Guarantor Fname"+driver.findElement(By.id("firstname")).getAttribute("value"));
			Log.info("Guarantor Lname "+driver.findElement(By.id("lastname")).getAttribute("value"));
			Log.info("Guarantor Relationship "+driver.findElement(By.id("select2-select_emc_relationship-container")).getAttribute("value"));
			
			driver.findElement(By.xpath("//*[@id=\"date\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"date\"]")).sendKeys("02/02/1989");
			driver.findElement(By.xpath("//*[@id=\"select_my_insurance_wrap\"]/div[2]/label")).click();	
			driver.findElement(By.xpath("//*[@id=\"form_parentguarantor_info\"]/div/div[8]/div/div/label")).click();	
			
			//*[@id="form_parentguarantor_info"]/div/div[8]/div/div/label
			driver.findElement(By.id("address1")).click();
			driver.findElement(By.id("address1")).clear();
			driver.findElement(By.id("address1")).sendKeys("31,green road");
			driver.findElement(By.id("address2")).click();
			driver.findElement(By.id("address2")).clear();
			driver.findElement(By.id("address2")).sendKeys("Dallas");
			driver.findElement(By.id("zipcode")).click();
			driver.findElement(By.id("zipcode")).clear();	
			//driver.findElement(By.id("zipcode")).sendKeys("10036");
			String add1 = driver.findElement(By.id("address1")).getAttribute("value");
			String add2 = driver.findElement(By.id("address2")).getAttribute("value");
			String zip =driver.findElement(By.id("zipcode")).getAttribute("value");
			
			Log.info("Address of the patient "+add1+ "," +add2+","+zip+".");
			
			String zipcode_value =driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).getAttribute("value");
			if(zipcode_value == " ")
			{
				driver.findElement(By.id("zipcode")).click();
				driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).sendKeys("1111",Keys.TAB);
				
				Log.info("Entered zipcode "+zipcode_value);
				
				Boolean zip_code= driver.findElement(By.xpath("//*[@id=\"zipcode-error\"]")).isDisplayed();
				Thread.sleep(2000);
				
				 if(zip_code)
				 {
					 Log.info("It is invalid zipcode");
					 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).clear();
					 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).sendKeys("10004");
					 Log.info("Entered zipcode "+zipcode_value);
					 String city_dropdown = driver.findElement(By.xpath("//*[@id=\"select2-select_statecity-container\"]")).getText();
					 Log.info("City State "+city_dropdown);
					 Thread.sleep(2000);
					 
				 }
				 
				 	driver.manage().timeouts().implicitlyWait(1000,TimeUnit.SECONDS); 
				 	WebElement doc = driver.findElement(By.xpath("//*[@id=\"select_statecity\"]"));
					Actions action =    new Actions(driver);
					Thread.sleep(2000);
					action.click(doc).perform();
					Select city_select = new Select( driver.findElement(By.xpath("//*[@id=\"select_statecity\"]")));
					city_select.selectByValue("other");
					Thread.sleep(2000);
					String City = driver.findElement(By.xpath("//*[@id=\"cityname\"]")).getAttribute("value");
					String state = driver.findElement(By.xpath("//*[@id=\"statename\"]")).getAttribute("value");
					if(City != "" && state!= "" )
					{
						Log.info("The text doent contain any value");
						driver.findElement(By.xpath("//*[@id=\"cityname\"]")).sendKeys("NewYork");
						driver.findElement(By.xpath("//*[@id=\"statename\"]")).sendKeys("NY");
					}
					else
					{
						Log.info("The text  contain value");
					}
					Thread.sleep(2000);
					 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).click();
					 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).clear();
					 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).sendKeys("11111");
					 Log.info("Entered zipcode "+zipcode_value);
					 driver.findElement(By.xpath("//*[@id=\"cityname\"]")).sendKeys("NewYork");
					 driver.findElement(By.xpath("//*[@id=\"statename\"]")).sendKeys("NY");
			}
			else
			{
			driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).clear();
			driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).sendKeys("1111",Keys.TAB);
			
			Log.info("Entered zipcode "+zipcode_value);
			
			Boolean zip_code= driver.findElement(By.xpath("//*[@id=\"zipcode-error\"]")).isDisplayed();
			Thread.sleep(2000);
			
			 if(zip_code)
			 {
				 Log.info("It is invalid zipcode");
				 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).clear();
				 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).sendKeys("10004");
				 Log.info("Entered zipcode "+zipcode_value);
				 String city_dropdown = driver.findElement(By.xpath("//*[@id=\"select2-select_statecity-container\"]")).getText();
				 Log.info("City State "+city_dropdown);
				 Thread.sleep(2000);
				 
			 }
			 
			 	driver.manage().timeouts().implicitlyWait(1000,TimeUnit.SECONDS); 
			 	WebElement doc = driver.findElement(By.xpath("//*[@id=\"select_statecity\"]"));
				Actions action =    new Actions(driver);
				Thread.sleep(2000);
				action.click(doc).perform();
				Select city_select = new Select( driver.findElement(By.xpath("//*[@id=\"select_statecity\"]")));
				city_select.selectByValue("other");
				Thread.sleep(2000);
				String City = driver.findElement(By.xpath("//*[@id=\"cityname\"]")).getAttribute("value");
				String state = driver.findElement(By.xpath("//*[@id=\"cityname\"]")).getAttribute("value");
				if(City != "" && state!= "" )
				{
					Log.info("The text doent contain any value");
					driver.findElement(By.xpath("//*[@id=\"cityname\"]")).sendKeys("NewYork");
					driver.findElement(By.xpath("//*[@id=\"statename\"]")).sendKeys("NY");
				}
				else
				{
					Log.info("The text  contain value");
				}
				Thread.sleep(2000);
				 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).click();
				 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).clear();
				 driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).sendKeys("11111");
				 Log.info("Entered zipcode "+zipcode_value);
				 driver.findElement(By.xpath("//*[@id=\"cityname\"]")).sendKeys("NewYork");
				 driver.findElement(By.xpath("//*[@id=\"statename\"]")).sendKeys("NY");
		}
			
			Log.info("Address of the patient \n"+add1+ "," +add2+","+zip+".");
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
public void insurance_info()
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
	 if(driver.findElement(By.xpath("//*[@id=\"form_minsurance_info\"]/div[3]/div/div/div/div[1]/input")).isDisplayed() == true)
	 {
			driver.findElement(By.xpath("//*[@id=\"form_minsurance_info\"]/div[3]/div/div/div/div[1]/input")).click();
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
	 if(driver.findElement(By.xpath("//*[@id=\"generalinfo\"]/div[3]/div[6]/div[1]/input")).isDisplayed() == true)
	 {
			driver.findElement(By.xpath("//*[@id=\"generalinfo\"]/div[3]/div[6]/div[1]/input")).click();
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
	System.out.println("PATIENT LIFE");
	Log.info("PATIENT LIFESTYLE");
	Select cigarettes_select = new Select(driver.findElement(By.xpath("//*[@id=\"select_smoking\"]")));
	cigarettes_select.selectByIndex(3);
	
	List<WebElement> cigarettes_list = driver.findElements(By.xpath("//*[@id=\"select_smoking\"]"));
	for(WebElement c_list : cigarettes_list) {
		 Log.info("Cigarettes Drop Down value:\n"+c_list.getText());
		}
	List<WebElement> Alcoholic_list = driver.findElements(By.xpath("//*[@id=\"select_drink\"]"));
	for(WebElement a_list : Alcoholic_list) {
		 Log.info("Alcoholic Drop Down value:\n"+a_list.getText());
		}
	List<WebElement> Recreational_Drugs_list = driver.findElements(By.xpath("//*[@id=\"select_smoking\"]"));
	for(WebElement r_list : Recreational_Drugs_list) {
		 Log.info("Recreational Drugs Drop Down value:\n"+r_list.getText());
		 
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
}
@Test (priority = 7)
public void patient_past_medical() throws InterruptedException
{
	System.out.println("PATIENT PAST MEDICAL HX");

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
	//pastmedicalhistory_search
	for (int i=1;i<=5;i++)
	{
		//driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/div/div["+i+"]/li")).click();
		driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li["+i+"]/label")).click();
		
		Log.info(driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li["+i+"]/label")).getText());
	}
	
	driver.findElement(By.xpath("//*[@id=\"add_medi_data1\"]")).click();
	
	JavascriptExecutor jse = (JavascriptExecutor)driver;
	jse.executeScript("scroll(0,1000);");

	
/*	
	
		driver.findElement(By.xpath("//*[@id=\"pastmedicalhistory_add\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"pastmedicalhistory_search\"]")).sendKeys("a");
		driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/div/div[1]/li")).click();
		driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/div/div[2]/li")).click();
		driver.findElement(By.xpath("//*[@id=\"add_medi_data1\"]")).click();*/
	
	/*List<WebElement> surgry_list = driver.findElements(By.xpath("//*[@id=\"select-surgicalhistory\"]/div"));
	int count2=driver.findElements(By.xpath("//*[@id=\"select-surgicalhistory\"]/div")).size();
	 Log.info("Past Medicalhistory List \n");
	for(WebElement e : medical_list) {
		 Log.info(e.getText());*/
		//}
	//*[@id="select-surgicalhistory"]/li[1]/label
	driver.findElement(By.xpath("//*[@id=\"surgicalhistory_add\"]")).click();
	List<WebElement> surgical_list = driver.findElements(By.xpath("//*[@id=\"select-surgicalhistory\"]/li"));
		
		int count2=driver.findElements(By.xpath("//*[@id=\"select-surgicalhistory\"]/li")).size();
		 Log.info("Past SurgicalHistory  List \n");
		for(WebElement e : surgical_list) {
			 Log.info(e.getText());
			}
		
		Log.info("Past SurgicalHistory Selected \n");
		
		for (int i=1;i<=5;i++)
		{
			//*[@id="select-surgicalhistory"]/li[1]/label
			driver.findElement(By.xpath("//*[@id=\"select-surgicalhistory\"]/li["+i+"]/label")).click();
			
			Log.info(driver.findElement(By.xpath("//*[@id=\"select-pastmedicalcondition1\"]/li["+i+"]/label")).getText());
		}
		
		driver.findElement(By.xpath("//*[@id=\"surgicalhistory_dataadd\"]")).click();
		
	
		/*driver.findElement(By.xpath("//*[@id=\"allergies_add\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"select_allergies\"]/li[5]/label")).click();
		driver.findElement(By.xpath("//*[@id=\"select_allergies\"]/li[11]/label")).click();
		driver.findElement(By.xpath("//*[@id=\"allergies_dataadd\"]")).click();
		*/
	
	/*	driver.findElement(By.xpath("//*[@id=\"familyhistory_add\"]")).click();
		//*[@id="fh_1151"] //*[@id="select-familyhistory"]/li[3]/label
		driver.findElement(By.xpath("//*[@id=\"select-familyhistory\"]/li[3]/label")).click();
		driver.findElement(By.xpath("//*[@id=\"fhh_1151\"]/li[2]/span/span[2]/label")).click();
		
		driver.findElement(By.xpath("//*[@id=\"familyhistory_dataadd\"]")).click();*/
		driver.findElement(By.xpath("//*[@id=\"vaccinations_none\"]")).click();
		
		
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

//VITALS
@Test (priority = 14)
public void vital_page()

{
	//*[@id="feet_answer_2531-selectized"]
	driver.findElement(By.xpath("//*[@id=\"feet_answer_2531-selectized\"]")).sendKeys("5",Keys.ENTER);
	driver.findElement(By.xpath("//*[@id=\"inches_answer_2531-selectized\"]")).sendKeys("5",Keys.ENTER);
	//*[@id="answer_2532"]
	driver.findElement(By.xpath("//*[@id=\"answer_2532\"]")).sendKeys("234");
	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[8]/div/input")).click();
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
	
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[5]/div/div[2]/label")).click();
	
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[6]/div/div[2]/label")).click();
	
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[8]/div/div[1]/label")).click();
	
	driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[9]/div/div[2]/label")).click();
	
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
	
	driver.findElement(By.xpath("//*[@id=\"dynamicinfo_38\"]/div[2]/div/div/a[1]")).click();
	//driver.findElement(By.xpath("//*[@id=\"agreeNOTICE_OF_PRIVACY_PRACTICES\"]")).click();
					

}

@Test (priority = 20)
public void privacy_policy_page() throws InterruptedException

{
	System.out.println("PRIVACY POLICY ");
	Log.info("PRIVACY POLICY");
	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	
		//WebElement doc = driver.findElement(By.xpath("//*[@id=\"declinePrivacy_Policy\"]"));
	WebElement doc = driver.findElement(By.xpath("//*[@id=\"dynamicinfo_39\"]/div[2]/div/div/a[2]"));
			Actions action =    new Actions(driver);
		   Thread.sleep(2000);
		   action.click(doc).perform();	
}

/*@Test (priority = 21)
public void notice_of_privacy_page2()

{
	System.out.println("NOTICE_OF_PRIVACY_PRACTICES ");
	Log.info("NOTICE_OF_PRIVACY_PRACTICES ");
	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	//*[@id="dynamicinfo_110"]/div[2]/div/div/a
	//driver.findElement(By.xpath("//*[@id=\"agreeNOTICE_OF_PRIVACY_PRACTICES\"]")).click();
	driver.findElement(By.xpath("//*[@id=\"dynamicinfo_110\"]/div[2]/div/div/a")).click();
					

}

@Test (priority = 22)
public void assignment_of_Benefits_page() throws InterruptedException

{
	System.out.println("Assignment_of_Benefits");
	Log.info("Assignment_of_Benefits");
	driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
	
		// WebElement doc = driver.findElement(By.xpath("//*[@id=\"agreeAssignment_of_Benefits\"]"));
	WebElement doc = driver.findElement(By.xpath("//*[@id=\"dynamicinfo_111\"]/div[2]/div/div/a"));
			Actions action =    new Actions(driver);
		   Thread.sleep(2000);
		   action.click(doc).perform();	
}
*/


/*@Test (priority = 17)
public void skip_page()

{
	//CREDIT CARD INFO
	driver.findElement(By.xpath("//*[@id=\"remove_contract\"]")).click();
	
}*/

@Test (priority = 21)
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
	extent.close();
	extent.flush();
	 driver.quit();
}

}
