package com.kingmo.utils.nbt;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.kingmo.utils.nms.NMSManager;

public abstract class NBTBase<I> implements ConfigurationSerializable {

	private I data;
	private final Class<?> nbtTag;
	protected static String version = NMSManager.getVersion();

	public NBTBase(I data, Class<?> nbtTag) {
		this.setData(data);
		this.nbtTag = nbtTag;
	}

	@SuppressWarnings("unchecked")
	public NBTBase(Map<String, Object> map) {
		data = (I) map.get("data");
		nbtTag = (Class<?>) map.get("class");
		this.deserializable();
	}

	public I getData() {
		return data;
	}

	public void setData(I data) {
		this.data = data;
	}

	public void deserializable() {

	}

	public abstract Object getAsNBT();

	public Class<?> getNbtTagClass() {
		return nbtTag;
	}

	public Map<String, Object> serializable() {
		Map<String, Object> map = new HashMap<>();
		map.put("data", data);
		map.put("class", this.getNbtTagClass());
		return map;
	}



}
