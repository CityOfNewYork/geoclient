package gov.nyc.doitt.gis.geoclient.batch.internal.spring;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.transform.FieldSet;

import gov.nyc.doitt.gis.geoclient.batch.InputRecord;

public class LocationItemReader implements ItemReader<InputRecord> {

	private ItemReader<FieldSet> fieldSetReader;

	@Override
	public InputRecord read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return null;
	}

	public ItemReader<FieldSet> getFieldSetReader() {
		return fieldSetReader;
	}

	public void setFieldSetReader(ItemReader<FieldSet> fieldSetReader) {
		this.fieldSetReader = fieldSetReader;
	}

}
