package com.DavidM1A2.afraidofthedark.common.research.base;

import com.DavidM1A2.afraidofthedark.AfraidOfTheDark;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Base class for all researches that the player can unlock
 */
public abstract class Research extends IForgeRegistryEntry.Impl<Research>
{
	// Gson serializer to convert from JSON to java types
	private static final Gson DESERIALIZER = new GsonBuilder().disableHtmlEscaping().create();

	// The research that this research requires before it can be researched
	private Research preRequisite;

	// The tooltip that is displayed when the research is hovered
	private String tooltip;
	// The pre-researched text that is displayed on the page when the research is not researched yet
	private String preResearchedText;
	// The researched text that is displayed on the page when the research is researched
	private String researchedText;

	// The X position on the research screen that this research will be placed at
	private int xPosition;
	// The Y position on the research screen that this research will be placed at
	private int yPosition;

	// A list of recipes that will be shown if the research is researched
	private List<Item> researchedRecipes = new LinkedList<>();
	// A list of recipes that will be shown if the previous research is researched
	private List<Item> preResearchedRecipes = new LinkedList<>();

	// The icon to show for this research
	private ResourceLocation icon;

	/**
	 * Constructor takes the resource location of the JSON file which contains the research data in it as the first parameter and
	 * the pre-requisite for this research as the second argument
	 *
	 * @param data The resource location with the JSON document for this research
	 * @param preRequisite The prerequisite research to this one
	 */
	public Research(ResourceLocation data, Research preRequisite)
	{
		// Initialize the pre-req field
		this.preRequisite = preRequisite;
		// Open an input stream to our data resource JSON file
		try (InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(data).getInputStream();
			 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
		{
			// Read the file as JSON
			JsonObject jsonObject = JsonUtils.fromJson(DESERIALIZER, reader, JsonObject.class);
			if (jsonObject != null)
			{
				// Parse all the fields of the JSON object using the JSONUtils class
				this.xPosition = JsonUtils.getInt(jsonObject, "x");
				this.yPosition = JsonUtils.getInt(jsonObject, "y");
				this.tooltip = JsonUtils.getString(jsonObject, "tooltip");
				for (JsonElement recipe : JsonUtils.getJsonArray(jsonObject, "recipes"))
					this.researchedRecipes.add(JsonUtils.getItem(recipe, ""));
				for (JsonElement preRecipe : JsonUtils.getJsonArray(jsonObject, "preRecipes"))
					this.preResearchedRecipes.add(JsonUtils.getItem(preRecipe, ""));
				this.icon = new ResourceLocation(JsonUtils.getString(jsonObject, "icon"));
				this.preResearchedText = JsonUtils.getString(jsonObject, "pre");
				this.researchedText = JsonUtils.getString(jsonObject, "researched");
			}
		}
		// This shouldn't happen, but if it does print out an error
		catch (IOException e)
		{
			AfraidOfTheDark.INSTANCE.getLogger().error("Could not load the research defined by '" + data.toString() + "'");
		}
	}

	///
	/// Getters for all fields
	///

	public Research getPreRequisite()
	{
		return preRequisite;
	}

	public String getTooltip()
	{
		return tooltip;
	}

	public String getPreResearchedText()
	{
		return preResearchedText;
	}

	public String getResearchedText()
	{
		return researchedText;
	}

	public int getXPosition()
	{
		return xPosition;
	}

	public int getZPosition()
	{
		return yPosition;
	}

	public List<Item> getResearchedRecipes()
	{
		return researchedRecipes;
	}

	public List<Item> getPreResearchedRecipes()
	{
		return preResearchedRecipes;
	}

	public ResourceLocation getIcon()
	{
		return icon;
	}
}