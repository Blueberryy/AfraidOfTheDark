package com.DavidM1A2.afraidofthedark.common.worldGeneration;

import com.DavidM1A2.afraidofthedark.AfraidOfTheDark;
import com.DavidM1A2.afraidofthedark.common.capabilities.world.IHeightmap;
import com.DavidM1A2.afraidofthedark.common.capabilities.world.OverworldHeightmap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Class used to map out the entire world height used for terrain generation
 */
public class WorldHeightMapper
{
	/**
	 * Called whenever a chunk is generated and needs population, we update our terrain height map here
	 *
	 * @param event The event containing the chunk and world
	 */
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onChunkPopulated(PopulateChunkEvent.Pre event)
	{
		// Get a reference to the world
		World world = event.getWorld();
		// Get the X and Z coordinates of the chunk that was populated
		int chunkX = event.getChunkX();
		int chunkZ = event.getChunkZ();

		// Generate the height map for the chunk
		this.heightMapChunk(world, chunkX, chunkZ, 3);
	}

	/**
	 * Called to generate the height map for a chunk X and Z +/- chunkPredictionRange from the center
	 *
	 * @param world The world to generate for, should be the overworld and server side
	 * @param chunkX The X coordinate of the chunk
	 * @param chunkZ The Z coordinate of the chunk
	 * @param chunkPredictionRange The prediction range to go out
	 */
	private void heightMapChunk(World world, int chunkX, int chunkZ, int chunkPredictionRange)
	{
		// If the world is server side, and it's the overworld, we begin heightmap creation
		if (!world.isRemote && world instanceof WorldServer && world.provider.getDimension() == 0)
		{
			// Cast the world to a world server
			WorldServer worldServer = (WorldServer) world;

			//Biome[] biomeName = worldServer.getBiomeProvider().getBiomes(null, 100000, 100000, 1, 1);

			// Grab a reference to the chunk provider
			ChunkProviderServer chunkProvider = worldServer.getChunkProvider();

			// Test if the chunk generator is an overworld generator, it should be since we're in dimension 0
			if (chunkProvider.chunkGenerator instanceof ChunkGeneratorOverworld)
			{
				// Cast the chunk generator
				ChunkGeneratorOverworld chunkGenerator = (ChunkGeneratorOverworld) chunkProvider.chunkGenerator;

				// Allocate one chunk primer to predict chunks
				// ChunkPrimer chunkPrimer = new ChunkPrimer();

				// Grab data for the world object
				IHeightmap heightmap = OverworldHeightmap.get(worldServer);

				// This should not happen, but in case it does throw an error
				if (heightmap == null)
				{
					AfraidOfTheDark.INSTANCE.getLogger().error("Heightmap could not be retrieved for overworld! This is an error and world generation will not work!");
					return;
				}

				// Iterate from chunkX - 3 to chunkX + 3
				// Iterate from chunkZ - 3 to chunkZ + 3
				// Predict the height of that chunk
				for (int x = chunkX - chunkPredictionRange; x <= chunkX + chunkPredictionRange; x++)
				{
					for (int z = chunkZ - chunkPredictionRange; z <= chunkZ + chunkPredictionRange; z++)
					{
						ChunkPos chunkPos = new ChunkPos(x, z);
						// Test first if the chunk has already been generated. We can just test 4,4 because if 4,4 is not present
						// The rest will not be either
						if (!heightmap.heightKnown(chunkPos))
						{
							// Let our world generator set the blocks inside the chunk. This is much faster than actually generating the chunk!
							//chunkGenerator.setBlocksInChunk(x, z, chunkPrimer);
							Chunk chunk = chunkGenerator.generateChunk(x, z);
							//chunkGenerator.replaceBiomeBlocks(x, z, chunkPrimer, world.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16));
							// Now use this chunk information to predict height values of chunks that have not yet been generated
							// Instead of heightmapping the entire chunk we only do 4 points to get an idea if the chunk is flat or not. This
							// Lets us make fairly accurate predictions if the chunk is flat or not. We pick the 4 corners of the chunk
							// Use z-1 because there's a bug with findGroundBlockIdx
							int corner1Height = chunk.getHeightValue(0, 0);//chunkPrimer.findGroundBlockIdx(0, 0); // Can't use -1? -1 is broken
							int corner2Height = chunk.getHeightValue(15, 0);//chunkPrimer.findGroundBlockIdx(15, 0); // Can't use -1? -1 is broken
							int corner3Height = chunk.getHeightValue(0, 15);//chunkPrimer.findGroundBlockIdx(0, 14);
							int corner4Height = chunk.getHeightValue(15, 15);//chunkPrimer.findGroundBlockIdx(15, 14);
							// Find the min and max of the 4 values
							int minHeight = Math.min(Math.min(corner1Height, corner2Height), Math.min(corner3Height, corner4Height));
							int maxHeight = Math.max(Math.max(corner1Height, corner2Height), Math.max(corner3Height, corner4Height));
							// Set the height
							heightmap.setHeight(chunkPos, minHeight, maxHeight);
						}
					}
				}
			}
		}
	}
}