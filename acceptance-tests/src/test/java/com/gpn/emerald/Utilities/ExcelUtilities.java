/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * The ExcelUtilities provides various method to perform operation on excel such as retrieving cell data, removing column and sheet etc. 
 */

public class ExcelUtilities {
	public static String filename = System.getProperty("user.dir") + "\\src\\com\\ATT\\Resources\\TestData.xlsx";
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	/*
	 * Constructor
	 * 
	 * @param path This is path of excel file to be operated upon
	 */

	public ExcelUtilities(String path) {

		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * This method is used to count number of rows of given sheet name
	 * 
	 * @param sheetName This is the sheetName
	 * 
	 * @return number This is the count of total number of rows present in the
	 * given sheet
	 */
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}

	}

	/*
	 * This method reads data from column specified in input parameter
	 * 
	 * @param sheetName This is sheet name
	 * 
	 * @param colName This is column name whose values need to be retrieved
	 * 
	 * @param rowNum This is value of row number from value has to be retrieved
	 */
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {

				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

				}

				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	/*
	 * This overload method to retrieve data from cell using column and row
	 * index
	 * 
	 * @param sheetName This is sheet name
	 * 
	 * @param colNum This is column index from which values need to be retrieved
	 * 
	 * @param rowNum This is row index from which value has to be retrieved
	 */
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);

			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

				}

				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	/*
	 * This method set data in the cell specified by colName and rowNum on the
	 * sheet specified by sheetName
	 * 
	 * @param sheetName This is sheet name
	 * 
	 * @param colName This is column name whose values need to be retrieved
	 * 
	 * @param rowNum This is value of row number from value has to be retrieved
	 * 
	 * @param data This is value that needs to be set in the cell specified
	 * 
	 * @return boolean This value specifies whether data set successful or
	 * failure
	 */
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);

			if (rowNum <= 0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {

				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			// cell style
			// CellStyle cs = workbook.createCellStyle();
			// cs.setWrapText(true);
			// cell.setCellStyle(cs);
			cell.setCellValue(data);

			fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * This method set data retrieved from URL in the cell specified by colName
	 * and rowNum on the sheet specified by sheetName
	 * 
	 * @param sheetName This is sheet name
	 * 
	 * @param colName This is column name whose values need to be retrieved
	 * 
	 * @param rowNum This is value of row number from value has to be retrieved
	 * 
	 * @param data This is value that needs to be set in the cell specified
	 * 
	 * @return boolean This value specifies whether data set successful or
	 * failure
	 */
	public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {

		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);

			if (rowNum <= 0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
					colNum = i;
			}

			if (colNum == -1)
				return false;
			sheet.autoSizeColumn(colNum); // ashish
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			cell.setCellValue(data);
			XSSFCreationHelper createHelper = workbook.getCreationHelper();

			// cell style for hyperlinks
			// by default hypelrinks are blue and underlined
			CellStyle hlink_style = workbook.createCellStyle();
			XSSFFont hlink_font = workbook.createFont();
			hlink_font.setUnderline(XSSFFont.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);
			// hlink_style.setWrapText(true);

			XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
			link.setAddress(url);
			cell.setHyperlink(link);
			cell.setCellStyle(hlink_style);

			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * This method creates sheet in excel
	 * 
	 * @param sheetName This is the name of sheet to be generated
	 * 
	 * @return boolean This specifies whether sheet creation success or failure
	 */
	public boolean addSheet(String sheetname) {

		FileOutputStream fileOut;
		try {
			workbook.createSheet(sheetname);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * This method removes sheet specified in input parameter
	 * 
	 * @param sheetName this is name of sheet to be removed
	 * 
	 * @return boolean This specifies whether sheet deletion success or failure
	 */
	public boolean removeSheet(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return false;

		FileOutputStream fileOut;
		try {
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * This method helps to create/add column in the given sheet
	 * 
	 * @param sheetName This is name of sheet
	 * 
	 * @colName This is name of column to be created
	 * 
	 * @return boolean This specifies whether column added successfully or not
	 */
	public boolean addColumn(String sheetName, String colName) {
		// .println("**************addColumn*********************");

		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return false;

			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);

			// cell = row.getCell();
			// if (cell == null)
			// .println(row.getLastCellNum());
			if (row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());

			cell.setCellValue(colName);
			cell.setCellStyle(style);

			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	/*
	 * This method help to delete column from given sheetname
	 * 
	 * @param sheetName This is name of sheet
	 * 
	 * @param colNum This is index of column which needs to be deleted
	 * 
	 * @return boolean This specifies whether column removal is successful or
	 * failure
	 */
	public boolean removeColumn(String sheetName, int colNum) {
		try {
			if (!isSheetExist(sheetName))
				return false;
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			@SuppressWarnings("unused")
			XSSFCreationHelper createHelper = workbook.getCreationHelper();
			style.setFillPattern(HSSFCellStyle.NO_FILL);

			for (int i = 0; i < getRowCount(sheetName); i++) {
				row = sheet.getRow(i);
				if (row != null) {
					cell = row.getCell(colNum);
					if (cell != null) {
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/*
	 * This method helps to identify whether sheet exists or not
	 * 
	 * @param sheetName This is the name of sheet to be checked for existence
	 * 
	 * @return boolean this value specifies whether sheet exist or not
	 */
	public boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	/*
	 * This method return number of columns in the sheet
	 * 
	 * @param sheetName This is name of sheets from number of columns has to be
	 * identified
	 * 
	 * @return int This is value of number of columns available
	 */

	// returns number of columns in a sheet
	public int getColumnCount(String sheetName) {
		// check if sheet exists
		if (!isSheetExist(sheetName))
			return -1;

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if (row == null)
			return -1;

		return row.getLastCellNum();

	}

	/*
	 * This method helps to add hyperlink
	 */
	public boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, int index, String url,
			String message) {

		url = url.replace('\\', '/');
		if (!isSheetExist(sheetName))
			return false;

		sheet = workbook.getSheet(sheetName);

		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)) {

				setCellData(sheetName, screenShotColName, i + index, message, url);
				break;
			}
		}

		return true;
	}

	/*
	 * This method helps to identify row number of cell
	 */

	public int getCellRowNum(String sheetName, String colName, String cellValue) {

		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;

	}

	/*
	 * This method helps to read cell value as string
	 */

	public static String getCellAsString(XSSFRow currentRow, int col) {
		Cell cell = (currentRow).getCell(col);

		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case Cell.CELL_TYPE_NUMERIC:
				return String.valueOf(cell.getNumericCellValue());
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			case Cell.CELL_TYPE_BLANK:
				return "";
			case Cell.CELL_TYPE_ERROR:
				return String.valueOf(cell.getErrorCellValue());
			case Cell.CELL_TYPE_FORMULA:
				break;
			}
		}
		return null;
	}

	/*
	 * This method helps to read sheet from excel as key value pair
	 */

	public static HashMap<String, String> readExcelData(String filepath, String sheetName, int key, String methodName)
			throws IOException {

		// Create the input stream for Excel file
		// String
		// filepath1="C://Users//sbhat008//workspace//ATT//src//com//ATT//Resources//TestData.xlsx";
		InputStream excelfile = new FileInputStream(filepath);
		// Create the XSSF workbook and sheet object/variables

		@SuppressWarnings("resource")
		XSSFWorkbook wrkbook = new XSSFWorkbook(excelfile);
		XSSFSheet currentSheet = wrkbook.getSheet(sheetName);

		// Get the Last ROW and Column number
		int lastRow = currentSheet.getLastRowNum();
		XSSFRow titleRow = currentSheet.getRow(0);
		int lastCol = titleRow.getLastCellNum();

		// Create an Object variable with size as Number of rows and 2 columns
		// and HashMap for storing excel data
		// Here we are storing the test case name in first index and row data as
		// HashMap in second index

		Object[][] data = new Object[lastRow][2];
		HashMap<String, String> rowdata = new HashMap<String, String>();

		// Iterate the through the rows and columns to fetch the data

		System.out.println("Row number is" + key);
		for (int row = key; row <= key; row++) {
			XSSFRow currentRow = currentSheet.getRow(row);

			Cell firstCell = currentRow.getCell(0);

			if (firstCell.getStringCellValue().trim().equals(methodName.toString().trim())) {

				for (int col = 0; col < lastCol - 1; col++) {
					String keyWord = titleRow.getCell(col).getStringCellValue();
					if (keyWord != null && titleRow.getCell(col + 1) != null && currentRow.getCell(col + 1) != null) {
						String valueAsString = ExcelUtilities.getCellAsString(currentRow, col + 1);
						rowdata.put(titleRow.getCell(col + 1).getStringCellValue(), valueAsString);
					}
				}
			}

			data[row - 1][0] = firstCell.getStringCellValue();
			data[row - 1][1] = rowdata.clone();

			// rowdata.clear();
			excelfile.close();

		}

		// return data;
		return rowdata;
	}

}
