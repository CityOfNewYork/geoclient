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
package gov.nyc.doitt.gis.geoclient.service.web;

import gov.nyc.doitt.gis.geoclient.service.domain.BadRequest;
import gov.nyc.doitt.gis.geoclient.service.domain.ServiceType;
import gov.nyc.doitt.gis.geoclient.service.domain.Version;
import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles RESTful requests for Geosupport data.
 */
@Controller
public class RestController
{

	public static final String ADDRESS_URI = "/address";
	public static final String BBL_URI = "/bbl";
	public static final String BIN_URI = "/bin";
	public static final String BLOCKFACE_URI = "/blockface";
	public static final String DOC_URI = "/doc";
	public static final String GEOSUPPORT_URI = "/geosupport";
	public static final String INTERSECTION_URI = "/intersection";
	public static final String PLACE_URI = "/place";
	public static final String VERSION_URI = "/version";
	
	public static final String ADDRESS_OBJ = ServiceType.ADDRESS.elementName();
	public static final String BBL_OBJ = ServiceType.BBL.elementName();
	public static final String BIN_OBJ = ServiceType.BIN.elementName();
	public static final String BLOCKFACE_OBJ = ServiceType.BLOCKFACE.elementName();
	public static final String DOC_OBJ = ServiceType.DOC.elementName();
	public static final String INTERSECTION_OBJ = ServiceType.INTERSECTION.elementName();
	public static final String PLACE_OBJ = ServiceType.PLACE.elementName();
	public static final String VERSION_OBJ = ServiceType.VERSION.elementName();
	
	public static final String DOC_VIEW_NAME = "index";

	private static final Logger logger = LoggerFactory.getLogger(RestController.class);

	@Autowired
	private GeosupportService geosupportService;

	@RequestMapping(value = ADDRESS_URI, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> address(
		    @RequestParam(required = false) String houseNumber,
			@RequestParam String street, 
			@RequestParam(required = false) String borough,
			@RequestParam(required = false) String zip) throws Exception
	{
		logger.debug("address[houseNumber='{}', street='{}', borough='{}', zip='{}']", houseNumber, street, borough, zip);
		if (borough == null && zip == null)
		{
			throw new MissingAnyOfOptionalServletRequestParametersException("borough", "zip");
		}
		Map<String, Object> addressMap = new HashMap<String, Object>();
		addressMap.put(ADDRESS_OBJ, this.geosupportService.callFunction1B(houseNumber, street, borough, zip));
		return addressMap;
	}
	
	@RequestMapping(value = PLACE_URI, method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> place(
			@RequestParam String name, 
			@RequestParam(required = false) String borough,
			@RequestParam(required = false) String zip) throws Exception
	{
		logger.debug("place[name='{}', borough='{}', zip='{}']", name, borough,zip);
		if (borough == null && zip == null)
		{
			throw new MissingAnyOfOptionalServletRequestParametersException("borough", "zip");
		}
		Map<String, Object> placeMap = new HashMap<String, Object>();
		placeMap.put(PLACE_OBJ, this.geosupportService.callFunction1B(null, name, borough, zip));
		return placeMap;
	}

	@RequestMapping(value = INTERSECTION_URI, method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> intersection(@RequestParam String crossStreetOne, @RequestParam String crossStreetTwo,
			@RequestParam String borough, @RequestParam(required = false) String boroughCrossStreetTwo,
			@RequestParam(required = false) String compassDirection)
	{
		logger.debug(
				"intersection[crossStreetOne='{}', crossStreetTwo='{}', borough='{}', boroughCrossStreetTwo='{}', compassDirection='{}']",
				crossStreetOne, crossStreetTwo, borough, boroughCrossStreetTwo, compassDirection);
		Map<String, Object> intersectionMap = new HashMap<String, Object>();
		intersectionMap.put(INTERSECTION_OBJ, this.geosupportService.callFunction2(crossStreetOne, borough,
				crossStreetTwo, boroughCrossStreetTwo, compassDirection));
		return intersectionMap;
	}

	@RequestMapping(value = BLOCKFACE_URI, method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> blockface(@RequestParam String onStreet, @RequestParam String crossStreetOne,
			@RequestParam String crossStreetTwo, @RequestParam String borough,
			@RequestParam(required = false) String boroughCrossStreetOne,
			@RequestParam(required = false) String boroughCrossStreetTwo,
			@RequestParam(required = false) String compassDirection)
	{
		logger.debug(
				"blockface[onStreet='{}',crossStreetOne='{}', crossStreetTwo='{}', borough='{}', boroughCrossStreetOne='{}', boroughCrossStreetTwo='{}', compassDirection='{}']",
				onStreet, crossStreetOne, crossStreetTwo, borough, boroughCrossStreetOne, boroughCrossStreetTwo,
				compassDirection);
		Map<String, Object> blockfaceMap = new HashMap<String, Object>();
		blockfaceMap.put(BLOCKFACE_OBJ, this.geosupportService.callFunction3(onStreet, borough, crossStreetOne,
				boroughCrossStreetOne, crossStreetTwo, boroughCrossStreetTwo, compassDirection));
		return blockfaceMap;
	}

	@RequestMapping(value = BBL_URI, method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> bbl(@RequestParam String borough, @RequestParam String block, @RequestParam String lot)
	{
		logger.debug("bbl[borough='{}',block='{}', lot='{}']", borough, block, lot);
		Map<String, Object> bblMap = new HashMap<String, Object>();
		bblMap.put(BBL_OBJ, this.geosupportService.callFunctionBL(borough, block, lot));
		return bblMap;
	}

	@RequestMapping(value = BIN_URI, method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> bin(@RequestParam String bin)
	{
		logger.debug("bin[bin='{}']", bin);
		Map<String, Object> binMap = new HashMap<String, Object>();
		binMap.put(BIN_OBJ, this.geosupportService.callFunctionBN(bin));
		return binMap;
	}

	@RequestMapping(value = GEOSUPPORT_URI, method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> geosupport(@RequestParam Map<String, Object> params)
	{
		logger.debug("geosupport[{}]", params);
		return this.geosupportService.callGeosupport(params);
	}

	@RequestMapping(value = DOC_URI, method = RequestMethod.GET)
	public String doc(ModelMap modelMap)
	{
		modelMap.put(DOC_OBJ, this.geosupportService.getDocumentation());
		modelMap.put(VERSION_OBJ, this.geosupportService.version());
		return DOC_VIEW_NAME;
	}

	@RequestMapping(value = VERSION_URI, method = RequestMethod.GET)
	public @ResponseBody
	Version version()
	{
		return this.geosupportService.version();
	}

	@ExceptionHandler(value = { MissingAnyOfOptionalServletRequestParametersException.class,
			MissingServletRequestParameterException.class })
	public @ResponseBody
	ResponseEntity<BadRequest> handleMissingRequestParameter(ServletRequestBindingException exception,
			HttpServletRequest req)
	{
		BadRequest badRequest = new BadRequest();
		badRequest.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
		badRequest.setMessage(exception.getMessage());
		badRequest.setRequestUri(String.format("%s?%s", req.getRequestURI(), req.getQueryString()));
		return new ResponseEntity<BadRequest>(badRequest, HttpStatus.BAD_REQUEST);
	}

	public void setGeosupportService(GeosupportService geosupportService)
	{
		this.geosupportService = geosupportService;
	}
}
