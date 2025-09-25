package com.cleannrooster.spellbladenext.fabric.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
