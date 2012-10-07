/*这个问题是说,你不能在对一个List进行遍历的时候将其中的元素删除掉
解决办法是,你可以先将要删除的元素用另一个list装起来,等遍历结束再remove掉
可以这样写*/
Caused by: java.util.ConcurrentModificationException

List delList = new ArrayList();//用来装需要删除的元素
for(Information ia:list)
    if(ia.getId()==k){
        n++;
        delList.add(ia);
    }
list.removeAll(delList);//遍历完成后执行删除
