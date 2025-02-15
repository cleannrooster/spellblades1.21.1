package com.cleannrooster.spellblades.client.effect;


import com.cleannrooster.spellblades.SpellbladesAndSuch;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.api.effect.CustomModelStatusEffect;

public class MagusEffectRenderer implements CustomModelStatusEffect.Renderer {
    public static final Identifier textureIDArcane = Identifier.of(SpellbladesAndSuch.MOD_ID, "effect/hunters_mark");


    public static final Identifier textureIDFrost = Identifier.of(SpellbladesAndSuch.MOD_ID, "effect/hunters_mark");
    public static final Identifier textureIDFire = Identifier.of(SpellbladesAndSuch.MOD_ID, "effect/hunters_mark");


    @Override
    public void renderEffect(int i, LivingEntity livingEntity, float v, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i1) {

    }
}