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
package gov.nyc.doitt.gis.geoclient.service.search.web.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import gov.nyc.doitt.gis.geoclient.service.search.Search;
import gov.nyc.doitt.gis.geoclient.service.search.SearchResult;
import gov.nyc.doitt.gis.geoclient.service.search.policy.Policy;
import gov.nyc.doitt.gis.geoclient.service.search.policy.SearchPolicy;

public class SearchResultConverter implements Converter<ParamsAndResult, SearchResponse>
{
    @Override
    public SearchResponse convert(ParamsAndResult source)
    {
        SearchResult searchResult = source.getSearchResult();
        SearchResponse target = new SearchResponse();
        target.setInput(searchResult.getInputString());
        target.setId(searchResult.getId());
        setStatus(source, target);
        setSearches(source, target);
        setPolicy(source, target);
        setParseTree(source, target);
        return target;
    }

    private void setStatus(ParamsAndResult source, SearchResponse target)
    {
        if(source.getSearchResult().successCount() > 0)
        {
            target.setStatus(Status.OK);
        }else
        {
            target.setStatus(Status.REJECTED);
        }
    }

    protected void setSearches(ParamsAndResult source, SearchResponse target)
    {
        SearchParameters searchParameters = source.getSearchParameters();
        SearchResult searchResult = source.getSearchResult();
        List<SearchSummary> results = new ArrayList<>();

        if(!searchParameters.isReturnPossiblesWithExactMatch() && searchResult.isExactMatch())
        {
            // Return exact match only
            results.add(summarize(searchResult.getExactMatch(), MatchStatus.EXACT_MATCH));
        } else {
            // Either there are successes but no exact match
            // or
            // There is an exact match and return of all successes is requested
            results.addAll(summarize(searchResult, true));
        }

        if(searchParameters.isReturnRejections())
        {
            results.addAll(summarize(searchResult, false));
        }

        target.setResults(results);

    }

    protected void setPolicy(ParamsAndResult source, SearchResponse target)
    {
        if(source.getSearchParameters().isReturnPolicy())
        {
            SearchPolicy searchPolicy = source.getSearchResult().getSearchPolicy();
            List<PolicySummary> policy = new ArrayList<>(3);
            policy.add(summarize(searchPolicy.getSearchDepthPolicy()));
            policy.add(summarize(searchPolicy.getExactMatchPolicy()));
            policy.add(summarize(searchPolicy.getSimilarNamesPolicy()));
            target.setPolicy(policy);
        }
    }

    protected void setParseTree(ParamsAndResult source, SearchResponse target)
    {
        if(source.getSearchParameters().isReturnTokens())
        {
            SearchResult searchResult = source.getSearchResult();
            target.setParseTree(searchResult.getLocationTokens().getChunks());
        }
    }

    protected List<SearchSummary> summarize(SearchResult searchResult, boolean success)
    {
        List<Search> searches = success ? searchResult.getSuccessfulSearches() : searchResult.getRejectedSearches();
        Search exactMatch = success ? searchResult.getExactMatch() : null;
        List<SearchSummary> summaries = new ArrayList<>(searches.size());
        for (Search search : searches)
        {
            MatchStatus matchStatus = success ? MatchStatus.POSSIBLE_MATCH : MatchStatus.REJECTED;
            if(exactMatch != null && search.equals(exactMatch))
            {
                matchStatus = MatchStatus.EXACT_MATCH;
            }
            summaries.add(summarize(search, matchStatus));
        }
        return summaries;
    }

    protected SearchSummary summarize(Search search, MatchStatus matchStatus)
    {
        SearchSummary summary = new SearchSummary();
        summary.setLevel(String.format("%d",search.getLevel()));
        summary.setRequest(search.getRequest().summarize());
        summary.setResponse(search.getResponse().getGeocodes());
        summary.setStatus(matchStatus);
        return summary;
    }

    protected PolicySummary summarize(Policy policy)
    {
        PolicySummary policySummary = new PolicySummary();
        policySummary.setName(policy.getName());
        policySummary.setDescription(policy.getDescription());
        return policySummary;
    }
}
