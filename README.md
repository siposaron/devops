# Devops Kubernetes

There are 2 applications communicating with each other via REST endpoints.

The Sensor app is sending generated status reports on actual state to the Aggregator app.

The Aggregator exposes Prometheus endpoint for scraping, also provides app specific metrics to Prometheus via Counter and Gauge.

Grafana is responsible for visualising the Prometheus metrics.

## Deploy to kubernetes

Go into the `kubernetes` folder and issue the following commands:

```
kubectl create -f 1-aggregator.yml
kubectl create -f 2-sensor.yml
kubectl create -f 3-prometheus.yml
kubectl create -f 4-grafana.yml
```

If you have Openshift just issue `oc` command instead of `kubectl`.

