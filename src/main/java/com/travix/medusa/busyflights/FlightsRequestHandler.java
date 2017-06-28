/**
 * 
 */
package com.travix.medusa.busyflights;

import java.util.ArrayList;
import java.util.List;

import com.services.client.FlightsInformationClient;
import com.services.client.FlightsInformationClientImpl;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

/*
 * 
 * A class for handling request, initiate WebService Client calling and process the same
 * 
 * */
public class FlightsRequestHandler {

	/*
	 * Entry point for processing request
	 * */
	public void processRequest(BusyFlightsRequest busyFlightsRequest) {
		if(busyFlightsRequest.getNumberOfPassengers()<=4)
			getAllFlightsInformation(busyFlightsRequest);
		else
			System.out.println("Maximum number of passengers allowed: 4");
	}
	
	@SuppressWarnings("unchecked")
	private void getAllFlightsInformation(BusyFlightsRequest busyFlightsRequest) {
		CommonUtilities commonUtilities = new CommonUtilities();
		
		CrazyAirRequest cAReq = createRequestForCrazyAir(busyFlightsRequest);
		ToughJetRequest tJReq = createRequestForToughJet(busyFlightsRequest);
		
		FlightsInformationClient flightsInformationClient = new FlightsInformationClientImpl();
		//Call to the WebService client for CrazyAir
		String crazyAirJSONResponse = flightsInformationClient.getAllFlightsFromCrazyAir(cAReq);
		
		System.out.println("JSON Response from CrazyAir WS Client");
	    System.out.println(crazyAirJSONResponse);
	      
		//JSON response being converted into List
		List<CrazyAirResponse> crazyAirList = (List<CrazyAirResponse>) commonUtilities.jSonToObjectCrazyAir(crazyAirJSONResponse);
		
		//Call to the WebService client for ToughJet
		String toughJetJSONResponse = flightsInformationClient.getAllFlightsFromToughJet(tJReq);
		
		System.out.println("JSON Response from ToughJet WS Client");
	    System.out.println(toughJetJSONResponse);
	    
		//JSON response being converted into List
		List<ToughJetResponse> toughJetList = (List<ToughJetResponse>) commonUtilities.jSonToObjectToughJet(toughJetJSONResponse);
		
		List<Object> allFlightsList = new ArrayList<Object>();
		
		for (CrazyAirResponse object : crazyAirList) {
			System.out.println("object.getAirline(): "+object.getAirline());
			System.out.println("object.getArrivalDate(): "+object.getArrivalDate());
			System.out.println("object.getCabinclass(): "+object.getCabinclass());
			System.out.println("object.getDepartureAirportCode(): "+object.getDepartureAirportCode());			
			System.out.println("object.getDestinationAirportCode(): "+object.getDestinationAirportCode());
			System.out.println("object.getPrice(): "+object.getPrice());
			
			allFlightsList.add(object);
		}
		
		for (ToughJetResponse object : toughJetList) {
			System.out.println("object.getArrivalAirportName(): "+object.getArrivalAirportName());
			System.out.println("object.getBasePrice(): "+object.getBasePrice());
			System.out.println("object.getCarrier(): "+object.getCarrier());
			System.out.println("object.getDepartureAirportName(): "+object.getDepartureAirportName());
			System.out.println("object.getDiscount(): "+object.getDiscount());
			System.out.println("object.getInboundDateTime(): "+object.getInboundDateTime());
			System.out.println("object.getOutboundDateTime(): "+object.getOutboundDateTime());
			System.out.println("object.getTax(): "+object.getTax());
			
			allFlightsList.add(object);
		}
		
		//to be implemented
		System.out.println("Before Sorting >>>>>>");
		for(Object obj:allFlightsList)
			System.out.println(obj);
		allFlightsList = (List<Object>) commonUtilities.sortOnFlightsFare(allFlightsList);
		System.out.println("After Sorting >>>>>>");
		for(Object obj:allFlightsList)
			System.out.println(obj);
	
		allFlightsList = createBusyFlightsResponse(allFlightsList);
		System.out.println(allFlightsList);
		
		
	}

	/*
	 * finally the response object to be shared
	 * */
	private List<Object> createBusyFlightsResponse(List<Object> allFlightsList) {
		BusyFlightsResponse busyFlightsResponse = null;
		List<Object> responseList = new ArrayList<Object>();
		for (Object object : allFlightsList) {
			if (object instanceof CrazyAirResponse){
				busyFlightsResponse = new BusyFlightsResponse();
				busyFlightsResponse.setAirline(((CrazyAirResponse) object).getAirline());
				busyFlightsResponse.setSupplier(((CrazyAirResponse) object).getAirline());
				busyFlightsResponse.setFare(((CrazyAirResponse) object).getPrice());
				busyFlightsResponse.setDepartureAirportCode(((CrazyAirResponse) object).getDepartureAirportCode());
				busyFlightsResponse.setDestinationAirportCode(((CrazyAirResponse) object).getDestinationAirportCode());
				busyFlightsResponse.setDepartureDate(((CrazyAirResponse) object).getDepartureDate());
				busyFlightsResponse.setArrivalDate(((CrazyAirResponse) object).getArrivalDate());
				
				responseList.add(busyFlightsResponse);
				
			}else if(object instanceof ToughJetResponse){
				busyFlightsResponse = new BusyFlightsResponse();
				busyFlightsResponse.setAirline(((ToughJetResponse) object).getCarrier());
				busyFlightsResponse.setSupplier(((ToughJetResponse) object).getCarrier());
				busyFlightsResponse.setFare((((ToughJetResponse) object).getBasePrice() + ((ToughJetResponse) object).getTax()) - 
						(((ToughJetResponse) object).getDiscount()/100)* (((ToughJetResponse) object).getBasePrice()));
				busyFlightsResponse.setDepartureAirportCode(((ToughJetResponse) object).getDepartureAirportName());
				busyFlightsResponse.setDestinationAirportCode(((ToughJetResponse) object).getArrivalAirportName());
				busyFlightsResponse.setDepartureDate(((ToughJetResponse) object).getOutboundDateTime());
				busyFlightsResponse.setArrivalDate(((ToughJetResponse) object).getInboundDateTime());
				
				responseList.add(busyFlightsResponse);
			}
		}
		return responseList;
	}

	/*
	 * Relevant request object for WebService call
	 * */
	private CrazyAirRequest createRequestForCrazyAir(BusyFlightsRequest busyFlightsRequest) {
		CrazyAirRequest cAReq = new CrazyAirRequest();
		
		cAReq.setOrigin(busyFlightsRequest.getOrigin());
		cAReq.setDestination(busyFlightsRequest.getDestination());
		cAReq.setDepartureDate(busyFlightsRequest.getDepartureDate());
		cAReq.setReturnDate(busyFlightsRequest.getReturnDate());
		cAReq.setPassengerCount(busyFlightsRequest.getNumberOfPassengers());
		
		return cAReq;
	}
	
	/*
	 * Relevant request object for WebService call
	 * */
	private ToughJetRequest createRequestForToughJet(BusyFlightsRequest busyFlightsRequest) {
		ToughJetRequest tJReq = new ToughJetRequest();
		tJReq.setFrom(busyFlightsRequest.getOrigin());
		tJReq.setTo(busyFlightsRequest.getDestination());
		tJReq.setOutboundDate(busyFlightsRequest.getDepartureDate());
		tJReq.setInboundDate(busyFlightsRequest.getReturnDate());
		tJReq.setNumberOfAdults(busyFlightsRequest.getNumberOfPassengers());
		
		return tJReq;
	}
}
