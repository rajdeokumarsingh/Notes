package com.pekall.csv;

import junit.framework.TestCase;

import java.io.*;

public class ConverterOssTest extends TestCase {

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

        // Debug.setVerboseDebugLog(true);
    }

    public void testParseEmptyCsvFile() throws Exception {
        String json = Converter.csv2JsonOSS(new File(EMPTY_CSV_PATH));
        Debug.logVerbose(json);

        // Empty CSV file should be converted to empty json array
        assertEquals("[]", json);
    }

    public void testParseEmptyLineCsv() throws Exception {
        String json = Converter.csv2Json(new File(EMPTY_LINE_CSV_PATH));
        Debug.logVerbose(json);

        // Empty lines should be ignored
        assertEquals("[]", json);
    }

    public void testParseEmptyLineCsv1() throws Exception {
        saveStrings(new String[]{"", "\n", ""});
        String json = Converter.csv2JsonOSS(new File(TMP_PATH));
        Debug.logVerbose(json);

        assertEquals("[]", json);
    }

    public void testError() throws Exception {
        try {
            saveStrings(new String[]{"!&^,@*^&,@<@,Y@&^,3478478,3,@&*@&@*"});
            Converter.csv2JsonOSS(new File(TMP_PATH));
            fail();
        } catch (NumberFormatException e) {
        }
    }

    public void testConverter() {
        String json = Converter.csv2JsonOSS(new File(CSV_PATH));
        Debug.logVerbose(json);
        assertEquals(json, TEMPLATE_JSON);
    }

    public void testConverterFile() {
        String json = Converter.csv2JsonOSS(new File(CSV_PATH));
        Debug.logVerbose(json);
        assertEquals(json, TEMPLATE_JSON);

        try {
            Converter.csv2JsonOSS(new File("/tmp"));
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            Converter.csv2JsonOSS(new File("/tmp/41234738074891820934"));
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    public void testPartialInfo1() throws Exception {
        // no user type and phone
        saveStrings(new String[]{"username1", "", "username2,"});
        String json = Converter.csv2JsonOSS(new File(TMP_PATH));
        Debug.logVerbose(json);

        // device field should be null
        assertEquals(json, SINGLE_USER_PART1);
    }

    public void testSingleUserPartialInfo() throws Exception {
        // no user type and phone
        saveStrings(new String[]{"username1,,user1@aa.com,,fullName1,firstName1,lastName1,"});
        String json = Converter.csv2JsonOSS(new File(TMP_PATH));
        Debug.logVerbose(json);

        // device field should be null
        assertEquals(json, SINGLE_USER_PART);
    }

    public void testSingleUserNoDevice() throws Exception {
        saveStrings(new String[]{
                "user1,password1,user1@aa.com,51302,lichengcheng,chengcheng,li,13800000000"
        });
        String json = Converter.csv2JsonOSS(new File(TMP_PATH));
        Debug.logVerbose(json);

        // device field should be null
        assertEquals(json, SINGLE_USER_NO_DEVICE);
    }

    public void testSingleUserNoDevice2() throws Exception {
        saveStrings(new String[]{"user1,password1,user1@aa.com,51302,lichengcheng,chengcheng,li,13800000000,,,,"});
        String json = Converter.csv2JsonOSS(new File(TMP_PATH));
        Debug.logVerbose(json);

        // device field should be null
        assertEquals(json, SINGLE_USER_NO_DEVICE);
    }

    public void testSingleUserDevicePartial() throws Exception {
        saveStrings(new String[]{
                "user2,password2,user2@aa.com,,,firstName2,,13800000001," +
                        "50701,51202,,securityInfo,"
        });
        String json = Converter.csv2JsonOSS(new File(TMP_PATH));
        Debug.logVerbose(json);

        assertEquals(json, SINGLE_USER_DEVICE_PARTIAL);
    }

    public void testEscapeCommas() throws Exception {
        saveStrings(new String[]{
                "\"Jacky, chen\",\"123,456,,\",user2@aa.com,,,firstName2,,\"1380,000, 0001\"," +
                        "50701,51202,,\",security,Info\","
        });
        String json = Converter.csv2JsonOSS(new File(TMP_PATH));
        Debug.logVerbose(json);

        assertEquals(json, SINGLE_USER_COMMA);
    }

    public void testEscapeQuotes() throws Exception {
        saveStrings(new String[]{
                "\"\"\"\",\"\"\"\"\"\",\"\"\"\"\"\"\"\"",
                "\"\"\"test\"\"\",\"\"\"test\",\"test me\"\"\"",
                "\"\"\"test, test\"\"\",\"\"\",test\"\"\",\"\"\"test,\"\"\""
        });
        String json = Converter.csv2JsonOSS(new File(TMP_PATH));
        Debug.logVerbose(json);

        assertEquals(json, SINGLE_USER_QUOTES);
    }

    public void testReadGbkFile() throws Exception {
        saveGbkStrings(new String[]{
                "\"Jacky, 成\",\"123,456,,\",王@aa.com,,,李成,,\"1380,000, 0001\"," +
                        "50701,51202,,\",security,Info\","
        });
        String json = Converter.csv2JsonOSS(new File(TMP_PATH));
        Debug.logVerbose(json);
        assertEquals(json, SINGLE_USER_CHN);
     }

    public void testPartialCsv() throws Exception {
        saveGbkStrings(new String[]{
                ",123456,123@123.com,22222222,whh,whh,whh,wanghai,50701,51202,en,securityInfo,tmpUuid----1"});
        Debug.setVerboseDebugLog(true);
        String json = Converter.csv2JsonOSS(new File(TMP_PATH));
        Debug.logVerbose(json);
        assertEquals(json, "");
    }

    private File saveStrings(String[] lines) throws IOException {
        File file = new File(TMP_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        for (String line : lines) {
            fileWriter.write(line + "\n");
        }
        fileWriter.close();

        return new File(TMP_PATH);
    }

    private File saveGbkStrings(String[] lines) throws IOException {
        File file = new File(TMP_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        DataOutputStream dos = new DataOutputStream(fos);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(dos, "GBK"));

        for (String line : lines) {
            bw.write(line + "\n");
        }
        bw.close();

        return new File(TMP_PATH);
    }

    private static final String TMP_PATH = "/tmp/cs_test.csv";
    private static final String CSV_PATH = "/tmp/test.csv";
    private static final String EMPTY_CSV_PATH = "/tmp/empty.csv";
    private static final String EMPTY_LINE_CSV_PATH = "/tmp/empty_line.csv";

    private static final String TEMPLATE_JSON = "[{\"username\":\"user1\",\"password\":\"password1\",\"email\":\"user1@aa.com\",\"type\":51302,\"firstName\":\"firstName1\",\"lastName\":\"lastName1\",\"fullName\":\"fullName1\",\"phone\":\"13800000000\",\"devices\":[{\"type\":50701,\"os\":51202,\"language\":\"en\",\"securityInfo\":\"securityInfo\",\"tmpUuid\":\"tmpUuid----1\",\"username\":\"user1\"},{\"type\":50701,\"os\":51202,\"language\":\"en\",\"securityInfo\":\"securityInfo\",\"tmpUuid\":\"tmpUuid----2\",\"username\":\"user1\"},{\"type\":50701,\"os\":51202,\"language\":\"en\",\"securityInfo\":\"securityInfo\",\"tmpUuid\":\"tmpUuid----3\",\"username\":\"user1\"}]},{\"username\":\"user2\",\"password\":\"password2\",\"email\":\"user2@aa.com\",\"type\":51302,\"firstName\":\"firstName2\",\"lastName\":\"lastName2\",\"fullName\":\"fullName2\",\"phone\":\"13800000001\",\"devices\":[{\"type\":50701,\"os\":51202,\"language\":\"en\",\"securityInfo\":\"securityInfo\",\"tmpUuid\":\"tmpUuid----4\",\"username\":\"user2\"}]},{\"username\":\"user3\",\"password\":\"password3\",\"email\":\"user3@aa.com\",\"type\":51302,\"firstName\":\"firstName3\",\"lastName\":\"lastName3\",\"fullName\":\"fullName3\",\"phone\":\"13800000002\",\"devices\":[{\"type\":50701,\"os\":51202,\"language\":\"cn\",\"securityInfo\":\"securityInfo\",\"tmpUuid\":\"tmpUuid----5\",\"username\":\"user3\"}]}]";
    private static final String TEST_CSV =
            "user1,password1,user1@aa.com,51302,fullName1,firstName1,lastName1,13800000000,50701,51202,en,securityInfo,tmpUuid----1\n" +
                    "user1,password1,user1@aa.com,51302,fullName1,firstName1,lastName1,13800000000,50701,51202,en,securityInfo,tmpUuid----2\n" +
                    "user1,password1,user1@aa.com,51302,fullName1,firstName1,lastName1,13800000000,50701,51202,en,securityInfo,tmpUuid----3\n" +
                    "user2,password2,user2@aa.com,51302,fullName2,firstName2,lastName2,13800000001,50701,51202,en,securityInfo,tmpUuid----4\n" +
                    "user3,password3,user3@aa.com,51302,fullName3,firstName3,lastName3,13800000002,50701,51202,cn,securityInfo,tmpUuid----5";

    private static final String SINGLE_USER_PART = "[{\"username\":\"username1\",\"password\":null," +
            "\"email\":\"user1@aa.com\",\"type\":null,\"firstName\":\"firstName1\"," +
            "\"lastName\":\"lastName1\",\"fullName\":\"fullName1\",\"phone\":null," +
            "\"devices\":null}]";

    private static final String SINGLE_USER_NO_DEVICE = "[{\"username\":\"user1\",\"password\":\"password1\",\"email\":\"user1@aa.com\",\"type\":51302,\"firstName\":\"chengcheng\",\"lastName\":\"li\",\"fullName\":\"lichengcheng\",\"phone\":\"13800000000\",\"devices\":null}]";

    private static final String SINGLE_USER_DEVICE_PARTIAL = "[{\"username\":\"user2\",\"password\":\"password2\",\"email\":\"user2@aa.com\",\"type\":null,\"firstName\":\"firstName2\",\"lastName\":null,\"fullName\":null,\"phone\":\"13800000001\",\"devices\":[{\"type\":50701,\"os\":51202,\"language\":null,\"securityInfo\":\"securityInfo\",\"tmpUuid\":null,\"username\":\"user2\"}]}]";

    private static final String SINGLE_USER_PART1 = "[{\"username\":\"username1\",\"password\":null,\"email\":null,\"type\":null,\"firstName\":null,\"lastName\":null,\"fullName\":null,\"phone\":null,\"devices\":null},{\"username\":\"username2\",\"password\":null,\"email\":null,\"type\":null,\"firstName\":null,\"lastName\":null,\"fullName\":null,\"phone\":null,\"devices\":null}]";

    private static final String SINGLE_USER_COMMA = "[{\"username\":\"Jacky, chen\",\"password\":\"123,456,,\",\"email\":\"user2@aa.com\",\"type\":null,\"firstName\":\"firstName2\",\"lastName\":null,\"fullName\":null,\"phone\":\"1380,000, 0001\",\"devices\":[{\"type\":50701,\"os\":51202,\"language\":null,\"securityInfo\":\",security,Info\",\"tmpUuid\":null,\"username\":\"Jacky, chen\"}]}]";

    private static final String SINGLE_USER_QUOTES = "[{\"username\":\"\\\"\",\"password\":\"\\\"\\\"\",\"email\":\"\\\"\\\"\\\"\",\"type\":null,\"firstName\":null,\"lastName\":null,\"fullName\":null,\"phone\":null,\"devices\":null},{\"username\":\"\\\"test\\\"\",\"password\":\"\\\"test\",\"email\":\"test me\\\"\",\"type\":null,\"firstName\":null,\"lastName\":null,\"fullName\":null,\"phone\":null,\"devices\":null},{\"username\":\"\\\"test, test\\\"\",\"password\":\"\\\",test\\\"\",\"email\":\"\\\"test,\\\"\",\"type\":null,\"firstName\":null,\"lastName\":null,\"fullName\":null,\"phone\":null,\"devices\":null}]";

    private static final String SINGLE_USER_CHN = "[{\"username\":\"Jacky, 成\",\"password\":\"123,456,,\",\"email\":\"王@aa.com\",\"type\":null,\"firstName\":\"李成\",\"lastName\":null,\"fullName\":null,\"phone\":\"1380,000, 0001\",\"devices\":[{\"type\":50701,\"os\":51202,\"language\":null,\"securityInfo\":\",security,Info\",\"tmpUuid\":null,\"username\":\"Jacky, 成\"}]}]";
}
