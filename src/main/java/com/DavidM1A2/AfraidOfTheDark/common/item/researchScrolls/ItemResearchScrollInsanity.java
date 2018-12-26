/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.item.researchScrolls;

import com.DavidM1A2.AfraidOfTheDark.common.reference.ResearchTypes;

public class ItemResearchScrollInsanity extends ItemResearchScroll
{
	public ItemResearchScrollInsanity()
	{
		super();
		this.setUnlocalizedName("research_scroll_insanity");
		this.setRegistryName("research_scroll_insanity");
	}

	@Override
	public void setMyType()
	{
		this.myType = ResearchTypes.Insanity;
	}
}