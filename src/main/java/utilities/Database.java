package main.java.utilities;

import java.sql.*;

import org.openqa.selenium.Alert;



public class Database {

		public Alert alert;
		static String $Env;
		static String $url,$database, $driver, $hostName, $port,  $uid,  $pwd,  $dbConStr; 
		static String myQuery;
		public static String [][] $dbDataSet ;
		public static Statement st;  
		public static Connection db2Conn;
		public static ResultSet resultSet;
		

			public static void main() {
				$database = "webete";
				$hostName = "yellow";
				$port = "9110";
				$uid = "web";
				$pwd = "webest";
				$url = "jdbc:db2://" + $hostName + ":" + $port + "/" + $database;
				myQuery ="SELECT A.* FROM CR.CUST A WHERE A.USER_ID LIKE 'XETE%'";
        
				runQuery($database, $hostName, $port, $uid, $pwd, myQuery,1);
				writeToSharedVariable("&CUST_ID",2);
				//System.out.println(Utility.getSharedVar("&CUST_ID"));
				
				
				runQuery($database, $hostName, $port, $uid, $pwd, myQuery,1);
				writeToSharedVariable("&USER_ID",2);
				//System.out.println(Utility.getSharedVar("&USER_ID"));
				
		}


		public static void db2Connect(String database, String hostName, String port, String uid, String password) { 
			
			/*=============================================================================
			 * 	$database = "webete";
			 *	$hostName = "yellow";
			 *	$port = "9110";
			 *	$uid = "web";
			 *	$pwd = "webest";
			 *	$url = "jdbc:db2://" + $hostName + ":" + $port + "/" + $database;
			 *	myQuery ="SELECT A.* FROM CR.CUST A WHERE A.USER_ID LIKE 'XETE%'";
			 *========================================================================*/        
			// Create connection
				try {
					Class.forName("com.ibm.db2.jcc.DB2Driver");
					$url = "jdbc:db2://" + $hostName + ":" + $port + "/" + $database;
					db2Conn = DriverManager.getConnection($url, $uid, $pwd); 
					st = db2Conn.createStatement();
				}
				catch (ClassNotFoundException cnfe)
				{
					cnfe.printStackTrace();
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				} finally {
				}
							
		}			     
		
		
		public static void runQuery(String database, String hostName, String port, String uid, String password, String myQuery, int OutputMode) { 
			
				// Create connection
				db2Connect(database, hostName, port, uid, password);
				try {
					// execute the query
					resultSet = st.executeQuery(myQuery); 
					// cycle through the resulSet and display dataSet
					ResultSetMetaData mdResultSet = resultSet.getMetaData();
            		// rowCount, colCount, int $dbDataSet
					int $colCount =  mdResultSet.getColumnCount();
					int $rowCount = 0 ;
					while (resultSet.next()) {
							$rowCount++;
					}
					$dbDataSet = new String [$rowCount+1][$colCount+1];
					// Re-execute the query
					resultSet = st.executeQuery(myQuery); 
					// cycle through the resulSet and display dataSet
					mdResultSet = resultSet.getMetaData();
            
					//Update to $dbDataSet
					//get ColumnName
					//-------------------------------------------------------------------------------------------------------------------------------
					System.out.println("==================================================================");				;
					System.out.println("**** Field Column Name  ***************");				;
					for (int i = 1 ; i <=  $colCount;  i++) {
						$dbDataSet[0][i] = mdResultSet.getColumnName(i);
						System.out.print("[" + i + "]=:" + $dbDataSet[0][i] + "; ");
					}
					System.out.println("");
					System.out.println("==================================================================");				;
				
					//Write to $dbDataSet
					//-------------------------------------------------------------------------------------------------------------------------------
					int i = 1;
					while (resultSet.next()) {
						System.out.println("=======================================================================");				;
						System.out.println("record " + i);
						System.out.println("=======================================================================");				;
						for (int j = 1; j <=$colCount; j ++) {
							$dbDataSet[i][j] = resultSet.getString($dbDataSet[0][j]);
							System.out.print("["+  $dbDataSet[0][j] + "]=:" +$dbDataSet[i][j] + "; ");
							System.out.println("");
						}
						i++;
					}	
					// clean up resources
					resultSet.close();
					st.close();
					db2Conn.close();
				}
			
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
		 	}	
		
		public static void writeToSharedVariable(String fieldName, int row) { 
		
			
			String $fieldName = null;
			int j = 1;
			for (j = 1; j <= $dbDataSet[0].length; j++) {
				System.out.println($dbDataSet[0][j]);
				if (("&" + $dbDataSet[0][j]).equalsIgnoreCase(fieldName)) {
					$fieldName = fieldName;
					break;
				}
			}	
			String $fieldValue = $dbDataSet[row][j];
			//Utility.addSharedVar($fieldName, "STRING", $fieldValue);
			System.out.println("=======================================================================");				;
			System.out.println("*****  Shared Variables  *****");				;
			//System.out.println(Utility.getSharedVar($fieldName));
			//System.out.println("[Variable]=:" + Utility.$sharedVariable  +  "; [Type]=" + Utility.$sharedVarType + "; [Value]=" + Utility.$sharedVarValue ); 
			System.out.println("=======================================================================");				;
			
		}


	
}

			
