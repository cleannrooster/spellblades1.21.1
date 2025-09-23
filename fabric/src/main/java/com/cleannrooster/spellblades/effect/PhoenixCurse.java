package com.cleannrooster.spellblades.effect;

import com.extraspellattributes.api.SpellStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellSchools;
import net.spell_power.mixin.DamageSourcesAccessor;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;

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
