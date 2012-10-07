
# Figure out where we are.
define my-dir
$(strip \
  $(eval md_file_ := $$(lastword $$(MAKEFILE_LIST))) \
  $(if $(filter $(CLEAR_VARS),$(md_file_)), \
    $(error LOCAL_PATH must be set before including $$(CLEAR_VARS)) \
    , \
    $(patsubst %/,%,$(dir $(md_file_))) \
   ) \
 )
endef

BASE_PATH := $(call my-dir)
LOCAL_PATH := $(BASE_PATH)
$(info $(MAKEFILE_LIST))
$(info $(BASE_PATH))

GEN := \
       $(BASE_PATH)/bindings/V8SQLTransactionSyncCallback.h

$(GEN): PRIVATE_PATH := $(LOCAL_PATH)
$(GEN): PRIVATE_CUSTOM_TOOL = SOURCE_ROOT=$(PRIVATE_PATH) perl -I$(PRIVATE_PATH)/bindings/scripts $(PRIVATE_PATH)/bindings/scripts/generate-bindings.pl --defines "$(FEATURE_DEFINES) LANGUAGE_JAVASCRIPT" --generator V8 --include dom --include html --include storage --outputdir $(dir $@) $<
$(GEN): $(BASE_PATH)/bindings/V8%.h : $(LOCAL_PATH)/storage/%.idl $(js_binding_scripts)
	@echo gen gen ...

LOCAL_GENERATED_SOURCES += $(GEN) $(GEN:%.h=%.cpp)

# We also need the .cpp files, which are generated as side effects of the
# above rules.  Specifying this explicitly makes -j2 work.
$(patsubst %.h,%.cpp,$(GEN)): $(BASE_PATH)/bindings/%.cpp : $(BASE_PATH)/bindings/%.h

test : Makefile
	@echo gg gg ...
