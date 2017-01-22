package com.DavidM1A2.AfraidOfTheDark.client.gui.customControls;

import org.lwjgl.input.Keyboard;

import com.DavidM1A2.AfraidOfTheDark.client.gui.GuiHandler;
import com.DavidM1A2.AfraidOfTheDark.client.gui.baseControls.AOTDGuiButton;
import com.DavidM1A2.AfraidOfTheDark.client.gui.baseControls.AOTDGuiImage;
import com.DavidM1A2.AfraidOfTheDark.client.gui.baseControls.AOTDGuiLabel;
import com.DavidM1A2.AfraidOfTheDark.client.gui.baseControls.AOTDGuiPanel;
import com.DavidM1A2.AfraidOfTheDark.client.gui.eventListeners.AOTDKeyListener;
import com.DavidM1A2.AfraidOfTheDark.client.gui.eventListeners.AOTDMouseListener;
import com.DavidM1A2.AfraidOfTheDark.client.gui.events.AOTDKeyEvent;
import com.DavidM1A2.AfraidOfTheDark.client.gui.events.AOTDMouseEvent;
import com.DavidM1A2.AfraidOfTheDark.client.gui.events.AOTDMouseEvent.MouseButtonClicked;
import com.DavidM1A2.AfraidOfTheDark.client.gui.guiScreens.SpellSelectionGUI;
import com.DavidM1A2.AfraidOfTheDark.client.settings.ClientData;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModCapabilities;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModSounds;
import com.DavidM1A2.AfraidOfTheDark.common.reference.Reference;
import com.DavidM1A2.AfraidOfTheDark.common.spell.Spell;
import com.DavidM1A2.AfraidOfTheDark.common.spell.SpellManager;

public class AOTDGuiSpell extends AOTDGuiPanel
{
	private Spell mySpell;
	private boolean waitingOnKeyInput = false;
	private AOTDGuiButton keyBind;
	private AOTDGuiButton delete;
	private SpellSelectionGUI callback;

	public AOTDGuiSpell(int x, int y, int width, int height, boolean scissorEnabled, final Spell source, SpellSelectionGUI callback)
	{
		super(x, y, width, height, scissorEnabled);

		this.callback = callback;

		mySpell = source;

		AOTDGuiImage background = new AOTDGuiImage(0, 0, width, height, "afraidofthedark:textures/gui/spell_crafting/spell_background.png");
		this.add(background);

		AOTDGuiPanel spellNameContainer = new AOTDGuiPanel(5, 2, width - 15, 15, false);
		this.add(spellNameContainer);

		AOTDGuiLabel spellName = new AOTDGuiLabel(0, 0, ClientData.getTargaMSHandFontSized(30f));
		spellName.setText(source.getName());
		spellName.setTextColor(new float[]
		{ 0.96f, 0.24f, 0.78f, 1.0f });
		spellName.setMaxStringLength(18);
		spellNameContainer.setHoverText(source.getName());
		spellNameContainer.add(spellName);

		AOTDMouseListener highlightEffect = new AOTDMouseListener()
		{
			@Override
			public void mouseExited(AOTDMouseEvent event)
			{
				event.getSource().brightenColor(0.1f);
			}

			@Override
			public void mouseEntered(AOTDMouseEvent event)
			{
				event.getSource().darkenColor(0.1f);
				if (AOTDGuiSpell.this.isVisible())
					entityPlayer.playSound(ModSounds.buttonHover, 0.7f, 1.9f);
			}
		};

		AOTDGuiButton edit = new AOTDGuiButton(11, 22, 22, 13, null, "afraidofthedark:textures/gui/spell_crafting/spell_background_edit.png");
		edit.addMouseListener(highlightEffect);
		edit.setHoverText("Edit spell");
		edit.addMouseListener(new AOTDMouseListener()
		{
			@Override
			public void mousePressed(AOTDMouseEvent event)
			{
				ClientData.spellToBeEdited = source;
				if (event.getSource().isHovered() && event.getClickedButton() == MouseButtonClicked.Left)
					entityPlayer.openGui(Reference.MOD_ID, GuiHandler.SPELL_CRAFTING_ID, entityPlayer.worldObj, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
			}
		});
		this.add(edit);

		this.delete = new AOTDGuiButton(38, 22, 22, 13, null, "afraidofthedark:textures/gui/spell_crafting/spell_background_delete.png");
		this.delete.addMouseListener(highlightEffect);
		this.delete.setHoverText("Delete spell. This cannot be undone");
		this.add(delete);

		this.keyBind = new AOTDGuiButton(66, 22, 22, 13, null, "afraidofthedark:textures/gui/spell_crafting/spell_background_key_bind.png");
		this.keyBind.addMouseListener(highlightEffect);
		SpellManager spellManager = entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null).getSpellManager();
		this.keyBind.setHoverText("Spell currently bound to: " + (spellManager.keyFromSpell(mySpell) == null ? "None" : spellManager.keyFromSpell(mySpell)));
		this.keyBind.addMouseListener(new AOTDMouseListener()
		{
			@Override
			public void mousePressed(AOTDMouseEvent event)
			{
				if (event.getSource().isHovered() && AOTDGuiSpell.this.isVisible() && !waitingOnKeyInput && event.getClickedButton() == MouseButtonClicked.Left)
				{
					waitingOnKeyInput = true;
					event.getSource().darkenColor(0.3f);
				}
				else if (waitingOnKeyInput)
				{
					waitingOnKeyInput = false;
					event.getSource().brightenColor(0.3f);
				}
			}
		});
		this.add(this.keyBind);

		this.addKeyListener(new AOTDKeyListener()
		{
			@Override
			public void keyTyped(AOTDKeyEvent event)
			{
				if (waitingOnKeyInput)
				{
					SpellManager spellManager = entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null).getSpellManager();
					spellManager.setKeybindingToSpell(Keyboard.getKeyName(event.getKeyCode()), mySpell);
					AOTDGuiSpell.this.keyBind.setHoverText("Spell currently bound to: " + (spellManager.keyFromSpell(mySpell) == null ? "None" : spellManager.keyFromSpell(mySpell)));
					waitingOnKeyInput = false;
					AOTDGuiSpell.this.keyBind.brightenColor(0.3f);
					AOTDGuiSpell.this.callback.update(AOTDGuiSpell.this);
				}
			}
		});
	}

	public AOTDGuiButton getDeleteButton()
	{
		return this.delete;
	}

	public AOTDGuiButton getKeyBindButton()
	{
		return this.keyBind;
	}

	public Spell getSpell()
	{
		return this.mySpell;
	}
}