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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import gov.nyc.doitt.gis.geoclient.service.search.policy.DefaultExactMatchPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.policy.DefaultSearchDepthPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.policy.DefaultSimilarNamesPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.policy.SearchPolicy;

public class SearchParameters
{
    public static final int MAX_CONFIGURABLE_DEPTH = 6;
    @NotEmpty
    private String input;
    @Min(value=0)
    @Max(value=MAX_CONFIGURABLE_DEPTH)
    private int maxDepth = DefaultSearchDepthPolicy.DEFAULT_MAX_DEPTH;
    private int similarNamesDistance = DefaultSimilarNamesPolicy.DEFAULT_SIMILAR_NAMES_DISTANCE;
    private int exactMatchMaxLevel = DefaultExactMatchPolicy.DEFAULT_EXACT_MATCH_MAX_LEVEL;
    private boolean exactMatchForSingleSuccess = DefaultExactMatchPolicy.DEFAULT_EXACT_MATCH_FOR_SINGLE_SUCCESS;
    private boolean returnTokens = false;
    private boolean returnPolicy = false;
    private boolean returnRejections = false;
    private boolean returnPossiblesWithExactMatch = false;

    public SearchParameters()
    {
        super();
    }

    public SearchParameters(String input)
    {
        super();
        this.input = input;
    }

    public int getMaxDepth()
    {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth)
    {
        this.maxDepth = maxDepth;
    }

    public int getSimilarNamesDistance()
    {
        return similarNamesDistance;
    }

    public void setSimilarNamesDistance(int similarNamesDistance)
    {
        this.similarNamesDistance = similarNamesDistance;
    }

    public int getExactMatchMaxLevel()
    {
        return exactMatchMaxLevel;
    }

    public void setExactMatchMaxLevel(int exactMatchMaxLevel)
    {
        this.exactMatchMaxLevel = exactMatchMaxLevel;
    }

    public boolean isExactMatchForSingleSuccess()
    {
        return exactMatchForSingleSuccess;
    }

    public void setExactMatchForSingleSuccess(boolean exactMatchForSingleSuccess)
    {
        this.exactMatchForSingleSuccess = exactMatchForSingleSuccess;
    }

    public String getInput()
    {
        return input;
    }

    public void setInput(String input)
    {
        this.input = input;
    }

    public boolean isReturnTokens()
    {
        return returnTokens;
    }

    public void setReturnTokens(boolean returnTokens)
    {
        this.returnTokens = returnTokens;
    }

    public boolean isReturnPolicy()
    {
        return returnPolicy;
    }

    public void setReturnPolicy(boolean returnPolicy)
    {
        this.returnPolicy = returnPolicy;
    }

    public SearchPolicy buildSearchPolicy()
    {
        SearchPolicy policy = new SearchPolicy();
        configure((DefaultExactMatchPolicy) policy.getExactMatchPolicy());
        configure((DefaultSearchDepthPolicy) policy.getSearchDepthPolicy());
        configure((DefaultSimilarNamesPolicy) policy.getSimilarNamesPolicy());
        return policy;
    }

    private void configure(DefaultExactMatchPolicy policy)
    {
        policy.setExactMatchForSingleSuccess(isExactMatchForSingleSuccess());
        policy.setExactMatchMaxLevel(getExactMatchMaxLevel());
    }

    private void configure(DefaultSearchDepthPolicy policy)
    {
        policy.setMaximumDepth(getMaxDepth());
    }

    private void configure(DefaultSimilarNamesPolicy policy)
    {
        policy.setSimilarNamesDistance(getSimilarNamesDistance());
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (exactMatchForSingleSuccess ? 1231 : 1237);
        result = prime * result + exactMatchMaxLevel;
        result = prime * result + ((input == null) ? 0 : input.hashCode());
        result = prime * result + maxDepth;
        result = prime * result + (returnPolicy ? 1231 : 1237);
        result = prime * result + (returnTokens ? 1231 : 1237);
        result = prime * result + similarNamesDistance;
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
        SearchParameters other = (SearchParameters) obj;
        if (exactMatchForSingleSuccess != other.exactMatchForSingleSuccess)
            return false;
        if (exactMatchMaxLevel != other.exactMatchMaxLevel)
            return false;
        if (input == null)
        {
            if (other.input != null)
                return false;
        } else if (!input.equals(other.input))
            return false;
        if (maxDepth != other.maxDepth)
            return false;
        if (returnPolicy != other.returnPolicy)
            return false;
        if (returnTokens != other.returnTokens)
            return false;
        if (similarNamesDistance != other.similarNamesDistance)
            return false;
        return true;
    }

    public boolean isReturnRejections()
    {
        return returnRejections;
    }

    public void setReturnRejections(boolean returnRejections)
    {
        this.returnRejections = returnRejections;
    }

    public boolean isReturnPossiblesWithExactMatch()
    {
        return returnPossiblesWithExactMatch;
    }

    public void setReturnPossiblesWithExactMatch(boolean returnPossiblesWithExactMatch)
    {
        this.returnPossiblesWithExactMatch = returnPossiblesWithExactMatch;
    }
}
