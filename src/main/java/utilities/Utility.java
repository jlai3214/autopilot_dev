package LTE.utilities;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertFalse;

public class Utility {

		public static List myStoredVar = new ArrayList();
		public static String traceMessage;
		public static Boolean trace =false;
		
		@Test
		public void abc() throws Exception {

			
			addStoredValue("&abc&", "&&aaaa");	
			System.out.println(myStoredVar);
	//		addStoredValue("&abc&","int", "&&10");	
			System.out.println(myStoredVar);
			
			//addStoredVar("&abc",1234);	
			//addStoredVar("&abc",true);	
			//getStoredValue("&abc");
		}
		
  public static void forceClick(WebElement e, String browser) {
            	
        		if (browser.equalsIgnoreCase("Internet Explorer")) {
        			e.sendKeys(Keys.ENTER);
        		}
        		else {
        			e.click();
        		}
        }

        
		public static boolean waitForPageFullyLoaded(WebDriver driver, int timeoutMs) {
		
			int previous;
			int current = 0;
			int timeSliceMs = 1000;
			do {
				previous = current;
				//Thread.sleep(timeSliceMs);
				Utility.testExecutionSleep(timeSliceMs);
				timeoutMs -= timeSliceMs;
				current = driver.findElements(By.xpath("//*")).size();
			} while(current > previous && timeoutMs > 0);
			if(timeoutMs > 0) {
				return true;
			}
			return false;
		}
		
			public static void setTimeStamp() {

					SimpleDateFormat date = new SimpleDateFormat("yyyy_M_d_hh_mm_ss");
					String $timeStamp = date.format(new Date());
					traceMessage="[TimeStam]=:" + $timeStamp;
			}

			
			public static void addStoredValue(String Name, String Value ) {
			
				//	Utility.addStoredValue("&"+myWEI, "actual",  myElement.getAttribute("value"));
				//&&value&&
				//Value = Value.replaceFirst("&&", "");
				List $sharedVar = new ArrayList();
				Boolean $Exist = false;
				//Check datatype
				String $dataType = "String";
				//	if (Value.matches(regex))
				System.out.println("Before" + myStoredVar);
				if (myStoredVar.size() != 0) {
					for ( int i = 1 ;  i <  myStoredVar.size(); i++) {
						List myVar = (List) myStoredVar.get(i);
						if (myVar.get(1).toString().equalsIgnoreCase("&"+ Name + "&")) {
								//&& myVar.get(2).toString().equalsIgnoreCase($dataType)) {;
						$Exist = true;
							myVar.set(2, Value);
							myVar.set(2, Value+"??????");
							
							break;
						}
					}
				}
				else {
					myStoredVar.add("new");
				}
				
				if ($Exist == false) {
					$sharedVar.add(myStoredVar.size());
					$sharedVar.add("&" + Name + "&");
					$sharedVar.add(Value);
					$sharedVar.add("Actual");
					$sharedVar.add($dataType);
					myStoredVar.add($sharedVar);
				}
				System.out.println("After" + myStoredVar);
		}
  
	
		public static String getStoredValue(String variable) {
			
				String myStoredValue = null;
  				for ( int i = 1 ;  i <  myStoredVar.size(); i++) {
  					List myVar = (List) myStoredVar.get(i);
  					if (myVar.get(1).toString().equalsIgnoreCase(variable)) { 
  						myStoredValue = (String) myVar.get(2);
  						break;
  					}
  				}
  				return myStoredValue;
  				
		} 

	/*	
		public static int getStringValue(int Name) {
			
			String myStoredValue = null;
				for ( int i = 1 ;  i <  myStoredVar.size(); i++) {
					List myVar = (List) myStoredVar.get(i);
					if (myVar.get(1).toString().equalsIgnoreCase(Name)) { 
						myStoredValue = myVar.get(3).toString();
		  			break;
					}									
				}
				return myStoredValue;
	} 

		
		public static int getIntValue(String StoredVariable) {
			
			int myStoredValue = 0;
				for ( int i = 1 ;  i <  myStoredVar.size(); i++) {
					List myVar = (List) myStoredVar.get(i);
					if (myVar.get(1).toString().equalsIgnoreCase(Name)) { 
						myStoredValue = Integer.parseInt(myVar.get(3).toString());
		  			break;
					}									
				}
				return myStoredValue;
		} 

		
		public static Boolean getBooleanValue(String StoredVariable) {
			
			boolean myStoredValue = false;
				for ( int i = 1 ;  i <  myStoredVar.size(); i++) {
					List myVar = (List) myStoredVar.get(i);
					if (myVar.get(1).toString().equalsIgnoreCase(Name)) { 
						myStoredValue = Boolean.parseBoolean(myVar.get(3).toString());
		  			break;
					}									
				}
				return myStoredValue;
		} 

		
		public static int getStoredValue1(String Name) {
			
			//'  				String myStoredValue = null;
			  				for ( int i = 1 ;  i <  myStoredVar.size(); i++) {
			  					List myVar = (List) myStoredVar.get(i);
			  					if (myVar.get(1).toString().equalsIgnoreCase(Name)) { 
			  						//myStoredValue = myVar.get(3).toString();
			  						switch (myVar.get(2).toString()) {
			  						case "int": {
			  							int myStoredValue = Integer.parseInt((String) myVar.get(3));
			  						}
			  						case "Boolean": {
			  							Boolean myStoredValue = Boolean.parseBoolean(myVar.get(3).toString().toString());
			  						}
			  				
			  						}
			  						
			  						break;
			  					}									
			  				}
			  				return myStoredValue;
					} 
				
		*/		
		
		public static String zgetStoredValue(String varName, String varType) {
			
  				String myStoredValue = null;
  				for ( int i = 1 ;  i <  myStoredVar.size(); i++) {
  					List myVar = (List) myStoredVar.get(i);
  					if (myVar.get(1).toString().equalsIgnoreCase(varName) 
  						&& myVar.get(2).toString().equalsIgnoreCase(varType)) {
  						traceMessage = "z[Stored Variable]=:"+ myVar.get(1);
  						traceMessage="; [Stored Value]=:"+ myVar.get(4);
  						myStoredValue = myVar.get(4).toString();
  						break;
  					}									
  				}
  				return myStoredValue;
		} 
		
		public static void traceLog(Boolean trace, Boolean NewLine, String traceMessage) {

			String $NewLine = NewLine.toString();
			if (trace == true ) {
				switch ($NewLine.toUpperCase()) {
					case "TRUE": {
						System.out.println( traceMessage);
						break;
					}
					case "FALSE": {
						System.out.print(traceMessage);
						break;
					}
				}		
			}
		}	
		
		// Accept the popup confirmation
		public static void popupAlert(WebDriver driver){
				Alert javascriptAlert = driver.switchTo().alert();
				javascriptAlert.accept();
		}

			// Sleep the test execution for x millisecond to control the speed
		public static void testExecutionSleep(int x){
				try {
					Thread.sleep(x);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
			public static String getListToString(List myList){
		
				String listString = "";
				for (int s=1; s < myList.size() ;s++) {
					listString  = listString + "; "+ myList.get(s) ;
				}
				//System.out.println(listString);
				return listString;
			}

	
}
