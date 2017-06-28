package com.travix.medusa.busyflights;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

class SortFlightList implements Comparator<Object> {
public int compare(Object e1, Object e2) {
	CrazyAirResponse e11=null;
	CrazyAirResponse e12=null;
	ToughJetResponse e21=null;
	ToughJetResponse e22=null;
	if(e1 instanceof CrazyAirResponse )
	  e11=(CrazyAirResponse)e1;
	else if(e1 instanceof ToughJetResponse )
	  e21=(ToughJetResponse)e1;
	if(e2 instanceof CrazyAirResponse )
		  e12=(CrazyAirResponse)e2;
	else if(e2 instanceof ToughJetResponse )
	      e22=(ToughJetResponse)e2;
	
	
    if((e11==null?e21.getBasePrice()+e21.getTax()-(e21.getBasePrice()*(e21.getDiscount()/100)):e11.getPrice()) > (e22==null?e12.getPrice():e22.getBasePrice()+e22.getTax()-(e22.getBasePrice()*(e22.getDiscount()/100)))){
        return 1;

    } else {
        return -1;
    }
}
	public void sortList(List list){
		Collections.sort(list,new SortFlightList());
	}
}