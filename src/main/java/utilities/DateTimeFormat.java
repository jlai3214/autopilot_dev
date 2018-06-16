package LTE.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;


public class DateTimeFormat {

		
    	public static void main(String[] args) throws Exception {
        
    	}	
		
    	public static String getDateTimeFormat(Date dateTime, String Format) throws InterruptedException {
			
				//'String format = "yyyy_MM_dd_HH_mm_ss_SSS";
				SimpleDateFormat timeStampFormat = new SimpleDateFormat(Format);
				return  timeStampFormat.format(dateTime);
		}
		
    	
		public static String getDateTimeDiff(Date TimeIn, Date TimeOut) throws InterruptedException {
				
				// TODO Auto-generated method stub

				String myTimeDiff = "";
				String $TimeIn = getDateTimeFormat(TimeIn,  "yyyy_MM_dd_HH_mm_ss SSS");
				String $TimeOut = getDateTimeFormat(TimeOut,  "yyyy_MM_dd_HH_mm_ss SSS");
			
				System.out.println($TimeIn);
				System.out.println($TimeOut);
	
				String $TimeDesc[] = {"","ms","s","m","h","d"};
				long diff = TimeOut.getTime() - TimeIn.getTime();
				long $TimeDiff[] = {0, diff % 1000, diff/1000 %60, diff/(60*1000) %60, diff/(60*60*1000) %24, diff/(24*60*60*1000) };
				String $TimeStampDiff = "";
		
				for (int i=1; i<=5; i++) {
					if ($TimeDiff[i] > 0) {
						myTimeDiff = $TimeDiff[i] + ""  + $TimeDesc[i] + " " +myTimeDiff;
					}		
				}
				System.out.println(myTimeDiff);
				return myTimeDiff;
		}
}



