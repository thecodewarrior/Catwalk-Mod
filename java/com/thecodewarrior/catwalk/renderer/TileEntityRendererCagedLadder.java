package com.thecodewarrior.catwalk.renderer;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.thecodewarrior.catwalk.CatwalkMod;
import com.thecodewarrior.catwalk.Reference;
import com.thecodewarrior.catwalk.TileEntityCagedLadder;
import com.thecodewarrior.catwalk.TileEntityCatwalk;

public class TileEntityRendererCagedLadder extends TileEntitySpecialRenderer {

	private final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/model/ladder.png");
	
	private final int texWidth  = 64;
	private final int texHeight = 64;
	
	private final double pxW = 1.0/texWidth;
	private final double pxH = 1.0/texHeight;
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		// TODO Auto-generated method stub
		TileEntityCagedLadder tile = (TileEntityCagedLadder)tileentity;
		//System.out.println("N: " + tile.north + " S: " + tile.south + " E: " + tile.east + " W: " + tile.west + " D: " + tile.down);
	    Tessellator tessellator = Tessellator.instance;
	    this.bindTexture(texture);

	    tessellator.setNormal(0.0F, 1.0F, 0.0F);
	    // Using glPushMatrix before doing our rendering, and then using glPopMatrix at the end means any "transformation"
	    // that we do (glTranslated, glRotated, et c.) does not screw up rendering in an unrelated part of the game.
    	double h = 0.005;
    	
    	int direction = tile.getDirection();
    	
	    GL11.glPushMatrix();
	    	GL11.glEnable(GL11.GL_ALPHA_TEST);
		    GL11.glTranslated(x, y, z); // This is necessary to make our rendering happen in the right place.
		    
		    GL11.glTranslated(0.5, 0.5, 0.5); 
		    float rot = 0;
		    switch(direction) {
		    case 0: rot = 180; break;
		    case 1: rot =  90; break;
		    case 2: rot =   0; break;
		    case 3: rot = 270; break;
		    }
		    GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F); // rotate block
		    GL11.glTranslated(-0.5, -0.5, -0.5);
		    tessellator.startDrawingQuads();
		    
		    
		    boolean isNorthOpen = false,
		    		isSouthOpen = false,
		    		isEastOpen  = false,
		    		isWestOpen  = false;
		    
		    switch(direction) {
		    case 0:
		    	isSouthOpen = tile.isNorthOpen();
		    	isNorthOpen = tile.isSouthOpen();
		    	isEastOpen  = tile.isWestOpen();
		    	isWestOpen  = tile.isEastOpen();
		    	break;
		    case 1:
		    	isSouthOpen = tile.isEastOpen();
		    	isNorthOpen = tile.isWestOpen();
		    	isEastOpen  = tile.isNorthOpen();
		    	isWestOpen  = tile.isSouthOpen();
		    	break;
		    case 2:
		    	isSouthOpen = tile.isSouthOpen();
		    	isNorthOpen = tile.isNorthOpen();
		    	isEastOpen  = tile.isEastOpen();
		    	isWestOpen  = tile.isWestOpen();
		    	break;
		    case 3:
		    	isSouthOpen = tile.isWestOpen();
		    	isNorthOpen = tile.isEastOpen();
		    	isEastOpen  = tile.isSouthOpen();
		    	isWestOpen  = tile.isNorthOpen();
		    	break;
		    }
		    
		    double outset = 0.0625;
		    
		    double lo = 0; // light offset
		    
		    if(tile.hasRopeLight()) {
		    	lo = 16*pxH;
		    }
		    
		    double bottomMinU = 0;
		    double bottomMinV = 32*pxH;
		    double bottomMaxU = 16*pxW;
		    double bottomMaxV = 48*pxH;
		    
		    if(tile.isOnBottom()) {
		    	tessellator.addVertexWithUV(0, 0, 0, bottomMaxU, bottomMaxV);
			    tessellator.addVertexWithUV(0, 0, 1, bottomMaxU, bottomMinV);
			    tessellator.addVertexWithUV(1, 0, 1, bottomMinU, bottomMinV);
			    tessellator.addVertexWithUV(1, 0, 0, bottomMinU, bottomMaxV);

			    tessellator.addVertexWithUV(1, 0, 0, bottomMinU, bottomMaxV);
			    tessellator.addVertexWithUV(1, 0, 1, bottomMinU, bottomMinV);
			    tessellator.addVertexWithUV(0, 0, 1, bottomMaxU, bottomMinV);
			    tessellator.addVertexWithUV(0, 0, 0, bottomMaxU, bottomMaxV);
		    }
		    
		    double backMinU =  0*pxW;
		    double backMinV =  0*pxH + lo;
		    double backMaxU = 16*pxW;
		    double backMaxV = 16*pxH + lo;
		    
		    if(!isNorthOpen) { // north
			    tessellator.addVertexWithUV(0, 0, outset, backMaxU, backMaxV);
			    tessellator.addVertexWithUV(0, 1, outset, backMaxU, backMinV);
			    tessellator.addVertexWithUV(1, 1, outset, backMinU, backMinV);
			    tessellator.addVertexWithUV(1, 0, outset, backMinU, backMaxV);
			    
			    tessellator.addVertexWithUV(1, 0, outset, backMinU, backMaxV);
			    tessellator.addVertexWithUV(1, 1, outset, backMinU, backMinV);
			    tessellator.addVertexWithUV(0, 1, outset, backMaxU, backMinV);
			    tessellator.addVertexWithUV(0, 0, outset, backMaxU, backMaxV);
		    }
		    
		    double frontMinU = 32*pxW;
		    double frontMinV =  0*pxH + lo;
		    double frontMaxU = 48*pxW;
		    double frontMaxV = 16*pxH + lo;
		    
		    if(!isSouthOpen) { // south
		    	tessellator.addVertexWithUV(1, 0, 1-outset, frontMaxU, frontMaxV);
			    tessellator.addVertexWithUV(1, 1, 1-outset, frontMaxU, frontMinV);
			    tessellator.addVertexWithUV(0, 1, 1-outset, frontMinU, frontMinV);
			    tessellator.addVertexWithUV(0, 0, 1-outset, frontMinU, frontMaxV);
			    
			    tessellator.addVertexWithUV(0, 0, 1-outset, frontMinU, frontMaxV);
			    tessellator.addVertexWithUV(0, 1, 1-outset, frontMinU, frontMinV);
			    tessellator.addVertexWithUV(1, 1, 1-outset, frontMaxU, frontMinV);
			    tessellator.addVertexWithUV(1, 0, 1-outset, frontMaxU, frontMaxV);
		    }
		    
		    double sideMinU = 16*pxW;
		    double sideMinV =  0*pxH + lo;
		    double sideMaxU = 32*pxW;
		    double sideMaxV = 16*pxH + lo;
		    
		    if(!isEastOpen) { // east
		    	tessellator.addVertexWithUV(1-outset, 0, 0, sideMaxU, sideMaxV);
			    tessellator.addVertexWithUV(1-outset, 1, 0, sideMaxU, sideMinV);
			    tessellator.addVertexWithUV(1-outset, 1, 1, sideMinU, sideMinV);
			    tessellator.addVertexWithUV(1-outset, 0, 1, sideMinU, sideMaxV);
			    
			    tessellator.addVertexWithUV(1-outset, 0, 1, sideMinU, sideMaxV);
			    tessellator.addVertexWithUV(1-outset, 1, 1, sideMinU, sideMinV);
			    tessellator.addVertexWithUV(1-outset, 1, 0, sideMaxU, sideMinV);
			    tessellator.addVertexWithUV(1-outset, 0, 0, sideMaxU, sideMaxV);
		    }
		    if(!isWestOpen) { // west
		    	tessellator.addVertexWithUV(outset, 0, 0, sideMaxU, sideMaxV);
			    tessellator.addVertexWithUV(outset, 1, 0, sideMaxU, sideMinV);
			    tessellator.addVertexWithUV(outset, 1, 1, sideMinU, sideMinV);
			    tessellator.addVertexWithUV(outset, 0, 1, sideMinU, sideMaxV);
			    
			    tessellator.addVertexWithUV(outset, 0, 1, sideMinU, sideMaxV);
			    tessellator.addVertexWithUV(outset, 1, 1, sideMinU, sideMinV);
			    tessellator.addVertexWithUV(outset, 1, 0, sideMaxU, sideMinV);
			    tessellator.addVertexWithUV(outset, 0, 0, sideMaxU, sideMaxV);
		    }
		    tessellator.draw();
	    GL11.glPopMatrix();
	}

}
/*
GL11.glTranslated(x+0.5, y+0.5, z+0.5); // This is necessary to make our rendering happen in the right place.
		    GL11.glRotatef(direction * 90, 0.0F, 1.0F, 0.0F);
		    GL11.glTranslated(-0.5, -0.5, -0.5);
*/