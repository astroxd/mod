package com.a_str0.a_str0utilities.blocks.machines;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import com.a_str0.a_str0utilities.init.ModItems;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;


public class FuelMixerIRecipes 
{
	// Make a singleton instance of the object
		private static final FuelMixerIRecipes INSTANCE = new FuelMixerIRecipes();
		// HashMap<output, Triple<input, input, input>>
		private final Table<ItemStack, ItemStack, ItemStack> 
			alloyList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
		private final Map<ItemStack, Float> 
			experienceList = Maps.<ItemStack, Float>newHashMap();
		
		public static FuelMixerIRecipes getInstance() {
			return INSTANCE;
		}
		
		private FuelMixerIRecipes() {
			addFuelMixerIRecipe(new ItemStack(ModItems.BURNT_CHARCOAL), new ItemStack(ModItems.ORGANIC_CHARCOAL), new ItemStack(ModItems.MIXED_CHARCOAL), 5.0F);
		}
		
		public void addFuelMixerIRecipe(ItemStack input1, ItemStack input2, ItemStack result, float experience) {
			if (getAlloyingResult(input1, input2) != ItemStack.EMPTY) return;
			this.alloyList.put(input1, input2, result);
			this.experienceList.put(result, Float.valueOf(experience));
		}
		
		public ItemStack getAlloyingResult(ItemStack input1, ItemStack input2 ) {
			// HashMap<output, Triple<input, input, input>>
			for (Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.alloyList.columnMap().entrySet()) {
				if (this.compareItemStacks(input1, (ItemStack)entry.getKey()))  
				{
					for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) 
					{
						if(this.compareItemStacks(input2, (ItemStack)ent.getKey())) 
						{
							return (ItemStack)ent.getValue();
						}
					}
				}
			}
			return ItemStack.EMPTY;
		}
		
		private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
			return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
		}
		
		public Table<ItemStack, ItemStack, ItemStack > getAlloyingList() {
			return alloyList;
		}
		
		public float getAlloyingExperience(ItemStack stack) {
			for (Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
				if (this.compareItemStacks(stack, (ItemStack)entry.getKey())) {
					return ((Float)entry.getValue()).floatValue();
				}
			}
			return 0.0f;
		}

	}
	