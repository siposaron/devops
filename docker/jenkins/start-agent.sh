# Starts a Jenkins agent in docker container. env.conf file is needed besides this shell script.
FILE=env.conf
if [ ! -f "$FILE" ]; then
    echo "$FILE does not exist, create it besides start-agent.sh with Jenkins variables: 
        JENKINS_SECRET=
        JENKINS_TUNNEL=
        JENKINS_AGENT_NAME=
        JENKINS_NAME=
        JENKINS_AGENT_WORKDIR=
        JENKINS_URL="
    exit -1;
fi

docker run --env-file env.conf --name jenkins-agent --init siposaron/jenkins-agent-j11-maven:$1
