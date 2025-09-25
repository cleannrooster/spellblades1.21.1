package com.cleannrooster.spellbladenext.fabric.mixin;

import net.spell_engine.api.spell.container.SpellContainerHelper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpellContainerHelper.class)

public class SpellContainerHelperMixin {

    /*@Inject(at = @At("HEAD"), method = "getFirstSourceOfSpell", cancellable = true)
    private static void getFirstSourceOfSpellSpellblades(Identifier spellId, PlayerEntity player, CallbackInfoReturnable<SpellContainerHelper.Source> callbackInfoReturnable) {
        if(SpellRegistry.from(player.getWorld()).get(spellId).cost.cooldown_hosting_item == false){
            callbackInfoReturnable.setReturnValue(new SpellContainerHelper(player.getMainHandStack(),SpellContainerHelper.(player)));
        }

    }*/
}
