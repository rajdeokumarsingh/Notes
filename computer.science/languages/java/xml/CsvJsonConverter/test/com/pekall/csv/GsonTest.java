package com.pekall.csv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.pekall.csv.bean.ContactInfo;
import com.pekall.csv.bean.UserInfo;
import junit.framework.TestCase;

public class GsonTest extends TestCase{

    public void testFromGson() throws Exception {
        Gson gson = new GsonBuilder().serializeNulls().create();
        ContactInfo info = null;
        try {
            info = gson.fromJson(TEST_JSON, ContactInfo.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(info.toString());
    }

    public void testToGson() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(Long.valueOf(20));

        ContactInfo info = new ContactInfo();
        info.setResult("0");
        info.addUserInfo(userInfo);
        info.addUserInfo(new UserInfo());

        Gson gson = new GsonBuilder().serializeNulls().create();
        System.out.println(gson.toJson(info, info.getClass()));
    }

    public void testTwoWay() throws Exception {
        Gson gson = new GsonBuilder().serializeNulls().create();
        ContactInfo info = null;
        try {
            info = gson.fromJson(TEST_JSON, ContactInfo.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(info.toString());
        assertEquals(TEST_JSON.replace("\n", ""), gson.toJson(info, info.getClass()));
    }

    private static final String TEST_JSON = "{\n" +
            "\"result\":\"0\",\"records\":26,\"users\":[{\n" +
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
