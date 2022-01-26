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

import java.util.ArrayList;
import java.util.List;

import gov.nyc.doitt.gis.geoclient.service.search.InputValue;
import gov.nyc.doitt.gis.geoclient.service.search.Search;
import gov.nyc.doitt.gis.geoclient.service.search.SearchResult;
import gov.nyc.doitt.gis.geoclient.service.search.request.Request;

public class DefaultSearchDepthPolicy extends AbstractPolicy implements SearchDepthPolicy
{
    public static final boolean DEFAULT_ASSIGNED_VALUE_REQUESTS_CAN_SPAWN_SUBSEARCH = false;
    public static final int DEFAULT_MAX_DEPTH = 3;

    /**
     * Whether a {@link CallBuilder} which contains an assigned {@link InputValue}
     * (e.g. a guess) can spawn subsearches. The default is false.
     */
    private boolean assignedValueRequestsCanSpawnSubsearch = DEFAULT_ASSIGNED_VALUE_REQUESTS_CAN_SPAWN_SUBSEARCH;

    /**
     * Maximum number of sub-search levels that can be performed. Note that
     * there may be more than one search performed at a given level (e.g.
     * missing borough results in 5 level n searches) so this value does not
     * necessarily reflect the maximum amount for searches that will be
     * performed.
     */
    private int maximumDepth = DEFAULT_MAX_DEPTH;

    public boolean continueSearch(SearchResult searchResult)
    {
        return !inputForSubSearches(searchResult).isEmpty();
    }

    public int getMaximumDepth()
    {
        return maximumDepth;
    }

    @Override
    public List<Search> inputForSubSearches(SearchResult searchResult)
    {
        List<Search> subSearchable = new ArrayList<>();
        int currentLevel = searchResult.getCurrentLevel();
        for (Search search : searchResult.getSearches())
        {
            if (search.getLevel() == currentLevel && search.isRejected() && nextLevelEnabled(search.getRequest()))
            {
                subSearchable.add(search);
            }
        }

        return subSearchable;
    }

    // TODO TESTME
    @Override
    public String getDescription()
    {
        String s = this.maximumDepth == 1 ? "" : "s";
        StringBuffer buff = new StringBuffer(String.format("A rejected search can spawn %d sub-search level%s.", this.maximumDepth,s));
        String not = this.assignedValueRequestsCanSpawnSubsearch ? "" : "not";
        buff.append(String.format(" Rejected searches built using an assigned (guessed) value can%s spawn sub-searches.", not));
        return buff.toString();
    }

    public boolean levelEnabled(int level)
    {
        return this.maximumDepth >= level;
    }

    public boolean nextLevelEnabled(Request request)
    {
        if(!isAssignedValueRequestsCanSpawnSubsearch() && request.containsAssignedValue())
        {
            return false;
        }
        return levelEnabled(request.getLevel() + 1);
    }

    public void setMaximumDepth(int maximumDepth)
    {
        this.maximumDepth = maximumDepth;
    }

    public boolean isAssignedValueRequestsCanSpawnSubsearch()
    {
        return assignedValueRequestsCanSpawnSubsearch;
    }

    public void setAssignedValueRequestsCanSpawnSubsearch(boolean assignedValueRequestsCanSpawnSubsearch)
    {
        this.assignedValueRequestsCanSpawnSubsearch = assignedValueRequestsCanSpawnSubsearch;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (assignedValueRequestsCanSpawnSubsearch ? 1231 : 1237);
        result = prime * result + maximumDepth;
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
        DefaultSearchDepthPolicy other = (DefaultSearchDepthPolicy) obj;
        if (assignedValueRequestsCanSpawnSubsearch != other.assignedValueRequestsCanSpawnSubsearch)
            return false;
        if (maximumDepth != other.maximumDepth)
            return false;
        return true;
    }
}
