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
package gov.nyc.doitt.gis.geoclient.service.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.nyc.doitt.gis.geoclient.doc.DataDictionary;
import gov.nyc.doitt.gis.geoclient.doc.FunctionDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.function.Function;

public class Documentation
{
	public static final String ADDRESS_FUNCTION = "Address";
	public static final String BBL_FUNCTION = "BBL";
	public static final String BIN_FUNCTION = "BIN";
	public static final String BLOCKFACE_FUNCTION = "Blockface";
	public static final String INTERSECTION_FUNCTION = "Intersection";
	public static final String PLACE_FUNCTION = "Place";
	public static final String ADDRESS_AND_PLACE_FUNCTIONS = ADDRESS_FUNCTION + "/" + PLACE_FUNCTION;
	public static final Map<String, String> FUNCTION_MAPPING = new HashMap<String, String>();
	static
	{
		FUNCTION_MAPPING.put(Function.F1B, ADDRESS_AND_PLACE_FUNCTIONS);
		FUNCTION_MAPPING.put(Function.FBL, BBL_FUNCTION);
		FUNCTION_MAPPING.put(Function.FBN, BIN_FUNCTION);
		FUNCTION_MAPPING.put(Function.F3, BLOCKFACE_FUNCTION);
		FUNCTION_MAPPING.put(Function.F2, INTERSECTION_FUNCTION);
	}
	private FunctionDocumentation addressDocumentation;
	private FunctionDocumentation bblDocumentation;
	private FunctionDocumentation binDocumentation;
	private FunctionDocumentation blockfaceDocumentation;
	private DataDictionary dataDictionary;
	private FunctionDocumentation intersectionDocumentation;
	private FunctionDocumentation placeDocumentation;
	private List<FunctionDocumentation> allFunctionDocumentation;

	public boolean isSupported(ItemDocumentation itemDocumentation)
	{
		return !applicableFunctions(itemDocumentation).isEmpty();
	}

	public List<String> applicableFunctions(ItemDocumentation itemDocumentation)
	{
		List<String> result = new ArrayList<String>();
		List<String> ids = itemDocumentation.getFunctionNames();
		if (ids != null)
		{
			for (String id : ids)
			{
				if (FUNCTION_MAPPING.containsKey(id))
				{
					result.add(FUNCTION_MAPPING.get(id));
				}
			}
		}
		return result;
	}

	public List<FunctionDocumentation> getAllFunctionDocumentation()
	{
		if (this.allFunctionDocumentation == null)
		{
			allFunctionDocumentation = new ArrayList<FunctionDocumentation>();
			allFunctionDocumentation.add(addressDocumentation);
			allFunctionDocumentation.add(bblDocumentation);
			allFunctionDocumentation.add(binDocumentation);
			allFunctionDocumentation.add(blockfaceDocumentation);
			allFunctionDocumentation.add(intersectionDocumentation);
			allFunctionDocumentation.add(placeDocumentation);
		}
		return allFunctionDocumentation;
	}

	public FunctionDocumentation getAddressDocumentation()
	{
		return addressDocumentation;
	}

	public FunctionDocumentation getBblDocumentation()
	{
		return bblDocumentation;
	}

	public FunctionDocumentation getBinDocumentation()
	{
		return binDocumentation;
	}

	public FunctionDocumentation getBlockfaceDocumentation()
	{
		return blockfaceDocumentation;
	}

	public DataDictionary getDataDictionary()
	{
		return dataDictionary;
	}

	public FunctionDocumentation getIntersectionDocumentation()
	{
		return intersectionDocumentation;
	}

	public FunctionDocumentation getPlaceDocumentation()
	{
		return placeDocumentation;
	}

	public void setAddressDocumentation(FunctionDocumentation addressDocumentation)
	{
		this.addressDocumentation = addressDocumentation;
		//this.addressDocumentation.setDisplayName(ADDRESS_FUNCTION);
	}

	public void setBblDocumentation(FunctionDocumentation bblDocumentation)
	{
		this.bblDocumentation = bblDocumentation;
		//this.bblDocumentation.setDisplayName(BBL_FUNCTION);
	}

	public void setBinDocumentation(FunctionDocumentation binDocumentation)
	{
		this.binDocumentation = binDocumentation;
		//this.binDocumentation.setDisplayName(BIN_FUNCTION);
	}

	public void setBlockfaceDocumentation(FunctionDocumentation blockfaceDocumentation)
	{
		this.blockfaceDocumentation = blockfaceDocumentation;
		//this.blockfaceDocumentation.setDisplayName(BLOCKFACE_FUNCTION);
	}

	public void setDataDictionary(DataDictionary dataDictionary)
	{
		this.dataDictionary = dataDictionary;
	}

	public void setIntersectionDocumentation(FunctionDocumentation intersectionDocumentation)
	{
		this.intersectionDocumentation = intersectionDocumentation;
		//this.intersectionDocumentation.setDisplayName(INTERSECTION_FUNCTION);
	}

	public void setPlaceDocumentation(FunctionDocumentation placeDocumentation)
	{
		this.placeDocumentation = placeDocumentation;
		//this.placeDocumentation.setDisplayName(PLACE_FUNCTION);
	}

}
