# Make是一个命令行程序


# $(shell) 中的命令会通过subshell执行

# 以tab开头的行会被解析成命令
# 进入命令解析模式
# 将命令传递给subshell去执行
# 空行被忽略

all:
	@echo something
	@echo something more ...
	# info前面必须有tab, 否则会终止command模式 $@
	# shell comment, with tab
	$(info make function in command mode, output in first line! before something)

# make file comment, without tab
	@echo something more more ....

tags:
	cd src && \
		ctags -R --extra=f
	#cd src; ctags -R --extra=f, ok
	# cd src
	# ctags -R --extra=f, 由于两条语句实在两个subshell中执行， 不会在src中找到tags

# @: silent
modifier:
	echo duplicate output
	@echo single output
	
# -: ignore errors, not recommended
# failed command will end make process
modifier2:
	-cd dir_not_exist
	@echo go on 1!
	cd dir_not_exist
	@echo go on 2!

# +: force execute command
modifier3:
	+echo force execute
	echo not force

# empty command
# avoid pattern rule to match the target
header.h: ;

$(info make flags, $(MAKEFLAGS)) # make的命令行选项 ??
$(info mflags, $(MFLAGS)) # make的兼容MAKEFLAGS ??
$(info make level, $(MAKELEVEL)) # make的嵌套调用次数, makefile中调用make命令的次数

