/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.handler;

import com.DavidM1A2.AfraidOfTheDark.AfraidOfTheDark;
import com.DavidM1A2.AfraidOfTheDark.client.settings.ClientData;
import com.DavidM1A2.AfraidOfTheDark.common.entities.DeeeSyft.EntityDeeeSyft;
import com.DavidM1A2.AfraidOfTheDark.common.entities.Enaria.EntityEnaria;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModCapabilities;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModPotionEffects;
import com.DavidM1A2.AfraidOfTheDark.common.item.ItemFlaskOfSouls;
import com.DavidM1A2.AfraidOfTheDark.common.packets.PlaySoundAtPlayer;
import com.DavidM1A2.AfraidOfTheDark.common.reference.AOTDDimensions;
import com.DavidM1A2.AfraidOfTheDark.common.reference.AOTDPlayerFollowSounds;
import com.DavidM1A2.AfraidOfTheDark.common.reference.Reference;
import com.DavidM1A2.AfraidOfTheDark.common.reference.ResearchTypes;
import com.DavidM1A2.AfraidOfTheDark.common.savedData.entityData.AOTDEntityData;
import com.DavidM1A2.AfraidOfTheDark.common.savedData.playerData.AOTDPlayerData;
import com.DavidM1A2.AfraidOfTheDark.common.utility.NBTHelper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerController
{
	// When the player dies, he is cloned. We move over extended properties such
	// as hasStartedAOTD, insanity, and research
	@SubscribeEvent
	public void onClonePlayer(final PlayerEvent.Clone event)
	{
		NBTTagCompound playerData = (NBTTagCompound) ModCapabilities.PLAYER_DATA.getStorage().writeNBT(ModCapabilities.PLAYER_DATA, event.original.getCapability(ModCapabilities.PLAYER_DATA, null), null);
		NBTTagCompound entityPlayerData = (NBTTagCompound) ModCapabilities.ENTITY_DATA.getStorage().writeNBT(ModCapabilities.ENTITY_DATA, event.original.getCapability(ModCapabilities.ENTITY_DATA, null), null);

		ModCapabilities.PLAYER_DATA.getStorage().readNBT(ModCapabilities.PLAYER_DATA, event.entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null), null, playerData);
		ModCapabilities.ENTITY_DATA.getStorage().readNBT(ModCapabilities.ENTITY_DATA, event.entityPlayer.getCapability(ModCapabilities.ENTITY_DATA, null), null, entityPlayerData);
	}

	@SubscribeEvent
	public void onPlayerRespawnEvent(PlayerRespawnEvent event)
	{
		if (!event.player.worldObj.isRemote)
		{
			if (event.player.dimension == AOTDDimensions.Nightmare.getWorldID())
			{
				AOTDDimensions.Nightmare.fromDimensionTo(event.player.getCapability(ModCapabilities.PLAYER_DATA, null).getPlayerDimensionPreTeleport(), (EntityPlayerMP) event.player);
			}
			else if (event.player.dimension == AOTDDimensions.VoidChest.getWorldID())
			{
				int locationX = event.player.getCapability(ModCapabilities.PLAYER_DATA, null).getPlayerLocationVoidChest() * AOTDDimensions.getBlocksBetweenIslands();
				((EntityPlayerMP) event.player).playerNetServerHandler.setPlayerLocation(locationX + 24.5, 104, 3, 0, 0);
			}
		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(final EntityJoinWorldEvent event)
	{
		// When the player joins the world
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer entityPlayer = (EntityPlayer) event.entity;
			/*
			 * Sync player research, insanity, and AOTDStart status
			 */
			entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null).requestSyncAll();
			entityPlayer.getCapability(ModCapabilities.ENTITY_DATA, null).requestSyncAll();

			if (!event.entity.worldObj.isRemote)
				if (entityPlayer.dimension == AOTDDimensions.Nightmare.getWorldID())
				{
					AfraidOfTheDark.instance.getPacketHandler().sendTo(new PlaySoundAtPlayer(60, AOTDPlayerFollowSounds.ErieEcho), (EntityPlayerMP) entityPlayer);
					AfraidOfTheDark.instance.getPacketHandler().sendTo(new PlaySoundAtPlayer(entityPlayer.worldObj.rand.nextInt(10 * 20) + 10 * 20, AOTDPlayerFollowSounds.BellsRinging), (EntityPlayerMP) entityPlayer);
				}
		}
		else if (event.entity instanceof EntityEnaria)
		{
			if (!event.entity.getEntityData().getBoolean(EntityEnaria.IS_VALID))
			{
				event.entity.onKillCommand();
			}
		}
	}

	@SubscribeEvent
	public void onAttachCapabilitiesEntity(AttachCapabilitiesEvent.Entity event)
	{
		if (event.getEntity() instanceof EntityLivingBase && !(event.getEntity() instanceof EntityArmorStand))
			event.addCapability(new ResourceLocation(Reference.MOD_ID + ":entityData"), new AOTDEntityData(event.getEntity()));

		if (event.getEntity() instanceof EntityPlayer)
			event.addCapability(new ResourceLocation(Reference.MOD_ID + ":playerData"), new AOTDPlayerData((EntityPlayer) event.getEntity()));
	}

	@SubscribeEvent
	public void onPlayerSleepInBedEvent(PlayerSleepInBedEvent event)
	{
		if (!event.entityPlayer.worldObj.isRemote)
		{
			EntityPlayerMP entityPlayer = (EntityPlayerMP) event.entityPlayer;
			if (event.entityPlayer.getActivePotionEffect(ModPotionEffects.sleepingPotion) != null)
			{
				if (event.entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null).canResearch(ResearchTypes.Nightmares))
				{
					event.entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null).unlockResearch(ResearchTypes.Nightmares, true);
				}
				if (event.entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null).isResearched(ResearchTypes.Nightmares))
				{
					AOTDDimensions.Nightmare.toDimension(entityPlayer);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderGameOverlayEventChat(final RenderGameOverlayEvent.Chat event)
	{
		if (ClientData.researchAchievedOverlay != null)
		{
			ClientData.researchAchievedOverlay.updateResearchAchievedWindow();
		}
	}

	@SubscribeEvent
	public void onEntityInteractEvent(final EntityInteractEvent event)
	{
		if (event.target instanceof EntityDeeeSyft)
		{
			if (event.entityPlayer.inventory.getCurrentItem() != null)
			{
				if (event.entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemFlintAndSteel)
				{
					event.target.setFire(1);
				}
			}
		}
	}

	@SubscribeEvent
	public void onItemTooltipEvent(ItemTooltipEvent event)
	{
		if (event.itemStack.isItemEnchanted())
		{
			NBTTagList enchantments = event.itemStack.getEnchantmentTagList();
			for (int i = 0; i < enchantments.tagCount(); i++)
			{
				if (enchantments.get(i) instanceof NBTTagCompound)
				{
					Integer enchantment = ((NBTTagCompound) enchantments.get(i)).getInteger("id");
					if (enchantment == Enchantment.fireProtection.effectId || enchantment == Enchantment.blastProtection.effectId || enchantment == Enchantment.projectileProtection.effectId || enchantment == Enchantment.smite.effectId || enchantment == Enchantment.baneOfArthropods.effectId)
					{
						if (event.entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null).canResearch(ResearchTypes.VitaeDisenchanter))
						{
							event.entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null).unlockResearch(ResearchTypes.VitaeDisenchanter, true);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onItemCraftedEvent(ItemCraftedEvent event)
	{
		if (event.crafting.getItem() instanceof ItemFlaskOfSouls)
		{
			if (!event.player.worldObj.isRemote)
			{
				if (event.player.getCapability(ModCapabilities.PLAYER_DATA, null).canResearch(ResearchTypes.PhylacteryOfSouls))
				{
					event.player.getCapability(ModCapabilities.PLAYER_DATA, null).unlockResearch(ResearchTypes.PhylacteryOfSouls, true);
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if (event.source.getEntity() instanceof EntityPlayer)
		{
			if (ItemFlaskOfSouls.flaskKillRequirements.containsKey(event.entityLiving.getClass().getSimpleName()))
			{
				EntityPlayer entityPlayer = (EntityPlayer) event.source.getEntity();
				for (int i = 0; i < 9; i++)
				{
					if (entityPlayer.inventory.mainInventory[i] != null)
					{
						ItemStack itemStack = entityPlayer.inventory.mainInventory[i];
						if (itemStack.getItem() instanceof ItemFlaskOfSouls)
						{
							if (entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null).isResearched(ResearchTypes.PhylacteryOfSouls))
							{
								if (NBTHelper.getString(itemStack, ItemFlaskOfSouls.FLASK_TYPE).equals(""))
								{
									NBTHelper.setString(itemStack, ItemFlaskOfSouls.FLASK_TYPE, event.entityLiving.getClass().getSimpleName());
									NBTHelper.setInteger(itemStack, ItemFlaskOfSouls.KILLS, 1);
									break;
								}
								else if (NBTHelper.getString(itemStack, ItemFlaskOfSouls.FLASK_TYPE).equals(event.entityLiving.getClass().getSimpleName()))
								{
									if (itemStack.getItemDamage() == 0)
									{
										int newKills = NBTHelper.getInt(itemStack, ItemFlaskOfSouls.KILLS) + 1;
										if (newKills == ItemFlaskOfSouls.flaskKillRequirements.get(event.entityLiving.getClass().getSimpleName()))
										{
											itemStack.setItemDamage(1);
											NBTHelper.setInteger(itemStack, ItemFlaskOfSouls.KILLS, newKills);
										}
										else
										{
											NBTHelper.setInteger(itemStack, ItemFlaskOfSouls.KILLS, newKills);
										}
										break;
									}
								}
							}
							else
							{
								if (!entityPlayer.worldObj.isRemote)
								{
									entityPlayer.addChatMessage(new ChatComponentText("This flask is trying to interact with the kill I just got but something's wrong."));
								}
								break;
							}
						}
					}
				}
			}
		}
	}
}
