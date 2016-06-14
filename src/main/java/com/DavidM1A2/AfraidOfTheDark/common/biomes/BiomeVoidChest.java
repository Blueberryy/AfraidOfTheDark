/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.biomes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeVoidChest extends BiomeGenBase
{
	private List<SpawnListEntry> spawnableCreatures = new ArrayList<BiomeGenBase.SpawnListEntry>();

	public BiomeVoidChest(int biomeID)
	{
		super(biomeID);

		this.biomeName = "VoidChest";
		this.setColor(5470985);
		this.enableRain = true;
		this.setFillerBlockMetadata(5159473);
		this.flowers.clear();
		this.setHeight(new BiomeGenBase.Height(0.125F, 0.05F));
		this.spawnableCreatureList.clear();
		this.waterColorMultiplier = 0x000000;
		this.theBiomeDecorator.treesPerChunk = 0;
		this.topBlock = Blocks.dirt.getDefaultState();
		this.setDisableRain();
	}

	/**
	 * No longer allowing other mods to add stupid creatures to our realm
	 */
	@Override
	public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType)
	{
		if (!spawnableCreatures.isEmpty())
			this.spawnableCreatures.clear();
		return spawnableCreatures;
	}

	/**
	 * returns the chance a creature has to spawn.
	 */
	@Override
	public float getSpawningChance()
	{
		return 0.0F;
	}
}