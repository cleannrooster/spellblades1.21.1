package com.cleannrooster.spellblades.items.loot;

import com.cleannrooster.spellblades.items.Items;
import com.cleannrooster.spellblades.items.armor.Armors;
import net.spell_engine.api.item.ItemConfig;
import net.spell_engine.api.loot.LootConfig;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Default {
    public final static ItemConfig itemConfig;
    public final static LootConfig lootConfig;
    static {
        itemConfig = new ItemConfig();
        for (var weapon : Items.entries) {
            itemConfig.weapons.put(weapon.name(), weapon.defaults());
        }
        for (var armor : Armors.entries) {
            itemConfig.armor_sets.put(armor.name(), armor.defaults());
        }


        lootConfig = new LootConfig();

        LootConfig.Pool poolWeapons = new LootConfig.Pool();
        final var weapons = "weapons";
/*        lootConfig.injectors.put(weapons, new LootConfig.ItemGroup(List.of(
                Items.frost_blade.id().toString(),
                Items.glacial_gladius.id().toString(),
                Items.frost_claymore.id().toString(),
                Items.frost_orb.id().toString(),
                Items.arcane_blade.id().toString(),
                Items.crystal_cutlass.id().toString(),
                Items.arcane_claymore.id().toString(),
                Items.arcane_orb.id().toString(),
                Items.fire_blade.id().toString(),
                Items.flaming_falchion.id().toString(),
                Items.fire_claymore.id().toString(),
                Items.fire_orb.id().toString(),
                "spellblades:whirlwindoil",
                "spellblades:spelloil",
                "spellblades:smiteoil",
                "spellblades:monkeystaff"



        ),*/

       /* ).chance(0.5F).enchant());
        List.of("minecraft:chests/ruined_portal")
                .forEach(id -> lootConfig..put(id, List.of(weapons)));

        List.of("minecraft:chests/spawn_bonus_chest",
                        "minecraft:chests/igloo_chest",
                        "minecraft:chests/shipwreck_supply",
                        "minecraft:chests/jungle_temple")
                .forEach(id -> lootConfig.loot_tables.put(id, List.of(weapons)));

        List.of("minecraft:chests/desert_pyramid",
                        "minecraft:chests/bastion_bridge",
                        "minecraft:chests/jungle_temple",
                        "minecraft:chests/pillager_outpost",
                        "minecraft:chests/underwater_ruin_small",
                        "minecraft:chests/stronghold_crossing")
                .forEach(id -> lootConfig.loot_tables.put(id, List.of(weapons)));

        List.of("minecraft:chests/nether_bridge")
                .forEach(id -> lootConfig.loot_tables.put(id, List.of(weapons)));

        List.of("minecraft:chests/shipwreck_supply",
                        "minecraft:chests/stronghold_corridor")
                .forEach(id -> lootConfig.loot_tables.put(id, List.of(weapons)));

        List.of("minecraft:chests/stronghold_library",
                        "minecraft:chests/underwater_ruin_big",
                        "minecraft:chests/bastion_other",
                        "minecraft:chests/woodland_mansion",
                        "minecraft:chests/simple_dungeon",
                        "minecraft:chests/underwater_ruin_big.json")
                .forEach(id -> lootConfig.loot_tables.put(id, List.of(weapons)));

        List.of("minecraft:chests/end_city_treasure",
                        "minecraft:chests/bastion_treasure",
                        "minecraft:chests/ancient_city",
                        "minecraft:chests/stronghold_library")
                .forEach(id -> lootConfig.loot_tables.put(id, List.of(weapons)));*/
    }

    @SafeVarargs
    private static <T> List<T> joinLists(List<T>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
