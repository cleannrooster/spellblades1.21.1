package com.cleannrooster.spellblades.client.entity;

import com.cleannrooster.spellblades.entity.CycloneEntity;
import mod.azure.azurelib.common.api.client.renderer.GeoEntityRenderer;
import mod.azure.azurelib.common.internal.common.cache.object.BakedGeoModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class CycloneRenderer<T extends CycloneEntity> extends GeoEntityRenderer<CycloneEntity> {

    public CycloneRenderer(EntityRendererFactory.Context context) {
        super(context, new CycloneModel<>());

    }

    @Override
    public void preRender(MatrixStack poseStack, CycloneEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        if(animatable.getOwner() != null ){

            if(animatable.getColor() != 5){
                this.scaleWidth = animatable.getOwner().getHeight() * 1.25F;
                this.scaleHeight = animatable.getOwner().getHeight() * 1.25F;

                poseStack.translate(-animatable.lastRenderX,-animatable.lastRenderY,-animatable.lastRenderZ);
                poseStack.translate(animatable.getOwner().lastRenderX,animatable.getOwner().lastRenderY,animatable.getOwner().lastRenderZ);

            }
            else{
                this.scaleWidth = 2 * 1.25F;
                this.scaleHeight = 2 * 1.25F;

            }

        }
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }


    @Override
    public boolean shouldRender(CycloneEntity entity, Frustum frustum, double x, double y, double z) {
        if(entity.getColor() != 5 && MinecraftClient.getInstance() != null && MinecraftClient.getInstance().cameraEntity != null &&  entity.getOwner() != null && MinecraftClient.getInstance().cameraEntity.equals(entity.getOwner()) && MinecraftClient.getInstance().options.getPerspective().isFirstPerson()){
            return false;
        }
        else{
            return super.shouldRender(entity,frustum,x,y,z);
        }
    }
}