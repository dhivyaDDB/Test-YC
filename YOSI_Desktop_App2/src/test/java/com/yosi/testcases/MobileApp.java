package com.yosi.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class MobileApp {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		AndroidDriver<?> driver;
		

	    DesiredCapabilities capabilities = DesiredCapabilities.android();

	    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);

	    capabilities.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);

	    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME , "Android");

	    capabilities.setCapability("showChromedriverLog","true");

	    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "my phone");

	    capabilities.setCapability (MobileCapabilityType.VERSION, "6.0.1");
	     
	     	File file = new File("D:/Automation testing/AutomationTestingPR/src/main/java/com/yosi/config/config.properties");
			FileInputStream fileInput = null;
			try {
				fileInput = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Properties prop1 = new Properties();
			prop1.load(fileInput);
			System.setProperty("driver.chrome.driver","D:\\Code\\TestAutomationPR\\chromedriver.exe");
		
			//WebDriver driver = new ChromeDriver();
			  
		     driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);	
		     
		     driver.get(prop1.getProperty("url"));
			driver.manage().deleteAllCookies();
				
				driver.get(prop1.getProperty("url"));
				driver.manage().deleteAllCookies();
				
				String apt_time= driver.findElement(By.xpath("/html/body/div/div[2]/div")).getText();
				System.out.println(apt_time);
				driver.findElement(By.className("start_ppr")).click();
				
				driver.manage().timeouts().implicitlyWait(15000,TimeUnit.SECONDS);
				

				driver.findElement(By.xpath("//*[@id=\"clkadult\"]/div/div")).click();
				driver.manage().timeouts().implicitlyWait(8000,TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"verify_date\"]")).click();
				driver.manage().timeouts().implicitlyWait(30000,TimeUnit.SECONDS);
				//driver.findElement(By.xpath("//*[@id=\"verify_date\"]")).sendKeys(prop1.getProperty("dob"));
				//driver.findElement(By.xpath("//*[@id=\"select_gender_wrap\"]")).click();
			
				//driver.findElement(By.xpath("//*[@id=\"proceed_login\"]")).click();
				
				
				driver.manage().timeouts().implicitlyWait(30000,TimeUnit.SECONDS);
				
				System.out.println("PATIENT INFORMATION");
				String fname = driver.findElement(By.xpath("//*[@id=\"firstname\"]")).getAttribute("value");
				System.out.println(fname);
				String lname = driver.findElement(By.xpath("//*[@id=\"lastname\"]")).getAttribute("value");
				System.out.println(lname);
				/*String gender = driver.findElement(By.xpath("//*[@id=\"select_gender_option1\"]")).getAttribute("value");
				System.out.println(gender);
				String dob = driver.findElement(By.xpath("//*[@id=\"date\"]")).getAttribute("value");
				System.out.println(dob);*/
				driver.findElement(By.xpath("//*[@id=\"address1\"]")).click();
				driver.findElement(By.xpath("//*[@id=\"address1\"]")).clear();
				driver.findElement(By.xpath("//*[@id=\"address1\"]")).sendKeys("31, River cross road");
				driver.findElement(By.xpath("//*[@id=\"address2\"]")).click();
				driver.findElement(By.xpath("//*[@id=\"address2\"]")).clear();	
				driver.findElement(By.xpath("//*[@id=\"address2\"]")).sendKeys("Texas");
				
				driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).click();
				driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).clear();
				driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).sendKeys("10036");
				//driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"form_mpersonal_info\"]/div/div[7]/div/input")).click();			
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
				
				System.out.println("The PATIENT GUARANTOR  page");
				System.out.println(driver.findElement(By.id("select_patientsame_access_wrap")).getText());
				System.out.println(driver.findElement(By.id("firstname")).getText());
				System.out.println(driver.findElement(By.id("lastname")).getText());
				System.out.println(driver.findElement(By.id("select2-select_emc_relationship-container")).getText());
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("scroll(0,1500);");
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"form_parentguarantor_info\"]/div/div[11]/div/div/label")).click();			
				driver.findElement(By.id("address1")).click();
				driver.findElement(By.id("address1")).clear();
				driver.findElement(By.id("address1")).sendKeys("31,green road");
				driver.findElement(By.id("address2")).click();
				driver.findElement(By.id("address2")).clear();
				driver.findElement(By.id("address2")).sendKeys("Dallas");
				driver.findElement(By.id("zipcode")).click();
				driver.findElement(By.id("zipcode")).clear();	
				driver.findElement(By.id("zipcode")).sendKeys("10036");
				
				driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);
				
				driver.findElement(By.xpath("//*[@id=\"form_parentguarantor_info\"]/div/div[14]/div/input")).click();
				
				//INSURANCE INFORMATION PAGE
				System.out.println("INSURANCE PAGE");
				driver.findElement(By.id("primary_companyname_wrap")).click();
				driver.findElement(By.id("primary_companyname_wrap")).click();
				driver.findElement(By.xpath("//*[@id=\"primary_companyname\"]")).sendKeys("Aethna");
				driver.findElement(By.xpath("//*[@id=\"primary_policynumber\"]")).click();
				driver.findElement(By.xpath("//*[@id=\"primary_policynumber\"]")).clear();
				driver.findElement(By.xpath("//*[@id=\"primary_policynumber\"]")).sendKeys("DPY9261");
				driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);
				
				//SECANDARY INFORMATION - Select yes to fill information
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
				driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).click();
				driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).clear();
				driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).sendKeys("252525252");
				driver.findElement(By.xpath("//*[@id=\"secondary_ssn\"]")).sendKeys("252525252");
				driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);
				Select relation = new Select(driver.findElement(By.xpath("//*[@id=\"secondary_relationship\"]")));
				relation.selectByIndex(3);
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
				//driver.findElement(By.xpath("//*[@id=\"secondary_companyname_wrap\"]/div/div[1]")).click();
				driver.findElement(By.xpath("//*[@id=\"secondary_policynumber\"]")).sendKeys("VTR2718");
				driver.manage().timeouts().implicitlyWait(10000,TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"form_minsurance_info\"]/div[3]/div/div/div/div[1]")).click();
				//select_relatedinjury
				driver.findElement(By.xpath("//*[@id=\"select_employed_wrap\"]/div[3]/label")).click();
				//driver.findElement(By.xpath("//*[@id=\"generalinfo\"]/div[3]/div[2]")).click();
				
				//PATIENT ADDITIONAL INFORMATION
				System.out.println("PATIENT ADDITIONAL INFORMATION");
				//driver.findElement(By.xpath("//*[@id=\"ssn\"]")).clear();
				driver.findElement(By.xpath("//*[@id=\"select_employed_wrap\"]/div[3]/label")).click();
				driver.findElement(By.xpath("//*[@id=\"ssn\"]")).sendKeys("444444444");
				driver.manage().timeouts().implicitlyWait(8000,TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"generalinfo\"]/div[3]/div[6]/div[1]/input")).click();
				
				//EMERGENCY CONTACT
				System.out.println("EMERGENCY CONTACT");
				driver.findElement(By.xpath("//*[@id=\"emc_firstname\"]")).sendKeys("Fredy");
				driver.findElement(By.xpath("//*[@id=\"emc_lastname\"]")).sendKeys("test");
				driver.findElement(By.xpath("//*[@id=\"emc_number\"]")).sendKeys("555555555555");
				driver.findElement(By.xpath("//*[@id=\"select2-select_emc_relationship-container\"]")).click();
				Select se = new Select(driver.findElement(By.id("select_emc_relationship")));
				se.selectByIndex(2);
				driver.findElement(By.xpath("//*[@id=\"select2-select_emc_relationship-container\"]")).click();		
				
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"mycontact\"]/div[8]/div/input")).click();
				
				System.out.println("PATIENT LIFESTYLE");
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"lifestyle\"]/div[2]/div[4]/div/input")).click();
				
				System.out.println("PATIENT PAST MEDICAL HX");
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
				Boolean b_pastmed = driver.findElement(By.xpath("//*[@id=\"pastmedicalhistory_add\"]")).isEnabled();
				if (b_pastmed == true)
				{
				//Yes Button
				driver.findElement(By.xpath("//*[@id=\"pastmedicalhistory_add\"]")).click();
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
				
				driver.findElement(By.xpath("//*[@id=\"select-surgicalhistory\"]/li[4]")).click();
				driver.findElement(By.xpath("//*[@id=\"select-surgicalhistory\"]/li[7]")).click();
				driver.findElement(By.xpath("//*[@id=\"surgicalhistory_dataadd\"]")).click();
				driver.findElement(By.xpath("//*[@id=\"allergies_add\"]")).click();
				driver.findElement(By.xpath("//*[@id=\"select_allergies\"]/li[6]")).click();
				driver.findElement(By.xpath("//*[@id=\"select_allergies\"]/li[11]")).click();
				driver.findElement(By.xpath("//*[@id=\"allergies_dataadd\"]")).click();
				driver.findElement(By.xpath("//*[@id=\"familyhistory_none\"]")).click();
				driver.findElement(By.xpath("//*[@id=\"vaccinations_none\"]")).click();
				driver.findElement(By.xpath("//*[@id=\"page_submitbtn\"]/div/input")).click();
				
				System.out.println("PATIENT RX INFO");
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"pharmacy\"]/div[7]/div/input")).click();
				
				System.out.println("ADDITIONAL SOCIAL HISTORY");
				
				/*driver.findElement(By.xpath("//*[@id=\"answer_1818_0\"]")).click();
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);*/
				driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[8]/div/input")).click();
				
				System.out.println("MEDICAL HISTORY FORM");
				/*driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[1]/div/div[1]")).click();
				driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[1]/div/div[8]")).click();
				jse.executeScript("scroll(0,500);");*/
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[4]/div/input")).click();
				
				System.out.println("PHQ-9");
				
				driver.findElement(By.xpath("//*[@id=\"answer_844_0\"]")).click();
				driver.findElement(By.xpath("//*[@id=\"answer_880_1\"]")).click();
				//JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("scroll(0,3000);");
				driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[14]/div/input")).click();
				
				System.out.println("GAD-7");
				//JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("scroll(0,2000);");
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[11]/div/input")).click();
				
				System.out.println("ENCOUNTER QUESTIONNAIRE ");
				//JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("scroll(0,1000);");
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[9]/div/input")).click();
				
				driver.findElement(By.xpath("//*[@id=\"form_mdynamicqadone\"]/div/div/div[2]/span/label")).click();
				driver.findElement(By.xpath("//*[@id=\"answer_861_1\"]")).click();
				driver.findElement(By.xpath("//*[@id=\"answer_noprefill_867\"]")).click();
				//JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("scroll(0, 3500);");
				driver.findElement(By.xpath("///*[@id=\"form_mdynamicqadone\"]/div/div/div[51]/div/input")).click();
				
				driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"dynamicinfo_57\"]/div[2]/div/div/a")).click();
		     
	}

}
