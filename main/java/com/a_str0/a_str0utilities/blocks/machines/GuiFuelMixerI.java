package com.a_str0.a_str0utilities.blocks.machines;

import com.a_str0.a_str0utilities.util.Reference;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFuelMixerI extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/fuel_mixeri_i.png");
	private final InventoryPlayer player;
	private final TileFuelMixerI tileEntity;
	
	public GuiFuelMixerI(InventoryPlayer player, TileFuelMixerI tileEntity)
	{
		super(new ContainerFuelMixerI(player, tileEntity));
		this.player = player;
		this.tileEntity = tileEntity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String tileName = this.tileEntity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize/2 - this.fontRenderer.getStringWidth(tileName)/2), 8, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 8, this.ySize-96+2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		this.drawDefaultBackground();
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int cookProgScaled = this.getCookProgressScaled(24);
		this.drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 34, 176, 14, cookProgScaled+1, 16);
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int currCookProg = this.tileEntity.getCookProgress();
		int totalCookTime = this.tileEntity.getTotalCookTime();
		
		if (totalCookTime != 0 && currCookProg != 0)
		{
			return currCookProg*pixels/totalCookTime;
		}
		else
		{
			return 0;
		}
	}
}
