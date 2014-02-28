netstat -an --protocol=inet -t

    -a, --all
          Show both listening and non-listening sockets.  With the --interfaces option, show interfaces that are not up

   --numeric , -n
          Show numerical addresses instead of trying to determine symbolic host, port or user names.

    --protocol=family , -A
        Specifies the address families (perhaps better described as low level protocols) for which connections are to be shown.  
        family is a comma (',') separated list of address family keywords like 
            inet, unix, ipx, ax25, netrom, and ddp.  This  has  the  same  effect  as
            using the --inet, --unix (-x), --ipx, --ax25, --netrom, and --ddp options.

            The address family inet includes raw, udp and tcp protocol sockets.

    [--tcp|-t] [--udp|-u] [--raw|-w]

    -e, --extend
        Display additional information.  Use this option twice for maximum detail.

    --route , -r
        Display the kernel routing tables. See the description in route(8) for details.  netstat -r and route -e produce the same output.



netstat -an|grep mysql

netstat -ae|grep “TIME_WAIT” |wc –l

TCP连接状态:
netstat -n | awk '/^tcp/ {++S[$NF]} END {for(a in S) print a, S[a]}' 

netstat -r

################################################################################
Linux下查看网络端口占用情况

命令: netstat -tupln 或者 netstat -pln
参数解释:
-t : 指明显示TCP端口
-u : 指明显示UDP端口
-p : 显示进程ID(PID)和程序名称，每一个套接字/端口都属于一个程序.
-l  : 仅显示监听套接字-- 所谓套接字就是使应用程序能够读写与收发通讯协议(protocol)与资料的程序
-n : 不进行DNS轮询(可以加速操作)
 
所有与网络有关的程序都需要和文件/etc/services打交道,你可以用cat /etc/services一看究竟.

