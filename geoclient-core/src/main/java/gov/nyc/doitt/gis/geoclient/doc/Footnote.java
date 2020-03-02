/*
 * Copyright 2013-2019 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.doc;

public class Footnote
{
    private String symbol;
    private int position;

    public Footnote()
    {
        super();
    }

    public Footnote(String symbol, int position)
    {
        super();
        this.symbol = symbol;
        this.position = position;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public boolean linksTo(Footnote otherFootnote)
    {
        return this.symbol.equals(otherFootnote.symbol);
    }
    
    @Override
    public String toString()
    {
        return toHtml();
    }

    public String toHtml()
    {
        return String.format("<sup>%s</sup>", this.symbol);
    }
}
