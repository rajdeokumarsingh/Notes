$(info style, $(STYLE))
$(info home, $(HOME))

OPTION = -d   # FIXME: has trailing spaces

VPATH = src include

CC = g++
CPPFLAGS += -I include

hello : hello.o lucky.o
ifdef STYLE
	@echo style-info 1
else
	@echo output-info 1
endif
ifdef HOME
	@echo home-info 1
else
	@echo no-home-info 1
endif

ifeq "$(OPTION)" "-d"
	@echo option 1
endif
ifeq "$(strip $(OPTION))" "-d"
	@echo option 2
endif
	$(CC) $(CPPFLAGS) $^ -o $@

hello.o lucky.o : lucky.h

.PHONY : clean
clean:
	rm -rf hello *.o
