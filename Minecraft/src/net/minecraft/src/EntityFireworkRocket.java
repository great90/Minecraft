package net.minecraft.src;

public class EntityFireworkRocket extends Entity
{
	private int fireworkAge;
	private int lifetime;
	
	public EntityFireworkRocket(World par1World)
	{
		super(par1World);
		setSize(0.25F, 0.25F);
	}
	
	public EntityFireworkRocket(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
	{
		super(par1World);
		fireworkAge = 0;
		setSize(0.25F, 0.25F);
		setPosition(par2, par4, par6);
		yOffset = 0.0F;
		int var9 = 1;
		if(par8ItemStack != null && par8ItemStack.hasTagCompound())
		{
			dataWatcher.updateObject(8, par8ItemStack);
			NBTTagCompound var10 = par8ItemStack.getTagCompound();
			NBTTagCompound var11 = var10.getCompoundTag("Fireworks");
			if(var11 != null)
			{
				var9 += var11.getByte("Flight");
			}
		}
		motionX = rand.nextGaussian() * 0.001D;
		motionZ = rand.nextGaussian() * 0.001D;
		motionY = 0.05D;
		lifetime = 10 * var9 + rand.nextInt(6) + rand.nextInt(7);
	}
	
	@Override public boolean canAttackWithItem()
	{
		return false;
	}
	
	@Override protected void entityInit()
	{
		dataWatcher.addObjectByDataType(8, 5);
	}
	
	@Override public float getBrightness(float par1)
	{
		return super.getBrightness(par1);
	}
	
	@Override public int getBrightnessForRender(float par1)
	{
		return super.getBrightnessForRender(par1);
	}
	
	@Override public float getShadowSize()
	{
		return 0.0F;
	}
	
	@Override public void handleHealthUpdate(byte par1)
	{
		if(par1 == 17 && worldObj.isRemote)
		{
			ItemStack var2 = dataWatcher.getWatchableObjectItemStack(8);
			NBTTagCompound var3 = null;
			if(var2 != null && var2.hasTagCompound())
			{
				var3 = var2.getTagCompound().getCompoundTag("Fireworks");
			}
			worldObj.func_92088_a(posX, posY, posZ, motionX, motionY, motionZ, var3);
		}
		super.handleHealthUpdate(par1);
	}
	
	@Override public boolean isInRangeToRenderDist(double par1)
	{
		return par1 < 4096.0D;
	}
	
	@Override public void onUpdate()
	{
		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;
		super.onUpdate();
		motionX *= 1.15D;
		motionZ *= 1.15D;
		motionY += 0.04D;
		moveEntity(motionX, motionY, motionZ);
		float var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
		for(rotationPitch = (float) (Math.atan2(motionY, var1) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
		{
			;
		}
		while(rotationPitch - prevRotationPitch >= 180.0F)
		{
			prevRotationPitch += 360.0F;
		}
		while(rotationYaw - prevRotationYaw < -180.0F)
		{
			prevRotationYaw -= 360.0F;
		}
		while(rotationYaw - prevRotationYaw >= 180.0F)
		{
			prevRotationYaw += 360.0F;
		}
		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
		if(fireworkAge == 0)
		{
			worldObj.playSoundAtEntity(this, "fireworks.launch", 3.0F, 1.0F);
		}
		++fireworkAge;
		if(worldObj.isRemote && fireworkAge % 2 < 2)
		{
			worldObj.spawnParticle("fireworksSpark", posX, posY - 0.3D, posZ, rand.nextGaussian() * 0.05D, -motionY * 0.5D, rand.nextGaussian() * 0.05D);
		}
		if(!worldObj.isRemote && fireworkAge > lifetime)
		{
			worldObj.setEntityState(this, (byte) 17);
			setDead();
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		fireworkAge = par1NBTTagCompound.getInteger("Life");
		lifetime = par1NBTTagCompound.getInteger("LifeTime");
		NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("FireworksItem");
		if(var2 != null)
		{
			ItemStack var3 = ItemStack.loadItemStackFromNBT(var2);
			if(var3 != null)
			{
				dataWatcher.updateObject(8, var3);
			}
		}
	}
	
	@Override public void setVelocity(double par1, double par3, double par5)
	{
		motionX = par1;
		motionY = par3;
		motionZ = par5;
		if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(par3, var7) * 180.0D / Math.PI);
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setInteger("Life", fireworkAge);
		par1NBTTagCompound.setInteger("LifeTime", lifetime);
		ItemStack var2 = dataWatcher.getWatchableObjectItemStack(8);
		if(var2 != null)
		{
			NBTTagCompound var3 = new NBTTagCompound();
			var2.writeToNBT(var3);
			par1NBTTagCompound.setCompoundTag("FireworksItem", var3);
		}
	}
}
