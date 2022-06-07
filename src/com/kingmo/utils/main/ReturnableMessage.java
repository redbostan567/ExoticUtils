package com.kingmo.utils.main;

public final class ReturnableMessage<I> {
	
	private final String message;
	private final I returnable;

	public ReturnableMessage(String message, I returnable) {
		this.message = Utils.color(message);
		this.returnable = returnable;
	}
	
	public String getMessage() {
		return message;
	}

	public I getReturned() {
		return returnable;
	}
	
}
