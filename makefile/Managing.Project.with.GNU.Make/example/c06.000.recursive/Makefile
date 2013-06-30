libui := lib/ui
libdb := lib/db
libcoded := lib/codec

main := app/player
libs := $(libui) $(libdb) $(libcoded)

.PHONY: all $(main) $(libs)
all: $(main)

# XXX: command line variable will pass to sub makefile automatically
# try: make -n
$(main): $(libs)
	@make -C $@

$(libs):
	@make -C $@
