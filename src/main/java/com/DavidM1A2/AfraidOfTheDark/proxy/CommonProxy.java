/*
 * Author: David Slovikosky Mod: Afraid of the Dark Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.afraidofthedark.proxy;

import com.DavidM1A2.afraidofthedark.AfraidOfTheDark;
import com.DavidM1A2.afraidofthedark.common.constants.ModBlocks;
import com.DavidM1A2.afraidofthedark.common.packets.animationPackets.SyncAnimation;
import com.DavidM1A2.afraidofthedark.common.packets.capabilityPackets.SyncAOTDPlayerBasics;
import com.DavidM1A2.afraidofthedark.common.packets.capabilityPackets.SyncStartedAOTD;
import com.DavidM1A2.afraidofthedark.common.packets.packetHandler.PacketHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
		OreDictionary.registerOre("slabWood", ModBlocks.GRAVEWOOD_HALF_SLAB);
		OreDictionary.registerOre("slabWood", ModBlocks.GRAVEWOOD_DOUBLE_SLAB);
		OreDictionary.registerOre("treeLeaves", ModBlocks.GRAVEWOOD_LEAVES);
		OreDictionary.registerOre("plankWood", ModBlocks.GRAVEWOOD_PLANKS);
		OreDictionary.registerOre("treeSapling", ModBlocks.GRAVEWOOD_SAPLING);
		OreDictionary.registerOre("stairWood", ModBlocks.GRAVEWOOD_STAIRS);
	}

	/**
	 * Called to initialize any mod smelting recipes
	 */
	@Override
	public void initializeSmeltingRecipes()
	{
		GameRegistry.addSmelting(new ItemStack(ModBlocks.GRAVEWOOD), new ItemStack(Items.COAL, 1, 1), 0.15f);
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

		packetHandler.registerPacket(SyncAnimation.class, new SyncAnimation.Handler(), Side.CLIENT);
	}
}
