package main.java.qa;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.rhi.utilities.DateTime;
import com.rhi.utilities.Excel;
import com.rhi.utilities.WebElements;


public class TestResultLog {
	
		//public static String $TestLogFile;
		public static String myTestResultLogFile;
	
		public static List myTestResultLog = new ArrayList();
		public static List stepLog = new ArrayList();
		
		public static List myTestResultErrLog = new ArrayList();
		public static List stepErrLog = new ArrayList();

		public static int  $LogLine, $LogStep;;
		
		public static String traceMessage;
		public static Boolean trace =false;
		public static String myTC_Status = "Passed";
		
		@Test
		public static void writeTestLog( ) throws Exception {
			
			 	/*=================================================================================
				** Write all step log to test result file
			 	**=================================================================================*/
				TestExecution.myTCRunTimeEnd = new Date();
				String $path = Validation.myGalaxyServer + "Resources\\";
				String $StdResultLog = "MyResultLog.xlsm";
				String $Sheet = "Log";
				
				myTestResultLogFile =   "Result Log "+TestExecution.myTScaseID + " "+ DateTime.getDateTimeFormat(TestExecution.myTCRunTimeStart, "yyyy_MM_dd_HH_mm_ss")  + ".xlsm";
		
				traceMessage="";
    			traceMessage="========================================================================================";
    			traceMessage="***** [Process # 4] -   TestResultLog" ; 
    		 	//traceMessage="***** [Result Log1]=:" +  $TestResultLog1 + "  *****";
    		 	traceMessage="***** [Result Log]=:" + myTestResultLogFile + "  *****";
    		 	traceMessage="========================================================================================";
    			Excel.openWorkbook($StdResultLog, $path) ;
				
    			/*------------------------------------------------------------------------------------------------------	
				*Update Result Log header
				*------------------------------------------------------------------------------------------------------*/
				traceMessage="*****  Update Result Log header  *****";
				List myResultLogHeader =  (List) myTestResultLog.get(0);
				System.out.println(myResultLogHeader);

				List myTestLogHeader =  (List) myTestResultLog.get(0);
				String $timestampformat =  "yyyy_MM_dd_HH_mm_ss_SSS";
				TestExecution.myTCRunTimeEnd = new Date();
				myTestLogHeader.set(11, DateTime.getDateTimeDiff(TestExecution.myTCRunTimeStart, TestExecution.myTCRunTimeEnd));
				myTestLogHeader.set(12, DateTime.getDateTimeFormat(TestExecution.myTCRunTimeStart, $timestampformat) + '\n'  + 	DateTime.getDateTimeFormat(TestExecution.myTCRunTimeEnd, $timestampformat));
			
				//myTestLogHeader.set(12,TestExecution.myTCrunTimeStart +" - " + 	TestExecution.myTCrunTimeEnd);
				for (int r = 2; r <= 7 ; r++) {
					System.out.println(myTestLogHeader.get(r-1).toString().length());
					if (myTestLogHeader.get(r-1).toString().length() >= 4 && myTestLogHeader.get(r-1).toString().substring(0,4).equalsIgnoreCase("href")) {
						System.out.println(myTestLogHeader.get(r-1).toString().substring(0,4));
						Excel.setCellHyperlink($Sheet,r, 4,  myTestLogHeader.get(r-1).toString());
					}
					else {
						Excel.setCellValue($Sheet,r, 4,  myTestLogHeader.get(r-1).toString());
					}
				}
				
				for (int r = 2; r <= 7 ; r++) {
					if (myTestLogHeader.get(r+5).toString().contentEquals("href")) {
						Excel.setCellHyperlink($Sheet,r, 8,  myTestLogHeader.get(r + 5).toString());
					}
					else {
						Excel.setCellValue($Sheet,r, 8,  myTestLogHeader.get(r + 5).toString());
				
					}		
				}
				
				/*------------------------------------------------------------------------------------------------------	
				*Update all Steps log'
				*myTestResultLog
				*------------------------------------------------------------------------------------------------------*/
				Excel.setRangeValue($Sheet,10, 2,  myTestResultLog);
				Excel.saveWorkbook(myTestResultLogFile, TestExecution.myTestResultDir);
				myTestResultLog.clear();
    			File srcFlle = FileUtils.getFile(TestExecution.myTestResultDir+myTestResultLogFile);
				FileUtils.copyFile(srcFlle,new File(TestExecution.myTestCaseDir+ myTestResultLogFile ));
		}		
		
				
		public static void writeStepLog(List testStepLog ) throws IOException {
		
				List myStepLog  = new ArrayList(testStepLog);
				if (myTestResultLog.size() == 1 ) {
					$LogLine = 1;
					$LogStep = 1;
				}
				else {
					$LogLine ++;
					$LogStep++;
				}
				myStepLog.add(1,Integer.toString($LogLine));					//Line#
				myStepLog.add(2,Integer.toString($LogStep));					//Step#
				
				traceMessage="[Log*]=: " + myStepLog;
				myTestResultLog.add(myStepLog);
		}
		

		public static void writeStepLog(List testStepLog, List testStepErrorLog ) throws IOException {
			
				List myStepLog  = new ArrayList(testStepLog);
				List myStepErrLog  = new ArrayList(testStepErrorLog);
				
				if (myTestResultLog.size() == 1 ) {
					$LogLine = 1;
					$LogStep = 1;
				}
				else {
					$LogLine ++;
					$LogStep++;
				}
				myStepLog.add(1,Integer.toString($LogLine));					//Line#
				myStepLog.add(2,Integer.toString($LogStep));					//Step#
				myTestResultLog.add(myStepLog);
				
				if (myStepErrLog.size() > 0) {
					myStepErrLog.add(1,Integer.toString($LogLine));					//Line#
					myStepErrLog.add(2,Integer.toString($LogStep));					//Step#
					myTestResultErrLog.add(myStepErrLog);
				}
					
			
		}

		
		public static void writeStepLog(int  LogLine, int LogStep, String Desc, String Status, String SnapShot, String TimeStamp  ) throws IOException {
			
				if (myTestResultLog.size() == 1 ) {
					$LogLine = 1;
					$LogStep = 1;
				}
				else {
					$LogLine ++;
					$LogStep++;
				}
				myTestResultLog.add(LogLine);
				myTestResultLog.add(LogStep);
				myTestResultLog.add(Desc);
				myTestResultLog.add(Status);
				myTestResultLog.add(SnapShot);
				myTestResultLog.add(TimeStamp);
			}
	
		
		public static void writeStepLog(String description ) throws IOException {
			
				if (myTestResultLog.size() == 0 ) {
					$LogLine = 0;
				}
				else {
					$LogLine ++;
				}
				List $myStepLog = new ArrayList();
				$myStepLog.add("");
				$myStepLog.add($LogLine);			//Line#
				$myStepLog.add("");
				$myStepLog.add("******** " + description + "********");						//Description
				$myStepLog.add("");						//Description
				$myStepLog.add("");						//Description
						
				myTestResultLog.add($myStepLog);
		}
		
		
			public static void writeMyTestSet( ) throws Exception {
			
		 	
				String $path = "c:\\myGalaxy\\Projects\\" + Validation.myProject + "\\";
					//String $path = "c:\\myGalaxy\\PeopleSoft\\";
			
					String $StdResultLog = "MyTestSet.xlsm";
					String $Sheet = "Main";
			
					traceMessage="";
					traceMessage="========================================================================================";
			traceMessage="***** [MyTestset"; 
		 	traceMessage="========================================================================================";
			Excel.openWorkbook($StdResultLog, $path) ;
			/*------------------------------------------------------------------------------------------------------	
			*Update Result Log header
			*------------------------------------------------------------------------------------------------------*/
			
				//String $address2 = "\\\\na\\corpshared\\apps\\quality assurance\\selenium\\automation\\Galaxy\\configuration\\WEC_PeopleSoft.xlsm";
				String $address2 = TestExecution.myTestResultDir + myTestResultLogFile;
			System.out.println($address2);
			$address2 = $address2.replace("\\\\", "\\"); 
			//System.out.println($address2);
			//$address2 = "\\\\na\\corpshared\\apps\\quality assurance\\selenium\\automation\\Galaxy\\GalaxyLog\\PeopleSoft\\WG-CA-Fri-06\\JEFLAI01-2013_10_16_08_12_12\\ReporLog 2013_10_16_08_12_12.xlsm";
			//System.out.println($address2);
			String myHyperlink = "href;file;Failed;" + $address2;
			Excel.setCellHyperlink($Sheet, 8, 3, myHyperlink);
			Excel.saveWorkbook();
			
			
		}		
		
		public static void SetMyTestSetStatus(List TestSet ) throws Exception {
			
				String myGalaxyDir = new  String (Validation.myGalaxyDir);
				String myProject = new  String (Validation.myProject);
				
				String $Path =  myGalaxyDir  + "\\" + Validation.myProject + "\\";
			
				String $TestSet = "MyTestSet.xlsm";
				String $Sheet = "Main";
		
//				Excel.openWorkbook($TestSet, $Path) ;
				Excel.openWorkbook("MyTestSet.xlsm", Validation.myTestSetDir) ;
		
				/*------------------------------------------------------------------------------------------------------	
				 *Update Result Status - Status[0] = Status , Status[1]=Result Log Address
				 *------------------------------------------------------------------------------------------------------*/
				for (int i = 1; i < TestSet.size(); i ++) {
					List $TS_TestCase = (List) TestSet.get(i);
					int $Row = (int) $TS_TestCase.get(0);
					String $Status[] = $TS_TestCase.get(3).toString().split(";");
					//String $address2 = TestExecution.myTestResultDir + myTestResultLogFile;
					//System.out.println($address2);
					$Status[1] = $Status[1].replace("\\\\", "\\"); 
					String myHyperlink = "href;file;" + $Status[0]+ ";" + $Status[1];
					System.out.println(myHyperlink);
					Excel.setCellHyperlink($Sheet, $Row, 3, myHyperlink);
				}
				Excel.saveWorkbook();
		}		
	
		//public static void updateStepLog(WebDriver driver,  String testData, String stepStatus) throws InterruptedException, IOException {
		public static void updateStepLog(String testDesc, String testData, String stepStatus, Date TimeStart, Date TimeEnd, String screenShotFile, String errorMsg) throws InterruptedException, IOException {

				stepErrLog.clear();
				stepLog.clear();
				
			//'Update '
				String duration = DateTime.getDateTimeDiff(TimeStart, TimeEnd);
				stepLog.add("");
				stepLog.add(testDesc);
				stepLog.add(testData);
				stepLog.add(stepStatus);
				stepLog.add(screenShotFile);
				stepLog.add(duration);
				System.out.println("***** Step Log ***** " + stepLog);
		
				if (stepStatus.equalsIgnoreCase("Failed")) {
					stepErrLog.add("");
					stepErrLog.add(testDesc);
					stepErrLog.add(testData);
					stepErrLog.add(stepStatus);
					stepErrLog.add(screenShotFile);
					stepErrLog.add(errorMsg);
					stepErrLog.add(duration);
					System.out.println("***** Step Err Log ***** " + stepErrLog);
				}	
				writeStepLog(stepLog, stepErrLog);
		}
		
	}		
		
		