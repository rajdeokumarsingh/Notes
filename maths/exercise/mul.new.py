#!/usr/bin/python

import random
import math
import sys
import string

def randomNdigits(n):
    'Return an n-digit random number'

    x = math.pow(10, n-1);
    # return [x, x * 10)
    return random.random() * 9 * x + x 

# default to multiple two-digit number to one-digit number
d1 = 2
d2 = 1

arg_len = len(sys.argv)
if(arg_len >= 2):
    d1 = string.atoi(sys.argv[1])
if(arg_len >= 3):
    d2 = string.atoi(sys.argv[2])

result = 0
correct = 0
error = 0
tab = "\t\t\t\t"
errors = {}
for i in range(30):
    x = randomNdigits(d1)
    x = int(math.floor(x))
    y = randomNdigits(d2)
    y = int(math.floor(y))

    print "\t", tab, x, y 
    result = input(tab + "result: ");
    # print result
    if(result == x * y):
        correct += 1;
        print tab + "bingo!"
    else:
        error += 1;
        errors[str(i)] = str(x) + "," + str(y) + " = " + str(result) 
        print "error"

print "correct: ", correct
print "error: ", error
if(error != 0):
    print errors

