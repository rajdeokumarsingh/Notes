define func1
@echo function name: $0, args: $1 $2
endef

var-func = $(info var-func, $1, $1, $1)

.PHONY: all
all:
	$(call func1,ok, good, great)
	$(call var-func,argument ...)
