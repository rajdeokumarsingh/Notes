# FIXME: 注意文件名的函数使用空格作为参数的分隔符号

obj = foo.c bar.c x.cpp y.l a.o b.p d.o .o

$(info test wildcard ...)
$(info wildcard .c, $(wildcard *.c))
$(info wildcard .o, .cpp, $(wildcard *.o *.cpp))

# test file exist
$(info has x.cpp, $(wildcard ./x.cpp))
$(info has b.cpp, $(wildcard ./b.cpp))
$(info )


FULL_PATH = $(shell pwd)/Android.mk
$(info test dir ...)
$(info full path, $(FULL_PATH))
$(info dir part, $(dir $(FULL_PATH)))
# multiple arguments
$(info not dir part, $(notdir $(FULL_PATH) $(FULL_PATH) $(FULL_PATH)))
$(info )

$(info test suffix ...)
$(info full path, $(FULL_PATH)) 
$(info suffix, $(suffix $(FULL_PATH))) 
$(info suffix multiple arguments, $(suffix $(FULL_PATH) $(FULL_PATH) $(FULL_PATH))) 
$(info )

$(info test basename ...)
$(info full path, $(FULL_PATH)) 
$(info basename, $(basename $(FULL_PATH))) 
$(info basename multiple arguments, $(basename $(FULL_PATH) $(FULL_PATH) $(FULL_PATH))) 
$(info )

$(info test addsuffix ...)
$(info add suffix .jar, $(addsuffix .jar,app framework)) 
$(info )

$(info test addprefix ...)
$(info add prefix root/ , $(addprefix /root/,app framework)) 
$(info )

$(info test join ...)
$(info join, $(join prefix1- prefix2-,suffix1 suffix2)) 
$(info )

