# VPATH = src include
vpath %.cpp src
vpath %.h include

CC = g++
CPPFLAGS += -I include

hello : hello.o lucky.o
	#g++ $(CPPFLAGS) $^ -o $@

hello.o lucky.o : lucky.h

# rm hello.o lucky.o after build
.INTERMEDIATE: hello.o lucky.o
# useless
#.INTERMEDIATE: %.o
# .INTERMEDIATE: $(wildcard *.o)

.PHONY : clean
clean:
	rm -rf hello *.o

.PHONY: help
help:
	@make --print-data-base --question|awk '/^[^.%][-A-Za-z0-9_]*:/'| sort

