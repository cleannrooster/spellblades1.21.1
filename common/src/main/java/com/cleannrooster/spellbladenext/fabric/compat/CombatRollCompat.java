package com.cleannrooster.spellbladenext.fabric.compat;


import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.internals.container.SpellContainerSource;

public class CombatRollCompat {
    public static void register(){
      /*  ServerSideRollEvents.PLAYER_START_ROLLING.register((player, roll) -> {
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


        });*/
    }
}
