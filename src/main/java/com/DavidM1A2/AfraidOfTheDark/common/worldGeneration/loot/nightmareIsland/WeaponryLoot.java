/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.worldGeneration.loot.nightmareIsland;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.DavidM1A2.AfraidOfTheDark.common.worldGeneration.loot.IChestGenerator;

import net.minecraft.init.Items;
import net.minecraft.util.WeightedRandomChestContent;

public class WeaponryLoot implements IChestGenerator
{
	@Override
	public List<WeightedRandomChestContent> getPossibleItems(Random random)
	{
		ArrayList<WeightedRandomChestContent> toReturn = new ArrayList<WeightedRandomChestContent>();

		//                                          Item, meta, min, max, chance
		toReturn.add(new WeightedRandomChestContent(Items.iron_sword, 0, 1, 5, 10));

		return toReturn;
	}
}