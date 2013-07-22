package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityPotion extends EntityThrowable
{
	private ItemStack potionDamage;
	
	public EntityPotion(World par1World)
	{
		super(par1World);
	}
	
	public EntityPotion(World par1World, double par2, double par4, double par6, int par8)
	{
		this(par1World, par2, par4, par6, new ItemStack(Item.potion, 1, par8));
	}
	
	public EntityPotion(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
	{
		super(par1World, par2, par4, par6);
		potionDamage = par8ItemStack;
	}
	
	public EntityPotion(World par1World, EntityLivingBase par2EntityLivingBase, int par3)
	{
		this(par1World, par2EntityLivingBase, new ItemStack(Item.potion, 1, par3));
	}
	
	public EntityPotion(World par1World, EntityLivingBase par2EntityLivingBase, ItemStack par3ItemStack)
	{
		super(par1World, par2EntityLivingBase);
		potionDamage = par3ItemStack;
	}
	
	@Override protected float func_70182_d()
	{
		return 0.5F;
	}
	
	@Override protected float func_70183_g()
	{
		return -20.0F;
	}
	
	@Override protected float getGravityVelocity()
	{
		return 0.05F;
	}
	
	public int getPotionDamage()
	{
		if(potionDamage == null)
		{
			potionDamage = new ItemStack(Item.potion, 1, 0);
		}
		return potionDamage.getItemDamage();
	}
	
	@Override protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if(!worldObj.isRemote)
		{
			List var2 = Item.potion.getEffects(potionDamage);
			if(var2 != null && !var2.isEmpty())
			{
				AxisAlignedBB var3 = boundingBox.expand(4.0D, 2.0D, 4.0D);
				List var4 = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var3);
				if(var4 != null && !var4.isEmpty())
				{
					Iterator var5 = var4.iterator();
					while(var5.hasNext())
					{
						EntityLivingBase var6 = (EntityLivingBase) var5.next();
						double var7 = getDistanceSqToEntity(var6);
						if(var7 < 16.0D)
						{
							double var9 = 1.0D - Math.sqrt(var7) / 4.0D;
							if(var6 == par1MovingObjectPosition.entityHit)
							{
								var9 = 1.0D;
							}
							Iterator var11 = var2.iterator();
							while(var11.hasNext())
							{
								PotionEffect var12 = (PotionEffect) var11.next();
								int var13 = var12.getPotionID();
								if(Potion.potionTypes[var13].isInstant())
								{
									Potion.potionTypes[var13].affectEntity(getThrower(), var6, var12.getAmplifier(), var9);
								} else
								{
									int var14 = (int) (var9 * var12.getDuration() + 0.5D);
									if(var14 > 20)
									{
										var6.addPotionEffect(new PotionEffect(var13, var14, var12.getAmplifier()));
									}
								}
							}
						}
					}
				}
			}
			worldObj.playAuxSFX(2002, (int) Math.round(posX), (int) Math.round(posY), (int) Math.round(posZ), getPotionDamage());
			setDead();
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		if(par1NBTTagCompound.hasKey("Potion"))
		{
			potionDamage = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("Potion"));
		} else
		{
			setPotionDamage(par1NBTTagCompound.getInteger("potionValue"));
		}
		if(potionDamage == null)
		{
			setDead();
		}
	}
	
	public void setPotionDamage(int par1)
	{
		if(potionDamage == null)
		{
			potionDamage = new ItemStack(Item.potion, 1, 0);
		}
		potionDamage.setItemDamage(par1);
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		if(potionDamage != null)
		{
			par1NBTTagCompound.setCompoundTag("Potion", potionDamage.writeToNBT(new NBTTagCompound()));
		}
	}
}
