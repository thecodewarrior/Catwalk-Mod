package com.thecodewarrior.catwalk;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ItemRopeLight extends Item {
	
	public ItemRopeLight() {
		setCreativeTab(Reference.catTab);
		setTextureName(Reference.MODID + ":ropeLight");
		setUnlocalizedName("ropeLight");
	}
	
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
	{
	    return true;
	}
	
}
