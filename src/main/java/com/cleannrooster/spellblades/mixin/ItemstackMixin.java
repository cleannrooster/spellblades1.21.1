package com.cleannrooster.spellblades.mixin;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.cleannrooster.spellblades.items.interfaces.PlayerDamageInterface;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.internals.SpellCooldownManager;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.internals.casting.SpellCasterEntity;
import net.spell_engine.utils.AnimationHelper;
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

@Mixin(ItemStack.class)
public class ItemstackMixin {
    @Inject(at = @At("HEAD"), method = "postDamageEntity", cancellable = true)
    public void postDamageEntitySpellblades(LivingEntity target, PlayerEntity player, CallbackInfo callbackInfo) {
 /*       if(player.hasStatusEffect(SPELLSTRIKE)&& player instanceof PlayerDamageInterface playerDamageInterface  &&
                playerDamageInterface.getSpellstriking() && !player.getWorld().isClient() &&
                SpellContainerHelper.getAvailable( player).spell_ids() != null && SpellContainerHelper.getAvailable( player).spell_ids().contains("spellbladenext:spellstrike")){
            double arcane = SpellPower.getSpellPower(SpellSchools.ARCANE,player).baseValue();
            double fire = SpellPower.getSpellPower(SpellSchools.FIRE,player).baseValue();
            double frost = SpellPower.getSpellPower(SpellSchools.FROST,player).baseValue();
            double lightning = SpellPower.getSpellPower(SpellSchools.LIGHTNING,player).baseValue();
            Spell spell = SpellRegistry.from(player.getWorld()).get(Identifier.of(MOD_ID,"blastarcane"));
            Identifier id = Identifier.of(MOD_ID,"blastarcane");
            if(arcane > fire && arcane > frost && arcane > lightning){
                spell = SpellRegistry.from(player.getWorld()).get(Identifier.of(MOD_ID,"blastarcane"));
                id = Identifier.of(MOD_ID,"blastarcane");
            }
            else if(fire > arcane && fire > frost && fire > lightning){
                spell = SpellRegistry.from(player.getWorld()).get(Identifier.of(MOD_ID,"blastfire"));
                id =Identifier.of(MOD_ID,"blastfire");
            }
            else if(frost > fire && frost > arcane && frost > lightning){
                spell = SpellRegistry.from(player.getWorld()).get(Identifier.of(MOD_ID,"blastfrost"));
                id =Identifier.of(MOD_ID,"blastfrost");

            }
            else if(lightning > fire && lightning > frost && lightning > arcane){
                spell = SpellRegistry.from(player.getWorld()).get(Identifier.of(MOD_ID,"blastlightning"));
                id =Identifier.of(MOD_ID,"blastlightning");

            }
            else{
                spell = SpellRegistry.from(player.getWorld()).get(Identifier.of(MOD_ID,"blastarcane"));
                id = Identifier.of(MOD_ID,"blastarcane");

            }


            SpellHelper.performSpell(player.getWorld(), player, id, TargetHelper.SpellTargetResult.of(List.of(target)), SpellCast.Action.RELEASE, 1.0F);
            AnimationHelper.sendAnimation((PlayerEntity) player, PlayerLookup.tracking(player), SpellCast.Animation.RELEASE, SpellRegistry.from(player.getWorld()).get(Identifier.of(MOD_ID, "spellstrike")).release.animation, 1F);

        }*/
    }
}
