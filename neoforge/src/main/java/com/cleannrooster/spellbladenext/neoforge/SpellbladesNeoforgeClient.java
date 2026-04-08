package com.cleannrooster.spellbladenext.neoforge;

import com.cleannrooster.spellblades.SpellbladesClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = "spellbladenext", value = Dist.CLIENT)
public final class SpellbladesNeoforgeClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        SpellbladesClient.onInitializeClient();
    }
}
