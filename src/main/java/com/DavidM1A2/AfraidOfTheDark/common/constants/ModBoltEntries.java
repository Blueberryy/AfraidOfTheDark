package com.DavidM1A2.afraidofthedark.common.constants;

import com.DavidM1A2.afraidofthedark.common.entity.bolt.*;
import com.DavidM1A2.afraidofthedark.common.registry.bolt.AOTDBoltEntry;
import com.DavidM1A2.afraidofthedark.common.registry.bolt.BoltEntry;

/**
 * A static class containing all of our bolt entry references for us
 */
public class ModBoltEntries
{
    public static final BoltEntry WOODEN = new AOTDBoltEntry("wooden", ModItems.WOODEN_BOLT, EntityWoodenBolt::new, null);
    public static final BoltEntry IRON = new AOTDBoltEntry("iron", ModItems.IRON_BOLT, EntityIronBolt::new, null);
    public static final BoltEntry ASTRAL_SILVER = new AOTDBoltEntry("astral_silver", ModItems.SILVER_BOLT, EntitySilverBolt::new, ModResearches.ASTRAL_SILVER);
    public static final BoltEntry IGNEOUS = new AOTDBoltEntry("igneous", ModItems.IGNEOUS_BOLT, EntityIgneousBolt::new, ModResearches.IGNEOUS);
    public static final BoltEntry STAR_METAL = new AOTDBoltEntry("star_metal", ModItems.STAR_METAL_BOLT, EntityStarMetalBolt::new, ModResearches.STAR_METAL);

    public static final BoltEntry[] BOLT_ENTRY_LIST = new BoltEntry[]
            {
                    WOODEN,
                    IRON,
                    ASTRAL_SILVER,
                    IGNEOUS,
                    STAR_METAL
            };
}
