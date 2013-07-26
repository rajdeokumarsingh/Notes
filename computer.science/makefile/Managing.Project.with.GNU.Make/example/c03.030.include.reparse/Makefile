# No foo.mk at first
include foo.mk
$(warning finishing include foo.mk)

# generate foo.mk
foo.mk : bar.mk
	@echo generating foo.mk
	m4 --define=FILENAME=$@ bar.mk > $@

# include foo.mk again

.PHONY: clean
clean:
	@rm -rf foo.mk
