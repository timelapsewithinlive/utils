#!/bin/bash
export JAVA_HOME=${JAVA_HOME8}
export PATH=$JAVA_HOME/bin:$PATH

cd `dirname $0`
DEPLOY_DIR=`pwd`
LOGS_DIR=$DEPLOY_DIR/logs
if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi

TINGYUN_AGENT_PATH="/data/agent/tingyun/tingyun-agent-java.jar"
if [ -f $TINGYUN_AGENT_PATH ]; then
    JAVA_OPTS="$JAVA_OPTS -javaagent:$TINGYUN_AGENT_PATH"
    export JAVA_OPTS;
fi

SKYWALKING_AGENT_PATH="/data/agent/agent-skywalking/skywalking-agent.jar";
if [ -f $SKYWALKING_AGENT_PATH ]; then
    JAVA_OPTS="$JAVA_OPTS -javaagent:$SKYWALKING_AGENT_PATH"
    export JAVA_OPTS;
fi

SPRING_BOOT_OPTS="--spring.profiles.active=pro"
STDOUT_FILE=$LOGS_DIR/stdout.log
nohup java -jar $JAVA_OPTS $DEPLOY_DIR/price-app.jar $SPRING_BOOT_OPTS >> $STDOUT_FILE 2>&1 &

echo "OK!"
PIDS=`ps -ef | grep java | grep "$DEPLOY_DIR" |grep -v grep | awk '{print $2}'`
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"