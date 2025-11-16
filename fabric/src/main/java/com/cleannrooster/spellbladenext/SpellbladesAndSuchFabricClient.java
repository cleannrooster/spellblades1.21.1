package com.cleannrooster.spellbladenext;

import com.cleannrooster.spellblades.SpellbladesClient;
import net.fabricmc.api.ClientModInitializer;

public class SpellbladesAndSuchFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SpellbladesClient.onInitializeClient();
    }
}
