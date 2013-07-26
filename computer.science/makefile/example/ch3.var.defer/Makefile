
v1 = $(v2)
$(info $(v1))
v2 = $(v3)
$(info $(v2))

v3 := $(wildcard *.c)
#$(info $(v3))

print: 
	$(info $(v1))
	$(info $(v2))
	@rm -Rf *.o

#objects := $(patsubst %.c,%.o,$(wildcard *.c))
#foo : $(objects)
#	$(info $(objects))
#	#cc -o foo $(objects)

.PHONY: clean print
clean:
	rm *.o
