package com.pekall.csv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pekall.csv.bean.ContactInfo;
import com.pekall.csv.bean.UserInfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
