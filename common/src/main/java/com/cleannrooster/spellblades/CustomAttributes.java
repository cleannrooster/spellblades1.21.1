package com.cleannrooster.spellblades;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class CustomAttributes {
    public static RegistryEntry.Reference<EntityAttribute> EPHEMERAL;


    public static void run(){
        if(EPHEMERAL == null)
            EPHEMERAL = Registry.registerReference(Registries.ATTRIBUTE, Identifier.of("spellbladenext", "ephemeral"), new ClampedEntityAttribute("attribute.name.spellbladenext.ephemeral", 100,100,9999));

    }
}
