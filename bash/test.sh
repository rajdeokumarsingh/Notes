#!/bin/bash
#!/usr/bin/more

echo $0 $1
echo $#
echo $*
echo $@

{
for i in $*
do
    echo $i
done

for i in $@
do
    echo $i
done
} > ./testme
