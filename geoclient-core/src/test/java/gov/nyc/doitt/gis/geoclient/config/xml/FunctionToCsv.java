package gov.nyc.doitt.gis.geoclient.config.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.doc.DataDictionary;
import gov.nyc.doitt.gis.geoclient.doc.ItemDocumentation;
import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;
import gov.nyc.doitt.gis.geoclient.jni.GeoclientStub;
import gov.nyc.doitt.gis.geoclient.util.OperatingSystemUtils;

public class FunctionToCsv
{
	private static Logger log = LoggerFactory.getLogger(FunctionToCsv.class);

	public static void main(String[] args) throws Exception
	{
		GeosupportConfig geosupportConfig = null;

		if (OperatingSystemUtils.isWindows())
		{
			geosupportConfig = new GeosupportConfig(new GeoclientStub());
		} else
		{
			geosupportConfig = new GeosupportConfig(new GeoclientStub());
		}
		log.debug("Name,Function,WorkArea");
		Function f1 = geosupportConfig.getFunction(Function.F1);
		logFields("All",f1.getWorkAreaOne());
		logFields(Function.F1,f1.getWorkAreaTwo());
		logFields(Function.F1A,geosupportConfig.getFunction(Function.F1A).getWorkAreaTwo());
		logFields(Function.F1AX,geosupportConfig.getFunction(Function.F1AX).getWorkAreaTwo());
		logFields(Function.F1B,geosupportConfig.getFunction(Function.F1B).getWorkAreaTwo());
		logFields(Function.F1E,geosupportConfig.getFunction(Function.F1E).getWorkAreaTwo());
		logFields(Function.F2,geosupportConfig.getFunction(Function.F2).getWorkAreaTwo());
		logFields(Function.F3,geosupportConfig.getFunction(Function.F3).getWorkAreaTwo());
		logFields(Function.FBL,geosupportConfig.getFunction(Function.FBL).getWorkAreaTwo());
		logFields(Function.FBN,geosupportConfig.getFunction(Function.FBN).getWorkAreaTwo());
		DataDictionary dataDictionary = geosupportConfig.getDataDictionary();
		for (ItemDocumentation itemDocumentation : dataDictionary.getItems())
		{
			logDataDictionaryItem(itemDocumentation);
		}
	}

	private static void logFields(String functionId, WorkArea workArea)
	{
		for (String fieldId : workArea.getFieldIds(Field.NAME_SORT, true, true))
		{
			log.debug("{},{},{}",fieldId,functionId,workArea.getId());
		}
	}
	
	private static void logDataDictionaryItem(ItemDocumentation itemDocumentation)
	{
		String id = itemDocumentation.getId();
		for (String functionId : itemDocumentation.getFunctionNames())
		{
			log.debug("{},{},{}",id,functionId,"DataDictionary");
		}
	}
}
