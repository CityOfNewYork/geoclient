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
package gov.nyc.doitt.gis.geoclient.service.search.task;

import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportService;
import gov.nyc.doitt.gis.geoclient.service.search.Response;
import gov.nyc.doitt.gis.geoclient.service.search.ResponseStatus;
import gov.nyc.doitt.gis.geoclient.service.search.Search;
import gov.nyc.doitt.gis.geoclient.service.search.request.Request;

import java.util.Map;
import java.util.concurrent.Callable;

import org.dozer.Mapper;

public abstract class SearchTask implements Callable<Search>
{
	protected final Request request;
	protected final GeosupportService geosupportService;
	private final Mapper mapper;
	
	public SearchTask(Request request, GeosupportService geosupportService, Mapper mapper)
	{
		super();
		this.request = request;
		this.geosupportService = geosupportService;
		this.mapper = mapper;
	}

	@Override
	public Search call() throws Exception
	{
		Map<String, Object> actualResult = doCall();
		ResponseStatus responseStatus = new ResponseStatus();
		this.mapper.map(actualResult, responseStatus);
		return new Search(request, new Response(responseStatus, actualResult));
	}
	
	protected abstract Map<String, Object> doCall();
}
