package gov.nyc.doitt.gis.geoclient.service.search;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import gov.nyc.doitt.gis.geoclient.parser.configuration.ParserConfig;
import gov.nyc.doitt.gis.geoclient.service.configuration.AppConfig;
import gov.nyc.doitt.gis.geoclient.service.search.policy.SearchPolicy;
import gov.nyc.doitt.gis.geoclient.service.search.request.AddressRequest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ParserConfig.class, AppConfig.class})
public class SingleFieldSearchHandlerTest
{
	@Autowired
	private SingleFieldSearchHandler singleFieldSearchHandler;

	@Test
	public void testFindLocationDefaultPolicy_exactAddressMatch()
	{
		SearchResult searchResult = this.singleFieldSearchHandler.findLocation("59 Maiden Ln Manhattan");
		assertThat(searchResult.isExactMatch(), is(true));
		assertThat(searchResult.getExactMatch(), not(nullValue()));
		assertThat(searchResult.getSearches().size(), equalTo(1));
		Search search = searchResult.getSearches().get(0);
		assertThat(search.getLevel(), equalTo(SearchPolicy.INITIAL_SEARCH_LEVEL));
	}

	@Test
	public void testFindLocationDefaultPolicy_addressWithoutBorough()
	{
		SearchResult searchResult = this.singleFieldSearchHandler.findLocation("948 Jamaica Ave");
		assertThat(searchResult.isExactMatch(), is(false));
		assertThat(searchResult.getExactMatch(), is(nullValue()));
		assertThat(searchResult.getSearches().size(), equalTo(5));
		for(Search search : searchResult.getSearches())
		{
			assertThat(search.getLevel(), equalTo(SearchPolicy.INITIAL_SEARCH_LEVEL + 1));
		}
	}

	@Test
	public void testFindLocationDefaultPolicy_addressWithZipAndNoBorough()
	{
		SearchResult searchResult = this.singleFieldSearchHandler.findLocation("280 RSD 10025");
		assertThat(searchResult.isExactMatch(), is(true));
		assertThat(searchResult.getExactMatch(), not(nullValue()));
		assertThat(searchResult.getSearches().size(), equalTo(1));
		Search search = searchResult.getSearches().get(0);
		assertThat(search.getLevel(), equalTo(SearchPolicy.INITIAL_SEARCH_LEVEL));
	}

	@Test
	public void testFindLocationDefaultPolicy_addressWithTwoValidSimilarNames()
	{
		SearchResult searchResult = this.singleFieldSearchHandler.findLocation("314 100 St Manhattan");
		assertThat(searchResult.isExactMatch(), is(false));
		assertThat(searchResult.getExactMatch(), is(nullValue()));
		assertThat(searchResult.getSearches().size()>=3, is(true));		
		// First result
		Search initialSearch = searchResult.getSearches().get(0);
		assertThat(initialSearch.getLevel(), equalTo(SearchPolicy.INITIAL_SEARCH_LEVEL));
		assertThat(initialSearch.getResponse().isRejected(), is(true));
		assertThat(initialSearch.getResponse().getResponseStatus().similarNamesCount() >= 2, is(true));
		List<String> similarNames = initialSearch.getSimilarNames();
		String east100Street = "EAST  100 STREET";
		assertThat(similarNames.contains(east100Street), is(true));
		String west100Street = "WEST  100 STREET";
		assertThat(similarNames.contains(west100Street), is(true));
		boolean east100IsFirst = similarNames.indexOf(east100Street) < similarNames.indexOf(west100Street);
		String firstSimilarName = east100IsFirst ? east100Street : west100Street;
		String secondSimilarName = east100IsFirst ? west100Street : east100Street;		
		// Second result
		Search secondSearch =  searchResult.getSearches().get(1);
		assertThat(secondSearch.getLevel(), equalTo(SearchPolicy.INITIAL_SEARCH_LEVEL + 1));
		AddressRequest secondRequest = (AddressRequest) secondSearch.getRequest();
		assertThat(secondRequest.getHouseNumber(), equalTo("314"));
		assertThat(secondRequest.getStreet(), equalTo(firstSimilarName));
		assertThat(secondRequest.getBorough(), equalTo("MANHATTAN"));
		assertThat(secondSearch.getResponseStatus().isRejected(), is(false));
		// Third result
		Search thirdSearch =  searchResult.getSearches().get(2);
		assertThat(thirdSearch.getLevel(), equalTo(SearchPolicy.INITIAL_SEARCH_LEVEL + 1));
		AddressRequest thirdRequest = (AddressRequest) thirdSearch.getRequest();
		assertThat(thirdRequest.getHouseNumber(), equalTo("314"));
		assertThat(thirdRequest.getStreet(), equalTo(secondSimilarName));
		assertThat(thirdRequest.getBorough(), equalTo("MANHATTAN"));
		assertThat(thirdSearch.getResponseStatus().isRejected(), is(false));
	}	

	@Test
	public void testFindLocationDefaultPolicy_exactPlaceMatch()
	{
		SearchResult searchResult = this.singleFieldSearchHandler.findLocation("Empire State Building, Manhattan");
		assertThat(searchResult.isExactMatch(), is(true));
		assertThat(searchResult.getExactMatch(), not(nullValue()));
		assertThat(searchResult.getSearches().size(), equalTo(1));
		Search search = searchResult.getSearches().get(0);
		assertThat(search.getLevel(), equalTo(SearchPolicy.INITIAL_SEARCH_LEVEL));
	}

	@Test
	public void testFindLocationDefaultPolicy_exactBblMatch()
	{
		SearchResult searchResult = this.singleFieldSearchHandler.findLocation("1018890001");
		assertThat(searchResult.isExactMatch(), is(true));
		assertThat(searchResult.getExactMatch(), not(nullValue()));
		assertThat(searchResult.getSearches().size(), equalTo(1));
		Search search = searchResult.getSearches().get(0);
		assertThat(search.getLevel(), equalTo(SearchPolicy.INITIAL_SEARCH_LEVEL));
	}

	@Test
	public void testFindLocationDefaultPolicy_exactBinMatch()
	{
		SearchResult searchResult = this.singleFieldSearchHandler.findLocation("1057127");
		assertThat(searchResult.isExactMatch(), is(true));
		assertThat(searchResult.getExactMatch(), not(nullValue()));
		assertThat(searchResult.getSearches().size(), equalTo(1));
		Search search = searchResult.getSearches().get(0);
		assertThat(search.getLevel(), equalTo(SearchPolicy.INITIAL_SEARCH_LEVEL));
	}
	
	@Test
	public void testFindLocationDefaultPolicy_blockfaceExactMatch()
	{
		SearchResult searchResult = this.singleFieldSearchHandler.findLocation("broadway between w 100 st and w 101 st, manhattan");
		assertThat(searchResult.isExactMatch(), is(true));
		assertThat(searchResult.getExactMatch(), not(nullValue()));
		assertThat(searchResult.getSearches().size(), equalTo(1));
		assertThat(searchResult.getExactMatch().getLevel(), equalTo(SearchPolicy.INITIAL_SEARCH_LEVEL));
	}

	@Test
	public void testFindLocationDefaultPolicy_blockfaceOnStreetValidSimilarName()
	{
		SearchResult searchResult = this.singleFieldSearchHandler.findLocation("bro between w 100 st and w 101 st, manhattan");
		assertThat(searchResult.isExactMatch(), is(false));
		assertTrue(searchResult.getSearches().size()>2);
		assertThat(searchResult.successCount(), equalTo(1));
	}
	
	@Test
	public void testFindLocationDefaultPolicy_blockfaceCrossStreetOneValidSimilarName()
	{
		SearchResult searchResult = this.singleFieldSearchHandler.findLocation("maiden ln between nassau and broadway, manhattan");
		assertThat(searchResult.isExactMatch(), is(false));
		assertTrue(searchResult.getSearches().size()>1);
		assertThat(searchResult.successCount(), equalTo(1));
	}
	
	@Test
	public void testFindLocationDefaultPolicy_blockfaceCrossStreetTwoValidSimilarName()
	{
		SearchResult searchResult = this.singleFieldSearchHandler.findLocation("maiden ln between broadway & nassau, manhattan");
		assertThat(searchResult.isExactMatch(), is(false));
		assertTrue(searchResult.getSearches().size()>1);
		assertThat(searchResult.successCount(), equalTo(1));
	}
	
	@Test
	public void testFindLocationDefaultPolicy_intersectionExactMatch()
	{
		SearchResult searchResult = this.singleFieldSearchHandler.findLocation("broadway & w 100 st nyc");
		assertThat(searchResult.isExactMatch(), is(true));
		assertThat(searchResult.getExactMatch(), not(nullValue()));
		assertThat(searchResult.getSearches().size(), equalTo(1));
		assertThat(searchResult.getExactMatch().getLevel(), equalTo(SearchPolicy.INITIAL_SEARCH_LEVEL));
	}
	
	@Test
	public void testFindLocationDefaultPolicy_intersectionWithOneValidSimilarName()
	{
		SearchResult searchResult = this.singleFieldSearchHandler.findLocation("bro & w 100 st nyc");
		assertThat(searchResult.isExactMatch(), is(false));
		assertTrue(searchResult.getSearches().size()>2);
		assertThat(searchResult.successCount(), equalTo(1));
	}
	
	@Test
	public void testFindLocationDefaultPolicy_intersectionWithCompassDirectionRequired()
	{
	    // 2015-04-22: "w 97 st & rsd manhattan" no longer requires a compass direction!
		SearchResult searchResult = this.singleFieldSearchHandler.findLocation("Cromwell Crescent and Alderton Street queens");
		assertThat(searchResult.isExactMatch(), is(false));
		assertThat(searchResult.getSearches().size(), equalTo(5));
		// Yup! All four compassDirections can be geocoded.
		assertThat(searchResult.successCount(), equalTo(4));  
	}
}
