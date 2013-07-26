#!/usr/bin/python

format = 'Hello %d %s!'
name = 1, 'Ray'

print format % name

print "You name is : %5s" % "Jiang Rui"
print "You name is : %.5s" % "Jiang Rui"
print "You name is : %.*s" % (5, "Jiang Rui")

greeting = "Hello new python world!"
print greeting.find("new")
print greeting.find("perl")

num = ["1", "1", "1", "1", "1"]
add = "+"
print add.join(num)
print '/'.join(num)
print greeting.lower()
print greeting.title()

print greeting.replace("new", "old")
print greeting.split(' ')


g = "**     new python!  &&& "
print g.strip("*&")
print g.strip(" *&")


from string import maketrans
table = maketrans("cs", "kz")
print "This is a incredible test".translate(table, ' ')



