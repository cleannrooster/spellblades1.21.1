package com.cleannrooster.spellbladenext.neoforge;

import com.cleannrooster.spellbladenext.ExampleMod;
import com.cleannrooster.spellbladenext.SpellbladesClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import static com.cleannrooster.spellbladenext.SpellbladesAndSuch.MOD_ID;

@EventBusSubscriber(modid = "spellbladenext", value = Dist.CLIENT)
public final class SpellbladesNeoforgeClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Run our common setup.
        SpellbladesClient.onInitializeClient();
        ExampleMod.init();
    }
}
