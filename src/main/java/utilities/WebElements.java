package main.java.utilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;




import com.rhi.apps.PeopleSoft;
//import com.rhi.apps.ITMCC;
import com.rhi.apps.Salesforce;
import com.rhi.qa.TestCheckpoint;
import com.rhi.qa.TestExecution;
import com.rhi.qa.TestResultLog;
import com.rhi.qa.myGalaxy;

public class WebElements {

		public Alert alert;
		public static List myStep, myData, myWEC;
		public static String myWEI, myWEType, myIdentifer, myValue, myLabel, myFrame;
		public static String myURL;
		public static String myRunStatus;
		public static List stepLog = new ArrayList();
		public static WebElement myElement = null;
		public static List <WebElement> myElements = null;
		public static List myWTElements = new ArrayList();
		public static String myTestStep;
		public static int stdtime = 2000; 	//sleep time in millsecond
		public static int timeoutMs = 5000;
		public static int myRow;	
		
		public static String traceMessage;
		public static Boolean trace; 
		public static String myScrLvl = "";
		public static int myStepNbr;
		public static Boolean positiveMode; 
		public static String myExpectedValue, myActualValue;
		public static Boolean getWEIValueMode, findWEIMode;
		
		
		
		//Step Log
		public static String myTestData, myDesc, myScreenShotFile;
		public static Date myTimeStart, myTimeEnd;
		public static String myApps = "PS";
	
		public static Boolean myRun = true,storedInputValueMode = false, storedOutputValueMode = false, checkpointMode = false;
			
		public static void runStep(WebDriver driver, List runTestStep) throws Exception {

				trace = new Boolean (Validation.trace);
				Setup(driver, runTestStep);
				//App Specific
				if (myApps.contentEquals(myIdentifer)) {
					AppSpecific(driver, myIdentifer, runTestStep);
				}
				//Standard identifier
				else { 
					if (findWEIMode == true) {
						findElement(driver, myIdentifer, myWEI, myValue);
					}
					if (myElement !=null && myRun == true) {
						run(driver, myWEType, myElement, myValue);
					}	
					//checkpoint
					if (checkpointMode == true) {
						TestCheckpoint.VerifyValue(myWEI, myValue, myActualValue, myExpectedValue);	
					}
				}
		}
			
		
		private static void Setup(WebDriver driver, List runTestStep) throws Exception{
				
				myStepNbr = (int) runTestStep.get(0);
				myStep =   (List) runTestStep.get(2);
				myData =  (List) runTestStep.get(3);
				myWEC =   (List) runTestStep.get(4);
				
				storedInputValueMode = false;
				storedOutputValueMode = false;
				myRun = true;
				checkpointMode = false;
				
				/*-----------------------------------------------------------------------------------------------------------
				//Web Element Catalog
				*-----------------------------------------------------------------------------------------------------------*/
				myLabel = myWEC.get(2).toString();			//Label for button
				myWEI = myWEC.get(3).toString();				//Web Element ID
				myWEType = myWEC.get(4).toString();		//WebElementType		
				myIdentifer = myWEC.get(5).toString();			//identifier
		
				//Scroll level
				if (myScrLvl !="" && myWEI.endsWith("$")) {
					myWEI = myWEI + myScrLvl;
				}
				
				//---------------------------------------------------------------------------------------------
				//Value'
				//---------------------------------------------------------------------------------------------
				myValue = myData.get(4).toString();
				
				//Stored Value
				//Stored inputValue
				if (myValue.startsWith("&&") && myValue.endsWith("&&") ) {
					//Utility.addStoredValue("&&" + myWEI + "&&", myValue.replaceAll("&&", ""));
					storedInputValueMode = true;
					myValue = myValue.replaceAll("&&", "");
				}
				// Store Output
				else if (myValue.equalsIgnoreCase("&&output")) {
					storedOutputValueMode = true;
					myRun = false;
				}
					
				//get Stored Value for input
				if (myValue.startsWith("&") && myValue.endsWith("&")) {
					Utility.getStoredValue(myValue); 
					myValue = Utility.getStoredValue(myValue); 
					System.out.println(myValue) ;
				}
				
				//Checkpoint - Input Value after enter
				if (myValue.startsWith("#") && myValue.endsWith("#")) {
						System.out.println("Checkpoint");
						checkpointMode = true;
						myValue = myValue.replaceAll("#", "");
						return;
				}

				// #Output vs Expected   &&#[Expected]#
				if (myValue.startsWith("&&#") && myValue.endsWith("#")) {
						System.out.println("Checkpoint");
						checkpointMode = true;
						storedOutputValueMode = true;
						myRun = false;
						myValue = myValue.replaceAll("&&","").replaceAll("#", "");
						return;
				}

				//Row Index
				if (!myValue.equalsIgnoreCase("") && myValue.startsWith("#") && !myValue.endsWith("#")) {
					myValue = myValue.replaceAll("#", "");
					myRow = Integer.parseInt(myValue);
				}
		
				myTestData = " [Label]=:" + myLabel + "; \n" +   " [Value]=:" + myValue;
				myDesc = "[Type]=:" +  myWEType + " || "  +  "[Identifier]=:"+ myIdentifer + " || "  + "[WEI]=:" + myWEI + " || "  ;

				traceMessage="*** Step #1 - Setup ***  ";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="[Step]=:" + myStep;
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="[Data]=:" + myData;
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="[WEC]=:" + myWEC;
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="[Run Step]=:" + myTestStep;
		}
				

		private static void AppSpecific(WebDriver driver, String myIdentifier, List runTestStep) throws Exception{

				//identifer
				System.out.println(myIdentifier);
				switch (myIdentifier.toUpperCase()) {
					case "SF": {
						Salesforce.SF_Method(driver, runTestStep);
					break;
						}
					case "ITMCC": {
						//ITMCC.SF_Method(driver, runTestStep);
						break;
					}
					case "PS": {
						if (myWEI.equalsIgnoreCase("ScrLvl")) {
							myScrLvl = myValue;
						}
						PeopleSoft.PS_Method(driver, runTestStep);
						break;
					}
					case "TCAST": {
						break;
					}
					case "IZ": {
					//	IZONE.izone_Method(driver, runTestStep);
						break;
					}
				}
		}
				
		
//		public static WebElement findElement(WebDriver driver, String Identifier, String WEI, List runTestStep) throws Exception {
		public static WebElement findElement(WebDriver driver, String Identifier, String WEI, String Value) throws Exception {
					
				String myIdentifier = Identifier.toUpperCase();
				String myErrorMsg = "";
				final String $myWEI = WEI;
	
				myFrame = myData.get(5).toString();
				if (myFrame.isEmpty()) {
					driver.switchTo().defaultContent();
				}
				else if (myFrame.equalsIgnoreCase("Default")) {
					//equalsIgnoreCase("TargetContent")) {
					driver.switchTo().defaultContent();
				}
				else {
					driver.switchTo().defaultContent();
					driver.switchTo().frame(myFrame);
				}
	
				/*-----------------------------------------------------------------------------------------------------------
				 *Initiate WebElement by Identifier
				 *-----------------------------------------------------------------------------------------------------------*/
				traceMessage="";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="*** Step #2 - Perform findElement method***  ";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="[findElement]* =:";
				Utility.traceLog(trace, false, traceMessage);
			
				//Standard Identifier
				myTimeStart = new Date();
				myTimeEnd = new Date();
				try {
					//System.out.println(myIdentifier);
					switch (myIdentifier.toUpperCase()) {
						case "ID": {
							//myElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.id(myWEI)));
							myElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.id($myWEI)));
							break;
						}
						case "XPATH": {
							Utility.testExecutionSleep(stdtime);
							if (myWEType.equalsIgnoreCase("RadioButton")) {
								myElement = driver.findElement(By.xpath("//input[@value='" + myWEI + "']"));
							}
							else {
								//Thread.sleep(3000);
								//myElement = (new WebDriverWait(driver, 30)).until(new ExpectedCondition<WebElement>(){@Override
									myElement = driver.findElement(By.xpath($myWEI));
									//public WebElement apply(WebDriver d) {
								//		return d.findElement(By.xpath($myWEI));
							//	}});
							}
							break;
						}
						case "NAME": {
							Utility.testExecutionSleep(stdtime);
							myElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.name(myWEI)));
							//myElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.name(myWEI)));
							break;
						}
						//case "SPNAME": {	
						//	Utility.testExecutionSleep(time);
						//	myElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.name(myWEI)));
						//	break;
						//}						
						case "LINKTEXT": {
							Utility.testExecutionSleep(stdtime);
							myWEI.replaceAll("&","");
							myWEI = myValue;
							myElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.linkText(myWEI)));
							break;
						}
						case "PARTIALLINKTEXT": {
							Utility.testExecutionSleep(stdtime);
							myElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(myWEI)));
							break;
						}
						case "CSS": {
							Utility.testExecutionSleep(stdtime);
							myElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(myWEI)));
							break;
						}
						case "URL" : {
   	  						myURL = myValue;
   	  						driver.get(myURL);
   	  						break;
   	  					}
						case "OBJECTLINK": {
							Utility.testExecutionSleep(stdtime);
							myElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.linkText(myValue)));
							traceMessage="[OBJECTLINK]=:" + myValue;
							break;
						}
						case "NAVIGATEBACK":{
							if (myValue.toUpperCase().equals("Y")){ // go back to previous screen
								driver.navigate().back(); 
								Utility.waitForPageFullyLoaded(driver, timeoutMs);
								break;
							}
						}
					}
				}
				catch (Exception e) {
					myTimeEnd = new Date();
				 	myRunStatus = "failed";
					myTestStep = "*** NoSuchElementException *** \n" + myTestStep;
					e.printStackTrace();
					StringWriter errors = new StringWriter();
				    e.printStackTrace(new PrintWriter(errors));
				    myErrorMsg = errors.toString();
		    		
				    takeScreenShot(driver,Validation.myScreenShot,myRunStatus);
		    	    TestResultLog.updateStepLog(myDesc, myTestStep, myRunStatus, myTimeStart, myTimeEnd, myScreenShotFile,  myErrorMsg );
				    //updateStepLog(driver, myTestStep, myRunStatus);
		 	    	
			 		traceMessage="";
		      		Utility.traceLog(trace, false, traceMessage);
		      		traceMessage="z*** WEC Not found";
					Utility.traceLog(trace, false, traceMessage);
					traceMessage="";
					Utility.traceLog(trace, false, traceMessage);
			    	System.out.println(myTestStep);
				}
				finally {
					if (!(myElement == null)) {
						traceMessage="WEC found";
						Utility.traceLog(trace, false, traceMessage);
						traceMessage="";
						Utility.traceLog(trace, true, traceMessage);
						if (getWEIValueMode == true) {
							myActualValue = myElement.getAttribute("value");
							if (myActualValue == null) {
								myActualValue = myElement.getText();
							}
						}
						System.out.println(myActualValue );
					}
					if (storedOutputValueMode == true && !(myElement == null )) {
				 		System.out.println(myElement.getAttribute("value"));
			    		//Utility.addStoredValue(myWEI, myElement.getAttribute("value"));
			    		Utility.addStoredValue(myWEI, myElement.getText());
			    		
 					}
					return myElement;
				}
				}

		
		public static void run(WebDriver driver, String WEIType, WebElement myElement, String myValue) throws Exception {
			
				String myErrorMsg = "";
			
				/*-----------------------------------------------------------------------------------------------------------
				 *Perform action by Web Element Type
				 *-----------------------------------------------------------------------------------------------------------*/
				traceMessage="";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="*** Step #3 - [Execution Action*]";
				Utility.traceLog(trace, true, traceMessage);
				traceMessage="[Execute]* =:";
				Utility.traceLog(trace, true, traceMessage);
				String $errMsg = "";
			
				try {
					switch (WEIType.toUpperCase()) {
						case "WEBEDIT" : {
   	  						myElement.clear();
   	  						myElement.sendKeys(myValue);
   	  						//myElement.sendKeys(Keys.ENTER);
   	  						driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
   	  						traceMessage="[Text]" + myElement.getAttribute("value");
   	  						Utility.traceLog(trace, true, traceMessage);
   	  						String $actual = myElement.getAttribute("value");
   	  					 	myRunStatus = "Passed";
   	  					 	break;
   	  					}
						case "WEBBUTTON" : {
   	  						Utility.testExecutionSleep(stdtime);
							if (myElement.isDisplayed()) {
								Utility.forceClick(myElement, WebDrivers.browser);
								//myElement.click();
								myRunStatus = "Passed";
				  			}
							Utility.testExecutionSleep(stdtime);
							Utility.waitForPageFullyLoaded(driver, timeoutMs);
							break;
   	  					}
   	  					case "WEBFILE"  : {
   	  						myElement.sendKeys(myValue);
   	  						Utility.testExecutionSleep(stdtime);
							myRunStatus = "Passed";
	  						break;
   	  					}
   	  					case "WEBRADIOBUTTON" : {
   	  						String checkFlag = myValue;
	  						if ((checkFlag).equalsIgnoreCase("ON") & !(myElement.isSelected())){
	  							myElement.click();
	  						 	myRunStatus = "Passed";
	  		  				}
	  						Utility.testExecutionSleep(stdtime);
							break;
	  					}
   	  					case "WEBSELECT" : {  //DROPDOWN
   	  						List<WebElement> listedItems =myElement.findElements(By.tagName("option"));
	  						int rows=listedItems.size();
	  						if (rows > 1){
	  							/*if (myValue.contains("#")){ //extract the link text of by input index
	  								myValue=myValue.replace("#", "");
	  								myValue=listedItems.get(Integer.parseInt(myValue)).getText();
	  							}*/
	  							// Change the conditional statement to Match pattern for number(s)
	  							if (myValue.matches("[0-9]+")){ //extract the link text of by input index
	  								//myValue=myValue.replace("#", "");
	  								myValue=listedItems.get(Integer.parseInt(myValue)).getText();
	  							}
	  							//extract the link texts of each link element
	  							for (WebElement e : listedItems) {
	  								if (e.getText().equals(myValue)){
	  									e.click();
	  									break;
	  								}
	  							}
	  						}else{
	  							traceMessage="No data is found in the list";
	  							Utility.traceLog(trace, true, traceMessage);
	  							//Reporter.log("No data is found in the list");
	  						}
	  					 	//myRunStatus = "Passed";
	  					 	//Utility.testExecutionSleep(stdtime);
							break;
	  					}
	  					case "WEBLINK" : {
	  						//Utility.forceClick(myElement, WebDrivers.browser);
   	  						myElement.click();
   	  						myRunStatus = "Passed";
   	  						Utility.testExecutionSleep(stdtime);
							break;
   	  					}
   	  					case "WEBIMAGE" : {
   	  						//webLink(driver,"xpath","//a[@href='CustomerInfo.htm']");
   	  						//System.out.println($Identifier +$value);
   	  						//Elements.webLink(driver,$Identifer,$Value);
   	  						//myRunStatus = "Passed";
	  						break;
   	  					}
   	  					case "WEBCHECKEDBOX" : {
   	  						boolean checkBox;
   	  						Utility.testExecutionSleep(stdtime);
   	  						/*if (myElement.isSelected() & myValue.toUpperCase().equals("N")){
   	  							myElement.click();
   	  							myRunStatus = "Passed";
   	  						}
   	  						if (!myElement.isSelected() & myValue.toUpperCase().equals("Y")){
	  							myElement.click();
	  							myRunStatus = "Passed";
   	  						}*/
   	  						if (!myElement.isSelected()){
   	  							// IE requires to hit ENTER and then click on a object
   	  						//'	Utility.	forceClick(myElement, WebDrivers.browser);
   	  							if (!myElement.isSelected()){
   	  								myElement.click();
   	  							}
   	  							//myElement.click();      	  							 	  							
   	  							
   	  						}
   	  						Utility.testExecutionSleep(stdtime);
   	  						if (myElement.isSelected()){
	  							myRunStatus = "Passed";
   	  						}else{
   	  							myRunStatus = "Failed";
   	  						}
   	  						
   	  						Utility.testExecutionSleep(stdtime);
   	  						//checkBox = myElement.isSelected(); 	//store this status to validate of the checkbox later
   	  						break;
   	  					}
   	  					case "URL" : {
   	  						myURL = myValue;
   	  						driver.get(myURL);
   	  						break;
   	  					}
   						case "OBJECTLINK" : {
							Utility.testExecutionSleep(stdtime);
							myElement.click();
							Utility.testExecutionSleep(stdtime);
							break;
						}
						case "WINDOWS" : {
							//webLink(driver,"xpath","//a[@href='CustomerInfo.htm']");
							// closeJscriptPopup(driver,alert);
							//myRunStatus = "Passed";
							//break;
						}
						/*case "NAVIGATEBACK":{ // go back to previous screen
							if (myValue.toUpperCase()=="Y"){
								driver.navigate().back(); 
								//Utility.testExecutionSleep(stdtime);
								Utility.waitForPageFullyLoaded(driver, timeoutMs);
								break;
							}
						}*/
					}
					myTimeEnd = new Date();
					//Stored actual'
					myActualValue = myElement.getAttribute("value");
					System.out.println(myActualValue);
					if (myActualValue == null) {
						myActualValue = myElement.getText();
					}
					System.out.println(myActualValue);
					
					// Store Value
					if (storedInputValueMode == true) {
			    		Utility.addStoredValue(myWEI, myActualValue);
			   	 	}
				}
				catch (Exception e) {
			    	myTimeEnd = new Date();
				 	myRunStatus = "failed";
				 	myTestStep = "*** Exception *** \n" + myTestStep;
				 	e.printStackTrace();
					StringWriter errors = new StringWriter();
				    e.printStackTrace(new PrintWriter(errors));
				    myErrorMsg = errors.toString();
			    	System.out.println(myTestStep);
				}	
		    	finally {
		    		//take screen shot
		    		takeScreenShot(driver,Validation.myScreenShot,myRunStatus);
		    	    TestResultLog.updateStepLog(myDesc, myTestStep, myRunStatus, myTimeStart, myTimeEnd, myScreenShotFile, myErrorMsg);
					//updateStepLog(driver, myTestStep, myRunStatus);
		    		TestExecution.setTestCaseStatus(myRunStatus);
		    		
		    		// Saleforce
		    		if (myWEI.equalsIgnoreCase("save"))  {
  			    		Salesforce.SF_getSaveErrorMessge(driver);
  					}
		    	}	
		}
			

		public static void takeScreenShot(WebDriver driver,  String ScreenShotMode, String stepStatus) throws InterruptedException, IOException {

			//String $snapShotMode = "3";
			String $SnapShotDir = null, $SnapShotFile = null, $SnapShotFileName=null;  
			Date $TimeStamp = new Date();

			ScreenShotMode  = "0-";
			//SnapShot'  0-None, 1-All, 2-Pass 3, -Fail
		/*	if (StepMode.equalsIgnoreCase("")) {
				$snapShotMode = new String(myGalaxy.myScreenShot);
			}
			else {
				$snapShotMode = StepMode;
			}
			*/
			if (ScreenShotMode.startsWith("0") || ScreenShotMode == null)  {
					//no screenshot
			}
			else {
				String $snapShotTmeStamp = DateTime.getDateTimeFormat( myTimeEnd, "yyyy_MM_dd_HH_mm_ss");
				//$TimeStamp  = DateTime.getDateTimeFormat(new Date(), "HH_mm_ss_SSS");
			
				if (stepStatus.equalsIgnoreCase("failed")) {
	//				if ($snapShotMode.equalsIgnoreCase("FAIL")  || $snapShotMode.equalsIgnoreCase("ALL")) {
					if (ScreenShotMode.startsWith("3-") || ScreenShotMode.startsWith("1-")) { 
						$SnapShotFileName =  "Err-" + TestExecution.myRunTestID[1] + "-" + $snapShotTmeStamp + "-" + myStepNbr+ ".gif" ;
						$SnapShotFileName = $SnapShotFileName.replace("\\\\", "\\");
						Snapshot.takeDriverSnapShot(driver, "local", $SnapShotFileName , TestExecution.mySnapShotDir);
					}
				}
				else if (stepStatus.equalsIgnoreCase("passed")) {
					if (ScreenShotMode.startsWith("2-") || ScreenShotMode.startsWith("1-")) { 
					//if ($snapShotMode.equalsIgnoreCase("PASS") || $snapShotMode.equalsIgnoreCase("ALL")) {
						$SnapShotFileName =  "pass-" +  $snapShotTmeStamp + "-" + myStepNbr + ".gif" ;
						$SnapShotDir = TestExecution.mySnapShotDir.replace("\\\\", "\\");
						Snapshot.takeDriverSnapShot(driver, "local", $SnapShotFileName , $SnapShotDir);
					}
				}
				myScreenShotFile = "href;file;"+ "$name" +";" +  $SnapShotDir + $SnapShotFileName;
				
			}	
		
		}	
	

		public static void myValue(String myValue) throws Exception {

				
				//myValue = myData.get(4).toString();
				myLabel = "EMPLID";
				myWEI = "Person";
				System.out.println(myValue);
				
				//Stored Input Value
				if ((myValue.startsWith("%") || myValue.startsWith("#"))  &&  (myValue.endsWith("%") ||  myValue.contains("%%%"))) {
					storedInputValueMode = true;
					String $Variable [] = null;
					if (myValue.contains("%$WEI%")) {
						getWEIValueMode = true;
					}	
					else {	
						if (myValue.startsWith("%")) { 
							$Variable = myValue.split("%");
						}	
						else {
							$Variable = myValue.split("#");
						}				
						//keyword
						if  (myValue.contains("#%%%")) {
							Utility.addStoredValue($Variable[2].replaceAll("%%%",""), $Variable[1]);
						}
						else if  (myValue.contains("%%%")) {
							Utility.addStoredValue($Variable[4], $Variable[1]);
						}
			
						//label
						else if 	(myValue.endsWith("%%")) {
							Utility.addStoredValue(myLabel,  $Variable [1]);
						}
						//  WEI
						else 	if  (myValue.endsWith("%")) {
							Utility.addStoredValue( myWEI, $Variable [1]);
						}
					}	
				}
				//get Stored Value for input
				if ((myValue.startsWith("&") ||  myValue.startsWith("#&")) 
						&& (myValue.endsWith("&") || myValue.endsWith("&#"))) {
					myValue = Utility.getStoredValue(myValue.replaceAll("#","")); 
					System.out.println(myValue) ;
				}
		
				//Checkpoint - Input Value after enter
				if (myValue.startsWith("#") && (myValue.contains("#") || myValue.contains("#%") ||myValue.contains("#&"))) {
					checkpointMode = true;
					positiveMode = true;
					System.out.println("Checkpoint");
					if (myValue.contains("##")) {
						positiveMode = false;
					}						
				}

				// Expected result
				if (checkpointMode ==true) {
					System.out.println("Checkpoint");
					// verify element value vs stored value
					if (myValue.startsWith("#&") || (myValue.endsWith("&#") || myValue.endsWith("&##"))) {
						getWEIValueMode = true;
						myRun = false;
						myExpectedValue = Utility.getStoredValue(myValue.replaceAll("#", ""));
					}
					//Verify get element value vs myValue
					else if  (myValue.startsWith("#") && myValue.endsWith("#&")) {
						getWEIValueMode = true;
						myRun = false;
						myExpectedValue = myValue.replaceAll("#", "").replaceAll("&", "");
					}
					//Verify get element value vs Input Value
					else {
						String $myExpectedValue [] = myValue.split("#");
						myExpectedValue = $myExpectedValue [1].replaceAll("#", "");
					}
					System.out.println("[Expected Value]:=" + myExpectedValue);
				}
							
		/*		
				//Row Index
				if (!myValue.equalsIgnoreCase("") && myValue.startsWith("#") && !myValue.endsWith("#")) {
					myValue = myValue.replaceAll("#", "");
					myRow = Integer.parseInt(myValue);
				}

				myTestData = " [Label]=:" + myLabel + "; \n" +   " [Value]=:" + myValue;
				myDesc = "[Type]=:" +  myWEType + " || "  +  "[Identifier]=:"+ myIdentifer + " || "  + "[WEI]=:" + myWEI + " || "  ;
*/
		}
		
		public static void zzupdateStepLog(WebDriver driver,  String testData, String stepStatus) throws InterruptedException, IOException {

				//'Update '
				String duration = DateTime.getDateTimeDiff(myTimeStart, myTimeEnd);
				stepLog.clear();
				stepLog.add("");
				stepLog.add(myDesc);
				stepLog.add(myTestData);
				stepLog.add(stepStatus);
				stepLog.add(myScreenShotFile);
				stepLog.add(duration);
				TestResultLog.writeStepLog(WebElements.stepLog);
		}

		
		
}						