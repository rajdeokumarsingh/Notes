#!/bin/bash

subdirs="bionic build frameworks hardware system dalvik"

working_dir=`pwd`
rm $working_dir/cscope.* $working_dir/tags

for m in $subdirs
do
	echo $working_dir/$m

	find -H $working_dir/$m -name "*.h" -o -name "*.hpp" -o -name "*.c" -o -name "*.cpp" -o -name "*.cxx" -o -name "*.adl" -o \
		-name "*.mk" -o -name "*.rc" -o -name "*.sh" -o -name "*.conf" -o -name "*.inl" -o -name "*.in" -o -name "*.cc" -o -name \
		"Makefile" -o -name "*.java" -o -name "*.xml" -o -name "*.aidl" -o -name "*.s" -o -name "*.S" >> $working_dir/cscope.files
done

ctags --extra=f -R $subdirs
cscope -Rbkq -i cscope.files

