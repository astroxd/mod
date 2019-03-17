package com.a_str0.a_str0utilities.util.handlers;

import com.a_str0.a_str0utilities.blocks.machines.ContainerFuelMixerI;
import com.a_str0.a_str0utilities.blocks.machines.TileEntityFuelMixerI;
import com.a_str0.a_str0utilities.util.Reference;
import com.a_str0.a_str0utilities.blocks.machines.GuiFuelMixerI;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{


	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == Reference.GUI_FUEL_MIXER_I) return new ContainerFuelMixerI(player.inventory, (TileEntityFuelMixerI)world.getTileEntity(new BlockPos(x,y,z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == Reference.GUI_FUEL_MIXER_I) return new GuiFuelMixerI(player.inventory, (TileEntityFuelMixerI)world.getTileEntity(new BlockPos(x,y,z)));
		return null;
	}

}
	
	