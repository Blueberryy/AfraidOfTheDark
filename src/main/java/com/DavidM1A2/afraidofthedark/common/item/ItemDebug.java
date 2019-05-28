package com.DavidM1A2.afraidofthedark.common.item;

import com.DavidM1A2.afraidofthedark.common.capabilities.player.spell.IAOTDPlayerSpellManager;
import com.DavidM1A2.afraidofthedark.common.constants.ModCapabilities;
import com.DavidM1A2.afraidofthedark.common.constants.ModSpellDeliveryMethods;
import com.DavidM1A2.afraidofthedark.common.constants.ModSpellEffects;
import com.DavidM1A2.afraidofthedark.common.constants.ModSpellPowerSources;
import com.DavidM1A2.afraidofthedark.common.item.core.AOTDItem;
import com.DavidM1A2.afraidofthedark.common.spell.Spell;
import com.DavidM1A2.afraidofthedark.common.spell.SpellStage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
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
        if (worldIn.isRemote)
        {
            //Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleEnariaBasicAttack(worldIn, playerIn.posX, playerIn.posY + 2.0, playerIn.posZ));
        }
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
            IAOTDPlayerSpellManager spellManager = playerIn.getCapability(ModCapabilities.PLAYER_SPELL_MANAGER, null);
            if (!spellManager.getSpells().isEmpty())
            {
                spellManager.deleteSpell(spellManager.getSpells().iterator().next());
            }
            else
            {
                Spell spell = new Spell(playerIn);
                SpellStage spellStage = new SpellStage();
                spellStage.setDeliveryMethod(ModSpellDeliveryMethods.SELF.newInstance());
                spellStage.getEffects()[0] = ModSpellEffects.DIG.newInstance();
                spell.getSpellStages().add(spellStage);
                spell.setPowerSource(ModSpellPowerSources.CREATIVE.newInstance());
                spell.setName("Test");
                spellManager.addOrUpdateSpell(spell);
                spellManager.keybindSpell("h", spell);
                spellManager.syncAll(playerIn);
            }

            /*
            AfraidOfTheDark.INSTANCE.getPacketHandler().sendTo(new SyncParticle(AOTDParticleRegistry.ParticleTypes.ENARIA_TELEPORT_ID,
                    Lists.newArrayList(new Vec3d(playerIn.posX, playerIn.posY + 2, playerIn.posZ)), Lists.newArrayList(Vec3d.ZERO)), (EntityPlayerMP) playerIn);

             */
            /*
            if (playerIn.dimension == 0)
            {
                playerIn.changeDimension(ModDimensions.NIGHTMARE.getId(), ModDimensions.NOOP_TELEPORTER);
            }
            else
            {c
                playerIn.changeDimension(0, ModDimensions.NOOP_TELEPORTER);
            }

             */
            // Z
            // SchematicGenerator.generateSchematic(ModSchematics.TUNNEL_EW, playerIn.world, playerIn.getPosition(), null, ModLootTables.GNOMISH_CITY);
			/*
			// 60x60
			playerIn.sendMessage(new TextComponentString("TREES: "));
			for (Schematic schematic : ModSchematics.DARK_FOREST_TREES)
				playerIn.sendMessage(new TextComponentString(schematic.getWidth() + ", " + schematic.getLength()));

			// 17x17
			playerIn.sendMessage(new TextComponentString("PROPS: "));
			for (Schematic schematic : ModSchematics.DARK_FOREST_PROPS)
				playerIn.sendMessage(new TextComponentString(schematic.getWidth() + ", " + schematic.getLength()));
			 */
			/*
			NBTHelper.getAllSavedPlayerNBTs(worldIn.getMinecraftServer(), true);
			 */

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
