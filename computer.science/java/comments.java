
javadoc提取下面几方面的注释
    包
    public类和接口
    public和protected方法
    public和protected域

    私有成员的注释不要写成javadoc的格式

格式：
    begin with /** end with */

    free-form text
        @author
        @param
        第一句应该是一个概要性的句子
        可使用html修饰符
            <em>...</em>强调
            <strong>...</strong>强调
            <code>...</code>等宽打字机字体
            <img ...>包括图片
                需要将图片放到目录doc-files中

    类注释
        import语句之后，类定义之前
        /**
         *  A class
         */
        public class A {
        }

    方法注释
        @param variable description
        @return  description
        @throws class description

        /**
         * Retrieve the HTTP authentication username and password for a given
         * host & realm pair
         *
         * @param host The host for which the credentials apply.
         * @param realm The realm for which the credentials apply.
         * @return String[] if found, String[0] is username, which can be null and
         *         String[1] is password. Return null if it can't find anything.
         */
        public String[] getHttpAuthUsernamePassword(String host, String realm) {
            return mDatabase.getHttpAuthUsernamePassword(host, realm);
        }

    域注释
        只需要对公有的域建立文档
        对私有的域建立文档也没错哦
        /**
         * Touch mode
         */
        private int mTouchMode = TOUCH_DONE_MODE;
        private static final int TOUCH_INIT_MODE = 1; 

    通用注释
        @author name
            可用于类文档注释中, 表示作者. 可有多个作者，每个作者对应一个@author标签

        @version text
            对当前版本的描述

        @since text
            可用于所有注释中
            @since version 1.7.1

        @deprecated text
            类，方法，域中添加一个不再使用的注释。text中给出替代的建议
            @deprecated Use setVisible(true) instead

        @see reference
            链接到文档其他部分
            可有多个@see注释， 但必须放在一起

            @see #savePicture
            @see #restorePicture
            reference
                package.class#feature lable
                <a href="...">lable</a>
                    超链接
                "text"
                    描述性文字

        @link
            可放在注视的任意位置
            规则和@see一样

    包注释
        需要在包目录中添加一个package.html的文件
        标记在<body>...</body>之间的所有文本就会被抽取出来

    概要注释(为所有源文件)
        需要在根目录中添加一个overview.html的文件
        标记在<body>...</body>之间的所有文本就会被抽取出来


生成java doc
    切换到包目录的父目录
    javadoc -d docTargetDir package1, package2, ...
    javadoc -d docTargetDir *.java

    -d 指定了生成html文档的目录
    -author, -version 增加@author和@version的标记(默认情况忽略)

    javadoc -link http://java.sun.com/j2se/5.0/docs/api *.java
        为标准类添加链接
        



