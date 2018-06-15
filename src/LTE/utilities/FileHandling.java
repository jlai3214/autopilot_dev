package LTE.utilities;

import java.io.*;


public class FileHandling	{
		
		public static boolean success;
		public static String $directories;
		public static String traceMessage;
		public static Boolean trace =false;
		
		//	public void main () {
			public static void main(String[] args) throws Exception {	
			String $directories="c:/temp/eclipse3/";
			createDir($directories);
			getNetworkID();
		}
		
		public static void createDir(String directories) {
			try{
				// Create multiple directories
				success = (new File(directories)).mkdirs();
				if (success) {
					traceMessage="Directories: "		+ directories + " created";
				}
				else {
					traceMessage="Directories: "		+ directories + " already existed";
				}
			}
			catch (Exception e){//Catch exception if any
					System.err.println("Error: " + e.getMessage());
			}
		}
	
		
		public static void getNetworkID() {
				String $networkID = System.getProperty("user.name");
				System.out.println($networkID);
				return;
		}
}