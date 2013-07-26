# $(call generate-files, prefix, file-list)
define generate-files
	$1_src := $(filter %.c,$2)
endef

# $(eval $(call generate-files,ls,a.c b.c c.c x.h y.h z.h))
$(call generate-files,ls,a.c b.c c.c x.h y.h z.h)

show-variables:
	# $(ls_src)
