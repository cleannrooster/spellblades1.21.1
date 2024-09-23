package com.cleannrooster.spellblades.effect;

import com.google.common.base.Suppliers;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.casting.SpellCast;
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

public class Slamming extends StatusEffect {
    public Slamming(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }



    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        if( !entity.isOnGround() && entity instanceof PlayerEntity && !entity.getWorld().isClient()) {
            Supplier<Collection<ServerPlayerEntity>> trackingPlayers = Suppliers.memoize(() -> {
                Collection<ServerPlayerEntity> playerEntities = PlayerLookup.tracking((PlayerEntity) entity);
                return playerEntities;
            });
            AnimationHelper.sendAnimation((PlayerEntity) entity, (Collection) trackingPlayers.get(), SpellCast.Animation.RELEASE, SpellRegistry.getSpell(Identifier.of(MOD_ID, "frostvert")).cast.animation, 1);
        }
        super.onApplied(entity, amplifier);
    }

    @Override
    public void onEntityRemoval(LivingEntity player, int amplifier, Entity.RemovalReason reason) {

        super.onEntityRemoval(player, amplifier, reason);
    }




    @Override
    public boolean applyUpdateEffect(LivingEntity player, int amplifier) {
        player.fallDistance = 0;
        if( player.isOnGround() && player instanceof PlayerEntity && !player.getWorld().isClient()){

            player.removeStatusEffect(SLAMMING);
            if(player instanceof PlayerEntity && !player.getWorld().isClient()) {
                List<Entity> list = TargetHelper.targetsFromArea(player,player.getEyePos(), SpellRegistry.getSpell(Identifier.of(MOD_ID, "frostvert")).range,new Spell.Release.Target.Area(), target -> TargetHelper.allowedToHurt(player,target) );
                for(Entity entity : list) {
                    if (entity instanceof LivingEntity living) {
                        SpellHelper.ImpactContext context = new SpellHelper.ImpactContext(1.0F, 1.0F, null, SpellPower.getSpellPower(SpellSchools.FIRE,player), TargetHelper.TargetingMode.AREA);
                        SpellInfo spell = new SpellInfo(SpellRegistry.getSpell (Identifier.of(MOD_ID, "frostvert")),Identifier.of(MOD_ID, "frostvert"));

                        SpellHelper.performImpacts(player.getWorld(), player, entity, player, spell, context);

                    }
                }
                Supplier<Collection<ServerPlayerEntity>> trackingPlayers = Suppliers.memoize(() -> {
                    Collection<ServerPlayerEntity> playerEntities = PlayerLookup.tracking(player);
                    return playerEntities;
                });

                ParticleHelper.sendBatches(player, SpellRegistry.getSpell(Identifier.of(MOD_ID, "frostvert")).release.particles);
                SoundHelper.playSound(player.getWorld(), player, SpellRegistry.getSpell(Identifier.of(MOD_ID, "frostvert")).release.sound);
                AnimationHelper.sendAnimation((PlayerEntity) player, (Collection)trackingPlayers.get(), SpellCast.Animation.RELEASE, SpellRegistry.getSpell(Identifier.of(MOD_ID, "frostvert")).release.animation, 1);
            }
        }
        return super.applyUpdateEffect(player, amplifier);
    }
}
