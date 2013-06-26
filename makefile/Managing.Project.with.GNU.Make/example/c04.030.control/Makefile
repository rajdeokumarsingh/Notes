# format:
# $(if condition,then-part,else-part)

$(if $(filter $(MAKE_VERSION),3.80),\
	$(info get make 3.80),\
	$(warning Need make 3.80))

$(if $(filter $(MAKE_VERSION),3.81),\
	$(info get make 3.81),\
	$(warning Need make 3.81))


# $(foreach variable,list,body)

$(foreach i,a b c d e f g,$(info $(i)))
ALL_LETTERS := $(foreach i,a b c d e f g,$(i))
$(info all letters, $(ALL_LETTERS))

LIST := SOURCES HOME PWD
$(foreach i,$(LIST),\
	$(if $($i),\
		$(info $(i) has value $($(i))),\
		$(info $(i) has no value)\
	)\
)

# TODO:
# $(call grep-string,key,word-list)

$(info )
