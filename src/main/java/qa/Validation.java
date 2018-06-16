package LTE.qa;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;



import com.rhi.utilities.DateTime;
import com.rhi.utilities.Excel;
import com.rhi.utilities.Utility;
import com.rhi.utilities.WebDrivers;

//ver 2.5 -1/1/2014

public class Validation {
	
//		public static String JSATDir = "c:\\JSAT 2.5\\";
	//public static String JSATDir = "\\\\na\\corpshared\\apps\\quality assurance\\selenium\\automation\\myGalaxy\\Users\\" + myNetworkID + "\\";
		
	public static String JSATDir = "\\\\na\\corpshared\\apps\\quality assurance\\selenium\\automation\\";
		
		//System Variable'
	public static int snapShotMode;
		public static boolean trace;
		public static String myConfigPath;
		public static String myGalaxyDir,	myTestCaseDir, myProject, myApp;
		public static boolean myTrace;
		public static String myScreenShot;
		public static String refPath;		
		public static String[] myJSATVar;		
		
		//Configuration
		public static String myConfigFile = "configuration.xlsm";
		public static String myTestset = "MyTestSet.xlsm";
		
		public static String myConfigSheet, mySystemSheet; // = myProject;
		
		public static String myGalaxyServer, myWECDir, myTestSetDir, myCSDir, myGalaxyLogDir, myBrowserDriverDir;
		public static List myTestSet= new ArrayList();
		public static List myWEC = new ArrayList();
		public static List myConfig, mySystem;
		public static String myNetworkID = System.getProperty("user.name");

		public static int $CaseNum;
		public static int $testSetNbr = 0;

		public static String $testcase;
		public static String $timeStamp;
		public static String $snapShotFolder;

		public static String traceMessage;
		
		public static Date myTS_StartTime, myTS_EndTime;
		

		@Before
		public void setUp() throws Exception {
			
				myTS_StartTime = new Date();
				System.out.println("Test Set Execution Start=:" + DateTime.getDateTimeFormat(myTS_StartTime, "hh:mm:ss SSS"));
	
				//myGalaxyDir = "\\\\na\\corpshared\\apps\\quality assurance\\selenium\\automation\\myGalaxy\\Users\\" + myNetworkID + "\\";
				myGalaxyDir = JSATDir + "myGalaxy\\Users\\" + myNetworkID + "\\";
				myConfigPath = myGalaxyDir+"\\config\\";
				
				
				//if (myProject.equalsIgnoreCase("ALL")) {
				//'					System.out.println("Execution aborted, Project not selected");
				//					System.exit(0);
				//				}
				
				traceMessage = "=================================================================================";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage = "***** Process  #1 - TestSet Setup";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="[Project]=:" + myProject;
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="=================================================================================";
				Utility.traceLog(trace, true, traceMessage);
		
				System.out.print("...... read Config");
				readConfig();
				System.out.println(" ...... Done");
				System.out.print("...... read WebElement Catlog");
				readWebElementCatalog();
				System.out.println(" ...... Done");
				System.out.print("...... read MyGalaxy");
				readMyGalaxy();  //TestSet
				System.out.println(" ...... Done");
				
		}					
		@Test
		public  void runTestSet() throws Exception {
				
				traceMessage="";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="=================================================================================";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="***** Process #2 - Execute Test cases - Total Test Cases = " + (myTestSet.size() -1)  ;
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="=================================================================================";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="";
				Utility.traceLog(trace, true, traceMessage);
				
				for (int i = 1; i < myTestSet.size(); i ++) {
					TestResultLog.myTestResultLog.clear();
					List $myTS_TestCase = (List) myTestSet.get(i);
					TestExecution.runTestCase($myTS_TestCase);
					traceMessage="";
					Utility.traceLog(trace, true, traceMessage);
					$myTS_TestCase.set(3, TestExecution.myTestCase_Status +";" +  TestExecution.myTestResultDir + TestResultLog.myTestResultLogFile);
				}
				myTS_EndTime = new Date();
				System.out.println("Test Set Execution Ended=:" + DateTime.getDateTimeFormat(myTS_EndTime, "hh:mm:ss SSS"));
				System.out.println(DateTime.getDateTimeDiff(myTS_StartTime, myTS_EndTime));
				TestResultLog.SetMyTestSetStatus(myTestSet);
		
				traceMessage="";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="*****  End Of All Test Cases Execution  *****";
				Utility.traceLog(trace, true, traceMessage);
				
				//TestExecution.myDriver.close();
				open_myTestSet();
		}

	
		
		
		
	
		/*=========================================================================================
		 * method
		 *=========================================================================================*/
		public static void readConfig() throws Exception {
			
				//System Variables
				mySystemSheet = "myConfig";
				Excel.openWorkbook(myConfigFile, myConfigPath);
				Excel.readToMyDataSet(Excel.myWorkbook, mySystemSheet, true ,true);
				mySystem =  new ArrayList(Excel.Excel_DataSet);
			
				//myConfigSheet = myProject;
				Excel.Excel_DataSet.clear();;
				for (int i  = 0 ; i < mySystem.size() ; i++) {
					List $SystemVar = (List) mySystem.get(i);
					String $Variable = $SystemVar.get(1).toString();
					switch( $Variable.toUpperCase()) {
						case "MYGALAXY" : {
							myGalaxyDir = $SystemVar.get(2).toString();
							break;
						}
						case "PROJECT" : {
							myProject = $SystemVar.get(2).toString();
							break;
						}
						case "APPLICATION" : {
							myApp = $SystemVar.get(2).toString();
							break;
						}
						case "TRACE" : {
							myTrace =  (boolean) $SystemVar.get(2);
							break;
						}
						case "SCREENSHOT" : {
							myScreenShot =  $SystemVar.get(2).toString();
							break;
						}
					}
				}
			
				//Project Variable
				myConfigSheet = myProject;
				Excel.openWorkbook(myConfigFile, myConfigPath);
				Excel.readToMyDataSet(Excel.myWorkbook, myConfigSheet, true ,true);
				myConfig =  new ArrayList(Excel.Excel_DataSet);
						
				Excel.Excel_DataSet.clear();;
				for (int i  = 1 ; i < myConfig.size() ; i++) {
				List $Type = (List) myConfig.get(i);
				String $Server = $Type.get(3).toString();
				switch( $Server.toUpperCase()) {
					case "GALAXYSERVER" : {
						myGalaxyServer = $Type.get(4).toString();
						break;
					}
					case "WECDIR" : {
						myWECDir = $Type.get(4).toString();
						break;
					}
					case "MYTESTSETDIR" : {
						myTestSetDir =  myGalaxyDir+  myNetworkID +  $Type.get(4).toString();
						myTestCaseDir = myTestSetDir + "Test Cases\\";
						break;
					}
					case "CSDIR" : {
						myCSDir =  $Type.get(4).toString();
						break;
					}
					case "GALAXYLOGDIR": {
						myGalaxyLogDir = $Type.get(4).toString();
						break;
					}
					case "BROWSERDRIVERDIR": {
						myBrowserDriverDir = $Type.get(4).toString();
						break;
					}
				}
			}
		}
		
		
		public static void readMyGalaxy() throws Exception {
		   
				//myTestSet
				String $TestSet = myTestset;
				String $Sheet = "Main";
				
				Excel.openWorkbook($TestSet, myTestSetDir);
				Excel.readToMyDataSet(Excel.myWorkbook, $Sheet, true, true);
				List $myTestSet =  new ArrayList(Excel.Excel_DataSet);
				Excel.Excel_DataSet.clear();
				
				List $header = (List) $myTestSet.get(7);
				myTestSet .add($header);
				for (int i  = 1 ; i < $myTestSet.size() ; i++) {
					List $myTestCase = (List) $myTestSet.get(i);
					String $TS_Run = $myTestCase.get(2).toString();
					if ($TS_Run.equalsIgnoreCase("####")) {
						i = $myTestSet.size();
					}
					else if ($TS_Run.equalsIgnoreCase("R") || $TS_Run.equalsIgnoreCase("C")) { 
						//traceMessage= $myTestCase;
						myTestSet.add($myTestCase);
					}
				}
				
				traceMessage="-----------------------------------------------------------------------------------------------------------------";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="[Total Test Case(s)]=:" + (myTestSet.size()-1) ;
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="-----------------------------------------------------------------------------------------------------------------";
				Utility.traceLog(trace, true, traceMessage);
				for (int i  = 1 ; i < myTestSet.size() ; i++) {
					traceMessage="#" + i + " " +myTestSet.get(i);
					Utility.traceLog(trace, true, traceMessage);
				}
		}
		
		
		public static void readWebElementCatalog() throws Exception {
			   
				String $WEC = "WEC_" + myApp + ".xlsm";
				String $mySheet = myApp;

				Excel.openWorkbook($WEC, myWECDir);
				Excel.readToMyDataSet(Excel.myWorkbook, $mySheet, true,true);
				myWEC = new ArrayList(Excel.Excel_DataSet);
				Excel.Excel_DataSet.clear();
		}
		
		
		public static void open_myTestSet() throws IOException {
			
			//'Desktop.getDesktop().open(new File(myGalaxyDir + "\\" + myProject + "\\MyTestSet.xlsm"));
			//Desktop.getDesktop().open(new File(myTestSetDir + "\\MyTestSet.xlsm"));
			System.out.println(myTestSetDir + "\\MyTestSet.xlsm");
			Desktop.getDesktop().open(new File("S:\\myGalaxy\\Users\\JEFLAI01\\PROJECTS\\TCAST\\MyTestSet.xlsm"));
			
		}
}

