package com.example.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * reference:
 *      http://poi.apache.org/spreadsheet/quick-guide.html
 */
public class C001BusyGuide {

    void createXlsx() throws IOException {
        Workbook wb1 = new XSSFWorkbook();
        FileOutputStream fileOut1 = new FileOutputStream("/tmp/workbook.xlsx");
        wb1.write(fileOut1);
        fileOut1.close();
    }

    void createXls() throws IOException {
        Workbook wb = new HSSFWorkbook();
        FileOutputStream fileOut = new FileOutputStream("/tmp/workbook.xls");
        wb.write(fileOut);
        fileOut.close();
    }

    void createSheets() throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet1 = wb.createSheet("new sheet");
        Sheet sheet2 = wb.createSheet("测试");

        // You can use org.apache.poi.ss.util.WorkbookUtil#createSafeSheetName(String nameProposal)}
        // for a safe way to create valid names, this utility replaces invalid characters with a space (' ')
        String safeName = WorkbookUtil.createSafeSheetName("[O'Brien's sales*?]"); // returns " O'Brien's sales   "
        Sheet sheet3 = wb.createSheet(safeName);

        FileOutputStream fileOut = new FileOutputStream("/tmp/workbook_sheet.xls");
        wb.write(fileOut);
        fileOut.close();
    }

    void createCells() throws IOException {
        Workbook wb = new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();

        Sheet sheet = wb.createSheet("new sheet");

        // Create a row and put some cells in it. Rows are 0 based.
        Row row = sheet.createRow((short)0);
        // Create a cell and put a value in it.
        Cell cell = row.createCell(0);
        cell.setCellValue(1);

        // Or do it on one line.
        row.createCell(1).setCellValue(1.2);
        row.createCell(2).setCellValue(
                createHelper.createRichTextString("This is a string"));
        row.createCell(3).setCellValue(true);

        Row row2 = sheet.createRow((short)2);
        row2.createCell(0).setCellValue(1.1);
        row2.createCell(1).setCellValue(new Date());
        row2.createCell(2).setCellValue(Calendar.getInstance());
        row2.createCell(3).setCellValue("a string");
        row2.createCell(4).setCellValue(true);
        row2.createCell(5).setCellType(Cell.CELL_TYPE_ERROR);

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("/tmp/workbook_cells.xls");
        wb.write(fileOut);
        fileOut.close();
    }

    void createDateCell() throws IOException {
        Workbook wb = new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("new sheet");

        // Create a row and put some cells in it. Rows are 0 based.
        Row row = sheet.createRow(0);

        // Create a cell and put a date value in it.  The first cell is not styled
        // as a date.
        Cell cell = row.createCell(0);
        cell.setCellValue(new Date());

        // we style the second cell as a date (and time).  It is important to
        // create a new cell style from the workbook otherwise you can end up
        // modifying the built in style and effecting not only this cell but other cells.
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
        cell = row.createCell(1);
        cell.setCellValue(new Date());
        cell.setCellStyle(cellStyle);

        //you can also set date as java.util.Calendar
        cell = row.createCell(2);
        cell.setCellValue(Calendar.getInstance());
        cell.setCellStyle(cellStyle);

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("/tmp/workbook_date_cell.xls");
        wb.write(fileOut);
        fileOut.close();
    }
}
