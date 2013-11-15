package com.pekall.csv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pekall.csv.bean.CsvFile;
import com.pekall.csv.bean.CsvLine;
import com.pekall.csv.bean.ImportDeviceInfoVo;
import com.pekall.csv.bean.ImportUserVo;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Convert user/device information from CSV format to json format
 *
 * CVS文件格式：
 * 1 开头是不留空，以行为单位。
 * 2 可含或不含列名，含列名则居文件第一行。
 * 3 一行数据不垮行，无空行。
 * 4 以半角逗号（即,）作分隔符，列为空也要表达其存在。
 * 5 列内容如存在半角逗号（即,）则用半角引号（即""）将该字段值包含起来。
 * TODO: support quote in content
 * 6 列内容如存在半角引号（即"）则应替换成半角双引号（""）转义，并用半角引号（即""）将该字段值包含起来。
 * 7 文件读写时引号，逗号操作规则互逆。
 * 8 内码格式不限，可为 ASCII、Unicode 或者其他。
 * 9 不支持特殊字符
 */
public class Converter {

    /**
     * Convert CSV lines to json
     * @param lines CSV lines
     * @return json string
     */
    public static String csv2Json(String[] lines) {
        if (lines == null) {
            throw new IllegalArgumentException("lines should not be null!");
        }

        CsvFile info = new CsvFile();
        for (String line : lines) {
            // Lines begin with "#" are comments, and ignore empty lines
            if (line.startsWith("#") || line.startsWith("\n")) continue;

            CsvLine csvLine = CsvLine.fromCsv(line);
            Debug.logVerbose(csvLine.toString());
            info.addLine(csvLine);
        }

        Type listType = new TypeToken<List<ImportUserVo>>() {
        }.getType();
        List<ImportUserVo> users = convertCsvInfo2Vo(info);
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(users, listType);
    }

    /**
     * Convert CSV file to json
     * @param fullPath of the CSV file
     * @return json string
     */
    public static String csv2Json(String fullPath) throws IOException {
        if (fullPath == null) {
            throw new IllegalArgumentException("file path should not be null");
        }

        ArrayList<String> lines = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader(fullPath);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 1024);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        String[] tmp = new String[lines.size()];
        lines.toArray(tmp);
        return csv2Json(tmp);
    }

    public static String csv2Json(File file) throws IOException {
        if (file == null || !file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("file should not be null");
        }
        return csv2Json(file.getAbsolutePath());
    }

    /**
     * Convert a CSV file to json string by OSS openCSV
     *
     * @param file CSV
     * @return json string
     */
    public static String csv2JsonOSS(File file) throws IOException {
        if (file == null || !file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("file should not be null");
        }

        CsvFile info = new CsvFile();
        try {
            DataInputStream din = new DataInputStream(new FileInputStream(file));
            BufferedReader br = new BufferedReader(new InputStreamReader(din, "GBK"));
            CSVReader csvReader = new CSVReader(br);
            String[] tokens;
            while ((tokens = csvReader.readNext()) != null) {
                CsvLine csvLine = CsvLine.fromCsv(tokens);
                Debug.logVerbose(csvLine.toString());
                info.addLine(csvLine);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        Type listType = new TypeToken<List<ImportUserVo>>() {
        }.getType();
        List<ImportUserVo> users = convertCsvInfo2Vo(info);
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(users, listType);
    }

    /**
     * Convert a CSV file to VO by OSS openCSV
     *
     * @param file CSV
     * @return list of VO
     */
    public static List<ImportUserVo> csv2VoOss(File file) throws IOException {
        if (file == null || !file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("file should not be null");
        }

        CsvFile info = new CsvFile();
        try {
            DataInputStream din = new DataInputStream(new FileInputStream(file));
            BufferedReader br = new BufferedReader(new InputStreamReader(din, "GBK"));
            CSVReader csvReader = new CSVReader(br);
            String[] tokens;
            while ((tokens = csvReader.readNext()) != null) {
                CsvLine csvLine = CsvLine.fromCsv(tokens);
                Debug.logVerbose(csvLine.toString());
                info.addLine(csvLine);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        return convertCsvInfo2Vo(info);
    }

    private static List<ImportUserVo> convertCsvInfo2Vo(CsvFile csvFile) {
        HashMap<String, ImportUserVo> userMap = new HashMap<String, ImportUserVo>();
        List<ImportUserVo> users = new ArrayList<ImportUserVo>();

        if(csvFile == null || csvFile.getLines() == null)
            return users;

        // create all user vo
        for (CsvLine userInfo: csvFile.getLines()) {
            if (userInfo == null || userInfo.getUsername() == null) continue;

            // The user has been created
            if(userMap.get(userInfo.getUsername()) != null) continue;

            ImportUserVo userVo = ImportUserVo.fromCsvUserInfo(userInfo);
            users.add(userVo);
            userMap.put(userInfo.getUsername(), userVo);
        }

        // create all device vo and map them to user
        for (CsvLine userInfo: csvFile.getLines()) {
            if (userInfo == null || userInfo.getUsername() == null) continue;

            ImportDeviceInfoVo deviceInfoVo = ImportDeviceInfoVo.fromCsvInfo(userInfo);
            ImportUserVo userVo = userMap.get(userInfo.getUsername());
            if (userVo != null && deviceInfoVo != null) {
                userVo.addDevice(deviceInfoVo);
            }
        }
        return users;
    }


    /**
     * @deprecated
     */
    public static Object csv2Bean(String fullPath) throws IOException {
        CsvFile info = null;
        try {
            FileReader fileReader = new FileReader(fullPath);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 1024);
            info = new CsvFile();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Lines begin with "#" are comments
                if(line.startsWith("#")) continue;

                info.addLine(CsvLine.fromCsv(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        // TODO: convert CSV file to new json format
        Gson gson = new GsonBuilder().serializeNulls().create();
        System.out.println(gson.toJson(info, info.getClass()));

        return info;
    }
}
