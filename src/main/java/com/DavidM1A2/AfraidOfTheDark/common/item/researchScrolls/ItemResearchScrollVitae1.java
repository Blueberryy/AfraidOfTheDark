/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.item.researchScrolls;

import com.DavidM1A2.AfraidOfTheDark.common.reference.ResearchTypes;

public class ItemResearchScrollVitae1 extends ItemResearchScroll
{
	public ItemResearchScrollVitae1()
	{
		super();
		this.setUnlocalizedName("research_scroll_vitae1");
		this.setRegistryName("research_scroll_vitae1");
	}

	@Override
	public void setMyType()
	{
		this.myType = ResearchTypes.VitaeI;
	}

	@Override
	public int numberOfScrollsToMakeCompleteResearch()
	{
		return 5;
	}
}
