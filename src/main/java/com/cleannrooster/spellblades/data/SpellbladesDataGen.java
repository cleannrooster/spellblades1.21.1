package com.cleannrooster.spellblades.data;

import com.cleannrooster.spellblades.Spells.SpellbladeSpells;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.spell_engine.api.datagen.SpellGenerator;
import org.apache.commons.lang3.builder.Builder;

import java.util.concurrent.CompletableFuture;

public class SpellbladesDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(SpellbladesSpellGen::new);
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


}