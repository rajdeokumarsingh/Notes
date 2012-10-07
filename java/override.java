/*                          Java中@Override的作用
@override有注释文档的作用， 同时可检查方法的正确性

如果想重写父类的方法，比如toString()方法的话，在被重载的方法前面加上@Override ，这样编译的时候系统可以帮你检查方法的正确性
如下
@Override
public String toString(){...}这是正确的

如果将toString写成tostring
@Override
public String tostring(){...}编译器可以检测出这种写法是错误的,提醒你改正

而如果不加@Override
public String tostring(){...}这样编译器是不会报错的，它会认为是你在类中加的新方法

所以编程时一定得细心点，不是所有错误系统都能找到的

所以，最好缺省都加上override, 除非Android中编译不过
*/
