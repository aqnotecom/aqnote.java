#/bin/bash

HADOOP_HOME="/usr/mytools/hadoop"

INPUT="/home/madding/workspace/hadoop/mapreduce/input"

OUTPUT="/home/madding/workspace/hadoop/mapreduce/output"


$HADOOP_HOME/bin/hadoop fs -rmr $OUTPUT

$HADOOP_HOME/bin/hadoop jar tools.hadoop-1.0-SNAPSHOT.jar com.madding.shared.hadoop.offer.MemberOfferCount $INPUT $OUTPUT



#/bin/bash

##HADOOP_HOME="/home/admin/hadoop"

##INPUT="/group/alibaba-dw-cbu/hive/bds_ast_cn_offer/hp_dw_end_date=3000-12-31/"

##OUTPUT="/group/b2b-rc/offer/memberoffercount"

##$HADOOP_HOME/bin/hadoop fs -rmr $OUTPUT

##$HADOOP_HOME/bin/hadoop jar memberoffercount.jar com.madding.shared.hadoop.examples.MemberOfferCount $INPUT $OUTPUT