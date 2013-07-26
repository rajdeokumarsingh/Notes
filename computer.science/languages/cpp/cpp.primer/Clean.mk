.PHONY: clean
clean:
	for i in `ls -d ch*`;do echo $$i; make -C $$i clean;done;
