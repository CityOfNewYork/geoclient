#!/usr/bin/bash

vtype_dir="dir"
vtype_pth="path"
vtype_stat_bad="MISSING"
vtype_stat_ok="OK"
vtype_stat_unset=""

# NOTE: This requires GNU getopt.  On Mac OS X and FreeBSD, you have to install this
# yourself as it is not included by default.
TEMP=`getopt -o hp:b:j: --long help,projectdir,gsbase,jdk -n "$(basename ${BASH_SOURCE[0]})" -- "$@"`

if [ $? != 0 ] ; then echo "getopt error $?. Terminating..." >&2 ; exit 1 ; fi

# Note the quotes around `$TEMP': they are essential!
eval set -- "$TEMP"

# Argument variables
gc_project="$(dirname "${BASH_SOURCE[0]}")/../../../../."
gs_base="${GS_LIBRARY_PATH}/../."
jdk_home="${JAVA_HOME}"
showhelp=

usage() {
  this_file="$0"
  read -r -d '' USAGE <<EOF

Usage:
    $this_file [OPTIONS]
      OR
    $this_file

    OPTIONS:
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
    -p | --projectdir ) gc_project="$2"; shift 2 ;;
    -b | --gbase ) gs_base="$2"; shift 2 ;;
    -j | --jdk ) jdk_home="$2"; shift 2 ;;
    -h | --help ) showhelp="true"; shift ;;
    -- ) shift; break ;;
    * ) break ;;
  esac
done

function verify() {
  [[ -d $gc_project ]] || exit 1
  [[ -d $gs_base ]] || exit 1
  [[ -d $jdk_home ]] || exit 1
}

GEOFILES="$gs_base/fls/"
GS_INCLUDE_PATH="$gs_base/include/foruser"
GS_LIBRARY_PATH="$gs_base/lib"

GC_LIBRARY_PATH="$gc_project/build/libs"

JAVA_HOME="$jdk_home"
LD_LIBRARY_PATH="$GS_LIBRARY_PATH:$GC_LIBRARY_PATH" # WARNING: Overwrites existing LD_LIBRARY_PATH

function gcexport () {
  verify
  export GEOFILES
  export GS_INCLUDE_PATH
  export GS_LIBRARY_PATH
  export GC_LIBRARY_PATH
  export JAVA_HOME
  export LD_LIBRARY_PATH
}

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

    #printf '\n'

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
