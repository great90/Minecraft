package net.minecraft.src;

public class EnchantmentDamage extends Enchantment
{
	private static final String[] protectionName = new String[] { "all", "undead", "arthropods" };
	private static final int[] baseEnchantability = new int[] { 1, 5, 5 };
	private static final int[] levelEnchantability = new int[] { 11, 8, 8 };
	private static final int[] thresholdEnchantability = new int[] { 20, 20, 20 };
	public final int damageType;
	
	public EnchantmentDamage(int par1, int par2, int par3)
	{
		super(par1, par2, EnumEnchantmentType.weapon);
		damageType = par3;
	}
	
	@Override public float calcModifierLiving(int par1, EntityLivingBase par2EntityLivingBase)
	{
		return damageType == 0 ? par1 * 1.25F : damageType == 1 && par2EntityLivingBase.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD ? par1 * 2.5F : damageType == 2 && par2EntityLivingBase.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD ? par1 * 2.5F : 0.0F;
	}
	
	@Override public boolean canApply(ItemStack par1ItemStack)
	{
		return par1ItemStack.getItem() instanceof ItemAxe ? true : super.canApply(par1ItemStack);
	}
	
	@Override public boolean canApplyTogether(Enchantment par1Enchantment)
	{
		return !(par1Enchantment instanceof EnchantmentDamage);
	}
	
	@Override public int getMaxEnchantability(int par1)
	{
		return getMinEnchantability(par1) + thresholdEnchantability[damageType];
	}
	
	@Override public int getMaxLevel()
	{
		return 5;
	}
	
	@Override public int getMinEnchantability(int par1)
	{
		return baseEnchantability[damageType] + (par1 - 1) * levelEnchantability[damageType];
	}
	
	@Override public String getName()
	{
		return "enchantment.damage." + protectionName[damageType];
	}
}
