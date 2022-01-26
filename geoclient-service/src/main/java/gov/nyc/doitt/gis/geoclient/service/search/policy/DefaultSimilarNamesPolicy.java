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

import org.apache.commons.text.similarity.LevenshteinDistance;

public class DefaultSimilarNamesPolicy extends AbstractPolicy implements SimilarNamesPolicy
{
    public static final int DEFAULT_SIMILAR_NAMES_DISTANCE = 8;

    /**
     * This is the threshold for the Levenshtein Distance as computed by
     * {@link StringUtils#getLevenshteinDistance(CharSequence, CharSequence, int)}.
     */
    private int similarNamesDistance = DEFAULT_SIMILAR_NAMES_DISTANCE;

    private LevenshteinDistance levenshteinDistance = new LevenshteinDistance(DEFAULT_SIMILAR_NAMES_DISTANCE);

    public int getSimilarNamesDistance()
    {
        return similarNamesDistance;
    }

    public void setSimilarNamesDistance(int similarNamesDistance)
    {
        synchronized (levenshteinDistance) {
            if (this.levenshteinDistance.getThreshold() != similarNamesDistance) {
                this.levenshteinDistance = new LevenshteinDistance(similarNamesDistance);
            }
            this.similarNamesDistance = similarNamesDistance;
        }
    }

    @Override
    public boolean isSimilarName(String original, String proposed)
    {
        return this.levenshteinDistance.apply(clean(original), clean(proposed)) >= 0;
    }

    @Override
    public String getDescription()
    {
        return String.format(
                "Street name suggestion from Geosupport is considered a similar name if it is within a Levenshtein distance of %d when compared to the input street.",
                this.similarNamesDistance);
    }

    String clean(String string)
    {
        String clean = string.toUpperCase();
        // TODO need more stuff!
        clean = clean.replaceAll("(\\bSTREET\\b|\\bST\\b)", "");
        clean = clean.replaceAll("(\\bAVENUE\\b|\\bAVE\\b)", "");
        clean = clean.replaceAll("(\\bBOULEVARD\\b|\\bBLVD\\b|\\bBL\\b)", "");
        clean = clean.replaceAll("(\\bPLACE\\b|\\bPL\\b)", "");
        clean = clean.replaceAll("(\\bNORTH\\b|\\bSOUTH\\b|\\bEAST\\b|\\bWEST\\b)", "");
        clean = clean.replaceAll("(\\bPARKWAY\\b|\\bPKWY\\b)", "");
        clean = clean.trim();
        if (clean.isEmpty()) {
            // Screw it, return the original
            return string;
        }
        return clean;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
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
        DefaultSimilarNamesPolicy other = (DefaultSimilarNamesPolicy) obj;
        if (similarNamesDistance != other.similarNamesDistance)
            return false;
        return true;
    }
}
