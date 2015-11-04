/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.block;

import java.util.Random;

import com.DavidM1A2.AfraidOfTheDark.common.block.core.AOTDBlockTileEntity;
import com.DavidM1A2.AfraidOfTheDark.common.block.tileEntity.TileEntitySpring;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSpring extends AOTDBlockTileEntity
{
	public BlockSpring(Material material)
	{
		super(material);
		this.setUnlocalizedName("spring");
		this.setHardness(10.0F);
		this.setResistance(50.0F);
		this.setHarvestLevel("pickaxe", 3);
	}

	@Override
	protected boolean displayInCreative()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntitySpring();
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}
}
