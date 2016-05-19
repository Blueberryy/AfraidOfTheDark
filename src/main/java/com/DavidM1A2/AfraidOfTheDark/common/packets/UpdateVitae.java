/*
 * Author: David Slovikosky 
 * Mod: Afraid of the Dark 
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.packets;

import com.DavidM1A2.AfraidOfTheDark.common.handler.ConfigurationHandler;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModCapabilities;
import com.DavidM1A2.AfraidOfTheDark.common.packets.minersBasicMessageHandler.MessageHandler;
import com.DavidM1A2.AfraidOfTheDark.common.utility.LogHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UpdateVitae extends AbstractEntitySync
{
	private int vitaeLevel = 0;

	public UpdateVitae()
	{
		super();
		this.vitaeLevel = 0;
	}

	public UpdateVitae(int vitaeLevel, Entity entityToUpdate)
	{
		super(entityToUpdate);
		this.vitaeLevel = vitaeLevel;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
		this.vitaeLevel = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		buf.writeInt(this.vitaeLevel);
	}

	// when we receive a packet we set HasStartedAOTD
	public static class Handler extends MessageHandler.Bidirectional<UpdateVitae>
	{
		@Override
		public IMessage handleClientMessage(final EntityPlayer entityPlayer, final UpdateVitae msg, MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					Entity toUpdate = entityPlayer.worldObj.getEntityByID(msg.entityIDToUpdate);
					if (toUpdate != null)
					{
						toUpdate.getCapability(ModCapabilities.ENTITY_DATA, null).setVitaeLevel(msg.vitaeLevel);
					}
				}
			});
			return null;
		}

		@Override
		public IMessage handleServerMessage(final EntityPlayer entityPlayer, final UpdateVitae msg, MessageContext ctx)
		{
			MinecraftServer.getServer().addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					if (ConfigurationHandler.debugMessages)
					{
						LogHelper.info("Update Vitae Status: " + msg.vitaeLevel + " on entity " + entityPlayer.worldObj.getEntityByID(msg.entityIDToUpdate).getName());
					}
					(entityPlayer.worldObj.getEntityByID(msg.entityIDToUpdate)).getCapability(ModCapabilities.ENTITY_DATA, null).setVitaeLevel(msg.vitaeLevel);
				}
			});
			return null;
		}
	}
}
