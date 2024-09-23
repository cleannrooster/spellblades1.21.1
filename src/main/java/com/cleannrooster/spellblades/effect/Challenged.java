package com.cleannrooster.spellblades.effect;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.extraspellattributes.api.SpellStatusEffect;
import com.extraspellattributes.api.SpellStatusEffectInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

import java.util.List;

public class Challenged extends SpellStatusEffect {
    public Challenged(StatusEffectCategory category, int color, Spell spell) {
        super(category, color, spell);
    }

    @Override
    public boolean canApplySpellEffect(int duration, int amplifier){
        if(duration % 10 == 1){
            return true;
        }
        return super.canApplyUpdateEffect(duration, amplifier);
    }

    @Override
    public void onEntityRemoval(LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
        if(entity.getStatusEffect(SpellbladesAndSuch.CHALLENGED) instanceof SpellStatusEffectInstance challenge && challenge.getOwner() != null && reason.equals(Entity.RemovalReason.KILLED) ){
            if(challenge.getOwner().getStatusEffect(SpellbladesAndSuch.FERVOR) instanceof StatusEffectInstance effectInstance) {
                challenge.getOwner().heal(challenge.getOwner().getMaxHealth()*(1+effectInstance.getAmplifier())*0.1F);
            }
            challenge.getOwner().addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,80,2));
            challenge.getOwner().addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,80,2));

            List<LivingEntity> list = entity.getWorld().getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class),challenge.getOwner().getBoundingBox().expand(16),entity2 -> TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, TargetHelper.Intent.HARMFUL,challenge.getOwner(),entity2));
            list.remove(entity);
            for(LivingEntity living: list) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 80, 1));
            }
        }
        super.onEntityRemoval(entity, amplifier, reason);


    }

    @Override
    public void applySpellEffect(LivingEntity entity, LivingEntity owner, int amplifier, float spellPower, Spell spell) {
        super.applySpellEffect(entity, owner, amplifier, spellPower, spell);
        if(owner != null){
            if(entity instanceof HostileEntity hostileEntity){
                hostileEntity.setTarget(owner);
            }
        }
    }
}
