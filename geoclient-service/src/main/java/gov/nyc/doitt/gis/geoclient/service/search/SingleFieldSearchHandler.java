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
package gov.nyc.doitt.gis.geoclient.service.search;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;

import gov.nyc.doitt.gis.geoclient.parser.Input;
import gov.nyc.doitt.gis.geoclient.parser.LocationTokenizer;
import gov.nyc.doitt.gis.geoclient.parser.LocationTokens;
import gov.nyc.doitt.gis.geoclient.service.search.policy.SearchPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.task.SearchTask;
import gov.nyc.doitt.gis.geoclient.service.search.task.SearchTaskFactory;

public class SingleFieldSearchHandler
{
  private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SingleFieldSearchHandler.class);
  private final SearchId searchId;
  private final LocationTokenizer locationTokenizer;
  private final SearchTaskFactory searchTaskFactory;
  private final ExecutorService executorService = Executors.newFixedThreadPool(4);

  public SingleFieldSearchHandler(SearchId searchId, LocationTokenizer locationTokenizer, SearchTaskFactory searchTaskFactory)
  {
    super();
    this.searchId = searchId;
    this.locationTokenizer = locationTokenizer;
    this.searchTaskFactory = searchTaskFactory;
  }

  public SearchResult findLocation(String inputString)
  {
    return findLocation(new SearchPolicy(), inputString);
  }

  public SearchResult findLocation(SearchPolicy searchPolicy, String inputString)
  {
    Input input = new Input(searchId.next(), inputString);
    LOGGER.info("Searching for {}", input);
    LocationTokens locationTokens = locationTokenizer.parse(input);
    // Initial search
    SearchResult searchResult = doInitialSearch(searchPolicy, locationTokens);
    if(searchResult.isExactMatch())
    {
      // We're done
      log(searchResult);
      return searchResult;
    }
    // Sub-searches
    doSubSearches(searchResult);
    log(searchResult);
    return searchResult;
  }

  protected SearchResult doInitialSearch(SearchPolicy searchPolicy, LocationTokens locationTokens)
  {
    List<SearchTask> searchTasks = this.searchTaskFactory.buildInitialSearchTasks(searchPolicy, locationTokens);
    SearchResult searchResult = new SearchResult(searchPolicy, locationTokens);
    doSearches(searchResult, searchTasks);
    return searchResult;
  }

  protected void doSubSearches(SearchResult searchResult)
  {
    List<SearchTask> subSearches = this.searchTaskFactory.buildSubsearchTasks(searchResult);
    while(!subSearches.isEmpty() && !searchResult.isExactMatch())
    {
      doSearches(searchResult, subSearches);
      subSearches = this.searchTaskFactory.buildSubsearchTasks(searchResult);
    }
  }

  protected void doSearches(SearchResult searchResult, List<SearchTask> searchTasks)
  {
    for (SearchTask searchTask : searchTasks)
    {
      Search search =  doSearch(searchTask);
      LOGGER.debug("Search {}", search);
      searchResult.add(search);
    }
  }

  protected Search doSearch(SearchTask searchTask)
  {
    Future<Search> future = this.executorService.submit(searchTask);
    try
    {
      return future.get();
    } catch (InterruptedException e)
    {
      Thread.currentThread().interrupt();
      future.cancel(true);
      throw new RuntimeException(e.getCause());
    } catch (ExecutionException executionException)
    {
      throw new RuntimeException(executionException.getCause());
    }
  }

  @PreDestroy
  public void releasePool()
  {
    if(this.executorService !=null)
    {
      this.executorService.shutdown();
    }
  }

  private void log(SearchResult searchResult)
  {
    LOGGER.info("Result for id: {} = {}", searchResult.getId(), searchResult);
  }
}
