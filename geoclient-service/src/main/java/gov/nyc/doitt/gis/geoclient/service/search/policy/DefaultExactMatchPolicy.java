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

public class DefaultExactMatchPolicy extends AbstractPolicy implements ExactMatchPolicy
{
    public static final int DEFAULT_EXACT_MATCH_MAX_LEVEL = 0;
    public static final boolean DEFAULT_EXACT_MATCH_FOR_SINGLE_SUCCESS = false;

    /**
     * The maximum level at which a successful search can be considered an exact
     * match. Default is 0 (i.e., the first unaltered search). Note that if
     * exactMatchForSingleSuccess is set to 'true', then a SearchResult
     * containing exactly one successful Search will be considered an exact
     * match even if the level is greater than this value;
     */
    private int exactMatchMaxLevel = DEFAULT_EXACT_MATCH_MAX_LEVEL;

    /**
     * Whether a SearchResult containing exactly one successful Search should be
     * considered an exact match even its level is greater than the
     * exactMatchLevel.
     */
    private boolean exactMatchForSingleSuccess = DEFAULT_EXACT_MATCH_FOR_SINGLE_SUCCESS;

    public int getExactMatchMaxLevel()
    {
        return exactMatchMaxLevel;
    }

    public void setExactMatchMaxLevel(int exactMatchThreshold)
    {
        this.exactMatchMaxLevel = exactMatchThreshold;
    }

    public boolean isExactMatchForSingleSuccess()
    {
        return exactMatchForSingleSuccess;
    }

    public void setExactMatchForSingleSuccess(boolean exactMatchForSingleSuccess)
    {
        this.exactMatchForSingleSuccess = exactMatchForSingleSuccess;
    }

    @Override
    public Search findExactMatch(SearchResult searchResult)
    {
        if(this.isExactMatchForSingleSuccess() && searchResult.successCount() == 1)
        {
            List<Search> searches = searchResult.getSearches();
            for (Search search : searches)
            {
                if(!search.isRejected())
                {
                    return search;
                }
            }
        }
        List<Search> searches = searchResult.successfulSearchesEqualOrLessThan(exactMatchMaxLevel);
        if(!searches.isEmpty())
        {
            // Choose the first one
            return searches.get(0);
        }
        return null;
    }

    // TODO TESTME
    @Override
    public String getDescription()
    {
        StringBuffer buff = new StringBuffer(String.format("The first successful search at level %d is considered an exact match", this.exactMatchMaxLevel));

        if(this.exactMatchForSingleSuccess)
        {
            buff.append(" If there is only one successful search, it is considered an exact match regardless of its level.");
        }

        return buff.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (exactMatchForSingleSuccess ? 1231 : 1237);
        result = prime * result + exactMatchMaxLevel;
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
        DefaultExactMatchPolicy other = (DefaultExactMatchPolicy) obj;
        if (exactMatchForSingleSuccess != other.exactMatchForSingleSuccess)
            return false;
        if (exactMatchMaxLevel != other.exactMatchMaxLevel)
            return false;
        return true;
    }

}
