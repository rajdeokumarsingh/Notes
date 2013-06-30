OBJDIR := ../obj/

src := $(wildcard *.cpp)

# binaries := $(src:%.cpp=%)
binaries := $(src:%.cpp=$(OBJDIR)%)
		
CFLAGS += -include "assert.h"
CFLAGS = -I./include -I../include

all : $(binaries)
# $(binaries) : $(notdir $@)
#FIXME: deps not correct
$(binaries) : $(src)
	g++  $(notdir $@).cpp -o $@

.PHONY : clean
clean :
	rm -f $(binaries)

