package com.yosi.util;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public static XSSFSheet ExcelWSheet;

	public static XSSFWorkbook ExcelWBook;

	public static XSSFCell Cell;

	public static XSSFRow Row;
	  
	public static DataFormatter formatter= new DataFormatter();

public static Object[][] getTableArray(String fileInputStream, String SheetName) throws Exception {   

	// FileInputStream fileInputStream= new FileInputStream(file_location); //Excel sheet file location get mentioned here
     ExcelWBook = new XSSFWorkbook (fileInputStream); //get my workbook 
     ExcelWSheet=ExcelWBook.getSheet(SheetName);// get my sheet from workbook
     XSSFRow Row=ExcelWSheet.getRow(1);     //get my Row which start from 0   
  
     int RowNum = ExcelWSheet.getPhysicalNumberOfRows();// count my number of Rows
     int ColNum= Row.getLastCellNum(); // get last ColNum 
      
     Object Data[][]= new Object[RowNum-1][ColNum]; // pass my  count data in array
      
         for(int i=0; i<RowNum-1; i++) //Loop work for Rows
         {  
       	  XSSFRow row= ExcelWSheet.getRow(i+1);
              
             for (int j=0; j<ColNum; j++) //Loop work for colNum
             {
                 if(row==null)
                     Data[i][j]= "";
                 else
                 {
               	  XSSFCell cell= row.getCell(j);
                     if(cell==null)
                         Data[i][j]= ""; //if it get Null value it pass no data 
                     else
                     {
                         String value=formatter.formatCellValue(cell);
                         Data[i][j]=value; //This formatter get my all values as string i.e integer, float all type data value
                     }
                 }
             }
         }

     return Data;

	}


}

