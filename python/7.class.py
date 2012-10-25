创建类
    class Person:
        name = 'default name'       # 默认的属性是公有的

        __privatevar = "private"    # __为前缀的属性和方法是私有的
                                    # 类的内部该属性变成了_Person__privatevar
                                    # 类的外部可通过 p._Person__privatevar来访问, 但不推荐这种方法

        _private        # 带下划线的变量不会被带星号的import语句导入
                        # from math import *

        number = 0  # 定义全局变量
        # FIXME: 上面定义的变量全部是static的
        
        def init(self)
            Person.number += 1 # 访问全局变量

        def setName(self, name):
            self.name = name

        def getName(self):
            return self.name

        def greet(self):
            print "Hello world, I am %s!" % self.name

        def __privateGreet(self):
            print "private, I am %s!" % self.name

        #def greet():
           # print "Hello world, I am %s!" % self.name
            
使用类
    p1 = Person()
    p1.init()
    p1.setName("Rui") # 自动会将self作为第一个参数传入
    print p1.getName()
    p1.greet()

    p2 = Person()
    p2.init()
    p1.setName("Rui") # 自动会将self作为第一个参数传入
    p2.setName("Feng")
    print p2.getName()
    p2.greet()

    print Person.number # 2
    print p1.number     # 2
    print p2.number     # 2

    p1.number = 500
    print p1.number  # 500
    print p2.number  # 2

继承
    class Filter:
        def init(self):
            self.block = []

        def filter(self, seq):
            return [x for x in seq if x not in self.block]


    class SpamFilter:
        def init(self):  # 覆盖父类方法
            self.block = ['fuck', 'shit']


    f = SpamFilter()
    print f.filter(['bull', 'shit', 'fuck', 'you'])

查询类型信息
    print issubclass(SpamFilter, Filter) # True
    print issubclass(Filter, SpamFilter) # False

    print SpamFilter.__bases__
    print Filter.__bases__

    sf = SpamFilter()
    print isinstance(sf, SpamFilter) # True
    print isinstance(sf, Filter) # True
    print isinstance(sf, str) # True
    print sf.__class__

多重继承
    class Calculator:
        def calculate(self, expr):
            self.value = eval(expr)

    class Talker:
        def talk(self):
            print "my value is:", self.value


    class TalkCalculater(Calculator, Talker):
        pass


tc = TalkCalculater()
tc.calculate("1+2*3")
tc.talk()

方法查询
print hasattr(tc, 'talk')
print hasattr(tc, 'calculate')
print hasattr(tc, 'calc')

print callable(getattr(tc, 'talk', None))
print hasattr(getattr(tc, 'talk'), '__call__')

setattr(tc, 'name', 'Jiang Rui')
print tc.__dict__ # {'name': 'Jiang Rui', 'value': 7}


