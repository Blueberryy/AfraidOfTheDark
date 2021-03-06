package com.davidm1a2.afraidofthedark.client.sound

import com.davidm1a2.afraidofthedark.common.constants.ModDimensions
import com.davidm1a2.afraidofthedark.common.constants.ModSounds
import net.minecraft.client.Minecraft
import net.minecraft.util.SoundCategory

/**
 * Class representing the bell ringing sound in the nightmare
 *
 * @constructor sets the sound event to play and sound category
 */
class BellsRinging : PlayerFollowingSound(ModSounds.BELLS, SoundCategory.AMBIENT) {
    init {
        // This sound loops
        repeat = true
    }

    /**
     * Ensure that this only players in the nightmare when the player is not dead
     */
    override fun update() {
        super.update()

        val entityPlayer = Minecraft.getMinecraft().player!!
        if (entityPlayer.isDead || entityPlayer.dimension != ModDimensions.NIGHTMARE.id) {
            donePlaying = true
        }
    }

    /**
     * Gets the repeat delay for the sound, this will be somewhat random
     *
     * @return The delay between sound plays
     */
    override fun getRepeatDelay(): Int {
        // Wait a minute (60 seconds * 20 ticks / second)
        return 60 * 20
    }
}