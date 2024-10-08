package com.cleannrooster.spellblades.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.particle.ParticleHelper;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;

public class Fervor extends CustomEffect{
    public Fervor(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        ParticleHelper.play(entity.getWorld(),entity, SpellRegistry.getSpell(Identifier.of(MOD_ID,"particlesholy")).release.target.cloud.client_data.particles);
        return super.applyUpdateEffect(entity, amplifier);
    }
}
