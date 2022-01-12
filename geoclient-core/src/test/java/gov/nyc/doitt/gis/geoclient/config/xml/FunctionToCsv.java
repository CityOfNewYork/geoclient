/*
 * Copyright 2013-2019 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.config.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.doc.DataDictionary;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;
import gov.nyc.doitt.gis.geoclient.jni.test.GeoclientStub;

public class FunctionToCsv
{
    private static Logger log = LoggerFactory.getLogger(FunctionToCsv.class);

    public static void main(String[] args) throws Exception
    {
        GeosupportConfig geosupportConfig = new GeosupportConfig(new GeoclientStub());
        log.debug("Name,Function,WorkArea");
        // NOTE: Single work area functions (BB, BF, D, DG, DN, N) are NOT
        // referenced below because they only contain WorkAreaOne and it has already
        // been logged.
        Function f1 = geosupportConfig.getFunction(Function.F1);
        // WorkAreaOne definition
        logFields("All",f1.getWorkAreaOne());
        // WorkAreaTwo definitions
        logFields(Function.F1,f1.getWorkAreaTwo());
        logFields(Function.F1A,geosupportConfig.getFunction(Function.F1A).getWorkAreaTwo());
        logFields(Function.F1AX,geosupportConfig.getFunction(Function.F1AX).getWorkAreaTwo());
        logFields(Function.F1B,geosupportConfig.getFunction(Function.F1B).getWorkAreaTwo());
        logFields(Function.F1E,geosupportConfig.getFunction(Function.F1E).getWorkAreaTwo());
        logFields(Function.FAP,geosupportConfig.getFunction(Function.FAP).getWorkAreaTwo());
        logFields(Function.F2,geosupportConfig.getFunction(Function.F2).getWorkAreaTwo());
        logFields(Function.F2W,geosupportConfig.getFunction(Function.F2W).getWorkAreaTwo());
        logFields(Function.F3,geosupportConfig.getFunction(Function.F3).getWorkAreaTwo());
        logFields(Function.FBL,geosupportConfig.getFunction(Function.FBL).getWorkAreaTwo());
        logFields(Function.FBN,geosupportConfig.getFunction(Function.FBN).getWorkAreaTwo());
        logFields(Function.FHR,geosupportConfig.getFunction(Function.FHR).getWorkAreaTwo());
        // DataDictionary ItemDocumentation elements
        DataDictionary dataDictionary = geosupportConfig.getDataDictionary();
        for (ItemDocumentation itemDocumentation : dataDictionary.getItems())
        {
            logDataDictionaryItem(itemDocumentation);
        }
    }

    private static void logFields(String functionId, WorkArea workArea)
    {
        for (String fieldId : workArea.getFieldIds(Field.NAME_SORT, true, true))
        {
            log.info("{},{},{}",fieldId,functionId,workArea.getId());
        }
    }

    private static void logDataDictionaryItem(ItemDocumentation itemDocumentation)
    {
        //String id = itemDocumentation.getId();
        //for (String functionId : itemDocumentation.getFunctionNames())
        //{
        //    log.info("{},{},{}",id,functionId,"DataDictionary");
        //}
    }
}
