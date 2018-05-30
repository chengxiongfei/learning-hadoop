#!/bin/sh
# This file is used to generate the BuildStamp.java class that
# records the user, url, revision and timestamp.
version=$1
revision=`svn info | sed -n -e 's/Last Changed Rev: \(.*\)/\1/p'`
url=`svn info | sed -n -e 's/URL: \(.*\)/\1/p'`
user=`whoami`
date=`date`
mkdir -p build/src/org/apache/hadoop
cat << EOF | \
  sed -e "s/VERSION/$version/" -e "s/USER/$user/" -e "s/DATE/$date/" \
      -e "s|URL|$url|" -e "s/REV/$revision/" \
      > build/src/org/apache/hadoop/package-info.java
/*
 * Generated by src/saveVersion.sh
 */
@HadoopVersionAnnotation(version="VERSION", revision="REV", 
                         user="USER", date="DATE", url="URL")
package org.apache.hadoop;
EOF
