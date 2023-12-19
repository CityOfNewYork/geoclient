# Kustomize HOWTO

## Scripts

* `build.sh`: runs kustomize and optionally parses out individual object manifests.
* `cluster-state.sh`: generates backup of existing object YAML definitions.

## Overlays

### Main Overlays

| Path                      | Component   | Generated Objects             |
|---------------------------|-------------|-------------------------------|
| ./overlays/app/dev/no-pvc | development | deployment, hpa, service      |
| ./overlays/app/dev/pvc    | development | deployment, hpa, service, pvc |
| ./overlays/app/prd/no-pvc | production  | deployment, hpa, service      |
| ./overlays/app/prd/pvc    | production  | deployment, hpa, service, pvc |

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

```sh
overlays/
├── app
│   ├── dev
│   │   ├── no-pvc
│   │   └── pvc
│   └── prd
│       ├── no-pvc
│       └── pvc
├── ingress-nginx
│   ├── base
│   ├── dev
│   └── prd
├── minikube
└── pvc-azurefile
    ├── base
    ├── dev
    └── prd
```
