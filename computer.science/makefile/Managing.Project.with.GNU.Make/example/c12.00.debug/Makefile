$(warning A top-level warning)

.PHONY: bar target makefile

FOO := $(warning right-hand side of a variable)bar

$(warning a target)target: $(warning a prerequisite) makefile $(FOO)
	$(warning a command)
	@echo $(CFLAGS)
	ls

$(FOO):

makefile:
