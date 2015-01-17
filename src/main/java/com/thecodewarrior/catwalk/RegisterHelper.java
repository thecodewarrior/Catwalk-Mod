package com.thecodewarrior.catwalk;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class RegisterHelper {
	public static void registerBlock(Block block) {
		// Reference.MODID + "_" + 
		GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
	}

	public static void registerItem(Item item) {
		// Reference.MODID + "_" + 
		GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
	}
}
