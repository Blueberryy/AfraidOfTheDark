/*
 * Author: David Slovikosky Mod: Afraid of the Dark Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.afraidofthedark.proxy;

import com.DavidM1A2.afraidofthedark.AfraidOfTheDark;
import com.DavidM1A2.afraidofthedark.common.constants.ModBlocks;
import com.DavidM1A2.afraidofthedark.common.packets.animationPackets.SyncAnimation;
import com.DavidM1A2.afraidofthedark.common.packets.capabilityPackets.*;
import com.DavidM1A2.afraidofthedark.common.packets.otherPackets.*;
import com.DavidM1A2.afraidofthedark.common.packets.packetHandler.PacketHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Common proxy that is instantiated on both sides (CLIENT and SERVER)
 */
public abstract class CommonProxy implements IProxy
{
    /**
     * Called to initialize any mod blocks into the ore dictionary. Happens on server and client
     */
    @Override
    public void initializeOreDictionary()
    {
        OreDictionary.registerOre("logWood", ModBlocks.GRAVEWOOD);
        OreDictionary.registerOre("plankWood", ModBlocks.GRAVEWOOD_PLANKS);
        OreDictionary.registerOre("treeLeaves", ModBlocks.GRAVEWOOD_LEAVES);
        OreDictionary.registerOre("slabWood", ModBlocks.GRAVEWOOD_HALF_SLAB);
        OreDictionary.registerOre("slabWood", ModBlocks.GRAVEWOOD_DOUBLE_SLAB);
        OreDictionary.registerOre("treeSapling", ModBlocks.GRAVEWOOD_SAPLING);
        OreDictionary.registerOre("stairWood", ModBlocks.GRAVEWOOD_STAIRS);

        OreDictionary.registerOre("logWood", ModBlocks.MANGROVE);
        OreDictionary.registerOre("plankWood", ModBlocks.MANGROVE_PLANKS);
        OreDictionary.registerOre("treeLeaves", ModBlocks.MANGROVE_LEAVES);
        OreDictionary.registerOre("slabWood", ModBlocks.MANGROVE_HALF_SLAB);
        OreDictionary.registerOre("slabWood", ModBlocks.MANGROVE_DOUBLE_SLAB);
        OreDictionary.registerOre("treeSapling", ModBlocks.GRAVEWOOD_SAPLING);
        OreDictionary.registerOre("stairWood", ModBlocks.MANGROVE_STAIRS);
    }

    /**
     * Registers any packets used by AOTD
     */
    @Override
    public void registerPackets()
    {
        PacketHandler packetHandler = AfraidOfTheDark.INSTANCE.getPacketHandler();

        packetHandler.registerBidiPacket(SyncStartedAOTD.class, new SyncStartedAOTD.Handler());
        packetHandler.registerBidiPacket(SyncAOTDPlayerBasics.class, new SyncAOTDPlayerBasics.Handler());
        packetHandler.registerBidiPacket(SyncResearch.class, new SyncResearch.Handler());
        packetHandler.registerBidiPacket(UpdateWatchedMeteor.class, new UpdateWatchedMeteor.Handler());
        packetHandler.registerBidiPacket(SyncSpell.class, new SyncSpell.Handler());
        packetHandler.registerBidiPacket(SyncClearSpells.class, new SyncClearSpells.Handler());

        packetHandler.registerPacket(SyncAnimation.class, new SyncAnimation.Handler(), Side.CLIENT);
        packetHandler.registerPacket(SyncItemWithCooldown.class, new SyncItemWithCooldown.Handler(), Side.CLIENT);
        packetHandler.registerPacket(SyncVoidChest.class, new SyncVoidChest.Handler(), Side.CLIENT);
        packetHandler.registerPacket(SyncParticle.class, new SyncParticle.Handler(), Side.CLIENT);
        packetHandler.registerPacket(SyncFreezeData.class, new SyncFreezeData.Handler(), Side.CLIENT);

        packetHandler.registerPacket(FireWristCrossbow.class, new FireWristCrossbow.Handler(), Side.SERVER);
        packetHandler.registerPacket(ProcessSextantInput.class, new ProcessSextantInput.Handler(), Side.SERVER);
        packetHandler.registerPacket(SyncSelectedWristCrossbowBolt.class, new SyncSelectedWristCrossbowBolt.Handler(), Side.SERVER);
        packetHandler.registerPacket(SyncSpellKeyPress.class, new SyncSpellKeyPress.Handler(), Side.SERVER);
    }
}
