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

import java.util.ArrayList;
import java.util.List;

public class TableRow
{
    private List<TableData> columns;

    public TableRow()
    {
        this(new ArrayList<TableData>());
    }

    public TableRow(List<TableData> tableData)
    {
        super();
        this.columns = tableData;
    }

    public List<TableData> getColumns()
    {
        return columns;
    }

    public void setColumns(List<TableData> columns)
    {
        this.columns = columns;
    }

    @Override
    public String toString()
    {
        return toHtml();
    }

    public String toHtml()
    {
        StringBuffer sb = new StringBuffer("<tr>");
        for (TableData td : columns)
        {
            sb.append(td.toHtml());
        }
        sb.append("</tr>");
        return sb.toString();
    }
}
