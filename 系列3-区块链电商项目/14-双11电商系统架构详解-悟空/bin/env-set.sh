#!/bin/bash -e
#
# Copyright 2021-2022 the original author or authors
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

#当前时间
export now_time=$(date +%Y-%m-%d_%H-%M-%S)
#整个期间的开始时间
[ -z "$work_time" ] && export work_time=$(date +%Y-%m-%d_%H-%M-%S)

if [ -z "$g_env_set" ]; then
	unset JRE_HOME JAVA_HOME CLASSPATH
	export nexus_redirect="http://192.168.0.15:7777/nexus/service/local/artifact/maven/redirect"
	export data_home="/root/svr"
	export server_home="$data_home/services"
	export JAVA_HOME="$data_home/jdk"
	export CATALINA_HOME="$data_home/apache-tomcat"
	export CATALINA_BASE="$CATALINA_HOME"
	export PATH=$JAVA_HOME/bin:$PATH
	export g_env_set=true
fi

#能够输入2个grep的字符串用来查找
proc_find() {
        if [ "$#" = 2 ]; then
                ps -eo pid,cmd|grep -v 'cmd\|grep'|grep "$1"|grep "$2"|sed 's/^ *\(.*\) *$/\1/'
        else 
                ps -eo pid,cmd|grep -v 'cmd\|grep'|grep "$1"|sed 's/^ *\(.*\) *$/\1/' 
        fi
}
#能够输入2个grep的字符串用来查找
proc_pid() {
        proc_find "$@" | cut -d ' ' -f1
}
#$1: 要重试的次数，超过此次数后使用kill -9
#$2: 能够输入2个grep的字符串用来查找
proc_kill() {
        grep_args="${@:2}"
        for ((i=1;i<=$1;++i))
        do
                pids=(`proc_pid "$grep_args"`)
                [ ${#pids[@]} = 0 ] && return 0

                echo "n=$i kill ${pids[@]}"
                kill ${pids[@]}
                sleep 1s
        done
        pids=(`proc_pid "$grep_args"`)
        [ ${#pids[@]} = 0 ] || kill -9 ${pids[@]}
}

tomcat_start0() {
	
	[ "$JAVA_OPTS" = "" ] && export JAVA_OPTS="-server -XX:MaxPermSize=128m -Xms512m -Xmx512m"	
	[ "`echo "$JAVA_OPTS"|grep '\-Djava.security.egd='`" = "" ] && export JAVA_OPTS="$JAVA_OPTS -Djava.security.egd=file:/dev/./urandom"
#防止X11导致awt在X11关闭后异常
	export JAVA_OPTS="-Djava.awt.headless=true $JAVA_OPTS -$grep_server"
	echo "START $grep_server: JAVA_OPTS=$JAVA_OPTS"
	"$CATALINA_HOME"/bin/startup.sh
}
tomcat_stop0() {
	proc_kill 3 "$grep_server"
}
tomcat_status0() {
	tmp=`proc_find "$grep_server"`
	if [ -z "$tmp" ]; then
		echo "$grep_server stopped."
	else
		echo "$grep_server running: $tmp"
	fi
}

tomcat() {
	if [ "$1" = "" ]; then
		printf 'Usage: %s {start|stop|restart|status} ????\n' "$prog"
		exit 1
	fi
	if [ "$2" = "" ]; then
		echo 'java -Dmykey=${mykey} miss: mykey'
		exit 1
	fi
	if [ "$CATALINA_BASE" = "" ]; then
		echo 'miss: $CATALINA_BASE='
		exit 1
	fi
	
	cd "$CATALINA_BASE"
	export grep_server="Dmykey=${2}x"
	case "$1" in
	start)
		tmp=`proc_find "$grep_server"`
		if [ -z "$tmp" ]; then
			tomcat_start0
		else
			echo "$grep_server already running: $tmp"
		fi
		sleep 1s
		tomcat_status0
		;;
	stop)
		tomcat_stop0
		tomcat_status0
		;;
	status)
		tomcat_status0
		;;
	restart)
		tomcat_stop0
		tomcat_start0
		sleep 1s
		tomcat_status0
		;;
	*)
		printf 'Usage: %s {start|stop|restart|status} ????\n' "$prog"
		exit 1
		;;
	esac
}
