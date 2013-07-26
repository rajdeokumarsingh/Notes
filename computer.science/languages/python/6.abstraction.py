函数

查询对象是否是函数:
    import math
    x = 1 
    y = math.sqrt

    print callable(x) # False
    print callable(y) # True

    # FIXME: not support by 2.6.5
    print hasattr(x, __call__) # False
    print hasattr(y, __call__) # True

函数定义
    def greeting(name):
        return "Hello, %s!" % name

    def fibs(num):
        ret = [0, 1]
        for i in range(num-2):
            ret.append(ret[-2] + ret[-1])

        return ret

函数文档
    def square(x):
        'This is a comment for this function'
        return x*x

    print square.__doc__

    >>>help(square) # 会显示类似man的帮助信息

函数参数
    传值还是引用, 还是指针
        对于primitive，传值
        字符串, 引用
        对象, 引用

    def testPrimitive(x):
        x = 20

    integer = 30;
    testPrimitive(integer)
    print integer # 30

    def test(x):
        x = "test"

    name = "example"
    test(name) 
    print name # 'example'


    def testList(l):
        del l[0]

    params = [1, 2, 3]
    testList(params[:])     # 复制了列表
    print params            # [1, 2, 3]

    testList(params)        # [2, 3]
    print params

    关键字参数
    def func1(name, addr):
        print "%s lives in %s" % (name, addr)

    func1("Rui", "Wang Jing")
    func1(name="Rui", addr="Wang Jing")
    func1(addr="Wang Jing", name="Rui")

    默认参数
    def func1(name="Rui", addr="Wang Jing"):
        print "%s lives in %s" % (name, addr)

    func1();
    func1("Feng");
    func1(addr="Hai Dian");

    变长参数
    def print_params(*args):
        print args

    print_params(1,2,3) # tuple (1, 2, 3)

    定长参数+变长元组参数
    def print_params(test, *args):
        print test
        print args

    print_params(1,2,3) # 1 (2, 3)
    print_params(1) # 1 ()

    def print_params2(x, y, z):
        print x, y, z 

    t = (1, 2, 3)
    print_params2(*t)
    l = [1, 2, 3]
    print_params2(*l)

    变长字典参数
    def print_params(**args):
        print args

    print_params(x=1,y=2,z=3)       # {'x':1, 'y':2, 'z':3}
    # # print_params(1,2,3)       # FIXME: error

    def print_params2(x, y, z):
        print x, y, z

    d = {'x':1, 'y':2, 'z':3}
    print_params2(**d)      # 1 2 3

    dd = {'x':1, 'w':2, 'z':3}
    print_params2(**dd)      
    # FIXME: error
    # print_params2(**dd) TypeError: print_params2() got an unexpected keyword argument 'w'


    # 组合使用各种参数
    def print_params(x, y, z=3, *tpargs, **dictargs):
        print x, y, z
        print tpargs
        print dictargs

    print_params(1,3,4,2,4, x=2, y=3) 
    # FIXME: error
    # print_params(1,3,4,2,4, x=2, y=3) 
    # TypeError: print_params() got multiple values for keyword argument 'x'

    print_params(1,3,4,2,4, p=2, q=3)
    # 1 3 4
    # (2, 4)
    # {'q': 3, 'p': 2}


    参数解包
    def add(x, y):
        return x + y

    params = (1, 2)
    print add(*params) # 将params tuple分解成1,2

    params2 = {'x':1, 'y':2}
    print add(**params2) # 将params dictionary分解成1,2

    #TODO: 变长参数
    # 对于函数定义时添加*, **表示对输入的多个参数会组装成tuple或dictionary
    # 对于函数调用时添加*, **表示对输入的tuple或dictionary, 将会解包成单个参数

作用域
    变量是什么:
        变量名-变量值pair

    作用域
        相当于一本字典, 字典中包括很多变量
        当你定义新的变量时，字典中就会增加一个变量名和变量值的pair(值可能是None)
        当你删除变量时，字典中就会去掉这个pair
        你可以修改变量的值

        有一本默认的字典

        你可以定义新的字典

        分类
            全局作用域
                globals()

                文件作用域??

                x = 1
                scope = vars();
                print scope['x'];
                scope['x'] += 1
                print scope['x'];

                print scope
                    {
                        '__builtins__': <module '__builtin__' (built-in)>
                        '__file__': './6.abstraction.py'
                        '__package__': None
                        'scope': {...}
                        'x': 2
                        '__name__': '__main__'
                        '__doc__': None
                    }


            函数作用域
                局部变量
                函数内部定义的变量不会影响全局变量
                同名的局部变量会屏蔽全局变量
                    x = 1
                    y = 'yyyy'
                    def test():
                        x = 2
                        print x
                        print y

                    test()
                    print x

                在函数内部可使用globals()来访问全局变量
                    gx = "global var"
                    def test():
                        gx = "local var"
                        print "local gx: %s, global gx: %s" % gx, globals()['gx'] # FIXME: error
                        print "local gx: %s, global gx: %s" % (gx, globals()['gx']) # FIXME: ok

                在函数内部定义全局变量
                    x = 1
                    def test():
                        global x
                        x += 1

                    test()
                    print x

    子函数:
        def mul1(x):
            def mul2(y):
                return x*y
            return mul2        

        d = mul1(5)
        print d(2)

递归 recursion
    计算n!
    def factorial(n):
        if 1 == n:
            return 1
        else:
            return n * factorial(n-1)

函数其他使用
    map()
        map(str, range(10)) # 等价于
        [str(x) for x in range(10)]


    过滤方法:
        def func(x):
            return x.isalnum()
        seq = ["test", "x41", '!34!*']
        print filter(seq, func)

        filter(lambda x: x.isalnum(), seq)




