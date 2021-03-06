package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet10Flying extends Packet
{
	public double xPosition;
	public double yPosition;
	public double zPosition;
	public double stance;
	public float yaw;
	public float pitch;
	public boolean onGround;
	public boolean moving;
	public boolean rotating;
	
	public Packet10Flying()
	{
	}
	
	public Packet10Flying(boolean par1)
	{
		onGround = par1;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		return true;
	}
	
	@Override public int getPacketSize()
	{
		return 1;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleFlying(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		onGround = par1DataInput.readUnsignedByte() != 0;
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.write(onGround ? 1 : 0);
	}
}
