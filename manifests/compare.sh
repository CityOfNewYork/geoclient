#!/usr/bin/env bash

#
# Compares generated kustomize output between two git revisions.
#

set -Eeuo pipefail

this_dir="$(dirname "$(readlink -vf "$BASH_SOURCE")")"
this_file="$(basename "$0")"

cd "${this_dir}"

#
# Globals
#
declare -A OVERLAYS=(
    [dev-no-pvc]='overlays/app/dev/no-pvc'
    [dev-pvc]='overlays/app/dev/pvc'
    [prd-no-pvc]='overlays/app/prd/no-pvc'
    [prd-pvc]='overlays/app/prd/pvc'
    [minikube-no-pvc]='overlays/app/minikube/no-pvc'
    [minikube-pvc]='overlays/app/minikube/pvc'
)

CURRENT_REPO="$(dirname ${this_dir})"
REVISION=
WORKING_TREE=working

KZTMP=$(mktemp -d /tmp/kz-$(date +"%Y%m%d")-XXXX)
trap 'rm -rf -- "$KZTMP"' EXIT

die() {
    [[ ! -z "$1" ]] && echo "$1"
    exit 1
}

compare_revisions() {
    local project_name="$(basename ${CURRENT_REPO})"
    local generated_base_dir="${KZTMP}/generated"
    local cloned_repo_root="${KZTMP}/${project_name}"
    echo "Cloning ${project_name} revision ${REVISION} to ${KZTMP}."
    # cd into temp dir repo and clone this repo
    cd "${KZTMP}"
    git clone "${CURRENT_REPO}" \
        || die "Failed to clone git repo ${CURRENT_REPO}."
    # cd into cloned repo and checkout the requested revision
    cd "${cloned_repo_root}"
    git checkout "${REVISION}" \
        || die "Failed to check out ${REVISION} from ${CURRENT_REPO}."
    mkdir -p "${generated_base_dir}"
    echo "[start] Generating overlays: ${generated_base_dir}"
    for name in "${!OVERLAYS[@]}"; do
        local overlay="${OVERLAYS[${name}]}"
        # Generate WORKING_TREE
        local target_dir_current="${generated_base_dir}/${WORKING_TREE}/${name}"
        local overlay_base_current="${this_dir}"
        mkdir -p "${target_dir_current}"
        generate "${WORKING_TREE}" "${name}" "${overlay_base_current}" "${overlay}" "${target_dir_current}"
        # Generate $REVISION
        local target_dir_revision="${generated_base_dir}/${REVISION}/${name}"
        local overlay_base_revision="${cloned_repo_root}/manifests"
        mkdir -p "${target_dir_revision}"
        generate "${REVISION}" "${name}" "${overlay_base_revision}" "${overlay}" "${target_dir_revision}"
    done
    local patch_file="${generated_base_dir}/${WORKING_TREE}-vs-${REVISION}.patch"
    set +e
    diff "${generated_base_dir}/${WORKING_TREE}" "${generated_base_dir}/${REVISION}" -ru > "${patch_file}"
    set -e
    [[ -f "${patch_file}" ]] || die "Failed to create ${patch_file} file."
    echo "[end] Generation complete: ${patch_file}"
}

generate() {
    [ $# -eq 5 ] \
        || die "Error: generate() requires 5 arguments but only $# were given."
    local rev="$1"
    local name="$2"
    local overlay_base="$3"
    local overlay="$4"
    local release_dir="$5"
    echo "[${rev}:${name}] start"
    if [[ -d "${overlay_base}/${overlay}" ]]; then
        # Always invoke the build script in $this_dir (CURRENT_REPO)
        echo "[${rev}:${name}] OVERLAY_BASE=${overlay_base} RELEASE_DIR=${release_dir} ${this_dir}/build.sh ${overlay};"
        # Use OVERLAY_BASE to test the REVISION's overlays using CURRENT_REPO's compare.sh and build.sh files.
        OVERLAY_BASE="${overlay_base}" RELEASE_DIR="${release_dir}" "${this_dir}"/build.sh "${overlay}";
    else
        echo "[${rev}:${name}] Generation skipped: overlay ${overlay} does not exist in revision ${rev}."
    fi
    echo "[${rev}:${name}] complete"
}

main() {
    if [ $# -eq 0 ]; then
        usage
        exit 0
    fi
    REVISION="$1"
    compare_revisions
    local tmpdirname="$(basename ${KZTMP})"
    tar czvf "${CURRENT_REPO}/${tmpdirname}.tgz" -C "/tmp/${tmpdirname}" generated
    echo "Results compressed to file ${CURRENT_REPO}/${tmpdirname}.tgz."
}

usage() {
    local this_file
    this_file="$(basename "${BASH_SOURCE[0]}")"
cat <<- EOF

Usage: ${this_file} <git_revision>

Checks out <git_revision> into a temporary directory and generates
all overlays into their own named subdirectories.

EOF
}

main "$@"
