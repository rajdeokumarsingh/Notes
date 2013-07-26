
deps := $(patsubst %.c,%.d,$(wildcard *.c))
#$(info $(deps))

#all : clean_deps $(deps)
all : $(deps)
%.d : %.c
	@gcc -MM $<

.PHONY: all clean
clean_deps:
	@rm make.deps
