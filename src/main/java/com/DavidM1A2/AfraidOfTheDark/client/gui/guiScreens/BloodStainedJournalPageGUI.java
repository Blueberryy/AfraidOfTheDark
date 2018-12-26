/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.client.gui.guiScreens;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.DavidM1A2.AfraidOfTheDark.AfraidOfTheDark;
import com.DavidM1A2.AfraidOfTheDark.client.gui.GuiHandler;
import com.DavidM1A2.AfraidOfTheDark.client.gui.baseControls.AOTDGuiButton;
import com.DavidM1A2.AfraidOfTheDark.client.gui.baseControls.AOTDGuiImage;
import com.DavidM1A2.AfraidOfTheDark.client.gui.baseControls.AOTDGuiLabel;
import com.DavidM1A2.AfraidOfTheDark.client.gui.baseControls.AOTDGuiPanel;
import com.DavidM1A2.AfraidOfTheDark.client.gui.baseControls.AOTDGuiRecipe;
import com.DavidM1A2.AfraidOfTheDark.client.gui.baseControls.AOTDGuiTextBox;
import com.DavidM1A2.AfraidOfTheDark.client.gui.eventListeners.AOTDMouseListener;
import com.DavidM1A2.AfraidOfTheDark.client.gui.events.AOTDMouseEvent;
import com.DavidM1A2.AfraidOfTheDark.client.gui.events.AOTDMouseEvent.MouseButtonClicked;
import com.DavidM1A2.AfraidOfTheDark.client.settings.ClientData;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModSounds;
import com.DavidM1A2.AfraidOfTheDark.common.utility.Utility;
import com.DavidM1A2.AfraidOfTheDark.common.utility.recipe.ConvertedRecipe;
import com.DavidM1A2.AfraidOfTheDark.common.utility.recipe.RecipeUtility;

import net.minecraft.item.Item;

public class BloodStainedJournalPageGUI extends AOTDGuiScreen
{
	private final String COMPLETE_TEXT;

	private final List<String> textOnEachPage = new LinkedList<String>();

	private AOTDGuiTextBox leftPage;
	private AOTDGuiTextBox rightPage;

	private AOTDGuiButton forwardButton;
	private AOTDGuiButton backwardButton;

	private AOTDGuiLabel leftPageNumber;
	private AOTDGuiLabel rightPageNumber;

	private AOTDGuiRecipe topLeftRecipe;
	private AOTDGuiRecipe bottomLeftRecipe;
	private AOTDGuiRecipe topRightRecipe;
	private AOTDGuiRecipe bottomRightRecipe;

	private AOTDGuiButton bookmarkButton;

	private AOTDGuiImage journalBackground;

	private AOTDGuiPanel journal;

	private int pageNumber = 0;

	private final List<ConvertedRecipe> researchRecipes = new ArrayList<ConvertedRecipe>();

	public BloodStainedJournalPageGUI(final String textNext, final String titleText, Item[] relatedItemRecipes)
	{
		// Setup tile and page text. Then add left and right page text boxes
		super();
		this.setupRecipes(relatedItemRecipes);
		this.COMPLETE_TEXT = textNext.replaceAll(" +", " ");

		int journalWidth = 256;
		int journalHeight = 256;
		// Calculate various variables later used in text box width/height calculation
		int xCornerOfPage = (640 - journalWidth) / 2;
		int yCornerOfPage = (360 - journalHeight) / 2;

		this.journal = new AOTDGuiPanel(xCornerOfPage, yCornerOfPage, journalWidth, journalHeight, false);

		this.journal.add(new AOTDGuiImage(0, 0, journalWidth, journalHeight, "afraidofthedark:textures/gui/blood_stained_journal_page.png"));

		AOTDGuiLabel title = new AOTDGuiLabel(5, 15, ClientData.getTargaMSHandFontSized(50f));
		title.setText(titleText);
		title.setTextColor(new Color(200, 0, 0));
		this.journal.add(title);

		this.leftPageNumber = new AOTDGuiLabel(8, journalHeight - 40, ClientData.getTargaMSHandFontSized(32f));
		this.rightPageNumber = new AOTDGuiLabel(230, journalHeight - 40, ClientData.getTargaMSHandFontSized(32f));
		this.leftPageNumber.setText(Integer.toString(1));
		this.rightPageNumber.setText(Integer.toString(2));
		this.leftPageNumber.setTextColor(new Color(200, 0, 0));
		this.rightPageNumber.setTextColor(new Color(200, 0, 0));
		this.journal.add(this.leftPageNumber);
		this.journal.add(this.rightPageNumber);

		this.leftPage = new AOTDGuiTextBox(5, 45, journalWidth / 2 - 10, journalHeight - 80, ClientData.getTargaMSHandFontSized(32f), 24);
		this.rightPage = new AOTDGuiTextBox(130, 45, journalWidth / 2 - 10, journalHeight - 80, ClientData.getTargaMSHandFontSized(32f), 24);
		this.leftPage.setTextColor(new Color(200, 0, 0));
		this.rightPage.setTextColor(new Color(200, 0, 0));
		this.journal.add(this.leftPage);
		this.journal.add(this.rightPage);

		this.bookmarkButton = new AOTDGuiButton(journalWidth / 2 - 17, journalHeight - 28, 15, 30, null, "afraidofthedark:textures/gui/slot_highlight.png");
		this.bookmarkButton.setVisible(false);
		this.bookmarkButton.setColor(new Color(255, 255, 255, 50));
		this.bookmarkButton.addMouseListener(new AOTDMouseListener()
		{
			@Override
			public void mouseClicked(AOTDMouseEvent event)
			{
				if (event.getSource().isHovered() && event.getClickedButton() == MouseButtonClicked.Left)
				{
					entityPlayer.closeScreen();
					entityPlayer.openGui(AfraidOfTheDark.instance, GuiHandler.BLOOD_STAINED_JOURNAL_ID, entityPlayer.world, entityPlayer.getPosition().getX(), entityPlayer.getPosition().getY(), entityPlayer.getPosition().getZ());
				}
			}

			@Override
			public void mouseEntered(AOTDMouseEvent event)
			{
				event.getSource().setVisible(true);
			}

			@Override
			public void mouseExited(AOTDMouseEvent event)
			{
				event.getSource().setVisible(false);
			}
		});
		this.journal.add(this.bookmarkButton);

		this.topLeftRecipe = new AOTDGuiRecipe(10, 38, 110, 90, null);
		this.journal.add(this.topLeftRecipe);
		this.bottomLeftRecipe = new AOTDGuiRecipe(10, 130, 110, 90, null);
		this.journal.add(this.bottomLeftRecipe);
		this.topRightRecipe = new AOTDGuiRecipe(130, 38, 110, 90, null);
		this.journal.add(this.topRightRecipe);
		this.bottomRightRecipe = new AOTDGuiRecipe(130, 130, 110, 90, null);
		this.journal.add(this.bottomRightRecipe);

		this.getContentPane().add(this.journal);

		this.forwardButton = new AOTDGuiButton(this.getContentPane().getWidth() - 64, this.getContentPane().getHeight() - 64, 64, 64, null, "afraidofthedark:textures/gui/buttons/forward_button.png");
		this.backwardButton = new AOTDGuiButton(0, this.getContentPane().getHeight() - 64, 64, 64, null, "afraidofthedark:textures/gui/buttons/backward_button.png");
		this.forwardButton.addMouseListener(new AOTDMouseListener()
		{
			@Override
			public void mouseClicked(AOTDMouseEvent event)
			{
				if (event.getSource().isHovered() && event.getClickedButton() == MouseButtonClicked.Left)
				{
					BloodStainedJournalPageGUI.this.advancePage();
				}
			}

			@Override
			public void mouseEntered(AOTDMouseEvent event)
			{
				event.getSource().darkenColor(0.1f);
			}

			@Override
			public void mouseExited(AOTDMouseEvent event)
			{
				event.getSource().brightenColor(0.1f);
			}
		});
		this.backwardButton.addMouseListener(new AOTDMouseListener()
		{
			@Override
			public void mouseClicked(AOTDMouseEvent event)
			{
				if (event.getSource().isHovered() && event.getClickedButton() == MouseButtonClicked.Left)
					BloodStainedJournalPageGUI.this.rewindPage();
			}

			@Override
			public void mouseEntered(AOTDMouseEvent event)
			{
				event.getSource().darkenColor(0.1f);
			}

			@Override
			public void mouseExited(AOTDMouseEvent event)
			{
				event.getSource().brightenColor(0.1f);
			}
		});
		this.getContentPane().add(this.forwardButton);
		this.getContentPane().add(this.backwardButton);

		this.updateText();
		this.updatePages();

		this.forwardButton.setVisible(this.hasPageForward());
		this.backwardButton.setVisible(this.hasPageBackward());

		entityPlayer.playSound(ModSounds.pageTurn, 1.0F, 1.0F);
	}

	private void advancePage()
	{
		if (this.hasPageForward())
		{
			pageNumber = pageNumber + 2;
			entityPlayer.playSound(ModSounds.pageTurn, 1.0F, 1.0F);
		}
		this.leftPageNumber.setText(Integer.toString(this.pageNumber + 1));
		this.rightPageNumber.setText(Integer.toString(this.pageNumber + 2));
		this.backwardButton.setVisible(this.hasPageBackward());
		this.forwardButton.setVisible(this.hasPageForward());
		this.updatePages();
	}

	private void rewindPage()
	{
		if (this.hasPageBackward())
		{
			pageNumber = pageNumber - 2;
			entityPlayer.playSound(ModSounds.pageTurn, 1.0F, 1.0F);
		}
		this.leftPageNumber.setText(Integer.toString(this.pageNumber + 1));
		this.rightPageNumber.setText(Integer.toString(this.pageNumber + 2));
		this.backwardButton.setVisible(this.hasPageBackward());
		this.forwardButton.setVisible(this.hasPageForward());
		this.updatePages();
	}

	private boolean hasPageBackward()
	{
		return pageNumber != 0;
	}

	private boolean hasPageForward()
	{
		return !(!Utility.hasIndex(this.textOnEachPage, pageNumber + 2) && !Utility.hasIndex(this.researchRecipes, (pageNumber + 2 - this.textOnEachPage.size()) * 2));
	}

	private void updatePages()
	{
		int adjustedIndexForRecipe = (pageNumber - textOnEachPage.size()) * 2;
		if (Utility.hasIndex(this.textOnEachPage, pageNumber))
		{
			this.leftPage.setText(this.textOnEachPage.get(pageNumber));
			this.topLeftRecipe.setRecipe(null);
			this.bottomLeftRecipe.setRecipe(null);
			adjustedIndexForRecipe = adjustedIndexForRecipe + 2;
		}
		else
		{
			this.leftPage.setText("");
			this.topLeftRecipe.setRecipe(Utility.hasIndex(this.researchRecipes, adjustedIndexForRecipe) ? this.researchRecipes.get(adjustedIndexForRecipe++) : null);
			this.bottomLeftRecipe.setRecipe(Utility.hasIndex(this.researchRecipes, adjustedIndexForRecipe) ? this.researchRecipes.get(adjustedIndexForRecipe++) : null);
		}
		if (Utility.hasIndex(textOnEachPage, pageNumber + 1))
		{
			this.rightPage.setText(this.textOnEachPage.get(pageNumber + 1));
			this.topRightRecipe.setRecipe(null);
			this.bottomRightRecipe.setRecipe(null);
		}
		else
		{
			this.rightPage.setText("");
			this.topRightRecipe.setRecipe(Utility.hasIndex(this.researchRecipes, adjustedIndexForRecipe) ? this.researchRecipes.get(adjustedIndexForRecipe++) : null);
			this.bottomRightRecipe.setRecipe(Utility.hasIndex(this.researchRecipes, adjustedIndexForRecipe) ? this.researchRecipes.get(adjustedIndexForRecipe++) : null);
		}
	}

	private void updateText()
	{
		this.textOnEachPage.clear();
		String textToDistribute = this.COMPLETE_TEXT;
		boolean alternater = true;

		while (!textToDistribute.isEmpty() && !textToDistribute.equals(" "))
		{
			String leftOver = alternater ? leftPage.getOverflowText(textToDistribute) : rightPage.getOverflowText(textToDistribute);
			alternater = !alternater;

			String page = textToDistribute.substring(0, textToDistribute.length() - leftOver.length() - 1);
			textToDistribute = textToDistribute.substring(textToDistribute.length() - leftOver.length() - 1);

			this.textOnEachPage.add(page);
		}
	}

	// If E is typed we close the GUI screen
	@Override
	protected void keyTyped(final char character, final int keyCode) throws IOException
	{
		if ((keyCode == INVENTORY_KEYCODE))
		{
			entityPlayer.openGui(AfraidOfTheDark.instance, GuiHandler.BLOOD_STAINED_JOURNAL_ID, entityPlayer.world, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
		}
		else if ((character == 'a') || (character == 'A') || (keyCode == Keyboard.KEY_LEFT))
		{
			this.rewindPage();
		}
		else if ((character == 'd') || (character == 'D') || (keyCode == Keyboard.KEY_RIGHT))
		{
			this.advancePage();
		}
		super.keyTyped(character, keyCode);
	}

	private void setupRecipes(Item[] relatedItemRecipes)
	{
		for (Item nextItem : relatedItemRecipes)
		{
			researchRecipes.addAll(RecipeUtility.getRecipesForItem(nextItem));
		}
	}

	@Override
	public boolean inventoryToCloseGuiScreen()
	{
		return false;
	}

	@Override
	public boolean drawGradientBackground()
	{
		return true;
	}
}