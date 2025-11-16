package com.cleannrooster.spellblades.mixin;

import net.spell_engine.spellbinding.SpellBindingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SpellBindingScreenHandler.class)
public class SpellBindingScreenHandlerMixin {
    @Shadow
    public static int MAXIMUM_SPELL_COUNT;

    static{
        MAXIMUM_SPELL_COUNT = 40;
    }

}
