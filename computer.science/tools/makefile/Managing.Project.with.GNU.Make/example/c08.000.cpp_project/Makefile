program :=
sources :=
libraries :=
out_dir := ./out

objects :=
deps :=

CC := g++
CPPFLAGS += -I./include 

SRC_DIR := lib app
vpath %.cpp $(shell find $(SRC_DIR) -type d)
vpath %.h $(include)

.PHONY: all create-out-dir
all:

-include $(shell find . -name 'module.mk')

all: $(program)

$(program) : $(out_dir)/main.o $(libraries)
	$(CC) $(CPPFLAGS) $^ -o $@

$(out_dir)/%.o : %.cpp
	$(CC) $(CPPFLAGS) -c $< -o $@

ifneq "$(MAKECMDGOALS)" "clean"
-include $(addprefix $(out_dir)/,$(notdir $(subst .cpp,.d,$(sources))))
endif

$(out_dir)/%.d: %.cpp
	$(shell test -d $(out_dir) || mkdir -p $(out_dir))
	$(CC) -M $(CPPFLAGS) $< | \
	sed 's,\($(notdir $*)\.o\) *:,$(dir $@)\1 $@: ,' > $@.tmp; \
	mv $@.tmp $@

.PHONY: clean
clean:
	@rm -rf $(objects) $(program) $(deps) $(libraries) $(out_dir)
