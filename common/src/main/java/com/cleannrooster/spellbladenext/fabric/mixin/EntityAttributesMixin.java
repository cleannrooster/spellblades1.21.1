package com.cleannrooster.spellbladenext.fabric.mixin;

import com.cleannrooster.spellbladenext.spellblades.CustomAttributes;
import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(EntityAttributes.class)
public class EntityAttributesMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void static_tail_SB(CallbackInfo ci) {
        CustomAttributes.register();
    }
}
