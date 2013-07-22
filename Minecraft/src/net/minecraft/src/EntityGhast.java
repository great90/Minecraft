package net.minecraft.src;

public class EntityGhast extends EntityFlying implements IMob
{
	public int courseChangeCooldown = 0;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	private Entity targetedEntity = null;
	private int aggroCooldown = 0;
	public int prevAttackCounter = 0;
	public int attackCounter = 0;
	private int explosionStrength = 1;
	
	public EntityGhast(World p_i3549_1_)
	{
		super(p_i3549_1_);
		texture = "/mob/ghast.png";
		setSize(4.0F, 4.0F);
		isImmuneToFire = true;
		experienceValue = 5;
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		if(isEntityInvulnerable()) return false;
		else if("fireball".equals(p_70097_1_.getDamageType()) && p_70097_1_.getEntity() instanceof EntityPlayer)
		{
			super.attackEntityFrom(p_70097_1_, 1000);
			((EntityPlayer) p_70097_1_.getEntity()).triggerAchievement(AchievementList.ghast);
			return true;
		} else return super.attackEntityFrom(p_70097_1_, p_70097_2_);
	}
	
	@Override protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int var3 = rand.nextInt(2) + rand.nextInt(1 + p_70628_2_);
		int var4;
		for(var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Item.ghastTear.itemID, 1);
		}
		var3 = rand.nextInt(3) + rand.nextInt(1 + p_70628_2_);
		for(var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Item.gunpowder.itemID, 1);
		}
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}
	
	@Override public boolean getCanSpawnHere()
	{
		return rand.nextInt(20) == 0 && super.getCanSpawnHere() && worldObj.difficultySetting > 0;
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.ghast.death";
	}
	
	@Override protected int getDropItemId()
	{
		return Item.gunpowder.itemID;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.ghast.scream";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.ghast.moan";
	}
	
	@Override public int getMaxHealth()
	{
		return 10;
	}
	
	@Override public int getMaxSpawnedInChunk()
	{
		return 1;
	}
	
	@Override protected float getSoundVolume()
	{
		return 10.0F;
	}
	
	private boolean isCourseTraversable(double p_70790_1_, double p_70790_3_, double p_70790_5_, double p_70790_7_)
	{
		double var9 = (waypointX - posX) / p_70790_7_;
		double var11 = (waypointY - posY) / p_70790_7_;
		double var13 = (waypointZ - posZ) / p_70790_7_;
		AxisAlignedBB var15 = boundingBox.copy();
		for(int var16 = 1; var16 < p_70790_7_; ++var16)
		{
			var15.offset(var9, var11, var13);
			if(!worldObj.getCollidingBoundingBoxes(this, var15).isEmpty()) return false;
		}
		return true;
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		byte var1 = dataWatcher.getWatchableObjectByte(16);
		texture = var1 == 1 ? "/mob/ghast_fire.png" : "/mob/ghast.png";
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		if(p_70037_1_.hasKey("ExplosionPower"))
		{
			explosionStrength = p_70037_1_.getInteger("ExplosionPower");
		}
	}
	
	@Override protected void updateEntityActionState()
	{
		if(!worldObj.isRemote && worldObj.difficultySetting == 0)
		{
			setDead();
		}
		despawnEntity();
		prevAttackCounter = attackCounter;
		double var1 = waypointX - posX;
		double var3 = waypointY - posY;
		double var5 = waypointZ - posZ;
		double var7 = var1 * var1 + var3 * var3 + var5 * var5;
		if(var7 < 1.0D || var7 > 3600.0D)
		{
			waypointX = posX + (rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
			waypointY = posY + (rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
			waypointZ = posZ + (rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
		}
		if(courseChangeCooldown-- <= 0)
		{
			courseChangeCooldown += rand.nextInt(5) + 2;
			var7 = MathHelper.sqrt_double(var7);
			if(isCourseTraversable(waypointX, waypointY, waypointZ, var7))
			{
				motionX += var1 / var7 * 0.1D;
				motionY += var3 / var7 * 0.1D;
				motionZ += var5 / var7 * 0.1D;
			} else
			{
				waypointX = posX;
				waypointY = posY;
				waypointZ = posZ;
			}
		}
		if(targetedEntity != null && targetedEntity.isDead)
		{
			targetedEntity = null;
		}
		if(targetedEntity == null || aggroCooldown-- <= 0)
		{
			targetedEntity = worldObj.getClosestVulnerablePlayerToEntity(this, 100.0D);
			if(targetedEntity != null)
			{
				aggroCooldown = 20;
			}
		}
		double var9 = 64.0D;
		if(targetedEntity != null && targetedEntity.getDistanceSqToEntity(this) < var9 * var9)
		{
			double var11 = targetedEntity.posX - posX;
			double var13 = targetedEntity.boundingBox.minY + targetedEntity.height / 2.0F - (posY + height / 2.0F);
			double var15 = targetedEntity.posZ - posZ;
			renderYawOffset = rotationYaw = -((float) Math.atan2(var11, var15)) * 180.0F / (float) Math.PI;
			if(canEntityBeSeen(targetedEntity))
			{
				if(attackCounter == 10)
				{
					worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1007, (int) posX, (int) posY, (int) posZ, 0);
				}
				++attackCounter;
				if(attackCounter == 20)
				{
					worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1008, (int) posX, (int) posY, (int) posZ, 0);
					EntityLargeFireball var17 = new EntityLargeFireball(worldObj, this, var11, var13, var15);
					var17.field_92057_e = explosionStrength;
					double var18 = 4.0D;
					Vec3 var20 = getLook(1.0F);
					var17.posX = posX + var20.xCoord * var18;
					var17.posY = posY + height / 2.0F + 0.5D;
					var17.posZ = posZ + var20.zCoord * var18;
					worldObj.spawnEntityInWorld(var17);
					attackCounter = -40;
				}
			} else if(attackCounter > 0)
			{
				--attackCounter;
			}
		} else
		{
			renderYawOffset = rotationYaw = -((float) Math.atan2(motionX, motionZ)) * 180.0F / (float) Math.PI;
			if(attackCounter > 0)
			{
				--attackCounter;
			}
		}
		if(!worldObj.isRemote)
		{
			byte var21 = dataWatcher.getWatchableObjectByte(16);
			byte var12 = (byte) (attackCounter > 10 ? 1 : 0);
			if(var21 != var12)
			{
				dataWatcher.updateObject(16, Byte.valueOf(var12));
			}
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setInteger("ExplosionPower", explosionStrength);
	}
}