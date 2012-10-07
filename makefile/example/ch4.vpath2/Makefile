#sample Makefile

LIBS = libtest.a
VPATH = ./src
GPATH = ./src # libtest.a will generate in ./src

libtest.a : display.o  files.o  insert.o 
	$(AR) $(ARFLAGS) $@ $^

.PHONY : clean

clean:	
	@rm -Rf obj/* libtest.a
	@rm -Rf *.o

touch:
	touch src/*

