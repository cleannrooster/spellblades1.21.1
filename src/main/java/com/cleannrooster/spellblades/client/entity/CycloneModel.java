package com.cleannrooster.spellblades.client.entity;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.cleannrooster.spellblades.entity.CycloneEntity;
import mod.azure.azurelib.common.api.client.model.GeoModel;
import net.minecraft.util.Identifier;

public class CycloneModel<T extends CycloneEntity> extends GeoModel<CycloneEntity> {

    @Override
    public Identifier getModelResource(CycloneEntity reaver) {

        return Identifier.of(SpellbladesAndSuch.MOD_ID,"geo/cyclone.json");
    }
    @Override
    public Identifier getTextureResource(CycloneEntity reaver) {
        if(reaver.getColor() == 1) {
            return Identifier.of(SpellbladesAndSuch.MOD_ID, "textures/mob/whirlwind.png");
        }
        else if (reaver.getColor() == 2 || reaver.getColor() == 5){
            return Identifier.of(SpellbladesAndSuch.MOD_ID, "textures/mob/maelstrom.png");
        }
        else if (reaver.getColor() == 3){
            return Identifier.of(SpellbladesAndSuch.MOD_ID, "textures/mob/tempest.png");
        }
        else if (reaver.getColor() == 4){
            return Identifier.of(SpellbladesAndSuch.MOD_ID, "textures/mob/inferno.png");
        }
        return Identifier.of(SpellbladesAndSuch.MOD_ID, "textures/mob/whirlwind.png");

    }

    @Override
    public Identifier getAnimationResource(CycloneEntity reaver) {
        return Identifier.of(SpellbladesAndSuch.MOD_ID,"animations/cyclone.animation.json");
    }

}
