package com.travix.medusa.busyflights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;

@SpringBootApplication
public class BusyFlightsApplication {

	public static void main(String[] args) { 
		SpringApplication.run(BusyFlightsApplication.class, args); 
		
		//Static data used for Testing
		BusyFlightsRequest busyFlightsRequest = new BusyFlightsRequest();
		busyFlightsRequest.setOrigin("LHR");
		busyFlightsRequest.setDestination("AMS");
		busyFlightsRequest.setDepartureDate("2017-12-03");
		busyFlightsRequest.setReturnDate("2017-12-15");
		busyFlightsRequest.setNumberOfPassengers(4);
		
		new FlightsRequestHandler().processRequest(busyFlightsRequest);
	}
}
