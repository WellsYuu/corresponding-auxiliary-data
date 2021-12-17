#!/bin/bash -e
cd "`dirname $0`"
. ../bin/env-set.sh
. ./pom.sh

#1. download war, ready env
echo "deploy time: $work_time"
mkdir -p war/
war=war/$pom_a-$pom_v.war
download_path="$nexus_redirect?r=$pom_r&g=$pom_g&a=$pom_a&v=$pom_v&e=war"
wget  $download_path -O $war

deploy_war() {
        target_d=war/${pom_a}-${pom_v}-$work_time
        target_dir=`pwd`/$target_d
        if [ ! -f "$war" ]; then
                echo "war not exist: $war"
                exit 1
        fi
        unzip -q $war -d $target_dir
        cp -r app-conf/* $target_dir/WEB-INF/classes/
        rm -rf appwar
        ln -sf $target_d appwar

        ./tomcat.sh stop

        target_ln=`pwd`/appwar
        echo '<?xml version="1.0" encoding="UTF-8" ?>
<Context docBase="'$target_ln'" allowLinking="true">
</Context>' > conf/Catalina/localhost/ROOT.xml
        ./tomcat.sh start
}

deploy_war
