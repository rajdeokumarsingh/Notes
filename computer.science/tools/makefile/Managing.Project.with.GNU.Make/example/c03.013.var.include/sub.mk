dir := /usr/bin/
editor := vim
browser += chromium

$(info environment variable HOME, $(HOME))

# FIXME: why can see unexported variable
$(info upper mk variable UPPER, $(UPPER))
$(info upper mk exported variable UPPER_EXPORT, $(UPPER_EXPORT))
