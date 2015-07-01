/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.schematic;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class SchematicBlockReplacer
{
	private static final short[] knownProblemIds = new short[]
	{ -122, (short) Block.getIdFromBlock(Blocks.spruce_stairs), -94, (short) Block.getIdFromBlock(Blocks.log2), -117, (short) Block.getIdFromBlock(Blocks.cobblestone_wall), -119, (short) Block.getIdFromBlock(Blocks.command_block), -83, (short) Block.getIdFromBlock(Blocks.coal_block), -92,
			(short) Block.getIdFromBlock(Blocks.dark_oak_stairs), -97, (short) Block.getIdFromBlock(Blocks.stained_hardened_clay), -85, (short) Block.getIdFromBlock(Blocks.carpet), -111, (short) Block.getIdFromBlock(Blocks.anvil), -64, (short) Block.getIdFromBlock(Blocks.acacia_fence), -102,
			(short) Block.getIdFromBlock(Blocks.hopper), -116, (short) Block.getIdFromBlock(Blocks.flower_pot), -108, (short) Block.getIdFromBlock(Blocks.heavy_weighted_pressure_plate), -109, (short) Block.getIdFromBlock(Blocks.light_weighted_pressure_plate), -125,
			(short) Block.getIdFromBlock(Blocks.tripwire_hook), -124, (short) Block.getIdFromBlock(Blocks.tripwire), -110, (short) Block.getIdFromBlock(Blocks.trapped_chest), -68, (short) Block.getIdFromBlock(Blocks.spruce_fence), -96, (short) Block.getIdFromBlock(Blocks.stained_glass_pane), -120,
			(short) Block.getIdFromBlock(Blocks.acacia_stairs), -67, (short) Block.getIdFromBlock(Blocks.birch_fence), -65, (short) Block.getIdFromBlock(Blocks.dark_oak_fence), -121, (short) Block.getIdFromBlock(Blocks.birch_stairs), -128, (short) Block.getIdFromBlock(Blocks.sandstone_stairs), -86,
			(short) Block.getIdFromBlock(Blocks.hay_block), -87, (short) Block.getIdFromBlock(Blocks.sea_lantern), -81, (short) Block.getIdFromBlock(Blocks.double_plant), -63, (short) Block.getIdFromBlock(Blocks.spruce_door), -59, (short) Block.getIdFromBlock(Blocks.dark_oak_door), -112,
			(short) Block.getIdFromBlock(Blocks.skull), -93, (short) Block.getIdFromBlock(Blocks.acacia_stairs), -95, (short) Block.getIdFromBlock(Blocks.leaves), -107, (short) Block.getIdFromBlock(Blocks.unpowered_comparator), -115, (short) Block.getIdFromBlock(Blocks.carrots), -114,
			(short) Block.getIdFromBlock(Blocks.potatoes), -70, (short) Block.getIdFromBlock(Blocks.dark_oak_fence_gate), -100, (short) Block.getIdFromBlock(Blocks.quartz_stairs), -88, (short) Block.getIdFromBlock(Blocks.prismarine) };

	public static Schematic replaceBlocks(Schematic schematic, Block... blocks)
	{
		if (blocks.length % 2 != 0)
		{
			return schematic;
		}

		int i = 0;

		for (int y = 0; y < schematic.getHeight(); y++)
		{
			for (int z = 0; z < schematic.getLength(); z++)
			{
				for (int x = 0; x < schematic.getWidth(); x++)
				{
					Block nextToPlace = Block.getBlockById(schematic.getBlocks()[i]);

					for (int j = 0; j < blocks.length; j = j + 2)
					{
						if (nextToPlace == blocks[j])
						{
							schematic.setBlock(blocks[j + 1], i);
						}
					}

					i = i + 1;
				}
			}
		}

		return schematic;
	}

	public static Schematic replaceBlocks(Schematic schematic, Short... blocks)
	{
		if (blocks.length % 2 != 0)
		{
			return schematic;
		}

		int i = 0;

		for (int y = 0; y < schematic.getHeight(); y++)
		{
			for (int z = 0; z < schematic.getLength(); z++)
			{
				for (int x = 0; x < schematic.getWidth(); x++)
				{
					Short nextToPlace = schematic.getBlocks()[i];

					for (int j = 0; j < blocks.length; j = j + 2)
					{
						if (nextToPlace == blocks[j])
						{
							schematic.setBlock(blocks[j + 1], i);
						}
					}

					i = i + 1;
				}
			}
		}

		return schematic;
	}

	public static Schematic fixKnownSchematicErrors(Schematic schematic)
	{
		int i = 0;

		for (int y = 0; y < schematic.getHeight(); y++)
		{
			for (int z = 0; z < schematic.getLength(); z++)
			{
				for (int x = 0; x < schematic.getWidth(); x++)
				{
					Short nextToPlace = schematic.getBlocks()[i];

					for (int j = 0; j < knownProblemIds.length; j = j + 2)
					{
						if (nextToPlace == knownProblemIds[j])
						{
							schematic.setBlock(knownProblemIds[j + 1], i);
						}
					}

					i = i + 1;
				}
			}
		}

		return schematic;
	}
}
