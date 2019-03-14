package com.a_str0.a_str0utilities.tabs;

import com.a_str0.a_str0utilities.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemTab extends CreativeTabs 
{
	public ItemTab(String label) { super("a_str0utilitiesitemtab");}
	public ItemStack getTabIconItem() { return new ItemStack(ModItems.BURNT_CHARCOAL);}
	
	
}
