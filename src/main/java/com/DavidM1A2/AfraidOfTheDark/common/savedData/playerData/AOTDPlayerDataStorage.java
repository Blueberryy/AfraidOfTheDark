package com.DavidM1A2.AfraidOfTheDark.common.savedData.playerData;

import com.DavidM1A2.AfraidOfTheDark.common.spell.SpellManager;
import com.DavidM1A2.AfraidOfTheDark.common.utility.LogHelper;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AOTDPlayerDataStorage implements IStorage<IAOTDPlayerData>
{
	private final static String HAS_STARTED_AOTD = "playerStartedAOTD";
	private final static String PLAYER_INSANITY = "PlayerInsanity";
	private final static String INVENTORY_SAVER = "inventorySaver";
	private final static String PLAYER_LOCATION_OVERWORLD = "playerLocationOverworld";
	private final static String PLAYER_LOCATION_NIGHTMARE = "playerLocationNightmare";
	private final static String PLAYER_LOCATION_VOID_CHEST = "playerLocationVoidChest";
	private final static String RESEARCH_DATA = "unlockedResearches";
	private final static String HAS_BEATEN_ENARIA = "hasBeatenEnaria";
	private final static String SELECTED_WRIST_CROSSBOW_BOLT = "selectedWristCrossbowBolt";
	private final static String SPELL_MANAGER = "spellManager";

	@Override
	public NBTBase writeNBT(Capability<IAOTDPlayerData> capability, IAOTDPlayerData instance, EnumFacing side)
	{
		NBTTagCompound compound = new NBTTagCompound();
		compound.setBoolean(HAS_STARTED_AOTD, instance.getHasStartedAOTD());
		compound.setDouble(PLAYER_INSANITY, instance.getPlayerInsanity());
		compound.setTag(INVENTORY_SAVER, instance.getPlayerInventory());
		compound.setIntArray(PLAYER_LOCATION_OVERWORLD, instance.getPlayerLocationOverworld());
		compound.setInteger(PLAYER_LOCATION_NIGHTMARE, instance.getPlayerLocationNightmare());
		compound.setTag(RESEARCH_DATA, instance.getResearches());
		compound.setInteger(PLAYER_LOCATION_VOID_CHEST, instance.getPlayerLocationVoidChest());
		compound.setBoolean(HAS_BEATEN_ENARIA, instance.getHasBeatenEnaria());
		compound.setInteger(SELECTED_WRIST_CROSSBOW_BOLT, instance.getSelectedWristCrossbowBolt());
		instance.getSpellManager().writeToNBT(compound);
		return compound;
	}

	@Override
	public void readNBT(Capability<IAOTDPlayerData> capability, IAOTDPlayerData instance, EnumFacing side, NBTBase nbt)
	{
		if (nbt instanceof NBTTagCompound)
		{
			NBTTagCompound compound = (NBTTagCompound) nbt;
			instance.setHasStartedAOTD(compound.getBoolean(HAS_STARTED_AOTD));
			instance.setPlayerInsanity(compound.getDouble(PLAYER_INSANITY));
			instance.setPlayerInventory(compound.getTagList(INVENTORY_SAVER, 10));
			instance.setPlayerLocationOverworld(compound.getIntArray(PLAYER_LOCATION_OVERWORLD));
			instance.setPlayerLocationNightmare(compound.getInteger(PLAYER_LOCATION_NIGHTMARE));
			instance.setReseraches((NBTTagCompound) compound.getTag(RESEARCH_DATA));
			instance.setPlayerLocationVoidChest(compound.getInteger(PLAYER_LOCATION_VOID_CHEST));
			instance.setHasBeatenEnaria(compound.getBoolean(HAS_BEATEN_ENARIA));
			instance.setSelectedWristCrossbowBolt(compound.getInteger(SELECTED_WRIST_CROSSBOW_BOLT));
			SpellManager spellManager = new SpellManager();
			spellManager.readFromNBT(compound);
			instance.setSpellManager(spellManager);
		}
		else
		{
			LogHelper.error("Didn't get NBTTagCompound, wtf?");
		}
	}
}