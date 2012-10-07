
################################################################################
CONDITIONAL EXPRESSIONS
man bash && grep -n\>, -a\> ...



################################################################################
string

5.字符串的掐头去尾
方法:
$echo ${variable#startletter*endletter} # #表示掐头,因为键盘上#在$前面,一个表示最小匹配
$echo ${variable##tartletter*endletter} 两个表示最大匹配
$echo ${variable%startletter*endletter} # %表示去尾,因为键盘上%在$后面,一个表示最小匹配
$echo ${variable%%startletter*endletter} 两个表示最大匹配


# 提取一个全路径的目录名和文件名
$ basename /home/jiangrui/notes.txt
notes.txt
$ dirname /home/jiangrui/notes.txt
/home/jiangrui


################################################################################
# output log to screen AND a file
adb logcat -v threadtime | tee log.txt

adb logcat -b main -b system -b radio -v threadtime
################################################################################
# replace skill

ls -R /data/data/*/databases

# replace "com.android.browser" to "com.pekall.browser"
grep 'com\.android\.browser' . -rns|awk -F: '{print $1}'|uniq|xargs sed -i 's/com.android.browser/com.pekall.browser/g'

grep 'Browser\.' ./src -rns|awk -F: '{print $1}'|uniq|xargs sed -i 's/Browser\./BrowserMetaData\./g'
grep 'Browser\.HISTORY_PROJECTION' ./src -rns|awk -F: '{print $1}'|uniq|xargs sed -i 's/Browser\.HISTORY_PROJECTION/BrowserProvider\.HISTORY_PROJECTION/g'

################################################################################
# executing script 
################################################################################
sh ex.sh
bash ex.sh
. ex.sh
./ex.sh

#!/bin/bash
# means: /bin/bash test.sh

#!/bin/rm
# means: /bin/rm test.sh
# just remove the file

################################################################################
# arguments
################################################################################
# name of executed shell file
$0 

# first argument, second argument,...
$1, $2,...

# number of argument
$#

# argument list, including all arguments
$*
$@

# return of previous executed command
#? 0 for sucess, 1 for failure

# $! is the pid of background process
dirname /home/jiangrui/notes.txt & echo $!

################################################################################
# special character
################################################################################
# result of last command
$? # 0 for sucess, other for failed

$$ #进程ID 变量.这个$$变量保存运行脚本进程ID

() #命令组.如:
(a=hello;echo $a)
#注意:在()中的命令列表,将作为一个子shell 来运行.

#用在数组初始化,如:
Array=(element1,element2,element3)

{xxx,yyy,zzz...}
#大括号扩展,如:
cat {file1,file2,file3} > combined_file
# 把file1,file2,file3 连接在一起,并且重定向到combined_file 中.

cp file22.{txt,backup}
# 拷贝"file22.txt" 到"file22.backup"中

File=/etc/fstab
{
    read line1
    read line2
} < $File

{
    echo "blabla..."
    echo "blabla..."
} > ./testme.output

#[] 数组元素
Array[1]=slot_1
echo ${Array[1]}

# 小写变成大写
ls -l | tr 'a-z' 'A-Z'

(cd /source/directory && tar cf - . ) | (cd /dest/directory && tar xpvf -)
bunzip2 linux-2.6.13.tar.bz2 | tar xvf -
echo "whatever" | cat -

echo $((2#1000))
echo "cmd1";echo "cmd2";...

case "$variable" in
abc) echo "\$variable = abc" ;;
xyz) echo "\$variable = xyz" ;;
esac

let "t2 = ((a = 9, 15 / 3))" # Set "a = 9" and "t2 = 15 / 3"

#: 空命令,等价于"NOP"(no op,一个什么也不干的命令).也可以被认为与shell 的内建命令
#(true)作用相同.":"命令是一个 bash 的内建命令,它的返回值为0,就是shell 返回的true.  如:
1 :
2 echo $? # 0

# dead loop
while : # while true
do
    operation-1
done

#与 cat /dev/null >data.xxx 的作用相同
: > data.xxx #文件"data.xxx"现在被清空了.


################################################################################
# 
################################################################################


#第 章 I/O 重定向

COMMAND_OUTPUT > filename
    # 重定向stdout 到一个文件.
    # 如果没有这个文件就创建, 否则就覆盖.
    ls -lR > dir-tree.list

COMMAND_OUTPUT >> filename
    # 重定向stdout 到一个文件.
    # 如果文件不存在, 那么就创建它, 如果存在, 那么就追加到文件后边.

1>filename
    # 重定向stdout 到文件"filename".
    ls 1>test

2>filename
    # 重定向stderr 到文件"filename".
    ls ./gg 2>test #当前目录没有gg文件
    bad_command1 2>test

&>filename
    # 将stdout 和stderr 都重定向到文件"filename".
    ls ./gg ./test &> test1 #当前目录没有gg文件,有test文件

2>&1
    # 重定向stderr 到stdout.

i>&j
    # 重定向文件描述符i 到 j.
    # 指向i 文件的所有输出都发送到j 中去.

>&j
    # 重定向文件描述符1(stdout)到 j.

#combo
    # tee - read from standard input and write to standard output and files
    ls ./gg ./test 2>&1 |tee test1 #当前目录没有gg文件,有test文件

0< FILENAME
< FILENAME
    # 从文件中接受输入.
    grep search-word <filename


[j]<>filename
    # 为了读写"filename", 把文件"filename"打开, 并且分配文件描述符"j"给它.
    # 如果文件"filename"不存在, 那么就创建它.
    # 如果文件描述符"j"没指定, 那默认是fd 0, stdin.
    #
    # 这种应用通常是为了写到一个文件中指定的地方.
    echo 1234567890 > File # 写字符串到"File".
    exec 3<> File # 打开"File"并且给它分配fd 3.
    read -n 4 <&3 # 只读4 个字符.
    echo -n . >&3 # 写一个小数点.
    exec 3>&- # 关闭fd 3.
    cat File # ==> 1234.67890
    # 随机存储.

# 对所有的.txt 文件的输出进行排序, 并且删除重复行,
# 最后将结果保存到"result-file"中.
cat *.txt | sort | uniq > result-file

#可以将输入输出重定向和(或)管道的多个实例结合到一起写在一行上.
command < input-file > output-file
command1 | command2 | command3 > output-file

可以将多个输出流重定向到一个文件上.
 ls -yz >> command.log 2>&1
# 将错误选项"yz"的结果放到文件"command.log"中.
# 因为stderr 被重定向到这个文件中,
# 所有的错误消息也就都指向那里了.

# 注意, 下边这个例子就不会给出相同的结果.
ls -yz 2>&1 >> command.log
# 输出一个错误消息, 但是并不写到文件中.

# 如果将stdout 和stderr 都重定向,
#+ 命令的顺序会有些不同.

关闭文件描述符
n<&- 关闭输入文件描述符n.
0<&-, <&- 关闭stdin.
n>&- 关闭输出文件描述符n.
1>&-, >&- 关闭stdout.




