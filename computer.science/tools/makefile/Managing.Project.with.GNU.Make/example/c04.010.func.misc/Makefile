obj = foo.c foo.c bar.c x.cpp y.l a.o b.p d.o .o
nodup = foo.c bar.c x.cpp y.l a.o b.p d.o .o

$(info test sort ...)
$(info origin list $(obj))
# XXX: sort will remove same items
$(info after sort, $(sort $(obj)))
$(info )


$(info test shell ...)
DIR := ..
SUBDIR := $(shell for i in `ls $(DIR)`;do echo $$i;done)
$(info $(SUBDIR))
$(info )

START_TIME := $(shell date)
CURRENT_TIME = $(shell date)

$(info test shell ...)
$(info start time, $(START_TIME))
$(info current time, $(CURRENT_TIME))
$(info )

$(info test duplicate ...)
$(info $(filter $(words $(obj)),$(words $(sort $(obj)))))
$(info $(filter $(words $(nodup)),$(words $(sort $(nodup)))))
$(info )

$(info test generate file name with date...)
$(info file-$(shell date +%F).tgz)
$(info )

