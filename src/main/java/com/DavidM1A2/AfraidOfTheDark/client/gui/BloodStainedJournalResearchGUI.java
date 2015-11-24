/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.client.gui;

import org.lwjgl.opengl.GL11;

import com.DavidM1A2.AfraidOfTheDark.AfraidOfTheDark;
import com.DavidM1A2.AfraidOfTheDark.client.gui.customControls.GuiClickAndDragable;
import com.DavidM1A2.AfraidOfTheDark.client.gui.customControls.NodeButton;
import com.DavidM1A2.AfraidOfTheDark.client.gui.spriteSheet.SpriteSheetAnimation;
import com.DavidM1A2.AfraidOfTheDark.client.settings.ClientData;
import com.DavidM1A2.AfraidOfTheDark.common.refrence.ResearchTypes;
import com.DavidM1A2.AfraidOfTheDark.common.savedData.AOTDPlayerData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class BloodStainedJournalResearchGUI extends GuiClickAndDragable
{
	private static final int RESEARCH_BASE_ID = 1;
	private static int currentID;
	private static final int DISTANCE_BETWEEN_NODES = 75;

	private final SpriteSheetAnimation verticalArrow = new SpriteSheetAnimation(new ResourceLocation("afraidofthedark:textures/gui/researchVertical.png"), 500, 20, 60, 180, true, true);
	private final SpriteSheetAnimation horizontalArrow = new SpriteSheetAnimation(new ResourceLocation("afraidofthedark:textures/gui/researchHorizontal.png"), 500, 20, 180, 60, true, false);

	private static final ResourceLocation researchBackdrop = new ResourceLocation("afraidofthedark:textures/gui/bloodStainedJournalResearchBackdrop.png");
	private static final ResourceLocation researchBackground = new ResourceLocation("afraidofthedark:textures/gui/bloodStainedJournalResearchBackground.png");

	// GUI height and width
	private static int baseWidth = 512;
	private static int baseHeight = 512;

	// Current GUI x/y Scrool positions, background positions, and research
	// positions
	private static int xPosScroll;
	private static int yPosScroll;

	private static int xPosBackground;
	private static int yPosBackground;

	private static int xPosBaseResearch;
	private static int yPosBaseResearch;

	private static final int MAX_HEIGHT = 0;
	private static final int MAX_WIDTH = 200;
	private static final int MAX_NEGATIVE_HEIGHT = -380;
	private static final int MAX_NEGATIVE_WIDTH = -200;

	// Background will always be 256x256
	private static final int BACKGROUND_HEIGHT = 256;
	private static final int BACKGROUND_WIDTH = 256;

	@Override
	public void initGui()
	{
		// Calculate the various positions of GUI elements on the screen
		currentID = RESEARCH_BASE_ID;
		baseHeight = (this.height - 256) / 2;
		baseWidth = (this.width - 256) / 2;
		xPosScroll = baseWidth;
		yPosScroll = baseHeight;
		xPosBackground = baseWidth + 20;
		yPosBackground = baseHeight + 20;
		xPosBaseResearch = xPosBackground + 92;
		yPosBaseResearch = (yPosBackground + (BACKGROUND_WIDTH / 2)) + 35;

		this.guiOffsetX = ClientData.currentBloodStainedJournalX;
		this.guiOffsetY = ClientData.currentBloodStainedJournalY;

		this.setupButtons();

		for (final Object o : this.buttonList)
		{
			((NodeButton) o).setPosition(this.guiOffsetX, this.guiOffsetY);
		}
	}

	// To draw the screen we first draw the default GUI background, then the
	// background images. Then we add buttons and a frame.
	@Override
	public void drawScreen(final int i, final int j, final float f)
	{
		this.mc.renderEngine.bindTexture(researchBackdrop);
		Gui.drawScaledCustomSizeModalRect(xPosScroll, yPosScroll, (this.guiOffsetX * 2) + 384, (this.guiOffsetY * 2) + 768, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, 1024, 1024);

		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		final int disWidth = Minecraft.getMinecraft().displayWidth;
		final int disHeight = Minecraft.getMinecraft().displayHeight;
		final int widthScale = Math.round(disWidth / (float) this.width);
		final int heightScale = Math.round(disHeight / (float) this.height);
		GL11.glScissor(disWidth - ((xPosScroll + BACKGROUND_WIDTH) * widthScale), disHeight - ((yPosScroll + BACKGROUND_HEIGHT) * widthScale), BACKGROUND_WIDTH * heightScale, BACKGROUND_HEIGHT * heightScale);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawLines();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		super.drawScreen(i, j, f);

		GL11.glDisable(GL11.GL_SCISSOR_TEST);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(researchBackground);
		this.drawTexturedModalRect(xPosScroll, yPosScroll, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);

		for (Object nodeButton : this.buttonList)
		{
			if (nodeButton instanceof NodeButton)
			{
				NodeButton newNodeButton = (NodeButton) nodeButton;

				if (newNodeButton.isMouseOver() && AOTDPlayerData.get(Minecraft.getMinecraft().thePlayer).isResearched(newNodeButton.getMyType()))
				{
					this.drawString(Minecraft.getMinecraft().fontRendererObj, newNodeButton.getMyType().formattedString(), newNodeButton.xPosition + newNodeButton.height, newNodeButton.yPosition, 0xFF3399);
					this.drawString(Minecraft.getMinecraft().fontRendererObj, EnumChatFormatting.ITALIC + newNodeButton.getMyType().getTooltip(), newNodeButton.xPosition + newNodeButton.height + 2, newNodeButton.yPosition + 10, 0xE62E8A);
				}
				else if (newNodeButton.isMouseOver() && AOTDPlayerData.get(Minecraft.getMinecraft().thePlayer).canResearch(newNodeButton.getMyType()))
				{
					this.drawString(Minecraft.getMinecraft().fontRendererObj, "?", newNodeButton.xPosition + newNodeButton.height, newNodeButton.yPosition, 0xFF3399);
					this.drawString(Minecraft.getMinecraft().fontRendererObj, EnumChatFormatting.ITALIC + "Unknown Research", newNodeButton.xPosition + newNodeButton.height + 2, newNodeButton.yPosition + 10, 0xE62E8A);
				}
			}
		}
	}

	// When the mouse is dragged, update the GUI accordingly
	@Override
	protected void mouseClickMove(final int mouseX, final int mouseY, final int lastButtonClicked, final long timeBetweenClicks)
	{
		super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeBetweenClicks);
		for (final Object o : this.buttonList)
		{
			((NodeButton) o).setPosition(this.guiOffsetX, this.guiOffsetY);
		}
	}

	// When a button is pressed, open the respective research
	@Override
	protected void actionPerformed(final GuiButton button)
	{
		final EntityPlayer entityPlayer = Minecraft.getMinecraft().thePlayer;
		for (final Object o : this.buttonList)
		{
			final NodeButton current = (NodeButton) o;
			if (current.id == button.id)
			{
				ClientData.currentlySelected = current.getMyType();
				if (AOTDPlayerData.get(entityPlayer).isResearched(current.getMyType()))
				{
					entityPlayer.openGui(AfraidOfTheDark.instance, GuiHandler.BLOOD_STAINED_JOURNAL_PAGE_ID, entityPlayer.worldObj, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
					break;
				}
				else if (AOTDPlayerData.get(entityPlayer).isResearched(current.getMyType().getPrevious()))
				{
					entityPlayer.openGui(AfraidOfTheDark.instance, GuiHandler.BLOOD_STAINED_JOURNAL_PAGE_PRE_ID, entityPlayer.worldObj, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
					break;
				}
			}
		}
	}

	// Draw an arrow for the gui
	private void drawLines()
	{
		verticalArrow.update();
		horizontalArrow.update();
		for (Object object : this.buttonList)
		{
			if (object instanceof NodeButton)
			{
				NodeButton nodeButton = (NodeButton) object;

				if (nodeButton.getMyType().getPrevious() != null)
				{
					if (AOTDPlayerData.get(Minecraft.getMinecraft().thePlayer).isResearched(nodeButton.getMyType()) || AOTDPlayerData.get(Minecraft.getMinecraft().thePlayer).canResearch(nodeButton.getMyType()))
					{
						ResearchTypes previous = nodeButton.getMyType().getPrevious();
						ResearchTypes current = nodeButton.getMyType();
						if (current.getPositionX() < previous.getPositionX())
						{
							horizontalArrow.draw(nodeButton.xPosition + 26, nodeButton.yPosition + 9, 54, 14);
						}
						else if (current.getPositionX() > previous.getPositionX())
						{
							horizontalArrow.draw(nodeButton.xPosition - 50, nodeButton.yPosition + 9, 54, 14);
						}
						else if (current.getPositionY() > previous.getPositionY())
						{
							verticalArrow.draw(nodeButton.xPosition + 9, nodeButton.yPosition + 30, 14, 46);
						}
						else if (current.getPositionY() < previous.getPositionY())
						{
							verticalArrow.draw(nodeButton.xPosition + 9, nodeButton.yPosition - 46, 14, 46);
						}
					}
				}
			}
		}
	}

	private void setupButtons()
	{
		this.buttonList.clear();
		for (ResearchTypes researchType : ResearchTypes.values())
		{
			this.buttonList.add(new NodeButton(currentID++, xPosBaseResearch + DISTANCE_BETWEEN_NODES * researchType.getPositionX(), yPosBaseResearch - DISTANCE_BETWEEN_NODES * researchType.getPositionY(), researchType));
		}
	}

	@Override
	protected void checkOutOfBounds()
	{
		if (this.guiOffsetX > MAX_WIDTH)
		{
			this.guiOffsetX = MAX_WIDTH;
		}
		if (this.guiOffsetX < MAX_NEGATIVE_WIDTH)
		{
			this.guiOffsetX = MAX_NEGATIVE_WIDTH;
		}
		if (this.guiOffsetY > MAX_HEIGHT)
		{
			this.guiOffsetY = MAX_HEIGHT;
		}
		if (this.guiOffsetY < MAX_NEGATIVE_HEIGHT)
		{
			this.guiOffsetY = MAX_NEGATIVE_HEIGHT;
		}
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat events
	 */
	@Override
	public void onGuiClosed()
	{
		ClientData.currentBloodStainedJournalX = guiOffsetX;
		ClientData.currentBloodStainedJournalY = guiOffsetY;
		super.onGuiClosed();
	}
}
