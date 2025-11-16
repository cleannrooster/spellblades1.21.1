package com.cleannrooster.spellblades.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class Spellstrike extends StatusEffect {
    public Spellstrike(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration == 1;
    }


    @Override
    public void onApplied(LivingEntity entity, int amplifier) {

        super.onApplied(entity, amplifier);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity player, int amplifier) {

        return super.applyUpdateEffect(player, amplifier);
    }


}
