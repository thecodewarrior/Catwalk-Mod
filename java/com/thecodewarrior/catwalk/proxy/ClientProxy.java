package com.thecodewarrior.catwalk.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.thecodewarrior.catwalk.CatwalkMod;
import com.thecodewarrior.catwalk.TileEntityCagedLadder;
import com.thecodewarrior.catwalk.TileEntityCatwalk;
import com.thecodewarrior.catwalk.renderer.TileEntityRendererCagedLadder;
import com.thecodewarrior.catwalk.renderer.TileEntityRendererCatwalk;
import com.thecodewarrior.catwalk.renderer.TileEntityRendererItemScaffold;

import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	public void registerProxies() {
		TileEntitySpecialRenderer catwalk_renderer = new TileEntityRendererCatwalk();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCatwalk.class, catwalk_renderer);
		
		TileEntityCatwalk catwalkItemTile = new TileEntityCatwalk();
		catwalkItemTile.northForceOpen = true;
		catwalkItemTile.southForceOpen = true;
		
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CatwalkMod.catwalk),
				new TileEntityRendererItemScaffold(catwalk_renderer, catwalkItemTile));
		
		TileEntitySpecialRenderer ladder_renderer = new TileEntityRendererCagedLadder();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCagedLadder.class, ladder_renderer);
		
		TileEntityCagedLadder ladderItemTile = new TileEntityCagedLadder();
		ladderItemTile.bottomForceOpen = true;
		ladderItemTile.directionOverride = 1;
		
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CatwalkMod.ladder),
				new TileEntityRendererItemCagedLadder(ladder_renderer, ladderItemTile));
	}
	
	public EntityPlayer getThePlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}
}
