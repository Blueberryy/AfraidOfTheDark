/*
 * Author: David Slovikosky 
 * Mod: Afraid of the Dark 
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.DavidM1A2.AfraidOfTheDark.client.gui.GuiHandler;
import com.DavidM1A2.AfraidOfTheDark.client.gui.ResearchAchieved;
import com.DavidM1A2.AfraidOfTheDark.client.settings.ClientData;
import com.DavidM1A2.AfraidOfTheDark.common.MCACommonLibrary.animation.AnimTickHandler;
import com.DavidM1A2.AfraidOfTheDark.common.commands.AOTDCommands;
import com.DavidM1A2.AfraidOfTheDark.common.commands.CMDInsanityCheck;
import com.DavidM1A2.AfraidOfTheDark.common.debug.DebugSpammer;
import com.DavidM1A2.AfraidOfTheDark.common.handler.ConfigurationHandler;
import com.DavidM1A2.AfraidOfTheDark.common.handler.FogRenderingEvents;
import com.DavidM1A2.AfraidOfTheDark.common.handler.GuiEventHandler;
import com.DavidM1A2.AfraidOfTheDark.common.handler.KeyInputEventHandler;
import com.DavidM1A2.AfraidOfTheDark.common.handler.PlayerController;
import com.DavidM1A2.AfraidOfTheDark.common.handler.WorldEvents;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModBiomes;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModBlocks;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModCapabilities;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModDimensions;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModEntities;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModFurnaceRecipies;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModGeneration;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModItems;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModOreDictionaryCompatability;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModPotionEffects;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModRecipes;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModSounds;
import com.DavidM1A2.AfraidOfTheDark.common.packets.minersBasicMessageHandler.PacketHandler;
import com.DavidM1A2.AfraidOfTheDark.common.reference.Reference;
import com.DavidM1A2.AfraidOfTheDark.common.utility.LogHelper;
import com.DavidM1A2.AfraidOfTheDark.proxy.IProxy;

import io.netty.util.concurrent.DefaultThreadFactory;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

/*
 * Main class run when the mod is started up
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class AfraidOfTheDark
{
	/**
	 * Singleton design pattern used here
	 */
	@Mod.Instance(Reference.MOD_ID)
	public static AfraidOfTheDark instance;

	/**
	 * Executor used in calling delayed code
	 */
	private final ScheduledExecutorService delayedExecutor = Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory("AfraidOfTheDark"));

	/**
	 * Channel for sending and receiving packets
	 */
	private final PacketHandler packetHandler = new PacketHandler("AOTD Packets");

	/**
	 * Sided proxy used to distinguish client & server side
	 */
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;

	/**
	 * @param event
	 *            Pre-init used to register events and various other things (see class names for what each line does)
	 */
	@Mod.EventHandler
	public void preInitialization(final FMLPreInitializationEvent event)
	{
		// Initialize configuration
		ConfigurationHandler.initializataion(event.getSuggestedConfigurationFile());
		ModCapabilities.initialize();
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
		// Initialize any player events
		final PlayerController controller = new PlayerController();
		MinecraftForge.EVENT_BUS.register(controller);
		MinecraftForge.EVENT_BUS.register(new FogRenderingEvents());
		MinecraftForge.TERRAIN_GEN_BUS.register(controller);
		FMLCommonHandler.instance().bus().register(controller);
		// Initialize any world events
		WorldEvents worldEvents = new WorldEvents();
		FMLCommonHandler.instance().bus().register(worldEvents);
		MinecraftForge.EVENT_BUS.register(worldEvents);
		if (event.getSide() == Side.CLIENT)
		{
			MinecraftForge.EVENT_BUS.register(new GuiEventHandler());
			// Register the animation handler
			MinecraftForge.EVENT_BUS.register(AnimTickHandler.getInstance());
		}
		// Initialize debug file to spam chat with variables
		if (Reference.isDebug)
			MinecraftForge.EVENT_BUS.register(new DebugSpammer());
		// Initialize mod blocks
		ModBlocks.initialize();
		// Initialize mod items
		ModItems.initialize(event.getSide());
		// Initialize mod entities
		ModEntities.intialize();
		// Initialize mod world generation
		ModGeneration.initialize();
		// Initialize furnace recipes
		ModFurnaceRecipies.initialize();
		// Initialize dimensions
		ModDimensions.intialize();
		// Initialize mod biomes
		ModBiomes.initialize();
		// Initialize the ORE-Dictionary compatability
		ModOreDictionaryCompatability.initialize();
		// Initialize GUI handler
		NetworkRegistry.INSTANCE.registerGuiHandler(AfraidOfTheDark.instance, new GuiHandler());
		// Initialize key bindings
		AfraidOfTheDark.proxy.registerKeyBindings();
		// Initialize the mod channel
		AfraidOfTheDark.proxy.registerChannel();
		// Initialize font renderers
		AfraidOfTheDark.proxy.registerMiscelaneous();
		// Setup Potion effects
		ModPotionEffects.initialize();
		// Initialize renderers
		AfraidOfTheDark.proxy.registerEntityRenders();
		// Initialize sounds
		ModSounds.initialize();
		proxy.preInit();

		if (ConfigurationHandler.debugMessages)
		{
			LogHelper.info("Pre-Initialization Complete");
		}
	}

	/**
	 * @param event
	 *            Initialization event is responsible for renders and recipes
	 */
	@Mod.EventHandler
	public void initialization(final FMLInitializationEvent event)
	{
		// Initialize Recipes
		ModRecipes.initialize();
		// Initialize mod item renderers
		AfraidOfTheDark.proxy.registerItemRenders();
		// Initialize block renderers
		AfraidOfTheDark.proxy.registerBlockRenders();
		AfraidOfTheDark.proxy.registerMiscRenders();
		// Initialize key input handler on client side only
		if (event.getSide() == Side.CLIENT)
		{
			FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());
		}

		if (ConfigurationHandler.debugMessages)
		{
			LogHelper.info("Initialization Complete");
		}
	}

	/**
	 * @param event
	 *            Register the research achieved overlay on the client side only
	 */
	@Mod.EventHandler
	public void postInitialization(final FMLPostInitializationEvent event)
	{
		if (event.getSide() == Side.CLIENT)
		{
			ClientData.researchAchievedOverlay = new ResearchAchieved(Minecraft.getMinecraft());
		}

		if (ConfigurationHandler.debugMessages)
		{
			LogHelper.info("Post-Initialization Complete");
		}
	}

	/**
	 * @param event
	 *            Register commands when the server starts
	 */
	@Mod.EventHandler
	public void serverStartingEvent(final FMLServerStartingEvent event)
	{
		// Register any player commands
		if (Reference.isDebug)
		{
			event.registerServerCommand(new CMDInsanityCheck());
		}
		event.registerServerCommand(new AOTDCommands());
	}

	/**
	 * @return SimpleNetworkWrapper instance
	 */
	public PacketHandler getPacketHandler()
	{
		return this.packetHandler;
	}

	/**
	 * @return ScheduledExecutor instance
	 */
	public ScheduledExecutorService getDelayedExecutor()
	{
		return this.delayedExecutor;
	}
}
