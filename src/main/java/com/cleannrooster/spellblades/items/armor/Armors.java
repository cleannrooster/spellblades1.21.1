package com.cleannrooster.spellblades.items.armor;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.extraspellattributes.ReabsorptionInit;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.spell_engine.api.config.ArmorSetConfig;
import net.spell_engine.api.config.AttributeModifier;
import net.spell_engine.api.item.armor.Armor;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Armors {
    private static final Supplier<Ingredient> WOOL_INGREDIENTS = () -> { return Ingredient.ofItems(
            Items.WHITE_WOOL,
            Items.ORANGE_WOOL,
            Items.MAGENTA_WOOL,
            Items.LIGHT_BLUE_WOOL,
            Items.YELLOW_WOOL,
            Items.LIME_WOOL,
            Items.PINK_WOOL,
            Items.GRAY_WOOL,
            Items.LIGHT_GRAY_WOOL,
            Items.CYAN_WOOL,
            Items.PURPLE_WOOL,
            Items.BLUE_WOOL,
            Items.BROWN_WOOL,
            Items.GREEN_WOOL,
            Items.RED_WOOL,
            Items.BLACK_WOOL);
    };

    public static RegistryEntry<ArmorMaterial> material(String name,
                                                        int protectionHead, int protectionChest, int protectionLegs, int protectionFeet,
                                                        int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient) {
        var material = new ArmorMaterial(
                Map.of(
                        ArmorItem.Type.HELMET, protectionHead,
                        ArmorItem.Type.CHESTPLATE, protectionChest,
                        ArmorItem.Type.LEGGINGS, protectionLegs,
                        ArmorItem.Type.BOOTS, protectionFeet),
                enchantability, equipSound, repairIngredient,
                List.of(new ArmorMaterial.Layer(Identifier.of(SpellbladesAndSuch.MOD_ID, name))),
                0,0
        );
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(SpellbladesAndSuch.MOD_ID, name), material);
    }
    public static RegistryEntry<ArmorMaterial> material_templar = material(
            "firetemplar",
            2, 6, 4, 2,
            10,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEBLAZE));
    public static RegistryEntry<ArmorMaterial> material_templaGuardian = material(
            "frosttemplar",
            2, 6, 4, 2,
            10,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEFROST));
    public static RegistryEntry<ArmorMaterial> material_endGuardian = material(
            "arcanetemplar",
            2, 6, 4, 2,
            10,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEGLEAM));

    public static RegistryEntry<ArmorMaterial> material_wizard = material(
            "wizard_robe",
            1, 3, 2, 1,
            9,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEGLEAM));

    public static RegistryEntry<ArmorMaterial> material_arcane = material(
            "runegleam",
            1, 3, 2, 1,
            10,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,  () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEGLEAM));

    public static RegistryEntry<ArmorMaterial> material_fire = material(
            "runeblaze",
            1, 3, 2, 1,
            10,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,  () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEBLAZE));

    public static RegistryEntry<ArmorMaterial> material_frost = material(
            "runefrost",
            1, 3, 2, 1,
            10,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,  () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEFROST));

    public static final ArrayList<Armor.Entry> entries = new ArrayList<>();
    private static Armor.Entry create(RegistryEntry<ArmorMaterial> material, Identifier id, int durability, Armor.Set.ItemFactory factory, ArmorSetConfig defaults) {
        var entry = Armor.Entry.create(
                material,
                id,
                durability,
                factory,
                defaults);
        entries.add(entry);
        return entry;
    }


    private static final float specializedRobeSpellPower = 0.25F;
    private static final float specializedRobeCritDamage = 0.1F;
    private static final float specializedRobeCritChance = 0.02F;
    private static final float specializedRobeHaste = 0.03F;

    public static final Armor.Set bastion = create(
            material_templar,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "bastion_guardian"),
            30,
            (material, type, setting) -> {return new TemplarArmor(material,type,setting,SpellSchools.FIRE);},
            ArmorSetConfig.with(
                    new ArmorSetConfig.Piece(3)
                            .addAll(List.of(
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2F),
                                    AttributeModifier.multiply(Identifier.of(ReabsorptionInit.MOD_ID,"recoupabsorb"), 0.05F),
                                    AttributeModifier.multiply(SpellSchools.FIRE.id, 0.2F),
                                    AttributeModifier.bonus(SpellSchools.HEALING.id, 1F)

                                    )),
                    new ArmorSetConfig.Piece(8)
                            .addAll(List.of(
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2F),
                                    AttributeModifier.multiply(Identifier.of(ReabsorptionInit.MOD_ID,"recoupabsorb"), 0.05F),
                                    AttributeModifier.multiply(SpellSchools.FIRE.id, 0.2F),
                                    AttributeModifier.bonus(SpellSchools.HEALING.id, 1F)
                            )),
                    new ArmorSetConfig.Piece(6)
                            .addAll(List.of(
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2F),
                                    AttributeModifier.multiply(Identifier.of(ReabsorptionInit.MOD_ID,"recoupabsorb"), 0.05F),
                                    AttributeModifier.multiply(SpellSchools.FIRE.id, 0.2F),
                                    AttributeModifier.bonus(SpellSchools.HEALING.id, 1F)
                            )),
                    new ArmorSetConfig.Piece(3)
                            .addAll(List.of(
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2F),
                                    AttributeModifier.multiply(Identifier.of(ReabsorptionInit.MOD_ID,"recoupabsorb"), 0.05F),
                                    AttributeModifier.multiply(SpellSchools.FIRE.id, 0.2F),
                                    AttributeModifier.bonus(SpellSchools.HEALING.id, 1F)
                            ))
            ))
            .armorSet();
    public static final Armor.Set endcity = create(
            material_endGuardian,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "end_city_guardian"),
            30,
            (material, type, setting) -> {return new TemplarArmor(material,type,setting,SpellSchools.ARCANE);},
            ArmorSetConfig.with(
                    new ArmorSetConfig.Piece(3)
                            .addAll(List.of(
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2F),
                                    AttributeModifier.multiply(Identifier.of(ReabsorptionInit.MOD_ID,"recoupabsorb"), 0.05F),
                                    AttributeModifier.multiply(SpellSchools.ARCANE.id, 0.2F),
                                    AttributeModifier.bonus(SpellSchools.HEALING.id, 1F)

                            )),
                    new ArmorSetConfig.Piece(8)
                            .addAll(List.of(
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2F),
                                    AttributeModifier.multiply(Identifier.of(ReabsorptionInit.MOD_ID,"recoupabsorb"), 0.05F),
                                    AttributeModifier.multiply(SpellSchools.ARCANE.id, 0.2F),
                                    AttributeModifier.bonus(SpellSchools.HEALING.id, 1F)
                            )),
                    new ArmorSetConfig.Piece(6)
                            .addAll(List.of(
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2F),
                                    AttributeModifier.multiply(Identifier.of(ReabsorptionInit.MOD_ID,"recoupabsorb"), 0.05F),
                                    AttributeModifier.multiply(SpellSchools.ARCANE.id, 0.2F),
                                    AttributeModifier.bonus(SpellSchools.HEALING.id, 1F)
                            )),
                    new ArmorSetConfig.Piece(3)
                            .addAll(List.of(
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2F),
                                    AttributeModifier.multiply(Identifier.of(ReabsorptionInit.MOD_ID,"recoupabsorb"), 0.05F),
                                    AttributeModifier.multiply(SpellSchools.ARCANE.id, 0.2F),
                                    AttributeModifier.bonus(SpellSchools.HEALING.id, 1F)
                            ))
            ))
            .armorSet();
    public static final Armor.Set valkyrie = create(
            material_templaGuardian,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "temple_guardian"),
            30,
            (material, type, setting) -> {return new TemplarArmor(material,type,setting,SpellSchools.FROST);},
            ArmorSetConfig.with(
                    new ArmorSetConfig.Piece(3)
                            .addAll(List.of(
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2F),
                                    AttributeModifier.multiply(Identifier.of(ReabsorptionInit.MOD_ID,"recoupabsorb"), 0.05F),
                                    AttributeModifier.multiply(SpellSchools.FROST.id, 0.2F),
                                    AttributeModifier.bonus(SpellSchools.HEALING.id, 1F)

                            )),
                    new ArmorSetConfig.Piece(8)
                            .addAll(List.of(
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2F),
                                    AttributeModifier.multiply(Identifier.of(ReabsorptionInit.MOD_ID,"recoupabsorb"), 0.05F),
                                    AttributeModifier.multiply(SpellSchools.FROST.id, 0.2F),
                                    AttributeModifier.bonus(SpellSchools.HEALING.id, 1F)
                            )),
                    new ArmorSetConfig.Piece(6)
                            .addAll(List.of(
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2F),
                                    AttributeModifier.multiply(Identifier.of(ReabsorptionInit.MOD_ID,"recoupabsorb"), 0.05F),
                                    AttributeModifier.multiply(SpellSchools.FROST.id, 0.2F),
                                    AttributeModifier.bonus(SpellSchools.HEALING.id, 1F)
                            )),
                    new ArmorSetConfig.Piece(3)
                            .addAll(List.of(
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2F),
                                    AttributeModifier.multiply(Identifier.of(ReabsorptionInit.MOD_ID,"recoupabsorb"), 0.05F),
                                    AttributeModifier.multiply(SpellSchools.FROST.id, 0.2F),
                                    AttributeModifier.bonus(SpellSchools.HEALING.id, 1F)
                            ))
            ))
            .armorSet();


    public static final Armor.Set arcane = create(
            material_arcane,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "runegleam"),
            20,
            RunicArmor::new,
            ArmorSetConfig.with(
                    new ArmorSetConfig.Piece(2)
                            .addAll(List.of(
                                   AttributeModifier.multiply(SpellSchools.ARCANE.id, 0.15F),
                                   AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.03F),
                                   AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 1.5F)
                            )),
                    new ArmorSetConfig.Piece(5)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.ARCANE.id, 0.15F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.03F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 1.5F)
                            )),
                    new ArmorSetConfig.Piece(4)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.ARCANE.id, 0.15F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.03F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 1.5F)
                            )),
                    new ArmorSetConfig.Piece(2)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.ARCANE.id, 0.15F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.03F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 1.5F)
                            ))
            ))
            .armorSet();

    public static final Armor.Set fire = create(
            material_fire,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "runeblaze"),
            20,
            RunicArmor::new,
            ArmorSetConfig.with(
                    new ArmorSetConfig.Piece(2)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FIRE.id, 0.15F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.03F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 1.5F)
                            )),
                    new ArmorSetConfig.Piece(5)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FIRE.id, 0.15F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.03F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 1.5F)
                            )),
                    new ArmorSetConfig.Piece(4)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FIRE.id, 0.15F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.03F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 1.5F)
                            )),
                    new ArmorSetConfig.Piece(2)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FIRE.id, 0.15F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.03F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 1.5F)
                            ))
            ))
            .armorSet();

    public static final Armor.Set frost = create(
            material_frost,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "runefrost"),
            20,
            RunicArmor::new,
            ArmorSetConfig.with(
                    new ArmorSetConfig.Piece(2)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FROST.id, 0.15F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.03F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 1.5F)
                            )),
                    new ArmorSetConfig.Piece(5)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FROST.id, 0.15F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.03F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 1.5F)
                            )),
                    new ArmorSetConfig.Piece(4)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FROST.id, 0.15F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.03F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 1.5F)
                            )),
                    new ArmorSetConfig.Piece(2)
                            .addAll(List.of(
                                   AttributeModifier.multiply(SpellSchools.FROST.id, 0.15F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.03F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 1.5F)
                            ))
            ))
            .armorSet();

    public static void register(Map<String, ArmorSetConfig> configs) {
        Armor.register(configs, entries, SpellbladesAndSuch.KEY);
    }
}