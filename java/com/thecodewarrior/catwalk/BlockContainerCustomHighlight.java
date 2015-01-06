package com.thecodewarrior.catwalk;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.raytracer.RayTracer;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockContainerCustomHighlight extends BlockContainer {

	public BlockContainerCustomHighlight(Material p_i45386_1_) {
		super(p_i45386_1_);
		// TODO Auto-generated constructor stub
	}

	private RayTracer rayTracer = new RayTracer();

	public MovingObjectPosition playerHit(World world, EntityPlayer player, int x, int y, int z) {
		return RayTracer.retraceBlock(world, player, x, y, z);
	}
	
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 start, Vec3 end) {
		EntityPlayer player = null;
		if(world.isRemote) {
			player = CatwalkMod.proxy.getThePlayer();
		}
		return this.collisionRayTrace(world, x, y, z, start, end, player);
	}
	
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 start, Vec3 end, EntityPlayer player) {
        TileEntity rawtile = world.getTileEntity(x, y, z);
        IHighlightProvider tile = null;
        if (rawtile == null)
            return null;
        if (rawtile instanceof IHighlightProvider) {
        	tile = (IHighlightProvider) rawtile;
        } else { return null; }
        	

        List<IndexedCuboid6> cuboids = new LinkedList<IndexedCuboid6>();
        tile.addTraceableCuboids(cuboids, player);
        return rayTracer.rayTraceCuboids(new Vector3(start), new Vector3(end), cuboids, new BlockCoord(x, y, z), this);
    }
    
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onBlockHighlight(DrawBlockHighlightEvent event) {
        if (event.target.typeOfHit == MovingObjectType.BLOCK && event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ) == this)
            RayTracer.retraceBlock(event.player.worldObj, event.player, event.target.blockX, event.target.blockY, event.target.blockZ);
    }

	@Override
	public abstract TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_);
	
}
