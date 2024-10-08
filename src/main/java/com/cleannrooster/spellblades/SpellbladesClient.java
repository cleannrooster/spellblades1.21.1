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
import net.minecraft.util.math.MathHelper;
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
        ClientTickEvents.START_CLIENT_TICK.register(server -> {
                    PlayerEntity player = server.player;
                    World level = server.world;

                    if (player != null && level != null) {
                        if (player instanceof SpellCasterEntity caster) {

                            if (Objects.equals(caster.getCurrentSpell(), SpellRegistry.getSpell(Identifier.of(MOD_ID, "lightningstep")))) {

                                if (SpellRegistry.getSpell(Identifier.of(MOD_ID, "lightningstep")) != null) {
                                    double speed = player.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 16;
                                    BlockHitResult result = level.raycast(new RaycastContext(player.getPos(), player.getPos().add(0, -2, 0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, player));
                                    if (player.isSneaking()) {
                                        speed *= 0;
                                    }
                                    double modifier = 0;
                                    if (result.getType() == HitResult.Type.BLOCK) {
                                        modifier = 1;
                                    }

                                    Spell spell = SpellRegistry.getSpell(Identifier.of(MOD_ID, "lightningstep"));



                                            player.setVelocity(player.getRotationVec(1).normalize().multiply(speed, speed, speed));
                                        }
                                    }
                                }
                        if (player instanceof SpellCasterEntity caster) {

                            if (SpellRegistry.getSpell(Identifier.of(MOD_ID, "overpower")) != null) {
                                if (Objects.equals(caster.getCurrentSpell(), SpellRegistry.getSpell(Identifier.of(MOD_ID, "overpower")))) {

                                    double speed = player.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)  * 4;
                                BlockHitResult result = level.raycast(new RaycastContext(player.getPos(), player.getPos().add(0, -2, 0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, player));
                                if (player.isSneaking()) {
                                    speed *= 0;
                                }
                                double modifier = 0;
                                if (result.getType() == HitResult.Type.BLOCK) {
                                    modifier = 1;
                                }

                                Spell spell = SpellRegistry.getSpell(Identifier.of(MOD_ID, "overpower"));




                                        player.setVelocity(player.getRotationVec(1).subtract(0, player.getRotationVec(1).y, 0).normalize().multiply(speed, speed * modifier, speed).add(0, player.getVelocity().y, 0));
                                    }
                            }
                        }
                    }
                }
        );
        EntityRendererRegistry.register(SpellbladesAndSuch.CYCLONEENTITY, CycloneRenderer::new);


        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }
}