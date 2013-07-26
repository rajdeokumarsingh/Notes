print  expr1, expr2, ...

导入模块
    import module
    from module import func
    from module import func1, func2, ...
    from module import * # 导入所有函数

    # 使用不同模块的同名函数
    module1.open()
    module2.open()

    # 为模块起别名
    import math as foobar
    print foobar.sqrt(9.0)

    # 为函数起别名
    from math import sqrt as foobar
    print foobar(9.0)

赋值 
    序列解包
    x, y, z = 1, 2, 3
    print x, y, z # 1 2 3

    x, y = y, x # swap x, y

    values = 1, 2, 3
    print values # (1, 2, 3)

    x, y, z = values
    print x # 1                         # FIXME: values的len必须和左边的变量数量一致

    # 链式赋值
    x = y = func1() 

    # augmented assignment
    x += 1
    x *= 2

语句块
    条件语句，循环语句后的逻辑需要添加缩进
    tab和四个空格都可以

    冒号表示语句块的开始
        块中每个语句的缩进都是相同的

条件语句
    布尔值

    下面值在if语句中都会被判定为假
        False, None, 0,(0.0, 0L,...) , '', "", (), [], {}
        # 可以解释为 有些东西，没有东西

    除了上述值，其他值都为真
        print True == 1 # True 
        print False == 0 # True

        # FIXME:
        print True == [1, 2, 3] # false
        print False == []  # false

        # 输出empty
        l = []
        if(l):
            print 'not empty'
        else:
            print 'empty'


    bool()函数可用来返回表达式的bool值
        bool("This is a sentence") # True
        bool(42) # True
        # FIXME: 注意,python使用时会自动转化这些值

    # if/else/elif语句
    if name.endswith('i'):
        print "Jiang"
    elif name.endswith('x'):
        print "Xiao"
    else:
        print "Ye"

    运算符
        ==, !=, <, >, >=, <=, 

        x is y          # 同一个对象
        x is not y      # 不是同一个对象
        x in y          # x在容器y中
        x not in y      # x不在容器y中
                                                    # FIXME: 对于primitive类型， "==" 等价于 "is"
                                                    x = 1
                                                    y = 1
                                                    z = x
                                                    print "x == y", x == y # True
                                                    print "x is y", x is y # True
                                                    print "x is z", x is z # True

                                                    # FIXME: 对于对象类型， "==" 不等价于 "is"
                                                    w = [1, 2, 3]
                                                    p = [1, 2, 3]
                                                    q = w
                                                    print "w == p", w == p # True
                                                    print "w is p", w is p # False
                                                    print "w is q", w is q # True

        0<age<100 # 可以的！

        # FIXME:
        == 比较的两个primitive和对象的值            # java中equals()
        is 比较的似乎两个对象的指针                 # java中==
                                                    # FIXME: 避免使用is来比较primitive和字符串

        字符串比较
        print "ab" < "ac"                   # True, 按照字母表顺序来比较的

        序列比较
        print [2, 1] > [1, 2]               # True, 比较方式和字符串一致
        print [2, [1, 3]] > [2, [2, 1]]     # False

        布尔运算符
        and, or, not

        if num <= 10 and number >= 1:
            print 'Good'
        else:
            print 'Wrong'

    问号表达式
    r1 if cond else r2  # 等价于 cond?r1:r2


    断言
    assert a < 0        # 否则退出程序

循环
    while循环

        x = 1
        r = 0
        while x <= 100:
            r += x
            x += 1  # FIXME: 注意python没有++运算符

        print "1+2+...+100 = [%d]" % r

    for循环
        words = ['This', 'is', 'an', 'example']
        for w in words:
            print w


        range()函数
            range(10) # return [0, 1, 2, ..., 9]
            range(0, 10) # return [0, 1, 2, ..., 9]
            range(0, 10, 2) # return [0, 2, 4, ..., 8]

            range(10, 0, -1) # FIXME: return reverse([1, 2, ..., 10])

            for i in range(1, 101):      # print 1, 2, ...., 100
                print i 

        # 遍历字典
        pb = {
            "Rui":"150 1100 5932", 
            "Phonex":"138 1166 6985", 
            "Mother":"132 0713 1873"
        }
        for key in pb:
            print key, "value:", pb[key]

        for key, value in pb.items()
            print key, "value:", value

# 迭代技巧
    words = ['This', 'is', 'an', 'example']
    weight = [4, 2, 2, 7]
    # FIXME: good!
    # weight = [ len(x) for x in words]

    for i in range(len(words)):
        print words[i]
        print weight[i]

    zip(words, weight) # [('This', 4), ...]

    for wo, we in zip(words, weight):
        print wo, we

    # 通过enumerate返回index-value对
    words = ['This', 'is', 'an', 'example']
    for i, w in enumerate(words):
        if 'an' in w:
            words[i] = 'AN';

    排序和翻转
        # 返回一个排序/翻转的序列。原来的序列不改变
        sorted()
        reversed()

        words = ['This', 'is', 'an', 'example']
        print sorted(words)
        print sorted("This is an example")          # ['a', 'e', 'l']
        print reverse("This is an example")         # 返回迭代对象，需要用list(reversed())来变成列表
        ''.joint(sorted("This is an example"))

    循环控制
        break
        continue

        while True:
            word = raw_input("please enter a word");
            if not word: break
            print "The word is:", word


        # for/else语句
        for i in range(100):
            if(i == 20):
                print i
                break
        else:
            print "not find the number"             # 如果没有break, 就会输出该语句

    列表推导式
        1. 对于类表中的每个元素都可做一个操作
        2. 可以进行多重循环
    [x*x for x in range(10)]                                # [0, 1, 4, 9, ..., 81]
    [x*x for x in range(10) if x % 3 == 0]                  # [0, 9, 36, 81], 运算后值满足条件的
    [(x, y) for x in range(3) for y in range(3)]            # x, y的全排列

    map(str, range(10)) # 等价于
    [str(x) for x in range(10)]


    # FIXME: 没有元组推导式

    空语句，占位符
    # FIXME: python不允许出现if后的空语句
    if somecond:
        # do nothing
        pass

    del 删除引用(非删除内存)
        words = ['This', 'is', 'an', 'example']
        words = None    # None类似于null, NULL 
                        # 对象变为None后, python会自动释放内存, 垃圾搜集

        # FIXME: 可以认为del会删除引用的名字, 该引用指向的对象会被垃圾回收
        x = 1
        del x
        print x         # 这里会报错

        x = ['This', 'is', 'an', 'example']
        y = x
        print y
        del x       # FIXME: 这里仅仅删除了x的名字, 列表对象由于还有引用y存在，不会被删除。
        print y

    执行代码求值
        exec "print 'Hello world!'"

        # 计算python表达式
        eval(raw_input("please input a expression: "))
        # 输入 4*2+3
        # 11


