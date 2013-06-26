vpath %.cpp src
vpath %.h include

CC = g++
CPPFLAGS += -I include
SOURCES = funny.cpp lucky.cpp

# $(AR) $(ARFLAGS) $@ $^
# $? are prerequisites newer than the targe
liblife.a : funny.o lucky.o
	$(AR) $(ARFLAGS) $@ $?

# generate dependencies (frome GNU Make Manual)
-include $(subst .cpp,.d, $(SOURCES))
%.d: %.cpp
	$(CC) -M $(CPPFLAGS) $< > $@.$$$$; \
	sed 's,\($*\)\.o[ :]*,\1.o $@ : ,g' < $@.$$$$ > $@; \
	rm -f $@.$$$$

# replace:
# hello.o: src/hello.cpp /usr/include/c++/4.7/iostream \
# hello.o hello.d : src/hello.cpp /usr/include/c++/4.7/iostream \

.PHONY : clean
clean:
	rm -rf *.a *.o *.d

.PHONY: help
help:
	@make --print-data-base --question|awk '/^[^.%][-A-Za-z0-9_]*:/'| sort

