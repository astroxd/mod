package com.a_str0.a_str0utilities.items;

import com.a_str0.a_str0utilities.Main;
import com.a_str0.a_str0utilities.init.ModItems;
import com.a_str0.a_str0utilities.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

	public ItemBase(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.ItemTab);
		ModItems.ITEMS.add(this);
	}
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");	
	}

}
