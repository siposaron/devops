.PHONY: release-agent start-agent

tag ?= latest
release-agent: 
	docker build --tag siposaron/jenkins-agent-j11-maven:${tag} .
	docker push siposaron/jenkins-agent-j11-maven:${tag}

tag ?= latest
start-agent:
	sh start-agent.sh ${tag}

