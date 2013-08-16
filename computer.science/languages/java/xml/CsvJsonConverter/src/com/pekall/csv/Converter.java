package com.pekall.csv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pekall.csv.bean.ContactInfo;
import com.pekall.csv.bean.UserInfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * CVS文件格式：
 * 1 开头是不留空，以行为单位。
 * 2 可含或不含列名，含列名则居文件第一行。
 * 3 一行数据不垮行，无空行。
 * 4 以半角逗号（即,）作分隔符，列为空也要表达其存在。
 * TODO: support comma in content
 * 5 列内容如存在半角逗号（即,）则用半角引号（即""）将该字段值包含起来。
 * TODO: support quote in content
 * 6 列内容如存在半角引号（即"）则应替换成半角双引号（""）转义，并用半角引号（即""）将该字段值包含起来。
 * 7 文件读写时引号，逗号操作规则互逆。
 * 8 内码格式不限，可为 ASCII、Unicode 或者其他。
 * 9 不支持特殊字符
 */
public class Converter {

    public static void main(String[] args) {
        csv2json(args[0]);
    }

    public static Object csv2json(String fullPath) {
        ContactInfo info = null;
        try {
            FileReader fileReader = new FileReader(fullPath);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 1024);
            info = new ContactInfo();
            info.setResult("0");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                info.addUserInfo(UserInfo.fromCsv(line));
            }
            if (info.getUsers() == null) {
                info.setRecords(0L);
            } else {
                info.setRecords(Long.valueOf(info.getUsers().size()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().serializeNulls().create();
        System.out.println(gson.toJson(info, info.getClass()));

        return info;
    }
}
