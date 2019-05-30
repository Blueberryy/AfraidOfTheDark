package com.DavidM1A2.afraidofthedark.client.gui.specialControls;

import com.DavidM1A2.afraidofthedark.AfraidOfTheDark;
import com.DavidM1A2.afraidofthedark.client.gui.AOTDGuiHandler;
import com.DavidM1A2.afraidofthedark.client.gui.base.AOTDGuiContainer;
import com.DavidM1A2.afraidofthedark.client.gui.eventListeners.AOTDMouseListener;
import com.DavidM1A2.afraidofthedark.client.gui.events.AOTDMouseEvent;
import com.DavidM1A2.afraidofthedark.client.gui.standardControls.AOTDGuiButton;
import com.DavidM1A2.afraidofthedark.client.gui.standardControls.AOTDGuiImage;
import com.DavidM1A2.afraidofthedark.client.gui.standardControls.AOTDGuiLabel;
import com.DavidM1A2.afraidofthedark.client.gui.standardControls.AOTDGuiPanel;
import com.DavidM1A2.afraidofthedark.client.settings.ClientData;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.spell.IAOTDPlayerSpellManager;
import com.DavidM1A2.afraidofthedark.common.constants.ModCapabilities;
import com.DavidM1A2.afraidofthedark.common.constants.ModSounds;
import com.DavidM1A2.afraidofthedark.common.spell.Spell;
import net.minecraft.init.SoundEvents;
import org.lwjgl.util.Color;

/**
 * UI component representing a spell in the GUI
 */
public class AOTDGuiSpell extends AOTDGuiContainer
{
    // The spell that is being drawn by this GuiSpell
    private final Spell spell;
    // Reference to the keybind button that allows us to bind keys to spells
    private final AOTDGuiButton btnKeybind;
    // Callback function that we fire when we want a new keybind for this spell
    private Runnable keybindCallback;
    // Callback function that we fire when the delete spell button is pressed
    private Runnable deleteCallback;

    /**
     * Constructor just initializes the gui spell by laying out all necessary controls
     *
     * @param x The x position of the control
     * @param y The y position of the control
     * @param width The width of the control
     * @param height The height of the control
     * @param spell The spell that this control represents
     */
    public AOTDGuiSpell(int x, int y, int width, int height, Spell spell)
    {
        super(x, y, width, height);
        this.spell = spell;

        // The background image to hold all the buttons
        AOTDGuiImage background = new AOTDGuiImage(0, 0, width, height, "afraidofthedark:textures/gui/spell_list/spell_background.png");
        this.add(background);

        // The container for spell name
        AOTDGuiPanel spellNameContainer = new AOTDGuiPanel(10, 3, width - 20, 15, false);

        // The label holding the actual spell name
        AOTDGuiLabel lblSpellName = new AOTDGuiLabel(0, 0, spellNameContainer.getWidth(), spellNameContainer.getHeight(), ClientData.getInstance().getTargaMSHandFontSized(30f));
        // Set the name label's name and color
        lblSpellName.setText(this.spell.getName());
        lblSpellName.setTextColor(new Color(245, 61, 199));
        // Update the hover text of the container and add the spell label to it
        spellNameContainer.setHoverText(lblSpellName.getText());
        spellNameContainer.add(lblSpellName);
        this.add(spellNameContainer);

        // When we hover any button play hover sound
        AOTDMouseListener hoverSound = new AOTDMouseListener()
        {
            @Override
            public void mouseEntered(AOTDMouseEvent event)
            {
                // Play a hover sound for visible buttons
                if (event.getSource().isVisible() && event.getSource().isHovered())
                {
                    entityPlayer.playSound(ModSounds.SPELL_CRAFTING_BUTTON_HOVER, 0.7f, 1.9f);
                }
            }

            @Override
            public void mouseClicked(AOTDMouseEvent event)
            {
                // Play a clicked sound for visible buttons
                if (event.getSource().isVisible() && event.getSource().isHovered())
                {
                    entityPlayer.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0f, 1.0f);
                }
            }
        };

        // All 3 buttons have a width of 20
        final int BUTTON_WIDTHS = 20;
        // Compute the gap between buttons
        final int BUTTON_GAP = (width - BUTTON_WIDTHS * 3) / 4;
        // Compute the x offset on the left of the first button to ensure all 3 buttons are centered
        final int BUTTON_X_OFFSET = (width - (BUTTON_GAP * 2 + BUTTON_WIDTHS * 3)) / 2;

        // Create a button to edit the spell
        AOTDGuiButton btnEdit = new AOTDGuiButton(BUTTON_X_OFFSET, 22, BUTTON_WIDTHS, 13, null, "afraidofthedark:textures/gui/spell_list/spell_edit.png", "afraidofthedark:textures/gui/spell_list/spell_edit_hovered.png");
        btnEdit.addMouseListener(hoverSound);
        btnEdit.setHoverText("Edit Spell");
        btnEdit.addMouseListener(new AOTDMouseListener()
        {
            @Override
            public void mouseClicked(AOTDMouseEvent event)
            {
                if (event.getSource().isHovered() && event.getClickedButton() == AOTDMouseEvent.MouseButtonClicked.Left)
                {
                    // Open the spell edit GUI
                    entityPlayer.openGui(AfraidOfTheDark.INSTANCE, AOTDGuiHandler.SPELL_CRAFTING_ID, entityPlayer.world, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
                }
            }
        });
        this.add(btnEdit);

        // Create a button to delete a spell
        AOTDGuiButton btnDelete = new AOTDGuiButton(BUTTON_X_OFFSET + BUTTON_GAP + BUTTON_WIDTHS, 22, BUTTON_WIDTHS, 13, null, "afraidofthedark:textures/gui/spell_list/spell_delete.png", "afraidofthedark:textures/gui/spell_list/spell_delete_hovered.png");
        btnDelete.addMouseListener(hoverSound);
        btnDelete.addMouseListener(new AOTDMouseListener()
        {
            @Override
            public void mousePressed(AOTDMouseEvent event)
            {
                if (event.getSource().isHovered() && event.getSource().isVisible() && event.getClickedButton() == AOTDMouseEvent.MouseButtonClicked.Left)
                {
                    if (deleteCallback != null)
                    {
                        deleteCallback.run();
                    }
                }
            }
        });
        btnDelete.setHoverTexts("Delete Spell", "This cannot be undone");
        this.add(btnDelete);

        // Create a button to keybind this spell
        btnKeybind = new AOTDGuiButton(BUTTON_X_OFFSET + (BUTTON_GAP + BUTTON_WIDTHS) * 2, 22, BUTTON_WIDTHS, 13, null, "afraidofthedark:textures/gui/spell_list/spell_keybind.png", "afraidofthedark:textures/gui/spell_list/spell_keybind_hovered.png");
        btnKeybind.addMouseListener(hoverSound);
        btnKeybind.addMouseListener(new AOTDMouseListener()
        {
            @Override
            public void mousePressed(AOTDMouseEvent event)
            {
                if (event.getSource().isHovered() && event.getSource().isVisible() && event.getClickedButton() == AOTDMouseEvent.MouseButtonClicked.Left)
                {
                    if (keybindCallback != null)
                    {
                        btnKeybind.setHoverText("Awaiting keypress...");
                        keybindCallback.run();
                    }
                }
            }
        });
        this.add(btnKeybind);

        // Refresh the spell labels
        this.refreshLabels();
    }

    /**
     * Refreshes this gui spell based on the current spell state if it's changed
     */
    public void refreshLabels()
    {
        // Grab the player's spell manager
        IAOTDPlayerSpellManager spellManager = entityPlayer.getCapability(ModCapabilities.PLAYER_SPELL_MANAGER, null);
        // Get the keybinding for the spell
        String keybindingForSpell = spellManager.getKeybindingForSpell(this.spell);
        // if the keybind is non-null show it, otherwise mention it's unbound
        if (keybindingForSpell != null)
        {
            btnKeybind.setHoverText("Spell is bound to: " + keybindingForSpell);
        }
        else
        {
            btnKeybind.setHoverText("Spell is unbound.");
        }
    }

    /**
     * Sets the callback when the keybind button is pressed
     *
     * @param keybindCallback The keybind callback to fire
     */
    public void setKeybindCallback(Runnable keybindCallback)
    {
        this.keybindCallback = keybindCallback;
    }

    /**
     * Sets the callback when the delete button is pressed
     *
     * @param deleteCallback The delete callback to fire
     */
    public void setDeleteCallback(Runnable deleteCallback)
    {
        this.deleteCallback = deleteCallback;
    }

    /**
     * @return The spell that this gui spell represents
     */
    public Spell getSpell()
    {
        return spell;
    }
}
