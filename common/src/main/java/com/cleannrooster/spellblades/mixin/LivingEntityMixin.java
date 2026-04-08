package com.cleannrooster.spellblades.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cleannrooster.spellblades.CustomAttributes.EPHEMERAL;

@Mixin(value = LivingEntity.class)
public class LivingEntityMixin {
  




    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void addAttributesSpellblades_RETURN(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        if(EPHEMERAL == null)
            EPHEMERAL = Registry.registerReference(Registries.ATTRIBUTE, Identifier.of("spellbladenext", "ephemeral"), new ClampedEntityAttribute("attribute.name.spellbladenext.ephemeral", 100,100,9999));

        info.getReturnValue().add(EPHEMERAL);
    }

}
