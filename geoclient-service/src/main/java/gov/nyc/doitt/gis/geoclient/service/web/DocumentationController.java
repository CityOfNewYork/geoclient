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
package gov.nyc.doitt.gis.geoclient.service.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.nyc.doitt.gis.geoclient.service.domain.ServiceType;
import gov.nyc.doitt.gis.geoclient.service.domain.Version;
import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportService;

@Controller
public class DocumentationController {
    public static final String DOC_OBJ = ServiceType.DOC.elementName();
    public static final String DOC_URI = "/doc";
    public static final String DOC_VIEW_NAME = "index";
    public static final String VERSION_OBJ = ServiceType.VERSION.elementName();
    private static final Logger logger = LoggerFactory.getLogger(DocumentationController.class);

    @Autowired
    private GeosupportService geosupportService;


    @RequestMapping(value = DOC_URI, method = RequestMethod.GET)
    public String doc(ModelMap modelMap)
    {
        //modelMap.put(DOC_OBJ, this.geosupportService.getDocumentation());
        Version version = this.geosupportService.version();
        modelMap.addAttribute(VERSION_OBJ, version);
        logger.debug("Returning Version instance {} to view named {}", version, DOC_VIEW_NAME);
        return DOC_VIEW_NAME;
    }

    public void setGeosupportService(GeosupportService geosupportService)
    {
        this.geosupportService = geosupportService;
    }

}
