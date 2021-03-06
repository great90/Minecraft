package net.minecraft.src;

import java.io.IOException;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import net.minecraft.client.Minecraft;

public class GuiScreenSubscription extends GuiScreen
{
	private final GuiScreen field_98067_a;
	private final McoServer field_98065_b;
	private final int field_98066_c = 0;
	private final int field_98064_d = 1;
	private int field_98068_n;
	private String field_98069_o;
	
	public GuiScreenSubscription(GuiScreen par1GuiScreen, McoServer par2McoServer)
	{
		field_98067_a = par1GuiScreen;
		field_98065_b = par2McoServer;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 0)
			{
				mc.displayGuiScreen(field_98067_a);
			} else if(par1GuiButton.id == 1)
			{
				;
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, I18n.func_135053_a("mco.configure.world.subscription.title"), width / 2, 17, 16777215);
		drawString(fontRenderer, I18n.func_135053_a("mco.configure.world.subscription.start"), width / 2 - 100, 53, 10526880);
		drawString(fontRenderer, field_98069_o, width / 2 - 100, 66, 16777215);
		drawString(fontRenderer, I18n.func_135053_a("mco.configure.world.subscription.daysleft"), width / 2 - 100, 85, 10526880);
		drawString(fontRenderer, String.valueOf(field_98068_n), width / 2 - 100, 98, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	private String func_98062_b(long par1)
	{
		GregorianCalendar var3 = new GregorianCalendar(TimeZone.getDefault());
		var3.setTimeInMillis(par1);
		return DateFormat.getDateTimeInstance().format(var3.getTime());
	}
	
	private void func_98063_a(long par1)
	{
		McoClient var3 = new McoClient(mc.func_110432_I());
		try
		{
			ValueObjectSubscription var4 = var3.func_98177_f(par1);
			field_98068_n = var4.field_98170_b;
			field_98069_o = func_98062_b(var4.field_98171_a);
		} catch(ExceptionMcoService var5)
		{
			Minecraft.getMinecraft().getLogAgent().logSevere(var5.toString());
		} catch(IOException var6)
		{
			Minecraft.getMinecraft().getLogAgent().logWarning("Realms: could not parse response");
		}
	}
	
	@Override public void initGui()
	{
		func_98063_a(field_98065_b.field_96408_a);
		Keyboard.enableRepeatEvents(true);
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, I18n.func_135053_a("gui.cancel")));
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override public void updateScreen()
	{
	}
}
