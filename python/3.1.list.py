基本操作
1. 元素赋值
slist[0] = 'H'

2. 删除元素

del slist[-1] # 删除了'!'
print slist

3. 分片
赋值
    slist[6:] = list("good world!") # 将'world'变成'goold world!'

插入
    numbers = [0, 4]
    numbers[1:1] = [1, 2, 3] # 插入序列到index为1的元素之前
    print numbers # [0, 1, 2, 3, 4]

删除    
   numbers = [0, 1, 2, 3, 4, 5]
   numbers[1:5] = []
   print numbers # [0, 5] , 删除了1~4

4 函数
    append(object) # 添加一个元素
                   # 使用'+'来添加多个元素
        number = [0, 5]
        number.append(9)
        number.append([10, 100, 999]) # [10, 100, 999]仅仅作为一个元素
        number += [10, 100, 999]
        print number # [0, 5, 9, [10, 100, 999], 10, 100, 99]

    extend(list) # 附加多个元素
        numbers = [0]
        numbers.extend([201, 202, 203])
        print numbers # [0, 201, 202, 203]

    insert(index, object) # 在index之前插入object
        numbers = [0, 1, 2, 3, 5, 6]
        numbers.insert(4, 4)
        print numbers

    pop() # 去掉最后的元素, 并返回
        numbers = [0, 1, 2, 3, 5, 6]
        print numbers.pop()
        print numbers

        # 注意, append()是pop的逆操作
        numbers.append(numbers.pop()) # numbers不变

    index(object) # 返回第一个index
        numbers = [0, 201, 202, 203, 202]
        print numbers.index(202) # 2

    count(object) # 统计变量次数
        numbers = [0, 5, 9, [10, 100, 999, 9], 10, 100, 9, 99]
        numbers.count(9) # 2
        numbers.count('9') # 0

    remove(object) # 删除第一个匹配的object
        numbers = [0, 201, 202, 203, 202]
        numbers.remove(202)
        # [0, 201, 203, 202]

    reverse() # 将列表反向存放
        numbers = [0, 201, 2099, 203, 202]
        numbers.reverse()
        print numbers # [202, 203, 2099, 201, 0]

    sort()
        numbers = [0, 201, 2099, 203, 202]
        numbers.sort()
        print numbers # [0, 201, 202, 203, 2099]

        sorted() # 不改变原来的列表，但是返回排序后的列表
        sorted().reverse() # 反向排序

        sort(key=len)
        sort(reverse=True)

    赋值
        # 引用赋值
        x = [0, 201, 2099, 203, 202]
        y = x
        y.sort();
        print x # 由于x, y都指向同一个列表，对y的操作对x也生效
        
        # 复制列表
        x = [0, 201, 2099, 203, 202]
        y = x[:] # 复制列表
        y.sort();
        print x # 对y的操作不影响x
 
    将字符串转化为列表
    slist = list('hello world!')

列表类数据结构的记忆框架
{
    创建/销毁
        tag = [0, 1, 2, 3, 4, 5]
        name = ["Edward Gumby", 42]

    查询/搜索 const
        单个元素/多个元素
            print tag[0]
            print tag[-1]

            print tag[3:5]
            print tag[4:]

        len(tag)

        print 'r' in 'forward' # True
        'forward'.index(r) # 2
        'book'.count('0') # 2

    修改方法
        赋值
            单个元素/多个元素
                tag[0] = 20
                slist[6:] = list("good world!")
        增加
            单个元素/多个元素 前/中/后
                tag.append(5)
                tag.extend([10, 11, 12])
                tag += [5, 6, 7]

                tag.insert(1, 1000)
                num[1:1] = [2,3,4] 

                乘法
                    tag * 3
        删除
            单个元素/多个元素 前/中/后
                del tag[-1]
                pop()

                del tag[1:3]
                tag[1:3] = []

        排序
            reverse, sort
            sorted

    和其他列表类之间转化
        slist = list('hello world!')
        slist = tuple('hello world!')

    组合方法
        搜索+删除
            name.remove(42)
        乘法
            tag * 3

    
}


