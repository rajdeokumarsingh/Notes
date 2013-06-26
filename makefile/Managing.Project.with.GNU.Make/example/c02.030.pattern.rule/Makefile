# VPATH = src include
vpath %.cpp src
vpath %.h include

CC = g++
CPPFLAGS += -I include

hello : hello.o lucky.o
#g++ $(CPPFLAGS) $^ -o $@

# hello.o lucky.o : lucky.h
%.o : %.cpp lucky.h
	$(CC) -c $(CPPFLAGS) $< -o $@
	@echo pattern rule!

.PHONY : clean
clean:
	rm -rf hello *.o
