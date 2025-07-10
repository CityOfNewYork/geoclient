#!/usr/bin/env bash

#
# Reports on status of objects which need to be configured for kustomize.
#

set -Eeuo pipefail

this_dir="$(dirname "$(readlink -vf "$BASH_SOURCE")")"
this_file="$(basename "$0")"

cd "${this_dir}"

#
# Globals
#
COLOR=
COLORS=()
ENVIRONMENT=
VER=

_die() {
    local msg="$1"
    echo "${msg}"
    exit 1
}

_set_globals() {
    case "$1" in
        v1|v2)
            VER="$1";
            ;;
        *)
            _die "Error: invalid geoclient version argument '$1'. Valid values: 'v1', 'v2'.";
            ;;
    esac

    local active_svc=$(kubectl -n gis-apps get ingress "geoclient-${VER}" -o json | jq '.spec.rules[0].http.paths[0].backend.service.name')
    local active_color="${active_svc##*-}"
    COLOR="${active_color:0:-1}"
    ENVIRONMENT=$(kubectl -n gis-apps config current-context)
}

_report() {
    printf '\n%-8s %-22s %-14s %-7s %-20s\n' 'STATUS' 'ENVIRONMENT' 'VERSION' 'COLOR' 'PVC'
    IFS=' ' read -a arr <<< "$(kubectl -n gis-apps get deployment -l app=geoclient-${VER} -o json | jq -M -cr '[.items[].metadata.labels.col] | join(" ")')"
    for clr in ${arr[@]}; do
        local pvc=$(kubectl -n gis-apps get deployment "geoclient-${VER}-${clr}" -o json | jq -M -cr '.spec.template.spec.volumes[0].persistentVolumeClaim.claimName')
        #pvc="${pvc:1}"
        #pvc="${pvc:0:-1}"
        local status=""
        if [[ "${COLOR}" == "${clr}" ]]; then
            status="active"
        fi
        printf '%-8s %-22s %-14s %-7s %-20s\n' "${status}" "${ENVIRONMENT}" "geoclient-${VER}" "${clr}" "${pvc}"
    done
    echo
}

verify_jq() {
    local jq_installed=$(which jq)
    [ -x "$jq_installed" ] || _die "Error: jq command not found."
}

main() {
    if [ $# -eq 0 ]; then
        usage
        exit 0
    fi
    _set_globals "$1"
    _report
}

usage() {
    local this_file
    this_file="$(basename "${BASH_SOURCE[0]}")"
cat <<- EOF

Usage: ${this_file} <(v1|v2)>

EOF
}

main "$@"

