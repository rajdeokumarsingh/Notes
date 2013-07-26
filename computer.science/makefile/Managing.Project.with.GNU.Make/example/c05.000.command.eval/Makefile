STRIP := true

define strip-program
	@echo strip $1
endef

complex_script:
	@echo gcc -c this.cpp -o this.o
ifdef STRIP
	$(call strip-program, this.o)
endif
	$(if $(PACK),@echo pack,@echo no pack)
	$(warning Final size: $(shell ls -s $@))
			
