package com.DavidM1A2.AfraidOfTheDark.common.utility;

import java.io.InputStream;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt.DirtType;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockSnow;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.DavidM1A2.AfraidOfTheDark.common.block.BlockGravewood;
import com.DavidM1A2.AfraidOfTheDark.common.dimension.NightmareTeleporter;

public class Utility
{
	public static int ticksToMilliseconds(int ticks)
	{
		return ticks * 50;
	}

	public static InputStream getInputStreamFromPath(String path)
	{
		InputStream inputStream = Utility.class.getClassLoader().getResourceAsStream(path);
		if (inputStream == null)
		{
			inputStream = Utility.class.getClassLoader().getResourceAsStream("assets/afraidofthedark/researchNotes/None.txt");
		}
		return inputStream;
	}

	/*
	 * See EntityPlayerMP.travelToDimension
	 */
	public static void sendPlayerToDimension(EntityPlayerMP entityPlayer, int dimensionId, boolean spawnPortal)
	{
		ServerConfigurationManager serverConfigurationManager = entityPlayer.mcServer.getConfigurationManager();
		int j = entityPlayer.dimension;
		WorldServer worldserver = serverConfigurationManager.getServerInstance().worldServerForDimension(entityPlayer.dimension);
		entityPlayer.dimension = dimensionId;
		WorldServer worldserver1 = serverConfigurationManager.getServerInstance().worldServerForDimension(entityPlayer.dimension);
		entityPlayer.playerNetServerHandler.sendPacket(new S07PacketRespawn(entityPlayer.dimension, worldserver1.getDifficulty(), worldserver1.getWorldInfo().getTerrainType(), entityPlayer.theItemInWorldManager.getGameType())); // Forge: Use new dimensions information
		worldserver.removePlayerEntityDangerously(entityPlayer);
		entityPlayer.isDead = false;

		if (!spawnPortal)
		{
			serverConfigurationManager.transferEntityToWorld(entityPlayer, j, worldserver, worldserver1, new NightmareTeleporter(worldserver1));
		}
		else
		{
			serverConfigurationManager.transferEntityToWorld(entityPlayer, j, worldserver, worldserver1, worldserver1.getDefaultTeleporter());
		}

		serverConfigurationManager.func_72375_a(entityPlayer, worldserver);
		entityPlayer.playerNetServerHandler.setPlayerLocation(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, entityPlayer.rotationYaw, entityPlayer.rotationPitch);
		entityPlayer.theItemInWorldManager.setWorld(worldserver1);
		serverConfigurationManager.updateTimeAndWeatherForPlayer(entityPlayer, worldserver1);
		serverConfigurationManager.syncPlayerInventory(entityPlayer);
		Iterator iterator = entityPlayer.getActivePotionEffects().iterator();

		while (iterator.hasNext())
		{
			PotionEffect potioneffect = (PotionEffect) iterator.next();
			entityPlayer.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(entityPlayer.getEntityId(), potioneffect));
		}
		FMLCommonHandler.instance().firePlayerChangedDimensionEvent(entityPlayer, j, dimensionId);

	}

	public static int getPlaceToSpawnAverage(World world, int x, int z, int height, int width) throws UnsupportedLocationException
	{
		int y1 = 0;
		int y2 = 0;
		int y3 = 0;
		int y4 = 0;

		y1 = getTheYValueAtCoords(world, x, z);
		y2 = getTheYValueAtCoords(world, x + width, z);
		y3 = getTheYValueAtCoords(world, x, z + height);
		y4 = getTheYValueAtCoords(world, x + width, z + height);

		if (y1 == 0 || y2 == 0 || y3 == 0 || y4 == 0)
		{
			throw new UnsupportedLocationException(y1, y2, y3, y4);
		}
		else
		{
			return (y1 + y2 + y3 + y4) / 4;
		}
	}

	public static int getPlaceToSpawnLowest(World world, int x, int z, int height, int width) throws UnsupportedLocationException
	{
		int y1 = 0;
		int y2 = 0;
		int y3 = 0;
		int y4 = 0;

		y1 = getTheYValueAtCoords(world, x, z);
		y2 = getTheYValueAtCoords(world, x + width, z);
		y3 = getTheYValueAtCoords(world, x, z + height);
		y4 = getTheYValueAtCoords(world, x + width, z + height);

		if (y1 == 0 || y2 == 0 || y3 == 0 || y4 == 0)
		{
			throw new UnsupportedLocationException(y1, y2, y3, y4);
		}
		else
		{
			return Math.min(y1, Math.min(y2, Math.min(y3, y4)));
		}
	}

	private static int getTheYValueAtCoords(World world, int x, int z)
	{
		int temp = 255;
		while (temp > 0)
		{
			Block current = world.getBlockState(new BlockPos(x, temp, z)).getBlock();
			if (current instanceof BlockFluidBase)
			{
				return 0;
			}
			if (current instanceof BlockGrass)
			{
				return temp;
			}
			if (current instanceof BlockDirt)
			{
				if (world.canSeeSky(new BlockPos(x, temp, z)))
				{
					return temp;
				}
				else if (world.getBlockState(new BlockPos(x, temp, z)) == Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.PODZOL))
				{
					return temp;
				}
				else if (world.getBlockState(new BlockPos(x, temp + 1, z)).getBlock() instanceof BlockLog || world.getBlockState(new BlockPos(x, temp + 1, z)).getBlock() instanceof BlockGravewood)
				{
					return temp;
				}
			}
			if (world.getBlockState(new BlockPos(x, temp + 1, z)).getBlock() instanceof BlockSnow)
			{
				return temp;
			}
			temp = temp - 1;
		}
		return 0;
	}
}
