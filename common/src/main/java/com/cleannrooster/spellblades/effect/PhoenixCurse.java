package com.cleannrooster.spellblades.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class PhoenixCurse extends StatusEffect {
    public PhoenixCurse(StatusEffectCategory category, int color) {
        super(category, color);
    }



    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        if(duration % 10 == 1){
            return true;
        }
        return super.canApplyUpdateEffect(duration, amplifier);

    }



    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {

        return super.applyUpdateEffect(entity, amplifier);
    }
}
