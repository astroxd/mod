package com.a_str0.a_str0utilities.init;

import java.util.ArrayList;
import java.util.List;

import com.a_str0.a_str0utilities.blocks.BlockBase;
import com.a_str0.a_str0utilities.blocks.FuelCrystalOre;
import com.a_str0.a_str0utilities.blocks.HexamineBlock;
import com.a_str0.a_str0utilities.blocks.XanderiteBlock;
import com.a_str0.a_str0utilities.blocks.XanderiteOre;
import com.a_str0.a_str0utilities.blocks.machines.BlockFuelMixerI;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block HEXAMINE_BLOCK = new HexamineBlock("hexamine_block", Material.IRON);
	public static final Block XANDERITE_BLOCK = new XanderiteBlock("xanderite_block", Material.IRON);
	public static final Block FUEL_CRYSTAL_ORE = new FuelCrystalOre("fuel_crystal_ore", Material.ROCK);
	public static final Block XANDERITE_ORE = new XanderiteOre("xanderite_ore", Material.ROCK);
	

	public static final Block FUEL_MIXER_I = new BlockFuelMixerI("fuel_mixer_i_");
	
}
