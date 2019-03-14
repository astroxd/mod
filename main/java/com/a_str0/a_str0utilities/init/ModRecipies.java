package com.a_str0.a_str0utilities.init;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipies {
	
	public static void init() {
		GameRegistry.addSmelting(ModItems.XANDERITE_DUST, new ItemStack(ModItems.XANDERITE_INGOT, 1), 1.5f);
		GameRegistry.addSmelting(ModBlocks.XANDERITE_ORE, new ItemStack(ModItems.XANDERITE_INGOT, 1), 1.5f); 
		GameRegistry.addSmelting(new ItemStack(Items.COAL, 1,0), new ItemStack(ModItems.BURNT_COAL, 1), 1.5f);
		GameRegistry.addSmelting(new ItemStack(Items.COAL, 1,1), new ItemStack(ModItems.BURNT_CHARCOAL, 1), 1.5f);
		GameRegistry.addSmelting(Items.REEDS, new ItemStack(ModItems.ORGANIC_CHARCOAL, 1), 1.5f);
		
		
	}
	

}
