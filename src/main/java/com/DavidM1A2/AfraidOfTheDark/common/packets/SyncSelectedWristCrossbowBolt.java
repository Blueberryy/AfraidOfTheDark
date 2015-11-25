/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.packets;

import com.DavidM1A2.AfraidOfTheDark.common.packets.minersBasicMessageHandler.MessageHandler;
import com.DavidM1A2.AfraidOfTheDark.common.savedData.AOTDPlayerData;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncSelectedWristCrossbowBolt implements IMessage
{
	private int type;

	public SyncSelectedWristCrossbowBolt()
	{
		this.type = 0;
	}

	public SyncSelectedWristCrossbowBolt(final int type)
	{
		this.type = type;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.type = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.type);
	}

	// when we receive a packet we set HasStartedAOTD
	public static class Handler extends MessageHandler.Server<SyncSelectedWristCrossbowBolt>
	{
		@Override
		public IMessage handleServerMessage(final EntityPlayer entityPlayer, final SyncSelectedWristCrossbowBolt msg, MessageContext ctx)
		{
			MinecraftServer.getServer().addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					AOTDPlayerData.get(entityPlayer).setSelectedWristCrossbowBolt(msg.type);
				}
			});

			return null;
		}
	}
}
