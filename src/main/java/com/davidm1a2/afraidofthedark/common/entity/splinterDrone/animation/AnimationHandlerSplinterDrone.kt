package com.davidm1a2.afraidofthedark.common.entity.splinterDrone.animation

import com.davidm1a2.afraidofthedark.common.entity.mcAnimatorLib.IMCAnimatedEntity
import com.davidm1a2.afraidofthedark.common.entity.mcAnimatorLib.animation.AnimationHandler
import com.davidm1a2.afraidofthedark.common.entity.mcAnimatorLib.animation.Channel

/**
 * Animation handler class for the splinter drone
 *
 * @param entity The entity that this animator belongs to
 */
class AnimationHandlerSplinterDrone(entity: IMCAnimatedEntity) : AnimationHandler(entity) {
    /**
     * Begins playing a specific animation given a name and starting frame
     *
     * @param name          The animation to play
     * @param startingFrame The frame to begin playing at
     */
    override fun activateAnimation(name: String, startingFrame: Float) {
        super.activateAnimation(ANIMATION_TO_CHANNEL, name, startingFrame)
    }

    /**
     * Stops playing a given animation
     *
     * @param name The animation to stop playing
     */
    override fun stopAnimation(name: String) {
        super.stopAnimation(ANIMATION_TO_CHANNEL, name)
    }

    companion object {
        // Map of animation name to channel
        private val ANIMATION_TO_CHANNEL = mapOf(
            "Activate" to ChannelActivate("Activate", 25.0f, 100, Channel.LINEAR),
            "Charge" to ChannelCharge("Charge", 100.0f, 100, Channel.LINEAR),
            "Idle" to ChannelIdle("Idle", 25.0f, 100, Channel.LINEAR)
        )
    }
}