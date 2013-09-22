package com.pekall.csv.bean;

import com.pekall.csv.Debug;
import junit.framework.TestCase;

import java.util.Arrays;

public class CsvLineTest extends TestCase {

    public void testSingleToken() throws Exception {
        final String CSV_LINE_1 = "\",\"";
        String[] tokens = CsvLine.combineTokens(CSV_LINE_1.split(","));

        // Debug.setVerboseDebugLog(true);
        for (String token : CSV_LINE_1.split(",")) {
            Debug.logVerbose(token);
        }
        Debug.logVerbose(Arrays.toString(tokens));

        assertEquals(tokens.length, 1);
        assertEquals(tokens[0], ",");
    }

    public void testSingleToken1() throws Exception {
        final String CSV_LINE_1 = "\"test,\"";
        String[] tokens = CsvLine.combineTokens(CSV_LINE_1.split(","));

        // Debug.setVerboseDebugLog(true);
        for (String token : CSV_LINE_1.split(",")) {
            Debug.logVerbose(token);
        }
        Debug.logVerbose(Arrays.toString(tokens));

        assertEquals(tokens.length, 1);
        assertEquals(tokens[0], "test,");
    }

    public void testSingleToken2() throws Exception {
        final String CSV_LINE_1 = "\",test\"";
        String[] tokens = CsvLine.combineTokens(CSV_LINE_1.split(","));

        // Debug.setVerboseDebugLog(true);
        for (String token : CSV_LINE_1.split(",")) {
            Debug.logVerbose(token);
        }
        Debug.logVerbose(Arrays.toString(tokens));

        assertEquals(tokens.length, 1);
        assertEquals(tokens[0], ",test");
    }

    public void testSingleToken3() throws Exception {
        final String CSV_LINE_1 = "\"good,test\"";
        String[] tokens = CsvLine.combineTokens(CSV_LINE_1.split(","));

        // Debug.setVerboseDebugLog(true);
        for (String token : CSV_LINE_1.split(",")) {
            Debug.logVerbose(token);
        }
        Debug.logVerbose(Arrays.toString(tokens));

        assertEquals(tokens.length, 1);
        assertEquals(tokens[0], "good,test");
    }

    public void testSingleToken4() throws Exception {
        final String CSV_LINE_1 = "\",more,,,good,more,test,,,,more,,\"";
        String[] tokens = CsvLine.combineTokens(CSV_LINE_1.split(","));

        // Debug.setVerboseDebugLog(true);
        for (String token : CSV_LINE_1.split(",")) {
            Debug.logVerbose(token);
        }
        Debug.logVerbose(Arrays.toString(tokens));

        assertEquals(tokens.length, 1);
        assertEquals(tokens[0], ",more,,,good,more,test,,,,more,,");
    }

    public void testMoreTokens1() throws Exception {
        final String CSV_LINE_1 = ",\",more,,,good,more,test,,,,more,,\"";
        String[] tokens = CsvLine.combineTokens(CSV_LINE_1.split(","));

        // Debug.setVerboseDebugLog(true);
        for (String token : CSV_LINE_1.split(",")) {
            Debug.logVerbose(token);
        }
        Debug.logVerbose(Arrays.toString(tokens));

        assertEquals(tokens.length, 2);
        assertEquals(tokens[0], "");
        assertEquals(tokens[1], ",more,,,good,more,test,,,,more,,");
    }

    public void testMoreTokens2() throws Exception {
        final String CSV_LINE_1 = "\"more,,,good,more,test,,,,more,,\",,,,";
        String[] tokens = CsvLine.combineTokens(CSV_LINE_1.split(","));

        // Debug.setVerboseDebugLog(true);
        for (String token : CSV_LINE_1.split(",")) {
            Debug.logVerbose(token);
        }
        Debug.logVerbose(Arrays.toString(tokens));

        assertEquals(tokens.length, 1);
        assertEquals(tokens[0], "more,,,good,more,test,,,,more,,");
    }


    public void testMoreTokens3() throws Exception {
        final String CSV_LINE_1 = "\"more,,,good,more,test,,,,more,,\",,test,";
        String[] tokens = CsvLine.combineTokens(CSV_LINE_1.split(","));

        // Debug.setVerboseDebugLog(true);
        for (String token : CSV_LINE_1.split(",")) {
            Debug.logVerbose(token);
        }
        Debug.logVerbose(Arrays.toString(tokens));

        assertEquals(tokens.length, 3);
        assertEquals(tokens[0], "more,,,good,more,test,,,,more,,");
        assertEquals(tokens[1], "");
        assertEquals(tokens[2], "test");
    }


    public void testMoreTokens4() throws Exception {
        final String CSV_LINE_1 = "to1,\",more,,,good,more,test,,,,more,,\", to3,\"tok4, m\"";
        String[] tokens = CsvLine.combineTokens(CSV_LINE_1.split(","));

        // Debug.setVerboseDebugLog(true);
        for (String token : tokens) {
            Debug.logVerbose(token);
        }
        Debug.logVerbose(Arrays.toString(tokens));

        assertEquals(tokens.length, 4);
        assertEquals(tokens[0], "to1");
        assertEquals(tokens[1], ",more,,,good,more,test,,,,more,,");
        assertEquals(tokens[2], " to3");
        assertEquals(tokens[3], "tok4, m");
    }
}
