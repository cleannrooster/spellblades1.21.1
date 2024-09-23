package com.cleannrooster.spellblades.mixin;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.cleannrooster.spellblades.items.Orb;
import com.cleannrooster.spellblades.items.interfaces.PlayerDamageInterface;
import com.extraspellattributes.api.SpellStatusEffect;
import com.extraspellattributes.api.SpellStatusEffectInstance;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.WorldScheduler;
import net.spell_engine.internals.casting.SpellCasterEntity;
import net.spell_engine.particle.ParticleHelper;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;
import net.spell_power.mixin.DamageSourcesAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.*;
import static net.spell_engine.internals.SpellHelper.ammoForSpell;
import static net.spell_engine.internals.SpellHelper.impactTargetingMode;

@Mixin(value = LivingEntity.class)
public class LivingEntityMixin {
  

    @Shadow
    private  DefaultedList<ItemStack> syncedHandStacks;
    @Shadow
    private  DefaultedList<ItemStack> syncedArmorStacks;



    @Inject(at = @At("HEAD"), method = "onAttacking", cancellable = true)
    public void onAttackingSpellbladesMixin(Entity target, CallbackInfo info) {
        LivingEntity living = (LivingEntity) (Object) this;

        if (!living.getWorld().isClient() && living instanceof PlayerEntity player && living instanceof SpellCasterEntity caster && living instanceof PlayerDamageInterface damageInterface &&
            SpellContainerHelper.getEquipped(living.getMainHandStack(), player) != null && SpellContainerHelper.getEquipped(player.getMainHandStack(), player).spell_ids() != null && SpellContainerHelper.getEquipped(player.getMainHandStack(), player).spell_ids().contains("spellbladenext:deathchill")) {
            if(!FabricLoader.getInstance().isModLoaded("frostiful")) {

                target.setFrozenTicks(target.getFrozenTicks() + 28);
            }
            else{
                target.setFrozenTicks(target.getFrozenTicks() + 28*3*20);

            }
        }
        if (!living.getWorld().isClient() && living instanceof SpellCasterEntity entity && living instanceof PlayerEntity player && SpellContainerHelper.getEquipped(living.getMainHandStack(),player) != null && SpellContainerHelper.getEquipped(player.getMainHandStack(),player).spell_ids().contains("spellbladenext:combustion") && ammoForSpell(player, SpellRegistry.getSpell(Identifier.of(MOD_ID, "combustion")), player.getMainHandStack()).satisfied() && !entity.getCooldownManager().isCoolingDown(Identifier.of(MOD_ID, "combustion"))) {
            if(target instanceof LivingEntity livingEntity && !livingEntity.hasStatusEffect(PHOENIXCURSE)){
                target.setOnFireFor(2);
                livingEntity.addStatusEffect(new SpellStatusEffectInstance(PHOENIXCURSE,SpellRegistry.getSpell(Identifier.of(MOD_ID,"combustion")), (float) SpellPower.getSpellPower(SpellSchools.FIRE,living).randomValue(),living,40,0,false,false,true,null));
                ((WorldScheduler)livingEntity.getWorld()).schedule(1,()->{
                    entity.getCooldownManager().set(Identifier.of(MOD_ID,"combustion"),(int) (20*SpellHelper.getCooldownDuration(living,SpellRegistry.getSpell(Identifier.of(MOD_ID,"combustion")))));
                });
            }
        }
        if (!living.getWorld().isClient() && living instanceof SpellCasterEntity entity && living instanceof PlayerEntity player && SpellContainerHelper.getEquipped(living.getMainHandStack(),player) != null && SpellContainerHelper.getEquipped(player.getMainHandStack(),player).spell_ids().contains("spellbladenext:smite")) {
            int i;
            if (living.getStatusEffect(SpellbladesAndSuch.FERVOR) != null) {
                i = living.getStatusEffect(SpellbladesAndSuch.FERVOR).getAmplifier() + 1;
            } else {
                i = 0;
            }
            ((WorldScheduler) living.getWorld()).schedule(1, () -> {
                        living.addStatusEffect(new StatusEffectInstance(SpellbladesAndSuch.FERVOR, 80, Math.min(i, 2)));
                    }
            );
        }
    }

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    public void tick_SB_HEAD(CallbackInfo info) {

            LivingEntity living = (LivingEntity) (Object) this;

            if(!living.getWorld().isClient() && living instanceof PlayerEntity player && living instanceof SpellCasterEntity caster && living instanceof PlayerDamageInterface damageInterface  &&
                SpellContainerHelper.getEquipped(living.getMainHandStack(), player) != null && SpellContainerHelper.getEquipped(player.getMainHandStack(), player).spell_ids() != null && SpellContainerHelper.getEquipped(player.getMainHandStack(), player).spell_ids().contains("spellbladenext:echoes")){
            if(damageInterface.getDiebeamStacks() < 3 &&  living.age % 80 == 0) {
                damageInterface.addDiebeamStack(1);
            }
            if(damageInterface.getDiebeamStacks() >0) {
                player.addStatusEffect(new StatusEffectInstance(SpellbladesAndSuch.UNLEASH, 5, damageInterface.getDiebeamStacks() - 1,false,false,true));
            }

        }
        if(!living.getWorld().isClient() && living instanceof PlayerEntity player && living instanceof SpellCasterEntity caster && living instanceof PlayerDamageInterface damageInterface  &&
                SpellContainerHelper.getEquipped(living.getMainHandStack(), player) != null && SpellContainerHelper.getEquipped(player.getMainHandStack(), player).spell_ids() != null && SpellContainerHelper.getEquipped(player.getMainHandStack(), player).spell_ids().contains("spellbladenext:deathchill")) {
            if(!FabricLoader.getInstance().isModLoaded("frostiful")){
                living.setFrozenTicks(living.getFrozenTicks()+1+living.getMinFreezeDamageTicks()/(20*20));
            }
            else{
                living.setFrozenTicks(living.getFrozenTicks()+2);

            }

        }
            /*if(!FabricLoader.getInstance().isModLoaded("reabsorption")) {

            if (living instanceof PlayerDamageInterface damageInterface && living.getAttributeInstance(WARDING) != null && living.getAttributeValue(WARDING) >= 1) {
                float additional = (float) (0.05 * living.getAttributeValue(WARDING) * (0.173287 * Math.pow(Math.E, -0.173287 * 0.05 * (living.age - damageInterface.getLasthurt()))));
                additional *= Spellblades.config.wardrate;
                if (damageInterface.getLasthurt() != 0 && living.age - damageInterface.getLasthurt() < 100 * 20 && damageInterface.getDamageAbsorbed() + additional <= living.getAttributeValue(WARDING)) {
                    damageInterface.absorbDamage(additional);
                }
                if (living.age < 16 * 20 && damageInterface.getLasthurt() == 0 && damageInterface.getDamageAbsorbed() + additional <= living.getAttributeValue(WARDING)) {
                    damageInterface.absorbDamage(additional);
                }
                if (damageInterface.getDamageAbsorbed() > living.getAbsorptionAmount()) {
                    if (!living.getWorld().isClient()) {
                        living.setAbsorptionAmount(damageInterface.getDamageAbsorbed());
                    }
                }
            }*/
    }

        @ModifyVariable(at = @At("HEAD"), method = "applyMovementInput", index = 1)
    public Vec3d applyInputMIX(Vec3d vec3d) {
        LivingEntity living = ((LivingEntity) (Object) this);

        if(living instanceof PlayerEntity player && player instanceof SpellCasterEntity entity && entity.getCurrentSpell() != null && player.getMainHandStack().getItem() instanceof Orb) {
            return vec3d.multiply(6);
        }
        else if(living instanceof PlayerEntity player && player instanceof SpellCasterEntity entity && entity.getCurrentSpell() != null &&(
                entity.getCurrentSpell().equals(SpellRegistry.getSpell(Identifier.of(MOD_ID,"reckoning"))) ||
                entity.getCurrentSpell().equals(SpellRegistry.getSpell(Identifier.of(MOD_ID,"whirlwind"))) ||
                entity.getCurrentSpell().equals(SpellRegistry.getSpell(Identifier.of(MOD_ID,"maelstrom"))) ||
                entity.getCurrentSpell().equals(SpellRegistry.getSpell(Identifier.of(MOD_ID,"tempest")))  ||
                entity.getCurrentSpell().equals(SpellRegistry.getSpell(Identifier.of(MOD_ID,"inferno"))) )) {
            return vec3d.multiply(18);

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


    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    private void hurtreal(final DamageSource player, float f, final CallbackInfoReturnable<Boolean> info) {
        LivingEntity player2 = ((LivingEntity) (Object) this);
        Registry<DamageType> registry = ((DamageSourcesAccessor)player2.getDamageSources()).getRegistry();
        if(player2.getWorld() instanceof ServerWorld serverWorld) {
            if(player2 instanceof PlayerEntity playerEntity) {
                if (playerEntity instanceof SpellCasterEntity entity && SpellContainerHelper.getEquipped(playerEntity.getMainHandStack(), playerEntity) != null && SpellContainerHelper.getEquipped(playerEntity.getMainHandStack(), playerEntity).spell_ids().contains("spellbladenext:soul_of_vengeance")) {
                    if (player.getAttacker() instanceof LivingEntity living && !TargetHelper.getRelation(living, playerEntity).equals(TargetHelper.Relation.ALLY) && playerEntity.distanceTo(player2) < 16 && TargetHelper.getRelation(playerEntity, player2).equals(TargetHelper.Relation.ALLY)) {

                        if (playerEntity.getStatusEffect(FERVOR) instanceof SpellStatusEffectInstance instance) {
                            int i = Math.min(9, instance.getAmplifier() + 1);
                            ((WorldScheduler) serverWorld).schedule(1, () -> {

                                playerEntity.addStatusEffect(new StatusEffectInstance(FERVOR, 160, i));
                            });
                        }
                        {

                            playerEntity.addStatusEffect(new StatusEffectInstance(FERVOR, 160, 0));

                        }
                    }
                }
            }
            for (PlayerEntity playerEntity : PlayerLookup.tracking(player2)) {
                if (playerEntity instanceof SpellCasterEntity entity && SpellContainerHelper.getEquipped(playerEntity.getMainHandStack(), playerEntity) != null && SpellContainerHelper.getEquipped(playerEntity.getMainHandStack(), playerEntity).spell_ids().contains("spellbladenext:soul_of_vengeance")) {

                    Spell spell = SpellRegistry.getSpell(Identifier.of(MOD_ID, "soul_of_vengeance"));
                    if (player.getAttacker() instanceof LivingEntity living && !TargetHelper.getRelation(living, playerEntity).equals(TargetHelper.Relation.ALLY) && playerEntity.distanceTo(player2) < 16 && TargetHelper.getRelation(playerEntity, player2).equals(TargetHelper.Relation.ALLY)) {
                        if (playerEntity.getStatusEffect(FERVOR) instanceof SpellStatusEffectInstance instance) {
                            int i = Math.min(9, instance.getAmplifier() + 1);
                            ((WorldScheduler) serverWorld).schedule(1, () -> {

                                playerEntity.addStatusEffect(new StatusEffectInstance(FERVOR, 160, i));
                            });
                        }
                        {

                            playerEntity.addStatusEffect(new StatusEffectInstance(FERVOR, 160, 0));

                        }
                    }
                }
            }
        }
        if (player.getAttacker() instanceof PlayerEntity player1  && !player1.getWorld().isClient()) {
            ItemStack stack = player1.getMainHandStack();


            if (player1 instanceof SpellCasterEntity entity && SpellContainerHelper.getEquipped(player1.getMainHandStack(),player1) != null && SpellContainerHelper.getEquipped(player1.getMainHandStack(),player1).spell_ids().contains("spellbladenext:arcaneoverdrive") && ammoForSpell(player1, SpellRegistry.getSpell(Identifier.of(MOD_ID, "arcaneoverdrive")), stack).satisfied() && !entity.getCooldownManager().isCoolingDown(Identifier.of(MOD_ID, "arcaneoverdrive"))) {
                Spell spell = SpellRegistry.getSpell(Identifier.of(MOD_ID, "arcaneoverdrive"));

                entity.getCooldownManager().set(Identifier.of(MOD_ID, "arcaneoverdrive"), (int) (20 * SpellHelper.getCooldownDuration(player1, spell)));
                Predicate<Entity> selectionPredicate = (target2) -> {
                    return (TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, TargetHelper.Intent.HARMFUL, player1, target2)
                    );
                };

                int i = 0;
                List<Entity> targets = player1.getWorld().getOtherEntities(player1, player1.getBoundingBox().expand(spell.range), selectionPredicate);


                SpellHelper.ImpactContext context = new SpellHelper.ImpactContext(1.0F, 1.0F, (Vec3d) null, SpellPower.getSpellPower(spell.school, player1), impactTargetingMode(spell));

                for (Entity target1 : targets) {
                    SpellInfo spell1 = new SpellInfo(SpellRegistry.getSpell(Identifier.of(MOD_ID, "arcaneoverdrive")), Identifier.of(MOD_ID, "arcaneoverdrive"));

                    SpellHelper.performImpacts(player1.getWorld(), player1, target1, player1, spell1, new SpellHelper.ImpactContext());
                }
                ParticleHelper.sendBatches(player1, spell.release.particles);
                SpellHelper.AmmoResult ammoResult = ammoForSpell(player1, spell, stack);
                if (ammoResult.ammo() != null) {
                    for (int ii = 0; ii < player1.getInventory().size(); ++ii) {
                        ItemStack stack1 = player1.getInventory().getStack(ii);
                        if (stack1.isOf(ammoResult.ammo().getItem())) {
                            stack1.decrement(1);
                            if (stack1.isEmpty()) {
                                player1.getInventory().removeOne(stack1);
                            }
                            break;
                        }
                    }
                }

            }


            Spell spell2 = SpellRegistry.getSpell(Identifier.of(MOD_ID, "fireoverdrive"));

            if (player1 instanceof SpellCasterEntity entity && SpellContainerHelper.getEquipped(player1.getMainHandStack(),player1) != null && SpellContainerHelper.getEquipped(player1.getMainHandStack(),player1).spell_ids().contains("spellbladenext:fireoverdrive") &&  ammoForSpell(player1, spell2, stack).satisfied() && !entity.getCooldownManager().isCoolingDown(Identifier.of(MOD_ID, "fireoverdrive"))) {
                Spell spell = SpellRegistry.getSpell(Identifier.of(MOD_ID, "fireoverdrive"));

                entity.getCooldownManager().set(Identifier.of(MOD_ID, "fireoverdrive"), (int) (20 * SpellHelper.getCooldownDuration(player1, spell)));
                Predicate<Entity> selectionPredicate = (target2) -> {
                    return (TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, TargetHelper.Intent.HARMFUL, player1, target2)
                    );
                };

                int i = 0;
                List<Entity> targets = player1.getWorld().getOtherEntities(player1, player1.getBoundingBox().expand(spell.range), selectionPredicate);

                SpellHelper.ImpactContext context = new SpellHelper.ImpactContext(1.0F, 1.0F, (Vec3d) null, SpellPower.getSpellPower(spell.school, player1), impactTargetingMode(spell));

                for (Entity target1 : targets) {
                    SpellInfo spell1 = new SpellInfo(SpellRegistry.getSpell(Identifier.of(MOD_ID, "fireoverdrive")), Identifier.of(MOD_ID, "fireoverdrive"));

                    SpellHelper.performImpacts(player1.getWorld(), player1, target1, player1, spell1, new SpellHelper.ImpactContext());
                }
                ParticleHelper.sendBatches(player1, spell.release.particles);
                SpellHelper.AmmoResult ammoResult = ammoForSpell(player1, spell, stack);
                if (ammoResult.ammo() != null) {
                    for (int ii = 0; ii < player1.getInventory().size(); ++ii) {
                        ItemStack stack1 = player1.getInventory().getStack(ii);
                        if (stack1.isOf(ammoResult.ammo().getItem())) {
                            stack1.decrement(1);
                            if (stack1.isEmpty()) {
                                player1.getInventory().removeOne(stack1);
                            }
                            break;
                        }
                    }
                }

            }


            Spell spell3 = SpellRegistry.getSpell(Identifier.of(MOD_ID, "frostoverdrive"));

            if (player1 instanceof SpellCasterEntity entity && SpellContainerHelper.getEquipped(player1.getMainHandStack(),player1) != null && SpellContainerHelper.getEquipped(player1.getMainHandStack(),player1).spell_ids().contains("spellbladenext:frostoverdrive") && ammoForSpell(player1, spell3, stack).satisfied() && !entity.getCooldownManager().isCoolingDown(Identifier.of(MOD_ID, "frostoverdrive"))) {
                Spell spell = SpellRegistry.getSpell(Identifier.of(MOD_ID, "frostoverdrive"));

                entity.getCooldownManager().set(Identifier.of(MOD_ID, "frostoverdrive"), (int) (20 * SpellHelper.getCooldownDuration(player1, spell)));
                Predicate<Entity> selectionPredicate = (target2) -> {
                    return (TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, TargetHelper.Intent.HARMFUL, player1, target2)
                    );
                };

                int i = 0;
                List<Entity> targets = player1.getWorld().getOtherEntities(player1, player1.getBoundingBox().expand(spell.range), selectionPredicate);

                SpellHelper.ImpactContext context = new SpellHelper.ImpactContext(1.0F, 1.0F, (Vec3d) null, SpellPower.getSpellPower(spell.school, player1), impactTargetingMode(spell));

                for (Entity target1 : targets) {
                    SpellInfo spell1 = new SpellInfo(SpellRegistry.getSpell(Identifier.of(MOD_ID, "frostoverdrive")), Identifier.of(MOD_ID, "frostoverdrive"));

                    SpellHelper.performImpacts(player1.getWorld(), player1, target1, player1, spell1, new SpellHelper.ImpactContext());
                }
                ParticleHelper.sendBatches(player1, spell.release.particles);
                SpellHelper.AmmoResult ammoResult = ammoForSpell(player1, spell, stack);
                if (ammoResult.ammo() != null) {
                    for (int ii = 0; ii < player1.getInventory().size(); ++ii) {
                        ItemStack stack1 = player1.getInventory().getStack(ii);
                        if (stack1.isOf(ammoResult.ammo().getItem())) {
                            stack1.decrement(1);
                            if (stack1.isEmpty()) {
                                player1.getInventory().removeOne(stack1);
                            }
                            break;
                        }
                    }
                }
            }

        }
        if (player2 instanceof SpellCasterEntity entity && (entity.getCurrentSpell() != null && (entity.getCurrentSpell().equals(SpellRegistry.getSpell(Identifier.of(MOD_ID, "eviscerate"))) || entity.getCurrentSpell().equals(SpellRegistry.getSpell(Identifier.of(MOD_ID, "monkeyslam")))))) {
            info.setReturnValue(false);
        }
    }
}
