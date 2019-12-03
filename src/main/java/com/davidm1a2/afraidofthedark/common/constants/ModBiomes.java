package com.davidm1a2.afraidofthedark.common.constants;

import com.davidm1a2.afraidofthedark.common.biomes.BiomeErieForest;
import com.davidm1a2.afraidofthedark.common.biomes.BiomeNightmare;
import com.davidm1a2.afraidofthedark.common.biomes.BiomeVoidChest;
import net.minecraft.world.biome.Biome;

/**
 * Storage for all mod biomes used in AOTD
 */
public class ModBiomes
{
    // The Erie Forest biome
    public static final Biome ERIE_FOREST = BiomeErieForest.INSTANCE;
    // The Void Chest biome
    public static final Biome VOID_CHEST = BiomeVoidChest.INSTANCE;
    // The Nightmare biome
    public static final Biome NIGHTMARE = BiomeNightmare.INSTANCE;

    // An array containing a list of biomes that AOTD adds
    public static final Biome[] BIOME_LIST = new Biome[]
            {
                    ERIE_FOREST,
                    VOID_CHEST,
                    NIGHTMARE
            };
}
