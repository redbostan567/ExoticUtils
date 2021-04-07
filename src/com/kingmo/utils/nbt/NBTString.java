package com.kingmo.utils.nbt;

import java.lang.reflect.Field;

import net.minecraft.server.v1_8_R3.NBTTagString;

public class NBTString extends NBTTagString{

	public NBTString(String str) throws IllegalArgumentException{
		super(str);
	}
	
	public void setString(String str) {
		
		Field field;
		try {
			field = NBTTagString.class.getDeclaredField("data");
			field.setAccessible(true);
			field.set(this, str);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public String getString() {
		return this.a_();
	}
	
	
}
