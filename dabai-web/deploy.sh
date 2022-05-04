#!/bin/bash

APP_NAME=dabai-web


PROG_NAME=$0
ACTION=$1
ACTIVE=$2
APP_HOME=/data/springboot/${APP_NAME} # 从package.tgz中解压出来的jar包放到这个目录下
JAR_NAME=${APP_HOME}/target/${APP_NAME}.jar # jar包的名字
LOG_DIR=/data/logs/${APP_NAME}

if [ ${ACTIVE} == "prod" ]; then
    JAVA_OPTS="-server -Dapp.log.path=${LOG_DIR} "
elif [ ${ACTIVE} == "test" ]; then
    JAVA_OPTS="-server -Dapp.log.path=${LOG_DIR} \
	-Xmx512m -Xms512m -Xss256k \
  -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSScavengeBeforeRemark \
  -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${LOG_DIR}/dump -XX:ErrorFile=${LOG_DIR}/jvm/jvm-err-%t.log \
  -verbose:gc -XX:+DisableExplicitGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+PrintGCApplicationStoppedTime -Xloggc:${LOG_DIR}/jvm/gc-%t.log "
else
	JAVA_OPTS=" -server -Dapp.log.path=${LOG_DIR}"
fi

usage() {
    echo "Usage: $PROG_NAME {start|stop|restart} {test|prod}"
    exit 2
}

#校验参数
if [ ! $# == 2 ]; then
usage
exit
fi

if [[ "$ACTION" =~ [start|stop|restart] ]] && [[ $ACTIVE =~ [test|prod] ]]; then :
else
usage
exit
fi

# 创建出相关目录
mkdir -p ${APP_HOME}
mkdir -p ${LOG_DIR}

health_check() {
    exptime=0
    echo "checking ${HEALTH_CHECK_URL}"
    while true
        do
            status_code=`/usr/bin/curl -L -o /dev/null --connect-timeout 5 -s -w %{http_code}  ${HEALTH_CHECK_URL}`
            if [ "$?" != "0" ]; then
               echo -n -e "\rapplication not started"
            else
                echo "code is $status_code"
                if [ "$status_code" == "200" ];then
                    break
                fi
            fi
            sleep 1
            ((exptime++))

            echo -e "\rWait app to pass health check: $exptime..."

            if [ $exptime -gt ${APP_START_TIMEOUT} ]; then
                echo 'app start failed'
               exit 1
            fi
        done
    echo "check ${HEALTH_CHECK_URL} success"
}
start_application() {
    echo "starting java process"
    source /etc/profile
    nohup java ${JAVA_OPTS} -jar ${JAR_NAME} --spring.profiles.active=${ACTIVE} >/dev/null 2>&1 &
    echo "started java process"
}

stop_application() {
   checkjavapid=`ps -ef | grep java | grep ${APP_NAME} | grep -v grep |grep -v 'deploy.sh'| awk '{print$2}'`

   if [[ ! $checkjavapid ]];then
      echo -e "\rno java process"
      return
   fi

   echo "stop java process"
   times=60
   for e in $(seq 60)
   do
        sleep 1
        COSTTIME=$(($times - $e ))
        checkjavapid=`ps -ef | grep java | grep ${APP_NAME} | grep -v grep |grep -v 'deploy.sh'| awk '{print$2}'`
        if [[ $checkjavapid ]];then
            kill -9 $checkjavapid
            echo -e  "\r        -- stopping java lasts `expr $COSTTIME` seconds."
        else
            echo -e "\rjava process has exited"
            break;
        fi
   done
   echo ""
}
start() {
    start_application
#    health_check
}
stop() {
    stop_application
}
case "$ACTION" in
    start)
        start
    ;;
    stop)
        stop
    ;;
    restart)
        stop
        start
    ;;
    *)
        usage
    ;;
esac