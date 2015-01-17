package com.thecodewarrior.catwalk;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import com.thecodewarrior.notmine.buildcraft.api.tools.IToolWrench;

public class Reference {
	public static final String MODID = "catwalkmod";
    public static final String VERSION = "1.0.1";
    
    public static final int lightRopeBrightness = 15;
    
    public static EntityPlayer player;
    
    public static final Material mat = new MaterialCatwalk(MapColor.blackColor);
    
    public static CreativeTabs catTab = new CreativeTabs("cattab") {
    	public Item getTabIconItem() {
    		return Item.getItemFromBlock(CatwalkMod.catwalk);
    	}
    };
    
    public static boolean isHoldingUsableWrench(EntityPlayer Eplayer)
    {
      Item equipped = Eplayer.inventory.getCurrentItem() != null ? Eplayer.inventory.getCurrentItem().getItem() : null;
      return equipped instanceof IToolWrench;
    }
    
}
