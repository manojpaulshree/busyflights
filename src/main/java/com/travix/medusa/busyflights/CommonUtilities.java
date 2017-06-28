package com.travix.medusa.busyflights;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
/*
 * This class is being used to provide common functionality in current requirement
 * as well as for any future amendments
 * */
public class CommonUtilities {

	public CommonUtilities() {
	}
	
	public List<CrazyAirResponse> jSonToObjectCrazyAir(String responseJSON) {
		List<CrazyAirResponse> list = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			list = mapper.readValue(responseJSON, new TypeReference<List<CrazyAirResponse>>(){});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<ToughJetResponse> jSonToObjectToughJet(String responseJSON) {
		List<ToughJetResponse> list = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			list = mapper.readValue(responseJSON, new TypeReference<List<ToughJetResponse>>(){});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/*
	 * Sorting mechanism on the base of fare will be implemented here and a sorted list
	will be returned.
	*/
	public List<?> sortOnFlightsFare(List<Object> allFlightsList) {
		SortFlightList sortFlightList=new SortFlightList();
		sortFlightList.sortList(allFlightsList);
		return allFlightsList;
	}
}
