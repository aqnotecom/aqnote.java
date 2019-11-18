#/bin/bash

LIBDIR="/home/madding/workspace/hadoop/mdp/lib"

HADOOP_HOME="/usr/mytools/hadoop"

INPUT="/home/madding/workspace/hadoop/mdp/input"

OUTPUT="/home/madding/workspace/hadoop/mdp/output"

LIB=""
for x in `ls $LIBDIR`
do
   if [ "$LIB" = "" ]; then
       LIB=$LIBDIR/$x
   else
       LIB="$LIBDIR/$x,$LIB"
   fi
done

$HADOOP_HOME/bin/hadoop fs -rmr $OUTPUT

$HADOOP_HOME/bin/hadoop jar ./tools.hadoop-1.0-SNAPSHOT.jar com.madding.shared.hadoop.offer.MdpKV2 \
$INPUT \
$OUTPUT \
-libjars \
$LIB