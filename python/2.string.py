字符串 {
    使用""或''括起来

    拼接字符串

    str("Hello, world")     # Hello, world, 打印用户可以理解的值
    repr("Hello, world")    # 'Hello, world' , 将结果字符串转化为合法的python表达式字符串

    str(10L)           # 10
    repr(10L)          # 10L

    # 忽略换行
    print "Hello \
    world!"

    长字符串
        使用三个'或"括起来
        print '''This is a very long long long long
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



