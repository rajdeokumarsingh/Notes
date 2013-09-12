package com.java.examples.basic.string;

public class StringFormatDemo {
    private static final String UA_BASE =
            "Mozilla/5.0 (Linux; U; Android %s)" +
            " AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 %s Safari/534.30";
    private static final String UA_MOBILE_PART = "Mobile ";
    private static final String UA_ONTIM_BASE = "ontim browser user agent base";

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        sb.append("test_version");
        String mobile = UA_MOBILE_PART;
        String base = UA_BASE;
        String ontim_base = UA_ONTIM_BASE;

        System.out.println(String.format(base, sb, mobile));
        System.out.println(String.format(ontim_base, sb, mobile));
    }
}
