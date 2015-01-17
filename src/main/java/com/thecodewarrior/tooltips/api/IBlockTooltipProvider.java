package com.thecodewarrior.tooltips.api;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IBlockTooltipProvider {
	public void addGuides(List<ITooltip> list, World w, int x, int y, int z, EntityPlayer player);
}
