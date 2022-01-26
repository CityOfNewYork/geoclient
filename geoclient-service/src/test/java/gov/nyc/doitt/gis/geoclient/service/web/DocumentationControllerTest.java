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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;

import gov.nyc.doitt.gis.geoclient.service.domain.Version;
import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportService;

public class DocumentationControllerTest {
    private GeosupportService geosupportServiceMock;
    private DocumentationController documentationController;

    @BeforeEach
    public void setUp() throws Exception {
        this.geosupportServiceMock = Mockito.mock(GeosupportService.class);
        this.documentationController = new DocumentationController();
        this.documentationController.setGeosupportService(geosupportServiceMock);
    }

    @Test
    public void testDoc() {
        //Documentation doc = new Documentation();
        ModelMap modelMap = new ModelMap();
        //Mockito.when(this.geosupportServiceMock.getDocumentation()).thenReturn(doc);
        Version version = new Version();
        Mockito.when(this.geosupportServiceMock.version()).thenReturn(version);
        assertEquals(DocumentationController.DOC_VIEW_NAME, this.documentationController.doc(modelMap));
        assertSame(version, modelMap.getAttribute("version"));
        //assertSame(doc, modelMap.get("doc"));
    }
}
