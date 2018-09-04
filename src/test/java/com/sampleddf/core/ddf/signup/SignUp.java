/*
Adding the test cases fro the sign up page.
*/

package com.sampleddf.core.ddf.signup;

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

public class SignUp extends BaseTestcase {

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
	
	@Test(priority=1,dataProvider="getDataPos")
	public void SignUpCredentials(String Fullname, String Email,String Password ) throws IOException, InterruptedException
	{
		logger = objreports.createTest("Sign Up Page Positive Test");		
		logger.log(Status.INFO, "Login Case Starts for Username: "+Fullname+" And Email: "+Email);
		applog.debug("Starting the Test --> Entering Fullname");
		
		PropertiesfileInit();		
		
		driver.findElement(By.linkText(OR.getProperty("linktext_SignUp"))).click();
		driver.findElement(By.xpath(OR.getProperty("xpath_Fullname"))).sendKeys(Fullname);
		driver.findElement(By.xpath(OR.getProperty("xpath_Emailid"))).sendKeys(Email);
		driver.findElement(By.xpath(OR.getProperty("xpath_Password"))).sendKeys(Password);
		driver.findElement(By.xpath(OR.getProperty("xpath_Submit"))).click();
		
	}


	//Dataprovider for Positive Test
	@DataProvider
	public Object[][] getDataPos()
	{			
		xls = new Xls_Reader(System.getProperty("user.dir")+"\\TestData\\Data.xlsx");
		return DataUtil.getTestData(xls, "SignUp");		
	}	
	
}
