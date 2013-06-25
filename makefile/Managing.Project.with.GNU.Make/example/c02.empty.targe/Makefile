# FIXME: not clear yet
hello: hello.o size
	g++ -o $@ $< 

size: hello.o
	size $^
	touch size

.PHONY : clean
clean:
	rm -rf hello hello.o
