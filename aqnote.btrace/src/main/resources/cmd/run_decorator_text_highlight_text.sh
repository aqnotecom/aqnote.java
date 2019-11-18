#!/bin/bash

LIB_DIR="/home/admin/web-deploy/target/bops-bundle-war/WEB-INF/lib"

LIB=""
for x in `ls $LIB_DIR`
do
   if [ "$LIB" = "" ]; then
       LIB=$LIB_DIR/$x
   else
       LIB="$LIB_DIR/$x:$LIB"
   fi
done

#echo $LIB

sh bin/btrace -cp $LIB $1 app/DecoratorText_HighlightText.java

exit 0