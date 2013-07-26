.PHONY: clean
clean:
	for i in `ls -d c*`;do echo $$i; make -C $$i clean;done;
