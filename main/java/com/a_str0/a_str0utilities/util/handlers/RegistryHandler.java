package com.a_str0.a_str0utilities.util.handlers;

import com.a_str0.a_str0utilities.Main;
import com.a_str0.a_str0utilities.blocks.BlockBase;
import com.a_str0.a_str0utilities.blocks.HexamineBlock;
import com.a_str0.a_str0utilities.init.ModBlocks;
import com.a_str0.a_str0utilities.init.ModItems;
import com.a_str0.a_str0utilities.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@EventBusSubscriber
public class RegistryHandler 
{
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
		TileEntityHandler.registerTileEntities();
	}

	
	
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : ModItems.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
	
		for(Block block : ModBlocks.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}
		}
	

	
	}
	public static void initRegistries() 
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
	}


	@SubscribeEvent
	public static void furnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event)
	{
		if(event.getItemStack().getItem() == (ModItems.BURNT_CHARCOAL))
		{
			event.setBurnTime(2400);
		}
		if(event.getItemStack().getItem() == (ModItems.BLAZED_BURNT_COAL))
	    {
	        event.setBurnTime(12800);
	    }
		if(event.getItemStack().getItem() == (ModItems.BURNT_COAL))
	    {
	        event.setBurnTime(2400);
	    }
		if(event.getItemStack().getItem() == (ModItems.BURNT_MIXED_CHARCOAL))
	    {
	        event.setBurnTime(16000);
	    }
		if(event.getItemStack().getItem() == (ModItems.FUEL_CRYSTAL))
	    {
	        event.setBurnTime(6400);
	    }
		if(event.getItemStack().getItem() == (ModItems.HEXAMINE_INGOT))
	    {
	        event.setBurnTime(52000);
	    }
		if(event.getItemStack().getItem() == (ModItems.ORGANIC_CHARCOAL))
	    {
	        event.setBurnTime(800);
	    }
		if(event.getItemStack().getItem() == (ModItems.UNREFINED_HEXAMINE))
	    {
	        event.setBurnTime(36000);
	    }
		if(event.getItemStack().getItem() == (ModItems.XANDERITE_INGOT))
	    {
	        event.setBurnTime(24000);
	    }
		if(event.getItemStack().getItem() == (ModItems.MIXED_CHARCOAL))
	    {
	        event.setBurnTime(14000);
	    }
		if(event.getItemStack().getItem() == Item.getItemFromBlock(ModBlocks.HEXAMINE_BLOCK))
	    {
	        event.setBurnTime(58400);
	    }
	
	}
}
