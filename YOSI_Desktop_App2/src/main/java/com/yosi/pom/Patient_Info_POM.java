package com.yosi.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.yosi.base.YosiBaseClass;
import com.yosi.util.Log;

public class Patient_Info_POM extends YosiBaseClass{
	
	private static WebElement element = null;
	
	/*@FindBy(xpath = "//*[@id=\"firstname\"]")
	 WebElement fname;
	

	@FindBy(xpath = "//*[@id=\"lastname\"]")
	 WebElement lname;
	
	@FindBy(xpath = "//*[@id=\"select_gender_option1\"]") 
	 WebElement gender;
	
	@FindBy(xpath = "//*[@id=\"date\"]")
	 WebElement dob;
	
	@FindBy(id = "address1")
	 WebElement address1;
	
	@FindBy(id = "address2")
	 WebElement address2;
	
	@FindBy(id = "zipcode")
	 WebElement zipcode;*/
	
	
	
	public static  String p_fname(WebDriver driver){
		 
		String fname = driver.findElement(By.xpath("//*[@id=\"firstname\"]")).getAttribute("value");
	 
	    return fname;
	 
	    }
	
	public static  String p_lname(WebDriver driver){
		 
		String fname = driver.findElement(By.xpath("//*[@id=\"lastname+ \"]")).getAttribute("value");
	 
	    return fname;
	 
	    }
	
	// Initializing the Page Objects:
		public Patient_Info_POM() {
			PageFactory.initElements(driver, this);
		}
	
	public  void patient_info_fill()
	{
		/*System.out.println("Frist name :"+fname.getAttribute("value"));
		System.out.println("Last name :"+lname.getAttribute("value"));
		System.out.println("Gender :"+gender.getAttribute("value"));
		System.out.println("Gender :"+dob.getAttribute("value"));*/
		
		/*address1.click();
		address1.clear();
		address1.sendKeys("56 river cross road");
		address2.click();
		address2.clear();	
		address2.sendKeys("DX");	
		zipcode.click();
		zipcode.clear();
		zipcode.sendKeys("10036");*/
		
		
	}
	
	
	public  void clickOnNextButton_Collbra_Type2()
	{
		driver.findElement(By.xpath("//form[@id='form_mpersonal_info']/div/div[7]/div/input")).click();	
	}
	
	public void clickOnNextButton_Collbra_Type1()
	{
		
		driver.findElement(By.xpath("//form[@id='form_mpersonal_info']/div/div[7]/div/input")).click();	
	}
	
	
	
}
