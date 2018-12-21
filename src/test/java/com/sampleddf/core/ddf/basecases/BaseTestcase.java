package com.sampleddf.core.ddf.basecases;

import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.Status;
import com.sampleddf.core.ddf.common.ExtentManager;
import com.sampleddf.core.ddf.common.Xls_Reader;

public class BaseTestcase 
{
	//Initialize/declarations

	public WebDriver driver = null;	
	Xls_Reader xls = null;
	
	public Properties prop = new Properties();
	public Properties checkpointprop = new Properties();
	public Properties OR = new Properties();
	public Logger applog = Logger.getLogger("devpinoyLogger");
	public com.aventstack.extentreports.ExtentReports objreports = ExtentManager.getInstance();
	public com.aventstack.extentreports.ExtentTest logger;		
		
	public void LaunchBrowser() throws IOException, InterruptedException
	{
		logger=objreports.createTest("Browser Launch Test");
		applog.debug("Starting the Test --> Launch Browser");		
		PropertiesfileInit();
				
		String Browser = prop.getProperty("Browser");
		System.out.println(Browser);
				
		if (Browser.equals("firefox"))
		{
			try
			{
				System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\BrowserDrivers\\FirefoxDriver\\geckodriver.exe");
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"null");
				driver = new FirefoxDriver();	
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);					
				
			}catch(Exception e)
			{
				applog.error("Browser Launching error", e);
			}				
		}
		else if (Browser.equals("chrome"))
		{
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\BrowserDrivers\\ChromeDriver\\chromedriver.exe");
			driver = new ChromeDriver();	
			driver.manage().window().maximize();
		}
		else if (Browser.equals("ie"))
		{
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"\\BrowserDrivers\\IEDriver\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();	
		}
		else if (Browser.equals("edge"))
		{
			System.setProperty("webdriver.edge.driver",System.getProperty("user.dir")+"\\BrowserDrivers\\EdgeDriver\\MicrosoftWebDriver.exe");
			driver = new EdgeDriver();	
		}
				
		Set<String> winIds = driver.getWindowHandles();
		System.out.println("Total windows - "+ winIds.size());
		
		if(winIds.size()>=1)
		{				
			logger.log(Status.PASS, "Browser Launch Successful...");			
			applog.debug("PASSED: Browser Launch Successful...");	
			takeScreenShot();
		}
		else
		{
			logger.log(Status.FAIL, "Browser Failed to Launch --> Cannot proceed ahead of Test");
			//reptest.log(LogStatus.FAIL, "Browser Launch Successful...");
			applog.debug("FAILED: Browser Launch Failed...");
			Assert.assertTrue(winIds.size()<1, "Browser Failed to Launched");
			takeScreenShot();
		}			
		applog.debug("Ending the Test --> Launch Browser");
	}	
	
	public void LaunchURL() throws InterruptedException, IOException
	{
		logger=objreports.createTest("URL Launch Test");
		applog.debug("Starting the Test -> URL Launch");
		
		System.out.println(checkpointprop.getProperty("pagetitleURLLaunch"));
		driver.navigate().to(prop.getProperty("URL"));
		Thread.sleep(3000); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		if(driver.getTitle().contains(checkpointprop.getProperty("pagetitleURLLaunch")))
		{
			applog.debug("PASSED: URL Launched Successfully");
			logger.log(Status.PASS, "URL Launched Successfully");
			takeScreenShot();
		}
		else
		{
			applog.debug("URL Launched but Not Expected.Currently -> "+driver.getTitle());
			logger.log(Status.FAIL, "URL Launched but Not Expected.Currently -> "+driver.getTitle());
			takeScreenShot();
			Assert.assertEquals(driver.getTitle(),checkpointprop.getProperty("pagetitleURLLaunch"), "Error Occured - URL Failed to Launch");
		}			
		applog.debug("Ending the Test -> URL Launch");		
	}
	
	
	public void CloseBrowser() throws InterruptedException
	{
		Thread.sleep(3000);
		driver.quit();
	}
	
	public void LoginAUT()
	{
		try
		{
		logger = objreports.createTest("Login to AUT");		
		logger.log(Status.INFO, "Login to AUT Case Starts for:  "+prop.getProperty("URL"));
		applog.debug("Starting the Test --> Entering User Cred");
		
		PropertiesfileInit();
		
		driver.findElement(By.linkText(OR.getProperty("linktext_SignIn"))).click();
		driver.findElement(By.xpath(OR.getProperty("xpath_LoginUsername"))).sendKeys(prop.getProperty("LoginUsername"));
		driver.findElement(By.xpath(OR.getProperty("xpath_LoginButtonEmail"))).click();
		
		Thread.sleep(2000);
		driver.findElement(By.xpath(OR.getProperty("xpath_LoginPassword"))).sendKeys(prop.getProperty("LoginPassword"));
		
		driver.findElement(By.xpath(OR.getProperty("xpath_LoginCheckBox"))).click();
		
		driver.findElement(By.xpath(OR.getProperty("xpath_LoginButtonPassword"))).click();
				
		Thread.sleep(4000);
		String SignOutText = driver.findElement(By.linkText(OR.getProperty("linktext_SignOut"))).getText();
		
		System.out.println(driver.findElement(By.linkText(OR.getProperty("linktext_SignOut"))).isDisplayed());
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
		logger.log(Status.INFO, "Login to AUT Case Ends");
		}
		catch(Exception e)
		{
			logger.log(Status.ERROR, "Exception Occured: "+e);
			System.out.println("Exception: "+e);
		}
	}
	
	public void LogoutAUT() 
	{
		try
		{
		logger = objreports.createTest("LogOut AUT Test");	
		logger.log(Status.INFO, "LogOut AUT Case Starts");
		applog.debug("Starting the Test --> LogOut AUT Test");
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
		logger.log(Status.INFO, "LogOut AUT Case Ends");
		applog.debug("Ending the Test --> LogOut AUT Test");
		}
		catch(Exception e)
		{
			logger.log(Status.ERROR, "Exception Occured: "+e);
			System.out.println("Exception: "+e);
		}
	}
	
	public void waitForPageToLoad()
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		int i=0;
		String state=null;
		Long d=null;
		
		while(i!=10)
		{
			state = (String)js.executeScript("return document.readyState;");
			System.out.println(state);	
			if(state.equals("complete"))
			{	
				logger.log(Status.PASS, "Java Script Page Load Status: "+state);
				break;
			}	
			else
				wait(2);	
			i++;
		}
		//logger.log(Status.ERROR, "Java Script Page Load Status: "+state);
		// check for jquery status
		i=0;
		while(i!=10)
		{
			d= (Long) js.executeScript("return jQuery.active;");
			System.out.println(d);
			if(d.longValue() == 0 )
			{	
				logger.log(Status.PASS, "JQuery Page Load Status: "+d);
				break;
			}	 	
			else
				 wait(2);
			 i++;				
		}
		//logger.log(Status.ERROR, "JQuery Page Load Status: "+d);
	}
	
	public void wait(int time)
	{
		try
		{
			Thread.sleep(time*1000);
		}
		catch (InterruptedException e) 
		{			
			logger.log(Status.ERROR, "Exception Occured: "+e);
		}
	}
	
	public boolean isElementPresent(String ElementExpression)
	{
		int s = driver.findElements(By.xpath(ElementExpression)).size();
		if(s==0)
			return false;
		else
			return true;
	}

	public String[][] GenerateExcelTestData(String SheetName, String testCaseName)
	{
		xls = new Xls_Reader(System.getProperty("user.dir")+"\\TestData\\Data.xlsx");
		String Celltextmy= xls.getCellData("TestData", 1,3);
		System.out.println("==============>"+Celltextmy);
		
		System.out.println("In the Get Generate Test Data from Excel function");
		String sheetName=SheetName;
		
		// reads data for only testCaseName		
		int testStartRowNum=1;
		while(!xls.getCellData(sheetName, 1, testStartRowNum).equals(testCaseName))
		{
			testStartRowNum++;
		}
		System.out.println("Required TestCase Found for execution at row - "+ testStartRowNum);
		int colStartRowNum=testStartRowNum+1;
		int dataStartRowNum=testStartRowNum+2;
		
		// calculate rows of data
		int rows=0;
		while(!xls.getCellData(sheetName, 1, dataStartRowNum+rows).equals(""))
		{
			rows++;
		}
		System.out.println("Total Data rows are  - "+rows );
		
		//calculate total cols
		int cols=1;
		while(!xls.getCellData(sheetName, cols, colStartRowNum).equals(""))
		{
			cols++;
		}
		cols=cols-1;
		System.out.println("Total Data cols are  - "+cols);
		String[][] data = new String[rows][cols];
		
		//read the data Logic
		int dataRow=0;
		int datacol=0;
		
		for(int rNum=dataStartRowNum;rNum<dataStartRowNum+rows;rNum++)
		{			
			for(int cNum=1;cNum<=cols;cNum++)
			{
				System.out.println("At DP Row:"+dataRow+"    At DP Col: "+datacol);
					
				String value= xls.getCellData(sheetName, cNum, rNum);
				
				System.out.println("Cell value is: "+value);
				data[dataRow][datacol] =value;
				System.out.println("DP Value is:"+data[dataRow][datacol]);
				System.out.println("------------------------------------------");
				datacol++;
			}			
			dataRow++;
			datacol=0;			
		}
		return data;
		
	}
	public void takeScreenShot()
	{			
		Date d=new Date();
		String screenshotfilename=d.toString().replace(":", "_").replace(" ", "_")+".png";
		//System.out.println(screenshotfilename);
		
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try 
		{			
			//step to import the jar file - commons-io-2.6.jar
			//System.out.println(System.getProperty("user.dir"));
			FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir")+"\\Screenshots\\"+screenshotfilename));
			applog.debug("Screenshot Taken: "+System.getProperty("user.dir")+"\\Screenshots\\"+screenshotfilename);
			
			logger.log(Status.INFO, "Screenshot - "+logger.addScreenCaptureFromPath(System.getProperty("user.dir")+"\\Screenshots\\"+screenshotfilename));			
		}
		catch (IOException e) 
		{	
			applog.debug("Screenshot Failed Exception"+e);
			logger.log(Status.ERROR,"Screenshot Exception: "+e);
			e.printStackTrace();
		}
	}
	
	public void SendEmail(String FinalReportFileName)
	{
		logger = objreports.createTest("Email Report");
		
		// Create object of Property file
		Properties props = new Properties();

		// this will set host of server- you can change based on your requirement 
		props.put("mail.smtp.host", "smtp.gmail.com");

		// set the port of socket factory 
		props.put("mail.smtp.socketFactory.port", "465");

		// set socket factory
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

		// set the authentication to true
		props.put("mail.smtp.auth", "true");

		// set the port of SMTP server
		props.put("mail.smtp.port", "465");

		// This will handle the complete authentication
		Session session = Session.getDefaultInstance
		(props,
		new javax.mail.Authenticator() 
		{				
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				return new PasswordAuthentication(prop.getProperty("EmailUsername"), prop.getProperty("EmailPassword"));
			}

		});

		try {

			// Create object of MimeMessage class
			Message message = new MimeMessage(session);

			// Set the from address
			message.setFrom(new InternetAddress("tejtiks@gmail.com"));

			// Set the recipient address
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("tejtiks@gmail.com"));
            
                        // Add the subject link
			message.setSubject("Rediff Automation Test Report");

			// Create object to add multimedia type content
			BodyPart messageBodyPart1 = new MimeBodyPart();

			// Set the body of email
			messageBodyPart1.setText("Rediff Automation Test Report is Atached");

			// Create another object to add another content
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			// Mention the file which you want to send
			String filename = System.getProperty("user.dir")+"\\Reports\\"+FinalReportFileName;
			System.out.println(filename);
			// Create data source and pass the filename
			DataSource source = new FileDataSource(filename);

			// set the handler
			messageBodyPart2.setDataHandler(new DataHandler(source));

			// set the file
			messageBodyPart2.setFileName(filename);

			// Create object of MimeMultipart class
			Multipart multipart = new MimeMultipart();

			// add body part 1
			multipart.addBodyPart(messageBodyPart2);

			// add body part 2
			multipart.addBodyPart(messageBodyPart1);

			// set the content
			message.setContent(multipart);

			// finally send the email
			Transport.send(message);
			
			logger.log(Status.INFO, "Report Sent on Email");
			System.out.println("=====Email Sent=====");

		}
		catch (MessagingException e)
		{
			throw new RuntimeException(e);
			
		}
	}
	
	public void PropertiesfileInit() throws IOException
	{
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
		prop.load(fs);
		
		FileInputStream fscheckpoint = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\checkpoints.properties");
		checkpointprop.load(fscheckpoint);
		
		FileInputStream fsOR = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\OR.properties");
		OR.load(fsOR);
	}
	
	
	@BeforeSuite()
	public void Emailingini()
	{
		System.out.println("In Before Suit");
	}		
	
	@AfterSuite()
	public void Emailing() throws InterruptedException
	{
		
		System.out.println("In Email Report Test");
		objreports.flush();
		Thread.sleep(5000);
		System.out.println("Report Email file Name"+ExtentManager.ExtentreportfileName);
		//SendEmail(ExtentManager.ExtentreportfileName);			
	}
}
