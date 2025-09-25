package com.cleannrooster.spellbladenext;

import com.cleannrooster.spellbladenext.SpellbladesAndSuch;

import com.cleannrooster.spellbladenext.fabric.Spells.SpellbladeSpells;
import com.cleannrooster.spellbladenext.fabric.effect.DeathchillRenderer;
import com.cleannrooster.spellbladenext.fabric.effect.DescryRenderer;
import com.cleannrooster.spellbladenext.fabric.effect.FeatherHealRenderer;
import com.cleannrooster.spellbladenext.fabric.effect.FeatherRenderer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.spell_engine.api.effect.CustomModelStatusEffect;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.internals.casting.SpellCasterEntity;

import java.util.Objects;

import static com.cleannrooster.spellbladenext.SpellbladesAndSuch.MOD_ID;


public class SpellbladesClient   {
    public static  void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register(server -> {
                    PlayerEntity player = server.player;
                    World level = server.world;

                    if (player != null && level != null) {
                        if (player instanceof SpellCasterEntity caster) {

                            if (Objects.equals(caster.getCurrentSpell(), SpellRegistry.from(player.getWorld()).get(Identifier.of(MOD_ID, "lightningstep")))) {

                                if (SpellRegistry.from(player.getWorld()).get(Identifier.of(MOD_ID, "lightningstep")) != null) {
                                    double speed = player.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 16;
                                    BlockHitResult result = level.raycast(new RaycastContext(player.getPos(), player.getPos().add(0, -2, 0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, player));
                                    if (player.isSneaking()) {
                                        speed *= 0;
                                    }
                                    double modifier = 0;
                                    if (result.getType() == HitResult.Type.BLOCK) {
                                        modifier = 1;
                                    }

                                    Spell spell = SpellRegistry.from(player.getWorld()).get(Identifier.of(MOD_ID, "lightningstep"));



                                            player.setVelocity(player.getRotationVec(1).normalize().multiply(speed, speed, speed));
                                        }
                                    }
                                }
                        if (player instanceof SpellCasterEntity caster) {

                            if (SpellRegistry.from(player.getWorld()).get(Identifier.of(MOD_ID, "overpower")) != null) {
                                if (Objects.equals(caster.getCurrentSpell(), SpellRegistry.from(player.getWorld()).get(Identifier.of(MOD_ID, "overpower")))) {

                                    double speed = player.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)  * 4;
                                BlockHitResult result = level.raycast(new RaycastContext(player.getPos(), player.getPos().add(0, -2, 0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.ANY, player));
                                if (player.isSneaking()) {
                                    speed *= 0;
                                }
                                double modifier = 0;
                                if (result.getType() == HitResult.Type.BLOCK) {
                                    modifier = 1;
                                }

                                Spell spell = SpellRegistry.from(player.getWorld()).get(Identifier.of(MOD_ID, "overpower"));




                                        player.setVelocity(player.getRotationVec(1).subtract(0, player.getRotationVec(1).y, 0).normalize().multiply(speed, speed * modifier, speed).add(0, player.getVelocity().y, 0));
                                    }
                            }
                        }
                    }
                }
        );
        CustomModelStatusEffect.register(SpellbladesAndSuch.FEATHER.value(), new FeatherRenderer());
        CustomModelStatusEffect.register(SpellbladesAndSuch.DEATHCHILL.value(), new DeathchillRenderer());
        CustomModelStatusEffect.register(SpellbladesAndSuch.FEATHERHEAL.value(), new FeatherHealRenderer());
        for (var entry: SpellbladeSpells.entries) {
            if (entry.mutator() != null) {
                SpellTooltip.addDescriptionMutator(entry.id(), entry.mutator());
            }
        }
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }
}