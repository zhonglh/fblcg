package com.fullbloom.framework.util;

import java.util.Comparator;

import com.fullbloom.framework.core.dbutil.SortDirection;

public class StringSort implements Comparator<String>{
	
	private SortDirection sortOrder;
	
	public StringSort(SortDirection sortDirection){
		this.sortOrder = sortDirection;
	}

	
	public int compare(String prev, String next) {
		return sortOrder.equals(SortDirection.asc)?
				prev.compareTo(next):next.compareTo(prev);
	}
	
}