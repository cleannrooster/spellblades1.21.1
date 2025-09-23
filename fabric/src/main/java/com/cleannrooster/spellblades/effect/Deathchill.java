package com.cleannrooster.spellblades.effect;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.extraspellattributes.api.SpellStatusEffect;
import com.extraspellattributes.api.SpellStatusEffectInstance;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.List;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.DEATHCHILL;
import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;

public class Deathchill extends StatusEffect {
    public Deathchill(StatusEffectCategory category, int color) {
        super(category, color);
    }



    @Override
    public void onEntityRemoval(LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
        super.onEntityRemoval(entity, amplifier, reason);

    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        if(duration % 10 == 1){
            return true;
        }
        else{
            return false;
        }
    }



}
