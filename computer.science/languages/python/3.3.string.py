
字符串是不可变的

常量方法基本都适用于字符串
    索引，分片， 判断成员，乘法，长度，最小/大值


格式化字符串:
    format = 'Hello %d %s!'
    name = 1, 'Ray'
    print format % name # c fmt % (c params)
                        # string % tuple

    如果format中有%, 需要使用%%来替代

    浮点数
    %f, %.3f
    %10.2f # 字符串宽度为10，精度为2。如果长度不够，左边补空格

    # 字符串的精度就是其长度
    print "You name is : %.5s" % "Jiang Rui"
    # output: : Jiang 

    # 精度可从参数中获取
    print "You name is : %.*s" % (5, "Jiang Rui")
    # output: : Jiang 
    
字符串方法

    查找子串: find
        greeting = "Hello new python world!"
        print greeting.find("new")
        # output: 6

        print greeting.find("perl")
        # output: -1

        # in 操作符也能实现find功能，但是仅仅返回True/False

        print greeting.find("perl", 2) # 提供起点
        print greeting.find("perl", 2, 6) # 提供起点, 结束点

    连结列表为字符串: join
        num = ["1", "1", "1", "1", "1"]
        add = "+"
        print add.join(num);
        # output: 1+1+1+1+1

    拆分字符串为列表: split
        greeting = "Hello new python world!"
        print greeting.split(' ')
        print greeting.split() # 将空格，制表，换行等白字符作为分隔符
        # output: ['Hello', 'new', 'python', 'world!']

    转化字符串为小写: lower
        greeting = "Hello new python world!"
        print greeting.lower()
        # output: hello new python world!

    title:
        greeting = "Hello new python world!"
        print greeting.lower()
        # output: Hello New Python World!
 
    替换字串: replace
        greeting = "Hello new python world!"
        print greeting.replace("new", "old")
        # output: Hello old python world!
    
    去掉前导、后缀字符:strip:
        greeting = "     Hello new python world!       "
        print greeting.strip() 
        # output: "Hello new python world!"

        greeting = "**     Hello new python world!  &&& "
        print greeting.strip(" *&") 
        # output: "Hello new python world!"

    翻译: translate
        from string import maketrans
        table = maketrans("cs", "kz") # c-->k, s-->z
        print "This is a incredible test".translate(table)
        # output: Thiz iz a inkredible tezt

        print "This is a incredible test".translate(table, ' ') # 删除空格
        # output: Thizizainkredibletezt

