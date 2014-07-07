# 注意netmask一定要配置正确，否则网络起不来
sudo ifconfig eth0 192.168.1.15 netmask 255.255.255.0 

sudo ifconfig up

# 为网卡添加多个ip
sudo ifconfig eth0:1 192.168.255.250 broadcast 192.168.255.255 netmask 255.255.0.0 up 
sudo route add -host 192.168.255.250 dev eth0:1


