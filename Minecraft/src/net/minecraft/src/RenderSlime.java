package net.minecraft.src;


public class RenderSlime extends RenderLiving
{
	private static final ResourceLocation field_110897_a = new ResourceLocation("textures/entity/slime/slime.png");
	private ModelBase scaleAmount;
	
	public RenderSlime(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase, par3);
		scaleAmount = par2ModelBase;
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110896_a((EntitySlime) par1Entity);
	}
	
	protected ResourceLocation func_110896_a(EntitySlime par1EntitySlime)
	{
		return field_110897_a;
	}
	
	@Override protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		scaleSlime((EntitySlime) par1EntityLivingBase, par2);
	}
	
	protected void scaleSlime(EntitySlime par1EntitySlime, float par2)
	{
		float var3 = par1EntitySlime.getSlimeSize();
		float var4 = (par1EntitySlime.field_70812_c + (par1EntitySlime.field_70811_b - par1EntitySlime.field_70812_c) * par2) / (var3 * 0.5F + 1.0F);
		float var5 = 1.0F / (var4 + 1.0F);
		GL11.glScalef(var5 * var3, 1.0F / var5 * var3, var5 * var3);
	}
	
	@Override protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return shouldSlimeRenderPass((EntitySlime) par1EntityLivingBase, par2, par3);
	}
	
	protected int shouldSlimeRenderPass(EntitySlime par1EntitySlime, int par2, float par3)
	{
		if(par1EntitySlime.isInvisible()) return 0;
		else if(par2 == 0)
		{
			setRenderPassModel(scaleAmount);
			GL11.glEnable(GL11.GL_NORMALIZE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			return 1;
		} else
		{
			if(par2 == 1)
			{
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
			return -1;
		}
	}
}
