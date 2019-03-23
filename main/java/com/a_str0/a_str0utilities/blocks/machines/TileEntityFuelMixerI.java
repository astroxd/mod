package com.a_str0.a_str0utilities.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFuelMixerI extends TileEntity implements ITickable, IInventory
{
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(5, ItemStack.EMPTY);
	private String customName;
	
	private static final Ingredient CHARCOAL = Ingredient.fromStacks(new ItemStack(Items.COAL, 1, 1));
	
	public static final int INPUT_1 = 0, INPUT_2 = 1;
	public static final int OUTPUT = 4;
	
	private int burnTime, currentBurnTime;
	private int cookTime, totalCookTime;

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.fuel_mixer_i";
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : inventory) {
			if (!stack.isEmpty()) return false;
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return (ItemStack)inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(inventory, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(inventory, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack)inventory.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		inventory.set(index, stack);
		
		if (stack.getCount() > getInventoryStackLimit()) stack.setCount(getInventoryStackLimit());
		if (index == INPUT_1 && index + 1 == INPUT_2 !=flag) {
			ItemStack stack1 = (ItemStack)inventory.get(index+1), stack2 = (ItemStack)inventory.get(index+2);
			totalCookTime = getCookTime(stack, stack1, stack2);
			cookTime = 0;
			markDirty();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		inventory = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, inventory);
		cookTime = compound.getInteger("cookTime");
		totalCookTime = compound.getInteger("totalCookTime");
		
		
		if (compound.hasKey("customName", 8)) setCustomName(compound.getString("customName"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("cookTime", (short)cookTime);
		compound.setInteger("totalCookTime", (short)totalCookTime);
		
		ItemStackHelper.saveAllItems(compound, inventory);
		
		if (hasCustomName()) compound.setString("customName", customName);
		return compound;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	public boolean isCooking() {
		return cookTime > 0;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isCooking(IInventory inventory) {
		return inventory.getField(INPUT_1) > 0;
	}
	
	@Override
	public void update() {
		boolean cooking = isCooking();
		boolean flag = false;
		
		if (isCooking()) --cookTime;
		
		if (!world.isRemote) {
			
			if (isCooking()  !=((((ItemStack)inventory.get(INPUT_1)).isEmpty() 
													|| ((ItemStack)inventory.get(INPUT_2)).isEmpty()))){
				if (!isCooking() && canAlloy()) 
				{
					
				
				if (isCooking() && canAlloy()) {
					++cookTime;
					if (cookTime == totalCookTime) {
						cookTime = 0;
						totalCookTime = getCookTime((ItemStack)inventory.get(INPUT_1), (ItemStack)inventory.get(INPUT_2), (ItemStack)inventory.get(OUTPUT));
						smeltItem();
						flag = true;
					}
				}
				
				else cookTime = 0;
			}
			else if (!isCooking() && cookTime > 0) {
				cookTime = MathHelper.clamp(cookTime - 2, 0, totalCookTime);
			}
			if (cooking != isCooking()) {
				flag = true;
				BlockFuelMixerI.setState(isCooking(), world, pos);
			}
		}
		if (flag) markDirty();}
		
	}
	
	public int getCookTime(ItemStack input1, ItemStack input2, ItemStack input3) {
		return 200;
	}
	
	private boolean canAlloy() {
		if (((ItemStack)inventory.get(INPUT_1)).isEmpty()
				|| ((ItemStack)inventory.get(INPUT_2)).isEmpty()) return false;
		else {
			ItemStack result = FuelMixerIRecipes.getInstance()
					.getAlloyingResult((ItemStack)inventory.get(INPUT_1), (ItemStack)inventory.get(INPUT_2));
			if (result.isEmpty()) return false;
			else {
				ItemStack output = (ItemStack)inventory.get(OUTPUT);
				if (output.isEmpty()) return true;
				if (!output.isItemEqual(result)) return false;
				int res = output.getCount() + result.getCount();
				return res <= getInventoryStackLimit() && res <= output.getMaxStackSize();
			}
		}
	}
	
	public void smeltItem() {
		if (canAlloy()) {
			ItemStack input1 = inventory.get(INPUT_1);
			ItemStack input2 = inventory.get(INPUT_2);
			ItemStack result = FuelMixerIRecipes.getInstance().getAlloyingResult(input1, input2);
			ItemStack output = inventory.get(OUTPUT);
			
			if (output.isEmpty()) inventory.set(OUTPUT, result.copy());
			else if (output.getItem() == result.getItem()) output.grow(result.getCount());
			
			input1.shrink(1);
			input2.shrink(1);
			
		}
	}
	
	public static int getItemBurnTime(ItemStack fuel) {
		if (fuel.isEmpty()) return 0;
		else {
			Item item = fuel.getItem();
			
			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
				Block block = Block.getBlockFromItem(item);
				
				if (block == Blocks.COAL_BLOCK) return 16000;
			}
			
			if (CHARCOAL.apply(fuel)) return 1600;
			
//			return GameRegistry.getFuelValue(fuel);
			return 0;
		}
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return world.getTileEntity(pos) != this ? false : 
			player.getDistanceSq((double) pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == OUTPUT) return false;
		else if (index != 2) return true;
		else
		{
			return isItemValidForSlot(index, stack);
		}
	}
	
	public String getGuiID() {
		return "au:fuel_mixer_i";
	}

	@Override
	public int getField(int id) {
		switch (id) {
		
		case 2:
			return cookTime;
		case 3:
			return totalCookTime;
		default:
			return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		
		case 2:
			cookTime = value;
			break;
		case 3:
			totalCookTime = value;
			break;
		}
	}

	@Override
	public int getFieldCount() {
		return 4;
	}

	@Override
	public void clear() {
		inventory.clear();
	}




}
