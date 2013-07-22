package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class ItemRenderer
{
	private static final ResourceLocation field_110930_b = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	private static final ResourceLocation field_110931_c = new ResourceLocation("textures/map/map_background.png");
	private static final ResourceLocation field_110929_d = new ResourceLocation("textures/misc/underwater.png");
	private Minecraft mc;
	private ItemStack itemToRender;
	private float equippedProgress;
	private float prevEquippedProgress;
	private RenderBlocks renderBlocksInstance = new RenderBlocks();
	public final MapItemRenderer mapItemRenderer;
	private int equippedItemSlot = -1;
	
	public ItemRenderer(Minecraft par1Minecraft)
	{
		mc = par1Minecraft;
		mapItemRenderer = new MapItemRenderer(par1Minecraft.gameSettings, par1Minecraft.func_110434_K());
	}
	
	private void renderFireInFirstPerson(float par1)
	{
		Tessellator var2 = Tessellator.instance;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		float var3 = 1.0F;
		for(int var4 = 0; var4 < 2; ++var4)
		{
			GL11.glPushMatrix();
			Icon var5 = Block.fire.func_94438_c(1);
			mc.func_110434_K().func_110577_a(TextureMap.field_110575_b);
			float var6 = var5.getMinU();
			float var7 = var5.getMaxU();
			float var8 = var5.getMinV();
			float var9 = var5.getMaxV();
			float var10 = (0.0F - var3) / 2.0F;
			float var11 = var10 + var3;
			float var12 = 0.0F - var3 / 2.0F;
			float var13 = var12 + var3;
			float var14 = -0.5F;
			GL11.glTranslatef(-(var4 * 2 - 1) * 0.24F, -0.3F, 0.0F);
			GL11.glRotatef((var4 * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
			var2.startDrawingQuads();
			var2.addVertexWithUV(var10, var12, var14, var7, var9);
			var2.addVertexWithUV(var11, var12, var14, var6, var9);
			var2.addVertexWithUV(var11, var13, var14, var6, var8);
			var2.addVertexWithUV(var10, var13, var14, var7, var8);
			var2.draw();
			GL11.glPopMatrix();
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	private void renderInsideOfBlock(float par1, Icon par2Icon)
	{
		mc.func_110434_K().func_110577_a(TextureMap.field_110575_b);
		Tessellator var3 = Tessellator.instance;
		float var4 = 0.1F;
		GL11.glColor4f(var4, var4, var4, 0.5F);
		GL11.glPushMatrix();
		float var5 = -1.0F;
		float var6 = 1.0F;
		float var7 = -1.0F;
		float var8 = 1.0F;
		float var9 = -0.5F;
		float var10 = par2Icon.getMinU();
		float var11 = par2Icon.getMaxU();
		float var12 = par2Icon.getMinV();
		float var13 = par2Icon.getMaxV();
		var3.startDrawingQuads();
		var3.addVertexWithUV(var5, var7, var9, var11, var13);
		var3.addVertexWithUV(var6, var7, var9, var10, var13);
		var3.addVertexWithUV(var6, var8, var9, var10, var12);
		var3.addVertexWithUV(var5, var8, var9, var11, var12);
		var3.draw();
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public void renderItem(EntityLivingBase par1EntityLivingBase, ItemStack par2ItemStack, int par3)
	{
		GL11.glPushMatrix();
		TextureManager var4 = mc.func_110434_K();
		if(par2ItemStack.getItemSpriteNumber() == 0 && par2ItemStack.itemID < Block.blocksList.length && Block.blocksList[par2ItemStack.itemID] != null && RenderBlocks.renderItemIn3d(Block.blocksList[par2ItemStack.itemID].getRenderType()))
		{
			var4.func_110577_a(var4.func_130087_a(0));
			renderBlocksInstance.renderBlockAsItem(Block.blocksList[par2ItemStack.itemID], par2ItemStack.getItemDamage(), 1.0F);
		} else
		{
			Icon var5 = par1EntityLivingBase.getItemIcon(par2ItemStack, par3);
			if(var5 == null)
			{
				GL11.glPopMatrix();
				return;
			}
			var4.func_110577_a(var4.func_130087_a(par2ItemStack.getItemSpriteNumber()));
			Tessellator var6 = Tessellator.instance;
			float var7 = var5.getMinU();
			float var8 = var5.getMaxU();
			float var9 = var5.getMinV();
			float var10 = var5.getMaxV();
			float var11 = 0.0F;
			float var12 = 0.3F;
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glTranslatef(-var11, -var12, 0.0F);
			float var13 = 1.5F;
			GL11.glScalef(var13, var13, var13);
			GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
			renderItemIn2D(var6, var8, var9, var7, var10, var5.getOriginX(), var5.getOriginY(), 0.0625F);
			if(par2ItemStack.hasEffect() && par3 == 0)
			{
				GL11.glDepthFunc(GL11.GL_EQUAL);
				GL11.glDisable(GL11.GL_LIGHTING);
				var4.func_110577_a(field_110930_b);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
				float var14 = 0.76F;
				GL11.glColor4f(0.5F * var14, 0.25F * var14, 0.8F * var14, 1.0F);
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glPushMatrix();
				float var15 = 0.125F;
				GL11.glScalef(var15, var15, var15);
				float var16 = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
				GL11.glTranslatef(var16, 0.0F, 0.0F);
				GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
				renderItemIn2D(var6, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glScalef(var15, var15, var15);
				var16 = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
				GL11.glTranslatef(-var16, 0.0F, 0.0F);
				GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
				renderItemIn2D(var6, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
				GL11.glPopMatrix();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDepthFunc(GL11.GL_LEQUAL);
			}
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
		GL11.glPopMatrix();
	}
	
	public void renderItemInFirstPerson(float par1)
	{
		float var2 = prevEquippedProgress + (equippedProgress - prevEquippedProgress) * par1;
		EntityClientPlayerMP var3 = mc.thePlayer;
		float var4 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * par1;
		GL11.glPushMatrix();
		GL11.glRotatef(var4, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * par1, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
		EntityPlayerSP var5 = var3;
		float var6 = var5.prevRenderArmPitch + (var5.renderArmPitch - var5.prevRenderArmPitch) * par1;
		float var7 = var5.prevRenderArmYaw + (var5.renderArmYaw - var5.prevRenderArmYaw) * par1;
		GL11.glRotatef((var3.rotationPitch - var6) * 0.1F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef((var3.rotationYaw - var7) * 0.1F, 0.0F, 1.0F, 0.0F);
		ItemStack var8 = itemToRender;
		float var9 = mc.theWorld.getLightBrightness(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ));
		var9 = 1.0F;
		int var10 = mc.theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ), 0);
		int var11 = var10 % 65536;
		int var12 = var10 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var11 / 1.0F, var12 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var13;
		float var20;
		float var22;
		if(var8 != null)
		{
			var10 = Item.itemsList[var8.itemID].getColorFromItemStack(var8, 0);
			var20 = (var10 >> 16 & 255) / 255.0F;
			var22 = (var10 >> 8 & 255) / 255.0F;
			var13 = (var10 & 255) / 255.0F;
			GL11.glColor4f(var9 * var20, var9 * var22, var9 * var13, 1.0F);
		} else
		{
			GL11.glColor4f(var9, var9, var9, 1.0F);
		}
		float var14;
		float var15;
		float var16;
		float var21;
		Render var27;
		RenderPlayer var26;
		if(var8 != null && var8.itemID == Item.map.itemID)
		{
			GL11.glPushMatrix();
			var21 = 0.8F;
			var20 = var3.getSwingProgress(par1);
			var22 = MathHelper.sin(var20 * (float) Math.PI);
			var13 = MathHelper.sin(MathHelper.sqrt_float(var20) * (float) Math.PI);
			GL11.glTranslatef(-var13 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(var20) * (float) Math.PI * 2.0F) * 0.2F, -var22 * 0.2F);
			var20 = 1.0F - var4 / 45.0F + 0.1F;
			if(var20 < 0.0F)
			{
				var20 = 0.0F;
			}
			if(var20 > 1.0F)
			{
				var20 = 1.0F;
			}
			var20 = -MathHelper.cos(var20 * (float) Math.PI) * 0.5F + 0.5F;
			GL11.glTranslatef(0.0F, 0.0F * var21 - (1.0F - var2) * 1.2F - var20 * 0.5F + 0.04F, -0.9F * var21);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(var20 * -85.0F, 0.0F, 0.0F, 1.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			mc.func_110434_K().func_110577_a(var3.func_110306_p());
			for(var12 = 0; var12 < 2; ++var12)
			{
				int var24 = var12 * 2 - 1;
				GL11.glPushMatrix();
				GL11.glTranslatef(-0.0F, -0.6F, 1.1F * var24);
				GL11.glRotatef((float) (-45 * var24), 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(59.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef((float) (-65 * var24), 0.0F, 1.0F, 0.0F);
				var27 = RenderManager.instance.getEntityRenderObject(mc.thePlayer);
				var26 = (RenderPlayer) var27;
				var16 = 1.0F;
				GL11.glScalef(var16, var16, var16);
				var26.renderFirstPersonArm(mc.thePlayer);
				GL11.glPopMatrix();
			}
			var22 = var3.getSwingProgress(par1);
			var13 = MathHelper.sin(var22 * var22 * (float) Math.PI);
			var14 = MathHelper.sin(MathHelper.sqrt_float(var22) * (float) Math.PI);
			GL11.glRotatef(-var13 * 20.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-var14 * 20.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-var14 * 80.0F, 1.0F, 0.0F, 0.0F);
			var15 = 0.38F;
			GL11.glScalef(var15, var15, var15);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
			var16 = 0.015625F;
			GL11.glScalef(var16, var16, var16);
			mc.func_110434_K().func_110577_a(field_110931_c);
			Tessellator var30 = Tessellator.instance;
			GL11.glNormal3f(0.0F, 0.0F, -1.0F);
			var30.startDrawingQuads();
			byte var29 = 7;
			var30.addVertexWithUV(0 - var29, 128 + var29, 0.0D, 0.0D, 1.0D);
			var30.addVertexWithUV(128 + var29, 128 + var29, 0.0D, 1.0D, 1.0D);
			var30.addVertexWithUV(128 + var29, 0 - var29, 0.0D, 1.0D, 0.0D);
			var30.addVertexWithUV(0 - var29, 0 - var29, 0.0D, 0.0D, 0.0D);
			var30.draw();
			MapData var19 = Item.map.getMapData(var8, mc.theWorld);
			if(var19 != null)
			{
				mapItemRenderer.renderMap(mc.thePlayer, mc.func_110434_K(), var19);
			}
			GL11.glPopMatrix();
		} else if(var8 != null)
		{
			GL11.glPushMatrix();
			var21 = 0.8F;
			if(var3.getItemInUseCount() > 0)
			{
				EnumAction var23 = var8.getItemUseAction();
				if(var23 == EnumAction.eat || var23 == EnumAction.drink)
				{
					var22 = var3.getItemInUseCount() - par1 + 1.0F;
					var13 = 1.0F - var22 / var8.getMaxItemUseDuration();
					var14 = 1.0F - var13;
					var14 = var14 * var14 * var14;
					var14 = var14 * var14 * var14;
					var14 = var14 * var14 * var14;
					var15 = 1.0F - var14;
					GL11.glTranslatef(0.0F, MathHelper.abs(MathHelper.cos(var22 / 4.0F * (float) Math.PI) * 0.1F) * (var13 > 0.2D ? 1 : 0), 0.0F);
					GL11.glTranslatef(var15 * 0.6F, -var15 * 0.5F, 0.0F);
					GL11.glRotatef(var15 * 90.0F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(var15 * 10.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(var15 * 30.0F, 0.0F, 0.0F, 1.0F);
				}
			} else
			{
				var20 = var3.getSwingProgress(par1);
				var22 = MathHelper.sin(var20 * (float) Math.PI);
				var13 = MathHelper.sin(MathHelper.sqrt_float(var20) * (float) Math.PI);
				GL11.glTranslatef(-var13 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(var20) * (float) Math.PI * 2.0F) * 0.2F, -var22 * 0.2F);
			}
			GL11.glTranslatef(0.7F * var21, -0.65F * var21 - (1.0F - var2) * 0.6F, -0.9F * var21);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			var20 = var3.getSwingProgress(par1);
			var22 = MathHelper.sin(var20 * var20 * (float) Math.PI);
			var13 = MathHelper.sin(MathHelper.sqrt_float(var20) * (float) Math.PI);
			GL11.glRotatef(-var22 * 20.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-var13 * 20.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-var13 * 80.0F, 1.0F, 0.0F, 0.0F);
			var14 = 0.4F;
			GL11.glScalef(var14, var14, var14);
			float var17;
			float var18;
			if(var3.getItemInUseCount() > 0)
			{
				EnumAction var25 = var8.getItemUseAction();
				if(var25 == EnumAction.block)
				{
					GL11.glTranslatef(-0.5F, 0.2F, 0.0F);
					GL11.glRotatef(30.0F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(-80.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
				} else if(var25 == EnumAction.bow)
				{
					GL11.glRotatef(-18.0F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(-12.0F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(-8.0F, 1.0F, 0.0F, 0.0F);
					GL11.glTranslatef(-0.9F, 0.2F, 0.0F);
					var16 = var8.getMaxItemUseDuration() - (var3.getItemInUseCount() - par1 + 1.0F);
					var17 = var16 / 20.0F;
					var17 = (var17 * var17 + var17 * 2.0F) / 3.0F;
					if(var17 > 1.0F)
					{
						var17 = 1.0F;
					}
					if(var17 > 0.1F)
					{
						GL11.glTranslatef(0.0F, MathHelper.sin((var16 - 0.1F) * 1.3F) * 0.01F * (var17 - 0.1F), 0.0F);
					}
					GL11.glTranslatef(0.0F, 0.0F, var17 * 0.1F);
					GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef(0.0F, 0.5F, 0.0F);
					var18 = 1.0F + var17 * 0.2F;
					GL11.glScalef(1.0F, 1.0F, var18);
					GL11.glTranslatef(0.0F, -0.5F, 0.0F);
					GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
				}
			}
			if(var8.getItem().shouldRotateAroundWhenRendering())
			{
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			}
			if(var8.getItem().requiresMultipleRenderPasses())
			{
				renderItem(var3, var8, 0);
				int var28 = Item.itemsList[var8.itemID].getColorFromItemStack(var8, 1);
				var16 = (var28 >> 16 & 255) / 255.0F;
				var17 = (var28 >> 8 & 255) / 255.0F;
				var18 = (var28 & 255) / 255.0F;
				GL11.glColor4f(var9 * var16, var9 * var17, var9 * var18, 1.0F);
				renderItem(var3, var8, 1);
			} else
			{
				renderItem(var3, var8, 0);
			}
			GL11.glPopMatrix();
		} else if(!var3.isInvisible())
		{
			GL11.glPushMatrix();
			var21 = 0.8F;
			var20 = var3.getSwingProgress(par1);
			var22 = MathHelper.sin(var20 * (float) Math.PI);
			var13 = MathHelper.sin(MathHelper.sqrt_float(var20) * (float) Math.PI);
			GL11.glTranslatef(-var13 * 0.3F, MathHelper.sin(MathHelper.sqrt_float(var20) * (float) Math.PI * 2.0F) * 0.4F, -var22 * 0.4F);
			GL11.glTranslatef(0.8F * var21, -0.75F * var21 - (1.0F - var2) * 0.6F, -0.9F * var21);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			var20 = var3.getSwingProgress(par1);
			var22 = MathHelper.sin(var20 * var20 * (float) Math.PI);
			var13 = MathHelper.sin(MathHelper.sqrt_float(var20) * (float) Math.PI);
			GL11.glRotatef(var13 * 70.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-var22 * 20.0F, 0.0F, 0.0F, 1.0F);
			mc.func_110434_K().func_110577_a(var3.func_110306_p());
			GL11.glTranslatef(-1.0F, 3.6F, 3.5F);
			GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			GL11.glTranslatef(5.6F, 0.0F, 0.0F);
			var27 = RenderManager.instance.getEntityRenderObject(mc.thePlayer);
			var26 = (RenderPlayer) var27;
			var16 = 1.0F;
			GL11.glScalef(var16, var16, var16);
			var26.renderFirstPersonArm(mc.thePlayer);
			GL11.glPopMatrix();
		}
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
	}
	
	public void renderOverlays(float par1)
	{
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		if(mc.thePlayer.isBurning())
		{
			renderFireInFirstPerson(par1);
		}
		if(mc.thePlayer.isEntityInsideOpaqueBlock())
		{
			int var2 = MathHelper.floor_double(mc.thePlayer.posX);
			int var3 = MathHelper.floor_double(mc.thePlayer.posY);
			int var4 = MathHelper.floor_double(mc.thePlayer.posZ);
			int var5 = mc.theWorld.getBlockId(var2, var3, var4);
			if(mc.theWorld.isBlockNormalCube(var2, var3, var4))
			{
				renderInsideOfBlock(par1, Block.blocksList[var5].getBlockTextureFromSide(2));
			} else
			{
				for(int var6 = 0; var6 < 8; ++var6)
				{
					float var7 = ((var6 >> 0) % 2 - 0.5F) * mc.thePlayer.width * 0.9F;
					float var8 = ((var6 >> 1) % 2 - 0.5F) * mc.thePlayer.height * 0.2F;
					float var9 = ((var6 >> 2) % 2 - 0.5F) * mc.thePlayer.width * 0.9F;
					int var10 = MathHelper.floor_float(var2 + var7);
					int var11 = MathHelper.floor_float(var3 + var8);
					int var12 = MathHelper.floor_float(var4 + var9);
					if(mc.theWorld.isBlockNormalCube(var10, var11, var12))
					{
						var5 = mc.theWorld.getBlockId(var10, var11, var12);
					}
				}
			}
			if(Block.blocksList[var5] != null)
			{
				renderInsideOfBlock(par1, Block.blocksList[var5].getBlockTextureFromSide(2));
			}
		}
		if(mc.thePlayer.isInsideOfMaterial(Material.water))
		{
			renderWarpedTextureOverlay(par1);
		}
		GL11.glEnable(GL11.GL_ALPHA_TEST);
	}
	
	private void renderWarpedTextureOverlay(float par1)
	{
		mc.func_110434_K().func_110577_a(field_110929_d);
		Tessellator var2 = Tessellator.instance;
		float var3 = mc.thePlayer.getBrightness(par1);
		GL11.glColor4f(var3, var3, var3, 0.5F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glPushMatrix();
		float var4 = 4.0F;
		float var5 = -1.0F;
		float var6 = 1.0F;
		float var7 = -1.0F;
		float var8 = 1.0F;
		float var9 = -0.5F;
		float var10 = -mc.thePlayer.rotationYaw / 64.0F;
		float var11 = mc.thePlayer.rotationPitch / 64.0F;
		var2.startDrawingQuads();
		var2.addVertexWithUV(var5, var7, var9, var4 + var10, var4 + var11);
		var2.addVertexWithUV(var6, var7, var9, 0.0F + var10, var4 + var11);
		var2.addVertexWithUV(var6, var8, var9, 0.0F + var10, 0.0F + var11);
		var2.addVertexWithUV(var5, var8, var9, var4 + var10, 0.0F + var11);
		var2.draw();
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public void resetEquippedProgress()
	{
		equippedProgress = 0.0F;
	}
	
	public void resetEquippedProgress2()
	{
		equippedProgress = 0.0F;
	}
	
	public void updateEquippedItem()
	{
		prevEquippedProgress = equippedProgress;
		EntityClientPlayerMP var1 = mc.thePlayer;
		ItemStack var2 = var1.inventory.getCurrentItem();
		boolean var3 = equippedItemSlot == var1.inventory.currentItem && var2 == itemToRender;
		if(itemToRender == null && var2 == null)
		{
			var3 = true;
		}
		if(var2 != null && itemToRender != null && var2 != itemToRender && var2.itemID == itemToRender.itemID && var2.getItemDamage() == itemToRender.getItemDamage())
		{
			itemToRender = var2;
			var3 = true;
		}
		float var4 = 0.4F;
		float var5 = var3 ? 1.0F : 0.0F;
		float var6 = var5 - equippedProgress;
		if(var6 < -var4)
		{
			var6 = -var4;
		}
		if(var6 > var4)
		{
			var6 = var4;
		}
		equippedProgress += var6;
		if(equippedProgress < 0.1F)
		{
			itemToRender = var2;
			equippedItemSlot = var1.inventory.currentItem;
		}
	}
	
	public static void renderItemIn2D(Tessellator par0Tessellator, float par1, float par2, float par3, float par4, int par5, int par6, float par7)
	{
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(0.0F, 0.0F, 1.0F);
		par0Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, par1, par4);
		par0Tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, par3, par4);
		par0Tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, par3, par2);
		par0Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, par1, par2);
		par0Tessellator.draw();
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(0.0F, 0.0F, -1.0F);
		par0Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0F - par7, par1, par2);
		par0Tessellator.addVertexWithUV(1.0D, 1.0D, 0.0F - par7, par3, par2);
		par0Tessellator.addVertexWithUV(1.0D, 0.0D, 0.0F - par7, par3, par4);
		par0Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0F - par7, par1, par4);
		par0Tessellator.draw();
		float var8 = 0.5F * (par1 - par3) / par5;
		float var9 = 0.5F * (par4 - par2) / par6;
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		int var10;
		float var11;
		float var12;
		for(var10 = 0; var10 < par5; ++var10)
		{
			var11 = (float) var10 / (float) par5;
			var12 = par1 + (par3 - par1) * var11 - var8;
			par0Tessellator.addVertexWithUV(var11, 0.0D, 0.0F - par7, var12, par4);
			par0Tessellator.addVertexWithUV(var11, 0.0D, 0.0D, var12, par4);
			par0Tessellator.addVertexWithUV(var11, 1.0D, 0.0D, var12, par2);
			par0Tessellator.addVertexWithUV(var11, 1.0D, 0.0F - par7, var12, par2);
		}
		par0Tessellator.draw();
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(1.0F, 0.0F, 0.0F);
		float var13;
		for(var10 = 0; var10 < par5; ++var10)
		{
			var11 = (float) var10 / (float) par5;
			var12 = par1 + (par3 - par1) * var11 - var8;
			var13 = var11 + 1.0F / par5;
			par0Tessellator.addVertexWithUV(var13, 1.0D, 0.0F - par7, var12, par2);
			par0Tessellator.addVertexWithUV(var13, 1.0D, 0.0D, var12, par2);
			par0Tessellator.addVertexWithUV(var13, 0.0D, 0.0D, var12, par4);
			par0Tessellator.addVertexWithUV(var13, 0.0D, 0.0F - par7, var12, par4);
		}
		par0Tessellator.draw();
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(0.0F, 1.0F, 0.0F);
		for(var10 = 0; var10 < par6; ++var10)
		{
			var11 = (float) var10 / (float) par6;
			var12 = par4 + (par2 - par4) * var11 - var9;
			var13 = var11 + 1.0F / par6;
			par0Tessellator.addVertexWithUV(0.0D, var13, 0.0D, par1, var12);
			par0Tessellator.addVertexWithUV(1.0D, var13, 0.0D, par3, var12);
			par0Tessellator.addVertexWithUV(1.0D, var13, 0.0F - par7, par3, var12);
			par0Tessellator.addVertexWithUV(0.0D, var13, 0.0F - par7, par1, var12);
		}
		par0Tessellator.draw();
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(0.0F, -1.0F, 0.0F);
		for(var10 = 0; var10 < par6; ++var10)
		{
			var11 = (float) var10 / (float) par6;
			var12 = par4 + (par2 - par4) * var11 - var9;
			par0Tessellator.addVertexWithUV(1.0D, var11, 0.0D, par3, var12);
			par0Tessellator.addVertexWithUV(0.0D, var11, 0.0D, par1, var12);
			par0Tessellator.addVertexWithUV(0.0D, var11, 0.0F - par7, par1, var12);
			par0Tessellator.addVertexWithUV(1.0D, var11, 0.0F - par7, par3, var12);
		}
		par0Tessellator.draw();
	}
}
