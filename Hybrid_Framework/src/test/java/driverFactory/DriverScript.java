package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utilities.ExcelFileUtil;
import commonFunctions.FunctionLibrary;

public class DriverScript {
WebDriver driver;
String inputpath = "./FileInput/DataEngine.xlsx";
String outputpath = "./FileOutput/HybridResults.xlsx";
ExtentReports report;
ExtentTest logger; 
public void startTest() throws Throwable
{
	String Modulestatus="";
	//create object for excelfileutil class
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	String Testcases = "MasterTestCases";
	//iterate all rows in Testcases sheet
	for (int i=1;i<=xl.rowCount(Testcases);i++)
	{
		if(xl.getCellData(Testcases, i, 2).equalsIgnoreCase("Y"))
		{
			//read all test cases and corresponding sheets
			String TCModule = xl.getCellData(Testcases, i, 1);
			//define path of html
			report = new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
			logger = report.startTest(TCModule);
			//iterate all rows in TCModule sheet
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				//read all cells from TCModule
				String Description = xl.getCellData(TCModule, j, 0);
				String Object_Type = xl.getCellData(TCModule, j, 1);
				String Locator_Type = xl.getCellData(TCModule, j, 2);
				String Locator_Value = xl.getCellData(TCModule, j, 3);
				String Test_Data = xl.getCellData(TCModule, j, 4);
				try {
					  if (Object_Type.equalsIgnoreCase("startBrowser"))
					  {
						  driver = FunctionLibrary.startBrowser();
						  logger.log(LogStatus.INFO, Description);
					  }
					  if (Object_Type.equalsIgnoreCase("openUrl"))
					  {
						  FunctionLibrary.openUrl();
						  logger.log(LogStatus.INFO, Description);
					  }
					  if (Object_Type.equalsIgnoreCase("waitForElement"))
					  {
						  FunctionLibrary.waitForElement(Locator_Type, Locator_Value, Test_Data);
						  logger.log(LogStatus.INFO, Description);
					  }
					  if (Object_Type.equalsIgnoreCase("typeAction"))
					  {
						  FunctionLibrary.typeAction(Locator_Type, Locator_Value, Test_Data);
						  logger.log(LogStatus.INFO, Description);
					  }
					  if (Object_Type.equalsIgnoreCase("clickAction"))
					  {
						  FunctionLibrary.clickAction(Locator_Type, Locator_Value);
						  logger.log(LogStatus.INFO, Description);
					  }
					  if (Object_Type.equalsIgnoreCase("validateTitle"))
					  {
						  FunctionLibrary.validateTitle(Test_Data);
						  logger.log(LogStatus.INFO, Description);
					  }
					  if(Object_Type.equalsIgnoreCase("closeBrowser"))
					  {
						  FunctionLibrary.closeBrowser();
						  logger.log(LogStatus.INFO, Description);
					  }
					  if (Object_Type.equalsIgnoreCase("dropDownAction"))
					  {
						  FunctionLibrary.dropDownAction(Locator_Type, Locator_Value, Test_Data);
						  logger.log(LogStatus.INFO, Description);
					  }
					  if (Object_Type.equalsIgnoreCase("capatureStockNum"))
					  {
						  FunctionLibrary.captureStockNum(Locator_Type, Locator_Value);
						  logger.log(LogStatus.INFO, Description);
					  }
					  if (Object_Type.equalsIgnoreCase("stockTable"))
					  {
						  FunctionLibrary.stockTable();
						  logger.log(LogStatus.INFO, Description); 
					  }
					  if (Object_Type.equalsIgnoreCase("captureSup"))
					  {
						  FunctionLibrary.captureSup(Locator_Type, Locator_Value);
						  logger.log(LogStatus.INFO, Description); 
					  }
					  if (Object_Type.equalsIgnoreCase("supplierTable"))
					  {
						  FunctionLibrary.supplierTable();
						  logger.log(LogStatus.INFO, Description); 
					  }
					  if (Object_Type.equalsIgnoreCase("captureCus"))
					  {
						  FunctionLibrary.captureCus(Locator_Type, Locator_Value);
						  logger.log(LogStatus.INFO, Description); 
					  }
					  if (Object_Type.equalsIgnoreCase("customerTable"))
					  {
						  FunctionLibrary.customerTable();
						  logger.log(LogStatus.INFO, Description); 
					  }

				  
					  //write as pass into status cell in TCModule
					  xl.setCellData(TCModule, j, 5, "PASS", outputpath);
					  logger.log(LogStatus.PASS, Description);
					  Modulestatus="True";
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
					//write as fail into status cell in TCModule
				   	xl.setCellData(TCModule, j, 5, "FAIL", outputpath);
				    logger.log(LogStatus.FAIL, Description);
					Modulestatus="False";
				}
				if(Modulestatus.equalsIgnoreCase("True"))
				{
					//write as pass into Test case sheet
					xl.setCellData(Testcases, i, 3, "PASS", outputpath);
				}
				else
				{
					//write as fail into Test case sheet
					xl.setCellData(Testcases, i, 3, "FAIL", outputpath);
				}
				report.endTest(logger);
				report.flush();
	      	}
					
		}
		else
		{
			//write as blocked into status cell for Flag N
			xl.setCellData(Testcases, i, 3, "Blocked", outputpath);
		}
	}
	
}
}
