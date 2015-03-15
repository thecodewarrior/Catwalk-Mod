package com.thecodewarrior.catwalk;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.thecodewarrior.guides.api.IBlockGuideProvider;
import com.thecodewarrior.notmine.codechicken.lib.raytracer.RayTracer;
import com.thecodewarrior.notmine.codechicken.lib.vec.Cuboid6;
import com.thecodewarrior.tooltips.api.Tooltip;
import com.thecodewarrior.tooltips.api.IBlockTooltipProvider;
import com.thecodewarrior.tooltips.api.ITooltip;

public class BlockCatwalk extends BlockContainerCustomHighlight implements IBlockTooltipProvider, IBlockGuideProvider {

	private RayTracer rayTracer = new RayTracer();
	
	public BlockCatwalk() {
		super(Reference.mat);
		setBlockName("scaffold");
		setCreativeTab(Reference.catTab);
		setStepSound(soundTypeStone);
		setHardness(1.0F);
	}

	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		updateSides(world,x,y,z);
    }
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		updateSides(world,x,y,z);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int blockSide, float hitX, float hitY, float hitZ) {
		
		MovingObjectPosition hit = this.playerHit(world, player, x, y, z);
		int side = 1;
		
		if (hit == null) {
            //System.out.println("NULL");
		} else {
			//System.out.println(hit.subHit);
			side = hit.subHit;
		}
		
		if(player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == CatwalkMod.ropeLight) {
			TileEntityCatwalk tile = (TileEntityCatwalk) world.getTileEntity(x, y, z);
			
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
  ( (ItemBlock) player.inventory.getCurrentItem().getItem() ).field_150939_a == this) {
				int setX = x;
			int setY = y;
			int setZ = z;
			//System.out.println("setting");
			switch(side) {
			case 2:
				setZ--; break;
			case 3:
				setZ++; break;
			case 4:
				setX--; break;
			case 5:
				setX++; break;
			}
			( new ItemBlock(this) ).placeBlockAt(
					player.inventory.getCurrentItem(),
					player, world, setX, setY, setZ,
					side, 0, 0, 0, 0);
			if(!player.capabilities.isCreativeMode) {
				--player.inventory.getCurrentItem().stackSize;
			}
			return true;
		}
		
		if(Reference.isHoldingUsableWrench(player)) {
			TileEntityCatwalk tile = (TileEntityCatwalk)world.getTileEntity(x, y, z);
			if(player.isSneaking()) {
				switch(side) {
				case 0:
					tile.downForceOpen = !tile.downForceOpen;
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
			return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	/**
     * Get a light value for the block at the specified coordinates, normal ranges are between 0 and 15
     *
     * @param world The current world
     * @param x X Position
     * @param y Y position
     * @param z Z position
     * @return The light value
     */
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
        TileEntityCatwalk tile = (TileEntityCatwalk) world.getTileEntity(x, y, z);
        if(tile.ropeLight) {
        	return Reference.lightRopeBrightness;
        } else { return getLightValue(); }
    }
	
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
		TileEntityCatwalk tile = (TileEntityCatwalk)world.getTileEntity(x, y, z);
		world.spawnEntityInWorld(
				new EntityItem(world, x, y, z,
						new ItemStack(this, 1))
				);
	    if(tile.hasRopeLight()) {
	    	world.spawnEntityInWorld(
					new EntityItem(world, x, y, z,
							new ItemStack(CatwalkMod.ropeLight, 1)
					));
	    }
		super.breakBlock(world, x, y, z, block, metadata);
	}
	
	public int quantityDropped(Random p_149745_1_)
    {
        return 0;
    }
	
	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityCatwalk();
	}
	@Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(entity instanceof EntityPlayer) {
			((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 10));
		}
	}
	
	public void onNeighborBlockChange(World w, int x, int y, int z, Block block) {
		updateSides(w,x,y,z);
	}
	
	public void updateSides(World w, int x, int y, int z) {
		//System.out.println("our coord: " + x + ", " + y + ", " + z);
		TileEntityCatwalk tile = (TileEntityCatwalk)w.getTileEntity(x, y, z);
		boolean oldNorth = tile.north,
				oldSouth = tile.south,
				oldEast  = tile.east,
				oldWest  = tile.west,
				oldDown  = tile.down;
		updateSide(2,w,x,y,z); // bottom, top, north, south, west, east
		updateSide(3,w,x,y,z);
		updateSide(5,w,x,y,z);
		updateSide(4,w,x,y,z);
		updateSide(0,w,x,y,z);
		if(oldNorth != tile.north ||
		   oldSouth != tile.south ||
		   oldEast  != tile.east  ||
		   oldWest  != tile.west  ||
		   oldDown  != tile.down
				){
			tile.markForUpdate();
		}
//		System.out.println("N: " + tile.north + " S: " + tile.south + " E: " + tile.east + " W: " + tile.west + " D: " + tile.down);
		//tile.markForUpdate();
	}
	
	public boolean shouldOpenSideFor(World w, int x, int y, int z) {
		return w.isAirBlock(x, y, z);
	}
	
	public void updateSide(int side, World world, int x, int y, int z) {
		TileEntityCatwalk tile = (TileEntityCatwalk)world.getTileEntity(x, y, z);
		switch(side) {
			case 0:
				y -= 1;
				tile.down = shouldOpenSideFor(world,x,y,z);
			break;
			case 1:
				y += 1;
				//tile.up = !shouldOpenSideFor(world,x,y,z);
			break;
			case 2:
				z -= 1;
				tile.north = shouldOpenSideFor(world,x,y,z);
			break;
			case 3:
				z += 1;
				tile.south = shouldOpenSideFor(world,x,y,z);
			break;
			case 4:
				x -= 1;
				tile.west = shouldOpenSideFor(world,x,y,z);
			break;
			case 5:
				x += 1;
				tile.east = shouldOpenSideFor(world,x,y,z);
			break;
		}
		
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB blockBounds, List list, Entity collidingEntity) {
		double px = 1.0 / 16.0;
        TileEntityCatwalk tile = (TileEntityCatwalk) world.getTileEntity(x, y, z);
		
        double height = 1.5;
        if(collidingEntity instanceof EntityPlayer && ((EntityPlayer)collidingEntity).isSneaking()) {
        	height = 0.75;
        }
        
		if(!tile.isDownOpen()) { // bottom
			addToList(world, x, y, z, blockBounds, list, 0, 0, 0, 1, px, 1);
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
//		List<IGuide> list = new ArrayList<IGuide>();
		boolean all = false;
		
		TileEntityCatwalk tile = (TileEntityCatwalk) w.getTileEntity(x, y, z);
		
		float px = 1.0F/16.0F;
		float height = 1;
		
		ItemStack blowtorch = new ItemStack( CatwalkMod.blowtorch);
		
		if(!tile.isDownOpen() || all) { // bottom
			list.add(new Tooltip(
					Reference.MODID, "catwalkDown",
					blowtorch,
					cuboid(x,y,z,0,0,0,1,px,1)
					));
		}
		if(!tile.isNorthOpen() || all) { // north
			list.add(new Tooltip(
					Reference.MODID, "catwalkSide",
					blowtorch,
					cuboid(x,y,z,0, 0, 0, 1, height, px)
					)); // NORTH
		}
		if(!tile.isSouthOpen() || all) { // south
			list.add(new Tooltip(
					Reference.MODID, "catwalkSide",
					blowtorch,
					cuboid(x,y,z, 0, 0, 1-px, 1, height, 1)
					)); // SOUTH
		}
		if(!tile.isWestOpen() || all) { // west
			list.add(new Tooltip(
					Reference.MODID, "catwalkSide",
					blowtorch,
					cuboid(x,y,z, 0, 0, 0, px, height, 1)
					)); // WEST
		}
		if(!tile.isEastOpen() || all) { // east
			list.add(new Tooltip(
					Reference.MODID, "catwalkSide",
					blowtorch,
					cuboid(x,y,z, 1-px, 0, 0, 1, height, 1)
					)); // EAST
		}
//		return list;
	}
    
    public Cuboid6 cuboid(int x, int y, int z, float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
    	return new Cuboid6(x+minX, y+minY, z+minZ, x+maxX, y+maxY, z+maxZ);
    }

	@Override
	public String getItemGuideName(ItemStack stack) {
		return Reference.MODID + ":catwalk";
	}
	
	
	
}

