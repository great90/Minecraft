package net.minecraft.src;

public class GenLayerRiver extends GenLayer
{
	public GenLayerRiver(long p_i3894_1_, GenLayer p_i3894_3_)
	{
		super(p_i3894_1_);
		super.parent = p_i3894_3_;
	}
	
	@Override public int[] getInts(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_)
	{
		int var5 = p_75904_1_ - 1;
		int var6 = p_75904_2_ - 1;
		int var7 = p_75904_3_ + 2;
		int var8 = p_75904_4_ + 2;
		int[] var9 = parent.getInts(var5, var6, var7, var8);
		int[] var10 = IntCache.getIntCache(p_75904_3_ * p_75904_4_);
		for(int var11 = 0; var11 < p_75904_4_; ++var11)
		{
			for(int var12 = 0; var12 < p_75904_3_; ++var12)
			{
				int var13 = var9[var12 + 0 + (var11 + 1) * var7];
				int var14 = var9[var12 + 2 + (var11 + 1) * var7];
				int var15 = var9[var12 + 1 + (var11 + 0) * var7];
				int var16 = var9[var12 + 1 + (var11 + 2) * var7];
				int var17 = var9[var12 + 1 + (var11 + 1) * var7];
				if(var17 != 0 && var13 != 0 && var14 != 0 && var15 != 0 && var16 != 0 && var17 == var13 && var17 == var15 && var17 == var14 && var17 == var16)
				{
					var10[var12 + var11 * p_75904_3_] = -1;
				} else
				{
					var10[var12 + var11 * p_75904_3_] = BiomeGenBase.river.biomeID;
				}
			}
		}
		return var10;
	}
}