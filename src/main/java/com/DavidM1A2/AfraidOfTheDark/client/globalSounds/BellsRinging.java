/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.client.globalSounds;

import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModSounds;
import com.DavidM1A2.AfraidOfTheDark.common.reference.AOTDDimensions;

import net.minecraft.util.ResourceLocation;

public class BellsRinging extends PlayerFollowingSound
{
	public BellsRinging()
	{
		super(ModSounds.bells);
		this.repeat = true;
	}

	@Override
	public void update()
	{
		super.update();
		if (this.entityPlayer.isDead || this.entityPlayer.dimension != AOTDDimensions.Nightmare.getWorldID())
			this.donePlaying = true;
	}

	@Override
	public int getRepeatDelay()
	{
		return this.entityPlayer.world.rand.nextInt(50 * 20) + 50 * 20;
	}
}