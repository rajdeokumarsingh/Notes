#sample Makefile
objects := main.o kbd.o command.o display.o \
		insert.o search.o files.o utils.o

%.o : %.c
	@echo $*
	@echo $(@D)
	@echo $(@F)
	$(CC) -c $(CPPFLAGS) $(CFLAGS) $< -o $@

edit : $(objects)
	$(CC) $(CFLAGS) $^ -o $@
	echo $?

clean :
	@rm edit $(objects)

gen:
	touch main.c defs.h command.h buffer.h kbd.c command.c display.c \
	insert.c search.c files.c utils.c 
