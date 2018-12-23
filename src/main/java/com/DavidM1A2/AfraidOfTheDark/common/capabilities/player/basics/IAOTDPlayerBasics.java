package com.DavidM1A2.afraidofthedark.common.capabilities.player.basics;

import net.minecraft.entity.player.EntityPlayer;

/**
 * An interface that is a base for AOTD player basic capabilities
 */
public interface IAOTDPlayerBasics
{
	/**
	 * @return True if the player has started the afraid of the dark mod, false otherwise
	 */
	boolean getStartedAOTD();

	/**
	 * Called to set true if the player has started the mod, false otherwise
	 *
	 * @param startedAOTD True if the player started AOTD, false otherwise
	 */
	void setStartedAOTD(boolean startedAOTD);

	/**
	 * Called to either tell client -> server the current client AOTD status or server -> client based on if it's client or server side
	 *
	 * @param entityPlayer The player to sync
	 */
	void syncStartedAOTD(EntityPlayer entityPlayer);

	/**
	 * Syncs all player basic data from server -> client
	 *
	 * @param entityPlayer The player to sync to
	 */
	void syncAll(EntityPlayer entityPlayer);
}