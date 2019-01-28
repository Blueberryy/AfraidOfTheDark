package com.DavidM1A2.afraidofthedark.common.entity.werewolf;

import com.DavidM1A2.afraidofthedark.common.capabilities.player.research.IAOTDPlayerResearch;
import com.DavidM1A2.afraidofthedark.common.constants.ModCapabilities;
import com.DavidM1A2.afraidofthedark.common.constants.ModItems;
import com.DavidM1A2.afraidofthedark.common.constants.ModResearches;
import com.DavidM1A2.afraidofthedark.common.constants.ModSounds;
import com.DavidM1A2.afraidofthedark.common.entity.mcAnimatorLib.IMCAnimatedEntity;
import com.DavidM1A2.afraidofthedark.common.entity.mcAnimatorLib.animation.AnimationHandler;
import com.DavidM1A2.afraidofthedark.common.entity.werewolf.animation.AnimationHandlerWerewolf;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

/**
 * Class representing a werewolf entity
 */
public class EntityWerewolf extends EntityMob implements IMCAnimatedEntity
{
	// Animation handler used by the werewolf
	private final AnimationHandler animHandler = new AnimationHandlerWerewolf(this);

	// Constants defining werewolf parameters
	private static final double MOVE_SPEED = .43D;
	private static final double AGRO_RANGE = 16.0D;
	private static final double FOLLOW_RANGE = 32.0D;
	private static final double MAX_HEALTH = 40.0D;
	private static final double KNOCKBACK_RESISTANCE = 0.5D;
	private static final double ATTACK_DAMAGE = 20.0D;

	// Flag telling the werewolf if it is allowed to attack anyone or just players that have started AOTD
	private boolean attacksAnyone;

	// NBT tag for if the werewolf can attack anyone or just players that have started AOTD
	private static final String NBT_CAN_ATTACK_ANYONE = "can_attack_anyone";

	/**
	 * Constructor initializes the werewolf properties
	 *
	 * @param world The world the werewolf is a part of
	 */
	public EntityWerewolf(final World world)
	{
		super(world);
		// Set the hitbox size
		this.setSize(1.8F, 1.6F);
		// Ensure this werewolf does not attack anyone yet
		this.attacksAnyone = false;
		// This werewolf is worth 10xp
		this.experienceValue = 10;
	}

	@Override
	protected void initEntityAI()
	{
		// First priority is to swim and not drown
		this.tasks.addTask(1, new EntityAISwimming(this));
		// Second priority is to attack melee if possible
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		// If we can't attack or swim just wander around
		this.tasks.addTask(3, new EntityAIWander(this, MOVE_SPEED * 2));
		// If we don't wander just look at the closest entity
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, (float) AGRO_RANGE));
		// If nothing else triggers look idle
		this.tasks.addTask(5, new EntityAILookIdle(this));
		// For our target tasks we first test if we were hurt, and if so target what hurt us
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		// Then introduce our custom werewolf target locator
		this.targetTasks.addTask(2, new CustomWerewolfTargetLocator(this, 10, true));
	}

	/**
	 * Sets entity attributes such as max health and movespeed
	 */
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MAX_HEALTH);
		this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(FOLLOW_RANGE);
		this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(KNOCKBACK_RESISTANCE);
		this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(MOVE_SPEED);
		this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ATTACK_DAMAGE);
	}

	/**
	 * Update animations for this entity when update is called
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();

		// Animations only update client side
		if (world.isRemote)
			this.animHandler.animationsUpdate();
	}

	/**
	 * Called every tick to update the entities state
	 */
	@Override
	public void onEntityUpdate()
	{
		// Call super
		super.onEntityUpdate();

		// Show the walking animation if the entity is walking and not biting
		if (this.world.isRemote)
			if (this.motionX > 0.05 || this.motionX < -0.05 || this.motionZ > 0.05 || this.motionZ < -0.05)
				if (!animHandler.isAnimationActive("Bite") && !animHandler.isAnimationActive("Run"))
					animHandler.activateAnimation("Run", 0);
	}

	/**
	 * Called when the werewolf dies
	 *
	 * @param cause The damage source that killed the werewolf
	 */
	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);

		// Server side processing only
		if (!world.isRemote)
		{
			// If the cause was from a player we perform further processing
			if (cause instanceof EntityDamageSource)
			{
				// Test if the killer was a player
				if (cause.getTrueSource() instanceof EntityPlayer)
				{
					EntityPlayer killer = (EntityPlayer) cause.getTrueSource();

					IAOTDPlayerResearch playerResearch = killer.getCapability(ModCapabilities.PLAYER_RESEARCH, null);
					/*
					// If the player can research the slaying of the wolves achievement do so
					if (playerResearch.canResearch(ModResearches.SLAYING_OF_THE_WOLVES))
					{
						playerResearch.setResearch(ModResearches.SLAYING_OF_THE_WOLVES, true);
						playerResearch.sync(killer, true);
					}

					// If the player has the slaying of the wolves achievement then test if the player has glass bottles to fill with werewolf blood
					if (playerResearch.isResearched(ModResearches.SLAYING_OF_THE_WOLVES))
						// If we can clear a glass bottle do so and add 1 werewolf blood
						if (killer.inventory.clearMatchingItems(Items.GLASS_BOTTLE, 0, 1, null) == 1)
							killer.inventory.addItemStackToInventory(new ItemStack(ModItems.WEREWOLF_BLOOD, 1));
					*/
				}
			}
		}
	}

	/**
	 * @return the sound this mob makes on death.
	 */
	protected SoundEvent getDeathSound()
	{
		return ModSounds.WEREWOLF_DEATH;
	}

	/**
	 * @return the sound this mob makes when it is hurt.
	 */
	protected SoundEvent getHurtSound()
	{
		return ModSounds.WEREWOLF_HURT;
	}

	/**
	 * @return the werewolf's volume
	 */
	@Override
	protected float getSoundVolume()
	{
		return 0.5F;
	}

	/**
	 * @return Returns the animation handler which manages our animation state
	 */
	@Override
	public AnimationHandler getAnimationHandler()
	{
		return this.animHandler;
	}

	/**
	 * @return Get the AI movespeed
	 */
	@Override
	public float getAIMoveSpeed()
	{
		return (float) EntityWerewolf.MOVE_SPEED;
	}

	/**
	 * Called when the entity is attacked from a damage source
	 *
	 * @param damageSource The damage source used
	 * @param damage The damage inflicted
	 * @return True if the attack went through, false otherwise
	 */
	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float damage)
	{
		// If the damage was 'silver_damage' then we can apply it, otherwise we just do 1 'generic' damage
		if (damageSource.damageType.equals("silver_damage"))
			return super.attackEntityFrom(damageSource, damage);
		return super.attackEntityFrom(DamageSource.GENERIC, 1);
	}

	/**
	 * Reads this entity from an NBT compound
	 *
	 * @param nbtTagCompound The compound to read from
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbtTagCompound)
	{
		this.attacksAnyone = nbtTagCompound.getBoolean(NBT_CAN_ATTACK_ANYONE);
		super.readEntityFromNBT(nbtTagCompound);
	}

	/**
	 * Writes this entity to an NBT compound
	 *
	 * @param nbtTagCompound The compound to write to
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbtTagCompound)
	{
		nbtTagCompound.setBoolean(NBT_CAN_ATTACK_ANYONE, attacksAnyone);
		super.writeEntityToNBT(nbtTagCompound);
	}
}
