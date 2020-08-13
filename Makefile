.PHONY: build build-aggregator build-sensor docker-build docker-push release release-aggregator docker-release-aggregator docker-release-sensor

aggregator_ver := $(shell mvn -f ./aggregator help:evaluate -Dexpression=project.version | grep -e '^[^\[]')
sensor_ver := $(shell mvn -f ./sensor help:evaluate -Dexpression=project.version | grep -e '^[^\[]')
app_ver := $(shell cat Metadata | grep 'VERSION=' | cut -f2- -d=)

build: build-aggregator build-sensor

build-aggregator:
	echo 'Building aggregator'
	mvn -f ./aggregator clean install
	
build-sensor:
	echo 'Building sensor'
	mvn -f ./sensor clean install

docker-build:	
	echo 'Building aggregator docker image'
	docker build --tag siposaron/aggregator:$(aggregator_ver) ./aggregator
	echo 'Building sensor docker image'
	docker build --tag siposaron/sensor:$(sensor_ver) ./sensor

docker-push:
	echo 'Pushing docker images'
	docker push siposaron/aggregator:$(aggregator_ver)
	docker push siposaron/sensor:${sensor_ver}

release: build docker-build docker-push

docker-release-aggregator:
	echo 'Building aggregator docker image'
	docker build --tag siposaron/aggregator:$(aggregator_ver) ./aggregator
	echo 'Pushing aggregator docker image'
	docker push siposaron/aggregator:$(aggregator_ver)

docker-release-sensor:
	echo 'Building sensor docker image'
	docker build --tag siposaron/sensor:$(sensor_ver) ./sensor
	echo 'Pushing sensor docker image'
	docker push siposaron/sensor:${sensor_ver}

release-aggregator:
	echo 'Releasing aggregator'
	mvn -f ./aggregator clean install
	docker build --tag siposaron/aggregator:$(aggregator_ver) ./aggregator
	docker push siposaron/aggregator:$(aggregator_ver)

