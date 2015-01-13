#!/bin/bash

# author: madding.lip
# date: 2010.08.25
# function: mutli call

for i in `seq 1 1000`; do

java com/madding/shared/bugs/hashmapdeadloop/HashMapLoop

done

exit 0
