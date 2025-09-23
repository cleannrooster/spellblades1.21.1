package com.cleannrooster.spellblades.items;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import net.minecraft.item.Item;

import java.util.HashMap;

public class SpellbladeItems {
    public static final HashMap<String, Item> entries;
    static {
        entries = new HashMap<>();
        for(var weaponEntry: Items.entries) {
            entries.put(weaponEntry.id().toString(), weaponEntry.item());
        }
        entries.put("spellblades:monkeystaff", SpellbladesAndSuch.MONKEYSTAFF);



    }
}