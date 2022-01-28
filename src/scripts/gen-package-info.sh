#!/usr/bin/env bash

#
# IMPORTANT:
# This script is hardcoded (using output from Gradle Checkstyle plugin)
# and must be adjusted manually to work.
# Hint: Update 'srcdir' and 'packages' variables.
#

# Assumes pwd is the root geoclient project directory.
srcdir="./geoclient-core/src/main/java"

declare -a packages=( \
    "gov/nyc/doitt/gis/geoclient/api" \
    "gov/nyc/doitt/gis/geoclient/cli" \
    "gov/nyc/doitt/gis/geoclient/config" \
    "gov/nyc/doitt/gis/geoclient/config/xml" \
    "gov/nyc/doitt/gis/geoclient/function" \
    "gov/nyc/doitt/gis/geoclient/util")

echo
for pack in "${packages[@]}"; do
  packpath="${srcdir}/${pack}"
  packfile="${packpath}/package-info.java"
  if [[ ! -f "${packfile}" ]]; then 
    packname="$(basename ${packpath})"
    cat <<- EOF > "${packfile}"
/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * ${packname^} package.
 *
 * @author Matthew Lipper
 */
package $(echo -n ${pack} | tr '/' '.' );
EOF
    echo "[INFO] Created ${packfile} file."
    echo
  else
    echo "[WARN] Not creating ${packfile} because it already exists."
    echo
  fi
done
