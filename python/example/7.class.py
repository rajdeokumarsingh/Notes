#!/usr/bin/python

class Person:
    name = 'default name'
    __privatevar = "private"
    _private = 0
    number = 0
    
    def init(self):
        Person.number += 1 # 

    def setName(self, name):
        self.name = name

    def getName(self):
        return self.name

    def greet(self):
        print "Hello world, I am %s!" % self.name

    def __privateGreet(self):
        print "private, I am %s!" % self.name

p1 = Person()
p1.init()
p1.setName("Rui")
print p1.getName()
p1.greet()
p1._Person__privateGreet()


p2 = Person()
p2.init()
p1.setName("Rui") # self
p2.setName("Feng")
print p2.getName()
p2.greet()

print Person.number
print p1.number
print p2.number

p1.number = 500
print p1.number
print p2.number

print 
print
print


class Filter:
    def init(self):
        self.block = []

    def filter(self, seq):
        return [x for x in seq if x not in self.block]

class SpamFilter(Filter):
    def init(self):
        self.block = ['fuck', 'shit']


f = SpamFilter()
f.init()
print f.filter(['bull', 'shit', 'fuck', 'you'])
print

print issubclass(SpamFilter, Filter) # True
print issubclass(Filter, SpamFilter) # False


print SpamFilter.__bases__
print Filter.__bases__

print
sf = SpamFilter()
print isinstance(sf, SpamFilter) # True
print isinstance(sf, Filter) # True
print isinstance(sf, str) # False
print sf.__class__

print
print
print

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

print
print
print

print hasattr(tc, 'talk')
print hasattr(tc, 'calculate')
print hasattr(tc, 'calc')
print callable(getattr(tc, 'talk', None))
print hasattr(getattr(tc, 'talk'), '__call__')


setattr(tc, 'name', 'Jiang Rui')
print tc.__dict__


print
print
print

print TalkCalculater
print globals()
print
print
print


print vars()
#
#
#
#
