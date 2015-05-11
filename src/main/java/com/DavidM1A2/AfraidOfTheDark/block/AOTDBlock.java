/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.DavidM1A2.AfraidOfTheDark.refrence.Refrence;

/*
 * This will be the base for all of our modded blocks
 */
public abstract class AOTDBlock extends Block
{
	// private static final PropertyBool VARIANT = PropertyBool.create("variant");

	public AOTDBlock(Material material)
	{
		// Set the material and creative tab
		super(material);
		this.setCreativeTab(Refrence.AFRAID_OF_THE_DARK);
		this.setUnlocalizedName("FORGOT TO SET");
	}

	// Default material is rock (stone)
	public AOTDBlock()
	{
		this(Material.rock);
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[]
		{});
	}

	// the block will render in the SOLID layer. See http://greyminecraftcoder.blogspot.co.at/2014/12/block-rendering-18.html for more information.
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.SOLID;
	}

	// used by the renderer to control lighting and visibility of other blocks.
	// set to true because this block is opaque and occupies the entire 1x1x1 space
	// not strictly required because the default (super method) is true
	@Override
	public boolean isOpaqueCube()
	{
		return true;
	}

	// used by the renderer to control lighting and visibility of other blocks, also by
	// (eg) wall or fence to control whether the fence joins itself to this block
	// set to true because this block occupies the entire 1x1x1 space
	// not strictly required because the default (super method) is true
	@Override
	public boolean isFullCube()
	{
		return true;
	}

	// render using a BakedModel (mbe01_block_simple.json --> mbe01_block_simple_model.json)
	// not strictly required because the default (super method) is 3.
	@Override
	public int getRenderType()
	{
		return 3;
	}

	@Override
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", Refrence.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
		// Format for a block is: tile.modid:blockname.name
	}

	// Get the unlocalized name
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
}