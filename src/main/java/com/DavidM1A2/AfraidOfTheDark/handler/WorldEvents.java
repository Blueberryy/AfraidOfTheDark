package com.DavidM1A2.AfraidOfTheDark.handler;

/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.DavidM1A2.AfraidOfTheDark.entities.WereWolf.EntityWereWolf;
import com.DavidM1A2.AfraidOfTheDark.playerData.HasStartedAOTD;

public class WorldEvents
{
	private boolean readyToSpawnWerewolves = true;

	// At midnight, spawn werewolves
	@SubscribeEvent
	public void midnightWerewolves(final WorldEvent event)
	{
		final World currentWorld = event.world;
		// Only in the overworld
		if (currentWorld.provider.getDimensionId() == 0)
		{
			// Full moon only
			if (currentWorld.getCurrentMoonPhaseFactor() == 1.0F)
			{
				// Current world time is total world time - (24,000 ticks * number of days passed)
				final long daysPassed = currentWorld.getWorldTime() / 24000L;
				final long worldTime = currentWorld.getWorldInfo().getWorldTime() - (24000L * daysPassed);
				// If it is 12oClock
				if ((worldTime > 16500) && (worldTime < 16700))
				{
					// If we are ready to spawn werewolves
					if (this.readyToSpawnWerewolves)
					{
						// Set werewolf attributes and update each already spawned werewolf
						EntityWereWolf.setMoveSpeedAndAgroRange(.51, 80.0D, 120.0D);
						for (final Object entity : currentWorld.loadedEntityList)
						{
							if (entity instanceof EntityWereWolf)
							{
								final EntityWereWolf myWolf = (EntityWereWolf) entity;
								myWolf.setWanderer();
								myWolf.setMyWatchClosest();
							}
						}

						// Tell each player that has started AOTD that it's "the night"
						final List<EntityPlayer> players = currentWorld.playerEntities;
						for (final EntityPlayer entityPlayer : players)
						{
							if (HasStartedAOTD.get(entityPlayer))
							{
								entityPlayer.addChatMessage(new ChatComponentText("�4�oSomething �4�ofeels �4�odifferent �4�otonight..."));
							}
						}
						this.readyToSpawnWerewolves = false;
					}
				}
				// If the world time is between 23500 and 23700 (dawn) reset werewolf attributes and update each werewolf
				else if ((worldTime > 23500) && (worldTime < 23700))
				{
					if (!this.readyToSpawnWerewolves)
					{
						EntityWereWolf.setMoveSpeedAndAgroRange(.38, 16.0D, 32.0D);
						for (final Object entity : currentWorld.loadedEntityList)
						{
							if (entity instanceof EntityWereWolf)
							{
								final EntityWereWolf myWolf = (EntityWereWolf) entity;
								myWolf.setWanderer();
								myWolf.setMyWatchClosest();
							}
						}

						// Tell each player that the event is over
						final List<EntityPlayer> players = currentWorld.playerEntities;
						for (final EntityPlayer entityPlayer : players)
						{
							if (HasStartedAOTD.get(entityPlayer))
							{
								entityPlayer.addChatMessage(new ChatComponentText("�2I �2feel �2better �2some �2how."));
							}
						}
						this.readyToSpawnWerewolves = true;
					}
				}
			}
		}
	}
}
