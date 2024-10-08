package com.cleannrooster.spellblades.mixin;

import com.cleannrooster.spellblades.items.interfaces.PlayerDamageInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_engine.internals.SpellCooldownManager;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.internals.casting.SpellCasterEntity;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;
import static com.cleannrooster.spellblades.SpellbladesAndSuch.SPELLSTRIKE;
import static net.spell_engine.internals.SpellHelper.intent;

@Mixin(ItemStack.class)
public class ItemstackMixin {
    @Inject(at = @At("HEAD"), method = "postDamageEntity", cancellable = true)
    public void postDamageEntitySpellblades(LivingEntity target, PlayerEntity player, CallbackInfo callbackInfo) {
        if(player.hasStatusEffect(SPELLSTRIKE)&& player instanceof PlayerDamageInterface playerDamageInterface  &&
                playerDamageInterface.getSpellstriking() && !player.getWorld().isClient() &&
                SpellContainerHelper.getEquipped(player.getMainHandStack(), player).spell_ids() != null && SpellContainerHelper.getEquipped(player.getMainHandStack(), player).spell_ids().contains("spellbladenext:spellstrike")){
            if(playerDamageInterface.getSpellstrikeSpells().isEmpty()) {
                playerDamageInterface.setSpellstriking(false);
                if(SpellPower.getSpellPower(SpellSchools.ARCANE,player).baseValue()>=1){
                    playerDamageInterface.queueSpellStrikeSpell(Identifier.of(MOD_ID,"blastarcane"));

                }
                if(SpellPower.getSpellPower(SpellSchools.FIRE,player).baseValue()>=1){
                    playerDamageInterface.queueSpellStrikeSpell(Identifier.of(MOD_ID,"blastfire"));

                }
                if(SpellPower.getSpellPower(SpellSchools.FROST,player).baseValue()>=1){
                    playerDamageInterface.queueSpellStrikeSpell(Identifier.of(MOD_ID,"blastfrost"));

                }
                if(SpellPower.getSpellPower(SpellSchools.LIGHTNING,player).baseValue()>=1){
                    playerDamageInterface.queueSpellStrikeSpell(Identifier.of(MOD_ID,"blastlightning"));

                }
            }
            if(!playerDamageInterface.getSpellstrikeSpells().isEmpty()){
                playerDamageInterface.setSpellstriking(false);
                for(Identifier spellId : playerDamageInterface.getSpellstrikeSpells() ){
                    Spell spell = SpellRegistry.getSpell(spellId);
                    SpellInfo spellInfo = new SpellInfo(spell,spellId);
                    if(player instanceof SpellCasterEntity caster) {
                        caster.getCooldownManager().set(spellId, 0, true);
                    }

                    List<Entity> targets = new ArrayList<>();
                    if(spell.release.target.type.equals(Spell.Release.Target.Type.CURSOR)){
                        targets.add(target);
                    }
                    if(spell.release.target.type.equals(Spell.Release.Target.Type.AREA)) {
                        for(Spell.Impact impact: spell.impact) {
                            targets.addAll(TargetHelper.targetsFromArea(player, target.getPos(), spell.range, spell.release.target.area,(entity) -> TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, intent(impact.action),player,entity)));
                        }
                    }
                    if(spell.release.target.type.equals(Spell.Release.Target.Type.BEAM)) {
                        for(Spell.Impact impact: spell.impact) {

                            targets.addAll(TargetHelper.targetsFromRaycast(player, spell.range,  (entity) -> TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, intent(impact.action), player, entity)));
                        }
                    }
                    if(spell.release.target.type.equals(Spell.Release.Target.Type.METEOR)) {
                        targets.add(target);
                    }
                    if(spell.release.target.type.equals(Spell.Release.Target.Type.PROJECTILE)) {
                        targets.add(target);
                    }
                    if(spell.release.target.type.equals(Spell.Release.Target.Type.CLOUD)) {
                        targets.add(target);
                    }
                    if(spell.release.target.type.equals(Spell.Release.Target.Type.SELF)) {
                        targets.add(player);
                    }
                    SpellHelper.performSpell(player.getWorld(),player,spellId, TargetHelper.SpellTargetResult.of(targets), SpellCast.Action.RELEASE,1.0F);
                }
                playerDamageInterface.setSpellstriking(true);
            }
            playerDamageInterface.clearSpellstrikeSpells();

        }
    }
}
