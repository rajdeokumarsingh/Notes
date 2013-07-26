local_src := $(wildcard $(local_dir)/*.cpp)

# move obj to obj dir
# local_obj := $(subst .cpp,.o,$(local_src))
# local_obj := $(notdir $(subst .cpp,.o,$(local_src)))
local_obj := $(addprefix $(out_dir)/,$(notdir $(subst .cpp,.o,$(local_src))))

sources += $(local_src)
objects += $(local_obj)

# move obj to obj dir
#deps += $(subst .cpp,.d,$(local_src))
deps += $(addprefix $(out_dir)/,$(notdir $(subst .cpp,.d,$(local_src))))

libraries += $(local_lib)
