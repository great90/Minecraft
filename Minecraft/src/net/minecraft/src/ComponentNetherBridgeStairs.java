package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentNetherBridgeStairs extends ComponentNetherBridgePiece
{
	public ComponentNetherBridgeStairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
	{
		super(par1);
		coordBaseMode = par4;
		boundingBox = par3StructureBoundingBox;
	}
	
	@Override public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
	{
		fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 6, 1, 6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 0, 6, 10, 6, 0, 0, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 0, 1, 8, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 5, 2, 0, 6, 8, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 1, 0, 8, 6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 6, 2, 1, 6, 8, 6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 1, 2, 6, 5, 8, 6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, 2, 0, 5, 4, Block.netherFence.blockID, Block.netherFence.blockID, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 6, 3, 2, 6, 5, 2, Block.netherFence.blockID, Block.netherFence.blockID, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 6, 3, 4, 6, 5, 4, Block.netherFence.blockID, Block.netherFence.blockID, false);
		placeBlockAtCurrentPosition(par1World, Block.netherBrick.blockID, 0, 5, 2, 5, par3StructureBoundingBox);
		fillWithBlocks(par1World, par3StructureBoundingBox, 4, 2, 5, 4, 3, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 3, 2, 5, 3, 4, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 2, 2, 5, 2, 5, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 1, 2, 5, 1, 6, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 1, 7, 1, 5, 7, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 6, 8, 2, 6, 8, 4, 0, 0, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 2, 6, 0, 4, 8, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 5, 0, Block.netherFence.blockID, Block.netherFence.blockID, false);
		for(int var4 = 0; var4 <= 6; ++var4)
		{
			for(int var5 = 0; var5 <= 6; ++var5)
			{
				fillCurrentPositionBlocksDownwards(par1World, Block.netherBrick.blockID, 0, var4, -1, var5, par3StructureBoundingBox);
			}
		}
		return true;
	}
	
	@Override public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
	{
		getNextComponentZ((ComponentNetherBridgeStartPiece) par1StructureComponent, par2List, par3Random, 6, 2, false);
	}
	
	public static ComponentNetherBridgeStairs createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -2, 0, 0, 7, 11, 7, par5);
		return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new ComponentNetherBridgeStairs(par6, par1Random, var7, par5) : null;
	}
}
