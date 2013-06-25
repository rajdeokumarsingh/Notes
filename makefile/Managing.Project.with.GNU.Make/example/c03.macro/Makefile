define output-info
	@echo output-info 1
	@echo output-info 2
	@echo output-info 3
endef

VPATH = src include

CC = g++
CPPFLAGS += -I include

hello : hello.o lucky.o
	$(output-info)
	$(CC) $(CPPFLAGS) $^ -o $@

hello.o lucky.o : lucky.h

.PHONY : clean
clean:
	rm -rf hello *.o
