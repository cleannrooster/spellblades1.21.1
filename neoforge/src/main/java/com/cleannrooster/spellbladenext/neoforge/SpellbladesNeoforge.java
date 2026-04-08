package com.cleannrooster.spellbladenext.neoforge;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod("spellbladenext")
public final class SpellbladesNeoforge {
    public SpellbladesNeoforge(IEventBus modBus) {
        SpellbladesAndSuch.onInitialize();
        modBus.addListener(RegisterEvent.class, SpellbladesNeoforge::register);
    }

    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.ATTRIBUTE, reg -> {
            SpellbladesAndSuch.registerAttributes();
        });
        event.register(RegistryKeys.ITEM, reg -> {
            SpellbladesAndSuch.registerItems();
        });
        event.register(RegistryKeys.STATUS_EFFECT, reg -> {
            SpellbladesAndSuch.registerEffects();
        });
    }
}
