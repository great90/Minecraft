package net.minecraft.src;


public class RenderSnowMan extends RenderLiving
{
	private static final ResourceLocation field_110895_a = new ResourceLocation("textures/entity/snowman.png");
	private ModelSnowMan snowmanModel;
	
	public RenderSnowMan()
	{
		super(new ModelSnowMan(), 0.5F);
		snowmanModel = (ModelSnowMan) super.mainModel;
		setRenderPassModel(snowmanModel);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110894_a((EntitySnowman) par1Entity);
	}
	
	protected ResourceLocation func_110894_a(EntitySnowman par1EntitySnowman)
	{
		return field_110895_a;
	}
	
	@Override protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
	{
		renderSnowmanPumpkin((EntitySnowman) par1EntityLivingBase, par2);
	}
	
	protected void renderSnowmanPumpkin(EntitySnowman par1EntitySnowman, float par2)
	{
		super.renderEquippedItems(par1EntitySnowman, par2);
		ItemStack var3 = new ItemStack(Block.pumpkin, 1);
		if(var3 != null && var3.getItem().itemID < 256)
		{
			GL11.glPushMatrix();
			snowmanModel.head.postRender(0.0625F);
			if(RenderBlocks.renderItemIn3d(Block.blocksList[var3.itemID].getRenderType()))
			{
				float var4 = 0.625F;
				GL11.glTranslatef(0.0F, -0.34375F, 0.0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(var4, -var4, var4);
			}
			renderManager.itemRenderer.renderItem(par1EntitySnowman, var3, 0);
			GL11.glPopMatrix();
		}
	}
}
