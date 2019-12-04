/*
 * Author: David Slovikosky Mod: Afraid of the Dark Ideas and Textures: Michael Albertson
 */
package com.davidm1a2.afraidofthedark.proxy

import com.davidm1a2.afraidofthedark.AfraidOfTheDark
import com.davidm1a2.afraidofthedark.common.constants.ModBlocks
import com.davidm1a2.afraidofthedark.common.packets.animationPackets.SyncAnimation
import com.davidm1a2.afraidofthedark.common.packets.capabilityPackets.*
import com.davidm1a2.afraidofthedark.common.packets.otherPackets.*
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.oredict.OreDictionary

/**
 * Common proxy that is instantiated on both sides (CLIENT and SERVER)
 */
abstract class CommonProxy : IProxy
{
    /**
     * Called to initialize any mod blocks into the ore dictionary. Happens on server and client
     */
    override fun initializeOreDictionary()
    {
        OreDictionary.registerOre("logWood", ModBlocks.GRAVEWOOD)
        OreDictionary.registerOre("plankWood", ModBlocks.GRAVEWOOD_PLANKS)
        OreDictionary.registerOre("treeLeaves", ModBlocks.GRAVEWOOD_LEAVES)
        OreDictionary.registerOre("slabWood", ModBlocks.GRAVEWOOD_HALF_SLAB)
        OreDictionary.registerOre("slabWood", ModBlocks.GRAVEWOOD_DOUBLE_SLAB)
        OreDictionary.registerOre("treeSapling", ModBlocks.GRAVEWOOD_SAPLING)
        OreDictionary.registerOre("stairWood", ModBlocks.GRAVEWOOD_STAIRS)
        OreDictionary.registerOre("logWood", ModBlocks.MANGROVE)
        OreDictionary.registerOre("plankWood", ModBlocks.MANGROVE_PLANKS)
        OreDictionary.registerOre("treeLeaves", ModBlocks.MANGROVE_LEAVES)
        OreDictionary.registerOre("slabWood", ModBlocks.MANGROVE_HALF_SLAB)
        OreDictionary.registerOre("slabWood", ModBlocks.MANGROVE_DOUBLE_SLAB)
        OreDictionary.registerOre("treeSapling", ModBlocks.GRAVEWOOD_SAPLING)
        OreDictionary.registerOre("stairWood", ModBlocks.MANGROVE_STAIRS)
    }

    /**
     * Registers any packets used by AOTD
     */
    override fun registerPackets()
    {
        val packetHandler = AfraidOfTheDark.INSTANCE.packetHandler
        packetHandler.registerBidiPacket(SyncStartedAOTD::class.java, SyncStartedAOTD.Handler())
        packetHandler.registerBidiPacket(SyncAOTDPlayerBasics::class.java, SyncAOTDPlayerBasics.Handler())
        packetHandler.registerBidiPacket(SyncResearch::class.java, SyncResearch.Handler())
        packetHandler.registerBidiPacket(UpdateWatchedMeteor::class.java, UpdateWatchedMeteor.Handler())
        packetHandler.registerBidiPacket(SyncSpell::class.java, SyncSpell.Handler())
        packetHandler.registerBidiPacket(SyncClearSpells::class.java, SyncClearSpells.Handler())
        packetHandler.registerPacket(SyncAnimation::class.java, SyncAnimation.Handler(), Side.CLIENT)
        packetHandler.registerPacket(SyncItemWithCooldown::class.java, SyncItemWithCooldown.Handler(), Side.CLIENT)
        packetHandler.registerPacket(SyncVoidChest::class.java, SyncVoidChest.Handler(), Side.CLIENT)
        packetHandler.registerPacket(SyncParticle::class.java, SyncParticle.Handler(), Side.CLIENT)
        packetHandler.registerPacket(SyncFreezeData::class.java, SyncFreezeData.Handler(), Side.CLIENT)
        packetHandler.registerPacket(FireWristCrossbow::class.java, FireWristCrossbow.Handler(), Side.SERVER)
        packetHandler.registerPacket(ProcessSextantInput::class.java, ProcessSextantInput.Handler(), Side.SERVER)
        packetHandler.registerPacket(SyncSelectedWristCrossbowBolt::class.java, SyncSelectedWristCrossbowBolt.Handler(), Side.SERVER)
        packetHandler.registerPacket(SyncSpellKeyPress::class.java, SyncSpellKeyPress.Handler(), Side.SERVER)
    }
}