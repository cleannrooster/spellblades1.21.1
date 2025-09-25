package com.cleannrooster.spellbladenext;

import com.cleannrooster.spellbladenext.SpellbladesAndSuch;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class SpellbladesAndSuchFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SpellbladesClient.onInitializeClient();
    }
}
