#!/bin/bash

# author: madding.lip
# date: 2012.06.13
# function: limit max run

JAVA_OPTS=" -Xms32m -Xmx32m -XX:MaxPermSize=32m"

java $JAVA_OPTS com.madding.shared.bugs.oom.OutOfMemory


exit 0
