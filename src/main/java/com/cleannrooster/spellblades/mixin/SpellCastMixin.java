package com.cleannrooster.spellblades.mixin;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.cleannrooster.spellblades.items.interfaces.PlayerDamageInterface;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.WorldScheduler;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.internals.casting.SpellCasterEntity;
import net.spell_engine.utils.AnimationHelper;
import net.spell_engine.utils.SoundHelper;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.api.SpellSchools;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;

@Mixin(SpellHelper.class)
public class SpellCastMixin {
   
    @Inject(at = @At("TAIL"), method = "performSpell", cancellable = true)
    private static void performSpellBladesEchp(World world, PlayerEntity player, Identifier spellId, TargetHelper.SpellTargetResult targets, SpellCast.Action action, float progress, CallbackInfo callbackInfo) {
        if (!player.isSpectator()) {

            if(!spellId.equals(Identifier.of(MOD_ID,"spellstrike"))&&!action.equals(SpellCast.Action.CHANNEL) &&!(targets.entities().isEmpty() && SpellRegistry.getSpell(spellId).release.target.type.equals(Spell.Release.Target.Type.CURSOR)) && player.hasStatusEffect(SpellbladesAndSuch.UNLEASH) && player instanceof SpellCasterEntity caster && player instanceof PlayerDamageInterface playerDamageInterface){
                int repeats = player.getStatusEffect(SpellbladesAndSuch.UNLEASH).getAmplifier()+1;
                player.removeStatusEffect(SpellbladesAndSuch.UNLEASH);
                playerDamageInterface.resetDiebeamStack();
                for(int i = 0; i < repeats; i++){
                    ((WorldScheduler)player.getWorld()).schedule((i+1)*4, () -> {
                        caster.getCooldownManager().set(spellId,0,true);
                        player.removeStatusEffect(SpellbladesAndSuch.UNLEASH);
                        playerDamageInterface.resetDiebeamStack();

                        SpellHelper.performSpell(world,player,spellId,targets,action,progress);
                    });
                }

            }
        }
    }
    @Inject(at = @At("HEAD"), method = "performSpell", cancellable = true)
    private static void performSpellBladesSpellstrike(World world, PlayerEntity player, Identifier spellId, TargetHelper.SpellTargetResult targets, SpellCast.Action action, float progress, CallbackInfo callbackInfo) {
        if (!player.isSpectator() ) {
            if (!spellId.equals(Identifier.of(MOD_ID,"spellstrike")) && player instanceof SpellCasterEntity caster && player instanceof PlayerDamageInterface playerDamageInterface && playerDamageInterface.getSpellstriking()) {
                Spell spell = SpellRegistry.getSpell(spellId);
                if(spell.cast.channel_ticks == 0) {
                    SpellInfo spellInfo = new SpellInfo(spell, spellId);
                    if (!playerDamageInterface.getSpellstrikeSpells().contains(spellId)) {
                        playerDamageInterface.queueSpellStrikeSpell(spellId);
                    }
                    caster.getCooldownManager().set(spellId, 20, true);
                    caster.setSpellCastProcess(null);
                    SoundHelper.playSound(world, player, SpellRegistry.getSpell(Identifier.of(MOD_ID, "spellstrike")).release.sound);
                    AnimationHelper.sendAnimation(player, PlayerLookup.tracking(player), SpellCast.Animation.RELEASE, SpellRegistry.getSpell(Identifier.of(MOD_ID, "spellstrike")).release.animation, 1F);
                    if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                        AnimationHelper.sendAnimation(player, List.of(serverPlayerEntity), SpellCast.Animation.RELEASE, SpellRegistry.getSpell(Identifier.of(MOD_ID, "spellstrike")).release.animation, 1F);
                    }


                    callbackInfo.cancel();
                }
            }
        }
    }
}
