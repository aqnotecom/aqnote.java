#!/bin/bash


HADOOP_HOME="/usr/mytools/hadoop"
JAVA_HOME="/usr/mytools/java6"
WORKSPACE="/home/madding/workspace/hadoop/mapreduce"


$JAVA_HOME/bin/javac -cp $HADOOP_HOME/hadoop-core-1.0.3.jar:$HADOOP_HOME/lib/commons-cli-1.2.jar -d $WORKSPACE/classes/ $WORKSPACE/src/MemberOfferCount.java

$JAVA_HOME/bin/jar -cvf tools.hadoop-1.0-SNAPSHOT.jar -C $WORKSPACE/classes/ .

exit

