package com.thecodewarrior.catwalk.renderer;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.thecodewarrior.catwalk.CatwalkMod;
import com.thecodewarrior.catwalk.Reference;
import com.thecodewarrior.catwalk.TileEntityCatwalk;

public class TileEntityRendererCatwalk extends TileEntitySpecialRenderer {

	private final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/model/scaffold.png");
	
	private final int texWidth  = 64;
	private final int texHeight = 64;
	
	private final double pxW = 1.0/texWidth;
	private final double pxH = 1.0/texHeight;
	
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		TileEntityCatwalk tile = (TileEntityCatwalk)tileentity;
		//System.out.println("N: " + tile.north + " S: " + tile.south + " E: " + tile.east + " W: " + tile.west + " D: " + tile.down);
	    Tessellator tessellator = Tessellator.instance;
	    this.bindTexture(texture);
	    tessellator.setNormal(0.0F, 1.0F, 0.0F);

	    // Using glPushMatrix before doing our rendering, and then using glPopMatrix at the end means any "transformation"
	    // that we do (glTranslated, glRotated, et c.) does not screw up rendering in an unrelated part of the game.
    	double h = 0.005;

	    GL11.glPushMatrix();
	    	GL11.glEnable(GL11.GL_ALPHA_TEST);
		    GL11.glTranslated(x, y, z); // This is necessary to make our rendering happen in the right place.
		    tessellator.startDrawingQuads();
		    	{

		    		if(!tile.isDownOpen()){ // FLOOR

				    	tessellator.addVertexWithUV(0, 0, 0, 16*pxW, 32*pxH);
					    tessellator.addVertexWithUV(0, 0, 1, 16*pxW, 16*pxH);
					    tessellator.addVertexWithUV(1, 0, 1, 0, 16*pxH);
					    tessellator.addVertexWithUV(1, 0, 0, 0, 32*pxH);
	
					    
					    tessellator.addVertexWithUV(1, 0, 0, 0, 32*pxH);
					    tessellator.addVertexWithUV(1, 0, 1, 0, 16*pxH);
					    tessellator.addVertexWithUV(0, 0, 1, 16*pxW, 16*pxH);
					    tessellator.addVertexWithUV(0, 0, 0, 16*pxW, 32*pxH);
					    
					}
		    		
			    	if(!tile.isNorthOpen()){ // NORTH

					    tessellator.addVertexWithUV(0, 0, 0, 16*pxW, 16*pxH);
					    tessellator.addVertexWithUV(0, 1, 0, 16*pxW, 0);
					    tessellator.addVertexWithUV(1, 1, 0, 0, 0);
					    tessellator.addVertexWithUV(1, 0, 0, 0, 16*pxH);
					    

					    tessellator.addVertexWithUV(1, 0, 0, 0, 16*pxH);
					    tessellator.addVertexWithUV(1, 1, 0, 0, 0);
					    tessellator.addVertexWithUV(0, 1, 0, 16*pxW, 0);
					    tessellator.addVertexWithUV(0, 0, 0, 16*pxW, 16*pxH);
					}
			    	
			    	if(!tile.isSouthOpen()){ // SOUTH

					    tessellator.addVertexWithUV(1, 0, 1, 16*pxW, 16*pxH);
					    tessellator.addVertexWithUV(1, 1, 1, 16*pxW, 0);
					    tessellator.addVertexWithUV(0, 1, 1, 0, 0);
					    tessellator.addVertexWithUV(0, 0, 1, 0, 16*pxH);
					    

					    tessellator.addVertexWithUV(0, 0, 1, 0, 16*pxH);
					    tessellator.addVertexWithUV(0, 1, 1, 0, 0);
					    tessellator.addVertexWithUV(1, 1, 1, 16*pxW, 0);
					    tessellator.addVertexWithUV(1, 0, 1, 16*pxW, 16*pxH);
					}
			    	
			    	if(!tile.isEastOpen()){ // EAST
			    		
					    tessellator.addVertexWithUV(1, 0, 0, 16*pxW, 16*pxH);
					    tessellator.addVertexWithUV(1, 1, 0, 16*pxW, 0);
					    tessellator.addVertexWithUV(1, 1, 1, 0, 0);
					    tessellator.addVertexWithUV(1, 0, 1, 0, 16*pxH);
					    

					    tessellator.addVertexWithUV(1, 0, 1, 0, 16*pxH);
					    tessellator.addVertexWithUV(1, 1, 1, 0, 0);
					    tessellator.addVertexWithUV(1, 1, 0, 16*pxW, 0);
					    tessellator.addVertexWithUV(1, 0, 0, 16*pxW, 16*pxH);
					}
			    	
			    	if(!tile.isWestOpen()){ // WEST

					    tessellator.addVertexWithUV(0, 0, 0, 16*pxW, 16*pxH);
					    tessellator.addVertexWithUV(0, 1, 0, 16*pxW, 0);
					    tessellator.addVertexWithUV(0, 1, 1, 0, 0);
					    tessellator.addVertexWithUV(0, 0, 1, 0, 16*pxH);
					    

					    tessellator.addVertexWithUV(0, 0, 1, 0, 16*pxH);
					    tessellator.addVertexWithUV(0, 1, 1, 0, 0);
					    tessellator.addVertexWithUV(0, 1, 0, 16*pxW, 0);
					    tessellator.addVertexWithUV(0, 0, 0, 16*pxW, 16*pxH);
					}
		    	}
		    	
		    	if(tile.hasRopeLight()) {
		    		tessellator.setBrightness(200);
		    		//tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
//		            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)l1, (float)240);  
		            h = 0;
		    		if(!tile.isNorthOpen()){ // NORTH
					    tessellator.addVertexWithUV(0, 0+h, h, 32*pxW, 16*pxH);
					    tessellator.addVertexWithUV(0, 1, h, 32*pxW, 0);
					    tessellator.addVertexWithUV(1, 1, h, 16*pxW, 0);
					    tessellator.addVertexWithUV(1, 0+h, h, 16*pxW, 16*pxH);
					    
					    tessellator.addVertexWithUV(1, 0+h, h, 16*pxW, 16*pxH);
					    tessellator.addVertexWithUV(1, 1, h, 16*pxW, 0);
					    tessellator.addVertexWithUV(0, 1, h, 32*pxW, 0);
					    tessellator.addVertexWithUV(0, 0+h, h, 32*pxW, 16*pxH);
					}
			    	
			    	if(!tile.isSouthOpen()){ // SOUTH
					    tessellator.addVertexWithUV(1, 0+h, 1-h, 32*pxW, 16*pxH);
					    tessellator.addVertexWithUV(1, 1, 1-h, 32*pxW, 0);
					    tessellator.addVertexWithUV(0, 1, 1-h, 16*pxW, 0);
					    tessellator.addVertexWithUV(0, 0+h, 1-h, 16*pxW, 16*pxH);
					    
					    tessellator.addVertexWithUV(0, 0+h, 1-h, 16*pxW, 16*pxH);
					    tessellator.addVertexWithUV(0, 1, 1-h, 16*pxW, 0);
					    tessellator.addVertexWithUV(1, 1, 1-h, 32*pxW, 0);
					    tessellator.addVertexWithUV(1, 0+h, 1-h, 32*pxW, 16*pxH);
					}
			    	
			    	if(!tile.isEastOpen()){ // EAST
					    tessellator.addVertexWithUV(1-h, 0+h, 0, 32*pxW, 16*pxH);
					    tessellator.addVertexWithUV(1-h, 1, 0, 32*pxW, 0);
					    tessellator.addVertexWithUV(1-h, 1, 1, 16*pxW, 0);
					    tessellator.addVertexWithUV(1-h, 0+h, 1, 16*pxW, 16*pxH);
					    
					    tessellator.addVertexWithUV(1-h, 0+h, 1, 16*pxW, 16*pxH);
					    tessellator.addVertexWithUV(1-h, 1, 1, 16*pxW, 0);
					    tessellator.addVertexWithUV(1-h, 1, 0, 32*pxW, 0);
					    tessellator.addVertexWithUV(1-h, 0+h, 0, 32*pxW, 16*pxH);
					}
			    	
			    	if(!tile.isWestOpen()){ // WEST
					    tessellator.addVertexWithUV(h, 0+h, 0, 32*pxW, 16*pxH);
					    tessellator.addVertexWithUV(h, 1, 0, 32*pxW, 0);
					    tessellator.addVertexWithUV(h, 1, 1, 16*pxW, 0);
					    tessellator.addVertexWithUV(h, 0+h, 1, 16*pxW, 16*pxH);
					    
					    tessellator.addVertexWithUV(h, 0+h, 1, 16*pxW, 16*pxH);
					    tessellator.addVertexWithUV(h, 1, 1, 16*pxW, 0);
					    tessellator.addVertexWithUV(h, 1, 0, 32*pxW, 0);
					    tessellator.addVertexWithUV(h, 0+h, 0, 32*pxW, 16*pxH);
					}
		    	}
		    
		    tessellator.draw();
	    GL11.glPopMatrix();
	}

}
