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
	Explosion(1, new EffectAffinity(0, .2, 0, 0), "explosion.png")
	{
		@Override
		public Effect newInstance()
		{
			return new Explosion();
		}
	},
	Grow(2, new EffectAffinity(0, 0, .2, 0), "grow.png")
	{
		@Override
		public Effect newInstance()
		{
			return new Grow();
		}
	},
	Heal(3, new EffectAffinity(.1, 0, .1, 0), "heal.png")
	{
		@Override
		public Effect newInstance()
		{
			return new Heal();
		}
	},
	Burn(4, new EffectAffinity(.2, .3, 0, .1), "burn.png")
	{
		@Override
		public Effect newInstance()
		{
			return new Burn();
		}
	},
	Dig(5, new EffectAffinity(.1, 0, 0, .3), "dig.png")
	{
		@Override
		public Effect newInstance()
		{
			return new Dig();
		}
	},
	Freeze(6, new EffectAffinity(.4, 0, 0, 0), "freeze.png")
	{
		@Override
		public Effect newInstance()
		{
			return new Freeze();
		}
	},
	EnderPocket(7, new EffectAffinity(.1, 0, 0, 0), "ender_pocket.png")
	{
		@Override
		public Effect newInstance()
		{
			return new EnderPocket();
		}
	},
	Teleport(8, new EffectAffinity(.1, .1, .1, .1), "teleport.png")
	{
		@Override
		public Effect newInstance()
		{
			return new Teleport();
		}
	};

	private int id;
	private String iconTexture;
	private EffectAffinity effectAffinity;

	private Effects(int id, EffectAffinity effectAffinity, String iconTexture)
	{
		this.id = id;
		this.iconTexture = "afraidofthedark:textures/gui/spell_crafting/effects/" + iconTexture;
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

	@Override
	public String getNameFormatted()
	{
		String toReturn = "";

		for (final String string : this.getName().split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])"))
		{
			toReturn = toReturn + string + " ";
		}

		if (toReturn.charAt(toReturn.length() - 1) == ' ')
			toReturn = toReturn.substring(0, toReturn.length() - 1);

		return toReturn;
	}
}