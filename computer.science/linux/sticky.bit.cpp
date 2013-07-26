
sticky bit 
    a user ownership access-right flag 
        can be assigned to files and directories

    // 现在基本都不用了
    introduced in the Fifth Edition of Unix for use with pure executable files
        It instructed the operating system to retain the text segment of the program in swap space after the process exited

        This speeds up subsequent executions by allowing the kernel to make a single operation 
            of moving the program from swap to real memory. 

    Sticky bit on directories
        When the sticky bit is set, only the item's owner, the directory's owner, or the superuser can rename or delete files.

        Without the sticky bit set, any user with write and execute permissions for the directory 
            can rename or delete contained files, regardless of owner.

        Typically this is set on the /tmp directory to prevent ordinary users from deleting or moving other users' file

            jiangrui@jiangrui-laptop:/$ ls -ld /tmp/
            drwxrwxrwt 17 root root 460 2013-01-06 15:17 /tmp/
            // 注意最后的t

    Linux 
        the Linux kernel ignores the sticky bit on files. 
        When the sticky bit is set on a directory, 
            files in that directory may only be unlinked or renamed by root or their owner.

////////////////////////////////////////////////////////////////////////////////
Examples
    chmod 
        octal mode 1000 
        or by its symbol t 

            chmod +t /usr/local/tmp
            chmod 1777 /usr/local/tmp


// 用户jiangrui创建两个目录和文件
jiangrui@jiangrui-laptop:delete.me$ mkdir dir
jiangrui@jiangrui-laptop:delete.me$ mkdir dir.sticky
jiangrui@jiangrui-laptop:delete.me$ chmod 777 dir
jiangrui@jiangrui-laptop:delete.me$ chmod 1777 dir.sticky/
jiangrui@jiangrui-laptop:delete.me$ ls -l
总用量 8
drwxrwxrwx 2 jiangrui jiangrui 4096 2013-01-06 16:05 dir
drwxrwxrwt 2 jiangrui jiangrui 4096 2013-01-06 16:05 dir.sticky

// 创建一个新用户test
jiangrui@jiangrui-laptop:~$ sudo useradd test
[sudo] password for jiangrui: 
jiangrui@jiangrui-laptop:~$ sudo passwd test
输入新的 UNIX 密码： 
重新输入新的 UNIX 密码： 
passwd：已成功更新密码

// 切换到用户test
jiangrui@jiangrui-laptop:delete.me$ su test
密码： 

// test无法删除jiangrui的目录，就算该目录的权限是777
$ rm dir -rf
rm: 无法删除"dir": 权限不够
$ rm dir.sticky -rf
rm: 无法删除"dir.sticky/1": 不允许的操作

// test可以删除jiangrui的目录，如果该文件可写
$ rm dir/1

// test无法删除jiangrui的设置了sticky bit的目录中的文件，就算该文件的权限是777
$ rm dir.sticky/1
rm: 无法删除"dir.sticky/1": 不允许的操作

