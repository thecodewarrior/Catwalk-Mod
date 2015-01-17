package com.thecodewarrior.guides.api;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GuideProvider {
	public String getItemGuide(ItemStack stack){
		return null;
	}
	
	public String getBlockGuide(ItemStack stack) {
		return null;
	}
	
	public String getBlockGuide(World w, int x, int y, int z) {
		return getBlockGuide(new ItemStack(w.getBlock(x, y, z)));
	}
}
