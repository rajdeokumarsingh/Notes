2. lsof

lsof命令的原始功能是列出打开的文件的进程
lsof -i :22 知道22端口现在运行什么程序

lsof       显示所有的进程

查看所属root用户进程所打开的文件类型为txt的文件: # lsof -a -u root -d txt 
