package main.java.qa;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.rhi.qa.*;
import com.rhi.utilities.WebDrivers;
import com.rhi.utilities.WebElements;

public class Testing {

		public static String $myLabel, $myWEI, $WEIType,$myIdentifer, $myValue;
		public static String $myTestString ;
		public static List JSAT = new ArrayList();
		
		
		public static void main(String[] args) throws Exception {

			
//		WebElements.myValue("abc");
//		WebElements.myValue("%abc%");
	//	WebElements.myValue("%abc%%");
	//	WebElements.myValue("%abc%%%xyz");
	//		WebElements.myValue("&xyz&");
//			WebElements.myValue("#xyz#");
//			WebElements.myValue("#xyz#%");
//			WebElements.myValue("#xyz#%%");
			WebElements.myValue("#xyz#%%%abc");
//			WebElements.myValue("#xyz#&");
//			WebElements.myValue("#xyz##&");
			//WebElements.myValue("#&abc&#");
			WebElements.myValue("#&abc&##");
							
							
					
		
		
			
//			TestCheckpoint.VerifyInput("WebEdit","xyx", "zya", 0);
//			TestCheckpoint.VerifyInput("WebEdit","xyz", "xyz",0);
			//String str1 = "'123";
		//	WebElement myElement = null;
			
			
	//		TestCheckpoint.VerifyElementExist(myElement, 0);
	//		TestCheckpoint.VerifyElementExist(str2, 0);
						
			
			String url = "";
			//url = "http://www.google.com";
			//url = "http://www.amazon.com";
			url = "http://rhi.com";
			
			//Browser
			//WebDrivers.getChromeDriver();
			WebDrivers.getFirefoxDriver();
			//WebDrivers.getIEDriver("IE 64-bit");
			//WebDrivers.getIEDriver("IE 32-bit");
		
			WebDriver myDriver = WebDrivers.driver;
			myDriver.get(url);
		
			
			/*===============================================================================
			/JSAT
			/ $myLabel, $myWEI, $WEIType,$myIdentifer, $myValue; frame
			/================================================================================*/

			//Verify Assertion/Checkpoint
			//rhi.com
			//input[name="keywords"]
			//Input
			String css = "input[name=" + "\"" +  "keywords" +"\"" +"]";
			setupJSAT("keyword", css, "WebEdit", "css", "Advertising Programs", "");
			WebElements.runStep(myDriver, JSAT);
			
			//CheckPoint - compare actual value vs expected value(input Value)
			setupJSAT("keyword", css, "WebEdit", "css", "#Advertising Programs#", "");
			WebElements.runStep(myDriver, JSAT);

			//???????
			//CheckPoint - compare actual value(on page) vs expected value(input Value)
			setupJSAT("keyword", css, "WebEdit", "css", "#Advertising Programs#", "");
			WebElements.runStep(myDriver, JSAT);

			
			//Store sharedoutputvalue - element value   &&[keyword]  == &&output
			setupJSAT("keyword", css, "WebEdit", "css", "&&output", "");
			WebElements.runStep(myDriver, JSAT);
	
			//Store sharedInputvalue - element value   &&[keyword]  == &&output
			setupJSAT("keyword", css, "WebEdit", "css", "&&Advertising Programs&&", "");
			WebElements.runStep(myDriver, JSAT);
			
			//Chnage Store sharedInputvalue - change element value   &&[keyword]  == &&output
			setupJSAT("keyword", css, "WebEdit", "css", "&&!!!Advertising Programs&&", "");
			WebElements.runStep(myDriver, JSAT);

			//get StoredValue
			String $css = "&"+ css + "&";
			setupJSAT("keyword", css, "WebEdit", "css", $css, "");
			WebElements.runStep(myDriver, JSAT);

			setupJSAT("location", "location", "WebEdit", "name", $css, "");
			WebElements.runStep(myDriver, JSAT);
			
			setupJSAT("location", "location", "WebEdit", "name", "&&output", "");
			WebElements.runStep(myDriver, JSAT);

			setupJSAT("location", "location", "WebEdit", "name", "&&#Advertising Programs#", "");
			WebElements.runStep(myDriver, JSAT);

			
			//			WebElement searchBox = myDriver.findElement(By.name("q"));
			//			searchBox.sendKeys("webdriver");
			//System.out.println(myDriver.getTitle());
			//WebElements.runStep(myDriver, JSAT);
			//setupJSAT("Searchbox", "q", "WebEdit", "name", "&q&", "");
			//WebElements.runStep(myDriver, JSAT);
			//setupJSAT("Searchbox", "q", "WebEdit", "name", "#q#", "");
			//WebElements.runStep(myDriver, JSAT);
					

	}

		public static void setupJSAT(String myLabel, String myWEI, String myWEIType, String myIdentifer ,String myValue, String myFrame) {
		
				JSAT.clear();;
				List $myStepNbr = new ArrayList();
				List $myStep = new ArrayList();
				List $myData = new ArrayList();
				List $myWEC = new ArrayList();

		
				$myStep.add("$0");
				$myStep.add("$1");
			
				$myWEC.add("$0");
				$myWEC.add("$1");
				$myWEC.add(myLabel);
				$myWEC.add(myWEI);
				$myWEC.add(myWEIType);
				$myWEC.add(myIdentifer);
			
				$myData.add("$0");
				$myData.add("$1");
				$myData.add("$2");
				$myData.add("$3");
				$myData.add(myValue);
				$myData.add(myFrame);
				
				JSAT.add(1);
				JSAT.add("");
				JSAT.add( $myStep);
				JSAT.add( $myData);
				JSAT.add($myWEC);
				
				
				//'''Collections.sort(JSAT);

				boolean find = $myData.contains("$3");
				
				
				System.out.println(JSAT);
			
		}
		
}
