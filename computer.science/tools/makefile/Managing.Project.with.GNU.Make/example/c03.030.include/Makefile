FILE_MK = var.mk

# sub.mk is in ./sub, try "make -I sub"
include sub.mk
include *.make
include $(FILE_MK)

$(info sub.mk var, $(SUB_VAR))
$(info a.make var, $(MAKE_A_VAR))
$(info b.make var, $(MAKE_B_VAR))
$(info var.mk var, $(VAR_MK))

$(info makefile list, $(MAKEFILE_LIST))
$(info variables, $(.VARIABLES))
