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
package gov.nyc.doitt.gis.geoclient.service.street.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportService;

/**
 *
 * REST controller for handling Geosupport function D* calls.
 *
 * @author mlipper
 * @since 2.0
 */
@RestController
@RequestMapping("/streetcode")
public class StreetCodeController {

    private static final Logger logger = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private GeosupportService geosupportService;

    @GetMapping("/b5sc")
    public @ResponseBody Map<String, Object> streetcode(@RequestParam String streetCode,
            @RequestParam(required = false) String streetCodeTwo,
            @RequestParam(required = false) String streetCodeThree,
            @RequestParam(required = false, defaultValue = "32") Integer length,
            @RequestParam(required = false, defaultValue = "S") String format) {
        logger.debug("street[streetCode='{}',streetCodeTwo='{}',streetCodeThree='{}',length='{}',format='{}']",
                streetCode, streetCodeTwo, streetCodeThree, length, format);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("response", this.geosupportService.callFunctionD(streetCode, streetCodeTwo, streetCodeThree, length, format));
        return resultMap;
    }
}
