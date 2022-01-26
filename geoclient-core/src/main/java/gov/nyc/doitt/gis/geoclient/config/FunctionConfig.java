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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.function.Configuration;
import gov.nyc.doitt.gis.geoclient.function.DefaultConfiguration;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.GeosupportFunction;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;
import gov.nyc.doitt.gis.geoclient.jni.Geoclient;

public class FunctionConfig {
    private static final Logger log = LoggerFactory.getLogger(FunctionConfig.class);

    private final String id;
    private final WorkAreaConfig workAreaOneConfig;
    private final WorkAreaConfig workAreaTwoConfig;
    // Use concrete type for easier XStream setup
    private final DefaultConfiguration configuration;

    public FunctionConfig(String id, WorkAreaConfig workAreaOneConfig, WorkAreaConfig workAreaTwoConfig,
            DefaultConfiguration configuration) {
        super();
        this.id = id;
        this.workAreaOneConfig = workAreaOneConfig;
        this.workAreaTwoConfig = workAreaTwoConfig;
        this.configuration = configuration;
    }

    public FunctionConfig(String id, WorkAreaConfig workAreaOneConfig, WorkAreaConfig workAreaTwoConfig) {
        this(id, workAreaOneConfig, workAreaTwoConfig, null);
    }

    public Function createFunction(Geoclient geoclient) {
        log.debug("Attempting to create function {}", this.id);
        WorkArea workAreaOne = this.workAreaOneConfig.createWorkArea();
        WorkArea workAreaTwo = null;
        if (isTwoWorkAreaFunction()) {
            workAreaTwo = this.workAreaTwoConfig.createWorkArea();
            warnIfDuplicateFieldIds(workAreaOne, workAreaTwo);
        }
        DefaultConfiguration funConf = this.configuration;
        if (funConf == null) {
            log.warn("Function.configuration is null. Defaulting to new instance.");
            funConf = new DefaultConfiguration();
        }
        GeosupportFunction function = new GeosupportFunction(this.id, workAreaOne, workAreaTwo, geoclient, funConf);
        return function;
    }

    public boolean isTwoWorkAreaFunction() {
        return this.workAreaTwoConfig != null;
    }

    public boolean hasDefaultArguments() {
        return this.configuration != null;
    }

    public String getId() {
        return id;
    }

    public WorkAreaConfig getWorkAreaOneConfig() {
        return workAreaOneConfig;
    }

    public WorkAreaConfig getWorkAreaTwoConfig() {
        return workAreaTwoConfig;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public String toString() {
        return "FunctionConfig [id=" + this.id + ", workAreaOne=" + workAreaOneConfig + ", workAreaTwo="
                + workAreaTwoConfig + "]";
    }

    private void warnIfDuplicateFieldIds(WorkArea workAreaOne, WorkArea workAreaTwo) {
        List<String> duplicateIds = workAreaOne.getFieldIds();
        // Find the duplicates
        duplicateIds.retainAll(workAreaTwo.getFieldIds());
        if (duplicateIds.size() > 0) {
            String wa1Id = workAreaOne.getId();
            String wa2Id = workAreaTwo.getId();
            log.debug("=====================================================");
            log.debug("== The following field id's are defined in both WorkArea[id={}] and WorkArea[id={}].", wa1Id,
                    wa2Id);
            for (String id : duplicateIds) {
                log.debug("== Field [id={}]", id);
            }
            log.debug("== Only the value from WorkArea[id={}] will be returned.", wa2Id);
            log.debug("=====================================================");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FunctionConfig other = (FunctionConfig) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
