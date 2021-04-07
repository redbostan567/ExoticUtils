package com.kingmo.utils.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Utils {

	public static boolean random(int min, int max) {
		Random r = new Random();
		return r.nextInt(max + 1) <= min;
	}
	
	public static int randomNumber(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	@SafeVarargs
	public static <E> List<E> toList(E... e){
		List<E> list = new ArrayList<>();
		for(E e1: e)list.add(e1);
		
		return list;
	}
	
	@SafeVarargs
	public static <E> Set<E> toSet(E... e){
		
		Set<E> set = new HashSet<>();
		
		for(E e1: e)set.add(e1);
		return set;
	}
	
	public static <E> List<E> add(List<E> list, E obj){
		list.add(obj);
		return list;
	}
	
	public static <E> List<E> weigh(List<E> list, E type, int amount) {
		for(int i = 0; i < amount; i++)list.add(type);
		return list;	
	}
	
	public static Object loadData(File f) {
		Object ob;
		try {
			if (!f.exists())
				f.createNewFile();

			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			ob = ois.readObject();
			ois.close();
			System.out.println("Loaded " + f.getName());
			return ob;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static void save(File f, Object o) {
		ObjectOutputStream oos;
		try {
			if (!f.exists())
				f.createNewFile();
			oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(o);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}