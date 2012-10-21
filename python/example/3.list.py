#!/usr/bin/python


slist = list('hello world!')
slist.append("new world")
slist[0] = 'H'

print slist
del slist[-1]
print slist

#slist[6:] = list("good world!")
slist[6:6] = list('good ')
print slist

numbers = [1, 5]
numbers[1:1] = [2, 3, 4]
numbers[0:0] = [0]
print numbers
numbers[1:5] = []
print numbers
numbers.append(9)
print numbers
numbers.append([10, 100, 999, 9])
numbers += [10, 100, 9, 99]
print numbers

print numbers.count(9)
del numbers[1:]
print numbers
numbers.extend([201, 202, 202, 203])
print numbers
print numbers.index(202)

numbers = [0, 1, 2, 3, 5, 6]
numbers.insert(4, 4)
print numbers

print numbers.pop()
print numbers

numbers = [0, 201, 202, 2099, 203, 202]
numbers.remove(202) 
print numbers
numbers.reverse()
print numbers
numbers.sort()
print numbers

num = numbers
print num

print '----------------'
x = [98, 2, 493934, 0, 201, 2099, 203, 202]
y = x
y.sort(reverse=True);
print x






