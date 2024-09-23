package com.cleannrooster.spellblades.mixin;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.casting.SpellCasterClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {
    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void render(T entity, float f, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo info) {
        if(entity instanceof SpellCasterClient caster) {

            if (caster.getCurrentSpell() != null && (caster.getCurrentSpell().equals(SpellRegistry.getSpell(Identifier.of(SpellbladesAndSuch.MOD_ID, "eviscerate"))) || (caster.getCurrentSpell().equals(SpellRegistry.getSpell(Identifier.of(SpellbladesAndSuch.MOD_ID, "monkeyslam"))) && caster.getSpellCastProgress() != null && caster.getSpellCastProgress().ratio() > 17 / 160F))) {

                info.cancel();

            }

        }
    }

}
