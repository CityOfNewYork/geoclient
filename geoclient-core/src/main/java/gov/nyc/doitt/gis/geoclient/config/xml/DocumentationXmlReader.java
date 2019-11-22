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
package gov.nyc.doitt.gis.geoclient.config.xml;

import java.util.List;

import gov.nyc.doitt.gis.geoclient.doc.DataDictionary;
import gov.nyc.doitt.gis.geoclient.doc.Description;
import gov.nyc.doitt.gis.geoclient.doc.FunctionDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.GroupDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.GroupMember;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.doc.Paragraph;
import gov.nyc.doitt.gis.geoclient.doc.Table;
import gov.nyc.doitt.gis.geoclient.doc.TableData;
import gov.nyc.doitt.gis.geoclient.doc.TableRow;
import gov.nyc.doitt.gis.geoclient.util.ClassUtils;

public class DocumentationXmlReader
{
  static final String CLASS_CODE_PROPERTY_KEY = "key";
  static final String CLASS_CODE_PROPERTY_VALUE = "value";
  static final String CLASS_DATA_DICTIONARY_PROPERTY_ITEMS = "items";
  static final String CLASS_DESCRIPTION_PROPERTY_PARAGRAPHS = "paragraphs";
  static final String CLASS_DOCUMENTATION_PROPERTY_ID = "id";
  static final String CLASS_DOCUMENTATION_PROPERTY_TABLES = "tables";
  static final String CLASS_GROUP_DOCUMENTATION_PROPERTY_ID = "id";
  static final String CLASS_GROUP_DOCUMENTATION_PROPERTY_ITEM_DOCUMENTATION = "itemDocumentation";
  static final String CLASS_GROUP_MEMBER_PROPERTY_ITEM_DOCUMENTATION = "itemDocumentation";
  static final String CLASS_GROUP_MEMBER_PROPERTY_ID = "id";
  static final String CLASS_GROUP_MEMBER_PROPERTY_SIZE_INDICATOR = "sizeIndicator";
  static final String CLASS_TABLE_PROPERTY_ID = "id";
  static final String CLASS_TABLE_PROPERTY_ROWS = "rows";
  static final String CLASS_TABLE_ROW_PROPERTY_COLUMNS = "columns";
  static final String XML_ALIAS_ELEMENT = "alias";
  static final String XML_DATA_DICTIONARY_ELEMENT = "dataDictionary";
  static final String XML_DESCRIPTION_ELEMENT = "description";
  static final String XML_DOCUMENTATION_ATTRIBUTE_ID = "id";
  static final String XML_DOCUMENTATION_ID_ELEMENT = "documentationId";
  static final String XML_DOCUMENTATION_ELEMENT = "documentation";
  static final String XML_EXTERNAL_LINK = "externalLink";
  static final String XML_FIELD_DOCUMENTATION_ATTRIBUTE_DOCUMENTATION_ID = "documentationId";
  static final String XML_FIELD_DOCUMENTATION_ATTRIBUTE_GROUP_ID = "groupId";
  static final String XML_FOOTNOTE_ATTRIBUTE_POSITION = "position";
  static final String XML_FUNCTION_DOCUMENTATION_ELEMENT = "functionDocumentation";
  static final String XML_FUNCTION_NAME_ELEMENT = "functionName";
  static final String XML_GROUP_DOCUMENTATION_ATTRIBUTE_ID = "id";
  static final String XML_GROUP_DOCUMENTATION_ELEMENT = "groupDocumentation";
  static final String XML_GROUP_MEMBER_ATTRIBUTE_SIZE_INDICATOR = "sizeIndicator";
  static final String XML_GROUP_MEMBER_ATTRIBUTE_ID = "id";
  static final String XML_GROUP_MEMBER_ELEMENT = "groupMember";
  static final String XML_PARAGRAPH_ELEMENT = "p";
  static final String XML_ROOT_ELEMENT = "geoclientDocumentation";
  static final String XML_TABLE_ATTRIBUTE_ID = "id";
  static final String XML_TABLE_ELEMENT = "table";
  static final String XML_TD_ATTRIBUTE_COLSPAN = "colspan";
  static final String XML_TD_ELEMENT = "td";
  static final String XML_TH_ELEMENT = "th";
  static final String XML_TR_ELEMENT = "tr";
  private DataDictionary dataDictionary;
  private List<GroupDocumentation> fieldGroups;
  private List<FunctionDocumentation> functions;

  public FunctionDocumentation getFunctionDocumentation(String id)
  {
    for (FunctionDocumentation fDoc : this.functions)
    {
      if(fDoc.getId().equals(id))
      {
        return fDoc;
      }
    }
    return null;
  }

  public List<FunctionDocumentation> getFunctions()
  {
    return functions;
  }

  public void setFunctions(List<FunctionDocumentation> functions)
  {
    this.functions = functions;
  }

  public DataDictionary getDataDictionary()
  {
    return dataDictionary;
  }

  public void setDataDictionary(DataDictionary dataDictionary)
  {
    this.dataDictionary = dataDictionary;
  }

  public List<GroupDocumentation> getFieldGroups()
  {
    return fieldGroups;
  }

  public void setFieldGroups(List<GroupDocumentation> fieldGroups)
  {
    this.fieldGroups = fieldGroups;
  }

  public static DocumentationXmlReader fromXml(String configFile)
  {

    XStreamBuilder builder = new XStreamBuilder();

    builder.addDefaultPermissions()
    .addAllClassesInSamePackageAs(DocumentationXmlReader.class)
    .addAllClassesInSamePackageAs(ItemDocumentation.class)
    .setReferenceMode() // Use reference="another id" to reference elements
    .alias(XML_ROOT_ELEMENT, DocumentationXmlReader.class) // <geoclient>
    .alias(XML_DATA_DICTIONARY_ELEMENT, DataDictionary.class) // <dataDictionary>
    .addImplicitCollection(DataDictionary.class, CLASS_DATA_DICTIONARY_PROPERTY_ITEMS, ItemDocumentation.class) // <dataDictionary><documentation>...<docmentation>... to DataDictionary.items
    .alias(XML_DOCUMENTATION_ELEMENT, ItemDocumentation.class) // <documentation> to class ItemDocumentation
    .aliasAttribute(ItemDocumentation.class, CLASS_DOCUMENTATION_PROPERTY_ID, XML_DOCUMENTATION_ATTRIBUTE_ID) // <documentation id="">
    .addImplicitCollection(ItemDocumentation.class, CLASS_DOCUMENTATION_PROPERTY_TABLES, Table.class) // <documentation>...<table>...<table>... to Document.tables
    .alias(XML_DOCUMENTATION_ID_ELEMENT,String.class) // <documentation>...<seeAlso><documentationId>foo</documentationId><seeAlso>...
    .alias(XML_EXTERNAL_LINK,String.class) // <documentation>...<seeAlso><externalLink>http://google.com</externalLink><seeAlso>...
    .alias(XML_ALIAS_ELEMENT,String.class) // <documentation>...<aliases><alias>myOtherName</alias><aliases>...
    .alias(XML_FUNCTION_NAME_ELEMENT, String.class) // <functionName>Foo</functionName> to class String
    .alias(XML_DESCRIPTION_ELEMENT, Description.class) // <description>
    .addImplicitCollection(Description.class, CLASS_DESCRIPTION_PROPERTY_PARAGRAPHS, Paragraph.class) // <description><p>...<p>... to Description.paragraphs
    .alias(XML_PARAGRAPH_ELEMENT, Paragraph.class) // <p>
    .registerToStringConverter(Paragraph.class) // <p>Some text</p> to new Paragraph("Some text")
    .alias(XML_TABLE_ELEMENT, Table.class) // <table>
    .aliasAttribute(Table.class, CLASS_TABLE_PROPERTY_ID, XML_TABLE_ATTRIBUTE_ID) // <table id=""> to Table.id
    .addImplicitCollection(Table.class, CLASS_TABLE_PROPERTY_ROWS, TableRow.class) // <table><tr>...<tr>... to Table.rows
    .alias(XML_TR_ELEMENT, TableRow.class) // <tr>
    .addImplicitCollection(TableRow.class, CLASS_TABLE_ROW_PROPERTY_COLUMNS, TableData.class) // <tr><th>...<th>... and <tr><td>...<td>... to TableRow.columns
    .alias(XML_TH_ELEMENT, TableData.class) // <th>
    .alias(XML_TD_ELEMENT, TableData.class) // <td>
    .registerConverter(new TableDataConverter()) // <th> and <td>
    .alias(XML_GROUP_DOCUMENTATION_ELEMENT, GroupDocumentation.class) // <groupDocumentation>
    .alias(XML_GROUP_MEMBER_ELEMENT, GroupMember.class) // <groupMember>
    .aliasField(XML_DOCUMENTATION_ELEMENT,GroupDocumentation.class,CLASS_GROUP_DOCUMENTATION_PROPERTY_ITEM_DOCUMENTATION) // <documentation> to be mapped to GroupDocumentation.itemDocumentation
    .aliasField(XML_DOCUMENTATION_ELEMENT,GroupMember.class,CLASS_GROUP_MEMBER_PROPERTY_ITEM_DOCUMENTATION) // <documentation> to be mapped to GroupMember.itemDocumentation
    .aliasAttribute(GroupDocumentation.class, CLASS_GROUP_DOCUMENTATION_PROPERTY_ID, XML_GROUP_DOCUMENTATION_ATTRIBUTE_ID) // <groupDocumentation id="">
    .aliasAttribute(GroupMember.class, CLASS_GROUP_MEMBER_PROPERTY_ID, XML_GROUP_MEMBER_ATTRIBUTE_ID) // <groupMember pattern="">
    .aliasAttribute(GroupMember.class, CLASS_GROUP_MEMBER_PROPERTY_SIZE_INDICATOR, XML_GROUP_MEMBER_ATTRIBUTE_SIZE_INDICATOR) // <groupMember sizeIndicator="">
    .alias(XML_FUNCTION_DOCUMENTATION_ELEMENT,FunctionDocumentation.class);

    return (DocumentationXmlReader) builder.build().fromXML(ClassUtils.getDefaultClassLoader().getResourceAsStream(configFile));
  }

}
