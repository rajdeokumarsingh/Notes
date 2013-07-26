#!/usr/bin/python

try:
    x = input('please enter a number:')
    y = input('please enter a number:')
    print x / y
except Exception, e:
    print "input error:", repr(e)
else:
    print "Everything is OK!"
finally:
    print "Everything is done!"



