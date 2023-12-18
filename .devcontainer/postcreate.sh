#!/usr/bin/env bash

add_cmds=$(cat <<ADDITIONAL_COMMMANDS
set -o vi
alias ll='ls -l'
alias lla='ls -lsa'
alias c=clear
ADDITIONAL_COMMMANDS
)

# Add additional commands unless they are already there.
if ! egrep -q "${add_cmds}" "${HOME}/.bashrc"; then
    echo "${add_cmds}" >> "${HOME}/.bashrc"
fi

#export GCBASE="/workspaces/geoclient"
#export GCJNI="${GCBASE}/geoclient-jni"
#export GCCORE="${GCBASE}/geoclient-core"
#export GCPARSER="${GCBASE}/geoclient-parser"
#export GCSERVICE="${GCBASE}/geoclient-service"

/opt/geosupport/current/bin/geosupport install
