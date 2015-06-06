/*
 * Author: David Slovikosky 
 * Mod: Afraid of the Dark 
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.utility;

import net.minecraftforge.fml.common.FMLLog;

import org.apache.logging.log4j.Level;

import com.DavidM1A2.AfraidOfTheDark.common.refrence.Refrence;

public class LogHelper
{
	public static void log(final Level logLevel, final Object object)
	{
		FMLLog.log(Refrence.MOD_NAME, logLevel, String.valueOf(object));
	}

	public static void all(final Object object)
	{
		LogHelper.log(Level.ALL, object);
	}

	public static void debug(final Object object)
	{
		LogHelper.log(Level.DEBUG, object);
	}

	public static void error(final Object object)
	{
		LogHelper.log(Level.ERROR, object);
	}

	public static void fatal(final Object object)
	{
		LogHelper.log(Level.FATAL, object);
	}

	public static void info(final Object object)
	{
		LogHelper.log(Level.INFO, object);
	}

	public static void off(final Object object)
	{
		LogHelper.log(Level.OFF, object);
	}

	public static void trace(final Object object)
	{
		LogHelper.log(Level.TRACE, object);
	}

	public static void warn(final Object object)
	{
		LogHelper.log(Level.WARN, object);
	}
}