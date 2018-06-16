package LTE.utilities;

import java.io.IOException;


//import com.rhi.itmcc.myGalaxy;
//import com.rhi.qa.ResultLog;
import com.rhi.utilities.*;


public class Checkpoint {

		public static String $runStatus;
		
		
		public static void selectVerifyMethod( String testMode, String verifyMethod,String message ) throws Exception {
			
			switch (verifyMethod.toUpperCase()) {
				case "VERIFYTEST": {
					System.out.println("[Verify method]=:" + verifyMethod + "; " + "[Test Mode]=:" + testMode + "; " + "[Message]=:"+ message);
					Checkpoint.verifyTest();
					break;
				}
				case "VERIFYBYINPUTVALUE": {
					System.out.println("[Verify method]=:" + verifyMethod + "; " + "[Test Mode]=:" + testMode + "; " + "[Message]=:"+ message);
					Checkpoint.verifyByInputValue("","");
					break;
				}
				case "VERIFYBYEXPECTEDVALUE": {
					System.out.println("[Verify method]=:" + verifyMethod + "; " + "[Test Mode]=:" + testMode + "; " + "[Message]=:"+ message);
					Checkpoint.verifyByExpectedValue("","");
					break;
				}
				case "VERIFYBYELEMENTEXISTS": {
					System.out.println("[Verify method]=:" + verifyMethod + "; " + "[Test Mode]=:" + testMode + "; " + "[Message]=:"+ message);
					Checkpoint.verifyByElementExists("","");
					break;
				}
				case "VERIFYBYDROPDOWNLIST": {
					System.out.println("[Verify method]=:" + verifyMethod + "; " + "[Test Mode]=:" + testMode + "; " + "[Message]=:"+ message);
					Checkpoint.verifyByDropdownList();
					break;
				}
				case "VERIFYBYCHECKBOXSELECTED": {
					System.out.println("[Verify method]=:" + verifyMethod + "; " + "[Test Mode]=:" + testMode + "; " + "[Message]=:"+ message);
					Checkpoint.verifyByCheckboxSelected();
					break;
				}
				case "VERIFYBYCHECKBOXSELECTION": {
					System.out.println("[Verify method]=:" + verifyMethod + "; " + "[Test Mode]=:" + testMode + "; " + "[Message]=:"+ message);
					Checkpoint.verifyByCheckboxSelection();
					break;
				}
				case "VERIFYBYSINGLECOLUMNTABLE": {
					System.out.println("[Verify method]=:" + verifyMethod + "; " + "[Test Mode]=:" + testMode + "; " + "[Message]=:"+ message);
					Checkpoint.VerifyBySingleColumnTable();
					break;
				}
				case "VERIFYBYMULTICOLUMNTABLE": {
					System.out.println("[Verify method]=:" + verifyMethod + "; " + "[Test Mode]=:" + testMode + "; " + "[Message]=:"+ message);
					Checkpoint.VerifyByMultiColumnTable();
					break;
				}
				case "VERIFYBYLABEL": {
					System.out.println("[Verify method]=:" + verifyMethod + "; " + "[Test Mode]=:" + testMode + "; " + "[Message]=:"+ message);
					Checkpoint.VerifyByLabel();
					break;
				}
				case "VERIFYBYANYTEXT": {
					System.out.println("[Verify method]=:" + verifyMethod + "; " + "[Test Mode]=:" + testMode + "; " + "[Message]=:"+ message);
					Checkpoint.VerifyByAnyText();
					break;
				}
				case "VERIFYBYRADIOBUTTON": {
					System.out.println("[Verify method]=:" + verifyMethod + "; " + "[Test Mode]=:" + testMode + "; " + "[Message]=:"+ message);
					Checkpoint.VerifyByRadioButton();
					break;
				}
			}
				// Write the checkpoint status to result log
				String $testData = "[type]=:" + " || " +  "[identifier]=:"+ "identifier" + " || "  + "[name]=:" + "objectName";
				Checkpoint.chkeckPointStatus(testMode, verifyMethod,$testData, Checkpoint.$runStatus,"");
		}
				
		
		public static void verifyTest() {
				System.out.println("[Verify method]=:" + "VerifyMethod");
				$runStatus = "pass";
		}

		public static void verifyByInputValue(String resultLog, String resultLogPath) {
				//System.out.println("Checkpoint Verification is comming soon ......");
				$runStatus = "fail";
		}
		
		public static void verifyByExpectedValue(String resultLog, String resultLogPath) {
				System.out.println("Checkpoint Verification is comming soon ......");
				$runStatus = "fail";
		}	
	
		
		public static void verifyByElementExists(String resultLog, String resultLogPath) {
				System.out.println("Checkpoint Verification is comming soon ......");
				$runStatus = "fail";
		}	
		
		public static void verifyByDropdownList() {
				System.out.println("Checkpoint Verification is comming soon ......");
				$runStatus = "fail";
		}
		
		public static void verifyByCheckboxSelected() {
				System.out.println("Checkpoint Verification is comming soon ......");
				$runStatus = "fail";
		}
		
		public static void verifyByCheckboxSelection() {
				System.out.println("Checkpoint Verification is comming soon ......");
				$runStatus = "fail";
		}
		
		public static void VerifyBySingleColumnTable() {
				System.out.println("Checkpoint Verification is comming soon ......");
				$runStatus = "fail";
		}
		
		public static void VerifyByMultiColumnTable() {
				System.out.println("Checkpoint Verification is comming soon ......");
				$runStatus = "fail";
		}
		
		public static void VerifyByLabel() {
				System.out.println("Checkpoint Verification is comming soon ......");
				$runStatus = "fail";
		}
		
		public static void VerifyByAnyText() {
				System.out.println("Checkpoint Verification is comming soon ......");
				$runStatus = "fail";
		}
		
		public static void VerifyByRadioButton() {
				System.out.println("Checkpoint Verification is comming soon ......");
				$runStatus = "fail";
		}	
		
		public static void chkeckPointStatus(String testState, String verifyMethod, String testData, String status, String Msg) throws IOException, InterruptedException {
				String $testState = "[Positive]";
				if (testState == "1") {
					$testState = "[Negative]";
					}
				String $desc = "    **********  CheckPoint " +$testState + "  **********  " + "\n[Verify method]=:" + verifyMethod + "; [Msg]=:" +Msg;  
				String $testData = "[type]=:" + " || " +  "[identifier]=:"+ "identifier" + " || "  + "[name]=:" + "objectName";
    			String $runStatus = status;
    		
				if (testState == "1" ) {
					if ($runStatus.toLowerCase()  == "pass") {
						$runStatus = "fail";
					}
					else {
						$runStatus = "pass";
					}
				}
				//	try {
				if ($runStatus.toLowerCase() == "fail") {
					//Snapshot.takeDriverSnapShot(WebDrivers.driver, "local", "webPage", myGalaxy.$snapShotFolder);
					//ResultLog.log	($desc, $testData, $runStatus, Snapshot.$file);
				}
				else {
					//ResultLog.log	($desc, $testData, $runStatus, "---");
				}
		}
}

