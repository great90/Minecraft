package net.minecraft.src;


public class RenderMooshroom extends RenderLiving
{
	public RenderMooshroom(ModelBase p_i3207_1_, float p_i3207_2_)
	{
		super(p_i3207_1_, p_i3207_2_);
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderLivingMooshroom((EntityMooshroom) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderLivingMooshroom((EntityMooshroom) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	@Override protected void renderEquippedItems(EntityLiving par1EntityLivingBase, float par2)
	{
		renderMooshroomEquippedItems((EntityMooshroom) par1EntityLivingBase, par2);
	}
	
	public void renderLivingMooshroom(EntityMooshroom par1EntityMooshroom, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(par1EntityMooshroom, par2, par4, par6, par8, par9);
	}
	
	protected void renderMooshroomEquippedItems(EntityMooshroom par1EntityMooshroom, float par2)
	{
		super.renderEquippedItems(par1EntityMooshroom, par2);
		if(!par1EntityMooshroom.isChild())
		{
			loadTexture("/terrain.png");
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glPushMatrix();
			GL11.glScalef(1.0F, -1.0F, 1.0F);
			GL11.glTranslatef(0.2F, 0.4F, 0.5F);
			GL11.glRotatef(42.0F, 0.0F, 1.0F, 0.0F);
			renderBlocks.renderBlockAsItem(Block.mushroomRed, 0, 1.0F);
			GL11.glTranslatef(0.1F, 0.0F, -0.6F);
			GL11.glRotatef(42.0F, 0.0F, 1.0F, 0.0F);
			renderBlocks.renderBlockAsItem(Block.mushroomRed, 0, 1.0F);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			((ModelQuadruped) mainModel).head.postRender(0.0625F);
			GL11.glScalef(1.0F, -1.0F, 1.0F);
			GL11.glTranslatef(0.0F, 0.75F, -0.2F);
			GL11.glRotatef(12.0F, 0.0F, 1.0F, 0.0F);
			renderBlocks.renderBlockAsItem(Block.mushroomRed, 0, 1.0F);
			GL11.glPopMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
		}
	}
}