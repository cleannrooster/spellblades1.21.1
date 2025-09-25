package com.cleannrooster.spellbladenext.fabric.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleEffect;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.fx.ParticleHelper;

import static net.spell_power.api.SpellSchools.FROST;

public class ArcticArmor extends CustomEffect{
    public static ParticleBatch ARCTICARMORPARTICLES;
    static{
        ARCTICARMORPARTICLES = new ParticleBatch("spell_engine:area_effect_714", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.FEET,null,0,0,1,0,0,0,0,0,false,FROST.color,2,true,1F);

    }
    public ArcticArmor(StatusEffectCategory category, int color) {
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
                    ARCTICARMORPARTICLES
            });
        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
