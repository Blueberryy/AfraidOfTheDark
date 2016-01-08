/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.client.gui;

import com.DavidM1A2.AfraidOfTheDark.client.gui.baseControls.AOTDGuiComponent;

public interface AOTDActionListener
{
	public enum ActionType
	{
		MousePressed,
		MouseReleased,
		MouseHover,
		MouseMove,
		MouseExitBoundingBox,
		MouseEnterBoundingBox,
		KeyTyped;
	}

	public abstract void actionPerformed(AOTDGuiComponent component, ActionType actionType);
}
