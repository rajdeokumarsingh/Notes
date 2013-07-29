#!/bin/bash

for i in `cat tt`
do
    echo $i
    dest="string/"$i
    echo $dest
    mkdir -p $dest
    cp $i"/strings.xml" $dest
    cp -rf $i"/arrays.xml" $dest
done
