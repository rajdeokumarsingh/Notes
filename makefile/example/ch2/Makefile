#sample Makefile

$(info $(MAKE_VERSION))
objects := main.o kbd.o command.o display.o \
		insert.o search.o files.o utils.o
edit : $(objects)
	touch edit
main.o : main.c defs.h
	touch main.o
kbd.o : kbd.c defs.h command.h
	touch kbd.o
command.o : command.c defs.h command.h
	touch command.o
display.o : display.c defs.h buffer.h
	touch display.o
insert.o : insert.c defs.h buffer.h
	touch insert.o
search.o : search.c defs.h buffer.h
	touch search.o
files.o : files.c defs.h buffer.h command.h
	touch files.o
utils.o : utils.c defs.h
	touch utils.o
	$(info "test")
clean :
	@rm edit $(objects)

gen:
	touch main.c defs.h command.h buffer.h kbd.c command.c display.c \
	insert.c search.c files.c utils.c 
