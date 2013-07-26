key-value之间的映射

key: 
    数字，字符串，元组
    唯一的, 不允许一个dictionary中有多个同样的key

创建
    empty_dic = {}

    phonebook = {"Rui":"150 1100 5932", "Phonex":"138 1166 6985", "Mother":"132 0713 1873"}
    print phonebook["Rui"]
    # output: 150 1100 5932

    # 复杂字典
                            pb3 = {
                                "Rui": {
                                    "tel":"150 1100 5932",
                                    "addr": "Wang Jing"
                                },
                                "Phonex": {
                                    "tel": "138 1166 6985",
                                    "addr": "Zhi Chun Road"
                                },
                                "Mother": {
                                    "tel":"132 0713 1873",
                                    "addr": "Wu Han"
                                }
                            }
                            print "Rui's info %(Rui)s" % pb3
                            # output: Rui's info {'tel': '150 1100 5932', 'addr': 'Wang Jing'}

    # shallow copy
                            # FIXME: just copy reference of object
                            pb4 = {
                                'version': 2,   # primitive value
                                'member': ['rui', 'p', 'm'] # object reference (FIXME)
                            }

                            pbs = pb4.copy();
                            pbs['version'] = 3
                            pbs['member'].remove('rui')
                            print pb4
                            # output: {'member': ['p', 'm'], 'version': 2}
                            print pbs
                            # output: {'member': ['p', 'm'], 'version': 3}

    # deep copy
                            # FIXME: also copy memory of reference
                            pb4 = {
                                'version': 2,   # primitive value
                                'member': ['rui', 'p', 'm'] # object reference (FIXME)
                            }

                            from copy import deepcopy
                            pbs = deepcopy(pb4);

                            pbs['version'] = 3
                            pbs['member'].remove('rui')
                            print pb4
                            # output: {'member': ['rui', 'p', 'm'], 'version': 2}
                            print pbs
                            # output: {'member': ['p', 'm'], 'version': 3}

    # create a NONE/default value dictionary
        print {}.fromkeys(['test', 1, 'good'])
        # output: {'test': None, 1: None, 'good': None}
        print dict.fromkeys(['test', 1, 'good'])
        # output: {'test': None, 1: None, 'good': None}

        print dict.fromkeys(['test', 1, 'good'], 'defalt')
        # output: {'test': 'default', 1: 'default', 'good': 'default'}


    和其他列表类之间转化: dict()
        # list of tuple(key,value)
        l = [("Rui", "150 1100 5932"), ("Phonex", "138 1166 6985"), ("Mother", "132 0713 1873")]
        pb2 = dict(l)
        print pb2

        # FIXME: key没有被引号包括起来
        print dict(Rui = "150 1100 5932", Phonex = "138 1166 6985", Mother = "132 0713 1873")

查询/搜索/统计 const
    单个元素/多个元素 前/后
    key in d
    has_key(key) # same with in

    d[key]
    d.get(key)
                        # deference of d[key] d.get(key)
                        d = {}
                        print d['test'] # FIXME: will exit with error!
                        print d.get('test') # output: None
                        print d.get('test', 'default') # output: default
    len(d)


    # items() and iteritems()
    # keys() and iterikeys()
    # values() and itervalues()
                        pb = {"Rui":"150 1100 5932", "Phonex":"138 1166 6985", "Mother":"132 0713 1873"}
                        print pb.items()
                        # output: [('Phonex', '138 1166 6985'), ('Rui', '150 1100 5932'), 
                        # ('Mother', '132 0713 1873')] 
                        print pb.iteritems() # 迭代器对象

                        print pb.keys()
                        # output: ['Phonex', 'Rui', 'Mother']
                        print pb.iterkeys() # 迭代器对象

                        # values() and itervalues()
                        print pb.values()
                        # output: ['138 1166 6985', '150 1100 5932', '132 0713 1873']
                        print pb.itervalues() # 迭代器对象
修改
    赋值
        单个元素/多个元素 前/后
        d[key] = value

        # 设置默认值: 如果不存在，设置默认值；如果存在，则不修改
            d = {}
            d.setdefault('name', 'n/a')
            print d['name'] # output: 'n/a'

            d['name'] = 'Rui'
            d.setdefault('name', 'n/a')
            print d['name'] # output: 'Rui'

    增加
        单个元素/多个元素 前/中/后

        d = {}
        d[42] = "test" # 自动添加
                                        # FIXME: 列表无法自动添加
                                        l = []
                                        l[3] = "test"

    删除
        单个元素/多个元素 前/中/后

        del d[key]
        d.pop(key) # remove key-value pair and return value
        d.popitem() # remove key-value randomly and return tuple(key, value)
                    # 用于循环处理

        d.clear() # 清除所有key-value

    排序

组合方法
    修改+增加: 
        d.update(dnew)
                            pb5 = {
                                'version': 2,
                                'member': ['rui', 'p', 'm']
                            }
                            pb6 = {
                                'version': 3, # 修改version为3
                                'data' : 80   # 增加data:80
                            }
                            pb5.update(pb6)
                            # output: {'member': ['rui', 'p', 'm'], 'version': 3, 'data': 80}

格式化输出
    pb3 = {
        "Rui": {
            "tel":"150 1100 5932",
            "addr": "Wang Jing"
        },
        "Phonex": {
            "tel": "138 1166 6985",
            "addr": "Zhi Chun Road"
        },
        "Mother": {
            "tel":"132 0713 1873",
            "addr": "Wu Han"
        }
    }
    print "Rui's info %(Rui)s" % pb3


