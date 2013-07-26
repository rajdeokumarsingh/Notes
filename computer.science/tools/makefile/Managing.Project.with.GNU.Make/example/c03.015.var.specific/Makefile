# build multiple objects (hello and hi)
.PHONY : all
all : hello hi
    
# specific variable
hello: CPPFLAGS += -DDEBUG
hello : hello.cpp
	@echo CPPFLAGS: $(CPPFLAGS)
	g++ $(CPPFLAGS) $< -o $@

hi : hi.cpp
	@echo CPPFLAGS: $(CPPFLAGS)
	g++ $< -o $@

.PHONY : clean
clean:
	rm -rf hello hi
