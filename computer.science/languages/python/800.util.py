
from random import choice
x = choice([1, 2, 'x', ["te", 'st']])

import random
testlist = [1,3,4,5]
a,b = 1,5
random.random()         # 生成0至1之间的随机浮点数，结果大于等于0.0，小于1.0
random.randint(a,b)     # 生成1至5之间的随机整数，结果大于等于1，小于等于5，a必须小于等于b
random.choice(testlist) # 从testlist中随机挑选一个数，也可以是元组、字符串
