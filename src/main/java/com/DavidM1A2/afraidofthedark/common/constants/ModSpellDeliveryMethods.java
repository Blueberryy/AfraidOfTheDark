package com.DavidM1A2.afraidofthedark.common.constants;

import com.DavidM1A2.afraidofthedark.common.spell.component.deliveryMethod.SpellDeliveryMethodSelf;
import com.DavidM1A2.afraidofthedark.common.spell.component.deliveryMethod.base.SpellDeliveryMethodEntry;
import net.minecraft.util.ResourceLocation;

/**
 * A static class containing all of our spell delivery method references for us
 */
public class ModSpellDeliveryMethods
{
    public static final SpellDeliveryMethodEntry SELF = new SpellDeliveryMethodEntry(new ResourceLocation(Constants.MOD_ID, "self"), SpellDeliveryMethodSelf::new);

    // An array containing a list of spell delivery methods that AOTD adds
    public static final SpellDeliveryMethodEntry[] SPELL_DELIVERY_METHODS = new SpellDeliveryMethodEntry[]
            {
                    SELF
            };
}