package com.DavidM1A2.afraidofthedark.common.item;

import com.DavidM1A2.afraidofthedark.common.capabilities.world.OverworldHeightmap;
import com.DavidM1A2.afraidofthedark.common.capabilities.world.StructurePlan;
import com.DavidM1A2.afraidofthedark.common.item.core.AOTDItem;
import com.DavidM1A2.afraidofthedark.common.worldGeneration.WorldStructurePlanner;
import com.DavidM1A2.afraidofthedark.common.worldGeneration.structure.base.Structure;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

/**
 * Item that allows for modding debug, does nothing else
 */
public class ItemDebug extends AOTDItem
{
	/**
	 * Constructor sets up item properties
	 */
	public ItemDebug()
	{
		super("debug");
		this.setMaxStackSize(1);
	}

	///
	/// Code below here is not documented due to its temporary nature used for testing
	///


	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		if (!worldIn.isRemote)
		{
			OverworldHeightmap x = OverworldHeightmap.get(worldIn);
			StructurePlan y = StructurePlan.get(worldIn);
			BlockPos position = playerIn.getPosition();
			ChunkPos chunkPos = new ChunkPos(position.getX() >> 4, position.getZ() >> 4);
			if (x != null)
			{
				if (x.heightKnown(chunkPos))
				{
					playerIn.sendMessage(new TextComponentString("Low height is: " + x.getLowestHeight(chunkPos)));
					playerIn.sendMessage(new TextComponentString("High height is: " + x.getHighestHeight(chunkPos)));
				}
				else
				{
					playerIn.sendMessage(new TextComponentString("Height unknown..."));
				}
			}

			if (y != null)
			{
				Structure structureAt = y.getStructureAt(chunkPos);
				if (structureAt != null)
				{
					playerIn.sendMessage(new TextComponentString("Structure at position is " + structureAt.getRegistryName()));

					if (playerIn.isSneaking())
					{
						structureAt.generate(worldIn, y.getStructureOrigin(chunkPos), chunkPos);
					}
				}
				else
				{
					playerIn.sendMessage(new TextComponentString("No structure at position"));
				}
			}

			/*
			SchematicGenerator.generateSchematic(
					ModSchematics.CRYPT,
					worldIn,
					playerIn.getPosition().add(3, 0, 3),
					new ChunkPos(playerIn.chunkCoordX + 1, playerIn.chunkCoordZ + 1),
					ModLootTables.CRYPT);

			SchematicGenerator.generateSchematic(
					ModSchematics.CRYPT,
					worldIn,
					playerIn.getPosition().add(3, 0, 3),
					new ChunkPos(playerIn.chunkCoordX + 2, playerIn.chunkCoordZ + 1),
					ModLootTables.CRYPT);

			SchematicGenerator.generateSchematic(
					ModSchematics.CRYPT,
					worldIn,
					playerIn.getPosition().add(3, 0, 3),
					new ChunkPos(playerIn.chunkCoordX + 2, playerIn.chunkCoordZ + 2),
					ModLootTables.CRYPT);

			SchematicGenerator.generateSchematic(
					ModSchematics.CRYPT,
					worldIn,
					playerIn.getPosition().add(3, 0, 3),
					new ChunkPos(playerIn.chunkCoordX + 1, playerIn.chunkCoordZ + 2),
					ModLootTables.CRYPT);
			*/
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}