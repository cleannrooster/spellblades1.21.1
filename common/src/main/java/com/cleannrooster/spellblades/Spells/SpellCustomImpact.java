package com.cleannrooster.spellblades.Spells;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.event.SpellHandlers;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.internals.SpellHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellPowerTags;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;
import static net.spell_engine.api.spell.event.SpellHandlers.registerCustomDelivery;
import static net.spell_engine.api.spell.event.SpellHandlers.registerCustomImpact;

public class SpellCustomImpact {
    public static class  Shatter implements SpellHandlers.CustomImpact {
        @Override
        public SpellHandlers.ImpactResult onSpellImpact(RegistryEntry<Spell> registryEntry, SpellPower.Result result, LivingEntity livingEntity, @Nullable Entity entity, SpellHelper.ImpactContext impactContext) {
            boolean bool = false;
            boolean crit = false;
            if(entity instanceof LivingEntity living && living.getHealth()/living.getMaxHealth() <= 0.2F){
                    if(living.getStatusEffect(SpellbladesAndSuch.DEATHCHILL) != null && living.getStatusEffect(SpellbladesAndSuch.DEATHCHILL).getAmplifier() >= 2 ) {

                        entity.damage(livingEntity.getDamageSources().create(SpellSchools.FROST.damageType, livingEntity, livingEntity), ((LivingEntity) entity).getMaxHealth() * 10);
                        bool = true;
                        crit = true;
                    }
            }

            return new SpellHandlers.ImpactResult(bool,crit);
        }
    }
    public static class  Collapse implements SpellHandlers.CustomImpact {
        @Override
        public SpellHandlers.ImpactResult onSpellImpact(RegistryEntry<Spell> registryEntry, SpellPower.Result result, LivingEntity livingEntity, @Nullable Entity entity, SpellHelper.ImpactContext impactContext) {
            boolean bool = false;
            boolean crit = false;

            if(entity instanceof LivingEntity living){
                if(living.getStatusEffect(SpellbladesAndSuch.COLLAPSE) != null){
                    var amp = living.getStatusEffect(SpellbladesAndSuch.COLLAPSE).getAmplifier();
                    living.setStatusEffect(new StatusEffectInstance(SpellbladesAndSuch.COLLAPSE,40,2*(amp+1)-1),livingEntity);
                    bool = true;
                    crit = true;
                }

            }

            return new SpellHandlers.ImpactResult(bool,crit);
        }
    }
    public static class  ApplyAdd implements SpellHandlers.CustomImpact {
        @Override
        public SpellHandlers.ImpactResult onSpellImpact(RegistryEntry<Spell> registryEntry, SpellPower.Result result, LivingEntity livingEntity, @Nullable Entity entity, SpellHelper.ImpactContext impactContext) {
            boolean bool = false;
            boolean crit = false;

            if(entity instanceof LivingEntity living){
                for(Spell.Impact impact :  registryEntry.value().impacts) {
                    if(impact.action.status_effect != null) {
                        var amp = -1;
                        if (living.getStatusEffect(Registries.STATUS_EFFECT.getEntry(Identifier.of(impact.action.status_effect.effect_id)).get()) != null) {
                            amp = living.getStatusEffect(Registries.STATUS_EFFECT.getEntry(Identifier.of(impact.action.status_effect.effect_id)).get()).getAmplifier();
                        }
                        living.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(Identifier.of(impact.action.status_effect.effect_id)).get(), (int) (impact.action.status_effect.duration * 20), Math.min((int) (amp + 1 +((int)( 1F* result.randomValue()*impact.action.status_effect.amplifier_power_multiplier))), impact.action.status_effect.amplifier_cap)), livingEntity);
                        bool = true;
                    }
                }

            }

            return new SpellHandlers.ImpactResult(bool,crit);
        }
    }
    public static void registerImpacts(){
        registerCustomImpact(Identifier.of(MOD_ID,"shatter"),new SpellCustomImpact.Shatter());
        registerCustomImpact(Identifier.of(MOD_ID,"double"),new SpellCustomImpact.Collapse());
        registerCustomImpact(Identifier.of(MOD_ID,"applyadd"),new SpellCustomImpact.ApplyAdd());



    }
}
