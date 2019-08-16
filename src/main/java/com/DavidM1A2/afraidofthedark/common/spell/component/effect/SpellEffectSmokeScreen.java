package com.DavidM1A2.afraidofthedark.common.spell.component.effect;

import com.DavidM1A2.afraidofthedark.AfraidOfTheDark;
import com.DavidM1A2.afraidofthedark.client.particle.AOTDParticleRegistry;
import com.DavidM1A2.afraidofthedark.common.constants.ModSpellEffects;
import com.DavidM1A2.afraidofthedark.common.packets.otherPackets.SyncParticle;
import com.DavidM1A2.afraidofthedark.common.spell.Spell;
import com.DavidM1A2.afraidofthedark.common.spell.component.EditableSpellComponentProperty;
import com.DavidM1A2.afraidofthedark.common.spell.component.effect.base.AOTDSpellEffect;
import com.DavidM1A2.afraidofthedark.common.spell.component.effect.base.SpellEffectEntry;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Creates a smoke screen at a given effect location
 */
public class SpellEffectSmokeScreen extends AOTDSpellEffect
{
    // NBT constants for smoke density
    private static final String NBT_SMOKE_DENSITY = "smoke_density";

    // The default smoke density
    private static final int DEFAULT_SMOKE_DENSITY = 10;

    // The amount of smoke density this effect gives
    private int smokeDensity = DEFAULT_SMOKE_DENSITY;

    /**
     * Constructor adds the editable prop
     */
    public SpellEffectSmokeScreen()
    {
        super();
        this.addEditableProperty(new EditableSpellComponentProperty(
                "Smoke Density",
                "The number of particles present in the smoke screen.",
                () -> Integer.toString(this.smokeDensity),
                newValue ->
                {
                    // Ensure the number is parsable
                    try
                    {
                        // Parse the smoke density
                        this.smokeDensity = Integer.parseInt(newValue);
                        // Ensure smoke density is valid
                        if (this.smokeDensity > 0)
                        {
                            return null;
                        }
                        else
                        {
                            this.smokeDensity = DEFAULT_SMOKE_DENSITY;
                            return "Smoke density must be larger than 0";
                        }
                    }
                    // If it's not valid return an error
                    catch (NumberFormatException e)
                    {
                        return newValue + " is not a valid integer!";
                    }
                }
        ));
    }

    /**
     * Gets the cost of the effect
     *
     * @return The cost of the effect
     */
    @Override
    public double getCost()
    {
        return this.smokeDensity / 5.0;
    }

    /**
     * Performs the effect against a given entity
     *
     * @param spell           The spell that caused the effect
     * @param spellStageIndex The spell stage that this effect is a part of
     * @param effectIndex     The effect slot that this effect is in
     * @param entityHit       The entity that the effect should be applied to
     */
    @Override
    public void performEffect(Spell spell, int spellStageIndex, int effectIndex, Entity entityHit)
    {
        List<Vec3d> positions = new ArrayList<>();
        // Create smokeDensity random smoke particles around the entity
        AxisAlignedBB boundingBox = entityHit.getEntityBoundingBox();
        for (int i = 0; i < this.smokeDensity; i++)
        {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            positions.add(new Vec3d(random.nextDouble(boundingBox.minX, boundingBox.maxX), random.nextDouble(boundingBox.minY, boundingBox.maxY), random.nextDouble(boundingBox.minZ, boundingBox.maxZ)));
        }
        AfraidOfTheDark.INSTANCE.getPacketHandler().sendToAllAround(
                new SyncParticle(AOTDParticleRegistry.ParticleTypes.SMOKE_SCREEN_ID, positions, Collections.nCopies(positions.size(), Vec3d.ZERO)),
                new NetworkRegistry.TargetPoint(entityHit.dimension, entityHit.posX, entityHit.posY, entityHit.posZ, 100));
    }

    /**
     * Performs the effect at a given position in the world
     *
     * @param spell           The spell that caused the effect
     * @param spellStageIndex The spell stage that this effect is a part of
     * @param effectIndex     The effect slot that this effect is in
     * @param world           The world the effect is being fired in
     * @param position        The position the effect is being performed at
     */
    @Override
    public void performEffect(Spell spell, int spellStageIndex, int effectIndex, World world, BlockPos position)
    {
        List<Vec3d> positions = new ArrayList<>();
        // Create smokeDensity random smoke particles
        for (int i = 0; i < this.smokeDensity; i++)
        {
            positions.add(new Vec3d(position.getX() + Math.random(), position.getY() + Math.random(), position.getZ() + Math.random()));
        }
        AfraidOfTheDark.INSTANCE.getPacketHandler().sendToAllAround(
                new SyncParticle(AOTDParticleRegistry.ParticleTypes.SMOKE_SCREEN_ID, positions, Collections.nCopies(positions.size(), Vec3d.ZERO)),
                new NetworkRegistry.TargetPoint(world.provider.getDimension(), position.getX(), position.getY(), position.getZ(), 100));
    }

    /**
     * Should get the SpellEffectEntry registry's type
     *
     * @return The registry entry that this component was built with, used for deserialization
     */
    @Override
    public SpellEffectEntry getEntryRegistryType()
    {
        return ModSpellEffects.SMOKE_SCREEN;
    }

    /**
     * Serializes the spell component to NBT, override to add additional fields
     *
     * @return An NBT compound containing any required spell component info
     */
    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = super.serializeNBT();

        nbt.setInteger(NBT_SMOKE_DENSITY, this.smokeDensity);

        return nbt;
    }

    /**
     * Deserializes the state of this spell component from NBT
     *
     * @param nbt The NBT to deserialize from
     */
    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        super.deserializeNBT(nbt);
        this.smokeDensity = nbt.getInteger(NBT_SMOKE_DENSITY);
    }
}
