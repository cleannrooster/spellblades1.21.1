package com.cleannrooster.spellbladenext.fabric.mixin;

import com.cleannrooster.spellbladenext.fabric.items.interfaces.PlayerDamageInterface;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.fx.ParticleHelper;
import net.spell_engine.fx.SpellEngineSounds;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.internals.container.SpellContainerSource;
import net.spell_engine.internals.target.SpellTarget;
import net.spell_engine.utils.AnimationHelper;
import net.spell_engine.utils.SoundHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static com.cleannrooster.spellbladenext.SpellbladesAndSuch.MOD_ID;

@Mixin(SpellHelper.class)
public class SpellCastMixin {
   
    @Inject(at = @At("HEAD"), method = "performSpell", cancellable = true)
    private static void performSpellSpellstrike(World world, PlayerEntity player, RegistryEntry<Spell> spellEntry, SpellTarget.SearchResult targetResult, SpellCast.Action action, float progress, CallbackInfo callbackInfo) {
        if (action.equals(SpellCast.Action.RELEASE)) {
            if (player instanceof PlayerDamageInterface playerInterface ) {

                if (playerInterface.getSpellstrikeSpells().stream().anyMatch(spell -> spell.toString().equals(spellEntry.getIdAsString()))) {
                    player.sendMessage(Text.translatable("spellbladenext:spellstrike_error"));
                    callbackInfo.cancel();
                }
            }
        }


    if(action.equals(SpellCast.Action.RELEASE) &&  SpellContainerSource.passiveSpellsOf(player).stream().anyMatch(spell -> spell.isIn(TagKey.of(SpellRegistry.KEY,Identifier.of(MOD_ID,"spellstrike")))) &&  spellEntry.value().type.equals(Spell.Type.ACTIVE) && spellEntry.value().active.cast.channel_ticks == 0){
            if(player instanceof PlayerDamageInterface playerInterface ) {
                if(!spellEntry.isIn(TagKey.of(SpellRegistry.KEY,Identifier.of(MOD_ID,"technique")))) {
                    if(spellEntry.value().impacts.stream().noneMatch(impact -> impact.school != null &&  impact.school.equals(ExternalSpellSchools.PHYSICAL_MELEE))) {
                        if(!spellEntry.value().target.type.equals(Spell.Target.Type.CASTER)) {
                            if (playerInterface.getSpellstrikeSpells().stream().noneMatch(spell -> spell.toString().equals(spellEntry.getIdAsString()))) {

                                playerInterface.queueSpellStrikeSpell(Identifier.tryParse(spellEntry.getIdAsString()));
                                SoundHelper.playSound(world,player,new Sound(SpellEngineSounds.BIND_SPELL.id()));
                                AnimationHelper.sendAnimation(player, PlayerLookup.tracking(player), SpellCast.Animation.RELEASE, spellEntry.value().release.animation, 1.0F);
                                AnimationHelper.sendAnimation(player, List.of((ServerPlayerEntity) player), SpellCast.Animation.RELEASE, spellEntry.value().release.animation, 1.0F);

                                ParticleHelper.play(world, player, spellEntry.value().release.particles);
                                SpellHelper.imposeCooldown(player,SpellContainerSource.getFirstSourceOfSpell(Identifier.tryParse(spellEntry.getIdAsString()),player),Identifier.tryParse(spellEntry.getIdAsString()),spellEntry,1.0F);
                                callbackInfo.cancel();
                            }
                        }
                    }
                }

         }
     }

    }
}
