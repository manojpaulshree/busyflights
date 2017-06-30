package com.travix.medusa.busyflights;

import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusyFlightsApplicationTests {
	
	private FlightsRequestHandler flightsRequestHandler = new FlightsRequestHandler();

	@Ignore
	@Test
	public void contextLoads() {
	}
	
	/*
	 * Test user request/response for flights. 
	 * This test will work only if we receive a response 
	 * from the WebService client
	 * 
	 * */
	@Test
	public void testSearchAllRelevantsFlights() {
		
		//Static data used for Testing
		BusyFlightsRequest busyFlightsRequest = new BusyFlightsRequest();
		busyFlightsRequest.setOrigin("LHR");
		busyFlightsRequest.setDestination("AMS");
		busyFlightsRequest.setDepartureDate("2017-12-03");
		busyFlightsRequest.setReturnDate("2017-12-15");
		busyFlightsRequest.setNumberOfPassengers(4);
	    
		String actualResult = flightsRequestHandler.processRequest(busyFlightsRequest);
		
		String expectedResult = "[{\"fare\":62747.16,\"destinationAirportCode\":\"AMS\",\"supplier\":\"Tough Jet\",\"departureDate\":\"2017-12-03\",\"airline\":\"Spice Jet\",\"departureAirportCode\":\"LHR\",\"arrivalDate\":\"2017-12-15\"},{\"fare\":64016.32,\"destinationAirportCode\":\"AMS\",\"supplier\":\"Tough Jet\",\"departureDate\":\"2017-12-03\",\"airline\":\"Malasia Airlines\",\"departureAirportCode\":\"LHR\",\"arrivalDate\":\"2017-12-15\"},{\"fare\":65330,\"destinationAirportCode\":\"AMS\",\"supplier\":\"Crazy Air\",\"departureDate\":\"2017-12-03\",\"airline\":\"Air Deccan\",\"departureAirportCode\":\"LHR\",\"arrivalDate\":\"2017-12-15\"},{\"fare\":65334,\"destinationAirportCode\":\"AMS\",\"supplier\":\"Crazy Air\",\"departureDate\":\"2017-12-03\",\"airline\":\"British Airways\",\"departureAirportCode\":\"LHR\",\"arrivalDate\":\"2017-12-15\"},{\"fare\":84319.35,\"destinationAirportCode\":\"AMS\",\"supplier\":\"Tough Jet\",\"departureDate\":\"2017-12-03\",\"airline\":\"Jet Airways\",\"departureAirportCode\":\"LHR\",\"arrivalDate\":\"2017-12-15\"},{\"fare\":87655,\"destinationAirportCode\":\"AMS\",\"supplier\":\"Crazy Air\",\"departureDate\":\"2017-12-03\",\"airline\":\"Air India\",\"departureAirportCode\":\"LHR\",\"arrivalDate\":\"2017-12-15\"}]";
		
		assertEquals(expectedResult,actualResult);
   }
}
