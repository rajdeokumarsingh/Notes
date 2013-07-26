#sample Makefile

vpath %.h ./include
vpath %.c ./src

objects := main.o kbd.o command.o display.o \
		insert.o search.o files.o utils.o
edit : $(objects)
	touch edit

main.o : main.c defs.h
	#cc -c $(CFLAGS) $< -o $@
	#FIXME: bad practice
	cc -c $(CFLAGS) main.c -o main.o
kbd.o : kbd.c defs.h command.h
	cc -c $(CFLAGS) $< -o $@
command.o : command.c defs.h command.h
	cc -c $(CFLAGS) $< -o $@
display.o : display.c defs.h buffer.h
	cc -c $(CFLAGS) $< -o $@
insert.o : insert.c defs.h buffer.h
	cc -c $(CFLAGS) $< -o $@
search.o : search.c defs.h buffer.h
	cc -c $(CFLAGS) $< -o $@
files.o : files.c defs.h buffer.h command.h
	cc -c $(CFLAGS) $< -o $@
utils.o : utils.c defs.h
	cc -c $(CFLAGS) $< -o $@
clean :
	@rm edit $(objects)

gen:
	touch main.c defs.h command.h buffer.h kbd.c command.c display.c \
	insert.c search.c files.c utils.c 
