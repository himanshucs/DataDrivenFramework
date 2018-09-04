package com.sampleddf.core.ddf.loginpage;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.sampleddf.core.ddf.basecases.BaseTestcase;
import com.sampleddf.core.ddf.common.DataUtil;
import com.sampleddf.core.ddf.common.Xls_Reader;


public class LoginPortfolio extends BaseTestcase
{
	Xls_Reader xls;
	
	@BeforeTest()
	public void BrowserTest() throws IOException, InterruptedException
	{
		System.out.println("In Before Test");		
		LaunchBrowser();
		LaunchURL();
	}
	
	
	@AfterTest()
	public void QuitBrowser() throws InterruptedException 
	{
		System.out.println("In After Test");
		CloseBrowser();
		//objreports.endTest(logger);
		//objreports.flush();
	}
	
	@Test(priority=3,dataProvider="getData")
	public void LoginCredentialsNeg(String username, String password) throws IOException, InterruptedException
	{
		try
		{				
		logger = objreports.createTest("Login Page Negative Test");		
		logger.log(Status.INFO, "Login Case Starts for Username: "+username+" And Password: "+password);
		applog.debug("Starting the Test --> Entering User Cred");
		
		PropertiesfileInit();
		driver.findElement(By.linkText(OR.getProperty("linktext_Home"))).click();
		
		driver.findElement(By.linkText(OR.getProperty("linktext_SignIn"))).click();
		driver.findElement(By.xpath(OR.getProperty("xpath_LoginUsername"))).sendKeys(username);
		driver.findElement(By.xpath(OR.getProperty("xpath_LoginButtonEmail"))).click();
		
		
		Thread.sleep(3000);
		//System.out.println("Element display----------------->"+driver.findElement(By.xpath(OR.getProperty("label_MsgInvalidEmail"))).isEnabled());
		//System.out.println("Element display----------------->"+driver.findElement(By.xpath(OR.getProperty("label_MsgInvalidEmail"))).getSize());
		
		System.out.println("Style of Element---------->"+driver.findElement(By.xpath("//*[@id='message_shows']")).getAttribute("style"));
		System.out.println("Style of Element---------->"+driver.findElement(By.xpath("//*[@id='message_shows']")).getAttribute("id"));
		System.out.println("Style of Element---------->"+driver.findElement(By.xpath("//*[@id='message_shows']")).getSize());
				
		
		if(driver.findElement(By.xpath(OR.getProperty("label_MsgInvalidEmail"))).getText().equals(checkpointprop.getProperty("msgInvalidEmail")))
		{
			logger.log(Status.PASS, "Username Entered as: "+username+" Invalid Email ID");
			applog.debug("PASSED: Username Entered as: "+username+" Invalid Email ID");
			takeScreenShot();		
		}
		else
		{
			logger.log(Status.FAIL, "Username Entered as: "+username+" Wrong Email ID Accepted");
			applog.debug("FAILEd: Username Entered as: "+username+" Wrong Email ID Accepted");
			takeScreenShot();
		}	
		
		if(driver.findElement(By.xpath(OR.getProperty("xpath_LoginPassword"))).isDisplayed() == true)
		{
			driver.findElement(By.xpath(OR.getProperty("xpath_LoginPassword"))).sendKeys(password);			
			driver.findElement(By.xpath(OR.getProperty("xpath_LoginCheckBox"))).click();
			driver.findElement(By.xpath(OR.getProperty("xpath_LoginButtonPassword"))).click();
			
			if(driver.findElement(By.xpath(OR.getProperty("label_MsgInvalidEmail"))).getText().equals(checkpointprop.getProperty("msgInvalidPassword")))
			{
				logger.log(Status.PASS, "Password Entered as: "+password+" Invalid Password");
				applog.debug("PASSED: Password Entered as: "+password+" Invalid Password");
				takeScreenShot();
			}
			else
			{
				Thread.sleep(2000);
				String SignOutText = driver.findElement(By.linkText(OR.getProperty("linktext_SignOut"))).getText();
				
				if(SignOutText.equals(checkpointprop.getProperty("textSignOut")))
				{
					logger.log(Status.FAIL, "Password Entered as: "+password+" Invalid Password Accepted");
					applog.debug("FAILED: Password Entered as: "+password+" Invalid Password Accepted");
					takeScreenShot();
				}
				else
				{
					applog.debug("FAILED: Something went wrong with the Invalid Password");		
					logger.log(Status.WARNING, "Something went wrong with the Invalid Password");
					takeScreenShot();
				}				
			}				
		}	
		else			
		{
			logger.log(Status.PASS, "Password Entered as: "+password+" Password field Does not Exist");
			applog.debug("PASSED: Password Entered as: "+password+" Password field Does not Exist");
		}
		
		applog.debug("Ending the Test --> Entering User Cred");
		logger.log(Status.INFO, "Login Case Ends");
		
		//Go to home page for next iteration
		driver.findElement(By.linkText(OR.getProperty("linktext_Home"))).click();
		Thread.sleep(3000);
		}
		catch(Exception e)
		{
			logger.log(Status.ERROR, "Exception Occured: "+e);
			System.out.println("Exception: "+e);
		}
	}
	
	@Test(priority=1,dataProvider="getDataPos")
	public void LoginCredentials(String username, String password) throws IOException, InterruptedException
	{
		try
		{
		logger = objreports.createTest("Login Page Positive Test");		
		logger.log(Status.INFO, "Login Case Starts for Username: "+username+" And Password: "+password);
		applog.debug("Starting the Test --> Entering User Cred");
		
		PropertiesfileInit();		
		
		driver.findElement(By.linkText(OR.getProperty("linktext_SignIn"))).click();
		driver.findElement(By.xpath(OR.getProperty("xpath_LoginUsername"))).sendKeys(username);
		driver.findElement(By.xpath(OR.getProperty("xpath_LoginButtonEmail"))).click();
		
		driver.findElement(By.xpath(OR.getProperty("xpath_LoginPassword"))).sendKeys(password);
		
		driver.findElement(By.xpath(OR.getProperty("xpath_LoginCheckBox"))).click();
		
		driver.findElement(By.xpath(OR.getProperty("xpath_LoginButtonPassword"))).click();
				
		Thread.sleep(2000);
		String SignOutText = driver.findElement(By.linkText(OR.getProperty("linktext_SignOut"))).getText();
		
		if(SignOutText.equals(checkpointprop.getProperty("textSignOut")))
		{
			logger.log(Status.PASS, "User Logged in Successfully");
			applog.debug("PASSED: User Logged in Successfully");
			takeScreenShot();
		}
		else
		{
			logger.log(Status.FAIL, "User NOT Logged in Successfully");
			applog.debug("PASSED: User NOT Logged in Successfully");
			takeScreenShot();
		}	
		
		applog.debug("Ending the Test --> Entering User Cred");
		logger.log(Status.INFO, "Login Case Ends");
		
		//Go to home page for next iteration
		driver.findElement(By.linkText(OR.getProperty("linktext_Home"))).click();
		Thread.sleep(3000);
		}
		catch(Exception e)
		{
			logger.log(Status.ERROR, "Exception Occured: "+e);
			System.out.println("Exception: "+e);
		}		
	}	
	
	@Test(priority=2,dependsOnMethods={"LoginCredentials"})
	public void LogOut() throws InterruptedException
	{
		logger = objreports.createTest("LogOut Page Test");	
		logger.log(Status.INFO, "LogOut Case Starts");
		applog.debug("Starting the Test --> LogOut Page Test");
		String SignOutText = driver.findElement(By.linkText(OR.getProperty("linktext_SignOut"))).getText();
				
		if(SignOutText.equals(checkpointprop.getProperty("textSignOut")))
		{
			driver.findElement(By.linkText(OR.getProperty("linktext_SignOut"))).click();
			Thread.sleep(3000);
			
			String SignOutMessage = driver.findElement(By.xpath(OR.getProperty("label_MessageSignOut"))).getText();
			
			if(SignOutMessage.equals(checkpointprop.getProperty("messageSignOut")))
			{
				applog.debug("PASSED: Logout Case Passed - User Logged Out Successfully");	
				logger.log(Status.PASS, "Logout Case Passed - User Logged Out Successfully");	
				takeScreenShot();				
			}
			else
			{
				applog.debug("FAILED: Logout Failed - Logout is not successful");		
				logger.log(Status.FAIL, "Login Failed - Logout is not successful");
				takeScreenShot();
			}			
		}
		else
		{
			applog.debug("FAILED: Logout cannot be performed - User have to Login first");		
			logger.log(Status.WARNING, "Logout cannot be performed - User have to Login first");
			takeScreenShot();
		}
		
		driver.findElement(By.linkText(OR.getProperty("linktext_Home"))).click();
		Thread.sleep(3000);
		logger.log(Status.INFO, "LogOut Case Ends");
		applog.debug("Ending the Test --> LogOut Page Test");		
	}	

	//Dataprovider for Positive Test
	@DataProvider
	public Object[][] getDataPos()
	{			
		xls = new Xls_Reader(System.getProperty("user.dir")+"\\TestData\\Data.xlsx");
		return DataUtil.getTestData(xls, "LoginTestPos");		
	}	
	
	
	//Dataprovider for Negative Test
	@DataProvider
	public Object[][] getData()
	{			
		xls = new Xls_Reader(System.getProperty("user.dir")+"\\TestData\\Data.xlsx");
		return DataUtil.getTestData(xls, "LoginTestNeg");		
	}	
}
