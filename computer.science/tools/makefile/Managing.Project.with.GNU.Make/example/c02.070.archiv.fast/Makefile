vpath %.cpp src
vpath %.h include

CC = g++
CPPFLAGS += -I include
SOURCES = funny.cpp lucky.cpp

liblife.a : liblife.a(funny.o) liblife.a(lucky.o)
# ranlib creates index of the lib
#$(RANLIB) $@

# using makefile implicit rules
#liblife.a(funny.o): funny.o
#	$(AR) $(ARFLAGS) $@ $<
#liblife.a(lucky.o) : lucky.o
#	$(AR) $(ARFLAGS) $@ $<

-include $(subst .cpp,.d, $(SOURCES))
%.d: %.cpp
	$(CC) -M $(CPPFLAGS) $< > $@.$$$$; \
	sed 's,\($*\)\.o[ :]*,\1.o $@ : ,g' < $@.$$$$ > $@; \
	rm -f $@.$$$$

.PHONY : clean
clean:
	rm -rf *.a *.o *.d

.PHONY: help
help:
	@make --print-data-base --question|awk '/^[^.%][-A-Za-z0-9_]*:/'| sort

