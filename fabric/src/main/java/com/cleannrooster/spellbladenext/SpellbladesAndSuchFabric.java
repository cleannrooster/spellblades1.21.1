package com.cleannrooster.spellbladenext;

import com.cleannrooster.spellbladenext.SpellbladesAndSuch;

import net.fabricmc.api.ModInitializer;

public class SpellbladesAndSuchFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SpellbladesAndSuch.onInitialize();
        SpellbladesAndSuch.registerItems();
        SpellbladesAndSuch.registerEffects();

    }
}
