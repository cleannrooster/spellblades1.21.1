package com.cleannrooster.spellblades.effect;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.extraspellattributes.api.SpellStatusEffect;
import com.extraspellattributes.api.SpellStatusEffectInstance;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.particle.ParticleHelper;
import net.spell_engine.utils.SoundHelper;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

import java.util.List;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.DEATHCHILL;
import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;

public class Deathchill extends SpellStatusEffect {
    public Deathchill(StatusEffectCategory category, int color, Spell spell) {
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
        super.onEntityRemoval(entity, amplifier, reason);

    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applySpellEffect(LivingEntity entity,LivingEntity owner, int amplifier, float spellPower, Spell spell) {
        if(owner instanceof PlayerEntity player && spell != null && spellPower > 0){

            SpellHelper.performImpacts(player.getWorld(),player,entity,player,new SpellInfo(spell, Identifier.of(SpellbladesAndSuch.MOD_ID,"deathchill")),

                    new SpellHelper.ImpactContext().target(TargetHelper.TargetingMode.DIRECT).power(new SpellPower.Result(SpellSchools.FROST,spellPower,0,1)));

        }
    }
}
