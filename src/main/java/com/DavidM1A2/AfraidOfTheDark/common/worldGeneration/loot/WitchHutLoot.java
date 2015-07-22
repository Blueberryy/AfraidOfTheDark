/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.worldGeneration.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModItems;

import net.minecraft.init.Items;
import net.minecraft.util.WeightedRandomChestContent;

public class WitchHutLoot implements IChestGenerator
{
	@Override
	public List<WeightedRandomChestContent> getPossibleItems(Random random)
	{
		ArrayList<WeightedRandomChestContent> toReturn = new ArrayList<WeightedRandomChestContent>();

		for (int i = 5; i < 25; i++)
		{
			//                                          Item, meta, min, max, chance
			toReturn.add(new WeightedRandomChestContent(Items.potionitem, i, 1, 3, 7));
		}

		toReturn.add(new WeightedRandomChestContent(ModItems.researchScrollWristCrossbow, 0, 1, 1, 5));

		return toReturn;
	}
}