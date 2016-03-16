/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.debug;

import java.util.ArrayList;
import java.util.UUID;

import com.DavidM1A2.AfraidOfTheDark.client.gui.GuiHandler;
import com.DavidM1A2.AfraidOfTheDark.common.item.core.AOTDItem;
import com.DavidM1A2.AfraidOfTheDark.common.refrence.Refrence;
import com.DavidM1A2.AfraidOfTheDark.common.savedData.AOTDPlayerData;
import com.DavidM1A2.AfraidOfTheDark.common.spell.Spell;
import com.DavidM1A2.AfraidOfTheDark.common.spell.SpellRegistry;
import com.DavidM1A2.AfraidOfTheDark.common.spell.SpellStage;
import com.DavidM1A2.AfraidOfTheDark.common.spell.effects.IEffect;
import com.DavidM1A2.AfraidOfTheDark.common.utility.LogHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWorldGenTest extends AOTDItem
{
	public ItemWorldGenTest()
	{
		super();
		this.setUnlocalizedName("worldGenTest");
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if (!entityPlayer.isSneaking())
			entityPlayer.openGui(Refrence.MOD_ID, GuiHandler.SPELL_SELECTION_ID, world, entityPlayer.getPosition().getX(), entityPlayer.getPosition().getY(), entityPlayer.getPosition().getZ());
		else
		{
			final ArrayList<IEffect> effects = new ArrayList<IEffect>()
			{
				{
					add(SpellRegistry.getEffect("explosion"));
				}
			};
			SpellStage[] stages = new SpellStage[]
			{ new SpellStage(SpellRegistry.getDeliveryMethod("projectile"), effects) };
			if (entityPlayer.worldObj.isRemote)
			{
				Spell temp = new Spell("Hello World, " + Double.toString(Math.random()).substring(0, 5), SpellRegistry.getPowerSource("projectile"), stages, UUID.randomUUID());
				LogHelper.info("Adding spell " + temp.getName());
				AOTDPlayerData.get(entityPlayer).getSpellManager().addSpell(temp);
				AOTDPlayerData.get(entityPlayer).syncSpellManager();
			}
		}

		return super.onItemRightClick(itemStack, world, entityPlayer);
	}
}
