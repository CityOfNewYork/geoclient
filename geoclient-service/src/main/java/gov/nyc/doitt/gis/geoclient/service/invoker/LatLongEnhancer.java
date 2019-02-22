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
package gov.nyc.doitt.gis.geoclient.service.invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import gov.nyc.doitt.gis.geoclient.api.OutputParam;
import gov.nyc.doitt.gis.geoclient.doc.Description;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geometry.domain.MapPoint;
import gov.nyc.doitt.gis.geometry.transform.Transformer;
import gov.nyc.doitt.gis.geometry.transform.TransformerFactory;

public class LatLongEnhancer
{
  public static final LatLongConfig DEFAULT_LATLONG_CONFIG = new LatLongConfig("latitude", "longitude",OutputParam.XCOORD,OutputParam.YCOORD, defaultDoc("latitude", OutputParam.YCOORD, Function.F1B, Function.F2), defaultDoc("longitude", OutputParam.XCOORD, Function.F1B, Function.F2));
  public static final LatLongConfig DEFAULT_LATLONG_INTERNAL_LABEL_CONFIG = new LatLongConfig("latitudeInternalLabel", "longitudeInternalLabel",OutputParam.XCOORD_INTERNAL_LABEL,OutputParam.YCOORD_INTERNAL_LABEL,defaultDoc("latitudeInternalLabel", OutputParam.YCOORD_INTERNAL_LABEL, Function.F1B, Function.FBL, Function.FBN), defaultDoc("longitudeInternalLabel", OutputParam.XCOORD_INTERNAL_LABEL, Function.F1B, Function.FBL, Function.FBN));
  public static final Transformer DEFAULT_TRANSFORMER = new TransformerFactory().nyspToLatLong();

  public static final List<LatLongConfig> DEFAULT_CONFIGS = new ArrayList<LatLongConfig>();
    static
    {
      DEFAULT_CONFIGS.add(DEFAULT_LATLONG_CONFIG);
      DEFAULT_CONFIGS.add(DEFAULT_LATLONG_INTERNAL_LABEL_CONFIG);
    }

    private static ItemDocumentation defaultDoc(String id, String inputField,String...functionNames)
    {
      ItemDocumentation itemDocumentation = new ItemDocumentation(id);
      itemDocumentation.setDescription(new Description(String.format("Value of %s converted to decimal degrees.",inputField)));
      itemDocumentation.setFunctionNames(Arrays.asList(functionNames));
      return itemDocumentation;
    }

  private Transformer transformer = DEFAULT_TRANSFORMER;

  private List<LatLongConfig> latLongConfigs = DEFAULT_CONFIGS;

  public void addLatLong(Map<String, Object> geocodingResult)
  {
    for (LatLongConfig config : this.latLongConfigs)
    {

    MapPoint nyspPoint = getNyspPoint(geocodingResult,config);
    if (nyspPoint.isValid())
    {
      MapPoint latLongPoint = this.transformer.transform(nyspPoint.getX(), nyspPoint.getY());
      geocodingResult.put(config.getLongName(), latLongPoint.getX());
      geocodingResult.put(config.getLatName(), latLongPoint.getY());
    }

    }

  }

  public MapPoint getNyspPoint(Map<String, Object> geocodingResult, LatLongConfig config)
  {
    MapPoint mapPoint = new MapPoint();
    if (geocodingResult.containsKey(config.getXCoordInputName()) && geocodingResult.containsKey(config.getYCoordInputName()))
    {
      mapPoint.setX(doubleValueOf(geocodingResult.get(config.getXCoordInputName())));
      mapPoint.setY(doubleValueOf(geocodingResult.get(config.getYCoordInputName())));
    }
    return mapPoint;
  }

  public List<ItemDocumentation> getItemDocumentation()
  {
    List<ItemDocumentation> allDocs = new ArrayList<ItemDocumentation>();
    for (LatLongConfig config : this.latLongConfigs)
    {
        allDocs.add(config.getLatDocumentation());
        allDocs.add(config.getLongDocumentation());
    }
    return allDocs;
  }

  public Transformer getTransformer()
  {
    return transformer;
  }

  public void setTransformer(Transformer transformer)
  {
    this.transformer = transformer;
  }

  public List<LatLongConfig> getLatLongConfigs()
  {
    return latLongConfigs;
  }

  public void setLatLongConfigs(List<LatLongConfig> latLongConfigs)
  {
    this.latLongConfigs = latLongConfigs;
  }

  private double doubleValueOf(Object value)
  {
    if (value != null)
    {
      String s = value.toString();
      if (NumberUtils.isParsable(s))
      {
        return Double.valueOf(s);
      }
    }
    return 0.0;
  }
}
