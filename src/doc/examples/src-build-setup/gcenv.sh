#!/bin/bash

projectDir="$(dirname "${BASH_SOURCE[0]}")/../../../../."
vtype_dir="dir"
vtype_pth="path"
vtype_stat_bad="MISSING"
vtype_stat_ok="OK"
vtype_stat_unset=""

# NOTE: This requires GNU getopt.  On Mac OS X and FreeBSD, you have to install this
# yourself as it is not included by default.
TEMP=`getopt -o fehp:b:j: --long file,exportenv,help,projectdir,gsbase,jdk -n "$(basename ${BASH_SOURCE[0]})" -- "$@"`

if [ $? != 0 ] ; then echo "getopt error $?. Terminating..." >&2 ; exit 1 ; fi

# Note the quotes around `$TEMP': they are essential!
eval set -- "$TEMP"

# Argument variables
exportenv=
pushd "$projectDir"
gc_project=$(pwd)
popd
gs_base="${GS_LIBRARY_PATH}/../."
jdk_home="${JAVA_HOME}"
default_outdir="$gc_project/.local"
default_outfile="$default_outdir/setenv.sh"
outfile=
showhelp=

usage() {
  this_file="$0"
  read -r -d '' USAGE <<EOF

Usage:
  $this_file [OPTIONS]
    OR
  $this_file

  OPTIONS:

  -e,--exportenv         If provided, then use the values from the --projectdir,
                         --gsbase, --file and --jdk options to set and export the
                         necessary environment variables. If any of these
                         optional arguments are not provided, or evaluate to
                         null when checking their defaults, the program will
                         exit.

  -f,--file <file>       Path to file which will be generated as a target
                         for sourcing from bash to export environment variables.
                         Defaults to "<projectdir>/.local/setenv.sh" if not
                         given.

  -p,--projectdir <dir>  The geoclient source root project directory.
                         If not given, defaults to:
                         "\$(dirname "\${BASH_SOURCE[0]}")/../../../../."

  -b,--gsbase <dir>      The base Geosupport installation directory which
                         has the fls, include/foruser, lib sub-directories.
                         Defaults to the value of "\$GS_LIBRARY_PATH/.." if
                         not given.

  -j,--jdk <dir>         The JDK installation directory.
                         Defaults to the value of "\$JAVA_HOME" if
                         not given.

  -h,--help              Show this message

EOF
  echo
  echo "$USAGE" 1>&2;
  echo
  exit $1;
}

while true; do
  case "$1" in
    -e | --exportenv ) exportenv="true"; shift ;;
    -f | --file ) outfile="$2"; shift 2 ;;
    -p | --projectdir ) gc_project="$2"; shift 2 ;;
    -b | --gbase ) gs_base="$2"; shift 2 ;;
    -j | --jdk ) jdk_home="$2"; shift 2 ;;
    -h | --help ) showhelp="true"; shift ;;
    -- ) shift; break ;;
    * ) break ;;
  esac
done

JAVA_HOME="$jdk_home"
GEOFILES="$gs_base/fls/"
GS_INCLUDE_PATH="$gs_base/include/foruser"
GS_LIBRARY_PATH="$gs_base/lib"
GC_LIBRARY_PATH="$gc_project/build/libs"
LD_LIBRARY_PATH="$GS_LIBRARY_PATH:$GC_LIBRARY_PATH" # WARNING: Overwrites existing LD_LIBRARY_PATH

#
# arg0 - Environment variable name
# arg1 - Environment variable value
# arg2 - (Optional) Variable type: dir, path, string. Defaults to "dir"
#
function showv () {

  local green="\e[1;92m"
  local red="\e[1;31m"
  local reset="\e[0m"
  local off=""
  local vname="$1"
  local value="$2"
  local vtype="$3"
  local status=$vtype_stat_unset

  if [[ -n $vtype && $vtype_pth = $vtype ]]; then
    if [[ -z $value ]]; then
      # Value is null
      printf '%16s   %-52s[%-8s]\n' "$vname" " " "$(echo -en "${red}$vtype_stat_bad$reset")"
    else
      # Parse and show path elements
      IFS=':' read -a paths <<< "${value}"

      let i=0
      for pathentry in "${paths[@]}"; do
        if [[ -d $pathentry || -f $pathentry ]]; then
          status=$vtype_stat_ok
          clrcode=green
        else
          status=$vtype_stat_bad
          clrcode=red
        fi

        if [[ i -eq 0 ]]; then
          printf '%16s   %-52s[%-8s]\n' "$vname" "$pathentry" "$(echo -en "${!clrcode}$status$reset")"
        else
          printf '%16s   %-52s[%-8s]\n' " "      "$pathentry" "$(echo -en "${!clrcode}$status$reset")"
        fi
        status=$vtype_stat_unset
        let i=(i + 1)
      done
    fi

  elif [[ -n $vtype && $vtype_dir = $vtype ]]; then
    if [[ -d $value || -f $value ]]; then
      status=$vtype_stat_ok
      clrcode=green
    else
      status=$vtype_stat_bad
      clrcode=red
    fi
    printf '%16s:  %-52s[%-8s]\n' "$vname" "$value" "$(echo -en "${!clrcode}$status$reset")"
  else
    printf '\n%16s:  %-60s\n' "$vname" "$value"
  fi
}

function report () {
  printf '\n'
  printf '%1s%-70s\n'    " " "Geoclient Environment on host $(uname -n): $(uname -op)"
  printf '%1s%-70s\n\n'  " " "-----------------------------------------------------------------------------"
  showv "GS_INCLUDE_PATH" "$GS_INCLUDE_PATH" "$vtype_pth"
  showv "GS_LIBRARY_PATH" "$GS_LIBRARY_PATH" "$vtype_pth"
  showv        "GEOFILES" "$GEOFILES"        "$vtype_dir"
  printf '\n'
  showv "GC_LIBRARY_PATH" "$GC_LIBRARY_PATH" "$vtype_pth"
  printf '\n'
  showv "LD_LIBRARY_PATH" "$LD_LIBRARY_PATH" "$vtype_pth"
  showv     "GRADLE_HOME" "$GRADLE_HOME"     "$vtype_dir"
  showv       "JAVA_HOME" "$JAVA_HOME"       "$vtype_dir"
  printf '\n'
}

if [[ -n $showhelp ]]; then
  usage 0
else
  report
  # Done unless --exportenv was specified
  [[ -n $exportenv ]] || exit 0

  printf '\n\n%8s%s\n' "NOTE:" "Your environment has not been changed."

  if [[ -z $outfile ]]; then
    mkdir -p "$default_outdir"
    outfile="$default_outfile"
  fi

  while true; do
    read -p "Continue and generate $outfile [y/n]?" yn
    case $yn in
      [Yy]* ) break;;
      [Nn]* ) exit 0;;
      * ) echo "Please answer yes or no.";;
    esac
  done

  cat <<EOF > "$outfile"
#!/usr/bin/bash

#
# $(date) - This file was generated using $(dirname "${BASH_SOURCE[0]}")
#

export JAVA_HOME="$JAVA_HOME"
export GEOFILES="$GEOFILES"
export GS_INCLUDE_PATH="$GS_INCLUDE_PATH"
export GS_LIBRARY_PATH="$GS_LIBRARY_PATH"

# NOTE: This path will not exist until the first run of './gradlew regenerate'
#
export GC_LIBRARY_PATH="$GC_LIBRARY_PATH"

# WARNING: Overwrites existing LD_LIBRARY_PATH and may not be necessary in some
#          circumstances.
#
export LD_LIBRARY_PATH="$GS_LIBRARY_PATH:$GC_LIBRARY_PATH"
EOF

  printf '\nSource file %s to export these to your current environment:\n%-8s\n' "$outfile" "\$ . $outfile"

fi
