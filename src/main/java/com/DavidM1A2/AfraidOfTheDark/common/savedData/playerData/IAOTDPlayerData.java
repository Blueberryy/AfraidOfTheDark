package com.DavidM1A2.AfraidOfTheDark.common.savedData.playerData;

import com.DavidM1A2.AfraidOfTheDark.AfraidOfTheDark;
import com.DavidM1A2.AfraidOfTheDark.client.settings.ClientData;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModItems;
import com.DavidM1A2.AfraidOfTheDark.common.packets.SyncAOTDPlayerData;
import com.DavidM1A2.AfraidOfTheDark.common.packets.SyncSelectedWristCrossbowBolt;
import com.DavidM1A2.AfraidOfTheDark.common.packets.SyncSpellManager;
import com.DavidM1A2.AfraidOfTheDark.common.packets.UpdateAOTDStatus;
import com.DavidM1A2.AfraidOfTheDark.common.packets.UpdateHasBeatenEnaria;
import com.DavidM1A2.AfraidOfTheDark.common.packets.UpdateInsanity;
import com.DavidM1A2.AfraidOfTheDark.common.packets.UpdateResearch;
import com.DavidM1A2.AfraidOfTheDark.common.reference.ResearchTypes;
import com.DavidM1A2.AfraidOfTheDark.common.spell.SpellManager;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;

public interface IAOTDPlayerData
{
	public boolean getHasStartedAOTD();
	public void setHasStartedAOTD(boolean hasStartedAOTD);
	public void syncHasStartedAOTD();

	public double getPlayerInsanity();
	public void setPlayerInsanity(double insanity);
	public void syncPlayerInsanity();

	public NBTTagList getPlayerInventory();
	public void setPlayerInventory(NBTTagList inventory);
	// Auto synced
	
	public int[] getPlayerLocationOverworld();
	public void setPlayerLocationOverworld(int[] location);
	
	public int getPlayerLocationNightmare();	
	public void setPlayerLocationNightmare(int location);

	public int getPlayerLocationVoidChest();
	public void setPlayerLocationVoidChest(int location);

	public NBTTagCompound getResearches();
	public void setReseraches(NBTTagCompound researches);
	public boolean isResearched(ResearchTypes research);
	public boolean canResearch(ResearchTypes research);
	public void unlockResearch(ResearchTypes research, boolean firstTimeResearched);
	public void syncResearches();

	public boolean getHasBeatenEnaria();
	public void setHasBeatenEnaria(boolean hasBeatenEnaria);
	public void syncHasBeatenEnaria();

	public void setSelectedWristCrossbowBolt(int selectedWristCrossbowBolt);
	public int getSelectedWristCrossbowBolt();
	public void syncSelectedWristCrossbowBolt();
	
	public SpellManager getSpellManager();
	public void setSpellManager(SpellManager spellManager);
	public void syncSpellManager();

	public void syncAll();
	public void requestSyncAll();
}