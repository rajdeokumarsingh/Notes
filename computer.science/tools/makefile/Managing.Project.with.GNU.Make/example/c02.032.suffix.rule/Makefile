# clear system suffix rules
.SUFFIXS:

# VPATH = src include
vpath %.cpp src
vpath %.h include

CC = g++
CPPFLAGS += -I include

hello : hello.o lucky.o

# FIXME: lucky.h not in deps
.cpp.o: lucky.h
	$(CC) -c $(CPPFLAGS) $< -o $@
	@echo suffix rule!

.PHONY : clean
clean:
	rm -rf hello *.o
