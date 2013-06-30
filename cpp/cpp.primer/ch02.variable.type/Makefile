VPATH = src

SOURCES := $(wildcard src/*.cpp)
BINARIES :=$(notdir $(patsubst %.cpp,%,$(SOURCES)))
		
CFLAGS = -I../include
CC = g++

all: $(BINARIES)

.PHONY: clean
clean:
	rm -f $(BINARIES)
