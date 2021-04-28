package com.kingmo.utils.nbt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import com.kingmo.utils.nms.NMSManager;

public class NBTTagManager {

	private Object tag;
	private Class<?> nmsItem;

	public NBTTagManager() {
		try {
			tag = createNewTag();
			nmsItem = NMSManager.getNMSClass("ItemStack");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public NBTTagManager(ItemStack is) {
		
		System.out.println(CraftItemStack.class);
		
		try {
			nmsItem = NMSManager.getNMSClass("ItemStack");
			
			Method getTag = nmsItem.getMethod("getTag");

			tag = getTag.invoke(NMSManager.asNMSCopy(is));
			
			if(tag == null) {
				tag = createNewTag();
			}
			

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ItemStack addTagToItem(ItemStack is) {

		try {
			Object itemStack = NMSManager.asNMSCopy(is);

			Method getTag = nmsItem.getMethod("getTag");

			Object tag = getTag.invoke(itemStack);

			if(tag == null) {
				tag = this.createNewTag();
			}
			
			Class<?> nbtTagCompound = NMSManager.getNMSClass("NBTTagCompound");

			Method a = nbtTagCompound.getDeclaredMethod("a", nbtTagCompound);

			System.out.println(this.getTag() + "class tag");
			
			a.invoke(tag, this.getTag());

			System.out.println(tag);
			
			Method setTag = nmsItem.getMethod("setTag", nbtTagCompound);

			setTag.invoke(itemStack, tag);

			return NMSManager.asBukkitCopy(itemStack);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Object createNewTag() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Class<?> nbtTagCompound = NMSManager.getNMSClass("NBTTagCompound");
		Object tag = nbtTagCompound.getDeclaredConstructor().newInstance();
		return tag;
	}

	public void setTag(String name, Object nbt) {

		try {
			Class<?> base = NMSManager.getNMSClass("NBTBase");

			System.out.println(tag);
			
			this.tag.getClass()
			.getMethod("set", String.class, base)
			.invoke(this.tag, name, nbt);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean containsTag(String name) {

		return tag != null ? hasKey(name) : false;
	}

	public boolean hasKey(String name) {
		try {
			Method method = tag.getClass().getDeclaredMethod("hasKey", String.class);

			return (boolean) method.invoke(tag, name);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void removeTag(String name) {

		try {
			tag.getClass().getDeclaredMethod("remove", String.class).invoke(tag, name);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Object getTag(String name) {

		Method m;
		try {
			m = tag.getClass().getDeclaredMethod("get", String.class);
			return m.invoke(m, name);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * TODO: Have to make it work w/ reflection CBA rn public Entity
	 * addTagToEntity(Entity entity) { net.minecraft.server.v1_8_R3.Entity nmsEntity
	 * = ((CraftEntity) entity).getHandle();
	 * 
	 * NBTTagCompound tag = nmsEntity.getNBTTag(); tag.a(this.tag);
	 * nmsEntity.f(tag);
	 * 
	 * return CraftEntity.getEntity((CraftServer) Bukkit.getServer(), nmsEntity); }
	 */

	public String getStringTag(String string) {

		try {

			Method getString = tag.getClass().getDeclaredMethod("getString", String.class);

			return (String) getString.invoke(tag, string);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	public void update(ItemStack is) {

		try {

			Class<?> itemStack = NMSManager.getCBClass("CraftItemStack", "inventory");
			Method getTag = itemStack.getDeclaredMethod("getTag");

			tag = getTag.invoke(null, NMSManager.asNMSCopy(is));
		} catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | SecurityException e) {

			e.printStackTrace();
		}

	}

	public static Map<String, String> castTo(Map<String, Object> tags) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getTag() {
		return this.tag;
	}

}
