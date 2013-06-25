VPATH = src

SOURCES := $(wildcard src/*.cpp)
BINARIES := $(notdir $(SOURCES:%.cpp=%))
		
CFLAGS = -I ./include
CC = g++

all : $(BINARIES)

.PHONY : clean
clean :
	-@rm -f $(BINARIES)

