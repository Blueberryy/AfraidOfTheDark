package com.DavidM1A2.afraidofthedark.common.worldGeneration.structure.base;

import com.DavidM1A2.afraidofthedark.common.capabilities.world.IHeightmap;
import net.minecraft.util.math.ChunkPos;

/**
 * Utility processor for finding the minimum ground height within a region
 */
public class LowestHeightChunkProcessor implements IChunkProcessor<Integer>
{
    // The minimum ground height
    private int minGroundHeight = 256;
    // Heightmap to use
    private IHeightmap heightmap;

    /**
     * Constructor initializes the heightmap field
     *
     * @param heightmap The heightmap to use
     */
    public LowestHeightChunkProcessor(IHeightmap heightmap)
    {
        this.heightmap = heightmap;
    }

    /**
     * Processes the X,Z chunk by finding the lowest point
     *
     * @param chunkX The X coordinate of the chunk
     * @param chunkZ The Z coordinate of the chunk
     * @return true to continue processing
     */
    @Override
    public boolean processChunk(int chunkX, int chunkZ)
    {
        // Compute the ground height in the chunk
        int groundHeight = heightmap.getLowestHeight(new ChunkPos(chunkX, chunkZ));
        minGroundHeight = Math.min(minGroundHeight, groundHeight);
        return true;
    }

    /**
     * @return The minimum ground height
     */
    @Override
    public Integer getResult()
    {
        return minGroundHeight;
    }
}
