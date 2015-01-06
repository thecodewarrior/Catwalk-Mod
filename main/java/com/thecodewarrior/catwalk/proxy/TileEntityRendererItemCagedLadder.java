package com.thecodewarrior.catwalk.proxy;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class TileEntityRendererItemCagedLadder implements IItemRenderer {
	TileEntitySpecialRenderer render;
	
	private TileEntity entity;
	
	public TileEntityRendererItemCagedLadder(TileEntitySpecialRenderer render, TileEntity entity) {
		this.entity = entity;
		this.render = render;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if(type == IItemRenderer.ItemRenderType.ENTITY)
			GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
		if(type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.EQUIPPED) {
			GL11.glTranslated(0.5, 0.5, 0.5);
			if(type == IItemRenderer.ItemRenderType.EQUIPPED) {
				GL11.glRotatef( -90, 0.0F, 1.0F, 0.0F); // rotate block
			} else if(type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
				GL11.glRotatef(180, 0.0F, 1.0F, 0.0F); // rotate block
			}
	    	GL11.glTranslated(-0.5, -0.5, -0.5);
		}
		this.render.renderTileEntityAt(this.entity, 0.0D, 0.0D, 0.0D, 0.0F);
	}

}
