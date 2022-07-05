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

import java.util.ArrayList;
import java.util.List;

import gov.nyc.doitt.gis.geoclient.parser.LocationTokens;
import gov.nyc.doitt.gis.geoclient.service.search.policy.SearchPolicy;

public class SearchResult
{
    private final SearchPolicy searchPolicy;
    private final LocationTokens locationTokens;
    private final List<Search> searches = new ArrayList<>();
    private int exactMatchIndex = -1;
    private int currentLevel = 0;

    // TODO TESTME!!!
    public SearchResult(SearchPolicy searchPolicy, LocationTokens locationTokens)
    {
        super();
        this.searchPolicy = searchPolicy;
        this.locationTokens = locationTokens;
    }

    public String getId()
    {
        return this.locationTokens.getInput().getId();
    }

    public String getInputString()
    {
        return this.locationTokens.getInput().getUnsanitizedValue();
    }

    public void add(Search search)
    {
        if(currentLevel<search.getLevel())
        {
            currentLevel = search.getLevel();
        }
        this.searches.add(search);
    }

    public int getCurrentLevel()
    {
        return currentLevel;
    }

    public SearchPolicy getSearchPolicy()
    {
        return searchPolicy;
    }

    public Search getExactMatch()
    {
        if (!isExactMatch())
        {
            return null;
        }
        return this.searches.get(exactMatchIndex);
    }

    public boolean isExactMatch()
    {
        markExactMatch(searchPolicy.findExactMatch(this));
        return this.exactMatchIndex >= 0;
    }

    public List<Search> getSearches()
    {
        return searches;
    }

    public List<Search> getSuccessfulSearches()
    {
        List<Search> successfulSearches = new ArrayList<>();
        for (Search search : this.searches)
        {
            if (!search.isRejected())
            {
                successfulSearches.add(search);
            }
        }
        return successfulSearches;
    }

    public List<Search> getRejectedSearches()
    {
        List<Search> rejectedSearches = new ArrayList<>();
        for (Search search : this.searches)
        {
            if (search.isRejected())
            {
                rejectedSearches.add(search);
            }
        }
        return rejectedSearches;
    }

    public LocationTokens getLocationTokens()
    {
        return locationTokens;
    }

    public int successCount()
    {
        int i = 0;
        for (Search search : this.searches)
        {
            if (!search.isRejected())
            {
                i++;
            }
        }
        return i;
    }

    public List<Search> inputForSubSearches()
    {
        return this.searchPolicy.inputForSubSearches(this);
    }

    public List<Search> successfulSearchesEqualOrLessThan(int level)
    {
        List<Search> matchingSearches = new ArrayList<>();
        for (Search search : this.searches)
        {
            if (!search.isRejected() && search.lessThanOrEqualTo(level))
            {
                matchingSearches.add(search);
            }
        }
        return matchingSearches;
    }

    private void markExactMatch(Search search)
    {
        if (search != null)
        {
            int index = this.searches.indexOf(search);
            if (index < 0 || index + 1 > this.searches.size())
            {
                throw new IllegalArgumentException(String.format(
                        "Give index %d is out of range for the search list whose size is %d.", index,
                        this.searches.size()));
            }
            this.exactMatchIndex = index;
        }
    }

    @Override
    public String toString()
    {
        return "SearchResult [searches=" + searches.size() + ", locationTokens=" + locationTokens
                + ", exactMatchIndex=" + exactMatchIndex + "]";
    }

}
