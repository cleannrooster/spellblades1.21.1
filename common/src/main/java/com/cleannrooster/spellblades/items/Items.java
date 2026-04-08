package com.cleannrooster.spellblades.items;

import com.cleannrooster.spellblades.SpellbladesAndSuch;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.spell_engine.api.config.AttributeModifier;
import net.spell_engine.api.config.WeaponConfig;
import net.spell_engine.rpg_series.item.Equipment;
import net.spell_engine.rpg_series.item.Weapon;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

public class Items {
    public static final ArrayList<Weapon.Entry> entries = new ArrayList<>();



    private static Weapon.Entry entry(String requiredMod, String name, Weapon.CustomMaterial material, Weapon.Factory item, WeaponConfig defaults, Equipment.WeaponType type) {
        var entry = new Weapon.Entry(SpellbladesAndSuch.MOD_ID, name, material, item, defaults, type);
        if (entry.isRequiredModInstalled()) {
            entries.add(entry);
        }
        return entry;
    }

    private static Supplier<Ingredient> ingredient(String idString) {
        return ingredient(idString, net.minecraft.item.Items.DIAMOND);
    }

    private static Supplier<Ingredient> ingredient(String idString, Item fallback) {
        var id = Identifier.of(idString);
        return () -> {
            var item = Registries.ITEM.get(id);
            var ingredient = item != null ? item : fallback;
            return Ingredient.ofItems(ingredient);
        };
    }

    private static final float bladeValue = 3.5F;
    private static final float bladeDamage = 2;
    private static final float claymoreDamage = 5.5F;
    private static final float bladeSpeed = -3;
    private static final float claymoreSpeed = -3;

    private static Weapon.Entry blade(String name, Weapon.CustomMaterial material, float damage, SpellSchool school) {
        return blade(null, name, material, damage, school );
    }


    private static Weapon.Entry blade(String requiredMod, String name, Weapon.CustomMaterial material, float damage, SpellSchool school) {
        var settings = new Item.Settings();
        return entry(requiredMod, name, material, Spellblade::new, new WeaponConfig(damage, -2.4F), Equipment.WeaponType.SPELL_BLADE);
    }

    public static final Weapon.Entry frost_blade = blade("frost_blade",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(net.minecraft.item.Items.PRISMARINE_SHARD)), 3F, SpellSchools.FROST)
            .attribute(AttributeModifier.bonus((SpellSchools.FROST).id, bladeValue))
            .attribute(AttributeModifier.multiply(Identifier.of("extraspellattributes","converttofrost"), 0.2F))
            .loot(Equipment.LootProperties.of(2));
    public static final Weapon.Entry fire_blade = blade("fire_blade",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(net.minecraft.item.Items.BLAZE_ROD)), 3F,SpellSchools.FIRE)
            .attribute(AttributeModifier.bonus((SpellSchools.FIRE).id, bladeValue))
            .attribute(AttributeModifier.multiply(Identifier.of("extraspellattributes","converttofire"), 0.2F))
            .loot(Equipment.LootProperties.of(2));
    public static final Weapon.Entry arcane_blade = blade("arcane_blade",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(net.minecraft.item.Items.AMETHYST_SHARD)), 3F,SpellSchools.ARCANE)
            .attribute(AttributeModifier.bonus((SpellSchools.ARCANE).id, bladeValue))
            .attribute(AttributeModifier.multiply(Identifier.of("extraspellattributes","converttoarcane"), 0.2F))
            .loot(Equipment.LootProperties.of(2));
    public static final Weapon.Entry glacial_gladius = blade("glacial_gladius",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEFROST)), 5F,SpellSchools.FROST)
            .attribute(AttributeModifier.bonus((SpellSchools.FROST).id, 4.5F))
            .attribute(AttributeModifier.multiply(Identifier.of("extraspellattributes","converttofrost"), 0.2F))
            .loot(Equipment.LootProperties.of(3));
    public static final Weapon.Entry flaming_falchion = blade("flaming_falchion",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEBLAZE)), 5F,SpellSchools.FIRE)
            .attribute(AttributeModifier.bonus((SpellSchools.FIRE).id, 4.5F))
            .attribute(AttributeModifier.multiply(Identifier.of("extraspellattributes","converttofire"), 0.2F))
            .loot(Equipment.LootProperties.of(3));
    public static final Weapon.Entry crystal_cutlass = blade("crystal_cutlass",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEGLEAM)), 5F,SpellSchools.ARCANE)
            .attribute(AttributeModifier.bonus((SpellSchools.ARCANE).id, 4.5F))
            .attribute(AttributeModifier.multiply(Identifier.of("extraspellattributes","converttoarcane"), 0.2F))
            .loot(Equipment.LootProperties.of(3));
    public static final Weapon.Entry ephemeral_edge = blade("ephemeral_edge",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(net.minecraft.item.Items.BREEZE_ROD)), 5F,SpellSchools.LIGHTNING)
            .attribute(AttributeModifier.bonus(SpellSchools.LIGHTNING.id, 4.5F))
            .attribute(AttributeModifier.multiply(Identifier.of(SpellbladesAndSuch.MOD_ID,"ephemeral"), 0.5F))
            .loot(Equipment.LootProperties.of(3));


    private static Weapon.Entry claymore(String name, Weapon.CustomMaterial material, float damage, SpellSchool school) {
        return claymore(null, name, material, damage, school );
    }
    private static Weapon.Entry claymore(String requiredMod, String name, Weapon.CustomMaterial material, float damage,SpellSchool school) {
        var settings = new Item.Settings();
        return entry(requiredMod, name, material, Spellblade::new, new WeaponConfig(damage, -3F), Equipment.WeaponType.SPELL_BLADE);
    }
    public static final Weapon.Entry frost_claymore = claymore("frost_claymore",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(net.minecraft.item.Items.PRISMARINE_SHARD)), 6F,SpellSchools.FROST)
            .attribute(AttributeModifier.bonus((SpellSchools.FROST).id, claymoreDamage))
            .attribute(AttributeModifier.multiply(Identifier.of("extraspellattributes","converttofrost"), 0.2F))
            .loot(Equipment.LootProperties.of(3));
    public static final Weapon.Entry fire_claymore = claymore("fire_claymore",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(net.minecraft.item.Items.BLAZE_ROD)), 6F,SpellSchools.FIRE)
            .attribute(AttributeModifier.bonus((SpellSchools.FIRE).id, claymoreDamage))
            .attribute(AttributeModifier.multiply(Identifier.of("extraspellattributes","converttofire"), 0.2F))
            .loot(Equipment.LootProperties.of(3));
    public static final Weapon.Entry arcane_claymore = claymore("arcane_claymore",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(net.minecraft.item.Items.AMETHYST_SHARD)), 6F,SpellSchools.ARCANE)
            .attribute(AttributeModifier.bonus((SpellSchools.ARCANE).id, claymoreDamage))
            .attribute(AttributeModifier.multiply(Identifier.of("extraspellattributes","converttoarcane"), 0.2F))
            .loot(Equipment.LootProperties.of(3));


    public static void register(Map<String, WeaponConfig> configs) {
        Weapon.register(configs, entries, RegistryKey.of(Registries.ITEM_GROUP.getKey(),Identifier.of(SpellbladesAndSuch.MOD_ID,"generic")));
    }
}

