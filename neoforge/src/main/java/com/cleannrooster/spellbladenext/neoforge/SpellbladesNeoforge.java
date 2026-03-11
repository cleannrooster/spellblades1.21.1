package com.cleannrooster.spellbladenext.neoforge;

import com.cleannrooster.spellblades.CustomAttributes;
import com.cleannrooster.spellblades.SpellbladesAndSuch;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import com.cleannrooster.spellblades.ExampleMod;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod("spellbladenext")
public final class SpellbladesNeoforge {
    public SpellbladesNeoforge(IEventBus modBus) {
        // Run our common setup.
        SpellbladesAndSuch.onInitialize();
        modBus.addListener(RegisterEvent.class, SpellbladesNeoforge::register);

        ExampleMod.init();
    }
    @SubscribeEvent // on the mod event bus
    public static void modifyDefaultAttributes(EntityAttributeModificationEvent event) {
        event.add(
                EntityType.PLAYER,
                CustomAttributes.EPHEMERAL
        );

    }
    public static void register(RegisterEvent event) {


        event.register(RegistryKeys.ITEM, reg ->{
            SpellbladesAndSuch.registerItems();

        });
        event.register(RegistryKeys.ATTRIBUTE, reg ->{
            SpellbladesAndSuch.registerAttributes();

        });

        event.register(RegistryKeys.STATUS_EFFECT, reg ->{
            SpellbladesAndSuch.registerEffects();

        });



    }
}
