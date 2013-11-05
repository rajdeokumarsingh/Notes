
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


netstat -an|grep mysql

netstat -ae|grep “TIME_WAIT” |wc –l

TCP连接状态:
netstat -n | awk '/^tcp/ {++S[$NF]} END {for(a in S) print a, S[a]}' 



