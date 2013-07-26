序列 sequence {
    序号, index, 从0开始
    最后一个元素序号为-1, 倒数第二个为-2
    获取长度len(seq)

    分类 {
        列表 {
            可以修改
        }

        元组 {
            不可修改 
                类似于const的作用
        }

        字符串
        Unicode字符串
        buffer对象
        xrange对象
    }

    定义序列:
        edward = ["Edward Gumby", 42]
        rui = ["Rui Jiang", 34]
        feng = ["Feng Xiao", 31]
        database = [edward, rui, feng]
        print database

    基本操作 {
        索引
            greeting = "Hello"
            print greeting[0]

            print edward[0]
            print database[-1][-1]
            print database

        分片
            tag = '0123456789'
            print tag[3:9]      # 输出345678, [3,9)
            print tag[-3:]      # 输出倒数3个数据
            print tag[:3]       # 输出前3个数据
            print tag[0::2]     # 输出前3个数据

            print tag[1:10:2]   # 输出 13579, 步长为2
            print tag[10:0:-2]   #

        序列相加
            print [1,2,3] + [0,4,9]

        相乘
            print 'python' * 3 # 输出pythonpythonpython
            [42] * 10 # [42,42,42,42,42,42,42,42,42,42]

        包含
            # 字符串包含
            perm = 'rw'
            print 'r' in perm

            # 序列包含
            edward = ["Edward Gumby", 42]
            rui = ["Rui Jiang", 34]
            feng = ["Feng Xiao", 31]
            database = [edward, rui, feng]
            if ['Rui Jiang', 34] in database: print 'Access granted'

        获取长度
            string = 'hello world!'
            print len(string)

        最大值，最小值
            print min(string), max(string) # 返回序列中的最小值（空格），和最大值('w')

            print max(34,43,1234,44,4909)
            print min(34,43,1234,44,4909)
    }

