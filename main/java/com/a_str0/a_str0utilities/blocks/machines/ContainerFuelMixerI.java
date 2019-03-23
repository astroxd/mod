package com.a_str0.a_str0utilities.blocks.machines;

import com.a_str0.a_str0utilities.blocks.machines.slots.SlotFuelMixerIOutput;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFuelMixerI extends Container
{
	private final TileEntityFuelMixerI tileEntity;
	private int cookTime, totalCookTime, burnTime, currentBurnTime;
	
	public ContainerFuelMixerI(InventoryPlayer player, TileEntityFuelMixerI tileEntity) {
		this.tileEntity = tileEntity;
		
		// FUELMIXERI Inventory
		addSlotToContainer(new Slot(this.tileEntity, TileEntityFuelMixerI.INPUT_1, 56, 7));
		addSlotToContainer(new Slot(this.tileEntity, TileEntityFuelMixerI.INPUT_2, 47, 25));
		addSlotToContainer(new SlotFuelMixerIOutput(player.player, this.tileEntity, TileEntityFuelMixerI.OUTPUT, 124, 25));
		
		// Player Inventory
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		// Player Hotbar
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, tileEntity);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int i = 0; i < listeners.size(); i++) {
			IContainerListener listener = listeners.get(i);
			
			if (cookTime != tileEntity.getField(2)) listener.sendWindowProperty(this, 2, tileEntity.getField(0));
			if (totalCookTime != tileEntity.getField(3)) listener.sendWindowProperty(this, 3, tileEntity.getField(1));
			
		
			cookTime = tileEntity.getField(0);
			totalCookTime = tileEntity.getField(1);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) 
	{
		tileEntity.setField(id, data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) 
	{
		return tileEntity.isUsableByPlayer(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) 
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) 
		{
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if(index == 3) 
			{
				if(!this.mergeItemStack(stack1, 4, 40, true)) return ItemStack.EMPTY;
				slot.onSlotChange(stack1, stack);
			}
			else if(index != 0) 
			{
				Slot slot1 = (Slot)this.inventorySlots.get(index + 1);
				if (!FuelMixerIRecipes.getInstance().getAlloyingResult(stack1, slot1.getStack()).isEmpty()) 
				{
					if(!this.mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
				}
				
			}
		}
		return stack;
	}
}
				
		
	

			
			
			
 