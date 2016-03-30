/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.block.core;

import com.DavidM1A2.AfraidOfTheDark.common.reference.Reference;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public abstract class AOTDStairs extends BlockStairs
{
	protected AOTDStairs(final IBlockState modelState)
	{
		super(modelState);
		this.setUnlocalizedName("FORGOT TO SET");
		this.setCreativeTab(Reference.AFRAID_OF_THE_DARK);
		this.useNeighborBrightness = true;
	}

	@Override
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", Reference.MOD_ID.toLowerCase() + ":", this.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
		// Format for a block is: tile.modid:blockname.name
	}

	// Get the unlocalized name
	protected String getUnwrappedUnlocalizedName(final String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
}
