package com.DavidM1A2.AfraidOfTheDark.client.entities.Werewolf;

import java.util.HashMap;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

import com.DavidM1A2.AfraidOfTheDark.client.MCAClientLibrary.MCAModelRenderer;
import com.DavidM1A2.AfraidOfTheDark.common.MCACommonLibrary.MCAVersionChecker;
import com.DavidM1A2.AfraidOfTheDark.common.MCACommonLibrary.animation.AnimationHandler;
import com.DavidM1A2.AfraidOfTheDark.common.MCACommonLibrary.math.Matrix4f;
import com.DavidM1A2.AfraidOfTheDark.common.MCACommonLibrary.math.Quaternion;
import com.DavidM1A2.AfraidOfTheDark.common.entities.POOPER123.EntityPOOPER123;

public class ModelWerewolf extends ModelBase
{
	public final int MCA_MIN_REQUESTED_VERSION = 5;
	public HashMap<String, MCAModelRenderer> parts = new HashMap<String, MCAModelRenderer>();

	MCAModelRenderer bodyUpper;
	MCAModelRenderer bodyLower;
	MCAModelRenderer head;
	MCAModelRenderer rightFrontLeg;
	MCAModelRenderer leftFrontLeg;
	MCAModelRenderer tail;
	MCAModelRenderer leftBackLeg;
	MCAModelRenderer rightBackLeg;
	MCAModelRenderer snoutUpper;
	MCAModelRenderer snoutLower;
	MCAModelRenderer rightEar;
	MCAModelRenderer leftEar;
	MCAModelRenderer rightFrontFoot;
	MCAModelRenderer lefttFrontFoot;
	MCAModelRenderer leftBackLowerLeg;
	MCAModelRenderer rightBackLowerLeg;
	MCAModelRenderer leftBackFoot;
	MCAModelRenderer rightBackFoot;

	public ModelWerewolf()
	{
		MCAVersionChecker.checkForLibraryVersion(getClass(), MCA_MIN_REQUESTED_VERSION);

		textureWidth = 128;
		textureHeight = 128;

		bodyUpper = new MCAModelRenderer(this, "BodyUpper", 47, 1);
		bodyUpper.mirror = false;
		bodyUpper.addBox(-5.0F, -5.0F, 0.0F, 10, 10, 11);
		bodyUpper.setInitialRotationPoint(0.0F, 0.0F, 0.0F);
		bodyUpper.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(0.0F, 0.0F, 0.0F, 1.0F)).transpose());
		bodyUpper.setTextureSize(128, 128);
		parts.put(bodyUpper.boxName, bodyUpper);

		bodyLower = new MCAModelRenderer(this, "BodyLower", 0, 0);
		bodyLower.mirror = false;
		bodyLower.addBox(-4.0F, -4.0F, -14.0F, 8, 8, 14);
		bodyLower.setInitialRotationPoint(0.0F, 0.0F, 0.0F);
		bodyLower.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(0.0F, 0.0F, 0.0F, 1.0F)).transpose());
		bodyLower.setTextureSize(128, 128);
		parts.put(bodyLower.boxName, bodyLower);
		bodyUpper.addChild(bodyLower);

		head = new MCAModelRenderer(this, "Head", 0, 51);
		head.mirror = false;
		head.addBox(-3.0F, -4.0F, 0.0F, 6, 7, 8);
		head.setInitialRotationPoint(0.0F, 3.0F, 10.0F);
		head.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(0.0784591F, 0.0F, 0.0F, 0.9969173F)).transpose());
		head.setTextureSize(128, 128);
		parts.put(head.boxName, head);
		bodyUpper.addChild(head);

		rightFrontLeg = new MCAModelRenderer(this, "RightFrontLeg", 0, 24);
		rightFrontLeg.mirror = false;
		rightFrontLeg.addBox(-1.5F, -7.0F, -1.5F, 3, 7, 3);
		rightFrontLeg.setInitialRotationPoint(-4.5F, -4.0F, 9.0F);
		rightFrontLeg.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(0.17364818F, 0.0F, 0.0F, 0.9848077F)).transpose());
		rightFrontLeg.setTextureSize(128, 128);
		parts.put(rightFrontLeg.boxName, rightFrontLeg);
		bodyUpper.addChild(rightFrontLeg);

		leftFrontLeg = new MCAModelRenderer(this, "LeftFrontLeg", 0, 24);
		leftFrontLeg.mirror = false;
		leftFrontLeg.addBox(-1.5F, -7.0F, -1.5F, 3, 7, 3);
		leftFrontLeg.setInitialRotationPoint(4.5F, -4.0F, 9.0F);
		leftFrontLeg.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(0.17364818F, 0.0F, 0.0F, 0.9848077F)).transpose());
		leftFrontLeg.setTextureSize(128, 128);
		parts.put(leftFrontLeg.boxName, leftFrontLeg);
		bodyUpper.addChild(leftFrontLeg);

		tail = new MCAModelRenderer(this, "Tail", 58, 33);
		tail.mirror = false;
		tail.addBox(-1.0F, -1.0F, -14.0F, 3, 3, 15);
		tail.setInitialRotationPoint(0.0F, 2.0F, -14.0F);
		tail.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(0.08715574F, 0.0F, 0.0F, 0.9961947F)).transpose());
		tail.setTextureSize(128, 128);
		parts.put(tail.boxName, tail);
		bodyLower.addChild(tail);

		leftBackLeg = new MCAModelRenderer(this, "LeftBackLeg", 0, 36);
		leftBackLeg.mirror = false;
		leftBackLeg.addBox(-2.0F, -5.0F, -2.0F, 4, 6, 4);
		leftBackLeg.setInitialRotationPoint(3.0F, -3.5F, -11.0F);
		leftBackLeg.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(-0.25881904F, 0.0F, 0.0F, 0.9659258F)).transpose());
		leftBackLeg.setTextureSize(128, 128);
		parts.put(leftBackLeg.boxName, leftBackLeg);
		bodyLower.addChild(leftBackLeg);

		rightBackLeg = new MCAModelRenderer(this, "RightBackLeg", 0, 36);
		rightBackLeg.mirror = false;
		rightBackLeg.addBox(-2.0F, -5.0F, -2.0F, 4, 6, 4);
		rightBackLeg.setInitialRotationPoint(-3.0F, -3.5F, -11.0F);
		rightBackLeg.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(-0.25881904F, 0.0F, 0.0F, 0.9659258F)).transpose());
		rightBackLeg.setTextureSize(128, 128);
		parts.put(rightBackLeg.boxName, rightBackLeg);
		bodyLower.addChild(rightBackLeg);

		snoutUpper = new MCAModelRenderer(this, "SnoutUpper", 34, 58);
		snoutUpper.mirror = false;
		snoutUpper.addBox(-2.0F, 0.0F, -1.0F, 4, 2, 6);
		snoutUpper.setInitialRotationPoint(0.0F, -1.0F, 8.0F);
		snoutUpper.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(0.0F, 0.0F, 0.0F, 1.0F)).transpose());
		snoutUpper.setTextureSize(128, 128);
		parts.put(snoutUpper.boxName, snoutUpper);
		head.addChild(snoutUpper);

		snoutLower = new MCAModelRenderer(this, "SnoutLower", 58, 59);
		snoutLower.mirror = false;
		snoutLower.addBox(-2.0F, -2.0F, -1.0F, 4, 2, 5);
		snoutLower.setInitialRotationPoint(0.0F, -1.0F, 8.0F);
		snoutLower.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(0.17364818F, 0.0F, 0.0F, 0.9848077F)).transpose());
		snoutLower.setTextureSize(128, 128);
		parts.put(snoutLower.boxName, snoutLower);
		head.addChild(snoutLower);

		rightEar = new MCAModelRenderer(this, "RightEar", 0, 0);
		rightEar.mirror = false;
		rightEar.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 1);
		rightEar.setInitialRotationPoint(-2.0F, 3.0F, 1.0F);
		rightEar.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(0.0F, 0.0F, 0.0F, 1.0F)).transpose());
		rightEar.setTextureSize(128, 128);
		parts.put(rightEar.boxName, rightEar);
		head.addChild(rightEar);

		leftEar = new MCAModelRenderer(this, "LeftEar", 0, 0);
		leftEar.mirror = false;
		leftEar.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 1);
		leftEar.setInitialRotationPoint(2.0F, 3.0F, 1.0F);
		leftEar.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(0.0F, 0.0F, 0.0F, 1.0F)).transpose());
		leftEar.setTextureSize(128, 128);
		parts.put(leftEar.boxName, leftEar);
		head.addChild(leftEar);

		rightFrontFoot = new MCAModelRenderer(this, "RightFrontFoot", 19, 24);
		rightFrontFoot.mirror = false;
		rightFrontFoot.addBox(-1.5F, -6.0F, -1.0F, 3, 7, 3);
		rightFrontFoot.setInitialRotationPoint(0.0F, -7.0F, 0.0F);
		rightFrontFoot.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(-0.25881904F, 0.0F, 0.0F, 0.9659258F)).transpose());
		rightFrontFoot.setTextureSize(128, 128);
		parts.put(rightFrontFoot.boxName, rightFrontFoot);
		rightFrontLeg.addChild(rightFrontFoot);

		lefttFrontFoot = new MCAModelRenderer(this, "LefttFrontFoot", 19, 24);
		lefttFrontFoot.mirror = false;
		lefttFrontFoot.addBox(-1.5F, -6.0F, -1.0F, 3, 7, 3);
		lefttFrontFoot.setInitialRotationPoint(0.0F, -7.0F, 0.0F);
		lefttFrontFoot.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(-0.25881904F, 0.0F, 0.0F, 0.9659258F)).transpose());
		lefttFrontFoot.setTextureSize(128, 128);
		parts.put(lefttFrontFoot.boxName, lefttFrontFoot);
		leftFrontLeg.addChild(lefttFrontFoot);

		leftBackLowerLeg = new MCAModelRenderer(this, "LeftBackLowerLeg", 19, 37);
		leftBackLowerLeg.mirror = false;
		leftBackLowerLeg.addBox(-2.0F, -4.5F, -1.5F, 3, 6, 3);
		leftBackLowerLeg.setInitialRotationPoint(0.5F, -5.0F, 0.0F);
		leftBackLowerLeg.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(0.47715878F, 0.0F, 0.0F, 0.8788171F)).transpose());
		leftBackLowerLeg.setTextureSize(128, 128);
		parts.put(leftBackLowerLeg.boxName, leftBackLowerLeg);
		leftBackLeg.addChild(leftBackLowerLeg);

		rightBackLowerLeg = new MCAModelRenderer(this, "RightBackLowerLeg", 19, 37);
		rightBackLowerLeg.mirror = false;
		rightBackLowerLeg.addBox(-2.0F, -4.5F, -1.5F, 3, 6, 3);
		rightBackLowerLeg.setInitialRotationPoint(0.5F, -5.0F, 0.0F);
		rightBackLowerLeg.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(0.47715878F, 0.0F, 0.0F, 0.8788171F)).transpose());
		rightBackLowerLeg.setTextureSize(128, 128);
		parts.put(rightBackLowerLeg.boxName, rightBackLowerLeg);
		rightBackLeg.addChild(rightBackLowerLeg);

		leftBackFoot = new MCAModelRenderer(this, "LeftBackFoot", 35, 38);
		leftBackFoot.mirror = false;
		leftBackFoot.addBox(-1.0F, -5.0F, -1.5F, 3, 5, 3);
		leftBackFoot.setInitialRotationPoint(-1.0F, -4.0F, 0.0F);
		leftBackFoot.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(-0.2079117F, 0.0F, 0.0F, 0.9781476F)).transpose());
		leftBackFoot.setTextureSize(128, 128);
		parts.put(leftBackFoot.boxName, leftBackFoot);
		leftBackLowerLeg.addChild(leftBackFoot);

		rightBackFoot = new MCAModelRenderer(this, "RightBackFoot", 35, 38);
		rightBackFoot.mirror = false;
		rightBackFoot.addBox(-1.0F, -5.0F, -1.5F, 3, 5, 3);
		rightBackFoot.setInitialRotationPoint(-1.0F, -4.0F, 0.0F);
		rightBackFoot.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(-0.2079117F, 0.0F, 0.0F, 0.9781476F)).transpose());
		rightBackFoot.setTextureSize(128, 128);
		parts.put(rightBackFoot.boxName, rightBackFoot);
		rightBackLowerLeg.addChild(rightBackFoot);

	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		EntityPOOPER123 entity = (EntityPOOPER123) par1Entity;

		AnimationHandler.performAnimationInModel(parts, entity);

		//Render every non-child part
		bodyUpper.render(par7);
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
	}

	public MCAModelRenderer getModelRendererFromName(String name)
	{
		return parts.get(name);
	}
}