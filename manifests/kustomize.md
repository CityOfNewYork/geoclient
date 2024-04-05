# Kustomize HOWTO

## Scripts

* `build.sh`: runs kustomize and optionally parses out individual object manifests.
* `cluster-state.sh`: generates backup of existing object YAML definitions.

## Overlays

### Main Overlays

| Path                           | Component   | Generated Objects             |
|--------------------------------|-------------|-------------------------------|
| ./overlays/app/dev/no-pvc      | development | deployment, hpa, service      |
| ./overlays/app/dev/pvc         | development | deployment, hpa, service, pvc |
| ./overlays/app/prd/no-pvc      | production  | deployment, hpa, service      |
| ./overlays/app/prd/pvc         | production  | deployment, hpa, service, pvc |
| ./overlays/app/minikube/no-pvc | minikube    | deployment, hpa, service      |
| ./overlays/app/minikube/pvc    | minikube    | deployment, hpa, service, pvc |

### Ingress Overlays

| Path                         | Component   | Generated Objects |
|------------------------------|-------------|-------------------|
| ./overlays/ingress-nginx/dev | development | ingress           |
| ./overlays/ingress-nginx/prd | production  | ingress           |

### PVC Overlays

| Path                         | Component   | Generated Objects |
|------------------------------|-------------|-------------------|
| ./overlays/pvc-azurefile/dev | development | pvc               |
| ./overlays/pvc-azurefile/prd | production  | pvc               |
| ./overlays/pvc-hostpath      | minikube    | pvc               |

```sh
├── base
│   ├── docker
│   ├── properties
│   └── resources
├── components
│   ├── development
│   ├── minikube
│   ├── production
│   ├── pvc-azurefile
│   └── pvc-hostpath
└── overlays
    ├── app
    │   ├── dev
    │   │   ├── no-pvc
    │   │   └── pvc
    │   ├── minikube
    │   │   ├── base
    │   │   ├── no-pvc
    │   │   └── pvc
    │   └── prd
    │       ├── no-pvc
    │       └── pvc
    ├── ingress-nginx
    │   ├── base
    │   ├── dev
    │   └── prd
    ├── pvc-azurefile
    │   ├── base
    │   ├── dev
    │   └── prd
    └── pvc-hostpath
```

### Minikube

```sh
 kubectl expose deployment geoclient-v2-blue --type=NodePort --port=8080
```

```sh
minikube service geoclient-v2-blue -n gis-apps --url
```

```sh
minikube dashboard
```
