package com.cleannrooster.spellblades.client.armor;

import com.cleannrooster.spellblades.items.armor.RunicArmor;
import mod.azure.azurelib.common.api.client.renderer.GeoArmorRenderer;
import mod.azure.azurelib.common.internal.client.util.RenderUtils;
import mod.azure.azurelib.common.internal.common.cache.object.GeoBone;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;

public class RuneArmorRenderer extends GeoArmorRenderer<RunicArmor> {
    public RuneArmorRenderer() {
        super(new RuneArmorModel());
    }

    public @Nullable GeoBone getWaistBone() {
        return this.model.getBone("armorWaist").orElse(null);
    }
    public @Nullable GeoBone getWaistFront() {
        return this.model.getBone("waistFront").orElse(null);
    }
    public @Nullable GeoBone getWaistBack() {
        return this.model.getBone("waistBack").orElse(null);
    }
    public @Nullable GeoBone getWaistLeft() {
        return this.model.getBone("waistLeft").orElse(null);
    }
    public @Nullable GeoBone getWaistRight() {
        return this.model.getBone("waistRight").orElse(null);
    }

    protected void applyBaseTransformations(BipedEntityModel<?> baseModel) {
        if (this.head != null) {
            ModelPart headPart = baseModel.head;

            RenderUtils.matchModelPartRot(headPart, this.head);
            this.head.updatePosition(headPart.pivotX, -headPart.pivotY, headPart.pivotZ);
        }

        if (this.body != null) {
            ModelPart bodyPart = baseModel.body;

            RenderUtils.matchModelPartRot(bodyPart, this.body);
            this.body.updatePosition(bodyPart.pivotX, -bodyPart.pivotY, bodyPart.pivotZ);
        }

        if (this.rightArm != null) {
            ModelPart rightArmPart = baseModel.rightArm;

            RenderUtils.matchModelPartRot(rightArmPart, this.rightArm);
            this.rightArm.updatePosition(rightArmPart.pivotX + 5, 2 - rightArmPart.pivotY, rightArmPart.pivotZ);
        }

        if (this.leftArm != null) {
            ModelPart leftArmPart = baseModel.leftArm;

            RenderUtils.matchModelPartRot(leftArmPart, this.leftArm);
            this.leftArm.updatePosition(leftArmPart.pivotX - 5f, 2f - leftArmPart.pivotY, leftArmPart.pivotZ);
        }
        boolean bool = false;
        boolean bool2 = true;
        boolean bool3 = true;
        ModelPart rightLegPart = baseModel.rightLeg;
        ModelPart leftLegPart = baseModel.leftLeg;
        if(rightLegPart != null && leftLegPart != null){
            if(leftLegPart.pitch > rightLegPart.pitch){
                bool2 = false;
            }
            if(leftLegPart.roll > rightLegPart.roll){
                bool3 = false;
            }
        }
        if (this.rightLeg != null) {
             rightLegPart = baseModel.rightLeg;
            ModelPart bodyPart = baseModel.body;

            RenderUtils.matchModelPartRot(rightLegPart, this.rightLeg);
            this.rightLeg.updatePosition(rightLegPart.pivotX + 2, 12 - rightLegPart.pivotY, rightLegPart.pivotZ);
            if(this.getWaistBone() != null) {

                RenderUtils.matchModelPartRot(bodyPart, this.getWaistBone());
                this.getWaistBone().updatePosition(bodyPart.pivotX, -bodyPart.pivotY, bodyPart.pivotZ);
                bool = true;
            }
            if (this.rightBoot != null) {
                RenderUtils.matchModelPartRot(rightLegPart, this.rightBoot);
                this.rightBoot.updatePosition(rightLegPart.pivotX + 2, 12 - rightLegPart.pivotY, rightLegPart.pivotZ);
            }
            if(this.getWaistFront() != null){
                if(bool2 && leftLegPart != null){
                    this.getWaistFront().updateRotation(-leftLegPart.pitch+bodyPart.pitch+10*(float)Math.PI/180F, bodyPart.yaw,bodyPart.roll);

                }
                else {
                    RenderUtils.matchModelPartRot(rightLegPart, this.getWaistFront());
                    this.getWaistFront().updateRotation(-rightLegPart.pitch+bodyPart.pitch+10F*(float)Math.PI/180F, bodyPart.yaw,bodyPart.roll);

                }

            }
            if(this.getWaistBack() != null){
                if(bool2){
                    RenderUtils.matchModelPartRot(rightLegPart, this.getWaistBack());
                    this.getWaistBack().updateRotation(-rightLegPart.pitch+bodyPart.pitch-10*(float)Math.PI/180F, bodyPart.yaw,bodyPart.roll);

                }
                else {
                    RenderUtils.matchModelPartRot(leftLegPart, this.getWaistBack());
                    this.getWaistBack().updateRotation(-leftLegPart.pitch+bodyPart.pitch-10*(float)Math.PI/180F, bodyPart.yaw,bodyPart.roll);

                }
            }
            if(this.getWaistRight() != null && leftLegPart != null){
                this.getWaistRight().updateRotation(bodyPart.pitch, bodyPart.yaw,leftLegPart.roll-bodyPart.roll-10*(float)Math.PI/180F);


            }

        }

        if (this.leftLeg != null) {
             leftLegPart = baseModel.leftLeg;
            ModelPart bodyPart = baseModel.body;

            RenderUtils.matchModelPartRot(leftLegPart, this.leftLeg);
            this.leftLeg.updatePosition(leftLegPart.pivotX - 2, 12 - leftLegPart.pivotY, leftLegPart.pivotZ);
            if(!bool && this.getWaistBone() != null){
                RenderUtils.matchModelPartRot(bodyPart, this.getWaistBone());

                this.getWaistBone().updatePosition(bodyPart.pivotX, -bodyPart.pivotY, bodyPart.pivotZ);
                bool = true;
            }
            if (this.leftBoot != null) {
                RenderUtils.matchModelPartRot(leftLegPart, this.leftBoot);
                this.leftBoot.updatePosition(leftLegPart.pivotX - 2, 12 - leftLegPart.pivotY, leftLegPart.pivotZ);

            }

            if(this.getWaistLeft() != null && rightLegPart != null){
                this.getWaistLeft().updateRotation(bodyPart.pitch, bodyPart.yaw,rightLegPart.roll-bodyPart.roll+10*(float)Math.PI/180F);

            }
        }
    }
    @Override
    protected void applyBoneVisibilityBySlot(EquipmentSlot currentSlot) {
        // Hide all bones initially
        this.setBoneVisible(getHeadBone(), false);
        this.setBoneVisible(getBodyBone(), false);
        this.setBoneVisible(getRightArmBone(), false);
        this.setBoneVisible(getLeftArmBone(), false);
        this.setBoneVisible(getRightLegBone(), false);
        this.setBoneVisible(getLeftLegBone(), false);
        this.setBoneVisible(getRightBootBone(), false);
        this.setBoneVisible(getLeftBootBone(), false);

        // Hide the legging torso bone initially
        this.setBoneVisible(getWaistBone(), false);

        // Make specific bones visible based on the equipped armor slot
        switch (currentSlot) {
            case HEAD -> this.setBoneVisible(getHeadBone(), true);
            case CHEST -> {
                this.setBoneVisible(getBodyBone(), true);
                this.setBoneVisible(getRightArmBone(), true);
                this.setBoneVisible(getLeftArmBone(), true);
            }
            case LEGS -> {
                // Make the legging torso bone visible when the legging armor is equiped
                this.setBoneVisible(getWaistBone(), true);

                this.setBoneVisible(getRightLegBone(), true);
                this.setBoneVisible(getLeftLegBone(), true);
            }
            case FEET -> {
                this.setBoneVisible(getRightBootBone(), true);
                this.setBoneVisible(getLeftBootBone(), true);
            }
        }
    }
}
