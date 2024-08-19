#!/usr/bin/env bash

THIS_DIR="$(dirname "$(readlink -vf "$BASH_SOURCE")")"
THIS_FILE="$(basename "$0")"

set -Eeuo pipefail

die() {
    [[ ! -z "$1" ]] && echo "$1"
    exit 1
}

port_forward() {
    local name="$1"
    local local_port="$2"
    local remote_port="$3"
    echo kubectl port-forward "${name}" ${local_port}:${remote_port}
}

test_version() {
    curl -s -k 'http://localhost:8081/geoclient/v2/version.json' | egrep --color '"version":"24.3","release":"24C"'
}

test_search() {
    curl -s -k 'http://localhost:8081/geoclient/v2/search.json?input=120%20Broadway%20manhattan' | egrep --color '"returnCode1a":"0(0|1)","returnCode1e":"0(0|1)"'
}

show_port_forward() {
    local color="$1"
    local lport="${2:-8081}"
    local rport="${3:-8080}"
    pods=($(kubectl -n gis-apps get pods -l "col=${color},app=geoclient-v2" --no-headers=true -o='custom-columns=PODS:.metadata.name'))
    for ((i=0; i < ${#pods[@]}; i++))
    do
        port_forward "${pods[i]}" "${lport}" "${rport}"
    done
}

main() {
    if [ $# -eq 0 ]; then
        usage
        exit 0
    fi
    local color=
    local show_opt=
    local test_opt=
    while [ $# -gt 0 ]; do
        case "$1" in
            -h|--help)
                usage && exit 0;
                ;;
            --show=*)
                color="${1##--show=}";
                [[ -z "${color}" ]] \
                    && die "Missing required <color> value from --show=<color> argument."
                show_opt="true"; shift
                ;;
            -s*)
                color="${1##-s}";
                [[ -z "${color}" ]] \
                    && die "Missing required <color> value from -s<color> argument."
                show_opt="true"; shift
                ;;
            -t|--test)
                test_opt="true"; shift
                ;;
            *)
                die "Unrecognized argument $1";
                ;;
        esac
    done
    if [[ ! -z "${test_opt}" ]]; then
        test_version
        test_search
    else
        show_port_forward "${color}"
    fi
}

usage() {
cat <<- EOF

Usage: ${THIS_FILE} -s<color> | -t | -h

  -h, --help
    Show this usage message.

  -s<color>, --show=<color>
    Show the kubectl port-forward command for each pod.
    This is the default if no options are given.

    NOTE: The 'kubectl port-forward' command blocks, so must be run
          from an interactive bash prompt.

  -t, --test
    Run the cURL tests against the local ports. This
    option takes precedence over the -s option if it is
    also given.

    NOTE: The tests will fail unless port forwarding is already running.

EOF
}

main "$@"
