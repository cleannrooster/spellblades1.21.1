package com.cleannrooster.spellblades;

import com.cleannrooster.spellblades.client.entity.CycloneRenderer;
import com.cleannrooster.spellblades.config.Ack;
import com.cleannrooster.spellblades.config.ConfigSync;
import com.cleannrooster.spellblades.config.ConfigurationTask;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.LightningEntityRenderer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.client.SpellEngineClient;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.casting.SpellCasterEntity;
import net.spell_engine.network.Packets;
import net.spell_power.api.SpellPowerMechanics;

import java.util.Objects;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;


public class SpellbladesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(SpellbladesAndSuch.CYCLONEENTITY, CycloneRenderer::new);



        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }
}