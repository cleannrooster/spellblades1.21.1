package com.cleannrooster.spellbladenext.data;

import com.cleannrooster.spellbladenext.fabric.Spells.compat.ElementalSpells;
import com.cleannrooster.spellbladenext.fabric.items.armor.Armors;
import com.cleannrooster.spellbladenext.fabric.Spells.SpellbladeSpells;
import com.cleannrooster.spellbladenext.fabric.items.Items;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.spell_engine.api.datagen.SpellGenerator;
import net.spell_engine.rpg_series.datagen.RPGSeriesDataGen;
import net.spell_engine.rpg_series.tags.RPGSeriesItemTags;

import java.util.concurrent.CompletableFuture;

public class SpellbladesDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(SpellbladesSpellGen::new);
        pack.addProvider(ItemTagGenerator::new);
    }



    public static class SpellbladesSpellGen extends SpellGenerator {
        public SpellbladesSpellGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generateSpells(Builder builder) {
            for (var entry: SpellbladeSpells.entries) {
                builder.add(entry.id(), entry.spell());
            }

        }
    }
    public static class ItemTagGenerator extends RPGSeriesDataGen.ItemTagGenerator {
        public ItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            generateWeaponTags(Items.entries);
            generateArmorTags(Armors.entries, RPGSeriesItemTags.ArmorMetaType.MAGIC);
        }
    }

}