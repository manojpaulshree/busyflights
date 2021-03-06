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
import com.travix.medusa.busyflights.supplier.Suppliers;

/*
 * 
 * A class for handling request, initiate WebService Client calling and process the same
 * 
 * */
public class FlightsRequestHandler {
	private List<BusyFlightsResponse> responseList = new ArrayList<BusyFlightsResponse>();
	private CommonUtilities commonUtilities = new CommonUtilities();
	
	/*
	 * Entry point for processing request
	 * */
	public String processRequest(BusyFlightsRequest busyFlightsRequest) {
		String jSON = "";
		if(busyFlightsRequest.getNumberOfPassengers()<=4 && busyFlightsRequest.getNumberOfPassengers()>0){
			getAllFlightsInformation(busyFlightsRequest);
			jSON = commonUtilities.converToJSON(responseList);
		}
		else
			System.out.println("Number of passengers should be 1 to 4");
		return jSON;
	}
	/*
	 * Transform the request, fetch the data and transform the response as per the requirement.
	 * */
	@SuppressWarnings("unchecked")
	private void getAllFlightsInformation(BusyFlightsRequest busyFlightsRequest) {
		
		FlightsInformationClient flightsInformationClient = new FlightsInformationClientImpl();
		//Call to the WebService client for CrazyAir
		String crazyAirJSONResponse = flightsInformationClient.getAllFlightsFromCrazyAir(createRequestForCrazyAir(busyFlightsRequest));
		
		System.out.println("JSON Response from CrazyAir WS Client");
	    System.out.println(crazyAirJSONResponse);
	      
		//JSON response being converted into List
		List<CrazyAirResponse> crazyAirList = (List<CrazyAirResponse>) commonUtilities.jSonToObjectCrazyAir(crazyAirJSONResponse);
		
		//Call to the WebService client for ToughJet
		String toughJetJSONResponse = flightsInformationClient.getAllFlightsFromToughJet(createRequestForToughJet(busyFlightsRequest));
		
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
		
		System.out.println("Before Sorting >>>>>>");
		for(Object obj:allFlightsList)
			System.out.println(obj);
		allFlightsList = (List<Object>) commonUtilities.sortOnFlightsFare(allFlightsList);
		System.out.println("After Sorting >>>>>>");
		for(Object obj:allFlightsList)
			System.out.println(obj);
	
		createBusyFlightsResponse(allFlightsList);
		System.out.println(responseList);
	}

	/*
	 * finally the response object to be shared will be wrapped in this method
	 * returning the list of BusyFlightsResponse object(s)
	 * */
	private void createBusyFlightsResponse(List<Object> allFlightsList) {
		BusyFlightsResponse busyFlightsResponse = null;
		for (Object object : allFlightsList) {
			if (object instanceof CrazyAirResponse){
				busyFlightsResponse = new BusyFlightsResponse();
				busyFlightsResponse.setAirline(((CrazyAirResponse) object).getAirline());
				busyFlightsResponse.setSupplier(Suppliers.SUPPLIER_CRAZY_AIR);
				busyFlightsResponse.setFare(((CrazyAirResponse) object).getPrice());
				busyFlightsResponse.setDepartureAirportCode(((CrazyAirResponse) object).getDepartureAirportCode());
				busyFlightsResponse.setDestinationAirportCode(((CrazyAirResponse) object).getDestinationAirportCode());
				busyFlightsResponse.setDepartureDate(((CrazyAirResponse) object).getDepartureDate());
				busyFlightsResponse.setArrivalDate(((CrazyAirResponse) object).getArrivalDate());
				
				responseList.add(busyFlightsResponse);
				
			}else if(object instanceof ToughJetResponse){
				busyFlightsResponse = new BusyFlightsResponse();
				busyFlightsResponse.setAirline(((ToughJetResponse) object).getCarrier());
				busyFlightsResponse.setSupplier(Suppliers.SUPPLIER_TOUGH_JET);
				busyFlightsResponse.setFare((((ToughJetResponse) object).getBasePrice() + ((ToughJetResponse) object).getTax()) - 
						(((ToughJetResponse) object).getDiscount()/100)* (((ToughJetResponse) object).getBasePrice()));
				busyFlightsResponse.setDepartureAirportCode(((ToughJetResponse) object).getDepartureAirportName());
				busyFlightsResponse.setDestinationAirportCode(((ToughJetResponse) object).getArrivalAirportName());
				busyFlightsResponse.setDepartureDate(((ToughJetResponse) object).getOutboundDateTime());
				busyFlightsResponse.setArrivalDate(((ToughJetResponse) object).getInboundDateTime());
				
				responseList.add(busyFlightsResponse);
			}
		}
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
