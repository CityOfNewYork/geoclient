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
package gov.nyc.doitt.gis.geoclient.function;

import java.util.Map;

public interface Function {
    public static final String F1 = "1";
    public static final String F1A = "1A";
    public static final String F1AX = "1AX";
    public static final String F1B = "1B";
    public static final String F1E = "1E";
    public static final String F2 = "2";
    public static final String F2W = "2W";
    public static final String F3 = "3";
    public static final String FAP = "AP";
    public static final String FBB = "BB";
    public static final String FBF = "BF";
    public static final String FBL = "BL";
    public static final String FBN = "BN";
    public static final String FD = "D";
    public static final String FDG = "DG";
    public static final String FDN = "DN";
    public static final String FHR = "HR";
    public static final String FN = "N";


    public Map<String, Object> call(Map<String, Object> parameters);

    public Configuration getConfiguration();

    public String getId();

    public WorkArea getWorkAreaOne();

    public WorkArea getWorkAreaTwo();

    public boolean isTwoWorkAreas();
}
