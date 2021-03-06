package net.minecraft.src;

public class EntityOcelot extends EntityTameable
{
	private EntityAITempt aiTempt;
	
	public EntityOcelot(World par1World)
	{
		super(par1World);
		setSize(0.6F, 0.8F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, aiSit);
		tasks.addTask(3, aiTempt = new EntityAITempt(this, 0.6D, Item.fishRaw.itemID, true));
		tasks.addTask(4, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0F, 0.8D, 1.33D));
		tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
		tasks.addTask(6, new EntityAIOcelotSit(this, 1.33D));
		tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3F));
		tasks.addTask(8, new EntityAIOcelotAttack(this));
		tasks.addTask(9, new EntityAIMate(this, 0.8D));
		tasks.addTask(10, new EntityAIWander(this, 0.8D));
		tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
		targetTasks.addTask(1, new EntityAITargetNonTamed(this, EntityChicken.class, 750, false));
	}
	
	@Override public boolean attackEntityAsMob(Entity par1Entity)
	{
		return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			aiSit.setSitting(false);
			return super.attackEntityFrom(par1DamageSource, par2);
		}
	}
	
	@Override protected boolean canDespawn()
	{
		return !isTamed() && ticksExisted > 2400;
	}
	
	@Override public boolean canMateWith(EntityAnimal par1EntityAnimal)
	{
		if(par1EntityAnimal == this) return false;
		else if(!isTamed()) return false;
		else if(!(par1EntityAnimal instanceof EntityOcelot)) return false;
		else
		{
			EntityOcelot var2 = (EntityOcelot) par1EntityAnimal;
			return !var2.isTamed() ? false : isInLove() && var2.isInLove();
		}
	}
	
	@Override public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
	{
		return spawnBabyAnimal(par1EntityAgeable);
	}
	
	@Override protected void dropFewItems(boolean par1, int par2)
	{
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(18, Byte.valueOf((byte) 0));
	}
	
	@Override protected void fall(float par1)
	{
	}
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
		func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.30000001192092896D);
	}
	
	@Override public EntityLivingData func_110161_a(EntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.func_110161_a(par1EntityLivingData);
		if(worldObj.rand.nextInt(7) == 0)
		{
			for(int var2 = 0; var2 < 2; ++var2)
			{
				EntityOcelot var3 = new EntityOcelot(worldObj);
				var3.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				var3.setGrowingAge(-24000);
				worldObj.spawnEntityInWorld(var3);
			}
		}
		return par1EntityLivingData;
	}
	
	@Override public boolean getCanSpawnHere()
	{
		if(worldObj.rand.nextInt(3) == 0) return false;
		else
		{
			if(worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox))
			{
				int var1 = MathHelper.floor_double(posX);
				int var2 = MathHelper.floor_double(boundingBox.minY);
				int var3 = MathHelper.floor_double(posZ);
				if(var2 < 63) return false;
				int var4 = worldObj.getBlockId(var1, var2 - 1, var3);
				if(var4 == Block.grass.blockID || var4 == Block.leaves.blockID) return true;
			}
			return false;
		}
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.cat.hitt";
	}
	
	@Override protected int getDropItemId()
	{
		return Item.leather.itemID;
	}
	
	@Override public String getEntityName()
	{
		return hasCustomNameTag() ? getCustomNameTag() : isTamed() ? "entity.Cat.name" : super.getEntityName();
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.cat.hitt";
	}
	
	@Override protected String getLivingSound()
	{
		return isTamed() ? isInLove() ? "mob.cat.purr" : rand.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow" : "";
	}
	
	@Override protected float getSoundVolume()
	{
		return 0.4F;
	}
	
	public int getTameSkin()
	{
		return dataWatcher.getWatchableObjectByte(18);
	}
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
		if(isTamed())
		{
			if(par1EntityPlayer.getCommandSenderName().equalsIgnoreCase(getOwnerName()) && !worldObj.isRemote && !isBreedingItem(var2))
			{
				aiSit.setSitting(!isSitting());
			}
		} else if(aiTempt.func_75277_f() && var2 != null && var2.itemID == Item.fishRaw.itemID && par1EntityPlayer.getDistanceSqToEntity(this) < 9.0D)
		{
			if(!par1EntityPlayer.capabilities.isCreativeMode)
			{
				--var2.stackSize;
			}
			if(var2.stackSize <= 0)
			{
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack) null);
			}
			if(!worldObj.isRemote)
			{
				if(rand.nextInt(3) == 0)
				{
					setTamed(true);
					setTameSkin(1 + worldObj.rand.nextInt(3));
					setOwner(par1EntityPlayer.getCommandSenderName());
					playTameEffect(true);
					aiSit.setSitting(true);
					worldObj.setEntityState(this, (byte) 7);
				} else
				{
					playTameEffect(false);
					worldObj.setEntityState(this, (byte) 6);
				}
			}
			return true;
		}
		return super.interact(par1EntityPlayer);
	}
	
	@Override public boolean isAIEnabled()
	{
		return true;
	}
	
	@Override public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return par1ItemStack != null && par1ItemStack.itemID == Item.fishRaw.itemID;
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		setTameSkin(par1NBTTagCompound.getInteger("CatType"));
	}
	
	public void setTameSkin(int par1)
	{
		dataWatcher.updateObject(18, Byte.valueOf((byte) par1));
	}
	
	public EntityOcelot spawnBabyAnimal(EntityAgeable par1EntityAgeable)
	{
		EntityOcelot var2 = new EntityOcelot(worldObj);
		if(isTamed())
		{
			var2.setOwner(getOwnerName());
			var2.setTamed(true);
			var2.setTameSkin(getTameSkin());
		}
		return var2;
	}
	
	@Override public void updateAITick()
	{
		if(getMoveHelper().isUpdating())
		{
			double var1 = getMoveHelper().getSpeed();
			if(var1 == 0.6D)
			{
				setSneaking(true);
				setSprinting(false);
			} else if(var1 == 1.33D)
			{
				setSneaking(false);
				setSprinting(true);
			} else
			{
				setSneaking(false);
				setSprinting(false);
			}
		} else
		{
			setSneaking(false);
			setSprinting(false);
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("CatType", getTameSkin());
	}
}
