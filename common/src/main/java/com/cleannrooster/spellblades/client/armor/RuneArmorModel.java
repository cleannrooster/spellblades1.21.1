package com.cleannrooster.spellblades.client.armor;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.cleannrooster.spellblades.items.armor.RunicArmor;
import mod.azure.azurelib.common.api.client.model.GeoModel;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellSchools;

public class RuneArmorModel extends GeoModel<RunicArmor> {
    @Override
    public Identifier getModelResource(RunicArmor animatable) {

        return Identifier.of(SpellbladesAndSuch.MOD_ID,"geo/rune_armor_v2_newaz.geo.json");
    }

    @Override
    public Identifier getTextureResource(RunicArmor animatable) {
        if(animatable.canRepair(animatable.getDefaultStack(),Items.NETHERITE_INGOT.getDefaultStack())){
            if(animatable.getMagicSchool().equals(SpellSchools.ARCANE)){
                return Identifier.of(SpellbladesAndSuch.MOD_ID,"textures/armor/rune_armor_arcane_netherite.png");
            }
            if(animatable.getMagicSchool().equals(SpellSchools.FROST)){
                return Identifier.of(SpellbladesAndSuch.MOD_ID,"textures/armor/rune_armor_frost_netherite.png");
            }
            return Identifier.of(SpellbladesAndSuch.MOD_ID,"textures/armor/rune_armor_fire_netherite.png");

        }
        if(animatable.getMagicSchool().equals(SpellSchools.ARCANE)){
            return Identifier.of(SpellbladesAndSuch.MOD_ID,"textures/armor/rune_armor_arcane.png");
        }
        if(animatable.getMagicSchool().equals(SpellSchools.FROST)){
            return Identifier.of(SpellbladesAndSuch.MOD_ID,"textures/armor/rune_armor_frost.png");
        }
        return Identifier.of(SpellbladesAndSuch.MOD_ID,"textures/armor/rune_armor_fire.png");
    }

    @Override
    public Identifier getAnimationResource(RunicArmor animatable) {
        return null;
    }
}
