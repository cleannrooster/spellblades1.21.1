package com.cleannrooster.spellblades;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import net.spell_power.internals.CustomEntityAttribute;
import net.spell_power.mixin.EntityAttributesMixin;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;

public class CustomAttributes {
    public static RegistryEntry.Reference<EntityAttribute> EPHEMERAL;

    public static void register(){
        EPHEMERAL = Registry.registerReference(Registries.ATTRIBUTE, Identifier.of("spellbladenext", "ephemeral"), new ClampedEntityAttribute("attribute.name.spellbladenext.ephemeral", 100,100,9999));

    }
}
