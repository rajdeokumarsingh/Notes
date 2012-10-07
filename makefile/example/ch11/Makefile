#sample Makefile
export $(objects)
objects := main.o kbd.o 
include sub.mk

testlib.a($(objects)) : $(objects)
	ar cr $@ $%

clean :
	@rm testlib.a $(objects)

gen:
	touch main.c defs.h command.h buffer.h kbd.c command.c display.c \
	insert.c search.c files.c utils.c 
