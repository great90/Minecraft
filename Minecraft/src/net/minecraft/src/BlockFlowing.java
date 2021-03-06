package net.minecraft.src;

import java.util.Random;

public class BlockFlowing extends BlockFluid
{
	int numAdjacentSources;
	boolean[] isOptimalFlowDirection = new boolean[4];
	int[] flowCost = new int[4];
	
	protected BlockFlowing(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}
	
	private boolean blockBlocksFlow(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockId(par2, par3, par4);
		if(var5 != Block.doorWood.blockID && var5 != Block.doorIron.blockID && var5 != Block.signPost.blockID && var5 != Block.ladder.blockID && var5 != Block.reed.blockID)
		{
			if(var5 == 0) return false;
			else
			{
				Material var6 = Block.blocksList[var5].blockMaterial;
				return var6 == Material.portal ? true : var6.blocksMovement();
			}
		} else return true;
	}
	
	private int calculateFlowCost(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		int var7 = 1000;
		for(int var8 = 0; var8 < 4; ++var8)
		{
			if((var8 != 0 || par6 != 1) && (var8 != 1 || par6 != 0) && (var8 != 2 || par6 != 3) && (var8 != 3 || par6 != 2))
			{
				int var9 = par2;
				int var11 = par4;
				if(var8 == 0)
				{
					var9 = par2 - 1;
				}
				if(var8 == 1)
				{
					++var9;
				}
				if(var8 == 2)
				{
					var11 = par4 - 1;
				}
				if(var8 == 3)
				{
					++var11;
				}
				if(!blockBlocksFlow(par1World, var9, par3, var11) && (par1World.getBlockMaterial(var9, par3, var11) != blockMaterial || par1World.getBlockMetadata(var9, par3, var11) != 0))
				{
					if(!blockBlocksFlow(par1World, var9, par3 - 1, var11)) return par5;
					if(par5 < 4)
					{
						int var12 = calculateFlowCost(par1World, var9, par3, var11, par5 + 1, var8);
						if(var12 < var7)
						{
							var7 = var12;
						}
					}
				}
			}
		}
		return var7;
	}
	
	private void flowIntoBlock(World par1World, int par2, int par3, int par4, int par5)
	{
		if(liquidCanDisplaceBlock(par1World, par2, par3, par4))
		{
			int var6 = par1World.getBlockId(par2, par3, par4);
			if(var6 > 0)
			{
				if(blockMaterial == Material.lava)
				{
					triggerLavaMixEffects(par1World, par2, par3, par4);
				} else
				{
					Block.blocksList[var6].dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
				}
			}
			par1World.setBlock(par2, par3, par4, blockID, par5, 3);
		}
	}
	
	@Override public boolean func_82506_l()
	{
		return true;
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return blockMaterial != Material.lava;
	}
	
	private boolean[] getOptimalFlowDirections(World par1World, int par2, int par3, int par4)
	{
		int var5;
		int var6;
		for(var5 = 0; var5 < 4; ++var5)
		{
			flowCost[var5] = 1000;
			var6 = par2;
			int var8 = par4;
			if(var5 == 0)
			{
				var6 = par2 - 1;
			}
			if(var5 == 1)
			{
				++var6;
			}
			if(var5 == 2)
			{
				var8 = par4 - 1;
			}
			if(var5 == 3)
			{
				++var8;
			}
			if(!blockBlocksFlow(par1World, var6, par3, var8) && (par1World.getBlockMaterial(var6, par3, var8) != blockMaterial || par1World.getBlockMetadata(var6, par3, var8) != 0))
			{
				if(blockBlocksFlow(par1World, var6, par3 - 1, var8))
				{
					flowCost[var5] = calculateFlowCost(par1World, var6, par3, var8, 1, var5);
				} else
				{
					flowCost[var5] = 0;
				}
			}
		}
		var5 = flowCost[0];
		for(var6 = 1; var6 < 4; ++var6)
		{
			if(flowCost[var6] < var5)
			{
				var5 = flowCost[var6];
			}
		}
		for(var6 = 0; var6 < 4; ++var6)
		{
			isOptimalFlowDirection[var6] = flowCost[var6] == var5;
		}
		return isOptimalFlowDirection;
	}
	
	protected int getSmallestFlowDecay(World par1World, int par2, int par3, int par4, int par5)
	{
		int var6 = getFlowDecay(par1World, par2, par3, par4);
		if(var6 < 0) return par5;
		else
		{
			if(var6 == 0)
			{
				++numAdjacentSources;
			}
			if(var6 >= 8)
			{
				var6 = 0;
			}
			return par5 >= 0 && var6 >= par5 ? par5 : var6;
		}
	}
	
	private boolean liquidCanDisplaceBlock(World par1World, int par2, int par3, int par4)
	{
		Material var5 = par1World.getBlockMaterial(par2, par3, par4);
		return var5 == blockMaterial ? false : var5 == Material.lava ? false : !blockBlocksFlow(par1World, par2, par3, par4);
	}
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
		if(par1World.getBlockId(par2, par3, par4) == blockID)
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
		}
	}
	
	private void updateFlow(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		par1World.setBlock(par2, par3, par4, blockID + 1, var5, 2);
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		int var6 = getFlowDecay(par1World, par2, par3, par4);
		byte var7 = 1;
		if(blockMaterial == Material.lava && !par1World.provider.isHellWorld)
		{
			var7 = 2;
		}
		boolean var8 = true;
		int var9 = tickRate(par1World);
		int var11;
		if(var6 > 0)
		{
			byte var10 = -100;
			numAdjacentSources = 0;
			int var13 = getSmallestFlowDecay(par1World, par2 - 1, par3, par4, var10);
			var13 = getSmallestFlowDecay(par1World, par2 + 1, par3, par4, var13);
			var13 = getSmallestFlowDecay(par1World, par2, par3, par4 - 1, var13);
			var13 = getSmallestFlowDecay(par1World, par2, par3, par4 + 1, var13);
			var11 = var13 + var7;
			if(var11 >= 8 || var13 < 0)
			{
				var11 = -1;
			}
			if(getFlowDecay(par1World, par2, par3 + 1, par4) >= 0)
			{
				int var12 = getFlowDecay(par1World, par2, par3 + 1, par4);
				if(var12 >= 8)
				{
					var11 = var12;
				} else
				{
					var11 = var12 + 8;
				}
			}
			if(numAdjacentSources >= 2 && blockMaterial == Material.water)
			{
				if(par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid())
				{
					var11 = 0;
				} else if(par1World.getBlockMaterial(par2, par3 - 1, par4) == blockMaterial && par1World.getBlockMetadata(par2, par3 - 1, par4) == 0)
				{
					var11 = 0;
				}
			}
			if(blockMaterial == Material.lava && var6 < 8 && var11 < 8 && var11 > var6 && par5Random.nextInt(4) != 0)
			{
				var9 *= 4;
			}
			if(var11 == var6)
			{
				if(var8)
				{
					updateFlow(par1World, par2, par3, par4);
				}
			} else
			{
				var6 = var11;
				if(var11 < 0)
				{
					par1World.setBlockToAir(par2, par3, par4);
				} else
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 2);
					par1World.scheduleBlockUpdate(par2, par3, par4, blockID, var9);
					par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
				}
			}
		} else
		{
			updateFlow(par1World, par2, par3, par4);
		}
		if(liquidCanDisplaceBlock(par1World, par2, par3 - 1, par4))
		{
			if(blockMaterial == Material.lava && par1World.getBlockMaterial(par2, par3 - 1, par4) == Material.water)
			{
				par1World.setBlock(par2, par3 - 1, par4, Block.stone.blockID);
				triggerLavaMixEffects(par1World, par2, par3 - 1, par4);
				return;
			}
			if(var6 >= 8)
			{
				flowIntoBlock(par1World, par2, par3 - 1, par4, var6);
			} else
			{
				flowIntoBlock(par1World, par2, par3 - 1, par4, var6 + 8);
			}
		} else if(var6 >= 0 && (var6 == 0 || blockBlocksFlow(par1World, par2, par3 - 1, par4)))
		{
			boolean[] var14 = getOptimalFlowDirections(par1World, par2, par3, par4);
			var11 = var6 + var7;
			if(var6 >= 8)
			{
				var11 = 1;
			}
			if(var11 >= 8) return;
			if(var14[0])
			{
				flowIntoBlock(par1World, par2 - 1, par3, par4, var11);
			}
			if(var14[1])
			{
				flowIntoBlock(par1World, par2 + 1, par3, par4, var11);
			}
			if(var14[2])
			{
				flowIntoBlock(par1World, par2, par3, par4 - 1, var11);
			}
			if(var14[3])
			{
				flowIntoBlock(par1World, par2, par3, par4 + 1, var11);
			}
		}
	}
}
