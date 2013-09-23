package com.pekall.csv.bean;

import com.pekall.csv.CSVWriter;
import junit.framework.TestCase;

import java.io.PrintWriter;
import java.util.ArrayList;

public class CsvVoDemoTest extends TestCase {
    public void testGenCsv() throws Exception {
        ArrayList<String[]> csvCols = new ArrayList<String[]>();
        CsvVoDemo demo = new CsvVoDemo();
        demo.setType(25);
        demo.setOs(123);
        demo.setLanguage("cn");
        demo.setSecurityInfo("xxkk--xx");
        demo.setTmpUuid("22kk--xx--x--");
        demo.setUsername("Ray");
        csvCols.add(demo.toCvs());

        csvCols.add(new CsvVoDemo().toCvs());

        CsvVoDemo demo1= new CsvVoDemo();
        demo1.setOs(123);
        demo1.setLanguage("cn");
        demo1.setSecurityInfo("xxkk--xx");
        demo1.setTmpUuid("22kk--xx--x--");
        demo1.setUsername("Ray");
        csvCols.add(demo1.toCvs());

        CsvVoDemo demo2 = new CsvVoDemo();
        demo2.setType(25);
        demo2.setOs(123);
        demo2.setLanguage("cn");
        demo2.setSecurityInfo("xxkk--xx");
        demo2.setTmpUuid("22kk--xx--x--");
        csvCols.add(demo2.toCvs());

        CsvVoDemo demo3 = new CsvVoDemo();
        demo3.setType(25);
        demo3.setSecurityInfo("xxkk--xx");
        demo3.setTmpUuid("22kk--xx--x--");
        csvCols.add(demo3.toCvs());

        CSVWriter writer = new CSVWriter(new PrintWriter(System.out), ',', CSVWriter.NO_QUOTE_CHARACTER);
        writer.writeAll(csvCols);
        writer.close();
    }
}
