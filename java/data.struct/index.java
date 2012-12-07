
数组中既能保存对象的引用，又能保存基本类型
集合类中只能保存对象的引用，不能保存基本类型


两个重要的接口
    Collection
        Set
            无序集合 
            元素不能重复
        List
            有序集合
        Queue 
            队列
    Map
        保存key, value对

常用类
    ArrayList, HashSet, HashMap

遍历Collection

    Iterator
        用于遍历Colleciton中的元素
        boolean hasNext();
        Object next(); // 获取下一个元素
        void remove(); // 删除集合中上一次next返回的方法


    for (Object obj : Collection)
