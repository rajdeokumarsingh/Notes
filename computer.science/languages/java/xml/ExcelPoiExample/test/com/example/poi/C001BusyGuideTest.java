package com.example.poi;

import junit.framework.TestCase;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class C001BusyGuideTest extends TestCase {

    public static final String TMP_WORKBOOK_CELLS_XLS = "/tmp/workbook_cell_iter.xls";

    private C001BusyGuide mGuide;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mGuide = new C001BusyGuide();
    }

    public void testCreateWorkbook() throws Exception {
        mGuide.createXls();
        mGuide.createXlsx();
    }

    public void testCreateSheets() throws Exception {
        mGuide.createSheets();
    }

    public void testCreateCells() throws Exception {
        mGuide.createCells();
    }

    public void testCreateDateCells() throws Exception {
        mGuide.createDateCell();
    }


    public void testIteration() throws Exception {
        // createCells();

        Workbook wb = WorkbookFactory.create(new File("/tmp/contact.xlsx"));
        Sheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            System.out.println("row begin, row number: " + row.getRowNum() +
                    ", getPhysicalNumberOfCells: " + row.getPhysicalNumberOfCells() +
                    ", getLastCellNum: " + row.getLastCellNum());
            if(row == null) {
                System.out.println("empty row, ignore");
                continue;
            }
            /*
            for (Cell cell : row) {
                if(cell == null) {
                    System.out.println("cell row, ignore");
                    continue;
                }
                System.out.println("cell: " + cell.toString());
            }
            */
            // for(int i = 0; i< row.getPhysicalNumberOfCells(); i++) {
            for(int i = 0; i< 10; i++) {
                Cell cell = row.getCell(i);
                if(cell == null) {
                    System.out.println("cell row, ignore");
                    continue;
                }
                System.out.println("cell: " + cell.toString());
            }
        }
    }

    private void createCells() throws IOException {
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
        FileOutputStream fileOut = new FileOutputStream(TMP_WORKBOOK_CELLS_XLS);
        wb.write(fileOut);
        fileOut.close();
    }
}
