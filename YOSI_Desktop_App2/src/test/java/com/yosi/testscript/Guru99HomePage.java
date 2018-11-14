package com.yosi.testscript;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Guru99HomePage 
	
	{

	    
	        
	        /**

	         * All WebElements are identified by @FindBy annotation

	         */

	        WebDriver driver;

	        @FindBy(name="uid")

	        WebElement user99GuruName;

	        

	        @FindBy(name="password")

	        WebElement password99Guru;

	        

	        @FindBy(className="barone")

	        WebElement titleText;

	        

	        @FindBy(name="btnLogin")

	        WebElement login;

	        

	        public Guru99HomePage(WebDriver driver){

	            this.driver = driver;

	            //This initElements method will create all WebElements

	            PageFactory.initElements(driver, this);

	        }

	        //Set user name in textbox

	        public void setUserName(String strUserName){

	            user99GuruName.sendKeys(strUserName);

	            

	        }

	        

	        //Set password in password textbox

	        public void setPassword(String strPassword){

	        password99Guru.sendKeys(strPassword);

	        }

	        

	        //Click on login button

	        public void clickLogin(){

	                login.click();

	}
	}


