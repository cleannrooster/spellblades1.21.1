package com.cleannrooster.spellbladenext;

import com.cleannrooster.spellblades.SpellbladesAndSuch;

import net.fabricmc.api.ModInitializer;

public class SpellbladesAndSuchFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SpellbladesAndSuch.onInitialize();
        SpellbladesAndSuch.registerItems();
        SpellbladesAndSuch.registerAttributes();
        SpellbladesAndSuch.registerEffects();

    }
}
