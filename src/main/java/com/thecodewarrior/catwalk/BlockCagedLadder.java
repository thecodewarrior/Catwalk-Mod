package com.thecodewarrior.catwalk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.thecodewarrior.guides.api.IBlockGuideProvider;
import com.thecodewarrior.notmine.codechicken.lib.raytracer.IndexedCuboid6;
import com.thecodewarrior.notmine.codechicken.lib.vec.Cuboid6;
import com.thecodewarrior.tooltips.api.Tooltip;
import com.thecodewarrior.tooltips.api.IBlockTooltipProvider;
import com.thecodewarrior.tooltips.api.ITooltip;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCagedLadder extends BlockContainerCustomHighlight implements IBlockTooltipProvider, IBlockGuideProvider {

	public BlockCagedLadder() {
		super(Reference.mat);
		setBlockName("cagedLadder");
		setCreativeTab(Reference.catTab);
		setStepSound(soundTypeLadder);
		setHardness(1.0F);
		float px = 0.0625F;
		setBlockBounds(px, 0, px, 1-px, 1, 1-px);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int l = MathHelper.floor_double((double)((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, l, 3);
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityCagedLadder();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int blockSide, float hitX, float hitY, float hitZ) {
		
		MovingObjectPosition hit = this.playerHit(world, player, x, y, z);
		int side = 0;
		
		if (hit == null)
            CatwalkMod.logger.warn("hit is null, subHit defaulting to 0");
		else {
			CatwalkMod.logger.info("subHit is " + hit.subHit);
			side = hit.subHit;
		}
		
		if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == CatwalkMod.ropeLight) {
			TileEntityCagedLadder tile = (TileEntityCagedLadder) world.getTileEntity(x, y, z);
			
			if(player.isSneaking() && tile.hasRopeLight() && !player.capabilities.isCreativeMode) {
				if(!world.isRemote) {
					tile.getWorldObj().spawnEntityInWorld(new EntityItem(tile.getWorldObj(), x, y, z, new ItemStack(CatwalkMod.ropeLight)));
				}
			}
			if (!player.isSneaking() && !player.capabilities.isCreativeMode) {
				--player.inventory.getCurrentItem().stackSize;
			}
			tile.ropeLight = !player.isSneaking();
			tile.markForUpdate();
			return true;
		}
		
		if(		player.inventory.getCurrentItem() != null &&
				player.inventory.getCurrentItem().getItem() instanceof ItemBlock &&
   ((ItemBlock) player.inventory.getCurrentItem().getItem()).field_150939_a == this) {

			switch(side) {
			case 0:
				( new ItemBlock(this) ).placeBlockAt(
						player.inventory.getCurrentItem(),
						player, world, x, y-1, z, side,
						hitX, hitY, hitZ,
						world.getBlockMetadata(x,y,z));
				world.setBlockMetadataWithNotify(x, y-1, z, world.getBlockMetadata(x,y,z), 3);
				if(!player.capabilities.isCreativeMode && y-1 > 0) {
					--player.inventory.getCurrentItem().stackSize;
				}
				return true;
			case 1:
				( new ItemBlock(this) ).placeBlockAt(
						player.inventory.getCurrentItem(),
						player, world, x, y+1, z, side,
						hitX, hitY, hitZ,
						world.getBlockMetadata(x,y,z));
				world.setBlockMetadataWithNotify(x, y+1, z, world.getBlockMetadata(x,y,z), 3);
				if(!player.capabilities.isCreativeMode && y+1 < 256) {
					--player.inventory.getCurrentItem().stackSize;
				}
				return true;
			}
		}
		
		if(Reference.isHoldingUsableWrench(player)) {
			TileEntityCagedLadder tile = (TileEntityCagedLadder)world.getTileEntity(x, y, z);
			switch(side) {
			case 0:
				tile.bottomForceOpen = !tile.bottomForceOpen;
				break;
			case 2:
				tile.northForceOpen = !tile.northForceOpen;
				break;
			case 3:
				tile.southForceOpen = !tile.southForceOpen;
				break;
			case 4:
				tile.westForceOpen = !tile.westForceOpen;
				break;
			case 5:
				tile.eastForceOpen = !tile.eastForceOpen;
				break;
			}
			tile.markForUpdate();
		}
		return false;
	}
	
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
		TileEntityCagedLadder tile = (TileEntityCagedLadder)world.getTileEntity(x, y, z);
		boolean rope = tile.hasRopeLight();
		super.breakBlock(world, x, y, z, block, metadata);

		world.spawnEntityInWorld(
				new EntityItem(world, x, y, z,
						new ItemStack(this, 1))
				);
	    if(rope) {
	    	world.spawnEntityInWorld(
					new EntityItem(world, x, y, z,
							new ItemStack(CatwalkMod.ropeLight, 1)
					));
	    }
	}
	
	public int quantityDropped(Random p_149745_1_)
    {
        return 0;
    }
	
	@Override
	public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
        return true;
    }
	
	@Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (block != this)
        {
            return block.getLightValue(world, x, y, z);
        }
        /**
         * Gets the light value of the specified block coords. Args: x, y, z
         */
        TileEntityCagedLadder tile = (TileEntityCagedLadder) world.getTileEntity(x, y, z);
        if(tile.ropeLight) {
        	return Reference.lightRopeBrightness;
        } else { return getLightValue(); }
    }
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB blockBounds, List list, Entity collidingEntity) {
		double px = 1.0 / 16.0;
        TileEntityCagedLadder tile = (TileEntityCagedLadder) world.getTileEntity(x, y, z);
		
        boolean hasWrench = false;
        if(collidingEntity instanceof EntityPlayer) {
        	EntityPlayer player = (EntityPlayer) collidingEntity;
        	hasWrench = Reference.isHoldingUsableWrench(player);
        }
        
        double height = 1.0;
        
        if(tile.isOnBottom()) {
        	addToList(world, x, y, z, blockBounds, list, 0, 0, 0, 1, px, 1); // down
        }
		if(!tile.isWestOpen()) { // west
			addToList(world, x, y, z, blockBounds, list, 0, 0, 0, px, height, 1); // WEST
		}
		if(!tile.isEastOpen()) { // east
			addToList(world, x, y, z, blockBounds, list, 1-px, 0, 0, 1, height, 1); // EAST
		}
		if(!tile.isNorthOpen()) { // north
			addToList(world, x, y, z, blockBounds, list, 0, 0, 0, 1, height, px); // NORTH
		}
		if(!tile.isSouthOpen()) { // south
			addToList(world, x, y, z, blockBounds, list, 0, 0, 1-px, 1, height, 1); // SOUTH
		}
	}
	
	public void addToList(World world, int x, int y, int z, AxisAlignedBB blockBounds, List list, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBox(
        		(double)x + minX, (double)y + minY, (double)z + minZ,
        		(double)x + maxX, (double)y + maxY, (double)z + maxZ
        		);
        		
		if (axisalignedbb1 != null && blockBounds.intersectsWith(axisalignedbb1))
        {
            list.add(axisalignedbb1);
        }
	}

	@Override
	public void addGuides(List<ITooltip> list, World w, int x, int y, int z,
			EntityPlayer player) {
		TileEntityCagedLadder tile = (TileEntityCagedLadder) w.getTileEntity(x, y, z);
		
    	float px = 1.0F/16;
        float inset = 1.0F/16;
        
        float mid = 6*px;
        
        float height = 1;
        
        ItemStack blowtorch = new ItemStack(CatwalkMod.blowtorch);
        
        if(tile.onBottom()) { // bottom
        	list.add(new Tooltip(
					Reference.MODID, "cagedLadderBottom",
					blowtorch,
					cuboid( x,y,z,
					mid, 0, mid,
					1-mid, px, 1-mid)));
		}
        if(tile.onTop()) {
        	list.add(new Tooltip(
					Reference.MODID, "cagedLadderTop",
					blowtorch,
					cuboid( x,y,z,
					mid, 1-px, mid,
					1-mid, 1, 1-mid)));
        }
        
		if(!tile.isNorthOpen()) { // north
			list.add(new Tooltip(
					Reference.MODID, "cagedLadderSide",
					blowtorch,
					cuboid( x,y,z,
					0, 0, px,
					1, 1, 2*px))); // NORTH
		}
		if(!tile.isSouthOpen()) { // south
			list.add(new Tooltip(
					Reference.MODID, "cagedLadderSide",
					blowtorch,
					cuboid( x,y,z,
					0, 0, 1-(2*px),
					1, 1, 1-(px)  ))); // SOUTH
		}
		if(!tile.isWestOpen()) { // west
			list.add(new Tooltip(
					Reference.MODID, "cagedLadderSide",
					blowtorch,
					cuboid( x,y,z,
					px, 0, 0,
					2*px, 1, 1))); // WEST
		}
		if(!tile.isEastOpen()) { // east
			list.add(new Tooltip(
					Reference.MODID, "cagedLadderSide",
					blowtorch,
					cuboid( x,y,z,
					1-(2*px), 0, 0,
					1-(px), 1, 1))); // EAST
		}
		
	}
	
	public Cuboid6 cuboid(int x, int y, int z, float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
    	return new Cuboid6(x+minX, y+minY, z+minZ, x+maxX, y+maxY, z+maxZ);
    }

	@Override
	public String getItemGuideName(ItemStack stack) {
		return Reference.MODID + ":cagedLadder";
	}
	
//	@Override
//	public void onNeighborBlockChange(World w, int x, int y, int z, Block block) {
//		checkIsOnBottom(w,x,y,z);
//	}
//	
//	public void checkIsOnBottom(World world, int x, int y, int z) {
//		TileEntityCagedLadder tile = (TileEntityCagedLadder) world.getTileEntity(x, y, z);
//		if(world.isAirBlock(x, y-1, z)) {
//			
//		}
//	}
	
}
