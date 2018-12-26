/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.client.gui.baseControls;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import com.DavidM1A2.AfraidOfTheDark.client.gui.AOTDGuiUtility;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

public abstract class AOTDGuiComponent
{
	private double scaleX = 1.0;
	private double scaleY = 1.0;
	private boolean isHovered = false;
	private boolean isVisible = true;
	private Rectangle boundingBox = new Rectangle();
	private Rectangle scaledBoundingBox = new Rectangle();
	private float[] color = new float[]
	{ 1.0f, 1.0f, 1.0f, 1.0f };
	protected final EntityPlayerSP entityPlayer = Minecraft.getMinecraft().player;
	protected final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
	private String[] hoverText = new String[0];

	public AOTDGuiComponent(int x, int y, int width, int height)
	{
		this.boundingBox = new Rectangle(x, y, width, height);
	}

	public void draw()
	{
		GL11.glColor4d(this.getColor()[0], this.getColor()[1], this.getColor()[2], this.getColor()[3]);
		// this.drawBoundingBox();
	}

	public void drawOverlay()
	{
		if (isVisible && isHovered)
			if (this.hoverText.length != 0)
			{
				int maxLength = 0;
				for (String hoverString : this.hoverText)
					if (fontRenderer.getStringWidth(hoverString) > maxLength)
						maxLength = fontRenderer.getStringWidth(hoverString);
				Gui.drawRect(AOTDGuiUtility.getMouseX() + 2, AOTDGuiUtility.getMouseY() - 2, AOTDGuiUtility.getMouseX() + maxLength + 7, AOTDGuiUtility.getMouseY() + fontRenderer.FONT_HEIGHT * this.hoverText.length, 0xB3200017);
				for (int i = 0; i < this.hoverText.length; i++)
				{
					String hoverString = this.hoverText[i];
					if (hoverString != "")
						fontRenderer.drawStringWithShadow(hoverString, AOTDGuiUtility.getMouseX() + 5, AOTDGuiUtility.getMouseY() + i * fontRenderer.FONT_HEIGHT, 0xFFFFFFFF);
				}
			}
	}

	public void drawBoundingBox()
	{
		Gui.drawRect(this.getXScaled(), this.getYScaled(), this.getXScaled() + this.getWidthScaled(), this.getYScaled() + 1, 0xFFFFFFFF);
		Gui.drawRect(this.getXScaled(), this.getYScaled(), this.getXScaled() + 1, this.getYScaled() + this.getHeightScaled(), 0xFFFFFFFF);
		Gui.drawRect(this.getXScaled() + this.getWidthScaled() - 1, this.getYScaled(), this.getXScaled() + this.getWidthScaled(), this.getYScaled() + this.getHeightScaled(), 0xFFFFFFFF);
		Gui.drawRect(this.getXScaled(), this.getYScaled() + this.getHeightScaled() - 1, this.getXScaled() + this.getWidthScaled(), this.getYScaled() + this.getHeightScaled(), 0xFFFFFFFF);
		GlStateManager.enableBlend();
	}

	public boolean intersects(AOTDGuiComponent other)
	{
		if (other == null)
			return false;
		return this.scaledBoundingBox.intersects(other.scaledBoundingBox);
	}

	public boolean intersects(Point other)
	{
		if (other == null)
			return false;
		return this.scaledBoundingBox.contains(other);
	}

	public boolean intersects(Rectangle other)
	{
		if (other == null)
			return false;
		return this.scaledBoundingBox.intersects(other);
	}

	public void setScaleXAndY(double scale)
	{
		this.setScaleX(scale);
		this.setScaleY(scale);
	}

	public void setScaleX(double scaleX)
	{
		this.scaleX = scaleX;
		this.updateScaledBounds();
	}

	public void setScaleY(double scaleY)
	{
		this.scaleY = scaleY;
		this.updateScaledBounds();
	}

	public void updateScaledBounds()
	{
		int xNew = (int) Math.round(this.scaleX * this.boundingBox.x);
		int yNew = (int) Math.round(this.scaleY * this.boundingBox.y);
		int widthNew = (int) Math.round(this.scaleX * this.boundingBox.width);
		int heightNew = (int) Math.round(this.scaleY * this.boundingBox.height);
		this.scaledBoundingBox.setBounds(xNew, yNew, widthNew, heightNew);
	}

	public double getScaleX()
	{
		return this.scaleX;
	}

	public double getScaleY()
	{
		return this.scaleY;
	}

	public void setX(int x)
	{
		this.boundingBox.x = x;
		this.updateScaledBounds();
	}

	public int getXScaled()
	{
		return this.scaledBoundingBox.x;
	}

	public int getX()
	{
		return this.boundingBox.x;
	}

	public void setY(int y)
	{
		this.boundingBox.y = y;
		this.updateScaledBounds();
	}

	public int getYScaled()
	{
		return this.scaledBoundingBox.y;
	}

	public int getY()
	{
		return this.boundingBox.y;
	}

	public void setWidth(int width)
	{
		this.boundingBox.width = width;
		this.updateScaledBounds();
	}

	public int getWidthScaled()
	{
		return this.scaledBoundingBox.width;
	}

	public int getWidth()
	{
		return this.boundingBox.width;
	}

	public void setHeight(int height)
	{
		this.boundingBox.height = height;
		this.updateScaledBounds();
	}

	public int getHeightScaled()
	{
		return this.scaledBoundingBox.height;
	}

	public int getHeight()
	{
		return this.boundingBox.height;
	}

	public boolean isVisible()
	{
		return isVisible;
	}

	public void setVisible(boolean isVisible)
	{
		this.isVisible = isVisible;
	}

	public boolean isHovered()
	{
		return isHovered;
	}

	public void setHovered(boolean isHovered)
	{
		this.isHovered = isHovered;
	}

	public void setColor(Color color)
	{
		this.setColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
	}

	public void setColor(float r, float g, float b, float a)
	{
		this.color = new float[]
		{ r, g, b, a };
	}

	public float[] getColor()
	{
		return this.color;
	}

	public void brightenColor(float amount)
	{
		this.color[0] = MathHelper.clamp(this.color[0] + amount, 0, 1.0f);
		this.color[1] = MathHelper.clamp(this.color[1] + amount, 0, 1.0f);
		this.color[2] = MathHelper.clamp(this.color[2] + amount, 0, 1.0f);
	}

	public void darkenColor(float amount)
	{
		this.color[0] = MathHelper.clamp(this.color[0] - amount, 0, 1.0f);
		this.color[1] = MathHelper.clamp(this.color[1] - amount, 0, 1.0f);
		this.color[2] = MathHelper.clamp(this.color[2] - amount, 0, 1.0f);
	}

	public String getHoverText()
	{
		return StringUtils.join(this.hoverText, "\n");
	}

	public void setHoverText(String hoverText)
	{
		this.hoverText = StringUtils.split(hoverText, "\n");
	}

	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + " located at " + this.boundingBox + " with a scaled resolution of " + this.scaledBoundingBox;
	}
}