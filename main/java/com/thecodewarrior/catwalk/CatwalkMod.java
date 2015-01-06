package com.thecodewarrior.catwalk;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Logger;

import com.thecodewarrior.catwalk.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MODID, version = Reference.VERSION)
public class CatwalkMod {
	
	@SidedProxy(clientSide="com.thecodewarrior.catwalk.proxy.ClientProxy", serverSide="com.thecodewarrior.catwalk.proxy.CommonProxy")
	public static CommonProxy proxy;
	
    public static Block catwalk;
    public static Block ladder;
    
    public static Item ropeLight;
    public static Item blowtorch;
    public static Item steelGrate;
    
    public static Logger logger;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
    	
		catwalk = new BlockCatwalk();
		RegisterHelper.registerBlock(catwalk);
		
		ladder = new BlockCagedLadder();
		RegisterHelper.registerBlock(ladder);
		
		ropeLight = new ItemRopeLight();
		RegisterHelper.registerItem(ropeLight);
		
		blowtorch = new ItemBlowtorch();
		RegisterHelper.registerItem(blowtorch);
		
		steelGrate = new ItemSteelGrate();
		RegisterHelper.registerItem(steelGrate);
		
		GameRegistry.addRecipe(new ItemStack(ropeLight, 16), new Object[]{
    	"GLG",
    	"GLG",
    	"GLG",
    	'G', Items.glowstone_dust,
    	'L', new ItemStack(Items.dye, 1, 4)
    	});
		
		GameRegistry.addRecipe(new ItemStack(steelGrate, 16), new Object[]{
			"IXI",
			"XIX",
			"IXI",
			'I', Items.iron_ingot
		});
		
		GameRegistry.addRecipe(new ItemStack(blowtorch), new Object[] {
			"FXX",
			"XIX",
			"XXI",
			'F', Items.flint_and_steel,
			'I', Items.iron_ingot
		});
		
		GameRegistry.addRecipe(new ItemStack(catwalk, 8), new Object[] {
			"GXG",
			"XGX",
			'G', steelGrate
		});
		
		GameRegistry.addRecipe(new ItemStack(ladder, 8), new Object[] {
			"XLX",
			"GLG",
			"XLX",
			'G', steelGrate,
			'L', Blocks.ladder
		});
		
		GameRegistry.registerTileEntity(TileEntityCatwalk.class, Reference.MODID + ":tileEntityScaffold");
		GameRegistry.registerTileEntity(TileEntityCagedLadder.class, Reference.MODID + ":tileEntityCagedLadder");
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	proxy.registerProxies();
    }
}
