# Get current dir of makefile
local_dir := $(dir $(word $(words $(MAKEFILE_LIST)), $(MAKEFILE_LIST)))

include lib.mk
program = $(out_dir)/prog
