package com.kingmo.utils.main;
/**
 * 
 * @author Kingmo100
 * Used to indicate {@link com.kingmo.utils} has been used incorrectly
 */
public class UtilityUseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 412387412L;
	
	public UtilityUseException(String errorMessage) {
		super(errorMessage);
	}

}
