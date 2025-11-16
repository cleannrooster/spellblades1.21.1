package com.cleannrooster.spellblades.effect;

import com.cleannrooster.spellblades.Spells.SpellbladeSpells;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.internals.SpellHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

import java.util.List;

public class Collapse extends CustomEffect{
    public Collapse(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration == 1;
    }

    @Override
    public void onEntityDamage(LivingEntity entity, int amplifier, DamageSource source, float amount) {
        super.onEntityDamage(entity, amplifier, source, amount);

    }

    @Override
    public ParticleEffect createParticle(StatusEffectInstance effect) {
        return ParticleTypes.SONIC_BOOM;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        var impact = new Spell.Impact();
        ParticleBatch[] particlebatch2 = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_arcane_spark_float", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,20,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,20,0.05f,0.1F,360),
                new ParticleBatch("spell_engine:magic_arcane_spark_float", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,100,0.35f,0.75F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,100,0.35f,0.7F,360)


        };
        impact = SpellbladeSpells.createArcaneImpact(0.8F,0.5F);
        impact.particles = particlebatch2;
        var context = new SpellHelper.ImpactContext().power(new SpellPower.Result(SpellSchools.ARCANE,(amplifier+1)*2,0.0F,0.0F));
        var impacts = List.of(impact);
        if(entity.getLastAttacker() != null) {
            SpellHelper.performImpacts(entity.getWorld(), entity.getLastAttacker(),entity, entity, SpellRegistry.from((entity.getWorld())).getEntry(Identifier.of("spellbladenext:echoes")).get(),
                    impacts,context);
        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
