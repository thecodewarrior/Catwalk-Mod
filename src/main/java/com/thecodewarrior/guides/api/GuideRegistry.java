package com.thecodewarrior.guides.api;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class GuideRegistry {
	
	private static HashMap<Class, GuideProvider> blocks = new HashMap<Class, GuideProvider>();
	private static HashMap<Class, GuideProvider> items  = new HashMap<Class, GuideProvider>();
	
	public static boolean registerBlockGuide(Class blockClass, String guide) {
		if(blocks.containsKey(blockClass)) {
			return false;
		} else {
			blocks.put(blockClass, new GuideProviderStatic(guide));
			return true;
		}
	}
	
	public static boolean registerItemGuide(Class itemClass, String guide) {
		if(items.containsKey(itemClass)) {
			return false;
		} else {
			items.put(itemClass, new GuideProviderStatic(guide));
			return true;
		}
	}
	
	public static boolean registerBlockGuide(Class blockClass, GuideProvider guide) {
		if(blocks.containsKey(blockClass)) {
			return false;
		} else {
			blocks.put(blockClass, guide);
			return true;
		}
	}
	
	public static boolean registerItemGuide(Class itemClass, GuideProvider guide) {
		if(items.containsKey(itemClass)) {
			return false;
		} else {
			items.put(itemClass, guide);
			return true;
		}
	}
	
	public static boolean hasBlockGuide(Class blockClass) {
		return blocks.containsKey(blockClass);
	}
	
	public static boolean hasItemGuide(Class itemClass) {
		return items.containsKey(itemClass);
	}
	
	public static String getBlockGuide(Class blockClass, ItemStack stack) {
		return blocks.get(blockClass).getBlockGuide(stack);
	}
	
	public static String getBlockGuide(Class blockClass, World w, int x, int y, int z) {
		return blocks.get(blockClass).getBlockGuide(w, x, y, z);
	}
	
	public static String getItemGuide(Class itemClass, ItemStack stack) {
		return items.get(itemClass).getItemGuide(stack);
	}
	
	private static class GuideProviderStatic extends GuideProvider{
		
		private String name;
		
		public GuideProviderStatic(String name) {
			this.name = name;
		}
		
		@Override
		public String getItemGuide(ItemStack stack) {
			return name;
		}

		@Override
		public String getBlockGuide(World w, int x, int y, int z) {
			return name;
		}
		
	}
	
}
