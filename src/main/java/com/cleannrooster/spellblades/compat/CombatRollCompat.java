package com.cleannrooster.spellblades.compat;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.impl.IAnimatedPlayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.combat_roll.Platform;
import net.combat_roll.api.event.ServerSideRollEvents;
import net.combat_roll.client.RollEffect;
import net.combat_roll.client.animation.AnimatablePlayer;
import net.combat_roll.network.Packets;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.event.CombatEvents;
import net.spell_engine.api.spell.event.SpellEvents;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.internals.SpellTriggers;
import net.spell_engine.internals.container.SpellContainerSource;
import net.spell_engine.utils.AnimationHelper;
import net.spell_engine.utils.WorldScheduler;

public class CombatRollCompat {
    public static void register(){
        ServerSideRollEvents.PLAYER_START_ROLLING.register((player, roll) -> {
            if (SpellContainerSource.passiveSpellsOf(player).contains(SpellRegistry.from(player.getWorld()).getEntry(Identifier.of("spellbladenext", "phase_dash")).get())) {
                final var forwardPacket = new Packets.RollAnimation(player.getId(), new RollEffect.Visuals(Identifier.of("spellbladenext", "phase_dash").toString(), RollEffect.Particles.PUFF), roll);
                Platform.tracking(player).forEach(serverPlayer -> {
                    try {
                        if (Platform.networkS2C_CanSend(serverPlayer, Packets.RollAnimation.PACKET_ID)) {
                            Platform.networkS2C_Send(serverPlayer, forwardPacket);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                Platform.networkS2C_Send(player, forwardPacket);

            }


        });
    }
}
