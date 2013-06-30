var1 := hello world
var2 := var1

$(info $($(var2)))

$(eval source := a.c b.c)


# $(call generate-files, prefix, file-list)
define generate-files
	$1_src := $(filter %.c,$2)
	$1_header := $(filter %.h,$2)
	$1_obj := $(patsubst %c,%o,$(filter %.c,$2))
endef

$(eval $(call generate-files,ls,a.c b.c c.c x.h y.h z.h))

show-variables:
	# $(ls_src)
	# $(ls_header)
	# $(ls_obj)
