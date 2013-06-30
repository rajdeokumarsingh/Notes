local_dir := lib/codec
local_src := $(wildcard $(local_dir)/*.cpp)
local_obj := $(subst .cpp,.o,$(local_src))

sources += $(local_src)
objects += $(local_obj)
deps += $(subst .cpp,.d,$(local_src))
