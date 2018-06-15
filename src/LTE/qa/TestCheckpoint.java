package LTE.qa;

import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import junit.framework.AssertionFailedError;

import org.junit.ComparisonFailure;
import org.openqa.selenium.WebElement;

import com.rhi.utilities.Checkpoint;
import com.rhi.utilities.WebElements;

public class TestCheckpoint {

		public static Date myTimeStart, myTimeEnd;
		
		public static void runCheckpoint(String checkPoint) throws Exception {
		
			String $checkPointStr = checkPoint;
				if ($checkPointStr.contains(";#")) {
					// Split it.
				} 
				else {
					throw new IllegalArgumentException("String " + $checkPointStr + " does not contain -");
				}
				String[] $checkPoint	 = $checkPointStr.split(";#");
				int j = $checkPoint.length;
				for (int i=0; i <$checkPoint.length; i++) {
					//System.out.println($checkPoint[i]);
				}
				if ($checkPoint.length != 3) 
					throw new IllegalArgumentException("String not in correct format");
				else {
				}
				System.out.println("***** CheckPoint *****");
				Checkpoint.selectVerifyMethod($checkPoint[0],$checkPoint[1],$checkPoint[2]);
		}
		
		
//		public static void VerifyInput(WebElement myElement, String WEIType, String ExpectedText) throws Exception { 
	
		public static void VerifyValue(String myWEI, String myValue, String ActualText, String ExpectedText) throws Exception { 
			
			myTimeStart = new Date();
			myTimeEnd = new Date();
			String $ActualText ="";
			String myErrorMsg = "-----";
		
			/*
			switch (WEIType.toUpperCase()) {
				case ("WEBEDIT"): {
					$ActualText = myElement.getAttribute("value");
					if ($ActualText == null) {
						$ActualText = myElement.getText();
					}
					System.out.println($ActualText );
					break;
				}
				case ("WEDLINK"): {
					$ActualText = myElement.getText();
				}
			}
			*/
			try {
				assertEquals(ExpectedText, $ActualText);				
			}
			catch (ComparisonFailure e) {
				//e.printStackTrace();
			    StringWriter errors = new StringWriter();
			    e.printStackTrace(new PrintWriter(errors));
			    myErrorMsg = errors.toString();
			    WebElements.myRunStatus = "Failed";
			}
			finally {
				  System.out.println("***** Done *****");
				  System.out.println("*** " + WebElements.myRunStatus + "***" + myErrorMsg);
				  TestResultLog.updateStepLog("myDesc", "testData", WebElements.myRunStatus, myTimeStart, myTimeEnd, "", myErrorMsg);
			}
		}
		
	
		public static void zVerifyInput(String WEIType,  String ActualText, String ExpectedText, int TestMode) throws Exception { 
			
			String $ActualText = ActualText;
			String $ErrorMsg = "-----";
			
			switch (WEIType.toUpperCase()) {
				case ("WEBEDIT"): {
					//$ActualText = myElement.getAttribute("value");
					$ActualText = new String($ActualText);
					break;
				}
			}
			
			try {
			 	assertEquals(ExpectedText, $ActualText);			
			}
			catch (ComparisonFailure e) {
				e.printStackTrace();
			    StringWriter errors = new StringWriter();
			    e.printStackTrace(new PrintWriter(errors));
			    $ErrorMsg = errors.toString();
			    WebElements.myRunStatus = "Fail";
			}
			finally {
			  System.out.println("***** Comparsion Done *****");
//			  System.out.println("*** " + myRunStatus + "***" + $ErrorMsg);
			}
		}
		
		
		public static void VerifyElementExist(WebElement myElement, int TestMode) throws Exception { 
		
		//		public static void VerifyElementExist(String myElement, int TestMode) throws Exception { 
					
				String $runStatus = "pass";
				String $ErrorMsg = "-----";
				
				String str1 = null;
				
				try {
				 	assertNotNull(myElement);
				//	assertNull(myElement);
					
				}
				catch (ComparisonFailure e) {
			    	e.printStackTrace();
			    	StringWriter errors = new StringWriter();
			    	e.printStackTrace(new PrintWriter(errors));
			    	$ErrorMsg = errors.toString();
			    	$runStatus = "Fail";
				}
				catch (AssertionError e) {
			    	//e.printStackTrace();
			    	StringWriter errors = new StringWriter();
			    	e.printStackTrace(new PrintWriter(errors));
			    	$ErrorMsg = errors.toString();
			    	$runStatus = "Fail";
				}
				finally {
				  System.out.println("***** Done *****");
				  System.out.println("*** " + $runStatus + "***" + $ErrorMsg);
				}
		
		}
}
			

			
	




