package com.pekall.csv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.pekall.csv.bean.ContactInfo;
import com.pekall.csv.bean.UserInfo;
import junit.framework.TestCase;

import java.io.*;

public class ConverterTest extends TestCase {

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

    public void testGenCsv() throws Exception {
        Gson gson = new GsonBuilder().serializeNulls().create();
        ContactInfo info = null;
        try {
            info = gson.fromJson(TEST_JSON, ContactInfo.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(info.toCvs());
    }

    public void testParseEmptyCsv() throws Exception {
        ContactInfo info = (ContactInfo) Converter.csv2json(EMPTY_CSV_PATH);
        System.out.println(info.toString());

        assertEquals("", info.toCvs());
    }

    public void testParseEmptyLineCsv() throws Exception {
        ContactInfo info = (ContactInfo) Converter.csv2json(EMPTY_LINE_CSV_PATH);
        System.out.println(info.toString());

        ContactInfo info1 = new ContactInfo();
        info1.setResult("0");
        info1.setRecords(2L);
        info1.addUserInfo(new UserInfo());
        info1.addUserInfo(new UserInfo());

        assertEquals(info, info1);
    }

    public void testParseCsv() {
        ContactInfo info = (ContactInfo) Converter.csv2json(CSV_PATH);

        Gson gson = new GsonBuilder().serializeNulls().create();
        ContactInfo info1 = null;
        try {
            // String TEST_JSON is the json content of file CSV_PATH
            // So info and info1 should be same
            info1 = gson.fromJson(TEST_JSON, ContactInfo.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        assertEquals(info, info1);
    }

    public void testParseCsvTwoWay() {
        ContactInfo info = (ContactInfo) Converter.csv2json(CSV_PATH);
        assertEquals(TEST_CSV, info.toCvs());
    }

    public void testParseCsvTwoWay1() {
        ContactInfo info = (ContactInfo) Converter.csv2json(CSV_PATH);

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(info, info.getClass());

        ContactInfo info1 = gson.fromJson(json, ContactInfo.class);
        assertEquals(TEST_CSV, info1.toCvs());
    }

    private static final String TEST_CSV = "1,,\"\",51003,1,admin,pekall@pekall.com,\"\",,1,,,admin,0,,1376620759435,46\n" +
            "2,51302,\"\",51001,1,,,,,1,,,yaozhong,0,,1376620759438,2\n" +
            "3,51302,\"\",51001,1,,,,,1,,,wjl,0,,1376620759439,1\n" +
            "4,51302,\"\",51001,1,,pekall@pekall.com,,,1,,,licheng,0,,1376620759440,1\n" +
            "5,51302,\"\",51003,34,wdadad,songbaimeng@126.com,123,,2,,,waw,0,110,1376620759441,0\n" +
            "6,51302,\"\",51001,34,adadada,songbaimeng@126.com,wdawd,,2,,,dwadada,0,110,1376620759441,0\n" +
            "7,51302,\"\",51001,34,adadadad,wdaw,adawa,,2,,,awadadwa,0,110,1376620759442,0\n" +
            "8,51302,\"\",51001,34,fuxiaoran,xiaoran.fu@pekall.com,xiaoran,,2,,,fuxiaoran,0,110,1376620759443,2\n" +
            "9,51302,\"\",51001,16382,fuxiaoran,xiaoran.fu@pekall.com,xiaoranq,,2,,,fuxiaoran1,0,110,1376620759443,0\n" +
            "10,51302,\"\",51001,16382,fuxiaoran,xiaoran.fu@pekall.com,xiaoranq,,2,,,liuyulong,0,110,1376620759444,0\n" +
            "11,51302,\"\",51001,16380,fuxiaoran,xiaoran.fu@pekall.com,xiaoranq,,2,,,alalalalal,0,110,1376620759445,0\n" +
            "12,51302,\"\",51001,16348,fuxiaoran,xiaoran.fu@pekall.com,xiaoranq,,2,,,lulujkfjkd,0,110,1376620759445,0\n" +
            "13,51302,\"\",51001,16349,fuxiaoran,xiaoran.fu@pekall.com,xiaoranq,,2,,,fdsafk;dsjaf,0,110,1376620759446,0\n" +
            "14,51302,\"\",51001,34,fuxiaoran,xiaoran.fu@pekall.com,xiaoranq,,2,,,fdfdsfads,0,110,1376620759447,0\n" +
            "15,51302,\"\",51001,98,fuxiaoran,xiaoran.fu@pekall.com,xiaoranq,,2,,,fdsfdsafdgfg,0,110,1376620759447,0\n" +
            "16,51302,\"\",51001,97,fuxiaoran,xiaoran.fu@pekall.com,xiaoranq,,2,,,ghfdhgfdh,0,110,1376620759448,0\n" +
            "17,51302,\"\",51001,34,fuxiaoran,xiaoran.fu@pekall.com,xiaoranq,,2,,,disnaci,0,110,1376620759449,0\n" +
            "18,51302,\"\",51001,38,fuxiaoran,xiaoran.fu@pekall.com,xiaoranq,,2,,,aaaaaa,0,110,1376620759449,0\n" +
            "19,51302,\"\",51001,46,fuxiaoran,xiaoran.fu@pekall.com,xiaoranq,,2,,,bbbb,0,110,1376620759450,0\n" +
            "20,51302,\"\",51003,62,fuxiaoran,xiaoran.fu@pekall.com,xiaoranq,,2,,,cccc,0,110,1376620759451,0";



    private static final String TEST_JSON = "{\n" +
            "\"result\":\"0\",\"records\":20,\"users\":[{\n" +
            "\"id\":1,\"type\":null,\"password\":\"\",\"status\":51003,\"roles\":1,\"fullName\":\"admin\",\"email\":\"pekall@pekall.com\",\"firstName\":\"\",\"lastName\":null,\"enterpriseId\":1,\"token\":null,\"tmpId\":null,\"username\":\"admin\",\"deleted\":0,\"phoneNumber\":null,\"createTime\":1376620759435,\"deviceNo\":46}\n" +
            ",{\n" +
            "\"id\":2,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":1,\"fullName\":null,\"email\":null,\"firstName\":null,\"lastName\":null,\"enterpriseId\":1,\"token\":null,\"tmpId\":null,\"username\":\"yaozhong\",\"deleted\":0,\"phoneNumber\":null,\"createTime\":1376620759438,\"deviceNo\":2}\n" +
            ",{\n" +
            "\"id\":3,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":1,\"fullName\":null,\"email\":null,\"firstName\":null,\"lastName\":null,\"enterpriseId\":1,\"token\":null,\"tmpId\":null,\"username\":\"wjl\",\"deleted\":0,\"phoneNumber\":null,\"createTime\":1376620759439,\"deviceNo\":1}\n" +
            ",{\n" +
            "\"id\":4,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":1,\"fullName\":null,\"email\":\"pekall@pekall.com\",\"firstName\":null,\"lastName\":null,\"enterpriseId\":1,\"token\":null,\"tmpId\":null,\"username\":\"licheng\",\"deleted\":0,\"phoneNumber\":null,\"createTime\":1376620759440,\"deviceNo\":1}\n" +
            ",{\n" +
            "\"id\":5,\"type\":51302,\"password\":\"\",\"status\":51003,\"roles\":34,\"fullName\":\"wdadad\",\"email\":\"songbaimeng@126.com\",\"firstName\":\"123\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"waw\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759441,\"deviceNo\":0}\n" +
            ",{\n" +
            "\"id\":6,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":34,\"fullName\":\"adadada\",\"email\":\"songbaimeng@126.com\",\"firstName\":\"wdawd\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"dwadada\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759441,\"deviceNo\":0}\n" +
            ",{\n" +
            "\"id\":7,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":34,\"fullName\":\"adadadad\",\"email\":\"wdaw\",\"firstName\":\"adawa\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"awadadwa\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759442,\"deviceNo\":0}\n" +
            ",{\n" +
            "\"id\":8,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":34,\"fullName\":\"fuxiaoran\",\"email\":\"xiaoran.fu@pekall.com\",\"firstName\":\"xiaoran\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"fuxiaoran\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759443,\"deviceNo\":2}\n" +
            ",{\n" +
            "\"id\":9,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":16382,\"fullName\":\"fuxiaoran\",\"email\":\"xiaoran.fu@pekall.com\",\"firstName\":\"xiaoranq\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"fuxiaoran1\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759443,\"deviceNo\":0}\n" +
            ",{\n" +
            "\"id\":10,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":16382,\"fullName\":\"fuxiaoran\",\"email\":\"xiaoran.fu@pekall.com\",\"firstName\":\"xiaoranq\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"liuyulong\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759444,\"deviceNo\":0}\n" +
            ",{\n" +
            "\"id\":11,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":16380,\"fullName\":\"fuxiaoran\",\"email\":\"xiaoran.fu@pekall.com\",\"firstName\":\"xiaoranq\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"alalalalal\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759445,\"deviceNo\":0}\n" +
            ",{\n" +
            "\"id\":12,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":16348,\"fullName\":\"fuxiaoran\",\"email\":\"xiaoran.fu@pekall.com\",\"firstName\":\"xiaoranq\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"lulujkfjkd\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759445,\"deviceNo\":0}\n" +
            ",{\n" +
            "\"id\":13,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":16349,\"fullName\":\"fuxiaoran\",\"email\":\"xiaoran.fu@pekall.com\",\"firstName\":\"xiaoranq\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"fdsafk;dsjaf\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759446,\"deviceNo\":0}\n" +
            ",{\n" +
            "\"id\":14,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":34,\"fullName\":\"fuxiaoran\",\"email\":\"xiaoran.fu@pekall.com\",\"firstName\":\"xiaoranq\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"fdfdsfads\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759447,\"deviceNo\":0}\n" +
            ",{\n" +
            "\"id\":15,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":98,\"fullName\":\"fuxiaoran\",\"email\":\"xiaoran.fu@pekall.com\",\"firstName\":\"xiaoranq\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"fdsfdsafdgfg\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759447,\"deviceNo\":0}\n" +
            ",{\n" +
            "\"id\":16,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":97,\"fullName\":\"fuxiaoran\",\"email\":\"xiaoran.fu@pekall.com\",\"firstName\":\"xiaoranq\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"ghfdhgfdh\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759448,\"deviceNo\":0}\n" +
            ",{\n" +
            "\"id\":17,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":34,\"fullName\":\"fuxiaoran\",\"email\":\"xiaoran.fu@pekall.com\",\"firstName\":\"xiaoranq\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"disnaci\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759449,\"deviceNo\":0}\n" +
            ",{\n" +
            "\"id\":18,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":38,\"fullName\":\"fuxiaoran\",\"email\":\"xiaoran.fu@pekall.com\",\"firstName\":\"xiaoranq\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"aaaaaa\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759449,\"deviceNo\":0}\n" +
            ",{\n" +
            "\"id\":19,\"type\":51302,\"password\":\"\",\"status\":51001,\"roles\":46,\"fullName\":\"fuxiaoran\",\"email\":\"xiaoran.fu@pekall.com\",\"firstName\":\"xiaoranq\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"bbbb\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759450,\"deviceNo\":0}\n" +
            ",{\n" +
            "\"id\":20,\"type\":51302,\"password\":\"\",\"status\":51003,\"roles\":62,\"fullName\":\"fuxiaoran\",\"email\":\"xiaoran.fu@pekall.com\",\"firstName\":\"xiaoranq\",\"lastName\":null,\"enterpriseId\":2,\"token\":null,\"tmpId\":null,\"username\":\"cccc\",\"deleted\":0,\"phoneNumber\":\"110\",\"createTime\":1376620759451,\"deviceNo\":0}\n" +
            "]}";
}
