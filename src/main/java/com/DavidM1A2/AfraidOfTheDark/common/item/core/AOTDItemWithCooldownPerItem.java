/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.item.core;

import com.DavidM1A2.AfraidOfTheDark.common.utility.NBTHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public abstract class AOTDItemWithCooldownPerItem extends AOTDItem implements IHasCooldown
{
	public static final String LAST_COOLDOWN = "lastCooldown";
	private long serverClientTimeDifference = 0;

	public AOTDItemWithCooldownPerItem()
	{
		super();
		this.setMaxStackSize(1);
	}

	@Override
	public boolean showDurabilityBar(ItemStack itemStack)
	{
		return true;
	}

	/**
	 * Queries the percentage of the 'Durability' bar that should be drawn.
	 *
	 * @param stack
	 *            The current ItemStack
	 * @return 1.0 for 100% 0 for 0%
	 */
	@Override
	public double getDurabilityForDisplay(ItemStack itemStack)
	{
		return Math.max(0, 1 - (System.currentTimeMillis() - this.serverClientTimeDifference - NBTHelper.getLong(itemStack, LAST_COOLDOWN)) / (this.getItemCooldownInTicks(itemStack) * 50.0));
	}

	public void setOnCooldown(ItemStack itemStack)
	{
		NBTHelper.setLong(itemStack, LAST_COOLDOWN, System.currentTimeMillis());
	}

	public void setServerClientDifference(long difference)
	{
		this.serverClientTimeDifference = difference;
	}

	public boolean isOnCooldown(ItemStack itemStack)
	{
		return (System.currentTimeMillis() - NBTHelper.getLong(itemStack, LAST_COOLDOWN)) < this.getItemCooldownInTicks(itemStack) * 50;
	}

	public int cooldownRemaining(ItemStack itemStack)
	{
		return MathHelper.ceiling_double_int(System.currentTimeMillis() - NBTHelper.getLong(itemStack, LAST_COOLDOWN)) / 1000 + 1;
	}

	@Override
	public boolean isDamageable()
	{
		return true;
	}
}