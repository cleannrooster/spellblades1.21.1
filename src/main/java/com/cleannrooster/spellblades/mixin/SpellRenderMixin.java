package com.cleannrooster.spellblades.mixin;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.spell_engine.client.util.SpellRender;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;


@Mixin(SpellRender.class)
public class SpellRenderMixin {
    @Inject(at = @At("HEAD"), method = "iconTexture", cancellable = true)
    private static void iconTextureReplaceSpellblade(Identifier spellId, CallbackInfoReturnable<Identifier> identifier) {
        if(MinecraftClient.getInstance() != null) {

            PlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null && SpellContainerHelper.getEquipped(player.getMainHandStack(),player) != null && SpellContainerHelper.getEquipped(player.getMainHandStack(), player).spell_ids() != null && SpellContainerHelper.getEquipped(player.getMainHandStack(), player).spell_ids().contains("spellbladenext:echoes")) {

                if (spellId.getPath().equals("echoes")) {
                    if(player.getStatusEffect(SpellbladesAndSuch.UNLEASH) != null) {
                        identifier.setReturnValue(Identifier.of(MOD_ID, "textures/spell/" + "echoes" + (player.getStatusEffect(SpellbladesAndSuch.UNLEASH).getAmplifier()+1) + ".png"));
                    }
                }

            }
            if (spellId.getPath().equals("spellstrike")) {
                if (player.getStatusEffect(SpellbladesAndSuch.SPELLSTRIKE) != null) {
                    double arcane = SpellPower.getSpellPower(SpellSchools.ARCANE, player).baseValue();
                    double fire = SpellPower.getSpellPower(SpellSchools.FIRE, player).baseValue();
                    double frost = SpellPower.getSpellPower(SpellSchools.FROST, player).baseValue();
                    if (arcane > fire && arcane > frost) {
                        identifier.setReturnValue(Identifier.of(MOD_ID, "textures/spell/" + "gleamingblade.png"));

                    }
                    if (frost > fire && frost > arcane) {
                        identifier.setReturnValue(Identifier.of(MOD_ID, "textures/spell/" + "frozenblade.png"));

                    }
                    if (fire > arcane && fire > frost) {
                        identifier.setReturnValue(Identifier.of(MOD_ID, "textures/spell/" + "searingblade.png"));

                    }
                }
            }
        }
    }
}
