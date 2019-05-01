package com.DavidM1A2.afraidofthedark.common.worldGeneration.structure;

import com.DavidM1A2.afraidofthedark.common.capabilities.world.IHeightmap;
import com.DavidM1A2.afraidofthedark.common.constants.ModBiomes;
import com.DavidM1A2.afraidofthedark.common.constants.ModSchematics;
import com.DavidM1A2.afraidofthedark.common.worldGeneration.schematic.Schematic;
import com.DavidM1A2.afraidofthedark.common.worldGeneration.structure.base.AOTDStructure;
import com.google.common.collect.ImmutableSet;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;

import java.util.Arrays;
import java.util.Set;

/**
 * Dark forest structure class
 */
public class StructureDarkForest extends AOTDStructure
{
    // A set of compatible biomes
    private static final Set<Biome> COMPATIBLE_BIOMES = ImmutableSet.of(
            Biomes.SAVANNA,
            Biomes.MUTATED_SAVANNA,
            Biomes.PLAINS,
            Biomes.MUTATED_PLAINS,
            ModBiomes.ERIE_FOREST
    );

    // The width of the dark forest dungeon
    private int width;
    // The height of the dark forest dungeon
    private int height;

    /**
     * Structure constructor just sets the registry name
     */
    public StructureDarkForest()
    {
        super("dark_forest");
        int widestTree = Arrays.stream(ModSchematics.DARK_FOREST_TREES).map(Schematic::getWidth).max(Short::compareTo).get();
        int longestTree = Arrays.stream(ModSchematics.DARK_FOREST_TREES).map(Schematic::getLength).max(Short::compareTo).get();
        // Width is width(BED_HOUSE) + 2*width(BIGGEST_TREE)
        this.width = ModSchematics.BED_HOUSE.getWidth() + 2 * widestTree;
        // Height is length(BED_HOUSE) + 2*length(BIGGEST_TREE)
        this.height = ModSchematics.BED_HOUSE.getLength() + 2 * longestTree;
    }

    /**
     * Tests if this structure is valid for the given position
     *
     * @param blockPos The position that the structure would begin at
     * @param heightmap The heightmap to use in deciding if the structure will fit at the position
     * @param biomeProvider The provider used to generate the world, use biomeProvider.getBiomes() to get what biomes exist at a position
     * @return A value between 0 and 1 which is the chance between 0% and 100% that a structure could spawn at the given position
     */
    @Override
    public double computeChanceToGenerateAt(BlockPos blockPos, IHeightmap heightmap, BiomeProvider biomeProvider)
    {

        return 0;
    }

    @Override
    public void generate(World world, BlockPos blockPos, ChunkPos chunkPos, NBTTagCompound data)
    {

    }

    @Override
    public int getXWidth()
    {
        return this.width;
    }

    @Override
    public int getZLength()
    {
        return this.height;
    }
}
