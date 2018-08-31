/*
 * Copyright 2013-2016 the original author or authors.
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

import gov.nyc.doitt.gis.geoclient.config.xml.DocumentationXmlReader;
import gov.nyc.doitt.gis.geoclient.config.xml.GeoclientXmlReader;
import gov.nyc.doitt.gis.geoclient.doc.DataDictionary;
import gov.nyc.doitt.gis.geoclient.doc.FunctionDocumentation;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.jni.Geoclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeosupportConfig
{
  private static final Logger log = LoggerFactory.getLogger(GeosupportConfig.class);
  public static final String DEFAULT_DOCUMENTATION_CONFIG_FILE = "geoclient-doc.xml";
  public static final String DEFAULT_CONFIG_FILE = "geoclient.xml";
  private final Geoclient geoclient;
  private final GeoclientXmlReader geoclientXmlReader;
  private final DocumentationXmlReader documentationXmlReader;

  public GeosupportConfig(Geoclient geoclient)
  {
    this(DEFAULT_CONFIG_FILE, DEFAULT_DOCUMENTATION_CONFIG_FILE, geoclient);
  }

  public GeosupportConfig(String configFile, String documentationConfigFile, Geoclient geoclient)
  {
    this.geoclientXmlReader = GeoclientXmlReader.fromXml(configFile);
    this.documentationXmlReader = DocumentationXmlReader.fromXml(documentationConfigFile);
    this.geoclient = geoclient;
    init();
  }

  public Function getFunction(String id)
  {
    if (!Registry.containsFunction(id))
    {
      throw new UnknownFunctionException(id);
    }
    return Registry.getFunction(id);
  }

  public FunctionDocumentation getFunctionDocumentation(String id)
  {
    if (!Registry.containsFunctionDocumentation(id))
    {
      throw new FunctionNotDocumentedException(id);
    }
    return Registry.getFunctionDocumentation(id);
  }

  public DataDictionary getDataDictionary()
  {
    return this.documentationXmlReader.getDataDictionary();
  }

  private void init()
  {
    DocumentationConfig documentationConfig = new DocumentationConfig(documentationXmlReader.getDataDictionary());
    for (FunctionConfig functionConfig : this.geoclientXmlReader.getFunctions())
    {
      log.info("Creating function from {}", functionConfig);
      Function function = functionConfig.createFunction(geoclient);
      Registry.addFunction(function);
      Registry.addFunctionDocumentation(documentationConfig.document(documentationXmlReader.getFunctionDocumentation(function.getId()) , function));
    }
  }

}
