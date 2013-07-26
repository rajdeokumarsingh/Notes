program :=
sources :=
libraries :=

objects :=
deps :=

CC := g++
CPPFLAGS += -I./include 

.PHONY: all
all:

-include $(shell find . -name 'module.mk')

all: $(program)

$(program) : app/player/main.o $(libraries)
	$(CC) $(CPPFLAGS) $^ -o $@

ifneq "$(MAKECMDGOALS)" "clean"
-include $(subst .cpp,.d,$(sources))
endif

%.d: %.cpp
	$(CC) -M $(CPPFLAGS) $< | \
	sed 's,\($(notdir $*)\.o\) *:,$(dir $@)\1 $@: ,' > $@.tmp; \
	mv $@.tmp $@

.PHONY: clean
clean:
	@rm -rf $(objects) $(program) $(deps)
