package com.thecodewarrior.catwalk;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import codechicken.lib.raytracer.IndexedCuboid6;

public interface IHighlightProvider {
	public void addTraceableCuboids(List<IndexedCuboid6> cuboids, EntityPlayer player);
}
