package com.sampleddf.core.ddf.mystocks;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.sampleddf.core.ddf.basecases.BaseTestcase;
import com.sampleddf.core.ddf.common.Xls_Reader;

public class AddStocks extends BaseTestcase 
{
	Xls_Reader xls = null;

	@BeforeTest()
	public void BrowserTest() throws IOException, InterruptedException
	{
		System.out.println("In Before Test AddStocks");		
		LaunchBrowser();
		LaunchURL();
		LoginAUT();
		waitForPageToLoad();
	}	
	
	@AfterTest()
	public void QuitBrowser() throws InterruptedException 
	{
		System.out.println("In After Test AddStocks");
		wait(3);
		LogoutAUT();
		CloseBrowser();	
	}
	
	@Test(priority=1)
	public void AddStocksPortfolio() throws InterruptedException, IOException
	{
		try
		{
		logger = objreports.createTest("Add Stocks TestCase");		
		logger.log(Status.INFO, "Add Stocks TestCase Starts ");
		applog.debug("Starting the Test --> Add Stocks TestCase");
		
		String TestData[][] = GenerateExcelTestData("TestData","AddStocks");
		
		//System.out.println("Excel Values are: "+TestData[1][1]);
		//System.out.println("Lenght of the Data "+TestData.length);
		
		for (int cntDataRow = 0; cntDataRow < TestData.length; cntDataRow++) 
		{
			for (int cntDataCol = 0; cntDataCol < TestData[cntDataRow].length; cntDataCol++)
			{
				String TestParameter=TestData[cntDataRow][cntDataCol];				
				System.out.println("Test Data TestParameter: Row: "+cntDataRow+" and Col: "+cntDataCol+" -> Value: "+TestParameter);										
			}			
		}	
		
		for(int cntIterations=0;cntIterations <TestData.length ;cntIterations++)
		{
			logger.log(Status.INFO, "Test Iteration Starts on Multiple set of Data for AddStocks At Data Row: "+(cntIterations+1));
			logger.log(Status.INFO, "TestData for Row --> "+TestData[cntIterations][0]+" : "+TestData[cntIterations][1]+" : "+TestData[cntIterations][2]+" : "+TestData[cntIterations][3]);
						
			driver.findElement(By.xpath(OR.getProperty("xpath_btnAddstock"))).click();
			driver.findElement(By.xpath(OR.getProperty("xpath_txtStockName"))).sendKeys(TestData[cntIterations][0]);
			wait(1);
			driver.findElement(By.xpath(OR.getProperty("xpath_txtStockName"))).sendKeys(Keys.ENTER);
			driver.findElement(By.xpath(OR.getProperty("xpath_txtDateofpurchase"))).sendKeys("07-05-2018");
			driver.findElement(By.xpath(OR.getProperty("xpath_txtQuantity"))).sendKeys(TestData[cntIterations][2]);
			driver.findElement(By.xpath(OR.getProperty("xpath_txtPurchasePrice"))).sendKeys(TestData[cntIterations][3]);
			driver.findElement(By.xpath(OR.getProperty("xpath_lblExchBSE"))).click();
			wait(1);
			takeScreenShot();
			driver.findElement(By.xpath(OR.getProperty("xpath_btnAddstockentry"))).click();
			wait(4);
						
			if(driver.findElement(By.xpath(OR.getProperty("xpath_btnAddstock"))).isDisplayed() == true)
			{
				logger.log(Status.PASS, "Record Added Successfully");
				applog.debug("PASSED: Record Added Successfully");	
				takeScreenShot();
			}			
			else					
			{
				logger.log(Status.FAIL, "Failed to Add Record");
				applog.debug("PASSED: Failed to Add Record");
				takeScreenShot();
			}				
		}					
		wait(3);
		applog.debug("Ending the Test --> Add Stocks TestCase");
		logger.log(Status.INFO, "Add Stocks TestCase Ends");
		}
		catch(Exception e)
		{
			logger.log(Status.ERROR, "Exception Occured: "+e);
			System.out.println("Exception: "+e);
		}
	}
	
	@Test(priority=2)
	public void AddStocksPortfolioValidate() throws InterruptedException, IOException
	{
		try
		{
		logger = objreports.createTest("Add Stocks Error Code Validation TestCase");		
		logger.log(Status.INFO, "Add Stocks Error Code Validation TestCase Starts ");
		applog.debug("Starting the Test --> Add Stocks Error Code Validation TestCase");
			
		String TestData[][] = GenerateExcelTestData("TestData","AddStocksValidate");
		
		//System.out.println("Excel Values are: "+TestData[1][1]);
		//System.out.println("Lenght of the Data "+TestData.length);
		
		for (int cntDataRow = 0; cntDataRow < TestData.length; cntDataRow++) 
		{
			for (int cntDataCol = 0; cntDataCol < TestData[cntDataRow].length; cntDataCol++)
			{
				String TestParameter=TestData[cntDataRow][cntDataCol];				
				System.out.println("Test Data TestParameter: Row: "+cntDataRow+"Col: "+cntDataCol+" -> Value: "+TestParameter);
			}			
		}	
		
		for(int cntIterations=0;cntIterations <TestData.length ;cntIterations++)
		{
			logger.log(Status.INFO, "Test Iteration Starts on Multiple set of Data for Validate Error Codes At Data Row: "+(cntIterations+1));
			logger.log(Status.INFO, "TestData for Row --> "+TestData[cntIterations][0]+" : "+TestData[cntIterations][1]+" : "+TestData[cntIterations][2]+" : "+TestData[cntIterations][3]);
					
			driver.findElement(By.xpath(OR.getProperty("xpath_btnAddstock"))).click();
			driver.findElement(By.xpath(OR.getProperty("xpath_txtStockName"))).sendKeys(TestData[cntIterations][0]);
			wait(1);
			driver.findElement(By.xpath(OR.getProperty("xpath_txtStockName"))).sendKeys(Keys.ENTER);
			if(cntIterations>1)
			{
				driver.findElement(By.xpath(OR.getProperty("xpath_txtDateofpurchase"))).sendKeys("17-03-2018");				
			}
			else
			{
				driver.findElement(By.xpath(OR.getProperty("xpath_txtDateofpurchase"))).sendKeys(TestData[cntIterations][1]);
			}		
			
			driver.findElement(By.xpath(OR.getProperty("xpath_txtQuantity"))).sendKeys(TestData[cntIterations][2]);
			driver.findElement(By.xpath(OR.getProperty("xpath_txtPurchasePrice"))).sendKeys(TestData[cntIterations][3]);
			driver.findElement(By.xpath(OR.getProperty("xpath_lblExchBSE"))).click();
			wait(1);
			
			driver.findElement(By.xpath(OR.getProperty("xpath_btnAddstockentry"))).click();
			wait(4);
			
			if(checkpointprop.getProperty("msgStockErrorCompany").equalsIgnoreCase(driver.findElement(By.xpath(OR.getProperty("xpath_msgStockAddError"))).getText()))
			{
				logger.log(Status.PASS, checkpointprop.getProperty("msgStockErrorCompany"));
				applog.debug("PASSED: "+checkpointprop.getProperty("msgStockErrorCompany"));
				takeScreenShot();
				driver.findElement(By.xpath(OR.getProperty("xpath_btnAddStockClose"))).click();
			}
			else if(checkpointprop.getProperty("msgStockErrorDate").equalsIgnoreCase(driver.findElement(By.xpath(OR.getProperty("xpath_msgStockAddError"))).getText()))
			{
				logger.log(Status.PASS, checkpointprop.getProperty("msgStockErrorDate"));
				applog.debug("PASSED: "+checkpointprop.getProperty("msgStockErrorDate"));
				takeScreenShot();
				driver.findElement(By.xpath(OR.getProperty("xpath_btnAddStockClose"))).click();
			}
			else if(checkpointprop.getProperty("msgStockErrorQuantity").equalsIgnoreCase(driver.findElement(By.xpath(OR.getProperty("xpath_msgStockAddError"))).getText()))
			{
				logger.log(Status.PASS, checkpointprop.getProperty("msgStockErrorQuantity"));
				applog.debug("PASSED: "+checkpointprop.getProperty("msgStockErrorQuantity"));
				takeScreenShot();
				driver.findElement(By.xpath(OR.getProperty("xpath_btnAddStockClose"))).click();
			}
			else if(checkpointprop.getProperty("msgStockErrorPrice").equalsIgnoreCase(driver.findElement(By.xpath(OR.getProperty("xpath_msgStockAddError"))).getText()))
			{
				logger.log(Status.PASS, checkpointprop.getProperty("msgStockErrorPrice"));
				applog.debug("PASSED: "+checkpointprop.getProperty("msgStockErrorPrice"));
				takeScreenShot();
				driver.findElement(By.xpath(OR.getProperty("xpath_btnAddStockClose"))).click();
			}
			else 
			{				
				logger.log(Status.FAIL, "Failed to Validate Record");
				applog.debug("PASSED: Failed to Validate Record");
				takeScreenShot();
			}
		}	
				
		wait(3);		
		applog.debug("Ending the Test --> Add Stocks Error Code Validation");
		logger.log(Status.INFO, "Add Stocks Error Code Validation Ends");
		}
		catch(Exception e)
		{
			logger.log(Status.ERROR, "Exception Occured: "+e);
			System.out.println("Exception: "+e);
		}
	}
	
	
}
