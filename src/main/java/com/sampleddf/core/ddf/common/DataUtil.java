package com.sampleddf.core.ddf.common;

import java.util.Hashtable;

public class DataUtil 
{	
	public static Object[][] getTestData(Xls_Reader xls, String testCaseName)
	{
		System.out.println("In the Get Test Data for Data Provider function");
		String sheetName="TestData";
		
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
		Object[][] data = new Object[rows][cols];
		
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
	
	public static boolean isRunnable(String testName, Xls_Reader xls)
	{
		String sheet="TestCases";
		int rows = xls.getRowCount(sheet);
		for(int r=2;r<=rows;r++)
		{
			String tName=xls.getCellData(sheet, "TCID", r);
			if(tName.equals(testName))
			{
				String runmode=xls.getCellData(sheet, "Runmode", r);
				if(runmode.equals("Y"))
					return true;
				else
					return false;
			}
		}
		return false;
	}

}