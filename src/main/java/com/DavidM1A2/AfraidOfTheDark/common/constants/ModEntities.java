package com.DavidM1A2.afraidofthedark.common.constants;

import com.DavidM1A2.afraidofthedark.client.entity.bolt.RenderIronBolt;
import com.DavidM1A2.afraidofthedark.client.entity.bolt.RenderWoodenBolt;
import com.DavidM1A2.afraidofthedark.client.entity.enchantedSkeleton.RenderEnchantedSkeleton;
import com.DavidM1A2.afraidofthedark.common.entity.bolt.EntityIronBolt;
import com.DavidM1A2.afraidofthedark.common.entity.bolt.EntityWoodenBolt;
import com.DavidM1A2.afraidofthedark.common.entity.enchantedSkeleton.EntityEnchantedSkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A static class containing all of our entity references for us
 */
public class ModEntities
{
	// Various entity IDs
	public static final int WEREWOLF_ID = 0;
	public static final int IRON_BOLT_ID = 1;
	public static final int SILVER_BOLT_ID = 2;
	public static final int WOODEN_BOLT_ID = 3;
	public static final int DEEE_SYFT_ID = 4;
	public static final int IGNEOUS_BOLT_ID = 5;
	public static final int STAR_METAL_BOLT_ID = 6;
	public static final int ENCHANTED_SKELETON_ID = 7;
	public static final int ENARIA_ID = 8;
	public static final int SPLINTER_DRONE_ID = 9;
	public static final int SPLINTER_DRONE_PROJECTILE_ID = 10;
	public static final int SPELL_PROJECTILE_ID = 11;
	public static final int SPELL_PROJECTILE_DIVE_ID = 12;
	public static final int SPELL_MYSELF_ID = 13;
	public static final int SPELL_AOE_ID = 14;
	public static final int GHASTLY_ENARIA_ID = 15;
	public static final int ARTWORK_ID = 16;
	public static final int SPELL_EXTRA_EFFECTS_ID = 17;

	// All mod entity static fields
	public static final EntityEntry ENCHANTED_SKELETON = EntityEntryBuilder.create()
			.egg(0x996600, 0xe69900)
			.entity(EntityEnchantedSkeleton.class)
			.id(new ResourceLocation(Constants.MOD_ID, "enchanted_skeleton"), ENCHANTED_SKELETON_ID)
			.name("enchanted_skeleton")
			.tracker(50, 1, true)
			.build();

	// 5 bolt entities
	public static final EntityEntry WOODEN_BOLT = EntityEntryBuilder.create()
			.entity(EntityWoodenBolt.class)
			.id(new ResourceLocation(Constants.MOD_ID, "wooden_bolt"), WOODEN_BOLT_ID)
			.name("wooden_bolt")
			.tracker(50, 1, true)
			.build();
	public static final EntityEntry IRON_BOLT = EntityEntryBuilder.create()
			.entity(EntityIronBolt.class)
			.id(new ResourceLocation(Constants.MOD_ID, "iron_bolt"), IRON_BOLT_ID)
			.name("iron_bolt")
			.tracker(50, 1, true)
			.build();

	// An array containing a list of entities that AOTD adds
	public static EntityEntry[] ENTITY_LIST = new EntityEntry[]
	{
		ENCHANTED_SKELETON,
		WOODEN_BOLT,
		IRON_BOLT
	};

	// A mapping of EntityEntry to entity renderer
	public static List<Pair<EntityEntry, IRenderFactory>> ENTITY_RENDERERS = new ArrayList<Pair<EntityEntry, IRenderFactory>>()
	{{
		add(Pair.of(ENCHANTED_SKELETON, RenderEnchantedSkeleton::new));
		add(Pair.of(WOODEN_BOLT, RenderWoodenBolt::new));
		add(Pair.of(IRON_BOLT, RenderIronBolt::new));
	}};
}
