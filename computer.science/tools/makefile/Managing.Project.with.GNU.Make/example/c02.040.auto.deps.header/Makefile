# VPATH = src include
vpath %.cpp src
vpath %.h include

CC = g++
CPPFLAGS += -I include

.PHONY: all
all := hello deps

hello : hello.o lucky.o
#g++ $(CPPFLAGS) $^ -o $@

# Generate dependency automatically
# hello.o lucky.o : lucky.h
deps : hello.cpp lucky.cpp
	$(CC) -M $(CPPFLAGS) $^ > $@

-include deps


# rm hello.o lucky.o after build
.INTERMEDIATE: hello.o lucky.o
# useless
#.INTERMEDIATE: %.o
# .INTERMEDIATE: $(wildcard *.o)

.PHONY : clean
clean:
	rm -rf hello *.o deps

.PHONY: help
help:
	@make --print-data-base --question|awk '/^[^.%][-A-Za-z0-9_]*:/'| sort

