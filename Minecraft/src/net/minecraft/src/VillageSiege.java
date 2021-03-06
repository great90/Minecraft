package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class VillageSiege
{
	private World worldObj;
	private boolean field_75535_b;
	private int field_75536_c = -1;
	private int field_75533_d;
	private int field_75534_e;
	private Village theVillage;
	private int field_75532_g;
	private int field_75538_h;
	private int field_75539_i;
	
	public VillageSiege(World par1World)
	{
		worldObj = par1World;
	}
	
	private Vec3 func_75527_a(int par1, int par2, int par3)
	{
		for(int var4 = 0; var4 < 10; ++var4)
		{
			int var5 = par1 + worldObj.rand.nextInt(16) - 8;
			int var6 = par2 + worldObj.rand.nextInt(6) - 3;
			int var7 = par3 + worldObj.rand.nextInt(16) - 8;
			if(theVillage.isInRange(var5, var6, var7) && SpawnerAnimals.canCreatureTypeSpawnAtLocation(EnumCreatureType.monster, worldObj, var5, var6, var7))
			{
				worldObj.getWorldVec3Pool().getVecFromPool(var5, var6, var7);
			}
		}
		return null;
	}
	
	private boolean func_75529_b()
	{
		List var1 = worldObj.playerEntities;
		Iterator var2 = var1.iterator();
		while(var2.hasNext())
		{
			EntityPlayer var3 = (EntityPlayer) var2.next();
			theVillage = worldObj.villageCollectionObj.findNearestVillage((int) var3.posX, (int) var3.posY, (int) var3.posZ, 1);
			if(theVillage != null && theVillage.getNumVillageDoors() >= 10 && theVillage.getTicksSinceLastDoorAdding() >= 20 && theVillage.getNumVillagers() >= 20)
			{
				ChunkCoordinates var4 = theVillage.getCenter();
				float var5 = theVillage.getVillageRadius();
				boolean var6 = false;
				int var7 = 0;
				while(true)
				{
					if(var7 < 10)
					{
						field_75532_g = var4.posX + (int) (MathHelper.cos(worldObj.rand.nextFloat() * (float) Math.PI * 2.0F) * var5 * 0.9D);
						field_75538_h = var4.posY;
						field_75539_i = var4.posZ + (int) (MathHelper.sin(worldObj.rand.nextFloat() * (float) Math.PI * 2.0F) * var5 * 0.9D);
						var6 = false;
						Iterator var8 = worldObj.villageCollectionObj.getVillageList().iterator();
						while(var8.hasNext())
						{
							Village var9 = (Village) var8.next();
							if(var9 != theVillage && var9.isInRange(field_75532_g, field_75538_h, field_75539_i))
							{
								var6 = true;
								break;
							}
						}
						if(var6)
						{
							++var7;
							continue;
						}
					}
					if(var6) return false;
					Vec3 var10 = func_75527_a(field_75532_g, field_75538_h, field_75539_i);
					if(var10 != null)
					{
						field_75534_e = 0;
						field_75533_d = 20;
						return true;
					}
					break;
				}
			}
		}
		return false;
	}
	
	private boolean spawnZombie()
	{
		Vec3 var1 = func_75527_a(field_75532_g, field_75538_h, field_75539_i);
		if(var1 == null) return false;
		else
		{
			EntityZombie var2;
			try
			{
				var2 = new EntityZombie(worldObj);
				var2.func_110161_a((EntityLivingData) null);
				var2.setVillager(false);
			} catch(Exception var4)
			{
				var4.printStackTrace();
				return false;
			}
			var2.setLocationAndAngles(var1.xCoord, var1.yCoord, var1.zCoord, worldObj.rand.nextFloat() * 360.0F, 0.0F);
			worldObj.spawnEntityInWorld(var2);
			ChunkCoordinates var3 = theVillage.getCenter();
			var2.func_110171_b(var3.posX, var3.posY, var3.posZ, theVillage.getVillageRadius());
			return true;
		}
	}
	
	public void tick()
	{
		boolean var1 = false;
		if(var1)
		{
			if(field_75536_c == 2)
			{
				field_75533_d = 100;
				return;
			}
		} else
		{
			if(worldObj.isDaytime())
			{
				field_75536_c = 0;
				return;
			}
			if(field_75536_c == 2) return;
			if(field_75536_c == 0)
			{
				float var2 = worldObj.getCelestialAngle(0.0F);
				if(var2 < 0.5D || var2 > 0.501D) return;
				field_75536_c = worldObj.rand.nextInt(10) == 0 ? 1 : 2;
				field_75535_b = false;
				if(field_75536_c == 2) return;
			}
		}
		if(!field_75535_b)
		{
			if(!func_75529_b()) return;
			field_75535_b = true;
		}
		if(field_75534_e > 0)
		{
			--field_75534_e;
		} else
		{
			field_75534_e = 2;
			if(field_75533_d > 0)
			{
				spawnZombie();
				--field_75533_d;
			} else
			{
				field_75536_c = 2;
			}
		}
	}
}
