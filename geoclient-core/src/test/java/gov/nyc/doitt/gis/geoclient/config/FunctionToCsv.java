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
package gov.nyc.doitt.gis.geoclient.config;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;
import gov.nyc.doitt.gis.geoclient.jni.Geoclient;
import gov.nyc.doitt.gis.geoclient.jni.test.GeoclientStub;

public class FunctionToCsv
{
    private static Logger log = LoggerFactory.getLogger(FunctionToCsv.class);

    private final GeosupportConfig config;

    public FunctionToCsv() {
        this(new GeoclientStub());
    }

    public FunctionToCsv(Geoclient geoclient) {
        this.config = new GeosupportConfig(geoclient);
    }

    public void generateReport() {
        // Write CSV header row
        log.info("field,function,workarea,input,start,end,length,filtered,alias,composite,whitespace");

        // NOTE: Single work area functions (BB, BF, D, DG, DN, N) are NOT
        // referenced below because they only contain WorkAreaOne and it has already
        // been logged.
        //
        // WorkAreaOne definition
        WorkArea workAreaOne =  config.getWorkAreaOne();
        logFields("All", workAreaOne, Field.NAME_SORT, true, true);

        // NOTE: Functions 1, 1A, 1AX, 1E and 2 are not reported below because
        // there are not currently exposed by the REST API.
        //
        // WorkAreaTwo definitions
        Comparator<Field> comparator = Field.DEFAULT_SORT;
        logWorkAreaTwoFields(config.getFunction(Function.F1B), comparator);
        logWorkAreaTwoFields(config.getFunction(Function.FAP), comparator);
        logWorkAreaTwoFields(config.getFunction(Function.F2W), comparator);
        logWorkAreaTwoFields(config.getFunction(Function.F3),  comparator);
        logWorkAreaTwoFields(config.getFunction(Function.FBL), comparator);
        logWorkAreaTwoFields(config.getFunction(Function.FBN), comparator);
        logWorkAreaTwoFields(config.getFunction(Function.FHR), comparator);
    }

    private void logWorkAreaTwoFields(Function function, Comparator<Field> comparator) {
        logFields(function.getId(), function.getWorkAreaTwo(), comparator, true, false);
    }

    private void logFields(String functionId, WorkArea workArea, Comparator<Field> comparator, boolean includeFiltered, boolean includeInputFields)
    {
        // Write rows
        for (Field field : workArea.getFields(comparator, includeFiltered, includeInputFields))
        {
            String fieldId = field.getId();
            int startPosition = field.getStart();
            int endPosition = startPosition+ field.getLength();
            String alias = "";
            if(field.isAliased()) {
                alias = field.getAlias();
            }
            String composite = "";
            if(field.isComposite()) {
                composite = "composite";
            }
            String filtered = "";
            if(workArea.isFiltered(field)){
                filtered = "filtered";
            }
            String input = "";
            // Hack alert!
            if(includeInputFields && startPosition >= 360) {
               input = "input";
            }
            String whitespace = "";
            if(field.isWhitespaceSignificant()){
                whitespace = "significant";
            }
            log.info("{},{},{},{},{},{},{},{},{},{},{}",
                fieldId,
                functionId,
                workArea.getId(),
                input,
                field.getStart(),
                endPosition,
                field.getLength(),
                filtered,
                alias,
                composite,
                whitespace);
        }
    }

    public static void main(String[] args) throws Exception
    {
        new FunctionToCsv().generateReport();
    }
}
