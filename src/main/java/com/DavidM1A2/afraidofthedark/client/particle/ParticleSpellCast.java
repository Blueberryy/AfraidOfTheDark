package com.DavidM1A2.afraidofthedark.client.particle;

import com.DavidM1A2.afraidofthedark.common.constants.ModSprites;
import net.minecraft.world.World;

/**
 * Particle representing a player's spell cast
 */
public class ParticleSpellCast extends AOTDParticle
{
    /**
     * Constructor takes the x,y,z position of the particle and the world
     *
     * @param worldIn  The world the particle is at
     * @param xCoordIn The x position of the basic attack
     * @param yCoordIn The y position of the basic attack
     * @param zCoordIn The z position of the basic attack
     */
    public ParticleSpellCast(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn)
    {
        super(worldIn, ModSprites.SPELL_CAST, xCoordIn, yCoordIn, zCoordIn, 0, 0, 0);
        // 0.5-1.5 second lifespan
        this.particleMaxAge = this.rand.nextInt(10) + 30;
        // Make the particles noticable
        this.particleScale = 2.0f + this.rand.nextFloat() * 2;
        // Random motion
        this.motionX = (this.rand.nextFloat() - 0.5) * 0.2;
        this.motionY = this.rand.nextFloat() * 0.1;
        this.motionZ = (this.rand.nextFloat() - 0.5) * 0.2;
    }

    /**
     * Called before the particle is moved, update the motionXYZ here
     */
    @Override
    void updateMotionXYZ()
    {
        // Don't update motion
    }
}