package com.kingmo.utils.abstractions;

import java.util.ArrayList;
import java.util.List;

public class UtilityList<I> extends ArrayList<I> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1452345L;

	/**
	 * @param <I>  Type of List should be automatically done.
	 * @param list the List being casted.
	 * @return a UtilityList of List.
	 */
	public static <I> UtilityList<I> cast(List<I> list) {

		UtilityList<I> utilList = new UtilityList<>();

		for (I i : list) {
			utilList.add(i);
		}

		return utilList;
	}

	/**
	 *
	 * @param type   defines the type of object being added to the List
	 * @param amount The weight of the type being added to the List
	 * @return
	 */
	public UtilityList<I> weigh(I type, int amount) {
		for (int i = 0; i < amount; i++)
			this.add(type);
		return this;
	}

	/**
	 * 
	 * @param <E> type of data being held in the List
	 * @param e Data held in list
	 * @return a UtilityList of all the data given in the paramaters. 
	 */
	@SafeVarargs
	public static <E> UtilityList<E> toList(E... e) {
		UtilityList<E> list = new UtilityList<>();
		for (E e1 : e)
			list.add(e1);

		return list;
	}

}
