package com.cleannrooster.spellblades.mixin;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.container.SpellContainerHelper;
import net.spell_engine.internals.SpellHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpellContainerHelper.class)

public class SpellContainerHelperMixin {

    /*@Inject(at = @At("HEAD"), method = "getFirstSourceOfSpell", cancellable = true)
    private static void getFirstSourceOfSpellSpellblades(Identifier spellId, PlayerEntity player, CallbackInfoReturnable<SpellContainerHelper.Source> callbackInfoReturnable) {
        if(SpellRegistry.from(player.getWorld()).get(spellId).cost.cooldown_hosting_item == false){
            callbackInfoReturnable.setReturnValue(new SpellContainerHelper(player.getMainHandStack(),SpellContainerHelper.(player)));
        }

    }*/
}
