#
# Copyright [$tody.year] [Wales Yu of copyright owner]
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

APP_HOME=${PWD}/..
MAIN_CLASS_NAME="com.dongnao.chat.server.ChatServer"


JVM_ARGS="-DlogDir=$APP_HOME/logs"
if [ -r app.vmoptions ];then
JVM_ARGS="$JVM_ARGS `tr '\n' ' ' < app.vmoptions`"
fi

process_Id=`/usr/sbin/lsof -i tcp:'{print $2}'|sed '/PID/d'`


PATH="./classes"
CLASSPATH=$APP_HOME/$PATH
for i in $APP_HOME/lib/*.jar;do
CLASSPATH="$i:$CLASSPATH"
done

export CLASSPATH


#echo "java_home is $JAVA_HOME"
#echo "APP_HOME is $APP_HOME \n"
#echo "CLASSPATH is $CLASSPATH \n"
#echo "JVM_ARGS is $JVM_ARGS "
#echo "MAIN_CLASS_NAME is $MAIN_CLASS_NAME"
#echo "process_Id is $process_Id \n"
#echo "$JAVA_HOME/bin/java $JVM_ARGS -classpath $CLASSPATH $MAIN_CLASS_NAME &"


start(){
    printf 'RenderServer is starting...\n'
    if [ $process_Id ];then
       kill -9 $process_Id
       sleep 1
    fi 

   $JAVA_HOME/bin/java $JVM_ARGS -classpath $CLASSPATH $MAIN_CLASS_NAME &
}

restart(){
    printf 'RenderServer is restart...\n'
    if [ $process_Id ];then
       kill -9 $process_Id
       sleep 1
    fi 

   $JAVA_HOME/bin/java $JVM_ARGS -classpath $CLASSPATH $MAIN_CLASS_NAME &
}

stop (){
   printf 'RenderServer is stoping...\n'
   if [ $process_Id ];then
     kill -9 $process_Id 
   fi 
}
 



case "$1" in
start)
  start
  ;;
restart)
  restart
  ;;
stop)
  stop
;;
*)
  printf 'Usage:%s {start|restart|stop}\n'
  printf 'app.vmoptions is configuration for  JVM \n '
  exit 1
  ;;
esac
