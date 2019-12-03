package com.davidm1a2.afraidofthedark.common.constants;

import com.davidm1a2.afraidofthedark.common.spell.component.deliveryMethod.*;
import com.davidm1a2.afraidofthedark.common.spell.component.deliveryMethod.base.SpellDeliveryMethodEntry;
import net.minecraft.util.ResourceLocation;

/**
 * A static class containing all of our spell delivery method references for us
 */
public class ModSpellDeliveryMethods
{
    public static final SpellDeliveryMethodEntry SELF = new SpellDeliveryMethodEntry(new ResourceLocation(Constants.MOD_ID, "self"), SpellDeliveryMethodSelf::new);
    public static final SpellDeliveryMethodEntry PROJECTILE = new SpellDeliveryMethodEntry(new ResourceLocation(Constants.MOD_ID, "projectile"), SpellDeliveryMethodProjectile::new);
    public static final SpellDeliveryMethodEntry AOE = new SpellDeliveryMethodEntry(new ResourceLocation(Constants.MOD_ID, "aoe"), SpellDeliveryMethodAOE::new);
    public static final SpellDeliveryMethodEntry LASER = new SpellDeliveryMethodEntry(new ResourceLocation(Constants.MOD_ID, "laser"), SpellDeliveryMethodLaser::new);
    public static final SpellDeliveryMethodEntry DELAY = new SpellDeliveryMethodEntry(new ResourceLocation(Constants.MOD_ID, "delay"), SpellDeliveryMethodDelay::new);

    // An array containing a list of spell delivery methods that AOTD adds
    public static final SpellDeliveryMethodEntry[] SPELL_DELIVERY_METHODS = new SpellDeliveryMethodEntry[]
            {
                    SELF,
                    PROJECTILE,
                    AOE,
                    LASER,
                    DELAY
            };
}