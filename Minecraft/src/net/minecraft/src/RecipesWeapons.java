package net.minecraft.src;

public class RecipesWeapons
{
	private String[][] recipePatterns = new String[][] { { "X", "X", "#" } };
	private Object[][] recipeItems;
	
	public RecipesWeapons()
	{
		recipeItems = new Object[][] { { Block.planks, Block.cobblestone, Item.ingotIron, Item.diamond, Item.ingotGold }, { Item.swordWood, Item.swordStone, Item.swordIron, Item.swordDiamond, Item.swordGold } };
	}
	
	public void addRecipes(CraftingManager p_77583_1_)
	{
		for(int var2 = 0; var2 < recipeItems[0].length; ++var2)
		{
			Object var3 = recipeItems[0][var2];
			for(int var4 = 0; var4 < recipeItems.length - 1; ++var4)
			{
				Item var5 = (Item) recipeItems[var4 + 1][var2];
				p_77583_1_.addRecipe(new ItemStack(var5), new Object[] { recipePatterns[var4], '#', Item.stick, 'X', var3 });
			}
		}
		p_77583_1_.addRecipe(new ItemStack(Item.bow, 1), new Object[] { " #X", "# X", " #X", 'X', Item.silk, '#', Item.stick });
		p_77583_1_.addRecipe(new ItemStack(Item.arrow, 4), new Object[] { "X", "#", "Y", 'Y', Item.feather, 'X', Item.flint, '#', Item.stick });
	}
}