
# 注意if, for, def后的冒号
if a < 0                    # FIXME
    print a

def init(self)              # FIXME
    Person.number += 1 # 

for key in widgetInfo.keys() # FIXME
    print key, ':', widgetInfo.get(key)


# 注意if, for, def后面语句的缩进
if a < 0:   
print a     # FIXME: error

if a < 0:   
    print a     # Good!

# 格式化字符串时，多个参数需要使用元组
print "param1 %s, param2 %s" % 'test1', 'test2' # FIXME: error
print "param1 %s, param2 %s" % ('test1', 'test2') # Good!


# 由于类的属性是在函数中动态添加的，需要保证函数调用的先后数序
# 需要保证被访问的属性先被创建
class Filter:
    def init(self):
        self.block = []

    def filter(self, seq):
        return [x for x in seq if x not in self.block]

class SpamFilter(Filter):
    def init(self):
        self.block = ['fuck', 'shit']

f = SpamFilter()
# f.init()          #FIXME:
print f.filter(['bull', 'shit', 'fuck', 'you'])

    # FIXME: 报错
    Traceback (most recent call last):
    File "./7.class.py", line 66, in <module>
    print f.filter(['bull', 'shit', 'fuck', 'you'])
    File "./7.class.py", line 57, in filter
    return [x for x in seq if x not in self.block]
    AttributeError: SpamFilter instance has no attribute 'block'



