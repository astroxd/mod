package com.a_str0.a_str0utilities.blocks.machines.slots;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import com.a_str0.a_str0utilities.blocks.machines.FuelMixerIRecipes;

public class SlotsFuelMixerIOutput extends SlotItemHandler
{
	public SlotsFuelMixerIOutput(IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}
	
	@Override
    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
    {
		if (!thePlayer.world.isRemote)
		{
	        int stackSize = stack.getCount();
	        float recipeExp = FuelMixerIRecipes.getInstance().getSinteringExperience(stack);
	        
	        int totalExp = (int)(stackSize*recipeExp);
	
	        if (totalExp == 0 && recipeExp > 0.0F)
	        {
	        	totalExp = 1;
	        }
	        while (totalExp > 0)
	        {
	            int expInCurrentOrb = EntityXPOrb.getXPSplit(totalExp);
	            thePlayer.world.spawnEntity(new EntityXPOrb(thePlayer.world, thePlayer.posX, thePlayer.posY + 0.5D, thePlayer.posZ, expInCurrentOrb));
	            totalExp -= expInCurrentOrb;
	        }
		}
        super.onTake(thePlayer, stack);
        return stack;
    }
}

