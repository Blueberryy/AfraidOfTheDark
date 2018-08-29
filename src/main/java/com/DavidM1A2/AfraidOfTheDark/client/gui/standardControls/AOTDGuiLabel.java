package com.DavidM1A2.afraidofthedark.client.gui.standardControls;

import com.DavidM1A2.afraidofthedark.AfraidOfTheDark;
import com.DavidM1A2.afraidofthedark.client.gui.base.AOTDGuiContainer;
import com.DavidM1A2.afraidofthedark.client.gui.base.TextAlignment;
import com.DavidM1A2.afraidofthedark.client.gui.fontLibrary.TrueTypeFont;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.util.Color;

/**
 * Class representing a label to be drawn on the GUI
 */
public class AOTDGuiLabel extends AOTDGuiContainer
{
	// The text to draw
	private String text = StringUtils.EMPTY;
	// The font to draw the text with
	private final TrueTypeFont font;
	// The color to draw the text with
	private Color textColor = new Color(255, 255, 255, 255);
	// Text alignment
	private TextAlignment textAlignment = TextAlignment.ALIGN_LEFT;
	// We draw at a lower scale because otherwise we get blurry text
	private float drawingScale = 0.25f;

	/**
	 * Constructor takes an x and y position as well as a font
	 *
	 * @param x The X coordinate of the label
	 * @param y The Y coordinate of the label
	 * @param font The font to use to draw the label
	 */
	public AOTDGuiLabel(int x, int y, int width, int height, TrueTypeFont font)
	{
		super(x, y, width, height);
		this.font = font;
	}

	/**
	 * Draw function that gets called every frame. Draw the text
	 */
	@Override
	public void draw()
	{
		// If the label is visible, draw it
		if (this.isVisible())
		{
			super.draw();

			// If we don't have a font, we can't draw anything
			if (this.font != null)
			{
				// Test if the text will fit into our label
				float width = (float) (this.font.getWidth(this.text) * this.drawingScale * this.getScaleX());
				float height = (float) (this.font.getHeight() * this.drawingScale * this.getScaleY());
				// If the width or height are invalid show an error
				if (width > this.getWidthScaled())
					AfraidOfTheDark.INSTANCE.getLogger().info("Attempting to set a label's text that isn't wide enough to hold its contents! -- " + this.text);
				if (height > this.getHeightScaled())
					AfraidOfTheDark.INSTANCE.getLogger().info("Attempting to create a label that isn't tall enough to hold its contents! -- " + this.text);

				float xCoord = this.getXScaled().floatValue() + (this.textAlignment == TextAlignment.ALIGN_LEFT ? 0 : this.textAlignment == TextAlignment.ALIGN_CENTER ? this.getWidthScaled() / 2f : this.getWidthScaled());
				float yCoord = this.getYScaled().floatValue();

				// Draw the string at (x, y) with the correct color and scale
				this.font.drawString(
					xCoord - this.font.getFontSize() * 0.15f,
					yCoord,
					this.text,
					this.getScaleX().floatValue() * this.drawingScale,
					this.getScaleY().floatValue() * this.drawingScale,
					textAlignment,
					this.textColor.getRed() / 255f, this.textColor.getGreen() / 255f, this.textColor.getBlue() / 255f, this.textColor.getAlpha() / 255f);
			}
		}
	}

	/**
	 * @param text Sets the text to be drawn
	 */
	public void setText(String text)
	{
		// Set the internal text of the label
		this.text = text;
	}

	/**
	 * @return Gets the font to be used while drawing the text
	 */
	public TrueTypeFont getFont()
	{
		return this.font;
	}

	/**
	 * @param textColor Sets the text's color
	 */
	public void setTextColor(Color textColor)
	{
		this.textColor = textColor;
	}

	/**
	 * @return Gets the text's color
	 */
	public Color getTextColor()
	{
		return this.textColor;
	}

	/**
	 * @param textAlignment Text alignment used by the label
	 */
	public void setTextAlignment(TextAlignment textAlignment)
	{
		this.textAlignment = textAlignment;
	}

	/**
	 * @return Get the text alignment used by the label
	 */
	public TextAlignment getTextAlignment()
	{
		return this.textAlignment;
	}

	/**
	 * @param drawingScale Sets the scale to draw the text at
	 */
	public void setDrawingScale(float drawingScale)
	{
		this.drawingScale = drawingScale;
	}

	/**
	 * @return Gets the text drawing scale
	 */
	public float getDrawingScale()
	{
		return this.drawingScale;
	}
}
