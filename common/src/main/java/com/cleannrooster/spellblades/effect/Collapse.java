package com.cleannrooster.spellblades.effect;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
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
import net.spell_engine.client.util.Color;
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
        ParticleBatch[] particlebatch2 = new ParticleBatch[]{new ParticleBatch("spell_engine:magic_arcane_spark_float", net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F), (new ParticleBatch(SpellEngineParticles.MagicParticles.get(SpellEngineParticles.MagicParticles.Shape.SPARK, SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA())};

        impact = SpellbladeSpells.createArcaneImpact(0.8F,0.5F);
        impact.particles = particlebatch2;
        var context = new SpellHelper.ImpactContext().power(new SpellPower.Result(SpellSchools.ARCANE,(amplifier+1)*2,0.0F,0.0F));
        var impacts = List.of(impact);
        if(entity.getLastAttacker() != null) {
            SpellHelper.performImpacts(entity.getWorld(), entity.getLastAttacker(),entity, entity, SpellRegistry.from((entity.getWorld())).getEntry(Identifier.of("spellbladenext:collapse")).get(),
                    impacts,context);
        }
        if(entity.hasStatusEffect(SpellbladesAndSuch.COLLAPSE)){
            entity.removeStatusEffect(SpellbladesAndSuch.COLLAPSE);
        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
