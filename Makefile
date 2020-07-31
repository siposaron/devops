.PHONY: build docker-build docker-push release release-aggregator

aggregator_ver := $(shell mvn -f ./aggregator help:evaluate -Dexpression=project.version | grep -e '^[^\[]')
sensor_ver := $(shell mvn -f ./sensor help:evaluate -Dexpression=project.version | grep -e '^[^\[]')

build:
	mvn -f ./aggregator clean install
	mvn -f ./sensor clean install

docker-build:	
	docker build --tag siposaron/sensor:$(sensor_ver) ./sensor
	docker build --tag siposaron/aggregator:$(aggregator_ver) ./aggregator

docker-push:
	docker push siposaron/aggregator:$(aggregator_ver)
	docker push siposaron/sensor:${sensor_ver}

release: build docker-build docker-push

release-aggregator:
	mvn -f ./aggregator clean install
	docker build --tag siposaron/aggregator:$(aggregator_ver) ./aggregator
	docker push siposaron/aggregator:$(aggregator_ver)