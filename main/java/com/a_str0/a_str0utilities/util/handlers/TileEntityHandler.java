package com.a_str0.a_str0utilities.util.handlers;

import com.a_str0.a_str0utilities.blocks.machines.TileEntityFuelMixerI;
import com.a_str0.a_str0utilities.blocks.machines.TileEntityFuelSintetizer;
import com.a_str0.a_str0utilities.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler 
{
	public static void registerTileEntities() 
	{
		GameRegistry.registerTileEntity(TileEntityFuelMixerI.class, new ResourceLocation(Reference.MOD_ID + ":fuel_mixer_i"));
		GameRegistry.registerTileEntity(TileEntityFuelSintetizer.class, new ResourceLocation(Reference.MOD_ID + ":fuel_sintetizer"));
	}
	
}	
