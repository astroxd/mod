package com.a_str0.a_str0utilities.blocks.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileFuelMixerI extends TileEntity implements ITickable
{

	private ItemStackHandler handler = new ItemStackHandler(2);
	private String customName;

	private int cookProgress;
	private int totalCookTime;

	
	@Override
	public void update() 
	{	
		if (this.canSmelt())
		{
			ItemStack input = this.handler.getStackInSlot(0);
			this.totalCookTime = this.getStackTotalCookTime(input);
			this.cookProgress++;
			
			if(cookProgress == totalCookTime)
			{
				ItemStack result = FuelMixerIRecipes.getInstance().getRecipeResult(input);
				ItemStack output = this.handler.getStackInSlot(1);
				
				if (output.isEmpty())
				{
					output = result.copy();
				}
				else
				{
					output.grow(result.getCount());
				}
				input.shrink(1);
				
				this.handler.setStackInSlot(0, input);
				this.handler.setStackInSlot(1, output);
				
				this.cookProgress = 0;
			}
			world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 2);
		}
		else
		{
			this.cookProgress = 0;
		}
	}

	private boolean canSmelt()
	{
		if (this.handler.getStackInSlot(0).isEmpty())
		{
			return false;
		}
		else
		{
			ItemStack result = FuelMixerIRecipes.getInstance().getRecipeResult(this.handler.getStackInSlot(0));
			
			if (result.isEmpty())
			{
				return false;
			}
			else
			{
				ItemStack output = this.handler.getStackInSlot(1);
				
				if (output.isEmpty())
				{
					return true;
				}
				if (!output.isItemEqual(result))
				{
					return false;
				}
				
				int res = output.getCount() + result.getCount();
				return res <= 64 && res <= output.getMaxStackSize();
			}
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) this.handler;
		return super.getCapability(capability, facing);
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		
	    return new SPacketUpdateTileEntity(getPos(), 1, this.getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{	
	    this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("CookProgress", (short)this.cookProgress);
		compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
		compound.setTag("Inventory", this.handler.serializeNBT());
		
		if (this.hasCustomName())
			compound.setString("CustomName", this.customName);
		
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.cookProgress = compound.getInteger("CookProgress");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		
		if (compound.hasKey("CustomName", 0))
			this.setCustomName(compound.getString("CustomName"));
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return this.hasCustomName() ? new TextComponentString(this.customName) : new TextComponentTranslation("container.fuel_mixer_i");
	}

	/**
	 * Gets the current cooking progress in ticks.
	 * @return The value of cookProgress
	 */
	public int getCookProgress()
	{
		return cookProgress;
	}

	public int getTotalCookTime()
	{
		return totalCookTime;
	}

	public int getStackTotalCookTime(ItemStack stack)
	{
		if (stack.isEmpty())
		{
			return 0;
		}
		else
		{
			return 200;
		}
	}

	public ItemStackHandler getItemStackHandler()
	{
		return handler;
	}

	public String getGuiID()
	{
		return "modblocks.fuel_mixer_i";
	}

	public IBlockState getBlockState()
	{
		return world.getBlockState(this.getPos());
	}

	public void setCustomName(String customName)
	{
		this.customName = customName;
	}

	public void setCookProgress(int cookTime)
	{
		this.cookProgress = cookTime;
	}
	
	public void setTotalCookTime(int totalCookTime)
	{
		this.totalCookTime = totalCookTime; 
	}

	public boolean hasCustomName()
	{
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
}
