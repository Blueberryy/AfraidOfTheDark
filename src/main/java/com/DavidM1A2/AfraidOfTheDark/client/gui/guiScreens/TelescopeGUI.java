package com.DavidM1A2.afraidofthedark.client.gui.guiScreens;

import com.DavidM1A2.afraidofthedark.AfraidOfTheDark;
import com.DavidM1A2.afraidofthedark.client.gui.base.AOTDGuiClickAndDragable;
import com.DavidM1A2.afraidofthedark.client.gui.eventListeners.AOTDMouseListener;
import com.DavidM1A2.afraidofthedark.client.gui.events.AOTDMouseEvent;
import com.DavidM1A2.afraidofthedark.client.gui.specialControls.AOTDGuiMeteorButton;
import com.DavidM1A2.afraidofthedark.client.gui.standardControls.AOTDGuiImage;
import com.DavidM1A2.afraidofthedark.client.gui.standardControls.AOTDGuiPanel;
import com.DavidM1A2.afraidofthedark.common.capabilities.player.research.IAOTDPlayerResearch;
import com.DavidM1A2.afraidofthedark.common.constants.Constants;
import com.DavidM1A2.afraidofthedark.common.constants.ModCapabilities;
import com.DavidM1A2.afraidofthedark.common.constants.ModRegistries;
import com.DavidM1A2.afraidofthedark.common.packets.otherPackets.UpdateWatchedMeteor;
import com.DavidM1A2.afraidofthedark.common.registry.meteor.MeteorEntry;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Gui screen that represents the telescope GUI
 */
public class TelescopeGUI extends AOTDGuiClickAndDragable
{
	// The panel that will contain all the meteors on it
	private final AOTDGuiPanel telescopeMeteorsBase;
	// The image that represents the 'sky'
	private final AOTDGuiImage telescopeImage;

	/**
	 * Constructor initializes the entire UI
	 */
	public TelescopeGUI()
	{
		// The gui will be 256x256
		final int GUI_SIZE = 256;

		// Calculate the various positions of GUI elements on the screen
		int xPosTelescope = (Constants.GUI_WIDTH - GUI_SIZE) / 2;
		int yPosTelescope = (Constants.GUI_HEIGHT - GUI_SIZE) / 2;

		// Create a panel that will hold all the UI contents
		AOTDGuiPanel telescope = new AOTDGuiPanel(xPosTelescope, yPosTelescope, GUI_SIZE, GUI_SIZE, true);
		// Create a frame that will be the edge of the telescope UI
		AOTDGuiImage telescopeFrame = new AOTDGuiImage(0, 0, GUI_SIZE, GUI_SIZE, "afraidofthedark:textures/gui/telescope_gui.png");
		// Create the panel to hold all the meteors
		telescopeMeteorsBase = new AOTDGuiPanel(0, 0, GUI_SIZE, GUI_SIZE, false);
		// Initialize the background star sky image and center the image
		telescopeImage = new AOTDGuiImage(0, 0, GUI_SIZE, GUI_SIZE, 3840, 2160, "afraidofthedark:textures/gui/telescope_background.png");
		telescopeImage.setU(this.guiOffsetX + (telescopeImage.getMaxTextureWidth() / 2));
		telescopeImage.setV(this.guiOffsetY + (telescopeImage.getMaxTextureHeight() / 2));

		// Create a random number of meteors to generate, let's go with 30-80
		int numberOfMeteors = 30 + entityPlayer.getRNG().nextInt(50);

		// Click listener that gets called when we click a meteor button
		AOTDMouseListener meteorClickListener = new AOTDMouseListener()
		{
			@Override
			public void mouseClicked(AOTDMouseEvent event)
			{
				if (event.getSource().isHovered() && event.getClickedButton() == AOTDMouseEvent.MouseButtonClicked.Left)
				{
					// Tell the server we're watching a new meteor. It will update our capability NBT data for us
					AfraidOfTheDark.INSTANCE.getPacketHandler().sendToServer(new UpdateWatchedMeteor(((AOTDGuiMeteorButton) event.getSource()).getMeteorType()));
					entityPlayer.closeScreen();
				}
			}
		};

		// Grab the player's research
		IAOTDPlayerResearch playerResearch = entityPlayer.getCapability(ModCapabilities.PLAYER_RESEARCH, null);
		// Grab a list of possible meteors
		List<MeteorEntry> possibleMeteors = ModRegistries.METEORS.getValuesCollection().stream().filter(meteorEntry -> playerResearch.isResearched(meteorEntry.getPreRequisite())).collect(Collectors.toList());

		// If we somehow open the GUI without having any known meteors don't show any. This can happen if the telescope is right
		// clicked and the packet to update research from the server hasn't arrived yet
		if (!possibleMeteors.isEmpty())
		{
			// Grab a random object to place meteors
			Random random = entityPlayer.getRNG();
			// Create one button for each meteor
			for (int i = 0; i < numberOfMeteors; i++)
			{
				// Create the meteor button based on if astronomy 2 is researched or not
				AOTDGuiMeteorButton meteorButton = new AOTDGuiMeteorButton(
						random.nextInt(this.telescopeImage.getMaxTextureWidth()) - this.telescopeImage.getMaxTextureWidth() / 2,
						random.nextInt(this.telescopeImage.getMaxTextureHeight()) - this.telescopeImage.getMaxTextureHeight() / 2,
						64,
						64,
						possibleMeteors.get(random.nextInt(possibleMeteors.size()))
				);
				// Add a listener
				meteorButton.addMouseListener(meteorClickListener);
				// Add the button
				this.telescopeMeteorsBase.add(meteorButton);
			}
		}

		// Add all the panels to the content pane
		telescope.add(telescopeImage);
		telescope.add(telescopeMeteorsBase);
		telescope.add(telescopeFrame);
		this.getContentPane().add(telescope);
	}

	/**
	 * Called when we drag the mouse
	 *
	 * @param mouseX The mouse X position
	 * @param mouseY The mouse Y position
	 * @param lastButtonClicked The last button clicked
	 * @param timeBetweenClicks The time between the last click
	 */
	@Override
	protected void mouseClickMove(final int mouseX, final int mouseY, final int lastButtonClicked, final long timeBetweenClicks)
	{
		// Call super first
		super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeBetweenClicks);

		// Move the meteors based on the gui offset
		this.telescopeMeteorsBase.setX(-this.guiOffsetX + telescopeMeteorsBase.getParent().getX());
		this.telescopeMeteorsBase.setY(-this.guiOffsetY + telescopeMeteorsBase.getParent().getY());

		// Update the background image's U/V
		telescopeImage.setU(this.guiOffsetX + (this.telescopeImage.getMaxTextureWidth() / 2));
		telescopeImage.setV(this.guiOffsetY + (this.telescopeImage.getMaxTextureHeight() / 2));
	}

	/**
	 * We can use this to test if the gui has scrolled out of bounds or not
	 */
	@Override
	protected void checkOutOfBounds()
	{
		this.guiOffsetX = MathHelper.clamp(this.guiOffsetX, -this.telescopeImage.getMaxTextureWidth() / 2, this.telescopeImage.getMaxTextureWidth() / 2);
		this.guiOffsetY = MathHelper.clamp(this.guiOffsetY, -this.telescopeImage.getMaxTextureHeight() / 2, this.telescopeImage.getMaxTextureHeight() / 2);
	}

	/**
	 * @return True since the inventory button closes the UI
	 */
	@Override
	public boolean inventoryToCloseGuiScreen()
	{
		return true;
	}

	/**
	 * @return True since the UI has a gradient background
	 */
	@Override
	public boolean drawGradientBackground()
	{
		return true;
	}
}