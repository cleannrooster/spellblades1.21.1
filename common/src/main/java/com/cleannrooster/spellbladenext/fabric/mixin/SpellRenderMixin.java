package com.cleannrooster.spellbladenext.fabric.mixin;

import net.spell_engine.client.util.SpellRender;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(SpellRender.class)
public class SpellRenderMixin {
   /* @Inject(at = @At("HEAD"), method = "iconTexture", cancellable = true)
    private static void iconTextureReplaceSpellblade(Identifier spellId, CallbackInfoReturnable<Identifier> identifier) {
        if(MinecraftClient.getInstance() != null) {

            PlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null && SpellContainerHelper.getAvailable(player) != null && SpellContainerHelper.getAvailable( player).spell_ids() != null && SpellContainerHelper.getAvailable( player).spell_ids().contains("spellbladenext:echoes")) {

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
                        identifier.setReturnValue(Identifier.of(MOD_ID, "textures/spell/" + "arcane_spellstrike.png"));

                    }
                    if (frost > fire && frost > arcane) {
                        identifier.setReturnValue(Identifier.of(MOD_ID, "textures/spell/" + "frost_spellstrike.png"));

                    }
                    if (fire > arcane && fire > frost) {
                        identifier.setReturnValue(Identifier.of(MOD_ID, "textures/spell/" + "fire_spellstrike.png"));

                    }
                }
            }
        }
    }*/
}
