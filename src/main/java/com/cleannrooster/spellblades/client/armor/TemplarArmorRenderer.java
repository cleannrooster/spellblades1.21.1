package com.cleannrooster.spellblades.client.armor;

import com.cleannrooster.spellblades.items.armor.TemplarArmor;
import mod.azure.azurelib.common.api.client.model.GeoModel;
import mod.azure.azurelib.common.api.client.renderer.GeoArmorRenderer;
import mod.azure.azurelib.common.api.client.renderer.GeoArmorRendererConstants;
import mod.azure.azurelib.common.internal.client.util.RenderUtils;
import mod.azure.azurelib.common.internal.common.cache.object.GeoBone;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;

public class TemplarArmorRenderer extends GeoArmorRenderer<TemplarArmor> {
    public TemplarArmorRenderer() {
        super(new TemplarArmorModel());
    }

    public @Nullable GeoBone getWaistBone() {
        return this.model.getBone("bipedWaist").orElse(null);
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
        if (this.rightLeg != null) {
            ModelPart rightLegPart = baseModel.rightLeg;
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
        }

        if (this.leftLeg != null) {
            ModelPart leftLegPart = baseModel.leftLeg;
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
