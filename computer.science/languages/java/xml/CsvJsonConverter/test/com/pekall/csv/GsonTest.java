package com.pekall.csv;

import com.google.gson.*;
import com.pekall.csv.bean.CsvFile;
import com.pekall.csv.bean.CsvLine;
import junit.framework.TestCase;

class TestGsonStrategy implements ExclusionStrategy {

    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes f) {
        return (f.getDeclaringClass() == CsvLine.class && f.getName().equals("username"))||
                (f.getDeclaringClass() == CsvLine.class && f.getName().equals("password"));
    }

}

public class GsonTest extends TestCase{

    public void testFromGson() throws Exception {
        Gson gson = new GsonBuilder().serializeNulls().
                setExclusionStrategies(new TestGsonStrategy()).create();
        CsvFile info = null;
        try {
            info = gson.fromJson(TEST_JSON, CsvFile.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(info.toString());
    }

    public void testToGson() throws Exception {
        CsvLine userInfo = new CsvLine();
        userInfo.setUsername("Ray");
        userInfo.setPassword("12345");

        CsvFile csvFile = new CsvFile();
        csvFile.addLine(userInfo);
        csvFile.addLine(new CsvLine());

        Gson gson = new GsonBuilder().serializeNulls().create();
        System.out.println(gson.toJson(csvFile, csvFile.getClass()));
    }

    public void testTwoWay() throws Exception {
        Gson gson = new GsonBuilder().serializeNulls().create();
        CsvFile info = null;
        try {
            info = gson.fromJson(TEST_JSON, CsvFile.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(info.toString());
        assertEquals(TEST_JSON.replace("\n", ""), gson.toJson(info, info.getClass()));
    }

    private static final String TEST_JSON = "{\"lines\":[{\"username\":\"Ray\",\"password\":\"12345\",\"email\":null,\"userType\":null,\"fullName\":null,\"firstName\":null,\"lastName\":null,\"phone\":null,\"deviceType\":null,\"os\":null,\"language\":null,\"securityInfo\":null,\"tmpUuid\":null},{\"username\":null,\"password\":null,\"email\":null,\"userType\":null,\"fullName\":null,\"firstName\":null,\"lastName\":null,\"phone\":null,\"deviceType\":null,\"os\":null,\"language\":null,\"securityInfo\":null,\"tmpUuid\":null}]}";

/* Test json
[
    {
        "username": "importuser1",
        "password": "123456",
        "email": "240647341@qq.com",
        "type": 51302,
        "fullName": "",
        "firstName": "",
        "lastName": null,
        "phone": "110",
        "devices": [
            {
                "language": null,
                "type": 50701,
                "tmpUuid": "importuuid----------------1",
                "os": 51202,
                "username": "importuser1",
                "securityInfo": null
            }
        ]
    }
]
*/

}
