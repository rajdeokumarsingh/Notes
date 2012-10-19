基本概念 {
    变量， 函数， 模块(module)

    运算符
        // 整除符号
            1.0 // 2.0 = 0.0

        ** 乘方
    常量
        长整数 1000000000000000000000L
        16进制 0xAF
        8进制 010

    变量
        不用声明
        x = 3
}

函数 {
    获取用户输入:
        x = input("x: ") # 获取合法的python表达式
        print x

        name = raw_input("what is your name:") # 将输入转化为字符串
        print name, "!"

    pow(2,3)  = 8      # 乘方
    round(1.0/2.0) = 1 # 四舍五入

    int(math.floor(32.9)) = 32 # 取整函数
}

模块 {
    类似于java的包概念

    import math
    math.floor(32.9) = 32.0
    math.ceil(32.9) = 33.0

    from math import sqrt # 导入后忽略模块名
    sqrt(9) = 3.0
}

合法的python表达式 {
    字符串， 使用""或‘’括起来的串
        如果没有用""/''括起来，就不合法
        "Hello world!" # 合法
        'Hello world!' # 合法
        Hello world!   # 非法

    数字
}

字符串 {
    使用""或''括起来

    拼接字符串

    str("Hello, world")     # Hello, world, 打印用户可以理解的值
    repr("Hello, world")    # 'Hello, world' , 将结果字符串转化为合法的python表达式字符串

    str(1000000L)           # 1000000L
    repr(1000000L)          # 10000L

    # 忽略换行
    print "Hello \
    world!"

    长字符串
        使用三个'或"括起来
        print '''This is a very long
            long 
            long
            long
            string'''
'''
    转义
        print "Hello\nworld!"   # 转义n, 换行
        print "C:\nowhere"      # 转义n, 换行
        print "C:\\nowhere"     # 转义\, 不换行

        print r"C:\nowhere"     # r表示原始字符串，不换行
                                # 不能在原始字符串尾部输入\
    unicode字符串
        一个unicode字符有16bit
        普通的字符为8bit
}


基本数据结构 {
    序列 sequence {
        序号, index, 从0开始
        最后一个元素序号为-1, 倒数第二个为-2

        分类 {
            列表 {
                可以修改
            }

            元组 {
                不可修改 
                    类似于const的作用
            }

            字符串
            Unicode字符串
            buffer对象
            xrange对象
        }

        定义序列:
            edward = ["Edward Gumby", 42]
            rui = ["Rui Jiang", 34]
            feng = ["Feng Xiao", 31]
            database = [edward, rui, feng]
            print database

        基本操作 {
            索引
                greeting = "Hello"
                print greeting[0]

                print edward[0]
                print database[-1][-1]
                print database

            分片
                tag = '0123456789'
                print tag[3:9]      # 输出345678, [3,9)
                print tag[-3:]      # 输出倒数3个数据
                print tag[:3]       # 输出前3个数据
                print tag[0::2]     # 输出前3个数据

                print tag[1:10:2]   # 输出 13579, 步长为2
                print tag[10:0:-2]   #

            序列相加
                print [1,2,3] + [0,4,9]

            相乘
                print 'python' * 3 # 输出pythonpythonpython
                [42] * 10 # [42,42,42,42,42,42,42,42,42,42]
        }
    }

p49
