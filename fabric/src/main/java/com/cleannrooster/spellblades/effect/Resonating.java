package com.cleannrooster.spellblades.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleEffect;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.fx.ParticleHelper;
import net.spell_engine.fx.SpellEngineParticles;

import static net.spell_power.api.SpellSchools.FROST;

public class Resonating extends CustomEffect{
    public static ParticleBatch SHIELDPARTICLES_PURPLE;
    static{
        SHIELDPARTICLES_PURPLE = new ParticleBatch(SpellEngineParticles.area_effect_293.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.FEET,null,0,0,1,0,0,0,0,0,false, 4284940287L, 2,true,1F);

    }
    public Resonating(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public ParticleEffect createParticle(StatusEffectInstance effect) {
        return super.createParticle(effect);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        if(duration % 10 == 0){
            return true;

        }
        return super.canApplyUpdateEffect(duration, amplifier);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!entity.getWorld().isClient()){
            ParticleHelper.sendBatches(entity, new ParticleBatch[]{
                    SHIELDPARTICLES_PURPLE
            });
        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
