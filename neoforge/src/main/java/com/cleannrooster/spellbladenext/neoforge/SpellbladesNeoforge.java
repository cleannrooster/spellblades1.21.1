package com.cleannrooster.spellbladenext.neoforge;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import com.cleannrooster.spellblades.ExampleMod;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod("spellbladenext")
public final class SpellbladesNeoforge {
    public SpellbladesNeoforge(IEventBus modBus) {
        // Run our common setup.
        SpellbladesAndSuch.onInitialize();
        modBus.addListener(RegisterEvent.class, SpellbladesNeoforge::register);

        ExampleMod.init();
    }
    public static void register(RegisterEvent event) {


        event.register(RegistryKeys.ITEM, reg ->{
            SpellbladesAndSuch.registerItems();

        });
        event.register(RegistryKeys.STATUS_EFFECT, reg ->{
            SpellbladesAndSuch.registerEffects();

        });



    }
}
