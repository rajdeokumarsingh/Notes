#!/usr/bin/python

import random
import math

result = 0
correct = 0
error = 0
tab = "\t\t\t\t"
errors = {}
for i in range(30):
    # multiple two-digit to one-digit
    x = random.random() * 90 + 10 # [10, 100)
    x = int(math.floor(x))
    y = random.random() * 9 + 1  # [1, 10)
    y = int(math.floor(y))

    print "\t", tab, x, y 
    result = input(tab + "result: ");
    # print result
    if(result == x + y):
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


