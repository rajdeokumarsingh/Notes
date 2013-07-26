
export defer = "defer"
export immediate := "immediate"
export objects := insert.c kbd.c

override CFLAGS += -x
#override USERNAME=make_test
USERNAME=make_test

$(info $(MAKEFLAGS))
$(info $(MAKELEVEL))
$(info $(CFLAGS))
$(info $(SHELL))
$(info $(USERNAME))

#objects: CFLAGS += -Wall
#%.o : CFLAGS += -O

all:
	$(MAKE) -f sub.mk
	$(info $(objects))

.PHONY: all
