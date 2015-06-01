/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.entities.Bolts;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.DavidM1A2.AfraidOfTheDark.initializeMod.ModItems;

// The bolt is a throwable entity and does Generic damage.
public abstract class EntityBolt extends EntityThrowable
{
	private int damageAmount = 6;
	private Item myDrop = ModItems.ironBolt;
	private double myChanceToDropHitEntity = .4;
	private double myChanceToDropHitGround = .8;
	protected final EntityLivingBase myDamageSource;

	public EntityBolt(final World world)
	{
		super(world);
		myDamageSource = null;
	}

	public EntityBolt(final World world, final EntityLivingBase entityLivingBase)
	{
		super(world, entityLivingBase);
		myDamageSource = entityLivingBase;
	}

	public EntityBolt(final World world, final double x, final double y, final double z)
	{
		super(world, x, y, z);
		myDamageSource = null;
	}

	// The bolt may drop depending on if the chance to drop is great enough
	@Override
	protected void onImpact(final MovingObjectPosition movingObjectPosition)
	{
		final Entity entityHit = movingObjectPosition.entityHit;

		if (!this.worldObj.isRemote)
		{
			if (entityHit != null)
			{
				if (myDamageSource instanceof EntityPlayer)
				{
					entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) myDamageSource), this.damageAmount);
				}

				if (Math.random() < this.myChanceToDropHitEntity)
				{
					entityHit.dropItem(this.myDrop, 1);
				}
			}
			else
			{
				if (Math.random() < this.myChanceToDropHitGround)
				{
					this.dropItem(this.myDrop, 1);
				}
			}
		}

		this.setDead();
	}

	// Set the damage of this bolt
	public void setDamage(final int damage)
	{
		this.damageAmount = damage;
	}

	// Set the type of bolt
	public void setMyType(final Item item)
	{
		this.myDrop = item;
	}

	// Set the chance to drop if the bolt hit the ground
	public void setChanceToDropHitGround(final double chance)
	{
		this.myChanceToDropHitGround = chance;
	}

	// Set the chance to drop if the bolt hit an entity
	public void setChanceToDropHitEntity(final double chance)
	{
		this.myChanceToDropHitEntity = chance;
	}

	public float getDamage()
	{
		return this.damageAmount;
	}

	public double getMyChanceToDropHitEntity()
	{
		return this.myChanceToDropHitEntity;
	}

	public double getMyChanceToDropHitGround()
	{
		return this.myChanceToDropHitGround;
	}

	public Item getMyType()
	{
		return this.myDrop;
	}
}
