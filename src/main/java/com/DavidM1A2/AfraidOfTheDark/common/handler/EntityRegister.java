package com.DavidM1A2.afraidofthedark.common.handler;

import com.DavidM1A2.afraidofthedark.common.block.core.AOTDSlab;
import com.DavidM1A2.afraidofthedark.common.constants.ModBlocks;
import com.DavidM1A2.afraidofthedark.common.constants.ModEntities;
import com.DavidM1A2.afraidofthedark.common.constants.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Class that receives the register entity event and registers all of our entities
 */
public class EntityRegister
{
	/**
	 * Called by forge to register any of our entities
	 *
	 * @param event The event to register to
	 */
	@SubscribeEvent
	public void registerEntities(RegistryEvent.Register<EntityEntry> event)
	{
		// Grab the registry for entities
		IForgeRegistry<EntityEntry> registry = event.getRegistry();

		// Register all of our mod entities
		for (EntityEntry entityEntry : ModEntities.ENTITY_LIST)
		{
			registry.register(entityEntry);
		}
	}
}