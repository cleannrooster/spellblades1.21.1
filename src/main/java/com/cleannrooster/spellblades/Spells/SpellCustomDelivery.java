package com.cleannrooster.spellblades.Spells;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.cleannrooster.spellblades.items.interfaces.PlayerDamageInterface;
import com.google.common.base.Suppliers;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.container.SpellContainerHelper;
import net.spell_engine.api.spell.event.SpellHandlers;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.fx.ParticleHelper;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.internals.casting.SpellCasterEntity;
import net.spell_engine.internals.container.SpellContainerSource;
import net.spell_engine.internals.target.EntityRelations;
import net.spell_engine.internals.target.SpellTarget;
import net.spell_engine.utils.AnimationHelper;
import net.spell_engine.utils.SoundHelper;
import net.spell_engine.utils.TargetHelper;
import net.spell_engine.utils.WorldScheduler;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;
import static com.cleannrooster.spellblades.SpellbladesAndSuch.SLAMMING;
import static net.spell_engine.api.spell.event.SpellHandlers.registerCustomDelivery;
import static net.spell_engine.internals.SpellHelper.imposeCooldown;
import static net.spell_engine.utils.SoundHelper.*;

public class SpellCustomDelivery {
    public static class  DragonSlam implements SpellHandlers.CustomDelivery {
        @Override
        public boolean onSpellDelivery(World world, RegistryEntry<Spell> registryEntry, PlayerEntity playerEntity, List<SpellHelper.TargetWithContext> targets, SpellHelper.ImpactContext impactContext, @Nullable Vec3d vec3d) {
            SpellSchool actualSchool = SpellSchools.FIRE;
            RegistryEntry<Spell> spellRegistryEntry =  SpellRegistry.from(playerEntity.getWorld()).getEntry(Identifier.of(MOD_ID, "dragon_slam")).get();
            if(!playerEntity.isOnGround()) {
                playerEntity.fallDistance = 0;
                playerEntity.velocityDirty = true;
                playerEntity.velocityModified = true;
                float f = playerEntity.getYaw();
                float g = playerEntity.getPitch();
                float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                float k = -MathHelper.sin(g * 0.017453292F);
                float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                float m = MathHelper.sqrt(h * h + k * k + l * l);
                float n = 3.0F * ((1.0F + (float) 3) / 4.0F);
                h *= n / m;
                k *= n / m;
                l *= n / m;

                playerEntity.setPosition(playerEntity.getPos().add(0, 0.5, 0));

                playerEntity.addVelocity((double) h * 0.6, (double) 1, (double) l * 0.6);
                playerEntity.setOnGround(false);

                playerEntity.addStatusEffect(new StatusEffectInstance(SLAMMING, 100, 0, false, false));
                imposeCooldown(playerEntity, SpellContainerSource.getFirstSourceOfSpell(Identifier.of(MOD_ID, "dragon_slam"), playerEntity), Identifier.of(MOD_ID, "dragon_slam"), SpellRegistry.from(playerEntity.getWorld()).get(Identifier.of(MOD_ID, "dragon_slam")), 1.0F);

            }
            else{
                List<Entity> list = new ArrayList<>();
                targets.forEach(targetWithContext -> list.add(targetWithContext.entity()));
                for(Entity entity : list) {
                    if (entity instanceof LivingEntity living) {
                        SpellHelper.ImpactContext context = new SpellHelper.ImpactContext(1.0F, 1.0F, playerEntity.getPos(), SpellPower.getSpellPower(SpellSchools.FIRE,playerEntity), SpellTarget.FocusMode.AREA,0);

                        SpellHelper.performImpacts(playerEntity.getWorld(), playerEntity, entity, playerEntity, spellRegistryEntry,spellRegistryEntry.value().impacts, context);

                    }
                }
                Supplier<Collection<ServerPlayerEntity>> trackingPlayers = Suppliers.memoize(() -> {
                    Collection<ServerPlayerEntity> playerEntities = PlayerLookup.tracking(playerEntity);
                    return playerEntities;
                });

                AnimationHelper.sendAnimation((PlayerEntity) playerEntity, (Collection)trackingPlayers.get(), SpellCast.Animation.RELEASE, "spell_engine:two_handed_slam_spellblade_2", 1);

            }
                return true;

        }
    }
    public static class  ReverbBrand implements SpellHandlers.CustomDelivery {
        @Override
        public boolean onSpellDelivery(World world, RegistryEntry<Spell> registryEntry, PlayerEntity playerEntity, List<SpellHelper.TargetWithContext> targets, SpellHelper.ImpactContext impactContext, @Nullable Vec3d vec3d) {
            if(!targets.isEmpty()){
                for(SpellHelper.TargetWithContext context : targets) {
                    for (int i = 0; i < 30 ;i++) {
                        ((WorldScheduler) context.entity().getWorld()).schedule(20 * i + 1, () -> {
                            SpellHelper.performImpacts(context.entity().getWorld(), playerEntity, context.entity(), context.entity(), registryEntry, registryEntry.value().impacts, impactContext, false);
                        });
                    }
                }
                return true;


            }
            return false;

        }
    }
            public static class  FlickeringFlame implements SpellHandlers.CustomDelivery {
        @Override
        public boolean onSpellDelivery(World world, RegistryEntry<Spell> registryEntry, PlayerEntity playerEntity, List<SpellHelper.TargetWithContext> targets, SpellHelper.ImpactContext impactContext, @Nullable Vec3d vec3d) {

    
            if(playerEntity instanceof PlayerDamageInterface player) {
                List<Entity> list = new ArrayList<>();
                int i = 0;
                for(SpellHelper.TargetWithContext context : targets){
                    if(context.entity() != null){
                        list.add(context.entity());
                    }
                }
                if (!list.stream().toList().isEmpty()) {
                    while (i < 16) {
                        for (Entity entity : list.stream().filter(target -> target instanceof LivingEntity).toList()) {
                            int finalI = i;
                            ((WorldScheduler) entity.getWorld()).schedule((int) Math.ceil((i + 1) * (5/playerEntity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED))), () -> {
                                        Vec3d vec31 = new Vec3d(1-2*entity.getRandom().nextFloat(), 0, 1-2*entity.getRandom().nextFloat()).normalize();
                                        Vec3d vec3 = entity.getPos().subtract(vec31.multiply(1 + 0.5 + (entity.getBoundingBox().getLengthX() / 2))).add(0,0.6,0);
                                        if(entity instanceof LivingEntity living && living.isAlive()) {
                                            if (!playerEntity.getWorld().getBlockState(new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z)).shouldSuffocate(playerEntity.getWorld(), new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z))) {
                                                playerEntity.requestTeleport(vec3.getX(), vec3.getY(), vec3.getZ());
                                            }
                                            playerEntity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, entity.getEyePos());
                                            SpellHelper.performImpacts(playerEntity.getWorld(), playerEntity, entity, playerEntity, registryEntry,SpellRegistry.from(playerEntity.getWorld()).get(Identifier.of(MOD_ID,"flickering_flame")).impacts, impactContext);
                                            if (finalI % 2 == 0) {
                                                AnimationHelper.sendAnimation(playerEntity, PlayerLookup.tracking(playerEntity), SpellCast.Animation.RELEASE, "spellbladenext:sword_swing_first", 1.0F);
                                                AnimationHelper.sendAnimation(playerEntity, List.of((ServerPlayerEntity) playerEntity), SpellCast.Animation.RELEASE, "spellbladenext:sword_swing_first", 1.0F);
                                            } else {
                                                AnimationHelper.sendAnimation(playerEntity, PlayerLookup.tracking(playerEntity), SpellCast.Animation.RELEASE, "spellbladenext:sword_swing_second", 1.0F);
                                                AnimationHelper.sendAnimation(playerEntity, List.of((ServerPlayerEntity) playerEntity), SpellCast.Animation.RELEASE, "spellbladenext:sword_swing_second", 1.0F);

                                            }
                                            ParticleHelper.sendBatches(playerEntity, SpellRegistry.from(playerEntity.getWorld()).get(Identifier.of(MOD_ID, "flickering_flame")).release.particles, true);
                                            SoundHelper.playSound(entity.getWorld(),playerEntity,registryEntry.value().release.sound);

                                        }
                                    }
                            );
                            i++;
                        }
                    }
                }
            }
            return true;
        }
    }
    
    public static class  Riptide implements SpellHandlers.CustomDelivery {
        @Override
        public boolean onSpellDelivery(World world, RegistryEntry<Spell> registryEntry, PlayerEntity playerEntity, List<SpellHelper.TargetWithContext> list, SpellHelper.ImpactContext impactContext, @Nullable Vec3d vec3d) {
            SpellSchool actualSchool = SpellSchools.FIRE;
            playerEntity.velocityDirty = true;
            playerEntity.velocityModified = true;
            float f = playerEntity.getYaw();
            float g = playerEntity.getPitch();
            float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
            float k = -MathHelper.sin(g * 0.017453292F);
            float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
            float m = MathHelper.sqrt(h * h + k * k + l * l);
            float n = 3.0F * ((1.0F + (float)3) / 4.0F);
            h *= n / m;
            k *= n / m;
            l *= n / m;
            playerEntity.addVelocity((double)h, (double)k, (double)l);
            playerEntity.useRiptide(20, (float) impactContext.power().randomValue(),playerEntity.getMainHandStack());
            if (playerEntity.isOnGround()) {
                float o = 1.1999999F;
                playerEntity.move(MovementType.SELF, new Vec3d(0.0D, 1.1999999284744263D, 0.0D));
            }

            SoundEvent soundEvent;
            soundEvent = SoundEvents.ITEM_TRIDENT_RIPTIDE_3.value();


            playerEntity.getWorld().playSoundFromEntity((PlayerEntity)null, playerEntity, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);

            return true; 
        }
    }

    public static class  Massacre implements SpellHandlers.CustomDelivery {
        @Override
        public boolean onSpellDelivery(World world, RegistryEntry<Spell> registryEntry, PlayerEntity playerEntity, List<SpellHelper.TargetWithContext> list, SpellHelper.ImpactContext impactContext, @Nullable Vec3d vec3d) {
            SpellSchool actualSchool = SpellSchools.FROST;

            if(list.isEmpty()){
                if(playerEntity instanceof SpellCasterEntity entity){
                    entity.setSpellCastProcess(null);
                }
                return true;
            }
            if(playerEntity instanceof PlayerDamageInterface playerDamageInterface && playerDamageInterface.getLastAttacked() != null && playerDamageInterface.getLastAttacked() instanceof LivingEntity living && (living.isDead() || !list.stream().anyMatch(any -> any.entity().equals(living)))){
                playerDamageInterface.resetRepeats();
                if(((PlayerDamageInterface) playerEntity).getLastAttacked() != null) {
                    List<LivingEntity> list2 = new ArrayList<>();

                    for(SpellHelper.TargetWithContext entity1 : list){
                        if(entity1.entity() instanceof LivingEntity living2 && living2 != ((PlayerDamageInterface) playerEntity).getLastAttacked()){
                            list2.add(living2);
                        }
                    }
                    Entity entity = playerEntity.getWorld().getClosestEntity(list2, TargetPredicate.DEFAULT,playerEntity,playerEntity.getX(),playerEntity.getY(),playerEntity.getZ());
                    LivingEntity livingEntity = null;
                    if(entity instanceof LivingEntity living2) {
                        livingEntity  = (LivingEntity) living2;
                    }
                    if(livingEntity != null) {
                        Vec3d pos = ((PlayerDamageInterface) playerEntity).getLastAttacked().getBoundingBox().getCenter();
                        for (int i = -5; i < 15; i++) {
                            System.out.println(livingEntity.getPos().add(0, livingEntity.getHeight() / 2, 0).subtract(pos).multiply(0.1 * i).length());

                            Vec3d pos2 = pos.add(livingEntity.getPos().add(0, livingEntity.getHeight() / 2, 0).subtract(pos).multiply(0.1 * i));
                            if (playerEntity.getWorld() instanceof ServerWorld serverWorld) {
                                for (ServerPlayerEntity player : PlayerLookup.tracking(livingEntity)) {
                                    //serverWorld.spawnParticles(player,Particles.snowflake.particleType,true, pos2.x, pos2.y, pos2.z, 1,0, 0, 0,0);
                                    serverWorld.spawnParticles(player, SpellEngineParticles.frost_shard.particleType(), true, pos2.x, pos2.y, pos2.z, 1, 0, 0, 0, 0);
                                    serverWorld.spawnParticles(player, SpellEngineParticles.snowflake.particleType(), true, pos2.x, pos2.y, pos2.z, 1, 0, 0, 0, 0);

                                    serverWorld.spawnParticles(player, ParticleTypes.ELECTRIC_SPARK
                                            , true, pos2.x, pos2.y, pos2.z, 1, 0, 0, 0, 0);


                                }
                            }
                        }
                    }
                }
                playerDamageInterface.setLastAttacked(null);
                
            }
            if(playerEntity instanceof PlayerDamageInterface playerDamageInterface && playerDamageInterface.getRepeats() >= 4){
                playerDamageInterface.resetRepeats();
                playerDamageInterface.setLastAttacked(null);

                if(playerEntity instanceof SpellCasterEntity entity){
                    entity.setSpellCastProcess(null);
                }
                return true;
            }

            if(playerEntity instanceof PlayerDamageInterface playerDamageInterface && playerDamageInterface.getLastAttacked() != null && list.stream().anyMatch(target -> target.entity().equals(playerDamageInterface.getLastAttacked()))) {
                SpellHelper.performImpacts(world,playerEntity,((PlayerDamageInterface) playerEntity).getLastAttacked(),((PlayerDamageInterface) playerEntity).getLastAttacked(),registryEntry,registryEntry.value().impacts,impactContext);
                Entity living = playerDamageInterface.getLastAttacked();
                Vec3d pos = living.getPos().add(0,living.getHeight()/2,0).subtract(new Vec3d(0,0,4*living.getBoundingBox().getLengthX()).rotateX(living.getWorld().getRandom().nextFloat()*360));

                for(int i = 0; i < 20; i++) {
                    Vec3d pos2 = pos.add(living.getPos().add(0,living.getHeight()/2,0).subtract(pos).multiply(0.1*i));
                    if(playerEntity.getWorld() instanceof ServerWorld serverWorld) {
                        for(ServerPlayerEntity player : PlayerLookup.tracking(living)) {
                            //serverWorld.spawnParticles(player,Particles.snowflake.particleType,true, pos2.x, pos2.y, pos2.z, 1,0, 0, 0,0);
                            serverWorld.spawnParticles(player, SpellEngineParticles.frost_shard.particleType(),true, pos2.x, pos2.y, pos2.z, 1,0, 0, 0,0);

                        }
                    }
                }
                living.getWorld().addParticle(ParticleTypes.SWEEP_ATTACK, true,living.getX(),living.getY(),living.getZ(),0,0,0);

                return false;
            }
            if(playerEntity instanceof PlayerDamageInterface playerDamageInterface && !list.isEmpty()) {
                Entity entity = playerDamageInterface.getLastAttacked();
                List<LivingEntity> list2 = new ArrayList<>();
                boolean bool = false;
                for(SpellHelper.TargetWithContext entity1 : list){
                    if(entity1.entity() instanceof LivingEntity living){
                        list2.add(living);
                    }
                }
                if(entity == null || (entity instanceof LivingEntity living && !living.isAlive()) || !list2.contains(entity)) {
                    entity = playerEntity.getWorld().getClosestEntity(list2, TargetPredicate.DEFAULT,playerEntity,playerEntity.getX(),playerEntity.getY(),playerEntity.getZ());

                }
                else{
                    
                    playerDamageInterface.setLastAttacked(null);
                    playerDamageInterface.resetRepeats();
                    if(playerEntity instanceof SpellCasterEntity antity){
                        antity.setSpellCastProcess(null);
                    }
                    return true;
                }
                if(entity != null) {
         
                    SpellHelper.performImpacts(world,playerEntity,entity,entity,registryEntry,registryEntry.value().impacts,impactContext);
                    playerDamageInterface.setLastAttacked(entity);
                    Entity living = entity;
                    Vec3d pos = living.getPos().add(0,living.getHeight()/2,0).subtract(new Vec3d(0,0,4*living.getBoundingBox().getLengthX()).rotateX(living.getWorld().getRandom().nextFloat()*360));

                    for(int i = 0; i < 20; i++) {
                        Vec3d pos2 = pos.add(living.getPos().add(0,living.getHeight()/2,0).subtract(pos).multiply(0.1*i));
                        if(playerEntity.getWorld() instanceof ServerWorld serverWorld) {
                            for(ServerPlayerEntity player : PlayerLookup.tracking(living)) {
                                //serverWorld.spawnParticles(player,Particles.snowflake.particleType,true, pos2.x, pos2.y, pos2.z, 1,0, 0, 0,0);
                                serverWorld.spawnParticles(player, SpellEngineParticles.frost_shard.particleType(),true, pos2.x, pos2.y, pos2.z, 1,0, 0, 0,0);
                            }
                        }
                    }
                    playerEntity.getWorld().addParticle(ParticleTypes.SWEEP_ATTACK, true,living.getX(),living.getY(),living.getZ(),0,0,0);


                }
            }
            return false;
        }
    }
    public static void registerDeliveries(){
        registerCustomDelivery(Identifier.of(MOD_ID,"riptide"),new Riptide());
        registerCustomDelivery(Identifier.of(MOD_ID,"eviscerate"),new Massacre());
        registerCustomDelivery(Identifier.of(MOD_ID,"flickering_flame"),new FlickeringFlame());
        registerCustomDelivery(Identifier.of(MOD_ID,"dragon_slam"),new DragonSlam());
        registerCustomDelivery(Identifier.of(MOD_ID,"reverberation_brand"),new ReverbBrand());


    }
}
