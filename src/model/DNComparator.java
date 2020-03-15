package model;

import java.util.Comparator;

public class DNComparator implements Comparator<User> {

	@Override
	public int compare(User u1, User u2) {
		int compare = 0;		
		
		if(Integer.parseInt(u1.getDocumentNumber()) > Integer.parseInt(u2.getDocumentNumber())) {
			compare = 1;
		}
		else if(Integer.parseInt(u1.getDocumentNumber()) < Integer.parseInt(u2.getDocumentNumber())) {
			compare = -1;
		}
		else {
			compare = 0;
		}	
		return compare;
	}

}
