package com.cleannrooster.spellblades.effect;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.extraspellattributes.api.SpellStatusEffect;
import com.extraspellattributes.api.SpellStatusEffectInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;
import net.spell_power.internals.CrossFunctionalAttributes;
import net.spell_power.mixin.attributes.CrossEntityAttributeInstance;

import java.util.List;

public class Challenged extends StatusEffect {
    public Challenged(StatusEffectCategory category, int color) {
        super(category, color);
    }


    @Override
    public void onEntityRemoval(LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
/*
        List<LivingEntity> list2 = entity.getWorld().getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class),entity.getBoundingBox().expand(16),entity2 -> TargetHelper.getRelation(entity,entity2).equals(TargetHelper.Relation.HOSTILE));
        list2.remove(entity);

        for(LivingEntity living : list2) {
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 80, 2));
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 80, 2));
        }
        List<LivingEntity> list = entity.getWorld().getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class),entity.getBoundingBox().expand(16),entity2 -> TargetHelper.getRelation(entity,entity2).equals(TargetHelper.Relation.FRIENDLY) || TargetHelper.getRelation(entity,entity2).equals(TargetHelper.Relation.ALLY) );
        list.remove(entity);
        for(LivingEntity living: list) {
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 80, 1));
        }
*/

        super.onEntityRemoval(entity, amplifier, reason);


    }



    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity.getLastAttacker() != null){
            if(entity instanceof HostileEntity hostile) {
                hostile.setTarget(hostile.getAttacker());
                if (entity.getBrain() != null) {
                    if (entity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET)) {
                        entity.getBrain().remember(MemoryModuleType.ATTACK_TARGET, hostile.getAttacker());
                    }
                    if (entity.getBrain().hasMemoryModule(MemoryModuleType.ANGRY_AT)) {
                        entity.getBrain().remember(MemoryModuleType.ANGRY_AT, hostile.getAttacker().getUuid());
                    }
                }
            }
        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
