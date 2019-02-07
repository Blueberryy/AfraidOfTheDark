package com.DavidM1A2.afraidofthedark.common.capabilities.player.dimension;

import com.DavidM1A2.afraidofthedark.common.capabilities.player.basics.IAOTDPlayerBasics;
import com.DavidM1A2.afraidofthedark.common.constants.ModCapabilities;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Class responsible for providing a capability to a player
 */
public class AOTDPlayerVoidChestDataProvider implements ICapabilitySerializable<NBTBase>
{
	// The instance of the player void chest data capability
	private IAOTDPlayerVoidChestData instance = ModCapabilities.PLAYER_VOID_CHEST_DATA.getDefaultInstance();

	/**
	 * Tests if the given capability is the player void chest data capability
	 *
	 * @param capability The capability to test
	 * @param facing ignored
	 * @return True if the capability is a player void chest data capability, false otherwise
	 */
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == ModCapabilities.PLAYER_VOID_CHEST_DATA;
	}

	/**
	 * Getter for a capability
	 *
	 * @param capability The capability to get
	 * @param facing ignored
	 * @param <T> The type of capability
	 * @return The capability or null if it was the wrong type
	 */
	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		return capability == ModCapabilities.PLAYER_VOID_CHEST_DATA ? ModCapabilities.PLAYER_VOID_CHEST_DATA.cast(this.instance) : null;
	}

	/**
	 * Serializes this capability to NBT using the storage's write to NBT method
	 *
	 * @return The NBTTagCompound representing this capability
	 */
	@Override
	public NBTBase serializeNBT()
	{
		return ModCapabilities.PLAYER_VOID_CHEST_DATA.getStorage().writeNBT(ModCapabilities.PLAYER_VOID_CHEST_DATA, this.instance, null);
	}

	/**
	 * Deserializes this capability into the default storage using the read from NBT method
	 *
	 * @param nbt The NBT tag compound to read from
	 */
	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		ModCapabilities.PLAYER_VOID_CHEST_DATA.getStorage().readNBT(ModCapabilities.PLAYER_VOID_CHEST_DATA, this.instance, null, nbt);
	}
}
