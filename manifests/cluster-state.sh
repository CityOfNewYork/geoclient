#!/usr/bin/env bash

#
# Creates backups of existing Kubernetes objects by writing their YAML
# definitions to files in BKDIR.
#
# This script only captures objects whose definitions will be superceded
# by a new pipeline release. During a blue/green style release, they will
# not be deleted immediately but will eventually be considered inactive if
# the deployment is successful.
#
# WARNING:
# Existing backup files will be overwritten if this is run more than once
# on the same day.
#

set -Eeuo pipefail

this_dir="$(dirname "$(readlink -vf "$BASH_SOURCE")")"
this_file="$(basename "$0")"

cd "${this_dir}"

declare -A OBJS=(
    [configmap]='(pipeline-variables|runtime-environment)'
    [service]='geoclient-v2-'
    [pvc]='pvc-geosupport-'
    [hpa]='geoclient-v2-'
    [deployment]='geoclient-v2-'
    [ingress]='geoclient-v2'
)

# Create the backup directory and an environment variable to reference the
# relative path.
BKDIR="$(echo "${this_dir}/temp/$(kubectl config current-context)/$(date '+%Y-%m-%d')")"

get_objs() {
    for obj_type in "${!OBJS[@]}"; do
        local pattern="${OBJS[${obj_type}]}"
        write_yaml "$obj_type" "$pattern"
    done
}

write_yaml() {
    if [[ 'configmap' == "$obj_type" ]]; then
    local obj_type="$1"
    local pattern="$2"
    local names=( $(kubectl get "$obj_type" | egrep $pattern | cut -d' ' -f1) )
    for name in ${names[@]}; do
        kubectl get "$obj_type" "${name}" -o yaml > "${BKDIR}/${obj_type}-${name}.yaml"
    done
    printf '%s (%i written)\n' "$obj_type" "${#names[@]}"
    fi
}

main() {
    mkdir -p "$BKDIR"
    printf '\n%s:\n\n' "${BKDIR}"
    kubectl config set-context $(kubectl config current-context) --namespace gis-apps > /dev/null
    get_objs
    echo
}

main
