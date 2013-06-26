# build multiple objects (hello and hi)
.PHONY : all
all : hello hi

hello : hello.cpp
	g++ $< -o $@

hi : hi.cpp
	g++ $< -o $@

# invoking shell script
.PHONY : make-doc
make-doc : df
	@echo "make doc..."

.PHONY : df
df :
	@df -k .

.PHONY : clean
clean:
	rm -rf hello hi
