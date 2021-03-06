package com.davidm1a2.afraidofthedark.client.entity.splinterDrone

import com.davidm1a2.afraidofthedark.client.entity.mcAnimatorLib.MCAModelRenderer
import com.davidm1a2.afraidofthedark.common.entity.mcAnimatorLib.animation.AnimationHandler
import com.davidm1a2.afraidofthedark.common.entity.mcAnimatorLib.math.Matrix4f
import com.davidm1a2.afraidofthedark.common.entity.mcAnimatorLib.math.Quaternion
import com.davidm1a2.afraidofthedark.common.entity.splinterDrone.EntitySplinterDroneProjectile
import net.minecraft.client.model.ModelBase
import net.minecraft.entity.Entity

/**
 * Model class that defines the splinter drone projectile model
 *
 * @constructor initializes the model
 * @property parts A map of part name to part
 * @property body The different parts of the model
 */
class ModelSplinterDroneProjectile internal constructor() : ModelBase() {
    private val parts = mutableMapOf<String, MCAModelRenderer>()
    private val body: MCAModelRenderer

    init {
        // Auto-generated from the MCAnimator software

        textureWidth = 32
        textureHeight = 32

        body = MCAModelRenderer(this, "body", 0, 0)
        body.mirror = false
        body.addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6)
        body.setInitialRotationPoint(0.0f, 0.0f, 0.0f)
        body.setInitialRotationMatrix(Matrix4f().set(Quaternion(0.0f, 0.0f, 0.0f, 1.0f)).transpose())
        body.setTextureSize(32, 32)
        parts[body.boxName] = body

        val part1 = MCAModelRenderer(this, "part1", 0, 16)
        part1.mirror = false
        part1.addBox(-2.0f, -2.0f, 0.0f, 4, 4, 4)
        part1.setInitialRotationPoint(0.0f, 0.0f, 0.0f)
        part1.setInitialRotationMatrix(Matrix4f().set(Quaternion(0.0f, 0.0f, 0.0f, 1.0f)).transpose())
        part1.setTextureSize(32, 32)
        parts[part1.boxName] = part1
        body.addChild(part1)

        val part2 = MCAModelRenderer(this, "part2", 0, 16)
        part2.mirror = false
        part2.addBox(0.0f, -2.0f, -2.0f, 4, 4, 4)
        part2.setInitialRotationPoint(0.0f, 0.0f, 0.0f)
        part2.setInitialRotationMatrix(Matrix4f().set(Quaternion(0.0f, 0.0f, 0.0f, 1.0f)).transpose())
        part2.setTextureSize(32, 32)
        parts[part2.boxName] = part2
        body.addChild(part2)

        val part3 = MCAModelRenderer(this, "part3", 0, 16)
        part3.mirror = false
        part3.addBox(-2.0f, -2.0f, -4.0f, 4, 4, 4)
        part3.setInitialRotationPoint(0.0f, 0.0f, 0.0f)
        part3.setInitialRotationMatrix(Matrix4f().set(Quaternion(0.0f, 0.0f, 0.0f, 1.0f)).transpose())
        part3.setTextureSize(32, 32)
        parts[part3.boxName] = part3
        body.addChild(part3)

        val part4 = MCAModelRenderer(this, "part4", 0, 16)
        part4.mirror = false
        part4.addBox(-4.0f, -2.0f, -2.0f, 4, 4, 4)
        part4.setInitialRotationPoint(0.0f, 0.0f, 0.0f)
        part4.setInitialRotationMatrix(Matrix4f().set(Quaternion(0.0f, 0.0f, 0.0f, 1.0f)).transpose())
        part4.setTextureSize(32, 32)
        parts[part4.boxName] = part4
        body.addChild(part4)

        val part5 = MCAModelRenderer(this, "part5", 0, 16)
        part5.mirror = false
        part5.addBox(-2.0f, 0.0f, -2.0f, 4, 4, 4)
        part5.setInitialRotationPoint(0.0f, 0.0f, 0.0f)
        part5.setInitialRotationMatrix(Matrix4f().set(Quaternion(0.0f, 0.0f, 0.0f, 1.0f)).transpose())
        part5.setTextureSize(32, 32)
        parts[part5.boxName] = part5
        body.addChild(part5)

        val part6 = MCAModelRenderer(this, "part6", 0, 16)
        part6.mirror = false
        part6.addBox(-2.0f, -4.0f, -2.0f, 4, 4, 4)
        part6.setInitialRotationPoint(0.0f, 0.0f, 0.0f)
        part6.setInitialRotationMatrix(Matrix4f().set(Quaternion(0.0f, 0.0f, 0.0f, 1.0f)).transpose())
        part6.setTextureSize(32, 32)
        parts[part6.boxName] = part6
        body.addChild(part6)
    }

    /**
     * Called every game tick to render the splinter drone projectile model
     *
     * @param entityIn        The entity to render, this must be a splinter drone projectile
     * @param limbSwing       ignored, used only by default MC
     * @param limbSwingAmount ignored, used only by default MC
     * @param ageInTicks      ignored, used only by default MC
     * @param netHeadYaw      ignored, used only by default MC
     * @param headPitch       ignored, used only by default MC
     * @param scale           The scale to render the model at
     */
    override fun render(
        entityIn: Entity?,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float,
        scale: Float
    ) {
        // Cast the entity to a splinter drone projectile model
        val entity = entityIn as EntitySplinterDroneProjectile?

        // Animate the model (moves all pieces from time t to t+1)
        AnimationHandler.performAnimationInModel(parts, entity)

        // Render the model
        body.render(scale)
    }
}
