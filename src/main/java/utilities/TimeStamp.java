package LTE.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {




    public static void main(String[] args) throws Exception {
        
    	//SimpleDateFormat date = new SimpleDateFormat("yyyy_M_dd_hh_mm_ss");
    	
		SimpleDateFormat date = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    		String $runTimeStart = date.format(new Date());
		System.out.println("[TimeStamp]=:" + $runTimeStart);	
    	
    	// Make a new Date object. It will be initialized to the current time.
        DateFormat dfm = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        //Date d = dfm.parse("2011-09-07 00:00:00");

        Date d = new Date();

        // See what toString() returns
        System.out.println(" 1. " + d.toString());

        // Next, try the default DateFormat
        System.out.println(" 2. " + DateFormat.getInstance().format(d));

        // And the default time and date-time DateFormats
        System.out.println(" 3. " + DateFormat.getTimeInstance().format(d));
        System.out.println(" 4. " +
            DateFormat.getDateTimeInstance().format(d));

        // Next, try the short, medium and long variants of the
        // default time format
        System.out.println(" 5. " +
            DateFormat.getTimeInstance(DateFormat.SHORT).format(d));
        System.out.println(" 6. " +
            DateFormat.getTimeInstance(DateFormat.MEDIUM).format(d));
        System.out.println(" 7. " +
            DateFormat.getTimeInstance(DateFormat.LONG).format(d));

        // For the default date-time format, the length of both the
        // date and time elements can be specified. Here are some examples:
        System.out.println(" 8. " + DateFormat.getDateTimeInstance(
            DateFormat.SHORT, DateFormat.SHORT).format(d));
        System.out.println(" 9. " + DateFormat.getDateTimeInstance(
            DateFormat.MEDIUM, DateFormat.SHORT).format(d));
        System.out.println("10. " + DateFormat.getDateTimeInstance(
            DateFormat.LONG, DateFormat.LONG).format(d));
    }
}