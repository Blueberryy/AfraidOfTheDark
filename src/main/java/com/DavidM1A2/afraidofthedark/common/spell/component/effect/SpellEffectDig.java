package com.DavidM1A2.afraidofthedark.common.spell.component.effect;

import com.DavidM1A2.afraidofthedark.common.constants.ModSpellEffects;
import com.DavidM1A2.afraidofthedark.common.spell.component.deliveryMethod.base.DeliveryTransitionState;
import com.DavidM1A2.afraidofthedark.common.spell.component.effect.base.AOTDSpellEffect;
import com.DavidM1A2.afraidofthedark.common.spell.component.effect.base.SpellEffectEntry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Dig effect digs a block
 */
public class SpellEffectDig extends AOTDSpellEffect
{
    /**
     * Constructor just calls super, there's nothing else to init
     */
    public SpellEffectDig()
    {
        super();
    }

    /**
     * Gets the cost of the effect given the state of the effect
     *
     * @return The cost of dig is 14
     */
    @Override
    public double getCost()
    {
        return 14;
    }

    /**
     * Performs the effect
     *
     * @param state The state that the spell is in
     */
    @Override
    public void procEffect(DeliveryTransitionState state)
    {
        World world = state.getWorld();
        if (state.getEntity() != null)
        {
            Entity entity = state.getEntity();
            // Digs the block under the player
            BlockPos blockPos = entity.getPosition().down();
            if (this.canBlockBeDestroyed(world, blockPos))
            {
                this.createParticlesAt(1, 3, new Vec3d(entity.posX, entity.posY, entity.posZ), entity.dimension);
                world.destroyBlock(blockPos, true);
            }
        }
        else
        {
            BlockPos position = new BlockPos(state.getPosition());
            // Digs the block at the position
            if (this.canBlockBeDestroyed(world, position))
            {
                this.createParticlesAt(1, 3, new Vec3d(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5), world.provider.getDimension());
                world.destroyBlock(position, true);
            }
        }
    }

    /**
     * Tests if a given block can be broken with a dig spell
     *
     * @param world    The world the block is in
     * @param blockPos The pos the block is at
     * @return True if the block can be destroyed, false otherwise
     */
    private boolean canBlockBeDestroyed(World world, BlockPos blockPos)
    {
        IBlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock().getBlockHardness(blockState, world, blockPos) != -1;
    }

    /**
     * Should get the SpellEffectEntry registry's type
     *
     * @return The registry entry that this component was built with, used for deserialization
     */
    @Override
    public SpellEffectEntry getEntryRegistryType()
    {
        return ModSpellEffects.DIG;
    }
}
