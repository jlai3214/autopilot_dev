package LTE.qa;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;












import com.rhi.utilities.Checkpoint;
import com.rhi.utilities.Database;
import com.rhi.utilities.DateTime;
import com.rhi.utilities.Excel;
import com.rhi.utilities.FileHandling;
import com.rhi.utilities.Snapshot;
import com.rhi.utilities.Utility;
import com.rhi.utilities.WebDrivers;
import com.rhi.utilities.WebElements;

public class TestExecution {

		public Alert alert;
    	public static WebDriver myDriver;
    	public static List JSAT = new  ArrayList();
		
    	public static String myTSRun, myTSrunStatus, myTSupLoadStatus,   myTScaseID,  myTSfile,  myTSfilePath, myTSsheet, myTSbrowser, myTSurl;
	   	//public static String myTCRrunTimeStart, myTCrunTimeEnd;
       	public static Date myTCRunTimeStart, myTCRunTimeEnd;
       	
    	public static String myTestCaseDir, myTestResultDir , mySnapShotDir ;

	   	public static List myTC_Steps =  new  ArrayList();
		public static List myTC_Step =  new  ArrayList();
		public static List  myTC_CSScript =  new  ArrayList();
		public static List  myTC_CSData =  new  ArrayList();
	
		public static List CSScipt, CSDataSet,  myCSTestData, myCSTestScript;
    	
		public static List myTestCase, myCSSteps;
		public static List myTestSteps, myStep;
    	
	  	public static String[] myRunTestID =  new String [2];
	   	public static String[] myTC_ID =  new String [2];
		public static List myCSRunSteps = new ArrayList();
		public static List myTestSteps_Err = new ArrayList();
	  	public static List myCSRunSteps_Err = new ArrayList();
	  	
		public static String traceMessage;
		public static Boolean trace;
    	public static String myTestCase_Status;
	  	
		public static  void runTestCase(List TestCase) throws Exception  {
			
				trace = Validation.myTrace;
    		
    			myTestCase = new ArrayList(TestCase);
    			traceMessage="-----------------------------------------------------------------------------------------------------------------";
    			Utility.traceLog(trace, true, traceMessage);
				traceMessage="*** [Process #2(Step-1)] - Test Case Execution -Setup";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage=Utility.getListToString(TestCase);
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="-----------------------------------------------------------------------------------------------------------------";
				Utility.traceLog(trace, true, traceMessage);
				
	
				myTestCase_Status = "Passed";
				myTCRunTimeStart = new Date();
    			traceMessage="[Execution RunTimeStart]=:" + DateTime.getDateTimeFormat(myTCRunTimeStart, "hh:mm:ss SSS");
				Utility.traceLog(trace, true, traceMessage);
	
				myTSrunStatus = myTestCase.get(2).toString();
				myTScaseID = myTestCase.get(5).toString();
				
				myTSfilePath = myTestCase.get(6).toString();
				if (myTSfilePath.equalsIgnoreCase("Default")) {
					myTSfilePath = Validation.myTestCaseDir;
				}
				myTSfile = myTestCase.get(7).toString();
				myTSsheet = myTestCase.get(8).toString();
				myTSurl = myTestCase.get(12).toString();
				myTSbrowser = myTestCase.get(13).toString();
				
    			/* ---------------------------------------------------------------------------------------------------------
				*Process #2-1a - Create Result Log Folder";
    			* ----------------------------------------------------------------------------------------------------------*/	
    			createResultFolder(myTestCase);

    			/* ---------------------------------------------------------------------------------------------------------
				*Process #2-1b - Update result";
    			* ----------------------------------------------------------------------------------------------------------*/	
    			setResultLogHeader();
    					
    			/* ---------------------------------------------------------------------------------------------------------
				*Process #2-1c - copy test case file to log folder";
    			* ----------------------------------------------------------------------------------------------------------*/	
    			File srcFlle = FileUtils.getFile(myTSfilePath + myTSfile);
				FileUtils.copyFile(srcFlle,new File(myTestResultDir + myTSfile));
	
				/*===============================================================================================
    			*Process #2-1d - 				//Open Test case data file
    			*===============================================================================================*/
				traceMessage="";
				Utility.traceLog(trace, true, traceMessage);
    			traceMessage="-----------------------------------------------------------------------------------------------------------------";
    			Utility.traceLog(trace, true, traceMessage);
    			traceMessage="*** [Process #2(Step-3)] - Load Test case file";
    			Utility.traceLog(trace, true, traceMessage);
    			traceMessage="[Test Script]=:"+ myTSfilePath  + myTSfile;
    			Utility.traceLog(trace, true, traceMessage);
    			traceMessage="-----------------------------------------------------------------------------------------------------------------";
    			Utility.traceLog(trace, true, traceMessage);
				Excel.openWorkbook(myTSfile, myTSfilePath);
				Excel.readToMyDataSet(Excel.myWorkbook, "Script", true, true);
				myTC_Steps = new ArrayList(Excel.Excel_DataSet);
				Excel.Excel_DataSet.clear();
		
				/*===============================================================================================
    			*Process #2-2a - * Open Browser and URL
				*===============================================================================================*/
				traceMessage="";
				Utility.traceLog(trace, true, traceMessage);
    			traceMessage="-----------------------------------------------------------------------------------------------------------------";
    			Utility.traceLog(trace, true, traceMessage);
				traceMessage="*** [Process #2(Step-4) - Open Browser & URL";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="-----------------------------------------------------------------------------------------------------------------";
				Utility.traceLog(trace, true, traceMessage);
				if (myTSrunStatus .equalsIgnoreCase("C")) {
					traceMessage="Continue";
					Utility.traceLog(trace, true, traceMessage);
						}
				else {
					traceMessage="URL=:" + myTSurl;
					Utility.traceLog(trace, true, traceMessage);
						if ( WebDrivers.driver != null) {
							WebDrivers.driver.close();
							openURL(myTSbrowser, myTSurl);
					}
					else {
						openURL(myTSbrowser, myTSurl);
					}			
				}
		
				/*============================================================================================
				*Process #2-2b - 	 * 'Execute Steps'				
				 *===========================================================================================*/
				for (int  i = 0 ; i < myTC_Steps.size() ; i++) {
					myTC_Step = (List) myTC_Steps.get(i);
					String $myTC_Run = myTC_Step.get(1).toString();
					if ($myTC_Run.equalsIgnoreCase("R")) {
						String $stepType = "STEP";
						String $myTC_CaseID =  myTC_Step.get(2).toString();
						traceMessage="";
						Utility.traceLog(trace, true, traceMessage);
		    			traceMessage="-----------------------------------------------------------------------------------------------------------------";
		    			Utility.traceLog(trace, true, traceMessage);
						traceMessage="*** [Process #2(Step-5) - Runtime test case Id";
						Utility.traceLog(trace, true, traceMessage);
						traceMessage="-----------------------------------------------------------------------------------------------------------------";
						Utility.traceLog(trace, true, traceMessage);
						
						/*============================================================================================
    					// TestCase ID
						 *===========================================================================================*/
						setTC_ID($myTC_CaseID);    //return '
						
						if (myTScaseID.equalsIgnoreCase(myTC_ID[0]) ) {
							Utility.traceLog(trace, true, traceMessage);
							traceMessage="[TestSetID]=:" + myTScaseID.toString();
							Utility.traceLog(trace, true, traceMessage);
							traceMessage="[Primary case ID(TestSet]=:" + myTC_ID[0].toString();
							Utility.traceLog(trace, true, traceMessage);
    						traceMessage="[Alternate case ID(CS/Data]" +myTC_ID[1].toString();
    						Utility.traceLog(trace, true, traceMessage);
    						traceMessage="";
    						Utility.traceLog(trace, true, traceMessage);
    						traceMessage="-----------------------------------------------------------------------------------------------------------------";
    						Utility.traceLog(trace, true, traceMessage);
    									
    						/*==================================================================
    						// 1. Step dependency
    						 *==================================================================*/
    						// To be developed
    				
    						
    						/*==================================================================
    						*2. Select Step Type
    						 *==================================================================*/
    						if (myTC_Step.get(8).toString().substring(0,3).equalsIgnoreCase("#CS") ) {
    							$stepType = "COMMONSCRIPT";
    							traceMessage="";
    							Utility.traceLog(trace, true, traceMessage);
        		    			traceMessage="-----------------------------------------------------------------------------------------------------------------";
        		    			Utility.traceLog(trace, true, traceMessage);
        						traceMessage="*** [Process #2(Step-6) - Commn Script & Test data";
        						Utility.traceLog(trace, true, traceMessage);
        						traceMessage="-----------------------------------------------------------------------------------------------------------------";
        						Utility.traceLog(trace, true, traceMessage);
        						traceMessage="Execution Step/Common Scipt ]=: "+ myTC_Step.get(6).toString();
        						Utility.traceLog(trace, true, traceMessage);
        						traceMessage="Execution Step Type]=: "+  myTC_Step;
        						Utility.traceLog(trace, true, traceMessage);
        						traceMessage="-----------------------------------------------------------------------------------------------------------------";
        						Utility.traceLog(trace, true, traceMessage);
        					}
    						
    						else if (myTC_Step.get(6).toString().equalsIgnoreCase("'CHECKPOINT")) {
    							$stepType = "CHECKPOINT";
    						}			
    							
    						switch($stepType.toUpperCase()) {
    							/*------------------------------------------------------------------------------------------------------------
	    						/* 1) Call single Step
    							 * ----------------------------------------------------------------------------------------------------------*/
    							case "STEP": {    
    								traceMessage = myTC_Step.toString();
    								Utility.traceLog(trace, true, traceMessage);
    	    						if (myTC_Step.get(7).toString().equalsIgnoreCase("#blank#") )   {
    				 					myStep.set(7, "");
    				 				}
    								List $myCSRunStep = new ArrayList();;
    				 				$myCSRunStep.add("$Data_Step");
    	          					$myCSRunStep.add("TestID");
    	          					$myCSRunStep.add(myStep);
    	          					$myCSRunStep.add("");
    	          					$myCSRunStep.add("WEC");
    	          					myCSRunSteps.add($myCSRunStep);
    	          					findWebElement(myCSRunSteps);
				 		    		if (!myStep.get(7).toString().equalsIgnoreCase("")) {
				 		    			WebElements.runStep(WebDrivers.driver, myCSRunSteps);
				 		    			TestResultLog.writeStepLog(WebElements.stepLog);
				 		    			break;
				 		    		}
    	    					}
    	    					/*------------------------------------------------------------------------------------------------------------
        						* 2) Call Common Script
        						* ----------------------------------------------------------------------------------------------------------*/
    							case "COMMONSCRIPT": {
    								runCommonScript(myTC_Step);
        							break;
    				 			}
    	   
    							/*------------------------------------------------------------------------------------------------------------
        						* 3) CheckPoint
    				 			* ----------------------------------------------------------------------------------------------------------
    	    					case "CHECKPOINT": {
    				 				//'TBD';
    	    						traceMessage="To be develoepd";
    	    						Utility.traceLog(trace, true, traceMessage);
    	    						//TestCheckpoint.runCheckpoint($checkPoint);
    				 				//traceMessage="[TestType]=: + $testType";
    				 				break;
    				 			} 
    				 			*/
    							
    	    					/*------------------------------------------------------------------------------------------------------------
        						*4) Call database
            					* ----------------------------------------------------------------------------------------------------------*/
    	    					case "DATABASE": {
    	    						traceMessage="To be develoepd";
    	    						Utility.traceLog(trace, true, traceMessage);
    	    						//'TBD'''
    	    						//traceMessage="[TestType]=: + $testType";
    				 				//traceMessage="Run Query";
    				 				//Database.main();
    				 				break;
    				 			}
    						}
    					}
					 }
    				/*-------------------------------------------------------------------------------------------------------
    				/* ### - breakpoint , temporary break
    				 * ------------------------------------------------------------------------------------------------------*/
    				 if  ($myTC_Run.equalsIgnoreCase("'###")) {
   				 	 		traceMessage = "";
   				 	 		Utility.traceLog(trace, true, traceMessage);
       						traceMessage ="";
       						Utility.traceLog(trace, true, traceMessage);
    						traceMessage="**********************Terminated***************************";
    						Utility.traceLog(trace, true, traceMessage);
    						TestResultLog.writeStepLog("  ***** terminated(###) *****");
    						Utility.traceLog(trace, true, traceMessage);
    				 		break;
    				 }
    				 
    				 /*-------------------------------------------------------------------------------------------------------
    				* #### - End of TestSet Execution
    				 * ------------------------------------------------------------------------------------------------------*/
    				if  ($myTC_Run.equalsIgnoreCase("'####")) {
    	   				 	traceMessage="**********************Test Set  execution completed***************************";
    	   					Utility.traceLog(trace, true, traceMessage);
           	    			TestResultLog.writeStepLog("Execution completed");
           	   	 		break;
    				 }
				}
				
				/*=======================================================================================
    	       *Write all step log to Result files
    	       *======================================================================++++==============*/
				TestResultLog.writeTestLog();
		}
						
		
    	/*==============================================================================================
    	 * methods				
    	*===============================================================================================*/
      	public  static void runCommonScript(List TC_Step) throws Exception {
      		
      			myCSRunSteps.clear();
      			myCSRunSteps_Err.clear();
      			JSAT.clear();
      			
      			String $myCSDir = Validation.myCSDir;
      			String $myTC_CS = TC_Step.get(8).toString();		//Common Script Name
      			String $myTC_CSData = TC_Step.get(9).toString();		//Common Script Name
      			String $myCSSheet = "Script";
	 			
      			$myTC_CS = $myTC_CS.replace("#","") + ".xlsm";
      		
      			File srcFlle = FileUtils.getFile($myCSDir + $myTC_CS);
				FileUtils.copyFile(srcFlle,new File(myTestResultDir + $myTC_CS));
  				String myResultDesc = "[Test Case ID]=:" + myTC_ID[0] +"\\" + myTC_ID[1] + "\n" + "[Common Script]=" + $myTC_CS + "\n" +"[Data]=:" + $myTC_CSData;
				TestResultLog.writeStepLog(myResultDesc);
			
      			if(!(myTSrunStatus.equalsIgnoreCase("C")) || !($myTC_CS.equalsIgnoreCase("CS_Login.xlsm"))) {
      				/*--------------------------------------------------------------------------------------------------------------------------------
      				*Process#2-3b - Step 1 - Load Common Script  - CSScript
      	      		*--------------------------------------------------------------------------------------------------------------------------------*/
      				
      				traceMessage="[Common Script]=:"+ $myCSDir + $myTC_CS;
      				Utility.traceLog(trace, true, traceMessage);
      				Excel.openWorkbook($myTC_CS, $myCSDir);
      				Excel.readToMyDataSet(Excel.myWorkbook, $myCSSheet, true, true);
      				myTC_CSScript =  new ArrayList(Excel.Excel_DataSet);
      				Excel.Excel_DataSet.clear();
      			
      				/*--------------------------------------------------------------------------------------------------------------------------------
      				*Step 2 - Load DataSheet - CSDataSet 
      	      		*--------------------------------------------------------------------------------------------------------------------------------*/
      				String $myCSD_Dir, $myCSD_DataFile, $myCSD_Sheet;
      				if ($myTC_CSData.equalsIgnoreCase("#data#") || $myTC_CSData.equalsIgnoreCase("") ) {
      					$myCSD_Dir = $myCSDir;
      					$myCSD_DataFile = $myTC_CS;      //Default datafile
      					$myCSD_Sheet = "data";
      				}
      				else {
      					//To be completed
      					String $myDataFile []  = $myTC_CSData.split("#");
      					int j  = $myDataFile[0].lastIndexOf("\\"); 
      					j++;
      					$myCSD_Dir = $myDataFile[0].substring(0,j);
      					$myCSD_DataFile = $myDataFile[0].replace($myCSD_Dir, "")+".xlsm";
      					$myCSD_Sheet = $myDataFile[1];
      				}
				
      				//Load DataSheet
      				Excel.openWorkbook($myCSD_DataFile, $myCSD_Dir);
      				Excel.readToMyDataSet(Excel.myWorkbook, $myCSD_Sheet, true, true);
      				myTC_CSData =  new ArrayList(Excel.Excel_DataSet);
      				Excel.Excel_DataSet.clear();
      				traceMessage="";
      				Utility.traceLog(trace, true, traceMessage);
      				traceMessage="[DataSheet]=:"+  $myCSDir + $myTC_CSData;
      				Utility.traceLog(trace, true, traceMessage);
      			
      				/*--------------------------------------------------------------------------------------------------------------------------------
      				*  Step 7 - 'Write test data with value to myCSTestData
      				*--------------------------------------------------------------------------------------------------------------------------------*/
      				traceMessage="";
      				Utility.traceLog(trace, true, traceMessage);
    				traceMessage="-----------------------------------------------------------------------------------------------------------------";
    				Utility.traceLog(trace, true, traceMessage);
    			    traceMessage="*** [Process #2(Step-7)- Write test data with value to [myCSTestData]";
    				Utility.traceLog(trace, true, traceMessage);
    			    traceMessage="[Common Script]=:"+ $myCSDir + $myTC_CS;
    				Utility.traceLog(trace, true, traceMessage);
      				traceMessage="-----------------------------------------------------------------------------------------------------------------";
      				Utility.traceLog(trace, true, traceMessage);
      				int $CSTestRow = 0;
      				int $myRunStepNbr = 0;
      				
      				myCSTestData = new ArrayList();
	 				myCSTestData.add("myCSTestData");
      				
	 				List $Frame = (List) myTC_CSData.get(1);
	 				List $WEI = (List) myTC_CSData.get(2);
          			List $WEIType = (List) myTC_CSData.get(3);
	 				List $Label = (List) myTC_CSData.get(7);
	 				
      				//List $Label = (List) myTC_CSData.get(1);
          			//List $WEI = (List) myTC_CSData.get(2);
          			//List $Frame = (List) myTC_CSData.get(0);
          			
          			for (int row = 10; row < myTC_CSData.size(); row++) {
      					List $myCSData = (List) myTC_CSData.get(row);
      					String $myCSData_ID = $myCSData.get(2).toString();
      					String $myCSData_Run = $myCSData.get(1).toString();
      			//		if ($myCSData_Run.equalsIgnoreCase("####")) {
      					if ($myCSData_Run.startsWith("###")) {
      					break;
      					}
      					if ((myTC_ID[1].toString().equalsIgnoreCase($myCSData_ID) || myTC_ID[0].toString().equalsIgnoreCase($myCSData_ID)) &&  !$myCSData_Run.equalsIgnoreCase("#"))      				{
      	      				
      						$CSTestRow ++;
      						List $myTestData = new ArrayList();

      						$myTestData.add($CSTestRow);
      						$myTestData.add(myTC_ID[0].toString()+  "/"  +myTC_ID[1].toString());
      						$myTestData.add(row);
      						
							List $myTestSteps = new ArrayList();
							$myTestSteps.add("Step#, Column, Label, WEI, Value; ");
  							for (int col = 6 ; col < $myCSData.size(); col ++) {
  								String $value = $myCSData.get(col).toString();
  								if (!$value.isEmpty() ) {
  									$myRunStepNbr ++;
      			      				List $myTestStep = new ArrayList();
      			      				$myTestStep.add($myRunStepNbr);
      			      				$myTestStep.add(col);
      			      				$myTestStep.add($Label.get(col).toString());
      			      				$myTestStep.add($WEI.get(col).toString());
      			      				$myTestStep.add($myCSData.get(col).toString());
      			      				$myTestStep.add($Frame.get(col).toString());
  								
      			      				$myTestSteps.add($myTestStep);
      							}
							}
							$myTestData.add($myTestSteps);
							myCSTestData.add($myTestData);
						}
          			}

          			/*======================================================================================================
          			*print  myCSTestData
          			*======================================================================================================*/
          			traceMessage="";
          			Utility.traceLog(trace, true, traceMessage);
          			traceMessage="Step-7A ";
          			Utility.traceLog(trace, false, traceMessage);
          			traceMessage="[myCSTestData]";
          			Utility.traceLog(trace, false, traceMessage);
          			traceMessage="; Total dataset=:" + (myCSTestData.size()-1);
          			Utility.traceLog(trace, true, traceMessage);
          			int $cnt = 0;
          			for (int $row = 1; $row < myCSTestData.size(); $row++ ){
          				List $myRow = (List) (myCSTestData.get($row));
          				//traceMessage=$myRow;
          				traceMessage="-----------------------------------------------------------------------------------------------------------------";
          				Utility.traceLog(trace, true, traceMessage);
    					traceMessage="[TestCase ID]=:" + $myRow.get(1) + "; [DataSet]=:" + $row + "/" + (myCSTestData.size()-1) ;
    					Utility.traceLog(trace, true, traceMessage);
    					traceMessage="-----------------------------------------------------------------------------------------------------------------";
    					Utility.traceLog(trace, true, traceMessage);
    					List $mySteps = (List) ($myRow.get(3));
          				for (int $step = 1; $step <  $mySteps.size(); $step++ ){
          					$cnt++;
          					traceMessage="#" + $cnt + " " + $mySteps.get($step);
          					Utility.traceLog(trace, true, traceMessage);
          				}			
          			}
          			
      				/*=================================================================================================
      				*  Step 4 - 'Read Script with status = 'R"
      				*==================================================================================================*/
          			traceMessage="";
          			Utility.traceLog(trace, true, traceMessage);
      				traceMessage="Step-7B* (myCSTestScript) - [Read Scipt/Step with 'R";
      				Utility.traceLog(trace, true, traceMessage);
      				traceMessage="-----------------------------------------------------------------------------------------------------------------";
      				Utility.traceLog(trace, true, traceMessage);
					myCSTestScript = new ArrayList();
          			myCSTestScript.add("myCSTestScript");
          			for (int row = 1; row < myTC_CSScript.size(); row++) {
      					List $myStep = (List) myTC_CSScript.get(row);
      					if ($myStep.get(1).toString().equalsIgnoreCase("R")) {
      						myCSTestScript.add($myStep);
      					}	
          			}
          			//print
          			//traceMessage= myCSTestScript;
          			traceMessage="";
          			Utility.traceLog(trace, true, traceMessage);
          		
          			// Test Data Value - findCS_TestDataValue
              		JSAT.add("JSAT");
      				for (int $row = 1 ; $row < myCSTestData.size(); $row ++) {
          				List $myTestData = (List) myCSTestData.get($row);
          				findCS_TestDataValue($myTestData);			
          			}		
          			
          			// Web ElemTest Data Value - findWebElement
      				traceMessage=""; 
      				Utility.traceLog(trace, true, traceMessage);
          			for (int i = 1 ; i < JSAT.size(); i ++) {
      					List $myJSAT = (List)  JSAT.get(i);
      					findWebElement($myJSAT);
          			}		
      					
          			/*======================================================================================================
      				*  Step 5C -  Print myCSRunSteps
      				*=======================================================================================================*/
          			traceMessage="";
          			Utility.traceLog(trace, true, traceMessage);
      				traceMessage="====================================================================================";
      				Utility.traceLog(trace, true, traceMessage);
      				traceMessage="***** JSAT *****";
      				Utility.traceLog(trace, true, traceMessage);
      				traceMessage="====================================================================================";
      				Utility.traceLog(trace, true, traceMessage);
      				for (int j = 1; j < JSAT.size(); j ++) {
      					traceMessage = j + " ";
      					Utility.traceLog(trace, false, traceMessage);
          				traceMessage=Utility.getListToString((List) JSAT.get(j));
      					Utility.traceLog(trace, true, traceMessage);
      					traceMessage="";
      					Utility.traceLog(trace, true, traceMessage);
      				}

      				traceMessage="";
      				Utility.traceLog(trace, true, traceMessage);
              		traceMessage="***** Web Element Id not found in Web Element Catalog *****";     //error if not found
              		Utility.traceLog(trace, true, traceMessage);
      				traceMessage="Proceed to execute.........";
      				Utility.traceLog(trace, true, traceMessage);
          			traceMessage=Utility.getListToString(myCSRunSteps_Err);
      				Utility.traceLog(trace, true, traceMessage);
          			
      				traceMessage="";
          			Utility.traceLog(trace, true, traceMessage);
          			  			
          			/*===================================================================================================
      				*  Process#3 -  Execution
      				*====================================================================================================*/
          			traceMessage="====================================================================================";
          			Utility.traceLog(trace, true, traceMessage);
      				traceMessage="*** Process #3  - Executing....." + "No of Steps " + (JSAT.size()-1);
      				Utility.traceLog(trace, true, traceMessage);
      				traceMessage="====================================================================================";
      				Utility.traceLog(trace, true, traceMessage);
          				if (myCSRunSteps_Err.size() == 0) {
      					for (int i = 1; i < 	JSAT.size(); i++) {
      						List $myJSAT = (List) JSAT.get(i);
      						traceMessage="";
      						Utility.traceLog(trace, true, traceMessage);
      						traceMessage="-----------------------------------------------------------------------------------------------------------------";
      						Utility.traceLog(trace, true, traceMessage);
      						traceMessage="*** Process #3-1  - Executing....." + "Step= " + i + "/" + (JSAT.size()-1);
      						Utility.traceLog(trace, true, traceMessage);
      						traceMessage=Utility.getListToString($myJSAT);
      						Utility.traceLog(trace, true, traceMessage);
      						traceMessage="-----------------------------------------------------------------------------------------------------------------";
      						Utility.traceLog(trace, true, traceMessage);
      						WebElements.runStep(WebDrivers.driver, $myJSAT);
      					}
      				}
      				else if (myCSRunSteps_Err.size() > 0) {
      					traceMessage="***Execution aborted - Common Script";
      					Utility.traceLog(trace, true, traceMessage);
      	          	}
      			}
      	}

      	
      	public static void findCS_TestDataValue (List myTestData ) throws Exception {
			
      			String $dataValue = "";
      			List $myTestSteps = (List) myTestData.get(3);
      			//List $myTestSteps = myCSTestScript;
      			
      			for (int i = 1; i < myCSTestScript.size(); i++) {
      				List $myCSTestStep = (List) myCSTestScript.get(i);
      				String $WEI_Step = $myCSTestStep.get(14).toString();      //WEI from script/step
      				List $myJSAT = new ArrayList();
          			for (int j = 1; j < $myTestSteps.size(); j++) {
          				List $myTestStep = (List) $myTestSteps.get(j);
          				String $WEI_Data =  $myTestStep.get(3).toString();
          				if ($WEI_Step.equals($WEI_Data)) {
          					traceMessage="";
          					Utility.traceLog(trace, true, traceMessage);
          					traceMessage="[Step-7C] - RunStep# " + j +"/" + $myTestSteps.size() + "  Get DataSheet Value" ;
          					Utility.traceLog(trace, true, traceMessage);
          					traceMessage="--------------------------------------------------------------------------------";
          					Utility.traceLog(trace, true, traceMessage);
          					traceMessage="[Before]=:"   +  $myTestStep; 
          					Utility.traceLog(trace, true, traceMessage);
          					int $Data_Step =  (int) $myTestStep.get(0);
          					$dataValue =  $myTestStep.get(4).toString();
          					if ($dataValue.equalsIgnoreCase("#blank#") )   {
          						$dataValue = "";
          						$myTestStep.set(4,"");
          					}
          					traceMessage="[After]  Value=[" + $dataValue +"]";
          					Utility.traceLog(trace, true, traceMessage);
          					traceMessage="[After]  Step=:[ " + $myTestStep;
          					Utility.traceLog(trace, true, traceMessage);
          					traceMessage="[After]  Data=:[ " + myTestData;
          					Utility.traceLog(trace, true, traceMessage);
          					$myJSAT.add($Data_Step);
          					$myJSAT.add(myTC_ID[0].toString()+  "/"  +myTC_ID[1].toString());
          					$myJSAT.add($myCSTestStep);
          					$myJSAT.add($myTestStep);
          					$myJSAT.add("WEC");
          					JSAT.add($myJSAT);
          					break;
          				}
          			}
          			if ($myJSAT == null) {
						traceMessage="*** Step#5c - [SKIP step(no value)=:[" +  myTScaseID + "];  " + $myCSTestStep;
						Utility.traceLog(trace, true, traceMessage);
          			}
      			}
      	}

      	
      	public static void findWebElement (List myTestStep) throws IOException {
		
      			//traceMessage=(myTestStep);
      			List $myTestStep = (List) myTestStep.get(2);
      			List $myWEC = Validation.myWEC;
      			
      			List $myWebElement = null;
      			
      			String $WEI_myTestStep = $myTestStep.get(14).toString();
      			if ($WEI_myTestStep.contains("$") &&  !$WEI_myTestStep.substring(0,2).equals("PS")) {
      				//$WEI_myTestStep = $WEI_myTestStep.substring(0, $WEI_myTestStep.indexOf("$"));
      			}
    			for (int  i = 1 ; i < $myWEC.size() ; i++) {
      				$myWebElement = (List) $myWEC.get(i);
      				String $WEI_myWEC = $myWebElement.get(3).toString();
        			if ($WEI_myWEC.equals($WEI_myTestStep)  && $myWebElement.get(1).toString().equalsIgnoreCase("A")  ) {
              			myTestStep.set(4,$myWebElement);
      					traceMessage="[Step-7D] - find Web Element#"  ;
      					Utility.traceLog(trace, true, traceMessage);
      					traceMessage="--------------------------------------------------------------------------------";
      					Utility.traceLog(trace, true, traceMessage);
      					traceMessage=( "[After] (WebElement]=: " + $myWebElement + ";" + myTestStep);
      					Utility.traceLog(trace, true, traceMessage);
      					traceMessage="";
      					Utility.traceLog(trace, true, traceMessage);
      	      	      	for (int cnt = 1; cnt < myCSRunSteps.size(); cnt++) {	
      	      	      		traceMessage=Utility.getListToString((List) myCSRunSteps.get(cnt));
      	      	      		Utility.traceLog(trace, true, traceMessage);
      	      	      	}
      	      	      	break;
      				}
      			}
			
      			if (myTestStep.get(4) == "WEC") {
      				myTestSteps_Err.add(myTestStep);
      				traceMessage="***** Web Element Id not found in Web Element Catalog";     //error if not found
      				Utility.traceLog(trace, true, traceMessage);
      			}
      	}


      	public static  void zcreateResultFolder(List TestCase) throws InterruptedException {
		
  				traceMessage ="";
  				Utility.traceLog(trace, true, traceMessage);
  				traceMessage="-----------------------------------------------------------------------------------------------------------------";
  				Utility.traceLog(trace, true, traceMessage);
      			traceMessage="*** [Process #2(Step-2)] - Test Case Execution -Setup - createResultFolder";
      			Utility.traceLog(trace, true, traceMessage);
      			traceMessage="-----------------------------------------------------------------------------------------------------------------";
      			Utility.traceLog(trace, true, traceMessage);
				//Test Case Dir
				myTestCaseDir = Validation.myGalaxyLogDir +  myTScaseID  +"\\\\";
				Utility.traceLog(trace, true, traceMessage);
				FileHandling.createDir(myTestCaseDir);			
		
				myTestResultDir = myTestCaseDir +  Validation.myNetworkID  + "-" + DateTime.getDateTimeFormat(myTCRunTimeStart, "yyyy_MM_dd_HH_mm_ss")  +"\\\\";
				traceMessage= "[Test Result Dir]=:" + myTestResultDir;   
				Utility.traceLog(trace, true, traceMessage);
				FileHandling.createDir(myTestResultDir);			

				//SnapShot
				mySnapShotDir = myTestResultDir +  "SnapShot\\";
				FileHandling.createDir(mySnapShotDir);			
		}
		

      	public static  void createResultFolder(List TestCase) throws InterruptedException {
    		
      			traceMessage ="";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="-----------------------------------------------------------------------------------------------------------------";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="*** [Process #2(Step-2)] - Test Case Execution -Setup - createResultFolder";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="-----------------------------------------------------------------------------------------------------------------";
				Utility.traceLog(trace, true, traceMessage);
				
				//Test Case Dir
				myTestCaseDir = Validation.myGalaxyLogDir +  TestCase.get(7).toString().replace(".xlsm", "") + "\\" + Validation.myNetworkID + " " + myTScaseID  +"\\\\";
				Utility.traceLog(trace, true, traceMessage);
				FileHandling.createDir(myTestCaseDir);			
	
				myTestResultDir = myTestCaseDir +   DateTime.getDateTimeFormat(myTCRunTimeStart, "yyyy_MM_dd_HH_mm_ss")  +"\\\\";
				traceMessage= "[Test Result Dir]=:" + myTestResultDir;   
				Utility.traceLog(trace, true, traceMessage);
				FileHandling.createDir(myTestResultDir);			

				//SnapShot
				mySnapShotDir = myTestResultDir +  "SnapShot\\";
				FileHandling.createDir(mySnapShotDir);			
	}

		public static  void openURL(String browser, String url) throws Exception {
			
				WebDrivers.getBrowser(browser, url);
				WebDrivers.driver.get(url );
				myDriver = WebDrivers.driver;
				myDriver.manage().window().maximize();
				Snapshot.takeDriverSnapShot(myDriver, "local", "HomePage", mySnapShotDir);
		}		
		
		
		public static void setResultLogHeader () {
			
				/*==========================================================================
				 *Result Log header
				=============================================================================*/
				List LogHeader = new ArrayList();;
			
				LogHeader.add("na");
				LogHeader.add("ITFS");										//Vertical
				LogHeader.add(Validation.myProject);			//Project
				
				//String $TCAddress = "\\\\na\\corpshared\\apps\\quality assurance\\selenium\\automation\\Galaxy\\configuration\\WEC_PeopleSoft.xlsm";
				String $TCAddress = myTestResultDir + myTSfile;
				$TCAddress = $TCAddress.replace("\\\\", "\\");
				LogHeader.add("href;file;"+ myTSfile +";" + $TCAddress);
				//LogHeader.add(myTSfilePath + myTSfile);										//Test Case
				LogHeader.add(myTScaseID);
				LogHeader.add( "na");
				
				String $snapShot =   myTSfilePath + myTSfile;   //snapShot File
				LogHeader.add("");
				//LogHeader.add(myTestResultDir.replace("\\\\", "\\"));
				
				LogHeader.add(Validation.myNetworkID);
				LogHeader.add("Local");
				LogHeader.add(myTSbrowser);
				LogHeader.add(myTSurl);
				LogHeader.add("duration");
				//LogHeader.add(myTCrunTimeStart);
				LogHeader.add(myTCRunTimeStart);
				
				TestResultLog.myTestResultLog.add(LogHeader);
		}
		
		public static void setTC_ID(String TestCaseID) throws Exception {
		
				//All only
				myTC_ID[0] =  TestCaseID;
				myTC_ID[1] =  TestCaseID;
				
				String $myTC_ID[] = TestCaseID.split("/");
				//All and Alt Test Case ID
				if ($myTC_ID.length == 2 && ($myTC_ID[0].equalsIgnoreCase("All") || TestCaseID.equalsIgnoreCase("")))  {
					myTC_ID[0] =  myTScaseID;
					myTC_ID[1] = $myTC_ID[1].replace("#", "");
					}
				else {
					if ($myTC_ID.length == 2) {
						myTC_ID[0] = $myTC_ID[0];
						myTC_ID[1] = $myTC_ID[1].replace("#", "");
					}
				}
		}

		public static void setTestCaseStatus(String status )  {
			
			if (status.equalsIgnoreCase("Failed") && myTestCase_Status.equalsIgnoreCase("Passed")) {
				myTestCase_Status = "Failed";
			}			
	}
		
  		@After
  		public void tearDown() throws Exception {
  			
  				//write result to log to excel log
  				// html report
  				TestResultLog.writeTestLog();
  			}

 }
        

