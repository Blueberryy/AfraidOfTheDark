package com.davidm1a2.afraidofthedark.common.spell.component.property;

import com.davidm1a2.afraidofthedark.common.spell.component.InvalidValueException;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Special spell component property that encapsulates float parsing
 */
class FloatSpellComponentProperty extends SpellComponentProperty
{
    /**
     * Constructor just sets all the fields
     *
     * @param name         The name of the property being edited
     * @param description  The description of the property being edited
     * @param getter       The getter to get this property's current value
     * @param setter       The setter to be used when setting this property's value
     * @param defaultValue The default value of the property
     * @param minValue     The minimum value of the property
     * @param maxValue     The maximum value of the property
     */
    FloatSpellComponentProperty(String name, String description, Consumer<Float> setter, Supplier<Float> getter, Float defaultValue, Float minValue, Float maxValue)
    {
        super(name,
                description,
                newValue ->
                {
                    // Ensure the number is parsable
                    try
                    {
                        // Parse the float
                        Float floatValue = Float.parseFloat(newValue);
                        // Ensure the float is valid
                        if (minValue != null && floatValue < minValue)
                        {
                            setter.accept(defaultValue);
                            throw new InvalidValueException(name + " must be larger than or equal to " + minValue);
                        }
                        if (maxValue != null && floatValue > maxValue)
                        {
                            setter.accept(defaultValue);
                            throw new InvalidValueException(name + " must be smaller than than or equal to " + maxValue);
                        }
                        setter.accept(floatValue);
                    }
                    // If it's not valid return an error
                    catch (NumberFormatException e)
                    {
                        throw new InvalidValueException(newValue + " is not a valid decimal number!");
                    }
                },
                () -> getter.get().toString());
    }
}