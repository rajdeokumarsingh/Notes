SOURCES := $(wildcard *.cpp)
BINARIES := $(patsubst %.cpp,%.o,$(SOURCES))

CC := g++

main: $(BINARIES)
	$(CC) $^ -o $@

.PHONY: clean
clean:
	rm -f *.o main
