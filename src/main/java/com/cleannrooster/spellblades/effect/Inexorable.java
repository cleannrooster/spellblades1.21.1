package com.cleannrooster.spellblades.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.fx.ParticleHelper;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;

public class Inexorable extends CustomEffect{
    public Inexorable(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onEntityDamage(LivingEntity entity, int amplifier, DamageSource source, float amount) {
        super.onEntityDamage(entity, amplifier, source, amount);

    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        ParticleHelper.play(entity.getWorld(),entity, SpellRegistry.from(entity.getWorld()).get(Identifier.of(MOD_ID,"particlesholy")).deliver.clouds.get(0).client_data.particles);
        return super.applyUpdateEffect(entity, amplifier);
    }
}
