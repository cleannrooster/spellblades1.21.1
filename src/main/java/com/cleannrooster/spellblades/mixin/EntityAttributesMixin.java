package com.cleannrooster.spellblades.mixin;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.EPHEMERAL;
import static com.extraspellattributes.ReabsorptionInit.*;
import static com.extraspellattributes.ReabsorptionInit.MOD_ID;

@Mixin(EntityAttributes.class)
public class EntityAttributesMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void static_tail_SB(CallbackInfo ci) {
        EPHEMERAL = Registry.registerReference(Registries.ATTRIBUTE, Identifier.of(MOD_ID, "ephemeral"), new ClampedEntityAttribute("attribute.name.spellbladenext.ephemeral", 100,100,9999));

    }
}
