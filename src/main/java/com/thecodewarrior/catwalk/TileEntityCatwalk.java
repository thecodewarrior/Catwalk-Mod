package com.thecodewarrior.catwalk;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.thecodewarrior.notmine.codechicken.lib.raytracer.IndexedCuboid6;
import com.thecodewarrior.notmine.codechicken.lib.vec.Cuboid6;

public class TileEntityCatwalk extends TileEntity implements IHighlightProvider{
	
	public boolean north = true;
	public boolean south = true;
	public boolean east  = true;
	public boolean west  = true;
	public boolean down  = true;
	
	public boolean northForceOpen = false;
	public boolean southForceOpen = false;
	public boolean eastForceOpen  = false;
	public boolean westForceOpen  = false;
	public boolean downForceOpen  = false;
	
	public boolean ropeLight = false;
	
	public boolean isNorthOpen() {
		return !north || northForceOpen;
	}
	
	public boolean isSouthOpen() {
		return !south || southForceOpen;
	}
	
	public boolean isEastOpen() {
		return !east || eastForceOpen;
	}
	
	public boolean isWestOpen() {
		return !west || westForceOpen;
	}
	
	public boolean isDownOpen() {
		return !down || downForceOpen;
	}
	
	public boolean hasRopeLight() {
		return ropeLight;
	}
	
    public void addTraceableCuboids(List<IndexedCuboid6> cuboids, EntityPlayer player)
    {
    	boolean all = true;
    	if(player != null) {
    		all = player.isSneaking();
    	}
    	float px = 1.0F/16;
        
        float height = 1;
        
		if(!isDownOpen() || all) { // bottom
			cuboids.add(new IndexedCuboid6(0, cuboid(0, 0, 0, 1, px, 1)));
		}
		if(!isNorthOpen() || all) { // north
			cuboids.add(new IndexedCuboid6(2, cuboid(0, 0, 0, 1, height, px))); // NORTH
		}
		if(!isSouthOpen() || all) { // south
			cuboids.add(new IndexedCuboid6(3, cuboid(0, 0, 1-px, 1, height, 1))); // SOUTH
		}
		if(!isWestOpen() || all) { // west
			cuboids.add(new IndexedCuboid6(4, cuboid(0, 0, 0, px, height, 1))); // WEST
		}
		if(!isEastOpen() || all) { // east
			cuboids.add(new IndexedCuboid6(5, cuboid(1-px, 0, 0, 1, height, 1))); // EAST
		}
    }
    
    public Cuboid6 cuboid(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
    	return new Cuboid6(xCoord+minX, yCoord+minY, zCoord+minZ, xCoord+maxX, yCoord+maxY, zCoord+maxZ);
    }
    
    @Override
    public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTag) {
		super.writeToNBT(nbtTag);
		nbtTag.setBoolean("n", north);
		nbtTag.setBoolean("s", south);
		nbtTag.setBoolean("e", east );
		nbtTag.setBoolean("w", west );
		nbtTag.setBoolean("d", down );
		
		nbtTag.setBoolean("nF", northForceOpen);
		nbtTag.setBoolean("sF", southForceOpen);
		nbtTag.setBoolean("eF", eastForceOpen );
		nbtTag.setBoolean("wF", westForceOpen );
		nbtTag.setBoolean("dF", downForceOpen );
		nbtTag.setBoolean("ropeLight", ropeLight);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
	    super.readFromNBT(nbt);
	    this.north = nbt.getBoolean("n");
	    this.south = nbt.getBoolean("s");
	    this.east  = nbt.getBoolean("e");
	    this.west  = nbt.getBoolean("w");
	    this.down  = nbt.getBoolean("d");
	    
	    this.northForceOpen = nbt.getBoolean("nF");
	    this.southForceOpen = nbt.getBoolean("sF");
	    this.eastForceOpen  = nbt.getBoolean("eF");
	    this.westForceOpen  = nbt.getBoolean("wF");
	    this.downForceOpen  = nbt.getBoolean("dF");
	    
	    this.ropeLight = nbt.getBoolean("ropeLight");
	}
	
	public void markForUpdate() {
		 worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); // Makes the server call getDescriptionPacket for a full data sync
		 markDirty(); // Marks the chunk as dirty, so that it is saved properly on changes. Not required for the sync specifically, but usually goes alongside the former.
		 //worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, CatwalkMod.scaffold, 1);
	}
	
	 public Packet getDescriptionPacket() {
		 NBTTagCompound nbtTag = new NBTTagCompound();
		 this.writeToNBT(nbtTag);
		 return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	 }
	 
	 public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		 readFromNBT(packet.func_148857_g());
	 }
}
