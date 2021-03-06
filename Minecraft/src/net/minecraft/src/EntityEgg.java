package net.minecraft.src;

public class EntityEgg extends EntityThrowable
{
	public EntityEgg(World par1World)
	{
		super(par1World);
	}
	
	public EntityEgg(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}
	
	public EntityEgg(World par1World, EntityLivingBase par2EntityLivingBase)
	{
		super(par1World, par2EntityLivingBase);
	}
	
	@Override protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if(par1MovingObjectPosition.entityHit != null)
		{
			par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0.0F);
		}
		if(!worldObj.isRemote && rand.nextInt(8) == 0)
		{
			byte var2 = 1;
			if(rand.nextInt(32) == 0)
			{
				var2 = 4;
			}
			for(int var3 = 0; var3 < var2; ++var3)
			{
				EntityChicken var4 = new EntityChicken(worldObj);
				var4.setGrowingAge(-24000);
				var4.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				worldObj.spawnEntityInWorld(var4);
			}
		}
		for(int var5 = 0; var5 < 8; ++var5)
		{
			worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		}
		if(!worldObj.isRemote)
		{
			setDead();
		}
	}
}
