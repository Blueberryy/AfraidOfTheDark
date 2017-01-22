/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.client.settings;

import java.util.HashMap;
import java.util.Map;

import com.DavidM1A2.AfraidOfTheDark.client.gui.ResearchAchieved;
import com.DavidM1A2.AfraidOfTheDark.client.trueTypeFont.FontLoader;
import com.DavidM1A2.AfraidOfTheDark.client.trueTypeFont.TrueTypeFont;
import com.DavidM1A2.AfraidOfTheDark.common.reference.AOTDMeteorTypes;
import com.DavidM1A2.AfraidOfTheDark.common.reference.ResearchTypes;
import com.DavidM1A2.AfraidOfTheDark.common.spell.Spell;

import net.minecraft.util.ResourceLocation;

public final class ClientData
{
	private static Map<Float, TrueTypeFont> fontMap = new HashMap<Float, TrueTypeFont>();

	public static ResearchTypes currentlySelected = ResearchTypes.AnUnbreakableCovenant;

	public static int[] selectedMeteor = new int[]
	{ -1, -1, -1 };
	public static AOTDMeteorTypes watchedMeteorType = null;

	public static ResearchAchieved researchAchievedOverlay;

	public static int currentBloodStainedJournalX = 0;
	public static int currentBloodStainedJournalY = 0;

	public static Spell spellToBeEdited;

	public static TrueTypeFont getTargaMSHandFontSized(float fontSize)
	{
		if (!ClientData.fontMap.containsKey(fontSize))
			ClientData.fontMap.put(fontSize, FontLoader.createFont(new ResourceLocation("afraidofthedark:fonts/targa_ms_hand.ttf"), fontSize, true));
		return ClientData.fontMap.get(fontSize);
	}
}
