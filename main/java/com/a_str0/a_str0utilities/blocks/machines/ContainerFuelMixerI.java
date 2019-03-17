package com.a_str0.a_str0utilities.blocks.machines;


import com.a_str0.a_str0utilities.blocks.machines.slots.SlotFuelMixerIOutput;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ContainerFuelMixerI extends Container
{
	private final IInventory tileOven;
    private int cookTime;
    private int totalCookTime;
    private int ovenBurnTime;

    public ContainerFuelMixerI(InventoryPlayer playerInventory, IInventory fuelMixerIInventory)
    {
        this.tileOven = fuelMixerIInventory;
        this.addSlotToContainer(new Slot(fuelMixerIInventory, 0, 56, 17));
        this.addSlotToContainer(new SlotFuelMixerIOutput(playerInventory.player, fuelMixerIInventory, 2, 116, 35));

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileOven);
    }
    
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.cookTime != this.tileOven.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.tileOven.getField(1));
            }

            if (this.ovenBurnTime != this.tileOven.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.tileOven.getField(0));
            }

           if (this.totalCookTime != this.tileOven.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, this.tileOven.getField(2));
            }
        }

        this.cookTime = this.tileOven.getField(1);
        this.ovenBurnTime = this.tileOven.getField(0);
        this.totalCookTime = this.tileOven.getField(2);
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileOven.setField(id, data);
    }
    
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.tileOven.isUsableByPlayer(playerIn);
    }

    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 30, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != 1 && index != 0)
            {
                if (!FuelMixerIRecipes.instance().getCookingResult(itemstack1).isEmpty())
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
               
                else if (index >= 2 && index < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 30, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 30 && index < 30 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 30, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }














}