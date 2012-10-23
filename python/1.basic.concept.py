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

        对于对象变量，其行为和java一样。可将对象看成是内存区域的引用

            # example 1
            x = {}
            y = x
            x['key'] = 'value'
            print y # {'key': 'value'}
            x = {}  # refer to a new object
            print y # {'key': 'value'}, refer to same object

            # example 2
            x = {}
            y = x
            x['key'] = 'value'
            print y # {'key': 'value'}
            x.clear() 
            print y # {}

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

获取帮助
# python
>>>help(repr)



