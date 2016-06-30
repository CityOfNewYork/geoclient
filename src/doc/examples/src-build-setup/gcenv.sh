#!/usr/bin/bash

VTYPE_DIR="dir"
VTYPE_PTH="path"
VTYPE_STAT_BAD="MISSING"
VTYPE_STAT_OK="OK"
VTYPE_STAT_UNSET=""

# See http://stackoverflow.com/questions/59895/can-a-bash-script-tell-what-directory-its-stored-in
unset CDPATH
scriptdir="${BASH_SOURCE[0]}"

while [ -h "$scriptdir" ]; do # resolve $scriptdir until the file is no longer a symlink
  dir="$( cd -P "$( dirname "$scriptdir" )" && pwd )"
  scriptdir="$(readlink "$scriptdir")"
  [[ $scriptdir != /* ]] && scriptdir="$dir/$scriptdir" # if $scriptdir was a relative symlink, we need to resolve it relative to the path where the symlink file was located
done

dir="$( cd -P "$( dirname "$scriptdir" )" && pwd )"
gc_project="$( dirname "$dir"/.. )"                 # Symlinks resolved when setting 'dir' so this is safe
gs_base="/opt/geosupport/server/current"            # Assumes single parent directory for all Geosupport files

GEOFILES="$gs_base/fls/"                            # Trailing '/' required!
GS_INCLUDE_PATH="$gs_base/include/foruser"
GS_LIBRARY_PATH="$gs_base/lib"

GC_LIBRARY_PATH="$gc_project/build/libs"

JAVA_HOME="/opt/java/sun/jdk1.8.0_66"
LD_LIBRARY_PATH="$GS_LIBRARY_PATH:$GC_LIBRARY_PATH" # WARNING: Overwrites existing LD_LIBRARY_PATH

function gcexport () {
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

  #
  # The 'clrcode' variable holds an indirect reference to the terminal
  # escape sequence for messing with colors. For more about indrect references 
  # see http://www.tldp.org/LDP/abs/html/complexfunct.html
  #
  local green="\e[1;92m"
  local red="\e[1;31m"
  local reset="\e[0m"
  local off=""
  local vname="$1"
  local value="$2"
  local vtype="$3"
  local status=$VTYPE_STAT_UNSET

  if [[ -n $vtype && $VTYPE_PTH = $vtype ]]; then 

    IFS=':' read -a paths <<< "${value}"

    let i=0
    for pathentry in "${paths[@]}"; do
      if [[ -d $pathentry || -f $pathentry ]]; then
        status=$VTYPE_STAT_OK
        clrcode=green
      else
        status=$VTYPE_STAT_BAD
        clrcode=red
      fi

      if [[ i -eq 0 ]]; then
        printf '%16s   %-52s[%-8s]\n' "$vname" "$pathentry" "$(echo -en "${!clrcode}$status$reset")"
      else
        printf '%16s   %-52s[%-8s]\n' " "      "$pathentry" "$(echo -en "${!clrcode}$status$reset")"
      fi
      status=$VTYPE_STAT_UNSET
      let i=(i + 1)
    done

    #printf '\n'

  elif [[ -n $vtype && $VTYPE_DIR = $vtype ]]; then

    if [[ -d $value || -f $value ]]; then
      status=$VTYPE_STAT_OK
      clrcode=green
    else
      status=$VTYPE_STAT_BAD
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
showv "GS_INCLUDE_PATH" "$GS_INCLUDE_PATH" "$VTYPE_PTH"
showv "GS_LIBRARY_PATH" "$GS_LIBRARY_PATH" "$VTYPE_PTH"
showv        "GEOFILES" "$GEOFILES"        "$VTYPE_DIR"
printf '\n'
showv "GC_LIBRARY_PATH" "$GC_LIBRARY_PATH" "$VTYPE_PTH"
printf '\n'
showv "LD_LIBRARY_PATH" "$LD_LIBRARY_PATH" "$VTYPE_PTH"
showv     "GRADLE_HOME" "$GRADLE_HOME"     "$VTYPE_DIR"
showv       "JAVA_HOME" "$JAVA_HOME"       "$VTYPE_DIR"
printf '\n'
