package com.thecodewarrior.guides.api;

import net.minecraft.item.ItemStack;

public interface IItemGuideProvider {
	public String getGuideName(ItemStack stack);
}
