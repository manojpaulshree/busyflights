package com.travix.medusa.busyflights;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
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
	 * Sorting mechanism on the base of fare here and a sorted list
	 * will be returned.
	 */
	public List<?> sortOnFlightsFare(List<Object> allFlightsList) {
		SortFlightList sortFlightList=new SortFlightList();
		sortFlightList.sortList(allFlightsList);
		return allFlightsList;
	}
	
	/*
	 * Converting a list to JSON and a JSON
	 * will be returned.
	 */
	public String converToJSON(List<BusyFlightsResponse> responseList) {
		JSONArray jsArray = null;
		if(responseList.size()>0){
			jsArray = new JSONArray(responseList);
			return jsArray.toString();
		}
		else
			return "";
	}
}
