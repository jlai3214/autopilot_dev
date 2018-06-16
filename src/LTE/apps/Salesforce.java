package LTE.apps;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import LTE.qa.TestResultLog;
import LTE.utilities.Utility;
import LTE.utilities.WebDrivers;
import LTE.utilities.WebElements;

public class Salesforce {

		
	public static List myStep, myData, myWEC;
	public static String myWEI, myWEType, myIdentifer, myValue, myLabel;
	
	public static WebElement myWebTable = null;
	public static List <WebElement> myWTElements;
	public Alert alert;
	public static String myRunStatus;
	public static List stepLog = new ArrayList();
	public static WebElement myElement = null;
	public static List <WebElement> myElements = null;
	public static WebElement myElement_unSelected;
	public static WebElement myElement_Selected;
	public static String myTestStep;
	public static int myRow;	
	public static boolean myRun;	
	public static String search = "lksrch";

	public static String myPicklist,  myPicklistElementID;
	public static Utility util = new Utility();
	public static String $parentHandle,$childHandle;
		
	public static List myWTElementsList = new ArrayList();
	public static int myWTColumn, myWTRow;
		
	public static int stdtime = 2000; 	//sleep time in millsecond
		public static int timeoutMs = 5000;
		public static String traceMessage;
		public static Boolean trace;
		private static int elementIndex=0;

		
		public static void SF_Method(WebDriver driver, List runTestStep) throws Exception {	
		
			
				trace = Validation.trace;
				myStep =   (List) runTestStep.get(2);
				myData =  (List) runTestStep.get(3);
				myWEC =   (List) runTestStep.get(4);
			
				//Web Element Catalog
				myLabel = myWEC.get(2).toString();			//Label for button
				myWEI = myWEC.get(3).toString();				//Web Element ID
				myWEType = myWEC.get(4).toString();		//WebElementType		
				myIdentifer = myWEC.get(5).toString();	
				
				myValue = myData.get(4).toString();
				if (myValue.substring(0, 1).equalsIgnoreCase("&")) {
					myValue = Utility.getStoredValue(myValue,"actual");
				}
				if (myValue.substring(0, 1).equalsIgnoreCase("#")) {
					myValue = myValue.replaceAll("#", "");
					myRow = Integer.parseInt(myValue);
				}
				switch (myWEType.toUpperCase()) {
					case "WEBLOOKUP_SF" : {
						SF_WebLookup(driver, runTestStep);
						break;
					}
					case "OBJECTLINK_SF" : {
						SF_ObjectLink(driver);
						break;
					}
					case "UPLOADDOC_S_SF": {
						SF_UpLoadDoc_S(driver);
						break;
					}	
					case "UPLOADDOC_E_SF": {
						SF_UpLoadDoc_E(driver);
						break;
					}	
					case "SELECTBOX_SF": {
						SF_SelectBox(driver); 
						break;
					}
					case "LOOKUPLINK_SF": {
						SF_LookupLink(driver);
						break;
					}
					case "WEBBUTTON_SF" : {
						SF_WebButton(driver);
						break;
					}
					case "MULTISELECTPICKLISTROW_SF": {
						SF_MutliSelectPickListRow(driver);
						break;
					}
					case "MULTISELECTPICKLISTCELL_SF": {
						SF_MutliSelectPickListCell(driver);
						break;
					}
					case "WEBTABLELINKS_SF": {
						SF_WebTable(driver, runTestStep);
						break;
					}
					case "WEBTABLE_SF": {
						SF_WebTable(driver, runTestStep);
						break;
					}
					case "WTELEMENT_SF": {
						SF_GetWebTableElement(driver, runTestStep);;
						break;
					}
					case "URL_SF": {
						SF_URL(driver);
						break;
				
					}
					case "WEBDELETE_SF":{
						SF_WebDelete(driver);
					}
				}
		}

		
		/*---------------------------------------------------------------------------------------------------
		*Salesforce specific method
		*----------------------------------------------------------------------------------------------------*/
		public static void SF_WebDelete(WebDriver driver){
			
			myElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.name(myWEI)));
			// IE requires to hit ENTER and then click on a object
			Utility.forceClick(myElement, WebDrivers.browser);
			//myElement.sendKeys(Keys.ENTER);
			//myElement.click();
			Utility.testExecutionSleep(stdtime);
			// accept popup confirmation
			Utility.popupAlert(driver);
			Utility.testExecutionSleep(stdtime);
			Utility.waitForPageFullyLoaded(driver, timeoutMs);
	
		
		}
		public static void SF_WebTable(WebDriver driver, List runTestStep) throws Exception {
				
				myWTElementsList.clear();
				myTestStep = "*** Execution Test Step ***  [Type]=:" +  myWEType + " || "  +  "[Identifier]=:"+ myIdentifer + " || "  + "[WEI]=:" + myWEI + " || "  + "[Value]=:" + myValue + " || "  + "[Label]=:" + myLabel;
				traceMessage=myTestStep;
				Utility.traceLog(trace, true, traceMessage);
	
				myWTElementsList.add("header");
				if (myValue.equalsIgnoreCase("Y")) {
					
					myWEI = driver.getCurrentUrl().substring(driver.getCurrentUrl().length()-15)+"_"+myWEI;									
					myWebTable = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.id(myWEI)));
					List $rows = myWebTable.findElements(By.tagName("tr"));
					myWTElements = myWebTable.findElements(By.tagName("td"));
					int $Cols = myWTElements.size() / ($rows.size()- 1);   //exclude the header
					
					traceMessage="Numer of rows in this table(exclude header)=: "+ ($rows.size()-1);
					Utility.traceLog(trace, true, traceMessage);
					traceMessage="Total elements in this table(exclude the key column)=: " + myWTElements.size();
					Utility.traceLog(trace, true, traceMessage);
					traceMessage="Total Columns in this table(exclude the key column)=: " + $Cols;
					Utility.traceLog(trace, true, traceMessage);
					
					int $row = 0;
					for (int i = 0; i < myWTElements.size(); i++) {
						List $WTRow = new ArrayList();
						$row ++;
						$WTRow.add($row);
						for (int j = 1 ; j <= $Cols; j++) {
							List $myElement = new ArrayList();
							$myElement.add(j);
							$myElement.add("text");
							$myElement.add(myWTElements.get(i));
							
							$WTRow.add($myElement);
							//traceMessage=$WTRow.get(j);
							if (j != $Cols) {
								i ++;
							}
							else if (j == $Cols) {
								myWTElementsList.add($WTRow);
								traceMessage=myWTElementsList.toString();
							}
						}
					}
					
					//key column
					myWTElements = myWebTable.findElements(By.tagName("th"));
					traceMessage="Numer of rows in key Column (Col 1)=: " + myWTElements.size();
					Utility.traceLog(trace, true, traceMessage);
					$row = 0;
					
					List $header = new ArrayList();
					$header.add(myWEI);
					for (int i =  0; i <= $Cols; i++) {
						traceMessage= myWTElements.get(i).getText();
						Utility.traceLog(trace, true, traceMessage);
						$header.add(myWTElements.get(i).getText()); 
					}
					myWTElementsList.set(0, $header);
					//traceMessage=$header;
				
					// insert key column (Col2)
					for (int j =  $Cols+1; j < myWTElements.size(); j++) {
						WebElement $keyElement =  (myWTElements.get(j)).findElement(By.tagName("a"));
						//traceMessage="myWTElements.get(j).getText());
						traceMessage=$keyElement.getText();
						Utility.traceLog(trace, true, traceMessage);
						$row ++;
						
						List $myElement = new ArrayList();
						$myElement.add(j);
						$myElement.add("WEBLINK");
						$myElement.add($keyElement);
						
						List $WTRow = (List) myWTElementsList.get($row);
						$WTRow.add(2, $myElement);
						myWTElementsList.set($row, $WTRow);
			
						//Checking
						if ($myElement.get(2) != null ) {
							String aa1 = ((WebElement) $myElement.get(2)).getAttribute("href").toString();
							traceMessage=aa1;
							Utility.traceLog(trace, true, traceMessage);
							WebElement myElement = (WebElement) $myElement.get(2);
						}
					}
					SF_GetWebTableElements();
				}	
		}			
				
		
		public static void SF_GetWebTableElements() throws Exception {
				
				List WTHeader = (List) myWTElementsList.get(0);
			
				for (int i= 1 ; i < myWTElementsList.size(); i++) {
					List WTRow = (List) myWTElementsList.get(i);
					traceMessage="";
					for (int j = 1 ; j < WTRow.size(); j++ ) {
						List myColumn = (List) WTRow.get(j); 
						String myHeader = (String) WTHeader.get(j); 
						System.out.print("["+ myHeader);
						System.out.print("-col(" + j + ")]=:"); 
						System.out.print(myColumn.get(0).toString());
						System.out.print(";");
						System.out.print("[Type]=:");
						System.out.print(myColumn.get(1).toString());
						System.out.print(";");
						WebElement  myElememt = (WebElement) myColumn.get(2);
						System.out.print("Text=:]");
						traceMessage="myElememt.getText() + ";
						Utility.traceLog(trace, true, traceMessage);
					}
					traceMessage="";
					Utility.traceLog(trace, true, traceMessage);
					traceMessage="************************************";
					Utility.traceLog(trace, true, traceMessage);
				}
		}
		
		
		public static void SF_GetWebTableElement(WebDriver driver, List runTestStep) throws Exception {
		
			
			myStep =   (List) runTestStep.get(2);
			myData =  (List) runTestStep.get(3);
			myWEC =   (List) runTestStep.get(4);
			
			/*-----------------------------------------------------------------------------------------------------------
			//Web Element Catalog
			*-----------------------------------------------------------------------------------------------------------*/
			//myLabel = myWEC.get(2).toString();			//Label for button
			//myWEI = myWEC.get(3).toString();				//Web Element ID
			//myWEType = myWEC.get(4).toString();		//WebElementType		
			//myIdentifer = myWEC.get(5).toString();			//identifier

				//Table Column
				//String $myStep_WEI = runTestStep.get(13).toString();
				String $myData_WEI = myData.get(3).toString();
				if ($myData_WEI.contains("$")){
					int i = $myData_WEI.indexOf("$") + 1;
					myWTColumn = Integer.parseInt($myData_WEI.substring(i));
					}
	
				// Table Row
				myValue = myData.get(4).toString();
				//myValue = runTestStep.get(7).toString();
				if (myValue.substring(0, 1).equalsIgnoreCase("&")) {
					myValue = Utility.getStoredValue(myValue,"actual");
				}
				if (myValue.substring(0, 1).equalsIgnoreCase("#")) {
					myValue = myValue.replaceAll("#", "");
					myWTRow = Integer.parseInt(myValue);
				}

				List $WTHeader = (List) myWTElementsList.get(0);
				List $WTRow = (List) myWTElementsList.get(myWTRow);
				List $WTRowColumn = (List) $WTRow.get(myWTColumn);
				
				WebElement  $myElememt = (WebElement) $WTRowColumn.get(2);
				String $WEType = (String) $WTRowColumn.get(1);
				
				traceMessage="";
				String myHeader = (String) $WTHeader.get(myWTColumn); 
				System.out.print("["+ myHeader);
				System.out.print("-col(" + myWTColumn + ")]=:"); 
				System.out.print($myElememt.getText() + ";  ");
				traceMessage="";
				traceMessage="";
				traceMessage="**********************************************";
				WebElements.run(driver, $WEType, $myElememt);
		}


		public static void SF_WebLookup(WebDriver driver ,List runTestStep) throws Exception {
			
				myStep =   (List) runTestStep.get(2);
				myData =  (List) runTestStep.get(3);
				myWEC =   (List) runTestStep.get(4);
				// store the parent window information
				String parentWindow =  driver.getWindowHandle();
				
				//img[@alt='People Lookup (New Window)']
				myWEI = "//img[@alt='" + myWEC.get(3).toString() + " (New Window)']";
				myValue = myData.get(4).toString();
						
				
				WebElements.findElement(driver, "xpath", myWEI, null);
				WebElements.myElement.click();
		
				// Sync up wait
				Utility.testExecutionSleep(stdtime);
				//Utility.testExecutionSleep(4000);

				Set s =  driver.getWindowHandles();
				Iterator ite=s.iterator();
				while(ite.hasNext())
				{
					String childWindow=ite.next().toString();
					if(!childWindow.contains(parentWindow))
					{
						// Switch to child window
						driver.switchTo().window(childWindow);
						// Switch to Search Frame
						driver.switchTo().frame("searchFrame");
						// Sync up wait
						Utility.testExecutionSleep(stdtime);
						driver.findElement(By.id(search)).clear();
						if (!myValue.contains("#")){
							driver.findElement(By.id(search)).sendKeys(myValue);
					}
						driver.findElement(By.name("go")).click();
						Utility.testExecutionSleep(stdtime);
						driver.switchTo().window(childWindow);
						driver.switchTo().frame("resultsFrame");
						
						// find search table section
						WebElement searchResult=driver.findElement(By.xpath("//table[@class='list']/tbody"));
						// extract rows from the table section
						List rows = searchResult.findElements(By.tagName("tr"));
						// extract linked cell data
						List<WebElement> linkedElement = searchResult.findElements(By.tagName("a"));
						// extract header cell data
						List<WebElement> cellsElement = searchResult.findElements(By.tagName("th"));

						// extract all the cell data in the string for testing purpose
						/*String[] linkTexts = new String[cellsElement.size()];
								int x = 0;
								for (WebElement e : linkedElement) {
									linkTexts[x] = e.getText();
									x++;
					    	}*/

						if (rows.size() > 1){
							if (myValue.contains("#")){ //extract the link text by input index
								myValue=myValue.replace("#", "");
								// find the index of cell data in the search result
								if (linkedElement.size()==cellsElement.size()){
									elementIndex = linkedElement.size() - rows.size() + Integer.parseInt(myValue);
								}else{
									elementIndex = Integer.parseInt(myValue)-1;
								}
								linkedElement.get(elementIndex).click();
							}else{
								//extract the link texts of each link element
								for (WebElement e : linkedElement) {
									if (e.getText().contains(myValue)){
										e.click();
										break;
									}
								}
							}
							myRunStatus = "Passed";
						}else{
							System.out.println("No search data is found");
							traceMessage="No search data is found";
							myRunStatus = "Failed";
						}		
						/*
						List<WebElement> linkElements = driver.findElements(By.tagName("a"));
						int rows=linkElements.size();
						String[] linkTexts = new String[linkElements.size()];
						if (rows >= 1){
							if (myValue.contains("#")){ //extract the link text by input index
								myValue=myValue.replace("#", "");
								linkElements.get(Integer.parseInt(myValue)).click();
							}else{
								//extract the link texts of each link element
								for (WebElement e : linkElements) {
									if (e.getText().contains(myValue)){
										e.click();
										break;
									}
								}
							}
						}else{
							System.out.println("No search data is found");
						}*/
						// Sync up wait
						Utility.testExecutionSleep(stdtime);
						driver.switchTo().window(parentWindow);
					}
				}
		}

		public static void zSF_WebLookup(WebDriver driver) throws Exception {
		
				//case "WEBLOOKUP_SF" : {
				//Store the current window handle
				String parentHandle =  driver.getWindowHandle();
				myElement = (new WebDriverWait(driver, 30)).until(new ExpectedCondition<WebElement>(){@Override
					public WebElement apply(WebDriver d) {
					return d.findElement(By.xpath(myWEI));
				}});			
				myElement.click();
				Utility.testExecutionSleep(2000);
				Set s =  driver.getWindowHandles();
				Iterator ite=s.iterator();
				while(ite.hasNext()) 	{
					String popupHandle=ite.next().toString();
					if(!popupHandle.contains(parentHandle))	{
						driver.switchTo().window(popupHandle);
						driver.switchTo().frame("resultsFrame");
						WebElements.findElement(driver, "linkText", myValue, null);
						WebElements.run(driver, "WEBLINK", myElement);
						driver.switchTo().window(parentHandle);
						break;
						//myElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.linkText(myValue)));
						//myElement.click();
						//driver.switchTo().window(parentHandle);
						//break;
					}
				}
		}

		
  		public static void SF_ObjectLink(WebDriver driver) throws Exception {

  				//case "OBJECTLINK" : {
  				myElement.click();
  			
  		}
  		
  		public static void SF_WebTableLinks(WebDriver driver) throws Exception {
		
  				myWEI = driver.getCurrentUrl().substring(driver.getCurrentUrl().length()-15)+myWEI;									
  				WebElements.findElement(driver, "id", myWEI, null);
  			
  				List rows=myElement.findElements(By.tagName("tr"));
  				traceMessage="Numer of rows in this table: "+rows.size();
  				Iterator i=rows.iterator();
  				while(i.hasNext())
  				{   	
  					//traceMessage="(WebElement)i.next()).getText();
  				}
				List<WebElement> linkElements = myElement.findElements(By.tagName("a"));
  				String[] linkTexts = new String[linkElements.size()];
  				int x = 0;
  				//extract the link texts of each link element
  				for (WebElement e : linkElements) {
  					linkTexts[x] = e.getText();
  					x++;
		    	}
  				//test each link
  				for (String t : linkTexts) {
  					if(!t.toUpperCase().equals("EDIT")){
  						driver.findElement(By.linkText(t)).click();
  						Utility.testExecutionSleep(stdtime);
  						if (driver.getTitle().contains(t) ) {
  							traceMessage=driver.getTitle();
		                } 
  						else {
  							traceMessage="Link didn't work";
  						}
  						driver.navigate().back();
  						Utility.testExecutionSleep(stdtime);
  					}   
  				}
  		}	

  		
  		public static void SF_UpLoadDoc_S(WebDriver driver) throws Exception {
  			List<WebElement> afterPopup;
  			myElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.name(myWEI)));
  			if (myElement.isEnabled()){
  				// find number of frame before popup
  				List<WebElement> beforePopup = driver.findElements(By.tagName("iframe"));
  				System.out.println(beforePopup.size());
				// IE requires to hit ENTER and then click on a object
				Utility.forceClick(myElement, WebDrivers.browser);  				
  				//myElement.click();
  				Utility.testExecutionSleep(stdtime);

  				int timecount = 1;
  				do {
  					afterPopup = driver.findElements(By.tagName("iframe"));
  					System.out.println(afterPopup.size());
  					Utility.testExecutionSleep(stdtime);
  					if (afterPopup.size()>beforePopup.size()){
  						// Switch to Upload Document frame
  						driver.switchTo().frame(driver.findElements(By.tagName("iframe")).get(afterPopup.size()-1));
  						break;
  					}
  					timecount++;
  					if (timecount > 30) {
  						break;
  					}
  				} while (beforePopup.size()== afterPopup.size());			
  			}
  		}

  		public static void SF_UpLoadDoc_E(WebDriver driver) throws Exception {

   			myElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.name(myWEI)));
  			//myElement.click();
   			Utility.forceClick(myElement, WebDrivers.browser);
  			Utility.testExecutionSleep(stdtime);
  			List<WebElement> countiframe = driver.findElements(By.tagName("iframe"));
  			System.out.println(countiframe.size());
  			// Switch to main record
  			driver.switchTo().defaultContent();		
  			myRunStatus = "Passed";
  		}
		
		
		public static void SF_SelectBox(WebDriver driver) throws Exception {
	
				Utility.testExecutionSleep(stdtime);
				if (myValue.toUpperCase().equals("ALL")) {
					myWEI = "allBox_" + myWEI; 
				}
				else {
					myWEI = "ids_" + myWEI + myValue.replace("#", ""); 
				}	
				//WebElements.findElement(driver, "id", myWEI, null);
				myElement=WebElements.findElement(driver, "id", myWEI, null);
				WebElements.run(driver, "WEBCHECKEDBOX", myElement);
			}	


		public static void SF_LookupLink(WebDriver driver) throws Exception {

				myWEI = "lookup" + myValue + myWEI;
				WebElements.findElement(driver, "id", myWEI, null);
				WebElements.run(driver, "WEBLINK", myElement);
		}	
		
		
		public static void SF_WebButton(WebDriver driver) throws Exception {
			
				myWEI = "'" + myWEI.replaceAll("&", "") + "'";
				myLabel = "'"+ myLabel + "'";
				WebElement myButton = driver.findElement(By.xpath("//input[@name=" + myWEI + " and @title=" + myLabel +"]"));
				myButton.click();
		}
		
		public static void SF_URL(WebDriver driver) throws Exception {
			
				String myURL = "https://cs9.salesforce.com/"+ myValue;
				myRun = true;
				driver.get(myURL);
		}
		
		public static void SF_getSaveErrorMessge(WebDriver driver) throws Exception, IOException {

				//Capture error message after save
				String myPageError = "***** [Error Message - Page]=:" + driver.findElement(By.id("errorDiv_ep")).getText();
				if (myPageError == "") { 
					TestResultLog.writeStepLog("***** Error after save *****");
					traceMessage="";
					traceMessage="[Page Error Message]=:" + myPageError;
					myRunStatus = "Failed";
					WebElements.updateStepLog(driver, myPageError, myRunStatus);
					TestResultLog.writeStepLog(WebElements.stepLog);

				//Error Details
					int ErrorSize = driver.findElements(By.className("errorMsg")).size();
					for (int i = 0; i<ErrorSize; i++) {
					WebElement myerror = driver.findElements(By.className("errorMsg")).get(i);
					String myErrorMsg = "***** [Error Message - detail]=:" + myerror.getText();
					traceMessage="";
					traceMessage="[Error Message#" + i +" ]=:" + myErrorMsg;
					traceMessage="";
					myRunStatus = "Failed";
					WebElements.updateStepLog(driver, myPageError, myRunStatus);
					TestResultLog.writeStepLog(WebElements.stepLog);
				}
			}
		}	

		
		public static void SF_MutliSelectPickListRow(WebDriver driver) throws Exception, IOException {

				myElement_unSelected = driver.findElement(By.id(myWEI + "_unselected"));
				myElement_Selected = driver.findElement(By.id(myWEI + "_selected"));
				myPicklist = myValue;
				myPicklistElementID =myWEI;
		}

		
		public static void SF_MutliSelectPickListCell(WebDriver driver) throws Exception, IOException {

				if (myValue.equalsIgnoreCase("ADD")) {
					if (myPicklist.substring(0,1).equalsIgnoreCase("#")) {
						int myPicklistIndex = Integer.parseInt(myPicklist.replaceFirst("#", ""));
						new Select(myElement_unSelected).selectByIndex(myPicklistIndex);
					}
					else {
						new Select(myElement_unSelected).selectByVisibleText(myPicklist);
					}
					WebElement add = driver.findElement(By.id(myPicklistElementID+"_right_arrow"));
					add.click();
				}
				else if (myValue.equalsIgnoreCase("REMOVE")) {
					if (myPicklist.substring(0).equalsIgnoreCase("'#")) {
						int myPicklistIndex = Integer.parseInt(myPicklist.replaceFirst("#", ""));
						new Select(myElement_Selected).selectByIndex(myPicklistIndex);
					}
					else {
						new Select(myElement_Selected).selectByVisibleText(myPicklist);
					}
					WebElement remove = driver.findElement(By.id( myPicklistElementID+"_left_arrow"));
					remove.click();
				}
		}
}



