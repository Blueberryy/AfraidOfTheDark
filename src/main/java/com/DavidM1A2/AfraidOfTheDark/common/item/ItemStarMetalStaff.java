/*
 * Author: David Slovikosky 
 * Mod: Afraid of the Dark 
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.item;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.DavidM1A2.AfraidOfTheDark.AfraidOfTheDark;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModCapabilities;
import com.DavidM1A2.AfraidOfTheDark.common.item.core.AOTDItemWithCooldownStatic;
import com.DavidM1A2.AfraidOfTheDark.common.reference.ResearchTypes;
import com.DavidM1A2.AfraidOfTheDark.common.utility.UnitConverterUtility;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemStarMetalStaff extends AOTDItemWithCooldownStatic
{
	private static final int MAX_TROLL_POLE_TIME_IN_TICKS = 60;

	public ItemStarMetalStaff()
	{
		super();
		this.setUnlocalizedName("starMetalStaff");
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer)
	{
		if (entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null).isResearched(ResearchTypes.StarMetal))
		{
			if (!this.isOnCooldown(itemStack))
			{
				if (!entityPlayer.capabilities.isCreativeMode)
				{
					entityPlayer.capabilities.disableDamage = true;
					AfraidOfTheDark.instance.getDelayedExecutor().schedule(new Runnable()
					{
						@Override
						public void run()
						{
							entityPlayer.capabilities.disableDamage = false;
						}
					}, UnitConverterUtility.ticksToMilliseconds(MAX_TROLL_POLE_TIME_IN_TICKS), TimeUnit.MILLISECONDS);
				}
				entityPlayer.addVelocity(0, .5, 0);
				this.setOnCooldown(itemStack, entityPlayer);
				if (!world.isRemote)
					entityPlayer.setItemInUse(itemStack, ItemStarMetalStaff.MAX_TROLL_POLE_TIME_IN_TICKS);
			}
			else
			{
				if (!world.isRemote)
					if (entityPlayer.getItemInUse() != itemStack)
						entityPlayer.addChatMessage(new ChatComponentText("Cooldown remaining: " + this.cooldownRemaining(itemStack) + " second" + (this.cooldownRemaining(itemStack) - 1 == 0.0 ? "." : "s.")));
			}
		}
		else
		{
			if (!world.isRemote)
				entityPlayer.addChatMessage(new ChatComponentText("I'm not sure what this is used for."));
		}

		return super.onItemRightClick(itemStack, world, entityPlayer);
	}

	/**
	 * Called each tick while using an item.
	 * 
	 * @param stack
	 *            The Item being used
	 * @param player
	 *            The Player using the item
	 * @param count
	 *            The amount of time in tick the item has been used for continuously
	 */
	@Override
	public void onUsingTick(final ItemStack stack, final EntityPlayer entityPlayer, int count)
	{
		if (!entityPlayer.worldObj.isRemote)
		{
			count = ItemStarMetalStaff.MAX_TROLL_POLE_TIME_IN_TICKS - count;
			if (count == 1)
				entityPlayer.fallDistance = 0.0f;
			if (count >= 3)
				((EntityPlayerMP) entityPlayer).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(entityPlayer.getEntityId(), 0, 0, 0));
			if (count == (ItemStarMetalStaff.MAX_TROLL_POLE_TIME_IN_TICKS - 1))
				entityPlayer.stopUsingItem();
		}
	}

	/**
	 * Called when the player stops using an Item (stops holding the right mouse button).
	 *
	 * @param timeLeft
	 *            The amount of ticks left before the using would have been complete
	 */
	@Override
	public void onPlayerStoppedUsing(final ItemStack stack, final World worldIn, final EntityPlayer entityPlayer, final int timeLeft)
	{
		if (!entityPlayer.capabilities.isCreativeMode)
			entityPlayer.capabilities.disableDamage = false;
		if (timeLeft < 5)
		{
			List entityList = worldIn.getEntitiesWithinAABBExcludingEntity(entityPlayer, entityPlayer.getEntityBoundingBox().expand(10, 10, 10));
			for (Object entityObject : entityList)
			{
				if (entityObject instanceof EntityPlayer || entityObject instanceof EntityLiving)
				{
					EntityLivingBase entity = (EntityLivingBase) entityObject;

					double knockbackStrength = 2;

					double motionX = entityPlayer.getPosition().getX() - entity.getPosition().getX();
					double motionZ = entityPlayer.getPosition().getZ() - entity.getPosition().getZ();

					double hypotenuse = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);

					entity.addVelocity(-motionX * knockbackStrength * 0.6000000238418579D / hypotenuse, 0.1D, -motionZ * knockbackStrength * 0.6000000238418579D / hypotenuse);
				}
			}
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		return super.onItemUseFinish(stack, worldIn, playerIn);
	}

	@Override
	public int getMaxItemUseDuration(final ItemStack stack)
	{
		return ItemStarMetalStaff.MAX_TROLL_POLE_TIME_IN_TICKS;
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 *
	 * @param tooltip
	 *            All lines to display in the Item's tooltip. This is a List of Strings.
	 * @param advanced
	 *            Whether the setting "Advanced tooltips" is enabled
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, final EntityPlayer entityPlayer, final List tooltip, final boolean advanced)
	{
		if (entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null).isResearched(ResearchTypes.StarMetal))
		{
			tooltip.add("Right click for temporary invincibility");
			tooltip.add("followed by an AOE knockback.");
			tooltip.add("Cooldown: " + this.getItemCooldownInTicks() / 20 + " seconds.");
		}
		else
		{
			tooltip.add("I'm not sure how to use this.");
		}
	}

	@Override
	public int getItemCooldownInTicks()
	{
		return 240;
	}

	@Override
	public int getItemCooldownInTicks(ItemStack itemStack)
	{
		return 240;
	}
}
