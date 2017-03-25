/*
 * Author: David Slovikosky
 * Mod: Afraid of the Dark
 * Ideas and Textures: Michael Albertson
 */
package com.DavidM1A2.AfraidOfTheDark.common.block.tileEntity;

import java.util.ArrayList;
import java.util.List;

import com.DavidM1A2.AfraidOfTheDark.AfraidOfTheDark;
import com.DavidM1A2.AfraidOfTheDark.common.block.core.AOTDTickingTileEntity;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModBlocks;
import com.DavidM1A2.AfraidOfTheDark.common.initializeMod.ModCapabilities;
import com.DavidM1A2.AfraidOfTheDark.common.packets.SyncVoidChest;
import com.DavidM1A2.AfraidOfTheDark.common.reference.AOTDDimensions;
import com.DavidM1A2.AfraidOfTheDark.common.savedData.playerData.AOTDPlayerData;
import com.DavidM1A2.AfraidOfTheDark.common.utility.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityVoidChest extends AOTDTickingTileEntity
{
	/** The current angle of the lid (between 0 and 1) */
	public float lidAngle;
	/** The angle of the lid last tick */
	public float prevLidAngle;

	private boolean shouldBeOpen = false;

	private int cachedChestType;

	private String owner = "";
	private List<String> friends = new ArrayList<String>();

	private int locationToGoTo = -1;

	private EntityPlayer entityPlayerToSend = null;
	private int coordinateToSendTo = -1;

	private long lastInteraction = -1;

	public TileEntityVoidChest()
	{
		super(ModBlocks.voidChest);
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		this.owner = compound.getString("owner");
		this.locationToGoTo = compound.getInteger("location");

		NBTTagList friends = compound.getTagList("friends", net.minecraftforge.common.util.Constants.NBT.TAG_STRING);

		for (int i = 0; i < friends.tagCount(); i++)
		{
			NBTBase friend = friends.get(i);
			if (friend instanceof NBTTagString)
			{
				this.friends.add(((NBTTagString) friend).getString());
			}
		}

		super.readFromNBT(compound);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setString("owner", this.owner);
		compound.setInteger("location", this.locationToGoTo);

		NBTTagList friends = new NBTTagList();

		for (int i = 0; i < this.friends.size(); i++)
		{
			String string = this.friends.get(i);
			if (string != null)
			{
				NBTTagString friend = new NBTTagString(string);
				friends.appendTag(friend);
			}
		}
		compound.setTag("friends", friends);

		super.writeToNBT(compound);
		return compound;
	}

	@Override
	public void update()
	{
		super.update();
		int i = this.pos.getX();
		int j = this.pos.getY();
		int k = this.pos.getZ();
		float f;

		if (this.ticksExisted % 20 == 0)
			if ((System.currentTimeMillis() - this.lastInteraction) > 3000)
				this.shouldBeOpen = false;

		this.prevLidAngle = this.lidAngle;
		f = 0.1F;
		double d2;

		// Opening chest
		if (shouldBeOpen && this.lidAngle == 0.0F)
		{
			double d1 = i + 0.5D;
			d2 = k + 0.5D;

			this.world.playSound(d1 + 0.5D, j, d2, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);
		}

		if (shouldBeOpen)
		{
			double xVelocity = this.pos.getX() + 0.5 - entityPlayerToSend.posX;
			double yVelocity = this.pos.getY() + 0.5 - entityPlayerToSend.posY;
			double zVelocity = this.pos.getZ() + 0.5 - entityPlayerToSend.posZ;
			xVelocity = MathHelper.clamp(xVelocity, -0.05, 0.05);
			yVelocity = MathHelper.clamp(yVelocity, -0.05, 0.05);
			zVelocity = MathHelper.clamp(zVelocity, -0.05, 0.05);
			entityPlayerToSend.addVelocity(xVelocity, yVelocity, zVelocity);
		}

		// Closing chest
		if (!shouldBeOpen && this.lidAngle > 0.0F || shouldBeOpen && this.lidAngle < 1.0F)
		{
			float f1 = this.lidAngle;

			if (shouldBeOpen)
			{
				this.lidAngle += f;
			}
			else
			{
				this.lidAngle -= f;
			}

			if (this.lidAngle > 1.0F)
			{
				this.lidAngle = 1.0F;
			}

			float f2 = 0.5F;

			if (this.lidAngle < f2 && f1 >= f2)
			{
				d2 = i + 0.5D;
				double d0 = k + 0.5D;

				this.world.playSound(d2, j + 0.5D, d0, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F, false);

				int currentDim = this.world.provider.getDimension();
				if (currentDim != AOTDDimensions.VoidChest.getWorldID() && currentDim != AOTDDimensions.Nightmare.getWorldID() && currentDim != 1)
				{
					for (EntityPlayerMP entityPlayerMP : this.world.getEntitiesWithinAABB(EntityPlayerMP.class, new AxisAlignedBB(this.pos, this.pos.add(.625D, .625D, .625D)).expand(2.0D, 2.0D, 2.0D)))
						if (entityPlayerMP == entityPlayerToSend)
							AOTDDimensions.VoidChest.toDimension(entityPlayerMP);
				}
				else
				{
					if (!world.isRemote)
						entityPlayerToSend.sendMessage(new TextComponentString("The void chest refuses to work in this dimension."));
				}
			}

			if (this.lidAngle < 0.0F)
			{
				this.lidAngle = 0.0F;
			}
		}
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public void interact(EntityPlayer entityPlayer)
	{
		if (!entityPlayer.world.isRemote)
		{
			if (this.owner.equals(""))
			{
				this.owner = entityPlayer.getDisplayName().getUnformattedText();
				this.locationToGoTo = this.validatePlayerLocationVoidChest(entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null).getPlayerLocationVoidChest(), entityPlayer);
				entityPlayer.sendMessage(new TextComponentString("The owner of this chest has been set to " + entityPlayer.getDisplayName().getUnformattedText() + "."));
			}
			else if (entityPlayer.getDisplayName().getUnformattedText().equals(owner))
			{
				if (entityPlayer.getHeldItemMainhand() != null)
				{
					ItemStack itemStack = entityPlayer.getHeldItemMainhand();
					if (itemStack.getItem() instanceof ItemNameTag)
					{
						if (!friends.contains(itemStack.getDisplayName()))
						{
							friends.add(itemStack.getDisplayName());
							if (!world.isRemote)
							{
								entityPlayer.sendMessage(new TextComponentString("Player " + itemStack.getDisplayName() + " was added to this chest's friend list."));
							}
						}
						else
						{
							friends.remove(itemStack.getDisplayName());
							if (!world.isRemote)
							{
								entityPlayer.sendMessage(new TextComponentString("Player " + itemStack.getDisplayName() + " was removed from this chest's friend list."));
							}
						}
						return;
					}
				}
				this.openChest(entityPlayer);
				AfraidOfTheDark.instance.getPacketHandler().sendToAllAround(new SyncVoidChest(this.pos.getX(), this.pos.getY(), this.pos.getZ(), entityPlayer), new TargetPoint(this.world.provider.getDimension(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), 30));
			}
			else if (friends.contains(entityPlayer.getDisplayName().getUnformattedText()))
			{
				if (entityPlayer.getHeldItemMainhand() != null)
				{
					ItemStack itemStack = entityPlayer.getHeldItemMainhand();
					if (itemStack.getItem() instanceof ItemNameTag)
					{
						entityPlayer.sendMessage(new TextComponentString("I can't edit access to this chest"));
						return;
					}
				}
				this.openChest(entityPlayer);
				AfraidOfTheDark.instance.getPacketHandler().sendToAllAround(new SyncVoidChest(this.pos.getX(), this.pos.getY(), this.pos.getZ(), entityPlayer), new TargetPoint(this.world.provider.getDimension(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), 30));
			}
			else
			{
				entityPlayer.sendMessage(new TextComponentString("I don't have access to this chest."));
			}
		}
	}

	public void openChest(EntityPlayer entityPlayer)
	{
		this.lastInteraction = System.currentTimeMillis();
		this.shouldBeOpen = true;
		this.entityPlayerToSend = entityPlayer;
	}

	private int validatePlayerLocationVoidChest(int locationX, EntityPlayer entityPlayer)
	{
		if (locationX == 0)
		{
			if (!entityPlayer.world.isRemote)
			{
				entityPlayer.world.getMinecraftServer().getCommandManager().executeCommand(entityPlayer.world.getMinecraftServer(), "/save-all");
			}

			int furthestOutPlayer = 0;
			for (NBTTagCompound entityPlayerData : NBTHelper.getOfflinePlayerNBTs())
			{
				furthestOutPlayer = Math.max(furthestOutPlayer, AOTDPlayerData.getPlayerLocationVoidChestOffline(entityPlayerData));
			}
			entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null).setPlayerLocationVoidChest(furthestOutPlayer + 1);
		}
		return entityPlayer.getCapability(ModCapabilities.PLAYER_DATA, null).getPlayerLocationVoidChest();
	}
}
