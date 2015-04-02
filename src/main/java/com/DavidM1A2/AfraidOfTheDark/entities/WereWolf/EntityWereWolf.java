/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.entities.WereWolf;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.DavidM1A2.AfraidOfTheDark.AI.CustomWerewolfTargetLocator;
import com.DavidM1A2.AfraidOfTheDark.refrence.Refrence;

// Define a new werewolf
public class EntityWereWolf extends EntityMob
{
	// setup movespeed, agroRange, and followRange
	private static double moveSpeed = .43D;
	private static double agroRange = 16.0D;
	private static double followRange = 32.0D;

	// AI wanderer and watcher
	private EntityAIWander myWanderer = new EntityAIWander(this, moveSpeed * 10);
	private EntityAIWatchClosest myWatchClosest = new EntityAIWatchClosest(this, EntityPlayer.class, (float) agroRange);

	public EntityWereWolf(World world)
	{
		// Set the model size
		super(world);
		this.setSize(.7F, 2);

		// Add various AI tasks
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		this.tasks.addTask(7, myWanderer);
		this.tasks.addTask(8, myWatchClosest);
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		// Use custom werewolf target locator
		this.targetTasks.addTask(2, new CustomWerewolfTargetLocator(this, EntityPlayer.class, 0, true));
	}

	// Apply entity attributes
	@Override
	protected void applyEntityAttributes()
	{
		if (this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth) == null)
		{
			this.getAttributeMap().registerAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
		}
		if (this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange) == null)
		{
			this.getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange).setBaseValue(followRange);
		}
		if (this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.knockbackResistance) == null)
		{
			this.getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		}
		if (this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed) == null)
		{
			this.getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(moveSpeed);
		}
		if (this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage) == null)
		{
			this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);
		}
	}

	// Get werewolf sound
	@Override
	protected String getLivingSound()
	{
		return "afraidofthedark:werewolf";
	}

	// Get werewolf's volume
	@Override
	protected float getSoundVolume()
	{
		return 1.0F;
	}

	// Get the AI movespeed
	@Override
	public float getAIMoveSpeed()
	{
		return (float) moveSpeed;
	}

	// We are using custom AI
	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	// Only take damage from silver weapons
	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float damage)
	{
		if (damageSource.damageType.equals(Refrence.silverWeapon.damageType) || damageSource.damageType.equals(DamageSource.outOfWorld))
		{
			if (this.isEntityInvulnerable())
			{
				return false;
			}
			else
			{
				return super.attackEntityFrom(damageSource, damage);
			}
		}
		else
		{
			return super.attackEntityFrom(DamageSource.generic, 1);
		}
	}

	// This is used to set movespeed and agro range during full moons
	public static void setMoveSpeedAndAgroRange(double _moveSpeed, double _agroRange, double _followRange)
	{
		moveSpeed = _moveSpeed;
		agroRange = _agroRange;
		followRange = _followRange;
	}

	// Various getters and setters
	public EntityAIWander getWanderer()
	{
		return myWanderer;
	}

	public void setWanderer()
	{
		myWanderer = new EntityAIWander(this, moveSpeed * 10);
	}

	public EntityAIWatchClosest getMyWatchClosest()
	{
		return myWatchClosest;
	}

	public void setMyWatchClosest()
	{
		myWatchClosest = new EntityAIWatchClosest(this, EntityPlayer.class, (float) agroRange);
	}
}
