package com.a_str0.a_str0utilities.blocks.machines.slots;

import com.a_str0.a_str0utilities.blocks.machines.TileEntityFuelSintetizer;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotFuelSintetizerFuel extends Slot
{
	 public SlotFuelSintetizerFuel(IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
	    {
	        super(inventoryIn, slotIndex, xPosition, yPosition);
	    }

	    /**
	     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
	     */
	    public boolean isItemValid(ItemStack stack)
	    {
	        return TileEntityFurnace.isItemFuel(stack) || isBucket(stack);
	    }

	    public int getItemStackLimit(ItemStack stack)
	    {
	        return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
	    }

	    public static boolean isBucket(ItemStack stack)
	    {
	        return stack.getItem() == Items.BUCKET;
	    }
}
