package com.davidm1a2.afraidofthedark.client.dimension

import com.davidm1a2.afraidofthedark.common.constants.Constants
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.IRenderHandler
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * Class that renders the void chest 'sky' texture
 */
class VoidChestSkyRenderer : IRenderHandler() {
    /**
     * Called to render the sky
     *
     * @param partialTicks The number of partial ticks since the last tick
     * @param world        The world to render in
     * @param mc           The minecraft instance
     */
    @SideOnly(Side.CLIENT)
    override fun render(partialTicks: Float, world: WorldClient, mc: Minecraft) {
        ///
        /// Code below found online and modified slightly
        ///

        GlStateManager.disableFog()
        GlStateManager.disableAlpha()
        GlStateManager.enableBlend()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        RenderHelper.disableStandardItemLighting()
        GlStateManager.depthMask(false)
        val tessellator = Tessellator.getInstance()
        val bufferBuilder = tessellator.buffer

        for (i in 0..5) {
            GlStateManager.pushMatrix()

            when (i) {
                1 -> {
                    Minecraft.getMinecraft().renderEngine.bindTexture(VOID_CHEST_SKY_SIDE_2)
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f)
                }
                2 -> {
                    Minecraft.getMinecraft().renderEngine.bindTexture(VOID_CHEST_SKY_SIDE_4)
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f)
                }
                3 -> {
                    Minecraft.getMinecraft().renderEngine.bindTexture(VOID_CHEST_SKY_TOP)
                    GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f)
                }
                4 -> {
                    Minecraft.getMinecraft().renderEngine.bindTexture(VOID_CHEST_SKY_SIDE_3)
                    GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f)
                }
                5 -> {
                    Minecraft.getMinecraft().renderEngine.bindTexture(VOID_CHEST_SKY_SIDE_1)
                    GlStateManager.rotate(-90.0f, 0.0f, 0.0f, 1.0f)
                }
                else -> Minecraft.getMinecraft().renderEngine.bindTexture(VOID_CHEST_SKY_BOTTOM)
            }

            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
            bufferBuilder.pos(-100.0, -100.0, -100.0).tex(0.0, 0.0).endVertex()
            bufferBuilder.pos(-100.0, -100.0, 100.0).tex(0.0, 1.0).endVertex()
            bufferBuilder.pos(100.0, -100.0, 100.0).tex(1.0, 1.0).endVertex()
            bufferBuilder.pos(100.0, -100.0, -100.0).tex(1.0, 0.0).endVertex()
            tessellator.draw()
            GlStateManager.popMatrix()
        }

        GlStateManager.depthMask(true)
        GlStateManager.enableTexture2D()
        GlStateManager.enableAlpha()
    }

    companion object {
        // Textures used by the 6 sides of the skybox
        private val VOID_CHEST_SKY_TOP = ResourceLocation(Constants.MOD_ID, "textures/skybox/void_chest_top.png")
        private val VOID_CHEST_SKY_BOTTOM = ResourceLocation(Constants.MOD_ID, "textures/skybox/void_chest_bottom.png")
        private val VOID_CHEST_SKY_SIDE_1 = ResourceLocation(Constants.MOD_ID, "textures/skybox/void_chest_side_1.png")
        private val VOID_CHEST_SKY_SIDE_2 = ResourceLocation(Constants.MOD_ID, "textures/skybox/void_chest_side_2.png")
        private val VOID_CHEST_SKY_SIDE_3 = ResourceLocation(Constants.MOD_ID, "textures/skybox/void_chest_side_3.png")
        private val VOID_CHEST_SKY_SIDE_4 = ResourceLocation(Constants.MOD_ID, "textures/skybox/void_chest_side_4.png")
    }
}
