
构造方法

class Foobar:
    def __init__(self):
        self.value = 0

    def __init__(self, value=42):
        self.value = value

x = Foobar()
print x.value

y = Foobar("test")
print y.value


class Bird:
    def __init__(self):
        self.hungry = True

    def eat(self):
        if self.hungry:
            print "eat..."
            self.hungry = False
        else:                
            print "No, thanks!"

class SongBird:
    def __init__(self):
        # Bird.__init__(self)
        # super(SongBird, self).__init__()
        self.sound = "Squawk!"

    def sing(self):
        print "Bird %s!!!" % self.sound

sb = SongBird()
sb.sing()
sb.eat()

__len__(self)
__getitem__(self, key)
__setitem__(self, key, value)
__delitem__(self, key)

class ArithmeticSequence:
    def __init__(self, start=0, step=1):
        self.start = start
        self.step = step
        self.changed = {}

静态函数，类函数
class MyClass:
    @staticmethod
    def smethod():
        print "This is a static method"
    # smethod = staticmethod(smethod)

    @classmethod
    def cmethod(cls):
        print "This is a class method, ", cls
    # cmethod = classmethod(cmethod)

class Rectangle:
    def __init__(self):
        self.width = 0
        self.heigth = 0

    def __setattr__(self, name, value):
        if name == 'size':
            self.width = self.heigth = value

    def __getattr__(self, name):
        if name == 'size':
            return self.width, self.heigth
        else:
            raise AttributeError

r = Rectangle()
r.size = 20
print r.size

迭代器

__iter__()返回一个iterator
    iterator有next()方法
    如果next()没有找到下一个元素，会抛出StopIterationError

class Fibs:
    def __init__(self):
        self.a = 0
        self.b = 1

    def next(self):
        self.a, self.b = self.b, self.a + self.b

    def __iter__(self):
        return self

fibs = Fibs()
for f in fibs:
    if f > 1000:
        print f
        break

it = iter([1, "test", 3])
print it
it.next()
print it

print list(it)

生成器
任何包括yield的函数称为生成器

nested = [[1,2] 3, [4,5,6]]

def flatten(n):
    for i in n:
        for j in i:
            yield j

for i in flatten(nested)
    print i

print list(flatten(nested))

# TODO



