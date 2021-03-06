package net.minecraft.src;


public class ItemSword extends Item
{
	private float weaponDamage;
	private final EnumToolMaterial toolMaterial;
	
	public ItemSword(int par1, EnumToolMaterial par2EnumToolMaterial)
	{
		super(par1);
		toolMaterial = par2EnumToolMaterial;
		maxStackSize = 1;
		setMaxDamage(par2EnumToolMaterial.getMaxUses());
		setCreativeTab(CreativeTabs.tabCombat);
		weaponDamage = 4.0F + par2EnumToolMaterial.getDamageVsEntity();
	}
	
	@Override public boolean canHarvestBlock(Block par1Block)
	{
		return par1Block.blockID == Block.web.blockID;
	}
	
	@Override public Multimap func_111205_h()
	{
		Multimap var1 = super.func_111205_h();
		var1.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Weapon modifier", weaponDamage, 0));
		return var1;
	}
	
	public float func_82803_g()
	{
		return toolMaterial.getDamageVsEntity();
	}
	
	@Override public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return toolMaterial.getToolCraftingMaterial() == par2ItemStack.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
	
	@Override public int getItemEnchantability()
	{
		return toolMaterial.getEnchantability();
	}
	
	@Override public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.block;
	}
	
	@Override public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 72000;
	}
	
	@Override public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	{
		if(par2Block.blockID == Block.web.blockID) return 15.0F;
		else
		{
			Material var3 = par2Block.blockMaterial;
			return var3 != Material.plants && var3 != Material.vine && var3 != Material.coral && var3 != Material.leaves && var3 != Material.pumpkin ? 1.0F : 1.5F;
		}
	}
	
	public String getToolMaterialName()
	{
		return toolMaterial.toString();
	}
	
	@Override public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		par1ItemStack.damageItem(1, par3EntityLivingBase);
		return true;
	}
	
	@Override public boolean isFull3D()
	{
		return true;
	}
	
	@Override public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
	{
		if(Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0D)
		{
			par1ItemStack.damageItem(2, par7EntityLivingBase);
		}
		return true;
	}
	
	@Override public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}
}
