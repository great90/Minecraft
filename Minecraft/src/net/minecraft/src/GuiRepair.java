package net.minecraft.src;

import java.util.List;

import net.minecraft.client.Minecraft;

public class GuiRepair extends GuiContainer implements ICrafting
{
	private static final ResourceLocation field_110429_t = new ResourceLocation("textures/gui/container/anvil.png");
	private ContainerRepair repairContainer;
	private GuiTextField itemNameField;
	private InventoryPlayer field_82325_q;
	
	public GuiRepair(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
	{
		super(new ContainerRepair(par1InventoryPlayer, par2World, par3, par4, par5, Minecraft.getMinecraft().thePlayer));
		field_82325_q = par1InventoryPlayer;
		repairContainer = (ContainerRepair) inventorySlots;
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.func_110434_K().func_110577_a(field_110429_t);
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		drawTexturedModalRect(var4 + 59, var5 + 20, 0, ySize + (repairContainer.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
		if((repairContainer.getSlot(0).getHasStack() || repairContainer.getSlot(1).getHasStack()) && !repairContainer.getSlot(2).getHasStack())
		{
			drawTexturedModalRect(var4 + 99, var5 + 45, xSize, 0, 28, 21);
		}
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
		fontRenderer.drawString(I18n.func_135053_a("container.repair"), 60, 6, 4210752);
		if(repairContainer.maximumCost > 0)
		{
			int var3 = 8453920;
			boolean var4 = true;
			String var5 = I18n.func_135052_a("container.repair.cost", new Object[] { Integer.valueOf(repairContainer.maximumCost) });
			if(repairContainer.maximumCost >= 40 && !mc.thePlayer.capabilities.isCreativeMode)
			{
				var5 = I18n.func_135053_a("container.repair.expensive");
				var3 = 16736352;
			} else if(!repairContainer.getSlot(2).getHasStack())
			{
				var4 = false;
			} else if(!repairContainer.getSlot(2).canTakeStack(field_82325_q.player))
			{
				var3 = 16736352;
			}
			if(var4)
			{
				int var6 = -16777216 | (var3 & 16579836) >> 2 | var3 & -16777216;
				int var7 = xSize - 8 - fontRenderer.getStringWidth(var5);
				byte var8 = 67;
				if(fontRenderer.getUnicodeFlag())
				{
					drawRect(var7 - 3, var8 - 2, xSize - 7, var8 + 10, -16777216);
					drawRect(var7 - 2, var8 - 1, xSize - 8, var8 + 9, -12895429);
				} else
				{
					fontRenderer.drawString(var5, var7, var8 + 1, var6);
					fontRenderer.drawString(var5, var7 + 1, var8, var6);
					fontRenderer.drawString(var5, var7 + 1, var8 + 1, var6);
				}
				fontRenderer.drawString(var5, var7, var8, var3);
			}
		}
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		GL11.glDisable(GL11.GL_LIGHTING);
		itemNameField.drawTextBox();
	}
	
	private void func_135015_g()
	{
		String var1 = itemNameField.getText();
		Slot var2 = repairContainer.getSlot(0);
		if(var2 != null && var2.getHasStack() && !var2.getStack().hasDisplayName() && var1.equals(var2.getStack().getDisplayName()))
		{
			var1 = "";
		}
		repairContainer.updateItemName(var1);
		mc.thePlayer.sendQueue.addToSendQueue(new Packet250CustomPayload("MC|ItemName", var1.getBytes()));
	}
	
	@Override public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		int var1 = (width - xSize) / 2;
		int var2 = (height - ySize) / 2;
		itemNameField = new GuiTextField(fontRenderer, var1 + 62, var2 + 24, 103, 12);
		itemNameField.setTextColor(-1);
		itemNameField.setDisabledTextColour(-1);
		itemNameField.setEnableBackgroundDrawing(false);
		itemNameField.setMaxStringLength(40);
		inventorySlots.removeCraftingFromCrafters(this);
		inventorySlots.addCraftingToCrafters(this);
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		if(itemNameField.textboxKeyTyped(par1, par2))
		{
			func_135015_g();
		} else
		{
			super.keyTyped(par1, par2);
		}
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		itemNameField.mouseClicked(par1, par2, par3);
	}
	
	@Override public void onGuiClosed()
	{
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
		inventorySlots.removeCraftingFromCrafters(this);
	}
	
	@Override public void sendContainerAndContentsToPlayer(Container par1Container, List par2List)
	{
		sendSlotContents(par1Container, 0, par1Container.getSlot(0).getStack());
	}
	
	@Override public void sendProgressBarUpdate(Container par1Container, int par2, int par3)
	{
	}
	
	@Override public void sendSlotContents(Container par1Container, int par2, ItemStack par3ItemStack)
	{
		if(par2 == 0)
		{
			itemNameField.setText(par3ItemStack == null ? "" : par3ItemStack.getDisplayName());
			itemNameField.setEnabled(par3ItemStack != null);
			if(par3ItemStack != null)
			{
				func_135015_g();
			}
		}
	}
}
