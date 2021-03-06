package net.minecraft.src;

public class RenderSilverfish extends RenderLiving
{
	private static final ResourceLocation field_110882_a = new ResourceLocation("textures/entity/silverfish.png");
	
	public RenderSilverfish()
	{
		super(new ModelSilverfish(), 0.3F);
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderSilverfish((EntitySilverfish) par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderSilverfish((EntitySilverfish) par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110881_b((EntitySilverfish) par1Entity);
	}
	
	protected ResourceLocation func_110881_b(EntitySilverfish par1EntitySilverfish)
	{
		return field_110882_a;
	}
	
	@Override protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase)
	{
		return getSilverfishDeathRotation((EntitySilverfish) par1EntityLivingBase);
	}
	
	protected float getSilverfishDeathRotation(EntitySilverfish par1EntitySilverfish)
	{
		return 180.0F;
	}
	
	@Override public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
	{
		renderSilverfish((EntitySilverfish) par1EntityLivingBase, par2, par4, par6, par8, par9);
	}
	
	public void renderSilverfish(EntitySilverfish par1EntitySilverfish, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(par1EntitySilverfish, par2, par4, par6, par8, par9);
	}
	
	@Override protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return shouldSilverfishRenderPass((EntitySilverfish) par1EntityLivingBase, par2, par3);
	}
	
	protected int shouldSilverfishRenderPass(EntitySilverfish par1EntitySilverfish, int par2, float par3)
	{
		return -1;
	}
}
