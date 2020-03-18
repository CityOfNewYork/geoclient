# Geoclient API

[Geoclient](https://github.com/mlipper/geoclient) provides a RESTful API for using the City of New York's official geocoder,
[Geosupport](http://www.nyc.gov/html/dcp/html/bytes/applbyte.shtml#geocoding_application). Geosupport is written and maintained
by the [Department of City Planning](https://www1.nyc.gov/site/planning/index.page).
and can be used to generates traffic during canary analysis when configured as a webhook.

## Prerequisites

* Kubernetes >= 1.14
* [Geosupport](http://www.nyc.gov/html/dcp/html/bytes/applbyte.shtml#geocoding_application) Linux distribution 20a_20.1 or greater

## Installing the Chart

Add Geoclient Helm repository:

```console
helm repo add geoclient https://geoclient.app
```

To install the chart with the release name `20a_20.1-geoclient`:

```console
helm upgrade -i 20a_20.1-geoclient geoclient
```

## Uninstalling the Chart

To uninstall/delete the `20a_20.1-geoclient` deployment:

```console
helm delete --purge 20a_20.1-geoclient
```

The command removes all the Kubernetes components associated with the chart and deletes the release.

## Configuration

The following tables lists the configurable parameters of the geoclient chart and their default values.

Parameter | Description | Default
--- | --- | ---
`image.repository` | Image repository | `mlipper/geoclient`
`image.pullPolicy` | Image pull policy | `IfNotPresent`
`image.tag` | Image tag | `<VERSION>`
`replicaCount` | Desired number of pods | `1`
`serviceAccountName` | Kubernetes service account name | `none`
`resources.requests.cpu` | CPU requests | `10m`
`resources.requests.memory` | Memory requests | `64Mi`
`tolerations` | List of node taints to tolerate | `[]`
`affinity` | node/pod affinities | `node`
`nodeSelector` | Node labels for pod assignment | `{}`
`service.type` | Type of service | `ClusterIP`
`service.port` | ClusterIP port | `80`
`cmd.timeout` | Command execution timeout | `1h`
`logLevel` | Log level can be debug, info, warning, error or panic | `info`

Specify each parameter using the `--set key=value[,key=value]` argument to `helm install`. For example,

```console
helm install 20a_20.1/geoclient --name 20a_20.1-geoclient
```

Alternatively, a YAML file that specifies the values for the above parameters can be provided while installing the chart. For example,

```console
helm install 20a_20.1/geoclient --name 20a_20.1-geoclient -f values.yaml
```

> **Tip**: You can use the default [values.yaml](values.yaml)
