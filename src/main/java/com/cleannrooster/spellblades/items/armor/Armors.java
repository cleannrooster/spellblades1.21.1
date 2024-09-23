package com.cleannrooster.spellblades.items.armor;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.extraspellattributes.ReabsorptionInit;
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
import net.spell_engine.api.item.ItemConfig;
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
    private static Armor.Entry create(RegistryEntry<ArmorMaterial> material, Identifier id, int durability, Armor.Set.ItemFactory factory, ItemConfig.ArmorSet defaults) {
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

    public static final Armor.Set arcane = create(
            material_arcane,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "runegleam"),
            20,
            RunicArmor::new,
            ItemConfig.ArmorSet.with(
                    new ItemConfig.ArmorSet.Piece(1)
                            .addAll(List.of(
                                    ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 1),
                                    ItemConfig.Attribute.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 4)
                            )),
                    new ItemConfig.ArmorSet.Piece(3)
                            .addAll(List.of(
                                    ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 1),
                                    ItemConfig.Attribute.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 4)
                            )),
                    new ItemConfig.ArmorSet.Piece(2)
                            .addAll(List.of(
                                    ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 1),
                                    ItemConfig.Attribute.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 4)
                            )),
                    new ItemConfig.ArmorSet.Piece(1)
                            .addAll(List.of(
                                    ItemConfig.Attribute.bonus(SpellSchools.ARCANE.id, 1),
                                    ItemConfig.Attribute.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 4)
                            ))
            ))
            .armorSet();

    public static final Armor.Set fire = create(
            material_fire,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "runeblaze"),
            20,
            RunicArmor::new,
            ItemConfig.ArmorSet.with(
                    new ItemConfig.ArmorSet.Piece(1)
                            .addAll(List.of(
                                    ItemConfig.Attribute.bonus(SpellSchools.FIRE.id, 1),
                                    ItemConfig.Attribute.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 4)
                            )),
                    new ItemConfig.ArmorSet.Piece(3)
                            .addAll(List.of(
                                    ItemConfig.Attribute.bonus(SpellSchools.FIRE.id, 1),
                                    ItemConfig.Attribute.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 4)
                            )),
                    new ItemConfig.ArmorSet.Piece(2)
                            .addAll(List.of(
                                    ItemConfig.Attribute.bonus(SpellSchools.FIRE.id, 1),
                                    ItemConfig.Attribute.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 4)
                            )),
                    new ItemConfig.ArmorSet.Piece(1)
                            .addAll(List.of(
                                    ItemConfig.Attribute.bonus(SpellSchools.FIRE.id, 1),
                                    ItemConfig.Attribute.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 4)
                            ))
            ))
            .armorSet();

    public static final Armor.Set frost = create(
            material_frost,
            Identifier.of(SpellbladesAndSuch.MOD_ID, "runefrost"),
            20,
            RunicArmor::new,
            ItemConfig.ArmorSet.with(
                    new ItemConfig.ArmorSet.Piece(1)
                            .addAll(List.of(
                                    ItemConfig.Attribute.bonus(SpellSchools.FROST.id, 1),
                                    ItemConfig.Attribute.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 4)
                            )),
                    new ItemConfig.ArmorSet.Piece(3)
                            .addAll(List.of(
                                    ItemConfig.Attribute.bonus(SpellSchools.FROST.id, 1),
                                    ItemConfig.Attribute.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 4)
                            )),
                    new ItemConfig.ArmorSet.Piece(2)
                            .addAll(List.of(
                                    ItemConfig.Attribute.bonus(SpellSchools.FROST.id, 1),
                                    ItemConfig.Attribute.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 4)
                            )),
                    new ItemConfig.ArmorSet.Piece(1)
                            .addAll(List.of(
                                    ItemConfig.Attribute.bonus(SpellSchools.FROST.id, 1),
                                    ItemConfig.Attribute.bonus(Identifier.of(ReabsorptionInit.MOD_ID,"reabsorption"), 4)
                            ))
            ))
            .armorSet();

    public static void register(Map<String, ItemConfig.ArmorSet> configs) {
        Armor.register(configs, entries, SpellbladesAndSuch.KEY);
    }
}