#sample Makefile

$(info $(defer))
$(info $(immediate))
$(info $(objects))

$(info $(MAKEFLAGS))
$(info $(MAKELEVEL))
$(info $(CFLAGS))

#objects: CFLAGS += -Wall
%.o : CFLAGS += -O
objects += main.o kbd.o command.o display.o \
		insert.o search.o files.o utils.o

edit : $(objects)
main.o : main.c defs.h
kbd.o : kbd.c defs.h command.h
command.o : command.c defs.h command.h
display.o : display.c defs.h buffer.h
insert.o : insert.c defs.h buffer.h
search.o : search.c defs.h buffer.h
files.o : files.c defs.h buffer.h command.h
utils.o : utils.c defs.h
clean :
	rm -Rf edit $(objects)
gen:
	touch main.c defs.h command.h buffer.h kbd.c command.c display.c \
	insert.c search.c files.c utils.c 

$(info $(objects))
