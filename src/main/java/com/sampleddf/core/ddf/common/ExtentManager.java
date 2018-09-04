package com.sampleddf.core.ddf.common;

//http://relevantcodes.com/Tools/ExtentReports2/javadoc/index.html?com/relevantcodes/extentreports/ExtentReports.html

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager 
{
	private static com.aventstack.extentreports.ExtentReports extent;
	static ExtentHtmlReporter htmlReporter;
	public static String ExtentreportfileName;
	
	public static ExtentReports getInstance() 
	{
		if (extent == null) 
		{
			Date d=new Date();
			ExtentreportfileName=d.toString().replace(":", "_").replace(" ", "_")+".html";
			htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\Reports\\"+ExtentreportfileName);
			
			extent = new ExtentReports ();
			extent.attachReporter(htmlReporter);
			extent.setSystemInfo("Host Name", "Rediff Money Automation");
			extent.setSystemInfo("QA", "Automation Testing");
			extent.setSystemInfo("User Name", "Tejas");
			
			htmlReporter.config().setDocumentTitle("Rediff Money Automation Test Report");
			htmlReporter.config().setReportName("Rediff Automation Test Report");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTheme(Theme.DARK);					
		}
		return extent;
	}
}