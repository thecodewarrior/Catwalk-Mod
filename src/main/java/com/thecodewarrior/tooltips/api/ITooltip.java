package com.thecodewarrior.tooltips.api;

import net.minecraft.item.ItemStack;

import com.thecodewarrior.notmine.codechicken.lib.vec.Cuboid6;

public interface ITooltip {
	public String getGuideUnlocalizedName();
	public ItemStack getGuideIcon();
	public Cuboid6 getGuideBoundingBox();
}
