package com.DavidM1A2.AfraidOfTheDark.threads;

import net.minecraft.entity.player.EntityPlayer;

public abstract class DelayedUpdate<E> extends Thread
{
	protected final EntityPlayer entityPlayer;
	protected final E data;

	public DelayedUpdate(final EntityPlayer entityPlayer, final E data)
	{
		this.entityPlayer = entityPlayer;
		this.data = data;
	}

	@Override
	public void run()
	{
		try
		{
			Thread.sleep(500);

			this.updatePlayer();
		}
		catch (final InterruptedException e)
		{
		}
	}

	protected abstract void updatePlayer();
}
