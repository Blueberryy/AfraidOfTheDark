package com.davidm1a2.afraidofthedark.client.entity.enaria

import com.davidm1a2.afraidofthedark.common.constants.Constants
import com.davidm1a2.afraidofthedark.common.entity.enaria.EntityEnaria
import net.minecraft.client.renderer.entity.RenderLiving
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

/**
 * Renders the enaria model
 *
 * @constructor just initializes the render living renderer
 * @param renderManager The render manager to pass down
 */
class RenderEnaria(renderManager: RenderManager) : RenderLiving<EntityEnaria>(renderManager, ENARIA_MODEL, 0f) {
    /**
     * Renders the entity at a given position, yaw, and partial ticks parameter
     *
     * @param entity       The entity to render
     * @param posX         The X position of the entity to render at
     * @param posY         The Y position of the entity to render at
     * @param posZ         The Z position of the entity to render at
     * @param entityYaw    The yaw of the entity to render
     * @param partialTicks The partial ticks that have gone by since the last frame
     */
    override fun doRender(
        entity: EntityEnaria,
        posX: Double,
        posY: Double,
        posZ: Double,
        entityYaw: Float,
        partialTicks: Float
    ) {
        GL11.glPushMatrix()
        GL11.glDisable(GL11.GL_CULL_FACE)
        super.doRender(entity, posX, posY, posZ, entityYaw, partialTicks)
        GL11.glEnable(GL11.GL_CULL_FACE)
        GL11.glPopMatrix()
    }

    /**
     * Before rendering the entity transform the rendering openGL state
     *
     * @param entityLiving    The entity to render
     * @param partialTickTime The partial ticks that have gone by since the last frame
     */
    override fun preRenderCallback(entityLiving: EntityEnaria, partialTickTime: Float) {
        GL11.glRotatef(180f, 0f, 1f, 0f)
        GL11.glRotatef(180f, 0f, 0f, 1f)
        GL11.glTranslatef(0f, MODEL_HEIGHT, 0f)
    }

    /**
     * Gets the texture for the entity
     *
     * @param entity The entity to get the texture for
     * @return The texture to use for this entity
     */
    override fun getEntityTexture(entity: EntityEnaria): ResourceLocation {
        return ENARIA_TEXTURE
    }

    companion object {
        // The texture to apply to the model
        private val ENARIA_TEXTURE = ResourceLocation(Constants.MOD_ID, "textures/entity/enaria.png")
        // The model to render
        private val ENARIA_MODEL = ModelEnaria()
        // The height of the model to render at
        private const val MODEL_HEIGHT = 2.8f
    }
}
