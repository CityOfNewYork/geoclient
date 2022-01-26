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
package gov.nyc.doitt.gis.geoclient.parser.token;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

public class TextUtils
{

  public static String sanitize(String s)
  {
    if(s == null || s.isEmpty())
    {
      return s;
    }
    // Remove leading and trailing spaces or punctuation (except for trailing
     //period characters (eg, N.Y.)
    String clean = RegExUtils.removePattern(s,"^(?:\\s|\\p{Punct})+|(?:\\s|[\\p{Punct}&&[^.]])+$");
    // Make sure ampersand is surrounded by spaces but allow double ampersand
    clean = clean.replaceAll("([^\\s&])\\&", "$1 &");
    clean = clean.replaceAll("\\&([^\\s&])", "& $1");
    // Normalize whitespace
    clean = StringUtils.normalizeSpace(clean);
    return clean;
  }

}
