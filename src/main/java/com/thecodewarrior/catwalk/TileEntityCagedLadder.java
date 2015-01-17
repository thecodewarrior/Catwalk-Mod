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

public class TileEntityCagedLadder extends TileEntity implements IHighlightProvider{
	
	public int directionOverride = -1;
	
	public boolean north = true;
	public boolean south = true;
	public boolean east  = true;
	public boolean west  = true;
	
	public boolean northForceOpen = false;
	public boolean southForceOpen = false;
	public boolean eastForceOpen  = false;
	public boolean westForceOpen  = false;
	
	public boolean ropeLight = false;

	public boolean bottomForceOpen;
	
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
	
	public boolean hasRopeLight() {
		return ropeLight;
	}
	
	public boolean isOnBottom() {
		return !bottomForceOpen && onBottom();
	}
	
	public boolean onBottom() {
		return worldObj.isAirBlock(xCoord, yCoord-1, zCoord);
	}
	
	public boolean onTop() {
		return worldObj.isAirBlock(xCoord, yCoord+1, zCoord);
	}

	public int getDirection() {
		if(directionOverride != -1) {
			return directionOverride;
		} else {
			return getWorldObj().getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	@Override
	public void addTraceableCuboids(List<IndexedCuboid6> cuboids, EntityPlayer player) {
		boolean all = true;
		boolean hasWrench = true;
		if(player != null) {
			all = player.isSneaking();
			hasWrench = Reference.isHoldingUsableWrench(player);
		}
    	float px = 1.0F/16;
        float inset = 1.0F/16;
        
        float mid = 6*px;
        
        float height = 1;
        
        
        if(onBottom()) { // bottom
			cuboids.add(new IndexedCuboid6(0, cuboid(
					mid, 0, mid,
					1-mid, px, 1-mid)));
		}
        if(onTop()) {
			cuboids.add(new IndexedCuboid6(1, cuboid(
					mid, 1-px, mid,
					1-mid, 1, 1-mid)));
        }
        
		if(!isNorthOpen() || all) { // north
			cuboids.add(new IndexedCuboid6(2, cuboid(
					0, 0, px,
					1, 1, 2*px))); // NORTH
		}
		if(!isSouthOpen() || all) { // south
			cuboids.add(new IndexedCuboid6(3, cuboid(
					0, 0, 1-(2*px),
					1, 1, 1-(px)  ))); // SOUTH
		}
		if(!isWestOpen() || all) { // west
			cuboids.add(new IndexedCuboid6(4, cuboid(
					px, 0, 0,
					2*px, 1, 1))); // WEST
		}
		if(!isEastOpen() || all) { // east
			cuboids.add(new IndexedCuboid6(5, cuboid(
					1-(2*px), 0, 0,
					1-(px), 1, 1))); // EAST
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
		
		nbtTag.setBoolean("nF", northForceOpen);
		nbtTag.setBoolean("sF", southForceOpen);
		nbtTag.setBoolean("eF", eastForceOpen );
		nbtTag.setBoolean("wF", westForceOpen );
		nbtTag.setBoolean("bF", bottomForceOpen);
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
	    
	    this.northForceOpen = nbt.getBoolean("nF");
	    this.southForceOpen = nbt.getBoolean("sF");
	    this.eastForceOpen  = nbt.getBoolean("eF");
	    this.westForceOpen  = nbt.getBoolean("wF");
	    this.bottomForceOpen = nbt.getBoolean("bF");
	    
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
