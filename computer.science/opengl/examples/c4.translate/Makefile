ifeq (${TERM_PROGRAM}, Apple_Terminal)
CPPFLAGS += \
    -I/Developer/SDKs/MacOSX10.6.sdk/usr/X11/include/ \
    -L/Developer/SDKs/MacOSX10.6.sdk/usr/X11/lib 
endif

main : *.cpp
	g++ ${CPPFLAGS} $^ -lglut -lGL -lGLU -o $@

.PHONY : clean

clean:
	@rm -rf main
