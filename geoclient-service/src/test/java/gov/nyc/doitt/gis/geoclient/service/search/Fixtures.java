package gov.nyc.doitt.gis.geoclient.service.search;

import gov.nyc.doitt.gis.geoclient.config.ReturnCodeValue;
import gov.nyc.doitt.gis.geoclient.parser.Input;
import gov.nyc.doitt.gis.geoclient.parser.LocationTokens;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.service.search.policy.SearchPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.request.AddressRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Fixtures
{
	public final Input input;
	public final List<Chunk> chunks;
	public final LocationTokens locationTokens;
	public final SearchPolicy searchPolicy;
	public final SearchResult searchResult;
	public final AddressRequest requestLevelZero;
	public final AddressRequest requestLevelOne;
	public final AddressRequest requestLevelTwo;
	public final AddressRequest requestLevelThree;
	public final AddressRequest requestLevelFour;
	public final Response responseSuccess;
	public final Response responseReject;
	public final ResponseStatus successStatus;
	public final ResponseStatus rejectStatus;
	public final Map<String, Object> geocodes;
	
	public Fixtures()
	{
		super();
		this.input = new Input("1-junit-test","59 Maiden Lane, Manhattan");
		this.chunks = new ArrayList<>();
		this.searchPolicy = new SearchPolicy();
		this.locationTokens = new LocationTokens(input, chunks);
		this.searchResult = new SearchResult(this.searchPolicy, locationTokens);
		this.requestLevelZero = new AddressRequest();
		this.requestLevelZero.setLevel(SearchPolicy.INITIAL_SEARCH_LEVEL);
		this.requestLevelOne = new AddressRequest();
		this.requestLevelOne.setLevel(SearchPolicy.INITIAL_SEARCH_LEVEL + 1);
		this.requestLevelTwo = new AddressRequest();
		this.requestLevelTwo.setLevel(SearchPolicy.INITIAL_SEARCH_LEVEL + 2);
		this.requestLevelThree = new AddressRequest();
		this.requestLevelThree.setLevel(SearchPolicy.INITIAL_SEARCH_LEVEL + 3);
		this.requestLevelFour = new AddressRequest();
		this.requestLevelFour.setLevel(SearchPolicy.INITIAL_SEARCH_LEVEL + 3);
		
		GeosupportReturnCode grc00 = new GeosupportReturnCode();
		grc00.setReturnCode(ReturnCodeValue.SUCCESS.value());
		successStatus = new ResponseStatus();
		successStatus.setGeosupportReturnCode(grc00);
		this.responseSuccess = new Response(successStatus, null);
		
		GeosupportReturnCode grcEE = new GeosupportReturnCode();
		grcEE.setReturnCode("EE");
		rejectStatus = new ResponseStatus();
		rejectStatus.setGeosupportReturnCode(grcEE);
		this.responseReject = new Response(rejectStatus, null);
		geocodes = new TreeMap<>();
	}

}
