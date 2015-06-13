package com.DavidM1A2.AfraidOfTheDark.common.refrence;

import net.minecraft.item.Item;

import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModItems;

public enum AOTDCrossbowBoltTypes
{
	wooden(0, ModItems.woodenBolt), iron(1, ModItems.ironBolt), silver(2, ModItems.silverBolt);

	private int id = 0;
	private Item myBoltItem;

	private AOTDCrossbowBoltTypes(int id, Item myBoltItem)
	{
		this.id = id;
		this.myBoltItem = myBoltItem;
	}

	public static int getIDFromType(AOTDCrossbowBoltTypes crossbowType)
	{
		return crossbowType.id;
	}

	public static AOTDCrossbowBoltTypes getTypeFromID(int id)
	{
		for (AOTDCrossbowBoltTypes crossbowTypes : AOTDCrossbowBoltTypes.values())
		{
			if (crossbowTypes.id == id)
			{
				return crossbowTypes;
			}
		}
		return null;
	}

	public Item getMyBoltItem()
	{
		return this.myBoltItem;
	}

	public AOTDCrossbowBoltTypes next()
	{
		if (this == silver)
		{
			return wooden;
		}
		else
		{
			return getTypeFromID(this.id + 1);
		}
	}
}