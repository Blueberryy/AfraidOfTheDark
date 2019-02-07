package com.DavidM1A2.afraidofthedark.common.item;

import com.DavidM1A2.afraidofthedark.common.capabilities.world.StructurePlan;
import com.DavidM1A2.afraidofthedark.common.constants.ModCapabilities;
import com.DavidM1A2.afraidofthedark.common.constants.ModRegistries;
import com.DavidM1A2.afraidofthedark.common.item.core.AOTDItem;
import com.DavidM1A2.afraidofthedark.common.registry.research.Research;
import com.DavidM1A2.afraidofthedark.common.utility.NBTHelper;
import com.DavidM1A2.afraidofthedark.common.worldGeneration.structure.base.Structure;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

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
		/*
		for (Research research : ModRegistries.RESEARCH)
			playerIn.sendMessage(new TextComponentString(research.getRegistryName().toString() + " -> " + playerIn.getCapability(ModCapabilities.PLAYER_RESEARCH, null).isResearched(research)));
*/
		/*
		playerIn.sendMessage(new TextComponentString("" + playerIn.getCapability(ModCapabilities.PLAYER_BASICS, null).getWatchedMeteorDropAngle()));
		playerIn.sendMessage(new TextComponentString("" + playerIn.getCapability(ModCapabilities.PLAYER_BASICS, null).getWatchedMeteorLatitude()));
		playerIn.sendMessage(new TextComponentString("" + playerIn.getCapability(ModCapabilities.PLAYER_BASICS, null).getWatchedMeteorLongitude()));
		*/
		if (!worldIn.isRemote)
		{
			NBTHelper.getAllSavedPlayerNBTs(worldIn.getMinecraftServer(), true);

			/*
			StructurePlan y = StructurePlan.get(worldIn);
			BlockPos position = playerIn.getPosition();
			ChunkPos chunkPos = new ChunkPos(position);

			if (y != null)
			{
				Structure structureAt = y.getStructureAt(chunkPos);
				if (structureAt != null)
				{
					playerIn.sendMessage(new TextComponentString("Structure is " + structureAt.getRegistryName()));
					playerIn.sendMessage(new TextComponentString("Origin pos is " + y.getStructureOrigin(chunkPos)));
				}
				else
				{
					playerIn.sendMessage(new TextComponentString("No structure at position"));
				}
			}*/

			/*
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
			*/

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
