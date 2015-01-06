package com.thecodewarrior.catwalk;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialCatwalk extends Material {

	public MaterialCatwalk(MapColor p_i2116_1_) {
		super(p_i2116_1_);
		
	}

    public boolean isSolid()
    {
        return false;
    }
	
    public boolean blocksMovement()
    {
        return false;
    }
    
}
