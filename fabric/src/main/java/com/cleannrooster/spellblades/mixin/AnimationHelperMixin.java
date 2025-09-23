package com.cleannrooster.spellblades.mixin;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.cleannrooster.spellblades.items.Spellblade;
import com.cleannrooster.spellblades.items.interfaces.PlayerDamageInterface;
import com.extraspellattributes.PlayerInterface;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.container.SpellContainerHelper;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.internals.casting.SpellCasterEntity;
import net.spell_engine.internals.container.SpellContainerSource;
import net.spell_engine.utils.AnimationHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Objects;

@Mixin(AnimationHelper.class)
public class AnimationHelperMixin {
    @Inject(at = @At("HEAD"), method = "sendAnimation", cancellable =   true)
    private static  void sendAnimationSpellblades(PlayerEntity animatedPlayer, Collection<ServerPlayerEntity> trackingPlayers, SpellCast.Animation type, String name, float speed, CallbackInfo info) {

        if(name != null && animatedPlayer instanceof PlayerDamageInterface playerInterface&&  name.equals( Identifier.of(SpellbladesAndSuch.MOD_ID,"sword_swing_first").toString())){
            if(playerInterface.isSecondSwing()) {
                AnimationHelper.sendAnimation(animatedPlayer, trackingPlayers, type, Identifier.of(SpellbladesAndSuch.MOD_ID, "sword_swing_second").toString(), speed);
                info.cancel();
            }

            playerInterface.nextSwing();
        }
        if(name != null && animatedPlayer instanceof PlayerDamageInterface playerInterface&&  name.equals( Identifier.of(SpellbladesAndSuch.MOD_ID,"spellbladestance").toString())) {
            if(animatedPlayer.getOffHandStack().getItem() instanceof SwordItem){
                AnimationHelper.sendAnimation(animatedPlayer, trackingPlayers, type, Identifier.of(SpellbladesAndSuch.MOD_ID, "spellbladestancedw").toString(), speed);
                info.cancel();

            }
            if(!animatedPlayer.getOffHandStack().isEmpty()){
                info.cancel();
            }
        }

    }
}
