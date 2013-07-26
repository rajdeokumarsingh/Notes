#!/usr/bin/python

edward = ["Edward Gumby", 42]
rui = ["Rui Jiang", 34]
feng = ["Feng Xiao", 31]
database = [edward, rui, feng]

print len(edward)
print edward[0]
print database[-1][-1]
print database

tag = '0123456789'
print tag[3:9]
print tag[-3:]
print tag[1:10:2]

print [1,2,3] + [0,4,9]

print 'python'*3

perm = 'rw'
print 'r' in perm

if ['Rui Jiang', 34] in database: print 'Access granted'

string = 'hello,world!'
print len(string), min(string), max(string)

print max(34,43,1234,44,4909)
print min(34,43,1234,44,4909)

list('hello world!')

