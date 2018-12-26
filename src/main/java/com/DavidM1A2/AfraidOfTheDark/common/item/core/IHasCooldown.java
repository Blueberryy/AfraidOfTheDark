/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.item.core;

import net.minecraft.item.ItemStack;

public interface IHasCooldown
{
	public int getItemCooldownInTicks(ItemStack itemStack);

	public int getItemCooldownInTicks();
}