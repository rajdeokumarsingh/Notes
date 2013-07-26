print sf.__class__

print SpamFilter.__bases__

print issubclass(SpamFilter, Filter) # True
print issubclass(Filter, SpamFilter) # False

print isinstance(tc, SpamFilter)
 

print globals()
__main__.TalkCalculater
{'p2': <__main__.Person instance at 0x7f8c2ff2dab8>, 'p1': <__main__.Person instance at 0x7f8c2ff2da70>, 'f': <__main__.SpamFilter instance at 0x7f8c2ff2db00>, '__builtins__': <module '__builtin__' (built-in)>, 'Calculator': <class __main__.Calculator at 0x7f8c2ff11e90>, '__file__': './7.class.py', 'tc': <__main__.TalkCalculater instance at 0x7f8c2ff2db90>, '__package__': None, 'Filter': <class __main__.Filter at 0x7f8c2ff11d10>, 'Person': <class __main__.Person at 0x7f8c2ff11e30>, 'SpamFilter': <class __main__.SpamFilter at 0x7f8c2ff11dd0>, 'Talker': <class __main__.Talker at 0x7f8c2ff11ef0>, 'TalkCalculater': <class __main__.TalkCalculater at 0x7f8c2ff11f50>, '__name__': '__main__', 'sf': <__main__.SpamFilter instance at 0x7f8c2ff2db48>, '__doc__': None}

print vars()

# 查看包中的信息
import math
print dir(math)
['__doc__', '__name__', '__package__', 'acos', 'acosh', 'asin', 'asinh', 'atan', 'atan2', 'atanh', 'ceil', 'copysign', 'cos', 'cosh', 'degrees', 'e', 'exp', 'fabs', 'factorial', 'floor', 'fmod', 'frexp', 'fsum', 'hypot', 'isinf', 'isnan', 'ldexp', 'log', 'log10', 'log1p', 'modf', 'pi', 'pow', 'radians', 'sin', 'sinh', 'sqrt', 'tan', 'tanh', 'trunc']
help(sin)



