package com.a_str0.a_str0utilities.blocks.machines;


import com.a_str0.a_str0utilities.blocks.machines.slots.SlotsFuelMixerIOutput;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;;

public class ContainerFuelMixerI extends Container
{
	private final TileFuelMixerI tileEntity;
	private int cookProgress, totalCookTime;
	
	public ContainerFuelMixerI(InventoryPlayer player, TileFuelMixerI tileEntity)
	{
		this.tileEntity = tileEntity;
		IItemHandler handler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 56, 26));
		this.addSlotToContainer(new SlotsFuelMixerIOutput(handler, 1, 116, 35));
		
		for (int row = 0; row < 3; row++)
		{
			for (int col = 0; col < 9; col++)
			{
				this.addSlotToContainer(new Slot(player, (row*9)+col+9, 8+(col*18), 84+(row*18)));
			}
		}
		
		for (int col = 0; col < 9; col++)
		{
			this.addSlotToContainer(new Slot(player, col, 8+(col*18), 142));
		}
	}
		
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for (int i = 0; i < this.listeners.size(); i++)
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			
			if (this.cookProgress != this.tileEntity.getCookProgress())
				listener.sendWindowProperty(this, 0, this.tileEntity.getCookProgress());
			if (this.totalCookTime != this.tileEntity.getTotalCookTime())
				listener.sendWindowProperty(this, 1, this.tileEntity.getTotalCookTime());

			this.cookProgress = this.tileEntity.getCookProgress();
			this.totalCookTime = this.tileEntity.getTotalCookTime();
		}
	}
		
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return this.tileEntity.isUsableByPlayer(playerIn);
	}
	
	/**
	 * Transfers the item at index to the first available slot in the inventory.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) 
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		
		if (slot instanceof SlotsFuelMixerIOutput)
		{
			
		}
		
		if(slot != null && slot.getHasStack()) 
		{
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if (index == 0)
			{
				if (!this.mergeItemStack(stack1, 2, 38, false))
					return ItemStack.EMPTY;
			}
			if(index == 1) 
			{
				if(!this.mergeItemStack(stack1, 2, 38, true))
					return ItemStack.EMPTY;
				((SlotsFuelMixerIOutput)slot).onTake(playerIn, stack);
			}
			else if(index != 0) 
			{						
				if(!FuelMixerIRecipes.getInstance().getRecipeResult(slot.getStack()).isEmpty()) //If the item being merged is an ingredient for a recipe
				{
					if(!this.mergeItemStack(stack1, 0, 1, false)) //If Item does not merge to slot 0 (ingredient slot)
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 2 && index < 29) //If item is in inventory (not hotbar)
				{
					if (!this.mergeItemStack(stack1, 29, 38, false)) //If item does not merge to hotbar
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 29 && index < 38 && !this.mergeItemStack(stack1, 2, 29, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if(!this.mergeItemStack(stack1, 2, 38, false)) 
			{
				return ItemStack.EMPTY;
			}
			if(stack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
			
			if(stack1.getCount() == stack.getCount())
				return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
	return stack;
	}
}
