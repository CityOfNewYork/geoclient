package gov.nyc.doitt.gis.geoclient.parser.test;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("specs")
public class UnparsedSpecs
{
	@XStreamImplicit(itemFieldName="spec")
	private List<UnparsedSpec> specs = new ArrayList<>();

	public List<UnparsedSpec> getSpecs()
	{
		return specs;
	}

	public void setSpecs(List<UnparsedSpec> specs)
	{
		this.specs = specs;
	}
}
