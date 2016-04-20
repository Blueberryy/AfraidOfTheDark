/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.spell.deliveryMethods;

import com.DavidM1A2.AfraidOfTheDark.client.entities.spell.ModelSpellProjectile;
import com.DavidM1A2.AfraidOfTheDark.client.entities.spell.RenderSpell;
import com.DavidM1A2.AfraidOfTheDark.common.entities.spell.EntitySpell;
import com.DavidM1A2.AfraidOfTheDark.common.entities.spell.AOE.EntityAOE;
import com.DavidM1A2.AfraidOfTheDark.common.entities.spell.myself.EntityMyself;
import com.DavidM1A2.AfraidOfTheDark.common.entities.spell.projectile.EntitySpellProjectile;
import com.DavidM1A2.AfraidOfTheDark.common.spell.ISpellComponentEnum;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;

public enum DeliveryMethods implements ISpellComponentEnum
{
	Projectile(1, EntitySpellProjectile.class, new ModelSpellProjectile(), "afraidofthedark:textures/gui/spellCrafting/deliveryMethods/projectile.png", "afraidofthedark:textures/entity/spell/projectile.png")
	{
		@Override
		public DeliveryMethod newInstance()
		{
			return new Projectile();
		}
	},
	Myself(2, EntityMyself.class, null, "afraidofthedark:textures/gui/spellCrafting/deliveryMethods/myself.png", null)
	{
		@Override
		public DeliveryMethod newInstance()
		{
			return new Myself();
		}
	},
	AOE(3, EntityAOE.class, null, "afraidofthedark:textures/gui/spellCrafting/deliveryMethods/AOE.png", null)
	{
		@Override
		public DeliveryMethod newInstance()
		{
			return new AOE();
		}
	};

	private int id;
	private String iconTexture;
	private ModelBase model;
	private String texture;
	private Class<? extends EntitySpell> deliveryEntity;

	private DeliveryMethods(int id, Class<? extends EntitySpell> deliveryEntity, ModelBase model, String iconTexture, String texture)
	{
		this.id = id;
		this.iconTexture = iconTexture;
		this.model = model;
		this.texture = texture;
		this.deliveryEntity = deliveryEntity;
	}

	public <T extends EntitySpell> RenderSpell<T> getDeliveryRenderer(RenderManager renderManager)
	{
		return new RenderSpell<T>(renderManager, this.model, this.texture);
	}

	public Class<? extends EntitySpell> getDeliveryEntity()
	{
		return this.deliveryEntity;
	}

	public int getID()
	{
		return this.id;
	}

	@Override
	public String getIcon()
	{
		return this.iconTexture;
	}

	@Override
	public String getName()
	{
		return this.toString();
	}

	@Override
	public abstract DeliveryMethod newInstance();
}