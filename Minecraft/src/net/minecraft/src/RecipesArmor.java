package net.minecraft.src;

public class RecipesArmor
{
	private String[][] recipePatterns = new String[][] { { "XXX", "X X" }, { "X X", "XXX", "XXX" }, { "XXX", "X X", "X X" }, { "X X", "X X" } };
	private Object[][] recipeItems;
	
	public RecipesArmor()
	{
		recipeItems = new Object[][] { { Item.leather, Block.fire, Item.ingotIron, Item.diamond, Item.ingotGold }, { Item.helmetLeather, Item.helmetChain, Item.helmetIron, Item.helmetDiamond, Item.helmetGold }, { Item.plateLeather, Item.plateChain, Item.plateIron, Item.plateDiamond, Item.plateGold }, { Item.legsLeather, Item.legsChain, Item.legsIron, Item.legsDiamond, Item.legsGold }, { Item.bootsLeather, Item.bootsChain, Item.bootsIron, Item.bootsDiamond, Item.bootsGold } };
	}
	
	public void addRecipes(CraftingManager par1CraftingManager)
	{
		for(int var2 = 0; var2 < recipeItems[0].length; ++var2)
		{
			Object var3 = recipeItems[0][var2];
			for(int var4 = 0; var4 < recipeItems.length - 1; ++var4)
			{
				Item var5 = (Item) recipeItems[var4 + 1][var2];
				par1CraftingManager.addRecipe(new ItemStack(var5), new Object[] { recipePatterns[var4], 'X', var3 });
			}
		}
	}
}
