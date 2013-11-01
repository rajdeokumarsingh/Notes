# local socket port range
cat /proc/sys/net/ipv4/ip_local_port_range

net.ipv4.tcp_timestamps=1 开启对于TCP时间戳的支持,若该项设置为0，则下面一项设置不起作用
sudo sysctl -w net.ipv4.tcp_timestamps=1

net.ipv4.tcp_tw_recycle=1 表示开启TCP连接中TIME-WAIT sockets的快速回收
sudo sysctl -w net.ipv4.tcp_tw_recycle=1

net.ipv4.ip_local_port_range 表示本地socket端口的返回，5000表示从5000开始
sudo sysctl -w net.ipv4.ip_local_port_range=5000





通过调整内核参数解决
vi /etc/sysctl.conf

编辑文件，加入以下内容：
net.ipv4.tcp_syncookies=1
net.ipv4.tcp_tw_reuse=1
net.ipv4.tcp_tw_recycle=1
net.ipv4.tcp_fin_timeout=30

 

然后执行/sbin/sysctl -p让参数生效。

net.ipv4.tcp_syncookies=1       #表示开启SYN Cookies。当出现SYN等待队列溢出时，启用cookies来处理，可防范少量SYN攻击，默认为0，表示关闭；
net.ipv4.tcp_tw_reuse=1         #表示开启重用。允许将TIME-WAIT sockets重新用于新的TCP连接，默认为0，表示关闭；
net.ipv4.tcp_tw_recycle=1       #表示开启TCP连接中TIME-WAIT sockets的快速回收，默认为0，表示关闭。
net.ipv4.tcp_fin_timeout=30     #修改系統默认的TIMEOUT时间



net.ipv4.tcp_keepalive_time=300


TCP connection:
http://blog.csdn.net/windxxf/article/details/6045381
http://blog.csdn.net/guichenglin/article/details/7753047
http://blog.csdn.net/leinchu/article/details/6588732
http://bbs.chinaunix.net/linux/timewait.shtml
http://zhumeng8337797.blog.163.com/blog/static/10076891420129155535790/
http://blog.csdn.net/lastsweetop/article/details/6400328
http://bbs.chinaunix.net/forum.php?mod=viewthread&tid=2036606
http://blog.csdn.net/wenchao126/article/details/7528543
http://blog.csdn.net/panda_bear/article/details/6552480




