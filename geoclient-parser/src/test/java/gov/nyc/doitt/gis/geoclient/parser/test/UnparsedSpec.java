package gov.nyc.doitt.gis.geoclient.parser.test;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamConverter(value=ToAttributedValueConverter.class, strings={"body"})
public class UnparsedSpec
{
	private String id;
	private String test;
	private String body;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getTest()
	{
		return test;
	}
	public void setTest(String test)
	{
		this.test = test;
	}
	public String getBody()
	{
		return body;
	}
	public void setBody(String body)
	{
		this.body = body;
	}

}
