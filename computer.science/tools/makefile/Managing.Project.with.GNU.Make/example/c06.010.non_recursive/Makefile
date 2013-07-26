program :=
sources :=

objects :=
deps :=

CC := g++
CPPFLAGS += -I./include 

vpath %.h include
vpath %.cpp lib/ui lib/db lib/codec app/player

include lib/ui/module.mk
include lib/db/module.mk
include lib/codec/module.mk
include app/player/module.mk

$(program) : $(objects)
	$(CC) $(CPPFLAGS) $^ -o $@

ifneq "$(MAKECMDGOALS)" "clean"
include $(subst .cpp,.d,$(sources))
endif

%.d: %.cpp
	$(CC) -M $(CPPFLAGS) $< | \
	sed 's,\($(notdir $*)\.o\) *:,$(dir $@)\1 $@: ,' > $@.tmp; \
	mv $@.tmp $@

.PHONY: clean
clean:
	@rm -rf $(objects) $(program) $(deps)

