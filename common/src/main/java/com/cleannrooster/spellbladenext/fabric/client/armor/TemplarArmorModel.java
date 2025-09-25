package com.cleannrooster.spellbladenext.fabric.client.armor;

import com.cleannrooster.spellbladenext.SpellbladesAndSuch;
import com.cleannrooster.spellbladenext.fabric.items.armor.TemplarArmor;
import mod.azure.azurelib.common.api.client.model.GeoModel;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellSchools;

public class TemplarArmorModel extends GeoModel<TemplarArmor> {
    @Override
    public Identifier getModelResource(TemplarArmor animatable) {
        if(animatable.getMagicSchool().equals(SpellSchools.ARCANE)){
            return Identifier.of(SpellbladesAndSuch.MOD_ID,"geo/cityguardian.json");
        }
        if(animatable.getMagicSchool().equals(SpellSchools.FROST)){
            return Identifier.of(SpellbladesAndSuch.MOD_ID,"geo/templeguardian.json");
        }
        return Identifier.of(SpellbladesAndSuch.MOD_ID,"geo/templar.geo.json");
    }

    @Override
    public Identifier getTextureResource(TemplarArmor animatable) {
        if(animatable.getMagicSchool().equals(SpellSchools.ARCANE)){
            return Identifier.of(SpellbladesAndSuch.MOD_ID,"textures/armor/cityguardian.png");
        }
        if(animatable.getMagicSchool().equals(SpellSchools.FROST)){
            return Identifier.of(SpellbladesAndSuch.MOD_ID,"textures/armor/templeguardian.png");
        }
        return Identifier.of(SpellbladesAndSuch.MOD_ID,"textures/armor/templar.png");
    }

    @Override
    public Identifier getAnimationResource(TemplarArmor animatable) {
        return null;
    }
}
