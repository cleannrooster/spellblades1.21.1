package com.cleannrooster.spellblades.effect;

import com.extraspellattributes.api.SpellStatusEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.TypeFilter;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.utils.TargetHelper;

import java.util.List;

public class Sundered extends SpellStatusEffect {
    public Sundered(StatusEffectCategory category, int color, Spell spell) {
        super(category, color, spell);
    }

    @Override
    public boolean canApplySpellEffect(int duration, int amplifier){
        if(duration % 10 == 1){
            return true;
        }
        return super.canApplyUpdateEffect(duration, amplifier);
    }



}
