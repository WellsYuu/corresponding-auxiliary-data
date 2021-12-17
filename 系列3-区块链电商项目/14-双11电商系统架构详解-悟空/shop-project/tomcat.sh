#!/bin/bash
if [ "`whoami`" != "root" ];then
		echo "Error: You must be apps to run this command."
		exit 1
fi

cd "`dirname $0`"
. ../bin/env-set.sh
. ./pom.sh

export CATALINA_BASE="`pwd`"
tomcat "$1" "${pom_a}-${pom_v}"
