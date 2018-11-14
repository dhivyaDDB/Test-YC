package com.yosi.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.util.SystemOutLogger;

public class Validator {
  
  //note in this expression below, each back slash is an escape character, so 
  //the regular expression should be "\b[0-9]{5}(?:-[0-9]{4})?\b"\ d{ 5} (-\ d{ 4} )?
	//String zipcode = "10036";

  public boolean zipcode_validate (String i)
  {
	  String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
		
		 
		Pattern pattern = Pattern.compile(regex);
	
	
		    Matcher matcher = pattern.matcher(i);
		    Boolean zip_match=matcher.matches();
		    System.out.println(matcher.matches());
			return zip_match;		
		
  }
  
  public boolean ssn_validate (String ssn)
  {
	  String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
		
		 
		Pattern pattern = Pattern.compile(regex);
	
	
		    Matcher matcher = pattern.matcher(ssn);
		    Boolean ssn_match=matcher.matches();
		    System.out.println(matcher.matches());
			return ssn_match;		
		
  }
  
  public boolean mob_num_validate (String mob_num)
  {
	  String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
		
		 
		Pattern pattern = Pattern.compile(regex);
	
	
		    Matcher matcher = pattern.matcher(mob_num);
		    Boolean mob_num_match=matcher.matches();
		    System.out.println(matcher.matches());
			return mob_num_match;		
		
  }
  public boolean firstname_validation(String fname) {
	  

		String regx = "[a-zA-Z]+\\.?";
	    Pattern f_pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
	    Matcher f_matcher = f_pattern.matcher(fname);
	    Boolean f_bol =f_matcher.find();
	    System.out.println(f_bol);
	return f_bol;
	  
  }
  
 
  
  
  
  
}
