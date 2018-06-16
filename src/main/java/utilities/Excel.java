package main.java.utilities;
  
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;  
import org.apache.poi.ss.usermodel.Hyperlink;
import org.junit.Test;

import com.rhi.qa.TestResultLog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Excel {

		public static Workbook myWorkbook;
		public static Sheet mySheet;
		public static Row myRow;
		
		public static String WorkbookName;
		public static String WorkbookPath;
		
		public static String traceMessage;
		public static Boolean trace =false;
				
		//public static List Excel_DataSet = new ArrayList();
		public static List Excel_DataSet = new ArrayList();
		
		@Test
		public void demo() throws Exception {
	
				//Example
				String $Select;
				$Select = "Cell"; 	//set Cell value
				//$Select = "Range";	//set Range value
				
				switch($Select) {
				
					case "Cell" : {
						TestResultLog.writeMyTestSet();
					String $folder = "C:\\TEMP\\";
					String $sheet = "Main";
					List myDataSet = new ArrayList();
					/* ------------------------------------------------------------------------------------------------------------------------------------------------
					 * Update specific cell and save to current workbook (1) or new workbook
					 *----------------------------------------------------------------------------------------------------------------------------------------------*/
					traceMessage="=============================================================================";
					traceMessage="// Update specfic cell, Cell(B2) =  true";
					traceMessage="=============================================================================";
					openWorkbook("Test one.xlsx", $folder);
					Boolean True = null;
					readToMyDataSet(myWorkbook, $sheet,  True ,True);
					
					myDataSet = Excel_DataSet;
			
					//Update and saveAs to other workbook
					//String myHyperlink = "href;file;Failed;C:/myGalaxy/PeopleSoft/Test Cases/TC-TCAST Demo v2.xlsm";
					String $address1 = "C:\\myGalaxy\\PeopleSoft\\Test Cases\\TC-TCAST Demo v2.xlsm";
					String $address2 = "\\\\na\\corpshared\\apps\\quality assurance\\selenium\\automation\\Galaxy\\configuration\\WEC_PeopleSoft.xlsm";
					
					System.out.println($address1);
					System.out.println($address2);
					
	//				String myHyperlink1 = "href;file;Failed;" + $address1.replace("\\","/");
	//				String myHyperlink2 = "href;file;Passed;" + $address2.replace("\\","/");

					String myHyperlink1 = "href;file;Failed;" + $address1;
					String myHyperlink2 = "href;file;Passed;" + $address2;

					System.out.println(myHyperlink1);
					System.out.println(myHyperlink2);

					
					setCellHyperlink($sheet,3, 3, myHyperlink1);
					setCellHyperlink($sheet,4, 3, myHyperlink2);
					saveWorkbook();
					break;
				}			case "Range" : {
					/* ------------------------------------------------------------------------------------------------------------------------------------------------
					* Update range  cell and save to current workbook (1) or new workbook
					*----------------------------------------------------------------------------------------------------------------------------------------------*/
						String $folder = "C:\\TEMP\\";
						String $workbook = "CS_Companies.xlsx";
						String $sheet = "Main";
						List myDataSet = new ArrayList();
			
						openWorkbook( $workbook, $folder);
						readToMyDataSet(myWorkbook, $sheet, true);
				
						List  $RowSet = new ArrayList();
						// make change
						for (int  i = 1; i <  4;  i++) {
							$RowSet.add(i);
							$RowSet.add("abc");
							myDataSet.add($RowSet);
						}
						System.out.println($RowSet);
						System.out.println(myDataSet);
						setRangeValue($sheet,2, 2, myDataSet);
						//saveWorkbook();
						saveWorkbook("SetRangeValue.xlsx","c:\\temp\\");
						
					}
				}
		}
		
		 /*=================================================================================================
		 * Open workbook, Excel 2017 and Excel 1010
		 * ================================================================================================*/
		public static void openWorkbook(String fileName, String filePath) throws Exception {

				WorkbookName = fileName;
				WorkbookPath = filePath;
				InputStream $Excel = null;
				try {
					String $Workbook = WorkbookPath   + WorkbookName;
					$Excel = new FileInputStream($Workbook);
					myWorkbook = WorkbookFactory.create($Excel);
				}
				catch (IOException e) {
					e.printStackTrace();
				} 
				finally {
					if ($Excel != null)
						$Excel.close();
				}
		}
			

		 /*=================================================================================================
		 * Read Excel Sheet data to List myDataSet, need to call OpenWorkbook first
		 * ================================================================================================*/
		public static void readToMyDataSet(Workbook workbook,  String sheet, Boolean header) throws Exception {
			
				int $sheetIndex =   myWorkbook.getSheetIndex(sheet);
				mySheet = myWorkbook.getSheetAt($sheetIndex);
			
				int r = -1;
				int $rowNum =0;
			
				// Store Header into index(0) and start data from index(1)
				if (header = false) {
					Excel_DataSet.add(null);
				}
				//Header(Excel Row#1) start at Row(0)
				Row myHeaderRow = (Row) mySheet.getRow(0);
				int $TotalCol = myHeaderRow.getLastCellNum();
				int myLastRow = mySheet.getLastRowNum();
				for (int i = 0 ; i <=myLastRow; i++) {
					List $data = new ArrayList();
					Row $myRow = (Row) mySheet.getRow(i);
					int $myColIndex = $myRow.getLastCellNum(); 
					if($TotalCol > $myColIndex) {
						$myColIndex = $TotalCol;
					}
					$data.add(i+1);
					for (int j =0; j <$myColIndex; j++) {
						if ($myRow.getCell(j) == null ||	$myRow.getCell(j).toString().trim().isEmpty()) {
							$myRow.createCell(j).setCellValue("");
						}
						$data.add($myRow.getCell(j));
					}
					Excel_DataSet.add($data);
				}
		}
	
		 /*========================================================================================
		* SetCellValue - Value = String
		* =========================================================================================*/
		public static void setCellValue(String sheet, int rowNum, int colNum, String  value) throws Exception {
	
				int $sheetIndex =   myWorkbook.getSheetIndex(sheet);
				mySheet = myWorkbook.getSheetAt($sheetIndex);
					
				Row myRow= mySheet.getRow(rowNum-1);
				Cell myCell; 
				if (!value.equalsIgnoreCase("na")) {
					if (myRow != null) {
						myCell = myRow.getCell(colNum-1);
						if (myCell == null) {    
							myCell = myRow.createCell(colNum-1);    
						}
						myCell.setCellType(myCell.CELL_TYPE_STRING);    
						myCell.setCellValue(value);		
					}
					else {
						myRow = mySheet.createRow(rowNum-1);
						myCell = myRow.createCell(colNum-1);    
						myCell.setCellType(myCell.CELL_TYPE_STRING);    
						myCell.setCellValue(value);
					}
				}
		}
		
		/*====================================================================================================
		* SetCellValue - value = numeric 
		* ===================================================================================================*/
		public static void setCellValue(String sheet, int  rowNum, int colNum,  int  value) throws Exception {	

				int $sheetIndex =   myWorkbook.getSheetIndex(sheet);
				mySheet = myWorkbook.getSheetAt($sheetIndex);
				//System.out.println(mySheet.getSheetName());

				Row myRow= mySheet.getRow(rowNum-1);
				Cell myCell; 
				if (myRow != null) {
					myCell = myRow.getCell(colNum-1);
					if (myCell == null) {    
						myCell = myRow.createCell(colNum-1);    
					}
					myCell.setCellType(myCell.CELL_TYPE_NUMERIC);
					myCell.setCellValue(value);		
				}
				else {
					myRow = mySheet.createRow(rowNum-1);
					myCell = myRow.createCell(colNum-1);    
					myCell.setCellType(myCell.CELL_TYPE_NUMERIC);
					myCell.setCellValue(value);		
				}
				//traceMessage="[cell(" + rowNum + "," + colNum + ").value]=:"+  myCell.getNumericCellValue());
		}
		
		
		/*====================================================================================================
		*  Update Range 
		* ===================================================================================================*/
		public static void setRangeValue(String sheet,int rowStart, int colStart, List dataRange) throws Exception {
	
				List $row;
				for (int i = 1 ; i < dataRange.size(); i++) {
					$row =  (List) dataRange.get(i);
					System.out.println($row);
					for (int j = 1 ; j  <  $row.size(); j++) {
						System.out.println($row.get(j).toString());
						if ($row.get(j).toString().startsWith("href")) {
							setCellHyperlink(sheet, rowStart + i-1  , colStart+ j-1  , $row.get(j).toString());
						}
						else {
							setCellValue(sheet, rowStart + i-1  , colStart+ j-1  , $row.get(j).toString());
						}
					}
				}
		}

		
		public static void saveWorkbook() throws IOException {
		    
				// Write the output to a file  - current workbook   
				String $Workbook = WorkbookPath   + WorkbookName;
				FileOutputStream $Excel  = new FileOutputStream($Workbook);    
				myWorkbook.write($Excel);
				$Excel.flush();
				$Excel.close();
				traceMessage="";
				traceMessage="***** [Save]=:" + $Workbook;
		}

		
		public static void saveWorkbook(String fileName, String filePath) throws IOException {
	    
				// Write the output to a file    
				String $Workbook = filePath   + fileName;
				FileOutputStream $Excel  = new FileOutputStream($Workbook);    
				myWorkbook.write($Excel);
				$Excel.flush();
				$Excel.close();
				traceMessage="";
				traceMessage="***** [Save As]=:" + $Workbook;
		}

		
		/*====================================================================================================
		*New Workbook
		* ===================================================================================================*/
		/*public static void newWorkbook(String workbook,  String sheet ) throws Exception {
				
				try {
					FileOutputStream $fileOut = new FileOutputStream(workbook);    
					Workbook $newWorkbook = new XSSFWorkbook();
					Sheet $newSheet = $newWorkbook.createSheet(sheet);
				} 
				catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
		}*/


		public static void printDataSet(List dataSet ) throws Exception {

				traceMessage="==============================================================================";
				traceMessage="Print DataSet  " +  WorkbookPath + WorkbookName + "*****" ;
				traceMessage="==============================================================================";
				for (int i = 1; i <dataSet.size(); i++) {
					List $header = (List) dataSet.get(0);
					List $row = (List) dataSet.get(i);
					for (int j = 0; j <$row.size(); j ++) {
							System.out.print("["+ j + "-"+ $header.get(j) + "]=");
							System.out.print($row.get(j));
							System.out.print("   ");
					}
					traceMessage="";
				}
		}
	
		          

		public static void readToMyDataSet(Workbook workbook,  String sheet, Boolean FormularEvaluator, Boolean header) throws Exception {

				int $sheetIndex =   myWorkbook.getSheetIndex(sheet);
				mySheet = myWorkbook.getSheetAt($sheetIndex);
				//System.out.println(mySheet.getSheetName());
				FormulaEvaluator evaluator = myWorkbook.getCreationHelper().createFormulaEvaluator();
		
				int r = -1;
				int $rowNum =0;
				if (header = false) {
					Excel_DataSet.add(null);
				}
				Row myHeaderRow = (Row) mySheet.getRow(0);
				int $TotalCol = myHeaderRow.getLastCellNum();
				int myLastRow = mySheet.getLastRowNum();
				for (int i = 0 ; i <=myLastRow; i++) {
					List $data = new ArrayList();
					Row $myRow = (Row) mySheet.getRow(i);
			
					int $myColIndex = $myRow.getLastCellNum(); 
					if($TotalCol > $myColIndex) {
						$myColIndex = $TotalCol;
					}
					$data.add(i+1);
					for (int j =0; j <$myColIndex; j++) {
						if ($myRow.getCell(j) == null ||	$myRow.getCell(j).toString().trim().isEmpty()) {
							$myRow.createCell(j).setCellValue("");
						}
						CellReference cellReference = new CellReference(i,j);
						Cell cell = $myRow.getCell(cellReference.getCol());
						CellValue cellValue = evaluator.evaluate(cell);
				
						//CellValue cellValue = evaluator.evaluate($myRow.getCell(j));
						//System.out.println(cellValue.getCellType());
						switch (cellValue.getCellType()) {
							case Cell.CELL_TYPE_BOOLEAN: {
								//System.out.println(cellValue.getBooleanValue());
								$data.add(cellValue.getBooleanValue());
								break;
							}	
						
							case Cell.CELL_TYPE_STRING: {
								//traceMessage="[String]=:" + cellValue.getStringValue());
								$data.add(cellValue.getStringValue());
								break;
							}
					
							case Cell.CELL_TYPE_NUMERIC: {
						//traceMessage="[Numeric]=:" + cellValue.getNumberValue());
								$data.add(cellValue.getNumberValue());
								break;
							}
					
							case Cell.CELL_TYPE_BLANK: {
								//traceMessage="[BLANK]=:"); 
								$data.add("");
								break;
							}
					
							case Cell.CELL_TYPE_ERROR: {
								//traceMessage="[ERROR]=:"); 
								$data.add("#ERR");
								break;
							}
						
							case Cell.CELL_TYPE_FORMULA: {
								//traceMessage="[FORMULAR]=:" + "WILL NEVER HAPPEN"); 
								break;
							}
						}
					}
					Excel_DataSet.add($data);
				}
		}


		public static void setCellHyperlink(String sheet, int cellRow, int cellCol,  String address) throws Exception {

			int $sheetIndex =   myWorkbook.getSheetIndex(sheet);
			mySheet = myWorkbook.getSheetAt($sheetIndex);
	
			//address =  "href;file;Failed;c://myGalaxy//PeopleSoft//myTestset.xlsm"
			// 1- Type, 2 - Cell Value, 3 - Address
			String $CellHyperlink[] = address.split(";");
			
			
			Row myRow= mySheet.getRow(cellRow-1);
			Cell myCell; 
			if (myRow != null) {
				myCell = myRow.getCell(cellCol-1);
				if (myCell == null) {    
					myCell = myRow.createCell(cellCol-1);    
				}
				myCell.setCellType(myCell.CELL_TYPE_STRING);    
			}
			else {
				myRow = mySheet.createRow(cellRow-1);
				myCell = myRow.createCell(cellCol-1);    
				myCell.setCellType(myCell.CELL_TYPE_STRING);    
			}	
			//Create some HSSFHyperlink objects */
			CreationHelper creationHelper = myWorkbook.getCreationHelper();
			Hyperlink url_link = creationHelper.createHyperlink(Hyperlink.LINK_URL);
			Hyperlink file_link = creationHelper.createHyperlink(Hyperlink.LINK_FILE);
			Hyperlink email_link = creationHelper.createHyperlink(Hyperlink.LINK_EMAIL);
	
			switch ($CellHyperlink[1].toUpperCase()) {
			case ("URL"): {
				myCell.setCellValue($CellHyperlink[2]);          
				String $address = $CellHyperlink[3].replace(" ", "%20").replace("\\", "/");
				System.out.println( $address);
				//url_link.setAddress($CellHyperlink[3].replace(" ", "%20").replace("\\", "/"));
				url_link.setAddress($address);
						
				myCell.setHyperlink(url_link);
				break;
			}
		
			case ("FILE"): {
			//file_link.setAddress("file:///c://test.csv");
			//cell.setCellValue("Click to Open the file");            
				myCell.setCellValue($CellHyperlink[2]); 
				System.out.println( $CellHyperlink[3]);
				//file_link.setAddress("/"+$CellHyperlink[3].replace(" ", "%20").replace("\\", "/"));
				String $address = $CellHyperlink[3].replace(" ", "%20").replace("\\", "/");
				file_link.setAddress($address);
				myCell.setHyperlink(file_link);
				break;
			}
			
			case ("EMAIL"): {
				//email_link.setAddress("mailto:test@gmail.com");
				//cell.setCellValue("Send an Email");             
				myCell.setCellValue($CellHyperlink[2]);            
				email_link.setAddress($CellHyperlink[3].replace(" ", "%20"));
				myCell.setHyperlink(email_link);
				break;
			}
		}
	}



}
