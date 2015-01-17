package com.thecodewarrior.tooltips.api;

import net.minecraft.item.ItemStack;

import com.thecodewarrior.notmine.codechicken.lib.vec.Cuboid6;

public class Tooltip implements ITooltip{

	private String name;
	private String modid;
	private ItemStack icon;
	private Cuboid6 boundingBox;
	
	public Tooltip(String modid, String name, ItemStack icon, Cuboid6 bb) {
		this.name = name;
		this.modid = modid;
		this.icon = icon;
		boundingBox = bb;
	}
	
	@Override
	public String getGuideUnlocalizedName() {
		// TODO Auto-generated method stub
		return "guide." + name;
	}

	@Override
	public ItemStack getGuideIcon() {
		// TODO Auto-generated method stub
		return icon;
	}

	@Override
	public Cuboid6 getGuideBoundingBox() {
		// TODO Auto-generated method stub
		return boundingBox;
	}

}
