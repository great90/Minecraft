package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageField2 extends ComponentVillage
{
	private int averageGroundLevel = -1;
	private int cropTypeA;
	private int cropTypeB;
	
	public ComponentVillageField2(ComponentVillageStartPiece p_i3862_1_, int p_i3862_2_, Random p_i3862_3_, StructureBoundingBox p_i3862_4_, int p_i3862_5_)
	{
		super(p_i3862_1_, p_i3862_2_);
		coordBaseMode = p_i3862_5_;
		boundingBox = p_i3862_4_;
		cropTypeA = pickRandomCrop(p_i3862_3_);
		cropTypeB = pickRandomCrop(p_i3862_3_);
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(averageGroundLevel < 0)
		{
			averageGroundLevel = getAverageGroundLevel(p_74875_1_, p_74875_3_);
			if(averageGroundLevel < 0) return true;
			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 4 - 1, 0);
		}
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 6, 4, 8, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 1, 2, 0, 7, Block.tilledField.blockID, Block.tilledField.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 0, 1, 5, 0, 7, Block.tilledField.blockID, Block.tilledField.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 0, 0, 8, Block.wood.blockID, Block.wood.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 6, 0, 0, 6, 0, 8, Block.wood.blockID, Block.wood.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 0, 5, 0, 0, Block.wood.blockID, Block.wood.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 8, 5, 0, 8, Block.wood.blockID, Block.wood.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 3, 0, 1, 3, 0, 7, Block.waterMoving.blockID, Block.waterMoving.blockID, false);
		int var4;
		for(var4 = 1; var4 <= 7; ++var4)
		{
			placeBlockAtCurrentPosition(p_74875_1_, cropTypeA, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 1, 1, var4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, cropTypeA, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 2, 1, var4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, cropTypeB, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 4, 1, var4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, cropTypeB, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 5, 1, var4, p_74875_3_);
		}
		for(var4 = 0; var4 < 9; ++var4)
		{
			for(int var5 = 0; var5 < 7; ++var5)
			{
				clearCurrentPositionBlocksUpwards(p_74875_1_, var5, 4, var4, p_74875_3_);
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.dirt.blockID, 0, var5, -1, var4, p_74875_3_);
			}
		}
		return true;
	}
	
	private int pickRandomCrop(Random p_82674_1_)
	{
		switch(p_82674_1_.nextInt(5))
		{
			case 0:
				return Block.carrot.blockID;
			case 1:
				return Block.potato.blockID;
			default:
				return Block.crops.blockID;
		}
	}
	
	public static ComponentVillageField2 func_74902_a(ComponentVillageStartPiece p_74902_0_, List p_74902_1_, Random p_74902_2_, int p_74902_3_, int p_74902_4_, int p_74902_5_, int p_74902_6_, int p_74902_7_)
	{
		StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74902_3_, p_74902_4_, p_74902_5_, 0, 0, 0, 7, 4, 9, p_74902_6_);
		return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74902_1_, var8) == null ? new ComponentVillageField2(p_74902_0_, p_74902_7_, p_74902_2_, var8, p_74902_6_) : null;
	}
}