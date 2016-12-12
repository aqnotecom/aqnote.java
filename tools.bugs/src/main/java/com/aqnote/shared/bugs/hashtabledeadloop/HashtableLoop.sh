#!/bin/bash

for i in `seq 1 2000`; do

java com/madding/shared/bugs/hashmapdeadloop/HashtableLoop

done

exit 0
