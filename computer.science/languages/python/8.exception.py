抛出异常
raise Exception
raise Exception('Engine overload!')

import exceptions
dir(exceptions)


自定义的异常类

class MyException(Exception):
    pass

捕获异常
try:
    x = input('please enter a number:')
    y = input('please enter a number:')
    print x / y
except ZeroDivisionError:
    print "zero drivision error"

except (ZeroDivisionError, TypeError, NameError):
    print "error"

except (ZeroDivisionError, TypeError, NameError), e:
    print e

except:  # 捕获所有异常
except Exception, e:  # 捕获所有异常
    print "input error:", repr(e)
    # input error: ZeroDivisionError('integer division or modulo by zero',)

else:
    print "Everything is OK!"

finally:
    print "Everything is done!"



