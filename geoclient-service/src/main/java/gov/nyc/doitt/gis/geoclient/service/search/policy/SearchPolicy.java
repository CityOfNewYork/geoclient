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
package gov.nyc.doitt.gis.geoclient.service.search.policy;

import java.util.List;

import gov.nyc.doitt.gis.geoclient.service.search.Search;
import gov.nyc.doitt.gis.geoclient.service.search.SearchResult;

/**
 * Instances of this class are used to determine what constitutes an exact match
 * and how many levels of sub-searching may be performed when the initial search
 * criteria are rejected.
 *
 * @author mlipper
 *
 */
public class SearchPolicy
{
    public static final int INITIAL_SEARCH_LEVEL = 0;

    /**
     * Exact match policy handler - a {@link DefaultExactMatchPolicy} by default.
     */
    private ExactMatchPolicy exactMatchPolicy = new DefaultExactMatchPolicy();

    /**
     * Search depth policy handler - a {@link DefaultSearchDepthPolicy} by default.
     */
    private SearchDepthPolicy searchDepthPolicy = new DefaultSearchDepthPolicy();

    /**
     * Similar names policy handler - a {@link DefaultSimilarNamesPolicy} by default.
     */
    private SimilarNamesPolicy similarNamesPolicy = new DefaultSimilarNamesPolicy();

    public Search findExactMatch(SearchResult searchResult)
    {
        return this.exactMatchPolicy.findExactMatch(searchResult);
    }

    public ExactMatchPolicy getExactMatchPolicy()
    {
        return exactMatchPolicy;
    }

    public SearchDepthPolicy getSearchDepthPolicy()
    {
        return searchDepthPolicy;
    }

    public SimilarNamesPolicy getSimilarNamesPolicy()
    {
        return similarNamesPolicy;
    }

    public List<Search> inputForSubSearches(SearchResult searchResult)
    {
        return this.searchDepthPolicy.inputForSubSearches(searchResult);
    }

    public boolean isSimilarName(String original, String proposed)
    {
        return this.similarNamesPolicy.isSimilarName(original, proposed);
    }

    public void setExactMatchPolicy(ExactMatchPolicy exactMatchPolicy)
    {
        this.exactMatchPolicy = exactMatchPolicy;
    }

    public void setSearchDepthPolicy(SearchDepthPolicy searchDepthPolicy)
    {
        this.searchDepthPolicy = searchDepthPolicy;
    }

    public void setSimilarNamesPolicy(SimilarNamesPolicy similarNamesPolicy)
    {
        this.similarNamesPolicy = similarNamesPolicy;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((exactMatchPolicy == null) ? 0 : exactMatchPolicy.hashCode());
        result = prime * result + ((searchDepthPolicy == null) ? 0 : searchDepthPolicy.hashCode());
        result = prime * result + ((similarNamesPolicy == null) ? 0 : similarNamesPolicy.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SearchPolicy other = (SearchPolicy) obj;
        if (exactMatchPolicy == null)
        {
            if (other.exactMatchPolicy != null)
                return false;
        } else if (!exactMatchPolicy.equals(other.exactMatchPolicy))
            return false;
        if (searchDepthPolicy == null)
        {
            if (other.searchDepthPolicy != null)
                return false;
        } else if (!searchDepthPolicy.equals(other.searchDepthPolicy))
            return false;
        if (similarNamesPolicy == null)
        {
            if (other.similarNamesPolicy != null)
                return false;
        } else if (!similarNamesPolicy.equals(other.similarNamesPolicy))
            return false;
        return true;
    }
}
