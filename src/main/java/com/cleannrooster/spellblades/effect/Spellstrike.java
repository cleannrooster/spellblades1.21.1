package com.cleannrooster.spellblades.effect;

import com.cleannrooster.spellblades.items.interfaces.PlayerDamageInterface;
import com.google.common.base.Suppliers;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.internals.casting.SpellCasterEntity;
import net.spell_engine.particle.ParticleHelper;
import net.spell_engine.utils.AnimationHelper;
import net.spell_engine.utils.SoundHelper;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;
import static com.cleannrooster.spellblades.SpellbladesAndSuch.SLAMMING;

public class Spellstrike extends StatusEffect {
    public Spellstrike(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration == 1;
    }









    @Override
    public boolean applyUpdateEffect(LivingEntity player, int amplifier) {

        if(player instanceof PlayerDamageInterface playerDamageInterface && player instanceof SpellCasterEntity caster){
            playerDamageInterface.clearSpellstrikeSpells();
            int cooldown = (int)(SpellHelper.getCooldownDuration(player,SpellRegistry.getSpell(Identifier.of(MOD_ID,"spellstrike")))*20);
            caster.getCooldownManager().set(Identifier.of(MOD_ID,"spellstrike"),cooldown);

        }
        return super.applyUpdateEffect(player, amplifier);
    }
}
