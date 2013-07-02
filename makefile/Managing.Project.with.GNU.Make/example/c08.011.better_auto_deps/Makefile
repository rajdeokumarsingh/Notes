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

# XXX: gcc -MM :remove system dependencies
# XXX: gcc -MP : add fake rule for headers
# $(call make-depend,source-file,obj-file,dep-file)
define make-depend
	$(shell test -d $(out_dir) || mkdir -p $(out_dir))
	$(CC) -MM -MP -M $(CPPFLAGS) $1 | \
	sed 's,$(notdir $2) *:,$2 $3: ,' > $3.tmp; \
	mv $3.tmp $3
endef

.PHONY: all create-out-dir
all:

-include $(shell find . -name 'module.mk')

all: $(program)

$(program) : $(out_dir)/main.o $(libraries)
	$(CC) $(CPPFLAGS) $^ -o $@

$(out_dir)/%.o : %.cpp
	$(call make-depend,$<,$@,$(subst .o,.d,$@))
	$(CC) $(CPPFLAGS) -c $< -o $@

ifneq "$(MAKECMDGOALS)" "clean"
-include $(addprefix $(out_dir)/,$(notdir $(subst .cpp,.d,$(sources))))
endif

.PHONY: clean
clean:
	@rm -rf $(objects) $(program) $(deps) $(libraries) $(out_dir)
