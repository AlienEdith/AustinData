package info.allaboutaustin.RestfulApi.models.ZipcodeComparators;

import java.util.Comparator;

import info.allaboutaustin.RestfulApi.models.Zipcode;

public class ZipcodeTrafficComparator implements Comparator<Zipcode>{

	@Override
	public int compare(Zipcode z1, Zipcode z2) {
		// TODO Auto-generated method stub
		return z2.getTrafficScore().compareTo(z1.getTrafficScore());
	}

}
