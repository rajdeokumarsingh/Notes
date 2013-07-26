// replace [:/&%#?=]+ to _
String url = "http://blog.csdn.net/xiazdong?query=test&more=1#fragment.test";
Pattern pattern = Pattern.compile("[:/&%#?=]+");
String rp = pattern.matcher(url).replaceAll("_");
