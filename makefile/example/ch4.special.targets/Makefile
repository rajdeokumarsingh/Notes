#sample Makefile
objects := main.o kbd.o command.o display.o \
		insert.o search.o files.o utils.o
edit : $(objects)
	touch $@
main.o : main.c defs.h
kbd.o : kbd.c defs.h command.h
command.o : command.c defs.h command.h
display.o : display.c defs.h buffer.h
insert.o : insert.c defs.h buffer.h
search.o : search.c defs.h buffer.h
files.o : files.c defs.h buffer.h command.h
utils.o : utils.c defs.h
#utils.o : utilccs.c defs.h

.PHONY : clean
clean :
	rm -Rf edit $(objects)

.INTERMEDIATE: main.o kbd.o command.o display.o \
        insert.o search.o files.o utils.o
.SECONDARY: main.o
.PRECIOUS: kdb.o
.SILENT: command.o

gen:
	touch main.c defs.h command.h buffer.h kbd.c command.c display.c \
	insert.c search.c files.c utils.c 
