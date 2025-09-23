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
import net.spell_engine.api.item.Equipment;
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
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEBLAZE));
    public static RegistryEntry<ArmorMaterial> material_templaGuardian = material(
            "frosttemplar",
            2, 6, 4, 2,
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEFROST));
    public static RegistryEntry<ArmorMaterial> material_endGuardian = material(
            "arcanetemplar",
            2, 6, 4, 2,
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEGLEAM));

    public static RegistryEntry<ArmorMaterial> material_wizard = material(
            "wizard_robe",
            1, 3, 2, 1,
            9,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEGLEAM));
    public static RegistryEntry<ArmorMaterial> material_netherite = material(
            "netherite",
            1, 3, 2, 1,
            30,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,  () -> Ingredient.ofItems(Items.NETHERITE_INGOT));

    public static RegistryEntry<ArmorMaterial> material_arcane = material(
            "runegleam",
            1, 3, 2, 1,
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,  () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEGLEAM));

    public static RegistryEntry<ArmorMaterial> material_fire = material(
            "runeblaze",
            1, 3, 2, 1,
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,  () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEBLAZE));

    public static RegistryEntry<ArmorMaterial> material_frost = material(
            "runefrost",
            1, 3, 2, 1,
            20,
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,  () -> Ingredient.ofItems(SpellbladesAndSuch.RUNEFROST));

    public static final ArrayList<Armor.Entry> entries = new ArrayList<>();
    private static Armor.Entry create(RegistryEntry<ArmorMaterial> material, Identifier id, int durability, Armor.Set.ItemFactory factory, ArmorSetConfig defaults,int tier) {
        var entry = Armor.Entry.create(
                material,
                id,
                durability,
                factory,
                defaults,
                Equipment.LootProperties.of(tier));
        entries.add(entry);
        return entry;
    }


    private static final float specializedRobeSpellPower = 0.25F;
    private static final float specializedRobeCritDamage = 0.1F;
    private static final float specializedRobeCritChance = 0.02F;
    private static final float specializedRobeHaste = 0.03F;



    public static final Armor.Set arcane = create(
            material_arcane,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "runegleam"),
            20,
            (material,slot,setting) ->new RunicArmor(material,slot,setting,SpellSchools.ARCANE),
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
            ),2)
            .armorSet();

    public static final Armor.Set fire = create(
            material_fire,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "runeblaze"),
            20,
            (material,slot,setting) ->new RunicArmor(material,slot,setting,SpellSchools.FIRE),
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
            ),2)
            .armorSet();

    public static final Armor.Set frost = create(
            material_frost,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "runefrost"),
            20,
            (material,slot,setting) ->new RunicArmor(material,slot,setting,SpellSchools.FROST),
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
            ),2)
            .armorSet();
    public static final Armor.Set arcaneNetherite = create(
            material_netherite,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "runegleam_netherite"),
            30,
            (material,slot,setting) ->new RunicArmor(material,slot,setting,SpellSchools.ARCANE),
            ArmorSetConfig.with(
                    new ArmorSetConfig.Piece(2)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.ARCANE.id, 0.20F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.04F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2.5F)
                            )),
                    new ArmorSetConfig.Piece(5)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.ARCANE.id, 0.20F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.04F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2.5F)
                            )),
                    new ArmorSetConfig.Piece(4)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.ARCANE.id, 0.20F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.04F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2.5F)
                            )),
                    new ArmorSetConfig.Piece(2)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.ARCANE.id, 0.20F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.04F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2.5F)
                            ))
            ),3)
            .armorSet();

    public static final Armor.Set fireNetherite = create(
            material_netherite,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "runeblaze_netherite"),
            30,
            (material,slot,setting) ->new RunicArmor(material,slot,setting,SpellSchools.FIRE),
            ArmorSetConfig.with(
                    new ArmorSetConfig.Piece(2)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FIRE.id, 0.2F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.04F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2.5F)
                            )),
                    new ArmorSetConfig.Piece(5)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FIRE.id, 0.2F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.04F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2.5F)
                            )),
                    new ArmorSetConfig.Piece(4)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FIRE.id, 0.2F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.04F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2.5F)
                            )),
                    new ArmorSetConfig.Piece(2)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FIRE.id, 0.2F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.04F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2.5F)
                            ))
            ),3)
            .armorSet();

    public static final Armor.Set frostNetherite = create(
            material_netherite,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "runefrost_netherite"),
            30,
            (material,slot,setting) ->new RunicArmor(material,slot,setting,SpellSchools.FROST),
            ArmorSetConfig.with(
                    new ArmorSetConfig.Piece(2)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FROST.id, 0.2F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.04F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2.5F)
                            )),
                    new ArmorSetConfig.Piece(5)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FROST.id, 0.2F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.04F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2.5F)
                            )),
                    new ArmorSetConfig.Piece(4)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FROST.id, 0.2F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.04F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2.5F)
                            )),
                    new ArmorSetConfig.Piece(2)
                            .addAll(List.of(
                                    AttributeModifier.multiply(SpellSchools.FROST.id, 0.2F),
                                    AttributeModifier.multiply(Identifier.of(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString()),0.043F),
                                    AttributeModifier.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 2.5F)
                            ))
            ),3)
            .armorSet();


    public static void register(Map<String, ArmorSetConfig> configs) {
        Armor.register(configs, entries, SpellbladesAndSuch.KEY);
    }
}