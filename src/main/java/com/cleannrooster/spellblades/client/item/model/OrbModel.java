package com.cleannrooster.spellblades.client.item.model;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.cleannrooster.spellblades.items.Orb;
import mod.azure.azurelib.common.api.client.model.GeoModel;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellSchools;

public class OrbModel extends GeoModel<Orb> {


    @Override
    public Identifier getModelResource(Orb orb) {
        return Identifier.of(SpellbladesAndSuch.MOD_ID,"geo/orb.geo.json");
    }

    @Override
    public Identifier getTextureResource(Orb orb) {
        if(orb.getSchool() == SpellSchools.FIRE) {
            return Identifier.of(SpellbladesAndSuch.MOD_ID, "textures/item/orb_fire.png");
        }
        if(orb.getSchool() == SpellSchools.FROST) {
            return Identifier.of(SpellbladesAndSuch.MOD_ID, "textures/item/orb_frost.png");
        }

        return Identifier.of(SpellbladesAndSuch.MOD_ID, "textures/item/orb_arcane.png");
    }

    @Override
    public Identifier getAnimationResource(Orb orb) {
        return Identifier.of(SpellbladesAndSuch.MOD_ID,"animations/orb.animations.json");
    }
}