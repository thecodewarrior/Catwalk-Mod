package com.thecodewarrior.catwalk;

import net.minecraft.item.Item;

public class ItemSteelGrate extends Item {
	public ItemSteelGrate() {
		setCreativeTab(Reference.catTab);
		setTextureName(Reference.MODID + ":steelGrate");
		setUnlocalizedName("steelGrate");
	}
}
