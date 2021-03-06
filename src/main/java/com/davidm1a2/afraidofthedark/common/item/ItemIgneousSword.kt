package com.davidm1a2.afraidofthedark.common.item

import com.davidm1a2.afraidofthedark.common.capabilities.getResearch
import com.davidm1a2.afraidofthedark.common.constants.ModDamageSources.getSilverDamage
import com.davidm1a2.afraidofthedark.common.constants.ModResearches
import com.davidm1a2.afraidofthedark.common.constants.ModToolMaterials
import com.davidm1a2.afraidofthedark.common.item.core.AOTDItemChargeableSword
import net.minecraft.client.Minecraft
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Enchantments
import net.minecraft.item.ItemStack
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World

/**
 * Igneous sword lets you set fire to an area with its special ability
 *
 * @constructor sets the sword properties
 */
class ItemIgneousSword : AOTDItemChargeableSword("igneous_sword", ModToolMaterials.IGNEOUS)
{
    init
    {
        percentChargePerAttack = 35.0
    }

    /**
     * Called when you left click an entity with the sword
     *
     * @param stack The itemstack that the entity was clicked with
     * @param player The player that clicked the entity
     * @param entity The entity that was clicked
     * @return True to cancel the interaction, false otherwise
     */
    override fun onLeftClickEntity(stack: ItemStack, player: EntityPlayer, entity: Entity): Boolean {
        // If igneous is researched allow the sword to function
        if (player.getResearch().isResearched(ModResearches.IGNEOUS)) {
            // The fire burn time is heavily upgraded by fire aspect enchantment
            entity.setFire(5 + EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) * 10)
            // Attack the entity from silver damage
            entity.attackEntityFrom(getSilverDamage(player), attackDamage)
        } else {
            return true
        }

        return super.onLeftClickEntity(stack, player, entity)
    }

    /**
     * Adds tooltip information to the sword
     *
     * @param stack The itemstack to get information about
     * @param world The world that the item is in
     * @param tooltip The tooltip to return
     * @param flag True if advanced tooltips are on, false otherwise
     */
    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {
        val player = Minecraft.getMinecraft().player
        if (player != null && player.getResearch().isResearched(ModResearches.IGNEOUS)) {
            tooltip.add("Magical items will never break.")
            tooltip.add("Right click to use an AOE fire strike")
            tooltip.add("centered on the block you are looking")
            tooltip.add("at when charged to 100%")
        } else {
            tooltip.add("I'm not sure how to use this.")
        }
    }

    /**
     * Called to strike down fire on entities in an area
     *
     * @param itemStack    The itemstack that was charged
     * @param world        The world the charge attack happened in
     * @param entityPlayer The player who used the charge attack
     * @return True if the location being aimed at is valid, false otherwise
     */
    override fun performChargeAttack(itemStack: ItemStack, world: World, entityPlayer: EntityPlayer): Boolean {
        // Grab the player's eye posiiton and look vector
        val fromVec = entityPlayer.getPositionEyes(0f)
        val lookDir = entityPlayer.lookVec

        // The vector we want to ray trace to
        val toVec = fromVec.add(lookDir.scale(SPECIAL_FIRE_RANGE_BLOCKS.toDouble()))
        // Perform the ray trace
        val rayTraceResult: RayTraceResult? = world.rayTraceBlocks(fromVec, toVec)

        // Ensure we hit something
        if (rayTraceResult?.hitVec != null) {
            // Grab the hit block position
            val hitPos = BlockPos(rayTraceResult.hitVec.x, rayTraceResult.hitVec.y, rayTraceResult.hitVec.z)
            // Grab all surrounding entities
            val surroundingEntities = world.getEntitiesWithinAABBExcludingEntity(
                entityPlayer,
                AxisAlignedBB(hitPos).grow(HIT_RANGE.toDouble())
            )

            // Set each entity living on fire
            for (entity in surroundingEntities) {
                (entity as? EntityLivingBase)?.setFire(20)
            }

            // True, the effect was procd
            return true
        }
        return false
    }

    companion object {
        // The max range the fire can be cast
        private const val SPECIAL_FIRE_RANGE_BLOCKS = 50
        // The AOE fire range
        private const val HIT_RANGE = 5
    }
}