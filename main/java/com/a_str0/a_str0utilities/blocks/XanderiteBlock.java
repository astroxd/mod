package com.a_str0.a_str0utilities.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class XanderiteBlock extends BlockBase 
{

	public XanderiteBlock(String name, Material material) 
	{
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(5.0F);	
		setResistance(15.0F);
		setHarvestLevel("pickaxe", 2);
		
	}

}
