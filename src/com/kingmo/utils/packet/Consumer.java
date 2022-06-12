package com.kingmo.utils.packet;

@FunctionalInterface
public interface Consumer <I> {

	public String consume(I i);
	
}
