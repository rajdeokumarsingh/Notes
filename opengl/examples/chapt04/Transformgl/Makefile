ifeq (${TERM_PROGRAM}, Apple_Terminal)
CPPFLAGS += \
    -I/Developer/SDKs/MacOSX10.6.sdk/usr/X11/include/ \
    -L/Developer/SDKs/MacOSX10.6.sdk/usr/X11/lib 
endif

ifeq (${DESKTOP_SESSION}, gnome)
CPPFLAGS += -D__LINUX__
endif

CPPFLAGS += -I../../shared/

main : *.cpp ../../shared/*.cpp
	g++ ${CPPFLAGS} $^ -lglut -lGL -lGLU -lGLEW -o $@

.PHONY : clean

clean:
	@rm -rf main
