package com.pekall.csv;

import com.pekall.csv.bean.CsvFile;
import com.pekall.csv.bean.CsvLine;
import junit.framework.TestCase;

import java.io.*;

public class CsvFileTest extends TestCase {

    private static final String CSV_PATH = "/tmp/test.csv";
    private static final String EMPTY_CSV_PATH = "/tmp/empty.csv";
    private static final String EMPTY_LINE_CSV_PATH = "/tmp/empty_line.csv";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        FileWriter writer = new FileWriter(CSV_PATH);
        writer.write(TEST_CSV);
        writer.close();

        // zero user info
        FileWriter writer1 = new FileWriter(EMPTY_CSV_PATH);
        writer1.close();

        // two empty user infos
        FileWriter writer2 = new FileWriter(EMPTY_LINE_CSV_PATH);
        writer2.write("\n");
        writer2.write("\n");
        writer2.close();
    }

    public void testParseEmptyCsv() throws Exception {
        CsvFile info = (CsvFile) Converter.csv2Bean(EMPTY_CSV_PATH);
        Debug.logVerbose(info.toString());

        assertEquals("", info.toCvs());
    }

    /*
    public void testParseEmptyLineCsv() throws Exception {
        CsvFile info = (CsvFile) Converter.csv2Bean(EMPTY_LINE_CSV_PATH);
        Debug.logVerbose(info.toString());

        CsvFile info1 = new CsvFile();
        info1.addLine(new CsvLine());
        info1.addLine(new CsvLine());

        assertEquals(info, info1);
    }
    */

    public void testParseCsv() {
        CsvFile info = (CsvFile) Converter.csv2Bean(CSV_PATH);
        Debug.logVerbose(info.toString());
    }

    public void testParseCsvTwoWay() {
        CsvFile info = (CsvFile) Converter.csv2Bean(CSV_PATH);
        assertEquals(TEST_CSV, info.toCvs());
    }

    public void testConverter() {
        // Debug.setVerboseDebugLog(true);
        Debug.logVerbose(Converter.csv2Json(CSV_PATH));
    }

    private static final String TEST_CSV =
            "user1,password1,user1@aa.com,51302,fullName1,firstName1,lastName1,13800000000,50701,51202,en,securityInfo,tmpUuid----1\n" +
            "user1,password1,user1@aa.com,51302,fullName1,firstName1,lastName1,13800000000,50701,51202,en,securityInfo,tmpUuid----2\n" +
            "user1,password1,user1@aa.com,51302,fullName1,firstName1,lastName1,13800000000,50701,51202,en,securityInfo,tmpUuid----3\n" +
            "user2,password2,user2@aa.com,51302,fullName2,firstName2,lastName2,13800000001,50701,51202,en,securityInfo,tmpUuid----4\n" +
            "user3,password3,user3@aa.com,51302,fullName3,firstName3,lastName3,13800000002,50701,51202,cn,securityInfo,tmpUuid----5";
}
