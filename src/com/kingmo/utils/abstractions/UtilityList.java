package com.kingmo.utils.abstractions;

import java.util.ArrayList;
import java.util.List;

public class UtilityList<I> extends ArrayList<I> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1452345L;

	
	public static <I> UtilityList<I> cast(List<I> list) {
		
		UtilityList<I> utilList = new UtilityList<>();
		
		for(I i : list) {
			utilList.add(i);
		}
		
		return utilList;
	}
	
	public UtilityList<I> weigh(I type, int amount) {
		for(int i = 0; i < amount; i++)this.add(type);
		return this;	
	}
	
	@SafeVarargs
	public static <E> UtilityList<E> toList(E... e){
		UtilityList<E> list = new UtilityList<>();
		for(E e1: e)list.add(e1);
		
		return list;
	}

}
