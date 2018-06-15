package LTE.qa;

import org.junit.Test;

public class TestDatabase {

	// 1) Select Query
	// 2) DB_Connection
	// 3 Execute Selected Query
	// 4) Output

	@Test
	public void chekPoint() {
		
		String $checkPointStr = "#0;#Find_Web_Text;#Inform us about your compensation";
		
		if ($checkPointStr.contains(";#")) {
		    // Split it.
		} 
		else {
		    throw new IllegalArgumentException("String " + $checkPointStr + " does not contain -");
		}
	
	
		String[] $checkPoint	 = $checkPointStr.split(";#");
		int j = $checkPoint.length;
		for (int i=0; i <$checkPoint.length; i++) {
			System.out.println($checkPoint[i]);
		}
		
		if ($checkPoint.length != 3) 
		     throw new IllegalArgumentException("String not in correct format");
		else {
		}
		
		selectVerifyMethod("Test");
		
		
	}

	public void selectVerifyMethod(String VerifyMethod) {
		
		switch (VerifyMethod.toUpperCase()) {
			case "TEST": { 
				System.out.println("[Verify method]=:" + VerifyMethod);
				
				
				
			}
		
		}
	
		
		
	}
	
}



