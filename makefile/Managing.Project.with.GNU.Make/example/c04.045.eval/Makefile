# TODO:

# It's important to realize that the eval argument is expanded twice; first by the eval function, then the results of that expansion are expanded again when they are parsed as makefile syntax."
# Take the example from GNU Make for example:

PROGRAMS    = server client

server_OBJS = server.o
client_OBJS = client.o

# Everything after this is generic

.PHONY: all
all: $(PROGRAMS)

#$(1): $$($(1)_OBJS) $$($(1)_OBJS:%.o=%.h)
define PROGRAM_template
$(1): $($(1)_OBJS) $($(1)_OBJS:%.o=%.h)
	$(CC) $$^ -o $$@
endef

$(foreach prog,$(PROGRAMS),$(eval $(call PROGRAM_template,$(prog))))

clean:
	rm -f $(ALL_OBJS) $(PROGRAMS) *.o

# --------------------------------
# How to understand the twice replace for eval:
# 	In foreach:
# 	  "eval $(call PROGRAM_template, server)"
# 1st time replacement, 'PROGRAM_template' is executed as shell:
# 	  "server: $(server_OBJS) $(server_LIBS:%=-l%)
# 2nd time replacement, execute as makefile:
# 	  server: $server_OBJS -l$server_LIBS
# 	  --------------------------------
# References:
# 	* GNU make manual: http://www.gnu.org/software/make/manual/make.html#Eval-Function
# * http://www.cnblogs.com/hnrainll/archive/2011/04/12/2013377.html




