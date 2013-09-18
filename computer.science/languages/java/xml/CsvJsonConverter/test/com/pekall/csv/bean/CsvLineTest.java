package com.pekall.csv.bean;

import com.pekall.csv.Debug;
import junit.framework.TestCase;

import java.util.Arrays;

public class CsvLineTest extends TestCase {

    public void testCombineTokens() throws Exception {
        final String CSV_LINE_1 = "user1,\"full,Name, test\",firstName";
        String[] tokens = CsvLine.combineTokens(CSV_LINE_1.split(","));

        Debug.logVerbose(Arrays.toString(tokens));
        assertEquals(tokens.length, 3);
        assertEquals(tokens[0], "user1");
        assertEquals(tokens[1], "full,Name, test");
        assertEquals(tokens[2], "firstName");
    }

    public void testCombineTokens1() throws Exception {
        final String CSV_LINE_1 = "\"user1,test \",password,abc@aa.com,51302,\" full,Name\"," +
                "firstName,lastName";
        String[] tokens = CsvLine.combineTokens(CSV_LINE_1.split(","));

        Debug.logVerbose(Arrays.toString(tokens));
        assertEquals(tokens.length, 7);
        assertEquals(tokens[0], "user1,test ");
        assertEquals(tokens[1], "password");
        assertEquals(tokens[2], "abc@aa.com");
        assertEquals(tokens[3], "51302");
        assertEquals(tokens[4], " full,Name");
        assertEquals(tokens[5], "firstName");
        assertEquals(tokens[6], "lastName");
    }

    /* public void testCombineTokens3() throws Exception {
        Debug.setVerboseDebugLog(true);

        final String CSV_LINE_1 = "\", test, god,\",\",,\",abc@aa.com,51302,\"full,Name\",firstName,lastName";
        Debug.logVerbose(Arrays.toString(CSV_LINE_1.split(",")));

        String[] tokens = CsvLine.combineTokens(CSV_LINE_1.split(","));

        Debug.logVerbose(Arrays.toString(tokens));
        assertEquals(tokens.length, 3);
        assertEquals(tokens[0], "user1");
        assertEquals(tokens[1], "full,Name, test");
        assertEquals(tokens[2], "firstName");
    }
    */

}
