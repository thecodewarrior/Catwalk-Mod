package com.thecodewarrior.guides.api;

import net.minecraft.item.ItemStack;

public interface IBlockGuideProvider {
	public String getItemGuideName(ItemStack stack);
}
