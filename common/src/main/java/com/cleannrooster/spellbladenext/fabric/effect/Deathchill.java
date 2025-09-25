package com.cleannrooster.spellbladenext.fabric.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

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
