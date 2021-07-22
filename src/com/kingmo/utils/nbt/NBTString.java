package com.kingmo.utils.nbt;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.kingmo.utils.nms.NMSManager;

public class NBTString extends NBTBase<String>{
	
	public NBTString(String str) throws IllegalArgumentException, ClassNotFoundException{
		super(str, NMSManager.getNMSClass("NBTTagString"));
	}

	public void setString(String str) {

		Field field;
		try {
			field = NMSManager.getNMSClass("NBTTagString").getDeclaredField("data");
			field.setAccessible(true);
			field.set(this, str);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> serialize() {
		return super.serializable();
	}

	@Override
	public Object getAsNBT() {
		
		
		try {
			System.out.println(version);
		switch(NBTString.version.replace(".", "").replace("v", "")) {
		
		case "1_16_R3":
			return this.getNbtTagClass().getDeclaredMethod("a", String.class).invoke(null, this.getData());
		
		case "1_8_R3":
			return this.getNbtTagClass().getDeclaredConstructor(String.class).newInstance(this.getData());

		}
		
		}catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | InstantiationException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

}
