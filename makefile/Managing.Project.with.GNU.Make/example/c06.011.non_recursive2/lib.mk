local_src := $(wildcard $(local_dir)/*.cpp)
local_obj := $(subst .cpp,.o,$(local_src))

sources += $(local_src)
objects += $(local_obj)
deps += $(subst .cpp,.d,$(local_src))
libraries += $(local_lib)

# $(call make-library,$(local_lib),$(local_obj))
define make-library
$1 : $2
	$(AR) $(ARFLAGS) $$@ $$^
endef
