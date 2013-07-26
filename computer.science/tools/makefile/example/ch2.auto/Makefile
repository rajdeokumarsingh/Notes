export $(objects)

def = $(objects)
objects := main.o
imd := $(objects)

#$(info $(def))
#$(info $(imd))
include sub.mk
#$(info $(def))
#$(info $(imd))

edit : $(objects)
	$(CC) $(CFLAGS) $^ -o $@
	$(info $(imd))

.PHONY : clean
clean :
	rm -rf edit $(objects)

gen:
	touch main.c defs.h command.h buffer.h kbd.c command.c display.c \
	insert.c search.c files.c utils.c 

