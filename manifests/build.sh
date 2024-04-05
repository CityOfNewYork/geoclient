#!/usr/bin/env bash

#
# Creates Kubernetes YAML manifests for release in AKS using Kustomize.
#

set -Eeuo pipefail

this_dir="$(dirname "$(readlink -vf "$BASH_SOURCE")")"
this_file="$(basename "$0")"

cd "${this_dir}"

echo "RELEASE_DIR=${RELEASE_DIR:-NULL}"
#
# Globals
#

# Used by compare.sh to be able to generate overlays for a different
# project revision checked out in a different local directory.
OVERLAY_BASE=${OVERLAY_BASE:-${this_dir}}
OVERLAY_DIR=
RELEASE_DIR="${RELEASE_DIR:-${this_dir}/release}"
RELEASE_FILE="release.yaml"
SPLIT=

echo "RELEASE_DIR=${RELEASE_DIR}"
#
# Functions
#
clean() {
    if [[ -d "$RELEASE_DIR" ]]; then
        rm -f "${RELEASE_DIR}/${RELEASE_FILE}"
        rm -f "${RELEASE_DIR}/configmap-00.yaml"
        rm -f "${RELEASE_DIR}/configmap-01.yaml"
        rm -f "${RELEASE_DIR}/deployment.yaml"
        rm -f "${RELEASE_DIR}/hpa.yaml"
        rm -f "${RELEASE_DIR}/ingress.yaml"
        rm -f "${RELEASE_DIR}/pvc.yaml"
        rm -f "${RELEASE_DIR}/service.yaml"
        # Check for unknown files and bail if any exist.
        local nfiles=($(ls -1 "$RELEASE_DIR" | wc -l))
        if [[ $nfiles -gt 0 ]]; then
            die "Refusing to rm directory $RELEASE_DIR because it contains $nfiles unknown files(s)."
        fi
        rm -R "$RELEASE_DIR"
    fi
}

die() {
    echo "$@"
    exit 1
}

build() {
    local resolved_overlay_dir="${OVERLAY_DIR}"
    if [[ ! "${resolved_overlay_dir:0:1}" = "/" ]]; then
        # Prepend OVERLAY_BASE if OVERLAY_DIR is not an absolute path
        resolved_overlay_dir="${OVERLAY_BASE}/${OVERLAY_DIR}"
    fi
    printf ' -> kustomize build \\\n -> \t%s \\\n -> \t%s\n' "${resolved_overlay_dir}" "-o ${RELEASE_DIR}/${RELEASE_FILE}"
    kustomize build "${resolved_overlay_dir}" -o "${RELEASE_DIR}/${RELEASE_FILE}"
    if [[ ! -z "$SPLIT" ]]; then
        pushd "$RELEASE_DIR" > /dev/null
        cat "$RELEASE_FILE" | csplit - -f obj --suppress-matched  "/---/" "{*}"
        shopt -s nullglob
        local arr=(obj*)
        for f in "${arr[@]}"; do
            local kind="$(egrep '^kind: ' $f )"
            kind="${kind:6}"
            kind="${kind,,}"
            local fname=
            case $kind in
                configmap)
                    num="${f:3}"
                    fname="${kind}-${num}.yaml"
                    ;;
                horizontalpodautoscaler)
                    fname="hpa.yaml"
                    ;;
                persistentvolumeclaim)
                    fname="pvc.yaml"
                    ;;
                *)
                    fname="${kind}.yaml"
                    ;;
            esac
            mv "$f" "${fname}"
        done
        popd > /dev/null
    fi
    echo " -> build complete."
}

main() {
    if [ $# -eq 0 ]; then
        usage
        exit 0
    fi
    while [ $# -gt 0 ]; do
        case "$1" in
            -h|--help)
                usage | more && exit 0;
                ;;
            -r*)
                RELEASE_DIR="${1##-r}"; shift
                ;;
            --release=*)
                RELEASE_DIR="${1##--release=}"; shift
                ;;
            -f*)
                RELEASE_FILE="${1##-f}"; shift
                ;;
            --file=*)
                RELEASE_FILE="${1##--file=}"; shift
                ;;
            -s|--split)
                SPLIT="true"; shift
                ;;
            -*|"")
                die "Invalid option: $1"; shift
                ;;
            *)
                OVERLAY_DIR="$1"; shift
                ;;
        esac
    done
    [[ -z "$OVERLAY_DIR" ]] && \
        die "Missing required kustomize overlay <directory> argument."
    [[ ! -d "$OVERLAY_DIR" ]] && \
        die "Directory $OVERLAY_DIR does not exist."

    #printf '%s\n--release=%s\n--file=%s\n--split=%s\n%s\n' "$this_file" "$RELEASE_DIR" "$RELEASE_FILE" "$SPLIT" "$OVERLAY_DIR"
    clean
    prepare
    build
}

prepare() {
    mkdir -p "$RELEASE_DIR"
}

usage() {
    local this_file
    this_file="$(basename "${BASH_SOURCE[0]}")"
cat <<- EOF

Usage: ${this_file} [OPTIONS] <directory>

<directory> (required)

            Builds kubernetes manifests by invoking kustomize with the
            given overlay <directory>.

            The overlay <directory> can be specified as an absolute or
            relative path. However, when running this script from anywhere
            other than the directory where this file
            is located, the value given MUST be relative to this file.

    Examples:

            # Using an absolute path from sibling of <project_root>:
            ../<project_root>/mainfests/${this_file}  \\
                /opt/<project_root>/manifests/overlays/app/dev/no-pvc

            # Using a relative path from this project's root directory:
            ./manifests/build.sh overlays/app/dev/no-pvc


            WARNING: The RELEASE_DIR will be _deleted_ if it exists.

  Options:

    -r<dir>, --release=<dir> (optional)

            Name of the <dir> where generated manifest files should
            be created. If not given, defaults to:

            <project_root>/manifests/release

            WARNING: This directory will be _deleted_ if it exists.

    -f<file>, --file=<file> (optional)

            Name of the <file> created by the kustomize command.
            If not given, defaults to ${RELEASE_FILE}.

    -s, --split (optional)

            Splits the generated kustomize YAML file into individual
            YAML files for each uniquely named kubernetes object:

            configmap-00.yaml (ConfigMap)
            configmap-01.yaml (ConfigMap)
            deployment.yaml   (Deployment)
            hpa.yaml          (HorizontalPodAutoscaler)
            pvc.yml           (PersistentVolumeClaim)

            NOTE: This option requires that csplit be installed.

    -h, --help
                Show this usage message and exit

EOF
}

main "$@"
