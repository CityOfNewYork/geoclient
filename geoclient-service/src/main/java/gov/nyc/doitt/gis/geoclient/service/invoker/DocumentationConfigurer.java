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
package gov.nyc.doitt.gis.geoclient.service.invoker;

import java.util.List;

import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.doc.DataDictionary;
import gov.nyc.doitt.gis.geoclient.doc.FunctionDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.service.domain.Documentation;

public class DocumentationConfigurer
{
    private final GeosupportConfig geosupportConfig;
    private final LatLongEnhancer latLongEnhancer;
    public DocumentationConfigurer(GeosupportConfig geosupportConfig, LatLongEnhancer latLongEnhancer)
    {
        super();
        this.geosupportConfig = geosupportConfig;
        this.latLongEnhancer = latLongEnhancer;
    }

    public Documentation configureDocumentation()
    {
        Documentation documentation = new Documentation();
        configureDataDictionary(documentation);
        configureAddressDocumentation(documentation);
        configureBblDocumentation(documentation);
        configureBinDocumentation(documentation);
        configureBlockfaceDocumentation(documentation);
        configureIntersectionDocumentation(documentation);
        configurePlaceDocumentation(documentation);
        return documentation;
    }

    private void configureDataDictionary(Documentation documentation)
    {
        DataDictionary dataDictionary = this.geosupportConfig.getDataDictionary();
        dataDictionary.getItems().addAll(this.latLongEnhancer.getItemDocumentation());
        documentation.setDataDictionary(dataDictionary);
    }

    private void configureAddressDocumentation(Documentation documentation)
    {
        FunctionDocumentation addressDocumentation = this.geosupportConfig.getFunctionDocumentation(Function.F1B);
        addressDocumentation.setDisplayName(Documentation.ADDRESS_FUNCTION);
        addLatLong(addressDocumentation);
        documentation.setAddressDocumentation(addressDocumentation);
    }

    private void configureBblDocumentation(Documentation documentation)
    {
        FunctionDocumentation bblDocumentation = this.geosupportConfig.getFunctionDocumentation(Function.FBL);
        bblDocumentation.setDisplayName(Documentation.BBL_FUNCTION);
        addLatLong(bblDocumentation);
        documentation.setBblDocumentation(bblDocumentation);
    }

    private void configureBinDocumentation(Documentation documentation)
    {
        FunctionDocumentation binDocumentation = this.geosupportConfig.getFunctionDocumentation(Function.FBN);
        binDocumentation.setDisplayName(Documentation.BIN_FUNCTION);
        addLatLong(binDocumentation);
        documentation.setBinDocumentation(binDocumentation);
    }

    private void configureBlockfaceDocumentation(Documentation documentation)
    {
        FunctionDocumentation blockfaceDocumentation = this.geosupportConfig.getFunctionDocumentation(Function.F3);
        blockfaceDocumentation.setDisplayName(Documentation.BLOCKFACE_FUNCTION);
        documentation.setBlockfaceDocumentation(blockfaceDocumentation);
    }

    private void configureIntersectionDocumentation(Documentation documentation)
    {
        FunctionDocumentation intersectionDocumentation = this.geosupportConfig.getFunctionDocumentation(Function.F2);
        intersectionDocumentation.setDisplayName(Documentation.INTERSECTION_FUNCTION);
        addLatLong(intersectionDocumentation);
        documentation.setIntersectionDocumentation(intersectionDocumentation);
    }

    private void configurePlaceDocumentation(Documentation documentation)
    {
        FunctionDocumentation placeDocumentation = new FunctionDocumentation();
        placeDocumentation.setDisplayName(Documentation.PLACE_FUNCTION);
        FunctionDocumentation addressDocumentation = this.geosupportConfig.getFunctionDocumentation(Function.F1B);
        placeDocumentation.setId(addressDocumentation.getId());
        placeDocumentation.setDescription(addressDocumentation.getDescription());
        placeDocumentation.setFields(addressDocumentation.getFields());
        placeDocumentation.setGroups(addressDocumentation.getGroups());
        addLatLong(placeDocumentation);
        documentation.setPlaceDocumentation(placeDocumentation);
    }

    private void addLatLong(FunctionDocumentation functionDocumentation)
    {
        List<ItemDocumentation> itemDocs = this.latLongEnhancer.getItemDocumentation();
        for (ItemDocumentation itemDocumentation : itemDocs)
        {
            if(appliesToFunction(functionDocumentation.getId(), itemDocumentation))
            {
                functionDocumentation.getFields().add(itemDocumentation);
            }
        }
    }

    private boolean appliesToFunction(String functionId, ItemDocumentation itemDocumentation)
    {
        for (String id : itemDocumentation.getFunctionNames())
        {
            if(functionId.equals(id))
            {
                return true;
            }
        }
        return false;
    }

}
