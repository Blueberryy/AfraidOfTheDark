package com.DavidM1A2.afraidofthedark.common.event;

import com.DavidM1A2.afraidofthedark.common.capabilities.player.basics.AOTDPlayerBasicsImpl;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.basics.AOTDPlayerBasicsProvider;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.basics.AOTDPlayerBasicsStorage;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.basics.IAOTDPlayerBasics;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.dimension.*;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.research.AOTDPlayerResearchImpl;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.research.AOTDPlayerResearchProvider;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.research.AOTDPlayerResearchStorage;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.research.IAOTDPlayerResearch;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.spell.AOTDPlayerSpellManagerImpl;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.spell.AOTDPlayerSpellManagerProvider;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.spell.AOTDPlayerSpellManagerStorage;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.spell.IAOTDPlayerSpellManager;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.spell.component.*;
import com.DavidM1A2.afraidofthedark.common.constants.Constants;
import com.DavidM1A2.afraidofthedark.common.constants.ModCapabilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Class used to register all of our mod capabilities
 */
public class CapabilityHandler
{
    // Store a flag that ensures if we create multiple capability handlers we only initialize once
    private static boolean wasInitialized = false;

    /**
     * Called to initialize all of our mod capabilities into the capability manager if it was not already initialized
     */
    public CapabilityHandler()
    {
        // If the capability manager was not initialized initialize it
        if (!CapabilityHandler.wasInitialized)
        {
            CapabilityManager.INSTANCE.register(IAOTDPlayerBasics.class, new AOTDPlayerBasicsStorage(), AOTDPlayerBasicsImpl::new);
            CapabilityManager.INSTANCE.register(IAOTDPlayerResearch.class, new AOTDPlayerResearchStorage(), AOTDPlayerResearchImpl::new);
            CapabilityManager.INSTANCE.register(IAOTDPlayerVoidChestData.class, new AOTDPlayerVoidChestDataStorage(), AOTDPlayerVoidChestDataImpl::new);
            CapabilityManager.INSTANCE.register(IAOTDPlayerNightmareData.class, new AOTDPlayerNightmareDataStorage(), AOTDPlayerNightmareImpl::new);
            CapabilityManager.INSTANCE.register(IAOTDPlayerSpellManager.class, new AOTDPlayerSpellManagerStorage(), AOTDPlayerSpellManagerImpl::new);
            CapabilityManager.INSTANCE.register(IAOTDPlayerSpellFreezeData.class, new AOTDPlayerSpellFreezeDataStorage(), AOTDPlayerSpellFreezeDataImpl::new);
            CapabilityManager.INSTANCE.register(IAOTDPlayerSpellCharmData.class, new AOTDPlayerSpellCharmDataStorage(), AOTDPlayerSpellCharmDataImpl::new);

            CapabilityHandler.wasInitialized = true;
        }
    }

    /**
     * When we get an attach capabilites event we attach our player capabilities
     *
     * @param event The attach event that we will add to
     */
    @SubscribeEvent
    public void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event)
    {
        // If the entity is a player then add any player specific capabilities
        if (event.getObject() instanceof EntityPlayer)
        {
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "player_basics"), new AOTDPlayerBasicsProvider());
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "player_research"), new AOTDPlayerResearchProvider());
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "player_void_chest_data"), new AOTDPlayerVoidChestDataProvider());
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "player_nightmare_data"), new AOTDPlayerNightmareDataProvider());
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "player_spell_manager"), new AOTDPlayerSpellManagerProvider());
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "player_spell_freeze_data"), new AOTDPlayerSpellFreezeDataProvider());
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "player_spell_charm_data"), new AOTDPlayerSpellCharmDataProvider());
        }
    }

    /**
     * When an entity joins the world we perform a capability sync
     *
     * @param event The join event we will check
     */
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        // When the player joins the world
        if (event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) event.getEntity();

            // The server will have correct data, the client needs new data
            if (!event.getWorld().isRemote)
            {
                entityPlayer.getCapability(ModCapabilities.PLAYER_BASICS, null).syncAll(entityPlayer);
                entityPlayer.getCapability(ModCapabilities.PLAYER_RESEARCH, null).sync(entityPlayer, false);
                // Dont sync PLAYER_VOID_CHEST_DATA because it's server side only storage!
                // Dont sync PLAYER_NIGHTMARE_DATA because it's server side only storage!
                entityPlayer.getCapability(ModCapabilities.PLAYER_SPELL_MANAGER, null).syncAll(entityPlayer);
                entityPlayer.getCapability(ModCapabilities.PLAYER_SPELL_FREEZE_DATA, null).sync(entityPlayer);
                // Dont sync PLAYER_SPELL_CHARM_DATA because it's server side only storage!
            }
        }
    }

    /**
     * When the player dies, he is cloned but no capabilities are copied by default, so we need to manually do that here
     *
     * @param event The clone event
     */
    @SubscribeEvent
    public void onClonePlayer(final PlayerEvent.Clone event)
    {
        // The player only loses capabilities upon death
        if (event.isWasDeath())
        {
            // Grab new and original player capabilities
            IAOTDPlayerBasics originalPlayerBasics = event.getOriginal().getCapability(ModCapabilities.PLAYER_BASICS, null);
            IAOTDPlayerBasics newPlayerBasics = event.getEntityPlayer().getCapability(ModCapabilities.PLAYER_BASICS, null);

            IAOTDPlayerResearch originalPlayerResearch = event.getOriginal().getCapability(ModCapabilities.PLAYER_RESEARCH, null);
            IAOTDPlayerResearch newPlayerResearch = event.getEntityPlayer().getCapability(ModCapabilities.PLAYER_RESEARCH, null);

            IAOTDPlayerVoidChestData originalPlayerVoidChestData = event.getOriginal().getCapability(ModCapabilities.PLAYER_VOID_CHEST_DATA, null);
            IAOTDPlayerVoidChestData newPlayerVoidChestData = event.getEntityPlayer().getCapability(ModCapabilities.PLAYER_VOID_CHEST_DATA, null);

            IAOTDPlayerNightmareData originalPlayerNightmareData = event.getOriginal().getCapability(ModCapabilities.PLAYER_NIGHTMARE_DATA, null);
            IAOTDPlayerNightmareData newPlayerNightmareData = event.getEntityPlayer().getCapability(ModCapabilities.PLAYER_NIGHTMARE_DATA, null);

            IAOTDPlayerSpellManager originalPlayerSpellManager = event.getOriginal().getCapability(ModCapabilities.PLAYER_SPELL_MANAGER, null);
            IAOTDPlayerSpellManager newPlayerSpellManager = event.getEntityPlayer().getCapability(ModCapabilities.PLAYER_SPELL_MANAGER, null);

            // Don't copy PLAYER_SPELL_FREEZE_DATA, if the player dies they aren't frozen anymore

            // Don't copy PLAYER_SPELL_CHARM_DATA, if the player dies they aren't charmed anymore

            // Grab the NBT compound off of the original capabilities
            NBTTagCompound originalPlayerBasicsNBT = (NBTTagCompound) ModCapabilities.PLAYER_BASICS.getStorage().writeNBT(ModCapabilities.PLAYER_BASICS, originalPlayerBasics, null);
            NBTTagCompound originalPlayerResearchNBT = (NBTTagCompound) ModCapabilities.PLAYER_RESEARCH.getStorage().writeNBT(ModCapabilities.PLAYER_RESEARCH, originalPlayerResearch, null);
            NBTTagCompound originalPlayerVoidChestDataNBT = (NBTTagCompound) ModCapabilities.PLAYER_VOID_CHEST_DATA.getStorage().writeNBT(ModCapabilities.PLAYER_VOID_CHEST_DATA, originalPlayerVoidChestData, null);
            NBTTagCompound originalPlayerNightmareDataNBT = (NBTTagCompound) ModCapabilities.PLAYER_NIGHTMARE_DATA.getStorage().writeNBT(ModCapabilities.PLAYER_NIGHTMARE_DATA, originalPlayerNightmareData, null);
            NBTTagCompound originalPlayerSpellManagerNBT = (NBTTagCompound) ModCapabilities.PLAYER_SPELL_MANAGER.getStorage().writeNBT(ModCapabilities.PLAYER_SPELL_MANAGER, originalPlayerSpellManager, null);

            // Copy the NBT compound onto the new capabilities
            ModCapabilities.PLAYER_BASICS.getStorage().readNBT(ModCapabilities.PLAYER_BASICS, newPlayerBasics, null, originalPlayerBasicsNBT);
            ModCapabilities.PLAYER_RESEARCH.getStorage().readNBT(ModCapabilities.PLAYER_RESEARCH, newPlayerResearch, null, originalPlayerResearchNBT);
            ModCapabilities.PLAYER_VOID_CHEST_DATA.getStorage().readNBT(ModCapabilities.PLAYER_VOID_CHEST_DATA, newPlayerVoidChestData, null, originalPlayerVoidChestDataNBT);
            ModCapabilities.PLAYER_NIGHTMARE_DATA.getStorage().readNBT(ModCapabilities.PLAYER_NIGHTMARE_DATA, newPlayerNightmareData, null, originalPlayerNightmareDataNBT);
            ModCapabilities.PLAYER_SPELL_MANAGER.getStorage().readNBT(ModCapabilities.PLAYER_SPELL_MANAGER, newPlayerSpellManager, null, originalPlayerSpellManagerNBT);
        }
    }
}
