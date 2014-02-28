package com.pekall.csv;

import com.pekall.csv.bean.ContactsCompanyInfo;
import com.pekall.csv.bean.ContactsEmployeeInfo;
import junit.framework.TestCase;

import java.io.*;
import java.util.List;

public class GeneralConverterTest extends TestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();

        FileWriter writer = new FileWriter(CSV_COMPANY_PATH);
        writer.write(TEST_COMPANY_CSV);
        writer.close();

        writer = new FileWriter(CSV_EMPLOYEE_PATH);
        writer.write(TEST_EMPLOYEE_CSV);
        writer.close();

        // zero info
        writer = new FileWriter(EMPTY_CSV_PATH);
        writer.close();

        // two empty infos
        writer = new FileWriter(EMPTY_LINE_CSV_PATH);
        writer.write("\n");
        writer.write("\n");
        writer.close();
    }

    public void testParseCompanyInfoNormal() throws IOException {
        String[] fields = new String[]{
                "name", "address", "postcode", "phoneNumber", "faxNumber"};
        CsvBeanFactory factory = new CsvBeanFactory(fields, ContactsCompanyInfo.class);
        List<ContactsCompanyInfo> infos =
                GeneralCsvConverter.csv2BeanList(new File(CSV_COMPANY_PATH), factory);


        assertEquals(infos.size(), 3);
        for (ContactsCompanyInfo info : infos) {
            assertNotNull(info);
            Debug.log(info.toString());
        }
    }

    public void testParseEmployInfoNormal() throws IOException {
        String[] fields = new String[]{
                "department", "name", "location", "phoneNumber",
                "cellPhoneNumber", "msn", "email" };
        CsvBeanFactory factory = new CsvBeanFactory(fields, ContactsEmployeeInfo.class);
        List<ContactsEmployeeInfo> infos =
                GeneralCsvConverter.csv2BeanList(new File(CSV_EMPLOYEE_PATH), factory);
        assertEquals(infos.size(), 25);
        for (ContactsEmployeeInfo info : infos) {
            assertNotNull(info);
            Debug.log(info.toString());
        }
    }

    public void testReadGbkFile() throws Exception {
        saveGbkStrings(new String[]{
                "物业设施,李海亮,亦庄,87120525,18618329012," +
                        "lihailiang2010@hotmail.com,lihl@cbcos.com"});

        String[] fields = new String[]{
                "department", "name", "location", "phoneNumber",
                "cellPhoneNumber", "msn", "email"};
        CsvBeanFactory factory = new CsvBeanFactory(fields, ContactsEmployeeInfo.class);
        List<ContactsEmployeeInfo> infos = GeneralCsvConverter.csv2BeanList(
                new File(TMP_PATH), factory, "GBK");

        assertEquals(infos.size(), 1);
        for (ContactsEmployeeInfo info : infos) {
            assertNotNull(info);
            Debug.log(info.toString());
        }
    }

    public void testParseEmptyCsvFile() throws Exception {
        String[] fields = new String[]{"name", "address", "postcode", "phoneNumber", "faxNumber"};
        CsvBeanFactory factory = new CsvBeanFactory(fields, ContactsCompanyInfo.class);
        List<ContactsCompanyInfo> infos = GeneralCsvConverter.csv2BeanList(
                new File(EMPTY_CSV_PATH), factory);
        assertEquals(infos.size(), 0);
        for (ContactsCompanyInfo info : infos) {
            assertNotNull(info);
            Debug.log(info.toString());
        }
    }

    public void testParseEmptyLineCsv() throws Exception {
        String[] fields = new String[]{"name", "address", "postcode", "phoneNumber", "faxNumber"};
        CsvBeanFactory factory = new CsvBeanFactory(fields, ContactsCompanyInfo.class);
        List<ContactsCompanyInfo> infos = GeneralCsvConverter.csv2BeanList(
                new File(EMPTY_LINE_CSV_PATH), factory);

        assertEquals(infos.size(), 2);
        for (ContactsCompanyInfo info : infos) {
            assertNotNull(info);
            Debug.log(info.toString());
        }
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

    private static final String CSV_COMPANY_PATH = "/tmp/test_company.csv";
    private static final String CSV_EMPLOYEE_PATH = "/tmp/test_employee.csv";

    private static final String TMP_PATH = "/tmp/cs_test.csv";
    private static final String EMPTY_CSV_PATH = "/tmp/empty.csv";
    private static final String EMPTY_LINE_CSV_PATH = "/tmp/empty_line.csv";

    private static final String TEST_COMPANY_CSV =
            "公司名称,公司地址,邮编,电话,传真,,\n" +
            "云基地科技,北京亦庄经济技术开发区地盛北街1号北工大软件园B区18号楼,100176,86-10-8712 0555,86-10-8712 0006 / 8712 0016,,\n" +
            ",\"NO.18 Building, Area B, NO.1, Disheng North Street, Software Park of Beijing University of Technology, Beijing Economic-Technological Development Area, 100176，China\",,,,,";

    private static final String TEST_EMPLOYEE_CSV =
            "部门,姓名,办公地点,电话,手机,MSN,公司邮箱\n" +
                    "管理层,尚青,亦庄/中关村,13901191121,13901191121,wendiydiamond@hotmail.com,shangqing@cbcos.com\n" +
                    "管理层,郝杰,亦庄/中关村,13501083363,13501083363,haojie@china-netcom.com,haojie@cbcos.com\n" +
                    "公司事务/宣传,张雷,中关村,,18611492128,,zhanglei@cbcos.com\n" +
                    "公司事务/宣传,倪苗苗,亦庄,87120026,18500086659,,nimm@cbcos.com\n" +
                    "物业设施,黄文舰,亦庄,87120500,13910519213,,huangwj@cbcos.com\n" +
                    "物业设施,李海亮,亦庄,87120525,18618329012,lihailiang2010@hotmail.com,lihl@cbcos.com\n" +
                    "物业设施,景文凯,亦庄,87120505,15611090005,,jingwk@cbcos.com\n" +
                    "物业设施,蒋卫峰,中关村,56380808,18618203058,,jiangwf@cbcos.com\n" +
                    "行政支持,巢鹏,亦庄,87120508,13801139370 / 18601371681,,chaopeng@cbcos.com\n" +
                    "行政支持,周燕蕾,亦庄,87120528,18601076956,zhou-yanlei@hotmail.com,zhouyl@cbcos.com\n" +
                    "行政支持,许术华,亦庄,87120536,18500030016,xushuhua2.-@hotmail.com,xush@cbcos.com\n" +
                    "行政支持,刘蕊,亦庄,87120457,18210113670,,liurui@cbcos.com\n" +
                    "行政支持,于平,亦庄,87120000,13436497890,,yuping@cbcos.com\n" +
                    "支付中心及税务执行,宋朝玲,亦庄,87120531,13811465081,,songcl@cbcos.com\n" +
                    "支付中心及税务执行,张建华,亦庄,,13651064969,,zhangjh@cbcos.com\n" +
                    "支付中心及税务执行,郭蕊新,亦庄,,13810589191,,guorx@cbcos.com\n" +
                    "支付中心及税务执行,沈林珺,亦庄,,13240341929,,shenlj@cbcos.com\n" +
                    "人力资源,张莹,亦庄/中关村,87120516/ 56380808-5561,13701353896,rita_ying@hotmail.com,zhangying1@cbcos.com\n" +
                    "人力资源,王京,亦庄/中关村,87120117/ 56380808-5559,13910836704,,wangjing@cbcos.com\n" +
                    "人力资源,沈珊珊,亦庄,87120521,13370158486,,shenss@cbcos.com\n" +
                    "人力资源,金静,亦庄,87120532,13581774810,,jinjing@cbcos.com\n" +
                    "人力资源,,,,,,\n" +
                    "人力资源,,,,,,\n" +
                    "人力资源,,,,,,";
}
