package com.pekall.csv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Convert a CSV file to bean list
 */
public class GeneralCsvConverter {

    /**
     * Convert a CSV file to bean list, default encoding is "UTF-8"
     *
     * @param file
     * @param factory to create bean object from a csv line
     * @return a bean object
     * @throws IOException
     */
    public static <T> List<T> csv2BeanList(File file, CsvBeanFactory factory) throws IOException {
        return csv2BeanList(file, factory, "UTF-8");
    }

    /**
     * Convert a CSV file to bean list
     *
     * @param file     CSV file
     * @param factory  to create bean object from a csv line
     * @param encoding of the file
     * @return a bean list
     * @throws IOException
     */
    public static <T> List<T> csv2BeanList(File file, CsvBeanFactory factory, String encoding) throws IOException {
        if (file == null || !file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Please check whether the csv file is OK");
        }

        List<T> beanList = new ArrayList<T>();
        try {
            DataInputStream din = new DataInputStream(new FileInputStream(file));
            BufferedReader br = new BufferedReader(new InputStreamReader(din, encoding));
            CSVReader csvReader = new CSVReader(br);
            String[] tokens;
            while ((tokens = csvReader.readNext()) != null) {
                beanList.add((T) factory.createBean(tokens));
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return beanList;
    }
}
