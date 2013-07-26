什么是content provider
    表示应用程序的数据
    提供安全机制

    可为其他进程提供访问接口

content provider的组成要素
    Content URIs
        content provider的名字

        example:
            content://user_dictionary/words

    ContentResolver 
    {
        用于访问content provider 
        提供query, insert, update, delete等等方法
            provide the basic "CRUD" (create, retrieve, update, and delete) functions

        两个要素:
            URI
                应用程序content provider的URI

            ContentValues
                保存一组数据库column-value值

    }

