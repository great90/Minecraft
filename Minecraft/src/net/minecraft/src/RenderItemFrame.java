package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class RenderItemFrame extends Render
{
	private static final ResourceLocation field_110789_a = new ResourceLocation("textures/map/map_background.png");
	private final RenderBlocks renderBlocksInstance = new RenderBlocks();
	private Icon field_94147_f;
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		func_82404_a((EntityItemFrame) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110788_a((EntityItemFrame) par1Entity);
	}
	
	protected ResourceLocation func_110788_a(EntityItemFrame par1EntityItemFrame)
	{
		return null;
	}
	
	private void func_82402_b(EntityItemFrame par1EntityItemFrame)
	{
		ItemStack var2 = par1EntityItemFrame.getDisplayedItem();
		if(var2 != null)
		{
			EntityItem var3 = new EntityItem(par1EntityItemFrame.worldObj, 0.0D, 0.0D, 0.0D, var2);
			var3.getEntityItem().stackSize = 1;
			var3.hoverStart = 0.0F;
			GL11.glPushMatrix();
			GL11.glTranslatef(-0.453125F * Direction.offsetX[par1EntityItemFrame.hangingDirection], -0.18F, -0.453125F * Direction.offsetZ[par1EntityItemFrame.hangingDirection]);
			GL11.glRotatef(180.0F + par1EntityItemFrame.rotationYaw, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef((float) (-90 * par1EntityItemFrame.getRotation()), 0.0F, 0.0F, 1.0F);
			switch(par1EntityItemFrame.getRotation())
			{
				case 1:
					GL11.glTranslatef(-0.16F, -0.16F, 0.0F);
					break;
				case 2:
					GL11.glTranslatef(0.0F, -0.32F, 0.0F);
					break;
				case 3:
					GL11.glTranslatef(0.16F, -0.16F, 0.0F);
			}
			if(var3.getEntityItem().getItem() == Item.map)
			{
				renderManager.renderEngine.func_110577_a(field_110789_a);
				Tessellator var4 = Tessellator.instance;
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
				GL11.glScalef(0.00390625F, 0.00390625F, 0.00390625F);
				GL11.glTranslatef(-65.0F, -107.0F, -3.0F);
				GL11.glNormal3f(0.0F, 0.0F, -1.0F);
				var4.startDrawingQuads();
				byte var5 = 7;
				var4.addVertexWithUV(0 - var5, 128 + var5, 0.0D, 0.0D, 1.0D);
				var4.addVertexWithUV(128 + var5, 128 + var5, 0.0D, 1.0D, 1.0D);
				var4.addVertexWithUV(128 + var5, 0 - var5, 0.0D, 1.0D, 0.0D);
				var4.addVertexWithUV(0 - var5, 0 - var5, 0.0D, 0.0D, 0.0D);
				var4.draw();
				MapData var6 = Item.map.getMapData(var3.getEntityItem(), par1EntityItemFrame.worldObj);
				GL11.glTranslatef(0.0F, 0.0F, -1.0F);
				if(var6 != null)
				{
					renderManager.itemRenderer.mapItemRenderer.renderMap((EntityPlayer) null, renderManager.renderEngine, var6);
				}
			} else
			{
				if(var3.getEntityItem().getItem() == Item.compass)
				{
					TextureManager var11 = Minecraft.getMinecraft().func_110434_K();
					var11.func_110577_a(TextureMap.field_110576_c);
					TextureAtlasSprite var13 = ((TextureMap) var11.func_110581_b(TextureMap.field_110576_c)).func_110572_b(Item.compass.getIconIndex(var3.getEntityItem()).getIconName());
					if(var13 instanceof TextureCompass)
					{
						TextureCompass var14 = (TextureCompass) var13;
						double var7 = var14.currentAngle;
						double var9 = var14.angleDelta;
						var14.currentAngle = 0.0D;
						var14.angleDelta = 0.0D;
						var14.updateCompass(par1EntityItemFrame.worldObj, par1EntityItemFrame.posX, par1EntityItemFrame.posZ, MathHelper.wrapAngleTo180_float(180 + par1EntityItemFrame.hangingDirection * 90), false, true);
						var14.currentAngle = var7;
						var14.angleDelta = var9;
					}
				}
				RenderItem.renderInFrame = true;
				RenderManager.instance.renderEntityWithPosYaw(var3, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				RenderItem.renderInFrame = false;
				if(var3.getEntityItem().getItem() == Item.compass)
				{
					TextureAtlasSprite var12 = ((TextureMap) Minecraft.getMinecraft().func_110434_K().func_110581_b(TextureMap.field_110576_c)).func_110572_b(Item.compass.getIconIndex(var3.getEntityItem()).getIconName());
					if(var12.func_110970_k() > 0)
					{
						var12.updateAnimation();
					}
				}
			}
			GL11.glPopMatrix();
		}
	}
	
	public void func_82404_a(EntityItemFrame par1EntityItemFrame, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		float var10 = (float) (par1EntityItemFrame.posX - par2) - 0.5F;
		float var11 = (float) (par1EntityItemFrame.posY - par4) - 0.5F;
		float var12 = (float) (par1EntityItemFrame.posZ - par6) - 0.5F;
		int var13 = par1EntityItemFrame.xPosition + Direction.offsetX[par1EntityItemFrame.hangingDirection];
		int var14 = par1EntityItemFrame.yPosition;
		int var15 = par1EntityItemFrame.zPosition + Direction.offsetZ[par1EntityItemFrame.hangingDirection];
		GL11.glTranslatef(var13 - var10, var14 - var11, var15 - var12);
		renderFrameItemAsBlock(par1EntityItemFrame);
		func_82402_b(par1EntityItemFrame);
		GL11.glPopMatrix();
	}
	
	private void renderFrameItemAsBlock(EntityItemFrame par1EntityItemFrame)
	{
		GL11.glPushMatrix();
		GL11.glRotatef(par1EntityItemFrame.rotationYaw, 0.0F, 1.0F, 0.0F);
		renderManager.renderEngine.func_110577_a(TextureMap.field_110575_b);
		Block var2 = Block.planks;
		float var3 = 0.0625F;
		float var4 = 0.75F;
		float var5 = var4 / 2.0F;
		GL11.glPushMatrix();
		renderBlocksInstance.overrideBlockBounds(0.0D, 0.5F - var5 + 0.0625F, 0.5F - var5 + 0.0625F, var3 * 0.5F, 0.5F + var5 - 0.0625F, 0.5F + var5 - 0.0625F);
		renderBlocksInstance.setOverrideBlockTexture(field_94147_f);
		renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
		renderBlocksInstance.clearOverrideBlockTexture();
		renderBlocksInstance.unlockBlockBounds();
		GL11.glPopMatrix();
		renderBlocksInstance.setOverrideBlockTexture(Block.planks.getIcon(1, 2));
		GL11.glPushMatrix();
		renderBlocksInstance.overrideBlockBounds(0.0D, 0.5F - var5, 0.5F - var5, var3 + 1.0E-4F, var3 + 0.5F - var5, 0.5F + var5);
		renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		renderBlocksInstance.overrideBlockBounds(0.0D, 0.5F + var5 - var3, 0.5F - var5, var3 + 1.0E-4F, 0.5F + var5, 0.5F + var5);
		renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		renderBlocksInstance.overrideBlockBounds(0.0D, 0.5F - var5, 0.5F - var5, var3, 0.5F + var5, var3 + 0.5F - var5);
		renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		renderBlocksInstance.overrideBlockBounds(0.0D, 0.5F - var5, 0.5F + var5 - var3, var3, 0.5F + var5, 0.5F + var5);
		renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
		GL11.glPopMatrix();
		renderBlocksInstance.unlockBlockBounds();
		renderBlocksInstance.clearOverrideBlockTexture();
		GL11.glPopMatrix();
	}
	
	@Override public void updateIcons(IconRegister par1IconRegister)
	{
		field_94147_f = par1IconRegister.registerIcon("itemframe_background");
	}
}
