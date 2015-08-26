package gov.nyc.doitt.gis.geoclient.config;

import gov.nyc.doitt.gis.geoclient.function.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestData
{
	public static Field fieldOne = new Field("one", 1, 6);
	public static Field fieldTwo = new Field("two", 7, 6);
	public static Field fieldThree = new Field("three", 1, 12, true);
	public static Field fieldDuplicateIdOfOne = new Field("one", 13, 4);

	public static List<Field> newFieldList(Field... fields)
	{
		// Arrays.asList(fields) creates an unmodifiable List
		List<Field> result = new ArrayList<Field>();
		result.addAll(Arrays.asList(fields));
		return result;
	}

	public static List<Field> newFieldList(Comparator<Field> comparator, Field... fields)
	{
		// Arrays.asList(fields) creates an unmodifiable List
		List<Field> result = Arrays.asList(fields);
		Collections.sort(result);
		return new ArrayList<Field>(result);
	}

	public static Field makeField(String id, int start, int length)
	{
		return new Field(id, start, length);
	}
}
