package com.cleannrooster.spellblades.mixin;

import com.cleannrooster.spellblades.items.Orb;
import com.cleannrooster.spellblades.SpellbladesAndSuch;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.internals.casting.SpellCasterEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cleannrooster.spellblades.CustomAttributes.EPHEMERAL;
import static com.cleannrooster.spellblades.SpellbladesAndSuch.*;

@Mixin(value = LivingEntity.class)
public class LivingEntityMixin {
  

    @Shadow
    private  DefaultedList<ItemStack> syncedHandStacks;
    @Shadow
    private  DefaultedList<ItemStack> syncedArmorStacks;


        @ModifyVariable(at = @At("HEAD"), method = "applyMovementInput", index = 1)
    public Vec3d applyInputMIX(Vec3d vec3d) {
        LivingEntity living = ((LivingEntity) (Object) this);

        if(living instanceof PlayerEntity player && player instanceof SpellCasterEntity entity && entity.getCurrentSpell() != null && player.getMainHandStack().getItem() instanceof Orb) {
            return vec3d.multiply(6);
        }
        else{
            return vec3d;
        }
    }
    @Inject(at = @At("HEAD"), method = "isInSwimmingPose", cancellable = true)

    public void isCrawlingSB(CallbackInfoReturnable<Boolean> booleanCallbackInfoReturnable) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if(entity instanceof LivingEntity living){
            if(living.getStatusEffect(SpellbladesAndSuch.SUNDERED)!= null){
                booleanCallbackInfoReturnable.setReturnValue(true);
            }
        }
    }
    /*
        @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
        private void hurtreal(final DamageSource player, float f, final CallbackInfoReturnable<Boolean> info) {
            LivingEntity player2 = ((LivingEntity) (Object) this);
            Registry<DamageType> registry = ((DamageSourcesAccessor)player2.getDamageSources()).getRegistry();
            if(player2.getStatusEffect(DEFIANCE) instanceof StatusEffectInstance instance && player2 instanceof PlayerEntity playerPlayer && player2 instanceof SpellCasterEntity caster && !caster.getCooldownManager().isCoolingDown(Identifier.of(MOD_ID,"defiance_of_destiny_heal"))){
                imposeCooldown(playerPlayer, SpellContainerHelper.getFirstSourceOfSpell(Identifier.of(MOD_ID,"defiance_of_destiny_heal"),playerPlayer),Identifier.of(MOD_ID,"defiance_of_destiny_heal"),SpellRegistry.from(player2.getWorld()).get(Identifier.of(MOD_ID,"defiance_of_destiny_heal")),1.0F);
                player2.heal((float) Math.min(f-1,SpellPower.getSpellPower(SpellSchools.HEALING,player2).randomValue()*(instance.getAmplifier()+1)*(1-player2.getHealth()/player2.getMaxHealth())));
            }
            if (player.getAttacker() instanceof PlayerEntity player1  && !player1.getWorld().isClient()) {
                ItemStack stack = player1.getMainHandStack();


                if (player1 instanceof SpellCasterEntity entity && SpellContainerHelper.getAvailable(player1) != null && SpellContainerHelper.getAvailable(player1).spell_ids().contains("spellbladenext:arcaneoverdrive")) {
                    Identifier id = Identifier.of(MOD_ID,"arcaneoverdrive");
                    Spell spell = SpellRegistry.from(player1.getWorld()).get(Identifier.of(MOD_ID, "arcaneoverdrive"));
                    ((WorldScheduler)player1.getWorld()).schedule(1,()-> {
                                if (!entity.isCastingSpell()) {

                                    SpellHelper.performSpell(player1.getWorld(), player1, id, TargetHelper.SpellTargetResult.of(TargetHelper.targetsFromArea(player1, spell.range, spell.release.target.area, (target) -> TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, TargetHelper.Intent.HARMFUL, player1, target))), SpellCast.Action.RELEASE, 1F);
                                }
                            }
                    );
                }
                if (player1 instanceof SpellCasterEntity entity && SpellContainerHelper.getAvailable(player1) != null && SpellContainerHelper.getAvailable(player1).spell_ids().contains("spellbladenext:lightningoverdrive")) {
                    RegistryEntry<Spell> spellRegistryEntry = SpellRegistry.from(player1.getWorld()).getEntry(Identifier.of(MOD_ID, "lightningoverdrive")).get();
                    Identifier id = Identifier.of(MOD_ID,"lightningoverdrive");

                    Spell spell = spellRegistryEntry.value();

                    ((WorldScheduler)player1.getWorld()).schedule(1,()-> {
                                if (!entity.isCastingSpell()) {

                                    SpellHelper.performSpell(player1.getWorld(), player1, id, TargetHelper.SpellTargetResult.of(TargetHelper.targetsFromArea(player1, spell.range, spell.release.target.area, (target) -> TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, TargetHelper.Intent.HARMFUL, player1, target))), SpellCast.Action.RELEASE, 1F);
                                }
                            }
                    );

                }

                if (player1 instanceof SpellCasterEntity entity && SpellContainerHelper.getAvailable(player1) != null && SpellContainerHelper.getAvailable(player1).spell_ids().contains("spellbladenext:fireoverdrive")) {
                    RegistryEntry<Spell> spellRegistryEntry = SpellRegistry.from(player1.getWorld()).getEntry(Identifier.of(MOD_ID, "fireoverdrive")).get();
                    Identifier id = Identifier.of(MOD_ID,"fireoverdrive");

                    Spell spell = spellRegistryEntry.value();
                    ((WorldScheduler)player1.getWorld()).schedule(1,()-> {
                                if (!entity.isCastingSpell()) {
                                    SpellHelper.performSpell(player1.getWorld(), player1, id, TargetHelper.SpellTargetResult.of(TargetHelper.targetsFromArea(player1, spell.range, spell.release.target.area, (target) -> TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, TargetHelper.Intent.HARMFUL, player1, target))), SpellCast.Action.RELEASE, 1F);
                                }
                            }
                    );

                }



                if (player1 instanceof SpellCasterEntity entity && SpellContainerHelper.getAvailable(player1) != null && SpellContainerHelper.getAvailable(player1).spell_ids().contains("spellbladenext:frostoverdrive")) {
                    RegistryEntry<Spell> spellRegistryEntry = SpellRegistry.from(player1.getWorld()).getEntry(Identifier.of(MOD_ID, "frostoverdrive")).get();
                    Identifier id = Identifier.of(MOD_ID,"frostoverdrive");

                    Spell spell = spellRegistryEntry.value();
                    ((WorldScheduler)player1.getWorld()).schedule(1,()-> {
                                if (!entity.isCastingSpell()) {

                                    SpellHelper.performSpell(player1.getWorld(), player1, id, TargetHelper.SpellTargetResult.of(TargetHelper.targetsFromArea(player1, spell.range, spell.release.target.area, (target) -> TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, TargetHelper.Intent.HARMFUL, player1, target))), SpellCast.Action.RELEASE, 1F);
                                }
                            }
                    );
                }

            }
            */
    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    private void hurtreal(final DamageSource player, float f, final CallbackInfoReturnable<Boolean> info) {
        LivingEntity player2 = ((LivingEntity) (Object) this);

            if (player2 instanceof SpellCasterEntity entity && (entity.getCurrentSpell() != null && (entity.getCurrentSpell().equals(SpellRegistry.from(player2.getWorld()).get(Identifier.of(MOD_ID, "eviscerate"))) || entity.getCurrentSpell().equals(SpellRegistry.from(player2.getWorld()).get(Identifier.of(MOD_ID, "vaulting_slam"))) || entity.getCurrentSpell().equals(SpellRegistry.from(player2.getWorld()).get(Identifier.of(MOD_ID, "xslash")))))) {
            info.setReturnValue(false);
        }

    }
    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void addAttributesSpellblades_RETURN(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        info.getReturnValue().add(EPHEMERAL);
    }

}
