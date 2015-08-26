package gov.nyc.doitt.gis.geoclient.parser.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ClassUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SpecBuilder
{
	private static final String PARSER_TEST_DATA_FILE = "specs.xml";
	private final XStream xStream;
	private final UnparsedSpecs unparsedTokenSpecs;

	public SpecBuilder()
	{
		xStream = new XStream(new DomDriver());
		xStream.setMode(XStream.ID_REFERENCES);
		xStream.processAnnotations(UnparsedSpecs.class);
		this.unparsedTokenSpecs = (UnparsedSpecs) xStream.fromXML(ClassUtils.getDefaultClassLoader().getResourceAsStream(
				PARSER_TEST_DATA_FILE));
	}

	public List<ChunkSpec> getSpecs(String testAttribute)
	{
		ChunkSpecParser specParser = new ChunkSpecParser();
		List<ChunkSpec> specs = new ArrayList<>();
		for (UnparsedSpec unparsed : this.unparsedTokenSpecs.getSpecs())
		{
			if (testAttribute.equalsIgnoreCase(unparsed.getTest()))
			{
				specs.add(specParser.parse(unparsed.getId(),unparsed.getBody()));
			}
		}
		return specs;
	}
}
