package com.DavidM1A2.AfraidOfTheDark.common.dimension;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModBiomes;

public class NightmareChunkProvider implements IChunkProvider
{
	private final World worldObj;

	public NightmareChunkProvider(World world)
	{
		worldObj = world;
	}

	/**
	 * Checks to see if a chunk exists at x, y
	 */
	@Override
	public boolean chunkExists(int x, int z)
	{
		return true;
	}

	@Override
	public Chunk provideChunk(int x, int z)
	{
		ChunkPrimer chunkprimer = new ChunkPrimer();
		IBlockState iblockstate = Blocks.air.getDefaultState();

		Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);

		byte[] biome = chunk.getBiomeArray();

		for (int i = 0; i < biome.length; i++)
		{
			biome[i] = (byte) ModBiomes.erieForest.biomeID;
		}

		chunk.generateSkylightMap();

		return chunk;
	}

	@Override
	public Chunk func_177459_a(BlockPos blockPos)
	{
		return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
	}

	@Override
	public void populate(IChunkProvider iChunkProvider, int x, int z)
	{
		// Every 62 chunks in the x direction (~ 1000 blocks)
		if (x % 62 == 0)
		{

		}
	}

	@Override
	public boolean func_177460_a(IChunkProvider p_177460_1_, Chunk p_177460_2_, int p_177460_3_, int p_177460_4_)
	{
		return false;
	}

	@Override
	public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
	{
		return true;
	}

	@Override
	public boolean unloadQueuedChunks()
	{
		return false;
	}

	@Override
	public boolean canSave()
	{
		return true;
	}

	@Override
	public String makeString()
	{
		return "NightmareWorld";
	}

	@Override
	public List func_177458_a(EnumCreatureType enumCreatureType, BlockPos blockPos)
	{
		return this.worldObj.getBiomeGenForCoords(blockPos).getSpawnableList(enumCreatureType);
	}

	@Override
	public BlockPos func_180513_a(World world, String structureType, BlockPos blockPos)
	{
		// Gen structures
		return null;
	}

	@Override
	public int getLoadedChunkCount()
	{
		return 0;
	}

	@Override
	public void func_180514_a(Chunk chunk, int x, int z)
	{
		// structures?
	}

	@Override
	public void saveExtraData()
	{
	}
}