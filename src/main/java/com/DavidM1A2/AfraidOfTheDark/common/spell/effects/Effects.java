/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.spell.effects;

import com.DavidM1A2.AfraidOfTheDark.common.spell.EffectAffinity;
import com.DavidM1A2.AfraidOfTheDark.common.spell.ISpellComponentEnum;

public enum Effects implements ISpellComponentEnum
{
	Explosion(1, new EffectAffinity(0, .2, 0, 0), "afraidofthedark:textures/gui/spellCrafting/effects/explosion.png")
	{
		@Override
		public Effect newInstance()
		{
			return new Explosion();
		}
	},
	Grow(2, new EffectAffinity(0, 0, .2, 0), "afraidofthedark:textures/gui/spellCrafting/effects/grow.png")
	{
		@Override
		public Effect newInstance()
		{
			return new Grow();
		}
	},
	Heal(3, new EffectAffinity(.1, 0, .1, 0), "afraidofthedark:textures/gui/spellCrafting/effects/heal.png")
	{
		@Override
		public Effect newInstance()
		{
			return new Heal();
		}
	};

	private int id;
	private String iconTexture;
	private EffectAffinity effectAffinity;

	private Effects(int id, EffectAffinity effectAffinity, String iconTexture)
	{
		this.id = id;
		this.iconTexture = iconTexture;
		this.effectAffinity = effectAffinity;
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

	public EffectAffinity getAffinity()
	{
		return this.effectAffinity;
	}

	@Override
	public abstract Effect newInstance();
}