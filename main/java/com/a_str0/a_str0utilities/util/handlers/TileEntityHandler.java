package com.a_str0.a_str0utilities.util.handlers;

import com.a_str0.a_str0utilities.blocks.machines.TileFuelMixerI;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler 
{
	public static void registerTileEntities() 
	{
		GameRegistry.registerTileEntity(TileFuelMixerI.class, "fuel_mixer_i");
	}
}	
