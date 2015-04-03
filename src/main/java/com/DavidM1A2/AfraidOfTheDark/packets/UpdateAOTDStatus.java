/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

import com.DavidM1A2.AfraidOfTheDark.playerData.HasStartedAOTD;
import com.DavidM1A2.AfraidOfTheDark.utility.LogHelper;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

// This is an UpdateAOTD packet that is sent to a client or from a client to the server
// that updates the status of if the player has begun the mod
public class UpdateAOTDStatus implements IMessage
{
	private boolean started;

	public UpdateAOTDStatus()
	{
		this.started = false;
	}

	public UpdateAOTDStatus(boolean started)
	{
		this.started = started;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.started = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(started);
	}

	// when we receive a packet we set HasStartedAOTD
	public static class HandlerServer implements IMessageHandler<UpdateAOTDStatus, IMessage>
	{
		@Override
		public IMessage onMessage(UpdateAOTDStatus message, MessageContext ctx)
		{
			LogHelper.info("Update Has Started AOTD Received! Status: " + message.started);
			HasStartedAOTD.set(ctx.getServerHandler().playerEntity, message.started);
			return null;
		}
	}

	// when we receive a packet we set HasStartedAOTD
	public static class HandlerClient implements IMessageHandler<UpdateAOTDStatus, IMessage>
	{
		@Override
		public IMessage onMessage(UpdateAOTDStatus message, MessageContext ctx)
		{
			LogHelper.info("Update Has Started AOTD Received! Status: " + message.started);
			HasStartedAOTD.set(Minecraft.getMinecraft().thePlayer, message.started);
			return null;
		}
	}
}