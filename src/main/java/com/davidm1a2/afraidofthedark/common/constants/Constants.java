/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.davidm1a2.afraidofthedark.common.constants;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * References for static final variables
 */
public class Constants
{
    // The ID of the mod
    public static final String MOD_ID = "afraidofthedark";
    // The mod name
    public static final String MOD_NAME = "Afraid of the Dark";
    // The mod version
    public static final String MOD_VERSION = "1.1.0";
    // The minecraft version number
    public static final String MC_VERSION = "1.12.2";
    // Refrences to the proxies
    public static final String SERVER_PROXY_CLASS = "com.davidm1a2.afraidofthedark.proxy.ServerProxy";
    public static final String CLIENT_PROXY_CLASS = "com.davidm1a2.afraidofthedark.proxy.ClientProxy";
    public static final String GUI_FACTORY_CLASS = "com.davidm1a2.afraidofthedark.client.gui.AOTDGuiFactory";

    // Creative Tab for the mod
    public static final CreativeTabs AOTD_CREATIVE_TAB = new CreativeTabs(Constants.MOD_ID)
    {
        /**
         * Getter for the mod creative tab icon
         *
         * @return The icon for the creative tab as an item
         */
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModItems.JOURNAL);
        }
    };

    // The base size of all GUIs
    public static final int GUI_WIDTH = 640;
    public static final int GUI_HEIGHT = 360;

    // All text will be rendered with this scale factor to avoid blurry text
    public static final float TEXT_SCALE_FACTOR = 0.25f;
}