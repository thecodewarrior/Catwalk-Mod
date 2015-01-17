package com.thecodewarrior.catwalk;

import com.thecodewarrior.notmine.buildcraft.api.tools.IToolWrench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ItemBlowtorch extends Item implements IToolWrench {

	public ItemBlowtorch() {
		setCreativeTab(Reference.catTab);
		setTextureName(Reference.MODID + ":blowtorch");
		setUnlocalizedName("blowtorch");
		setMaxStackSize(1);
	}

	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
		// TODO Auto-generated method stub
		
	}
	
	/**
    *
    * Should this item, when held, allow sneak-clicks to pass through to the underlying block?
    *
    * @param world The world 
    * @param x The X Position
    * @param y The X Position
    * @param z The X Position
    * @param player The Player that is wielding the item
    * @return
    */
   public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
   {
       return true;
   }
	
}
