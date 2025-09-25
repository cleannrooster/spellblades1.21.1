package com.cleannrooster.spellbladenext.fabric.effect;

import com.cleannrooster.spellbladenext.fabric.Spells.SpellbladeSpells;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.spell_engine.fx.ParticleHelper;

public class PhaseDash extends CustomEffect{
    public PhaseDash(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return  true;
    }



    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!entity.getWorld().isClient()) {
            ParticleHelper.sendBatches(entity, SpellbladeSpells.phasedash().spell().impacts.get(0).particles);
        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
