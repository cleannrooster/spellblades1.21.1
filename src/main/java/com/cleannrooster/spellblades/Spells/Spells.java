package com.cleannrooster.spellblades.Spells;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.cleannrooster.spellblades.entity.CycloneEntity;
import com.cleannrooster.spellblades.items.attacks.Attacks;
import com.cleannrooster.spellblades.items.interfaces.PlayerDamageInterface;
import com.extraspellattributes.api.SpellStatusEffect;
import com.extraspellattributes.api.SpellStatusEffectInstance;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.MaceItem;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.spell_engine.api.spell.*;
import net.spell_engine.entity.SpellProjectile;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.WorldScheduler;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.internals.casting.SpellCasterEntity;
import net.spell_engine.particle.ParticleHelper;
import net.spell_engine.particle.Particles;
import net.spell_engine.utils.AnimationHelper;
import net.spell_engine.utils.SoundHelper;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.*;
import static com.cleannrooster.spellblades.items.attacks.Attacks.eleWhirlwind;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static net.spell_engine.internals.SpellHelper.imposeCooldown;
import static net.spell_engine.internals.SpellHelper.launchPoint;

public class Spells {
    public static void diebeam(CustomSpellHandler.Data data1) {
        Vec3d pos = data1.caster().getPos().add(0, data1.caster().getHeight() / 2, 0);

        float range = SpellRegistry.getSpell(Identifier.of(MOD_ID, "eldritchblast")).range;
        Sound soundEvent;
        soundEvent = SpellRegistry.getSpell(Identifier.of(MOD_ID, "eldritchblast")).release.sound;
        if (data1.caster().getWorld() instanceof ServerWorld world) {
            SoundHelper.playSound(world, data1.caster(), soundEvent);
        }
        for (int i = 2; i < SpellRegistry.getSpell(Identifier.of(MOD_ID, "eldritchblast")).range; i++) {
            Vec3d pos2 = pos.add(data1.caster().getRotationVec(1.0F).multiply(i));
            HitResult result = data1.caster().getWorld().raycast(new RaycastContext(data1.caster().getEyePos(), pos2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, data1.caster()));
            if (result.getType() != HitResult.Type.BLOCK && data1.caster().getWorld() instanceof ServerWorld world) {

                for(ServerPlayerEntity player: PlayerLookup.tracking(data1.caster())) {
                    world.spawnParticles(player,ParticleTypes.SONIC_BOOM,true,pos2.getX(),pos2.getY(),pos2.getZ(),1,0,0,0,0);
                }
                world.spawnParticles((ServerPlayerEntity)data1.caster(),ParticleTypes.SONIC_BOOM,true,pos2.getX(),pos2.getY(),pos2.getZ(),1,0,0,0,0);


            }
        }
        List<Entity> list = TargetHelper.targetsFromRaycast(data1.caster(), SpellRegistry.getSpell(Identifier.of(MOD_ID, "eldritchblast")).range, (target) -> {
            return !target.isSpectator() && target.canHit();
        });
        for (Entity entity : list) {
            SpellInfo spell = new SpellInfo(SpellRegistry.getSpell (Identifier.of(MOD_ID, "eldritchblast")),Identifier.of(MOD_ID, "eldritchblast"));

            SpellHelper.performImpacts(data1.caster().getWorld(), data1.caster(), entity, data1.caster(), spell,
                    data1.impactContext());

        }
    }
    public static void register(){

        CustomSpellHandler.register(Identifier.of(MOD_ID,"bladestorm"),(data) -> {
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            for(Entity entity : data1.targets()){
                if(entity instanceof LivingEntity living){
                    CycloneEntity cyclone = new CycloneEntity(CYCLONEENTITY,entity.getWorld());
                    cyclone.setColor(5);
                    cyclone.setOwner(data1.caster());
                    cyclone.setPosition(data1.caster().getPos().getX(),data1.caster().getPos().getY(),data1.caster().getPos().getZ());
                    cyclone.target = entity;
                    cyclone.context = data1.impactContext();
                    entity.getWorld().spawnEntity(cyclone);
                }
            }
            if(data1.targets().isEmpty()){
                CycloneEntity cyclone = new CycloneEntity(CYCLONEENTITY,data1.caster().getWorld());
                cyclone.setColor(5);
                cyclone.setOwner(data1.caster());
                cyclone.setPos(data1.caster().getPos().getX(),data1.caster().getPos().getY(),data1.caster().getPos().getZ());
                cyclone.context = data1.impactContext();
                data1.caster().getWorld().spawnEntity(cyclone);

            }

            return true;
        });
        CustomSpellHandler.register(Identifier.of(MOD_ID,"deathchill"),(data) -> {
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            for(Entity target : data1.targets()){
                if(target instanceof LivingEntity living){
                    living.addStatusEffect(new SpellStatusEffectInstance(DEATHCHILL,SpellRegistry.getSpell(Identifier.of(MOD_ID,"deathchill")), (float) SpellPower.getSpellPower(SpellSchools.FROST, data1.caster()).randomValue(), data1.caster(), 160,0,false,false,true,null));
                }
            }
            return true;
        });

        CustomSpellHandler.register(Identifier.of(MOD_ID,"coldbuff"),(data) -> {
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;

            for(Entity entity: data1.targets()){
                if(entity instanceof LivingEntity living){
                    living.setFrozenTicks(living.getMinFreezeDamageTicks()*20);

                    if(living.getStatusEffect(DEATHCHILL) instanceof SpellStatusEffectInstance spellStatusEffect){

                        SpellStatusEffectInstance spellStatusEffectInstance = new SpellStatusEffectInstance(DEATHCHILL,SpellRegistry.getSpell(Identifier.of(MOD_ID,"deathchill")), (float) SpellPower.getSpellPower(SpellSchools.FROST, data1.caster()).randomValue()*2,data1.caster(),160,0,false,false,true,null);
                        living.removeStatusEffect(DEATHCHILL);
                        living.addStatusEffect(spellStatusEffectInstance);

                    }
                    else {
                        living.addStatusEffect(new SpellStatusEffectInstance(DEATHCHILL, SpellRegistry.getSpell(Identifier.of(MOD_ID, "deathchill")), (float) SpellPower.getSpellPower(SpellSchools.FROST, data1.caster()).randomValue(), data1.caster(), 160, 0, false, false, true, null));

                    }
                }
            }

            return true;
        });
        CustomSpellHandler.register(Identifier.of(MOD_ID,"challenge"),(data) -> {
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;

            for(Entity entity: data1.targets()){
                if(entity instanceof LivingEntity living){
                    living.addStatusEffect(new SpellStatusEffectInstance(CHALLENGED, SpellRegistry.getSpell(Identifier.of(MOD_ID, "challenge")), (float) SpellPower.getSpellPower(SpellSchools.HEALING, data1.caster()).randomValue(), data1.caster(), 20*20, 0, false, false, true, null));
                    living.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING,20*20,0));

                }
                return true;

            }

            return false;
        });
        CustomSpellHandler.register(Identifier.of(MOD_ID,"overpower"),(data) -> {
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;

            for(Entity entity: data1.targets()){
                if(entity instanceof LivingEntity living){
                    if(data1.caster().getArmor() > living.getArmor()){
                        ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(SUNDERED,120,0,false,false,true,null));
                        ((LivingEntity) entity).playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE,0.5F,0.8F);
                    }
                    knockbackNearbyEntities(data1.caster().getWorld(), data1.caster(),entity);

                    SpellHelper.performImpacts(entity.getWorld(),data1.caster(),entity, data1.caster(), new SpellInfo(SpellRegistry.getSpell(Identifier.of(MOD_ID,"overpower")),Identifier.of(MOD_ID,"overpower")),data1.impactContext());
                }
                return true;

            }

            return false;
        });
        CustomSpellHandler.register(Identifier.of(MOD_ID,"frostbloom0"),(data) -> {
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            List<Entity> livingEntities = new ArrayList<>();
            livingEntities.addAll(data1.targets());
            for(Entity entity: data1.targets()){
                SpellHelper.performImpacts(entity.getWorld(), data1.caster(), entity,data1.caster(),new SpellInfo(SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostbloom0")),Identifier.of(MOD_ID,"frostbloom0"))
                        ,data1.impactContext());

                List<Entity> entities = entity.getWorld().getOtherEntities(entity,entity.getBoundingBox().expand(6), entity2 ->  entity2.isAttackable() && !livingEntities.contains(entity2) && TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, TargetHelper.Intent.HARMFUL,data1.caster(),entity2));
                livingEntities.addAll(entities);
                ((WorldScheduler)entity.getWorld()).schedule(10,() -> {
                    ParticleHelper.sendBatches(entity,SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostbloom0")).release.particles);
                    SoundHelper.playSound(entity.getWorld(),entity,SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostbloom0")).release.sound);
                    for(Entity entity1 : entities){
                        SpellHelper.performImpacts(entity1.getWorld(), data1.caster(), entity1,data1.caster(),new SpellInfo(SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostbloom0")),Identifier.of(MOD_ID,"frostbloom0"))
                                ,data1.impactContext());
                        List<Entity> entities2 = entity1.getWorld().getOtherEntities(entity1,entity1.getBoundingBox().expand(6), entity2 ->  entity2.isAttackable() && !livingEntities.contains(entity2)&& TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, TargetHelper.Intent.HARMFUL,data1.caster(),entity2));
                        livingEntities.addAll(entities2);
                        ParticleHelper.sendBatches(entity1,SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostbloom0")).release.particles);
                        SoundHelper.playSound(entity1.getWorld(),entity1,SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostbloom0")).release.sound);
                        ((WorldScheduler)entity1.getWorld()).schedule(10,() -> {
                            ParticleHelper.sendBatches(entity1,SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostbloom0")).release.particles);
                            SoundHelper.playSound(entity1.getWorld(),entity1,SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostbloom0")).release.sound);

                            for(Entity entity2 : entities2){

                                SpellHelper.performImpacts(entity2.getWorld(), data1.caster(), entity2,data1.caster(),new SpellInfo(SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostbloom0")),Identifier.of(MOD_ID,"frostbloom0"))
                                        ,data1.impactContext());
                            }
                        });
                    }
                });
            }

            return true;
        });

        CustomSpellHandler.register(Identifier.of(MOD_ID,"eldritchblast"),(data) -> {
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            Vec3d look = data1.caster().getRotationVec(1.0F).normalize().multiply((double)SpellRegistry.getSpell(Identifier.of(MOD_ID,"eldritchblast")).range);
            diebeam(data1);



            return true;
        });


        CustomSpellHandler.register(Identifier.of(MOD_ID,"whirlingblades"),(data) -> {
            SpellSchool actualSchool = SpellSchools.FIRE;
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            float modifier = SpellRegistry.getSpell(Identifier.of(MOD_ID,"whirlingblades")).impact[0].action.damage.spell_power_coefficient;
            data1.caster().velocityDirty = true;
            data1.caster().velocityModified = true;
            float f = data1.caster().getYaw();
            float g = data1.caster().getPitch();
            float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
            float k = -MathHelper.sin(g * 0.017453292F);
            float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
            float m = MathHelper.sqrt(h * h + k * k + l * l);
            float n = 3.0F * ((1.0F + (float)3) / 4.0F);
            h *= n / m;
            k *= n / m;
            l *= n / m;
            data1.caster().addVelocity((double)h, (double)k, (double)l);
            data1.caster().useRiptide(20, (float) (modifier*SpellPower.getSpellPower(SpellSchools.FROST,data1.caster()).randomValue()),data1.caster().getMainHandStack());
            if (data1.caster().isOnGround()) {
                float o = 1.1999999F;
                data1.caster().move(MovementType.SELF, new Vec3d(0.0D, 1.1999999284744263D, 0.0D));
            }

            SoundEvent soundEvent;
            soundEvent = SoundEvents.ITEM_TRIDENT_RIPTIDE_3.value();


            data1.caster().getWorld().playSoundFromEntity((PlayerEntity)null, data1.caster(), soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);

            return true;
        });

        CustomSpellHandler.register(Identifier.of(MOD_ID,"frostvert"),(data) -> {
            SpellSchool actualSchool = SpellSchools.FIRE;
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            if( data1.caster().isOnGround() && data1.caster() instanceof PlayerEntity && !data1.caster().getWorld().isClient()){
                List<Entity> list = TargetHelper.targetsFromArea(data1.caster(),data1.caster().getEyePos(), SpellRegistry.getSpell(Identifier.of(MOD_ID, "frostvert")).range,new Spell.Release.Target.Area(), target -> TargetHelper.allowedToHurt(data1.caster(),target) );
                for(Entity entity : list) {
                    if (entity instanceof LivingEntity living) {
                        SpellHelper.ImpactContext context = new SpellHelper.ImpactContext(1.0F, 1.0F, null, SpellPower.getSpellPower(SpellSchools.FIRE,data1.caster()), TargetHelper.TargetingMode.AREA);
                        SpellInfo spell = new SpellInfo(SpellRegistry.getSpell (Identifier.of(MOD_ID, "frostvert")),Identifier.of(MOD_ID, "frostvert"));

                        SpellHelper.performImpacts(data1.caster().getWorld(), data1.caster(), entity, data1.caster(), spell, context);

                    }
                }
                Supplier<Collection<ServerPlayerEntity>> trackingPlayers = Suppliers.memoize(() -> {
                    Collection<ServerPlayerEntity> playerEntities = PlayerLookup.tracking(data1.caster());
                    return playerEntities;
                });

                ParticleHelper.sendBatches(data1.caster(), SpellRegistry.getSpell(Identifier.of(MOD_ID, "frostvert")).release.particles);
                SoundHelper.playSound(data1.caster().getWorld(), data1.caster(), SpellRegistry.getSpell(Identifier.of(MOD_ID, "frostvert")).release.sound);
                AnimationHelper.sendAnimation((PlayerEntity) data1.caster(), (Collection)trackingPlayers.get(), SpellCast.Animation.RELEASE, SpellRegistry.getSpell(Identifier.of(MOD_ID, "frostvert")).release.animation, 1);
                return true;
            }
            float modifier = SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostvert")).impact[0].action.damage.spell_power_coefficient;
            float modifier2 = SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostvert")).impact[1].action.damage.spell_power_coefficient;

            data1.caster().fallDistance = 0;
            data1.caster().velocityDirty = true;
            data1.caster().velocityModified = true;
            float f = data1.caster().getYaw();
            float g = data1.caster().getPitch();
            float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
            float k = -MathHelper.sin(g * 0.017453292F);
            float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
            float m = MathHelper.sqrt(h * h + k * k + l * l);
            float n = 3.0F * ((1.0F + (float)3) / 4.0F);
            h *= n / m;
            k *= n / m;
            l *= n / m;
            data1.caster().addVelocity((double)h*0.6, (double)1, (double)l*0.6);
            data1.caster().addStatusEffect(new StatusEffectInstance(SLAMMING,100,0,false,false));
            data1.caster().setOnGround(false);
            data1.caster().setPosition(data1.caster().getPos().add(0,0.2,0));
            imposeCooldown(data1.caster(), Identifier.of(MOD_ID,"frostvert"), SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostvert")), data1.progress());
            ;
            return false;
        });
        CustomSpellHandler.register(Identifier.of(MOD_ID,"finalstrike"),(data) -> {
            SpellSchool actualSchool = SpellSchools.ARCANE;
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            float modifier = SpellRegistry.getSpell(Identifier.of(MOD_ID,"finalstrike")).impact[0].action.damage.spell_power_coefficient;
            float modifier2 = SpellRegistry.getSpell(Identifier.of(MOD_ID,"finalstrike")).impact[1].action.damage.spell_power_coefficient;
            SpellPower.Result power2 = SpellPower.getSpellPower(actualSchool, (LivingEntity) data1.caster());
            spellbladePassive(data1.caster(),SpellSchools.ARCANE,49);

            List<Entity> list = TargetHelper.targetsFromRaycast(data1.caster(),SpellRegistry.getSpell(Identifier.of(MOD_ID,"finalstrike")).range, Objects::nonNull);

            if(!data1.targets().isEmpty()) {
                if(data1.targets().get(data1.targets().size()-1) instanceof LivingEntity living){
                    Vec3d vec3 = data1.targets().get(data1.targets().size()-1).getPos().add(data1.caster().getRotationVec(1F).subtract(0,data1.caster().getRotationVec(1F).getY(),0).normalize().multiply(1+0.5+(data1.targets().get(data1.targets().size()-1).getBoundingBox().getLengthX() / 2)));
                    if(living.getWorld().getBlockState(new BlockPos((int) vec3.x,(int)vec3.y,(int) vec3.z)).shouldSuffocate(living.getWorld(),new BlockPos((int) vec3.x,(int)vec3.y,(int) vec3.z))) {
                        data1.caster().requestTeleport(living.getPos().getX(),living.getPos().getY(),living.getPos().getZ());
                    }
                    else{
                        data1.caster().requestTeleport(vec3.getX(), vec3.getY(), vec3.getZ());

                    }
                }
                for (Entity entity : data1.targets()) {

                    Attacks.attackAll(data1.caster(), List.of(entity), (float) modifier);

                    SpellPower.Result power = SpellPower.getSpellPower(actualSchool, (LivingEntity) data1.caster());
                    SpellPower.Vulnerability vulnerability = SpellPower.Vulnerability.none;
                    if (entity instanceof LivingEntity living) {
                        vulnerability = SpellPower.getVulnerability(living, actualSchool);
                    }
                    double amount = modifier2 * power.randomValue(vulnerability);
                    entity.timeUntilRegen = 0;

                    entity.damage(SpellDamageSource.player(actualSchool, data1.caster()), (float) amount);
                    ParticleHelper.sendBatches(entity,SpellRegistry.getSpell(Identifier.of(MOD_ID,"finalstrike")).impact[0].particles);
                    ParticleHelper.sendBatches(entity,SpellRegistry.getSpell(Identifier.of(MOD_ID,"finalstrike")).impact[1].particles);


                }
            }
            else {
                BlockHitResult result = data1.caster().getWorld().raycast(new RaycastContext(data1.caster().getEyePos(),data1.caster().getEyePos().add(data1.caster().getRotationVector().multiply(SpellRegistry.getSpell(Identifier.of(MOD_ID,"finalstrike")).range)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE,data1.caster()));
                if (!list.isEmpty()) {
                    Attacks.attackAll(data1.caster(), list, (float) modifier);
                    for (Entity entity : list) {
                        SpellPower.Result power = SpellPower.getSpellPower(actualSchool, (LivingEntity) data1.caster());
                        SpellPower.Vulnerability vulnerability = SpellPower.Vulnerability.none;
                        if (entity instanceof LivingEntity living) {
                            vulnerability = SpellPower.getVulnerability(living, actualSchool);
                        }
                        double amount = modifier * power.randomValue(vulnerability);
                        entity.timeUntilRegen = 0;

                        entity.damage(SpellDamageSource.player(actualSchool, data1.caster()), (float) amount);
                        ParticleHelper.sendBatches(entity,SpellRegistry.getSpell(Identifier.of(MOD_ID,"finalstrike")).impact[0].particles);
                        ParticleHelper.sendBatches(entity,SpellRegistry.getSpell(Identifier.of(MOD_ID,"finalstrike")).impact[1].particles);

                    }
                }
                if(result.getPos() != null) {
                    data1.caster().requestTeleport(result.getPos().getX(),result.getPos().getY(),result.getPos().getZ());
                }
            }
            return true;
        });
        CustomSpellHandler.register(Identifier.of(MOD_ID,"phoenixdive"),(data) -> {
            SpellSchool actualSchool = SpellSchools.ARCANE;
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;

            BlockHitResult result = data1.caster().getWorld().raycast(new RaycastContext(data1.caster().getEyePos(),data1.caster().getEyePos().add(data1.caster().getRotationVector().multiply(SpellRegistry.getSpell(Identifier.of(MOD_ID,"phoenixdive")).range)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE,data1.caster()));

            if(result.getPos() != null) {
                data1.caster().requestTeleport(result.getPos().getX(),result.getPos().getY(),result.getPos().getZ());


            }
            List<Entity> list = TargetHelper.targetsFromArea(data1.caster(),data1.caster().getEyePos(),8,new Spell.Release.Target.Area(), target -> TargetHelper.allowedToHurt(data1.caster(),target) );
            for(Entity entity : list){
                SpellInfo spell = new SpellInfo(SpellRegistry.getSpell (Identifier.of(MOD_ID, "phoenixdive")),Identifier.of(MOD_ID, "phoenixdive"));

                SpellHelper.performImpacts(data1.caster().getWorld(),data1.caster(),entity,data1.caster(),spell,data1.impactContext());
            }
            return true;
        });
        CustomSpellHandler.register(Identifier.of(MOD_ID,"snuffout"),(data) -> {
            SpellSchool actualSchool = SpellSchools.ARCANE;
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            for(Entity entity : data1.targets()){
                if(entity instanceof LivingEntity living && living.isOnFire()) {
                    List<Entity> list = TargetHelper.targetsFromArea(entity, entity.getEyePos(), 8, new Spell.Release.Target.Area(), target -> TargetHelper.allowedToHurt(data1.caster(), target));
                    SpellInfo spell = new SpellInfo(SpellRegistry.getSpell (Identifier.of(MOD_ID, "snuffout")),Identifier.of(MOD_ID, "snuffout"));

                    for (Entity entity1 : list) {
                        SpellHelper.performImpacts(data1.caster().getWorld(), data1.caster(), entity1, data1.caster(), spell, data1.impactContext());
                    }
                    SpellHelper.performImpacts(data1.caster().getWorld(), data1.caster(), entity, data1.caster(), spell, data1.impactContext());

                    entity.setFireTicks(0);
                }
            }
            return true;
        });

        CustomSpellHandler.register(Identifier.of(MOD_ID,"frostblink"),(data) -> {
            SpellSchool actualSchool = SpellSchools.ARCANE;
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            float modifier = SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostblink")).impact[0].action.damage.spell_power_coefficient;
            float modifier2 = SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostblink")).impact[1].action.damage.spell_power_coefficient;

            List<Entity> list = TargetHelper.targetsFromRaycast(data1.caster(),SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostblink")).range, Objects::nonNull);
            if(!data1.targets().isEmpty()) {
                Attacks.attackAll(data1.caster(), data1.targets(), (float) modifier);
                for (Entity entity : data1.targets()) {
                    SpellPower.Result power = SpellPower.getSpellPower(actualSchool, (LivingEntity) data1.caster());
                    SpellPower.Vulnerability vulnerability = SpellPower.Vulnerability.none;
                    if (entity instanceof LivingEntity living) {
                        vulnerability = SpellPower.getVulnerability(living, actualSchool);
                    }
                    double amount = modifier2 * power.randomValue(vulnerability);
                    entity.timeUntilRegen = 0;

                    entity.damage(SpellDamageSource.player(actualSchool, data1.caster()), (float) amount);
                    ParticleHelper.sendBatches(entity,SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostblink")).impact[0].particles);
                    ParticleHelper.sendBatches(entity,SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostblink")).impact[1].particles);

                    if(entity instanceof LivingEntity living) {
                        Vec3d vec3 = entity.getPos().add(data1.caster().getRotationVec(1F).subtract(0, data1.caster().getRotationVec(1F).getY(), 0).normalize().multiply(1 + 0.5 + (entity.getBoundingBox().getLengthX() / 2)));
                        if (living.getWorld().getBlockState(new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z)).shouldSuffocate(living.getWorld(), new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z))) {
                            data1.caster().requestTeleport(living.getPos().getX(), living.getPos().getY(), living.getPos().getZ());
                        } else {
                            data1.caster().requestTeleport(vec3.getX(), vec3.getY(), vec3.getZ());

                        }
                    }
                }
            }
            else {
                BlockHitResult result = data1.caster().getWorld().raycast(new RaycastContext(data1.caster().getEyePos(),data1.caster().getEyePos().add(data1.caster().getRotationVector().multiply(SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostblink")).range)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE,data1.caster()));
                if (!list.isEmpty()) {
                    Attacks.attackAll(data1.caster(), list, (float) modifier);
                    for (Entity entity : list) {
                        SpellPower.Result power = SpellPower.getSpellPower(actualSchool, (LivingEntity) data1.caster());
                        SpellPower.Vulnerability vulnerability = SpellPower.Vulnerability.none;
                        if (entity instanceof LivingEntity living) {
                            vulnerability = SpellPower.getVulnerability(living, actualSchool);
                        }
                        double amount = modifier2 * power.randomValue(vulnerability);
                        entity.timeUntilRegen = 0;

                        entity.damage(SpellDamageSource.player(actualSchool, data1.caster()), (float) amount);
                        ParticleHelper.sendBatches(entity,SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostblink")).impact[0].particles);
                        ParticleHelper.sendBatches(entity,SpellRegistry.getSpell(Identifier.of(MOD_ID,"frostblink")).impact[1].particles);

                    }
                }
                if(result.getPos() != null) {
                    data1.caster().requestTeleport(result.getPos().getX(),result.getPos().getY(),result.getPos().getZ());
                }
            }
            return true;
        });

        CustomSpellHandler.register(Identifier.of(MOD_ID,"flicker_strike"),(data) -> {

            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            float modifier = SpellRegistry.getSpell(Identifier.of(MOD_ID,"flicker_strike")).impact[0].action.damage.spell_power_coefficient;
            modifier *= 0.2;
            float modifier2 = SpellRegistry.getSpell(Identifier.of(MOD_ID,"flicker_strike")).impact[1].action.damage.spell_power_coefficient;
            modifier2 *= 0.2;
            SpellPower.Result power2 = SpellPower.getSpellPower(SpellSchools.FIRE, (LivingEntity) data1.caster());
            spellbladePassive(data1.caster(),SpellSchools.FIRE,49);

            if(data1.caster() instanceof PlayerDamageInterface player) {
                List<LivingEntity> list = new ArrayList<>();
                for(Entity entity: data1.targets()){
                    if(entity instanceof LivingEntity living && (!player.getList().contains(living) || (data1.targets().size() == 1 && data1.targets().contains(data1.caster().getAttacking())))){
                        list.add(living);
                    }
                }
                if(list.isEmpty()){
                    player.listRefresh();
                    return false;

                }
                LivingEntity closest = data1.caster().getWorld().getClosestEntity(list,TargetPredicate.DEFAULT, data1.caster(),data1.caster().getX(),data1.caster().getY(),data1.caster().getZ());
                if(closest != null) {
                    BlockPos pos = new BlockPos((int) (closest.getX() - ((closest.getWidth() + 1) * data1.caster().getRotationVec(1.0F).subtract(0, data1.caster().getRotationVec(1.0F).getY(), 0).normalize().getX())), (int) closest.getY(), (int) (closest.getZ() - ((closest.getWidth() + 1) * data1.caster().getRotationVec(1.0F).subtract(0, data1.caster().getRotationVec(1.0F).getY(), 0).normalize().getZ())));
                    Vec3d posvec = new Vec3d(closest.getX() - ((closest.getWidth() + 1) * data1.caster().getRotationVec(1.0F).subtract(0, data1.caster().getRotationVec(1.0F).getY(), 0).normalize().getX()), closest.getY(), closest.getZ() - ((closest.getWidth() + 1) * data1.caster().getRotationVec(1.0F).subtract(0, data1.caster().getRotationVec(1.0F).getY(), 0).normalize().getZ()));

                    if (closest != null && !closest.getWorld().getBlockState(pos).shouldSuffocate(closest.getWorld(), pos) && !closest.getWorld().getBlockState(pos.up()).shouldSuffocate(closest.getWorld(), pos.up())) {
                        data1.caster().requestTeleport(posvec.getX(), posvec.getY(), posvec.getZ());

                        Attacks.attackAll(data1.caster(), List.of(closest), modifier);
                        SpellPower.Result power = SpellPower.getSpellPower(SpellSchools.FIRE, (LivingEntity) data1.caster());
                        SpellPower.Vulnerability vulnerability = SpellPower.Vulnerability.none;
                        vulnerability = SpellPower.getVulnerability(closest, SpellSchools.FIRE);

                        double amount = modifier2 * power.randomValue(vulnerability);
                        closest.timeUntilRegen = 0;

                        closest.damage(SpellDamageSource.player(SpellSchools.FIRE, data1.caster()), (float) amount);

                        player.listAdd(closest);
                        return false;
                    }
                }
                else{
                    player.listRefresh();
                    return true;
                }

            }
            return false;
        });

        CustomSpellHandler.register(Identifier.of(MOD_ID,"eviscerate"),(data) -> {
            SpellSchool actualSchool = SpellSchools.FROST;
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            data1.targets().remove(data1.caster());
            SpellPower.Result power2 = SpellPower.getSpellPower(actualSchool, (LivingEntity) data1.caster());
            spellbladePassive(data1.caster(),SpellSchools.FROST,49);

            if(data1.targets().isEmpty()){
                if(data1.caster() instanceof SpellCasterEntity entity){
                    entity.setSpellCastProcess(null);
                }
                return true;
            }
            if(data1.caster() instanceof PlayerDamageInterface playerDamageInterface && playerDamageInterface.getLastAttacked() != null && playerDamageInterface.getLastAttacked() instanceof LivingEntity living && living.isDead()){
                playerDamageInterface.resetRepeats();
                playerDamageInterface.setLastAttacked(null);
            }
            if(data1.caster() instanceof PlayerDamageInterface playerDamageInterface && playerDamageInterface.getRepeats() >= 4){
                playerDamageInterface.resetRepeats();
                playerDamageInterface.setLastAttacked(null);

                if(data1.caster() instanceof SpellCasterEntity entity){
                    entity.setSpellCastProcess(null);
                }
                return true;
            }
            float modifier = SpellRegistry.getSpell(Identifier.of(MOD_ID,"eviscerate")).impact[0].action.damage.spell_power_coefficient;
            modifier *= 0.2;
            float modifier2 = SpellRegistry.getSpell(Identifier.of(MOD_ID,"eviscerate")).impact[1].action.damage.spell_power_coefficient;
            modifier2 *= 0.2;

            if(data1.caster() instanceof PlayerDamageInterface playerDamageInterface && playerDamageInterface.getLastAttacked() != null && data1.targets().contains(playerDamageInterface.getLastAttacked())) {
                EntityAttributeModifier modifier1 = new EntityAttributeModifier(Identifier.of("knockback"),1, EntityAttributeModifier.Operation.ADD_VALUE);
                ImmutableMultimap.Builder<RegistryEntry<EntityAttribute>, EntityAttributeModifier> builder = ImmutableMultimap.builder();
                builder.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, modifier1);

                ((LivingEntity)playerDamageInterface.getLastAttacked()).getAttributes().addTemporaryModifiers(builder.build());

                Attacks.attackAll(data1.caster(), List.of(playerDamageInterface.getLastAttacked()), (float) modifier);
                playerDamageInterface.repeat();
                SpellPower.Result power = SpellPower.getSpellPower(actualSchool, (LivingEntity) data1.caster());
                SpellPower.Vulnerability vulnerability = SpellPower.Vulnerability.none;
                if (playerDamageInterface.getLastAttacked() instanceof LivingEntity living) {
                    vulnerability = SpellPower.getVulnerability(living, actualSchool);
                }
                double amount = modifier2 * power.randomValue(vulnerability);
                playerDamageInterface.getLastAttacked().timeUntilRegen = 0;

                playerDamageInterface.getLastAttacked().damage(SpellDamageSource.player(actualSchool, data1.caster()), (float) amount);
                if(playerDamageInterface.getLastAttacked() instanceof LivingEntity living)
                    living.getAttributes().removeModifiers(builder.build());
                Entity living = playerDamageInterface.getLastAttacked();
                Vec3d pos = living.getPos().add(0,living.getHeight()/2,0).subtract(new Vec3d(0,0,4*living.getBoundingBox().getLengthX()).rotateX(living.getWorld().getRandom().nextFloat()*360));

                for(int i = 0; i < 20; i++) {
                    Vec3d pos2 = pos.add(living.getPos().add(0,living.getHeight()/2,0).subtract(pos).multiply(0.1*i));
                    if(living.getWorld() instanceof ServerWorld serverWorld) {
                        for(ServerPlayerEntity player : PlayerLookup.tracking(living)) {
                            //serverWorld.spawnParticles(player,Particles.snowflake.particleType,true, pos2.x, pos2.y, pos2.z, 1,0, 0, 0,0);
                            serverWorld.spawnParticles(player, Particles.frost_shard.particleType,true, pos2.x, pos2.y, pos2.z, 1,0, 0, 0,0);
                            serverWorld.spawnParticles(player,Particles.frost_hit.particleType,true, pos2.x, pos2.y, pos2.z, 1,0, 0, 0,0);

                        }
                    }
                }
                living.getWorld().addParticle(ParticleTypes.SWEEP_ATTACK, true,living.getX(),living.getY(),living.getZ(),0,0,0);

                return false;
            }
            if(data1.caster() instanceof PlayerDamageInterface playerDamageInterface && !data1.targets().isEmpty()) {
                Entity entity = playerDamageInterface.getLastAttacked();
                List<LivingEntity> list = new ArrayList<>();
                for(Entity entity1 : data1.targets()){
                    if(entity1 instanceof LivingEntity living){
                        list.add(living);
                    }
                }
                if(entity == null || !data1.targets().contains(entity)) {
                    entity = data1.caster().getWorld().getClosestEntity(list, TargetPredicate.DEFAULT,data1.caster(),data1.caster().getX(),data1.caster().getY(),data1.caster().getZ());
                }
                else{
                    playerDamageInterface.setLastAttacked(null);
                    playerDamageInterface.resetRepeats();
                    if(data1.caster() instanceof SpellCasterEntity antity){
                        antity.setSpellCastProcess(null);
                    }
                    return true;
                }

                if(entity != null) {
                    EntityAttributeModifier modifier1 = new EntityAttributeModifier(Identifier.of("knockback"),1, EntityAttributeModifier.Operation.ADD_VALUE);
                    ImmutableMultimap.Builder<RegistryEntry<EntityAttribute>, EntityAttributeModifier> builder = ImmutableMultimap.builder();
                    builder.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, modifier1);

                    ((LivingEntity)entity).getAttributes().addTemporaryModifiers(builder.build());

                    Attacks.attackAll(data1.caster(), List.of(entity), (float) modifier);
                    playerDamageInterface.setLastAttacked(entity);
                    SpellPower.Result power = SpellPower.getSpellPower(actualSchool, (LivingEntity) data1.caster());
                    SpellPower.Vulnerability vulnerability = SpellPower.Vulnerability.none;
                    if (entity instanceof LivingEntity living) {
                        vulnerability = SpellPower.getVulnerability(living, actualSchool);
                    }
                    double amount = modifier2 * power.randomValue(vulnerability);
                    entity.timeUntilRegen = 0;

                    entity.damage(SpellDamageSource.player(actualSchool, data1.caster()), (float) amount);

                    if(entity instanceof LivingEntity living)
                        living.getAttributes().removeModifiers(builder.build());
                    Entity living = playerDamageInterface.getLastAttacked();
                    Vec3d pos = living.getPos().add(0,living.getHeight()/2,0).subtract(new Vec3d(0,0,4*living.getBoundingBox().getLengthX()).rotateX(living.getWorld().getRandom().nextFloat()*360));

                    for(int i = 0; i < 20; i++) {
                        Vec3d pos2 = pos.add(living.getPos().add(0,living.getHeight()/2,0).subtract(pos).multiply(0.1*i));
                        if(living.getWorld() instanceof ServerWorld serverWorld) {
                            for(ServerPlayerEntity player : PlayerLookup.tracking(living)) {
                                //serverWorld.spawnParticles(player,Particles.snowflake.particleType,true, pos2.x, pos2.y, pos2.z, 1,0, 0, 0,0);
                                serverWorld.spawnParticles(player,Particles.frost_shard.particleType,true, pos2.x, pos2.y, pos2.z, 1,0, 0, 0,0);
                                serverWorld.spawnParticles(player,Particles.frost_hit.particleType,true, pos2.x, pos2.y, pos2.z, 1,0, 0, 0,0);

                            }
                        }
                    }
                    living.getWorld().addParticle(ParticleTypes.SWEEP_ATTACK, true,living.getX(),living.getY(),living.getZ(),0,0,0);


                }
            }
            return false;
        });

        CustomSpellHandler.register(Identifier.of(MOD_ID,"fireflourish"),(data) -> {
            SpellSchool actualSchool = SpellSchools.FIRE;
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            spellbladePassive(data1.caster(),SpellSchools.FIRE,49);

            float modifier = SpellRegistry.getSpell(Identifier.of(MOD_ID,"fireflourish")).impact[0].action.damage.spell_power_coefficient;
            float modifier2 = SpellRegistry.getSpell(Identifier.of(MOD_ID,"fireflourish")).impact[0].action.damage.spell_power_coefficient;

            Attacks.attackAll(data1.caster(),data1.targets(),(float)modifier);
            for(Entity entity: data1.targets()){
                SpellPower.Result power = SpellPower.getSpellPower(actualSchool, (LivingEntity) data1.caster());
                SpellPower.Vulnerability vulnerability = SpellPower.Vulnerability.none;
                if(entity instanceof LivingEntity living) {
                    vulnerability = SpellPower.getVulnerability(living, actualSchool);
                }
                double amount = modifier2 *  power.randomValue(vulnerability);
                entity.timeUntilRegen = 0;

                entity.damage(SpellDamageSource.player(actualSchool,data1.caster()), (float) amount);
            }
            int iii = -200;
            for (int i = 0; i < 5; i++) {

                for (int ii = 0; ii < 80; ii++) {

                    iii++;

                    int finalIii = iii;
                    int finalI = i;
                    int finalIi = ii;
                    ((WorldScheduler)data1.caster().getWorld()).schedule(i+1,() ->{
                        if(data1.caster().getWorld() instanceof ServerWorld serverWorld) {
                            double x = 0;
                            double x2 = 0;

                            double z = 0;
                            x =  ((4.5*data1.caster().getWidth() + 2*data1.caster().getWidth() * sin(20 *  ((double) finalIii /(double)(4*31.74)))) * cos(((double) finalIii /(double)(4*31.74))));
                            x2 =  -((4.5*data1.caster().getWidth() + 2*data1.caster().getWidth() * sin(20 *  ((double) finalIii /(double)(4*31.74)))) * cos(((double) finalIii /(double)(4*31.74))));

                            z =  ((4.5*data1.caster().getWidth() + 2*data1.caster().getWidth() * sin(20 * ((double) finalIii /(double)(4*31.74)))) * sin(((double) finalIii /(double)(4*31.74))));
                            float f7 = data1.caster().getYaw() % 360;
                            float f = data1.caster().getPitch();
                            Vec3d vec3d = Attacks.rotate(x,0,z,Math.toRadians(-f7),0,0);
                            Vec3d vec3d2 = Attacks.rotate(x2,0,z,Math.toRadians(-f7),0,0);
                            Vec3d vec3d3 = vec3d.add(data1.caster().getEyePos().getX(),data1.caster().getEyeY(),data1.caster().getEyePos().getZ());
                            Vec3d vec3d4 = vec3d2.add(data1.caster().getEyePos().getX(),data1.caster().getEyeY(),data1.caster().getEyePos().getZ());

                            double y = data1.caster().getY()+data1.caster().getHeight()/2;



                            for(ServerPlayerEntity player : PlayerLookup.tracking(data1.caster())) {
                                if (finalIi % 2 == 1) {
                                    serverWorld.spawnParticles(player, ParticleTypes.SMOKE,true, vec3d3.getX(), y, vec3d3.getZ(), 1, 0, 0, 0, 0);
                                    serverWorld.spawnParticles(player , ParticleTypes.SMOKE,true, vec3d4.getX(), y, vec3d4.getZ(), 1, 0, 0, 0, 0);
                                }
                                serverWorld.spawnParticles(player,Particles.flame.particleType, true, vec3d3.getX(), y, vec3d3.getZ(), 1, 0, 0, 0, 0);
                                serverWorld.spawnParticles(player,Particles.flame.particleType, true, vec3d4.getX(), y, vec3d4.getZ(), 1, 0, 0, 0, 0);
                            }
                            if(data1.caster() instanceof ServerPlayerEntity player) {
                                if (finalIi % 2 == 1) {
                                    serverWorld.spawnParticles(player, ParticleTypes.SMOKE, true, vec3d3.getX(), y, vec3d3.getZ(), 1, 0, 0, 0, 0);
                                    serverWorld.spawnParticles(player, ParticleTypes.SMOKE, true, vec3d4.getX(), y, vec3d4.getZ(), 1, 0, 0, 0, 0);
                                }
                                serverWorld.spawnParticles(player, Particles.flame.particleType, true, vec3d3.getX(), y, vec3d3.getZ(), 1, 0, 0, 0, 0);
                                serverWorld.spawnParticles(player, Particles.flame.particleType, true, vec3d4.getX(), y, vec3d4.getZ(), 1, 0, 0, 0, 0);
                            }
                        }
                    });

                }


            }
            return true;
        });
        CustomSpellHandler.register(Identifier.of(MOD_ID,"arcaneflourish"),(data) -> {
            SpellSchool actualSchool = SpellSchools.ARCANE;
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            float modifier = SpellRegistry.getSpell(Identifier.of(MOD_ID,"arcaneflourish")).impact[0].action.damage.spell_power_coefficient;
            float modifier2 = SpellRegistry.getSpell(Identifier.of(MOD_ID,"arcaneflourish")).impact[1].action.damage.spell_power_coefficient;

            SpellPower.Result power2 = SpellPower.getSpellPower(actualSchool, (LivingEntity) data1.caster());
            spellbladePassive(data1.caster(),SpellSchools.ARCANE,49);

            Attacks.attackAll(data1.caster(),data1.targets(),(float)modifier);
            for(Entity entity: data1.targets()){
                SpellPower.Result power = SpellPower.getSpellPower(actualSchool, (LivingEntity) data1.caster());
                SpellPower.Vulnerability vulnerability = SpellPower.Vulnerability.none;
                if(entity instanceof LivingEntity living) {
                    vulnerability = SpellPower.getVulnerability(living, actualSchool);
                }
                double amount = modifier2 *  power.randomValue(vulnerability);
                entity.timeUntilRegen = 0;

                entity.damage(SpellDamageSource.player(actualSchool,data1.caster()), (float) amount);
            }
            int iii = -200;
            for (int i = 0; i < 5; i++) {

                for (int ii = 0; ii < 80; ii++) {

                    iii++;

                    int finalIii = iii;
                    int finalI = i;
                    int finalIi = ii;
                    ((WorldScheduler)data1.caster().getWorld()).schedule(i+1,() ->{
                        if(data1.caster().getWorld() instanceof ServerWorld serverWorld) {
                            double x = 0;
                            double x2 = 0;

                            double z = 0;
                            x =  ((4.5*data1.caster().getWidth() + 2*data1.caster().getWidth() * sin(20 *  ((double) finalIii /(double)(4*31.74)))) * cos(((double) finalIii /(double)(4*31.74))));
                            x2 =  -((4.5*data1.caster().getWidth() + 2*data1.caster().getWidth() * sin(20 *  ((double) finalIii /(double)(4*31.74)))) * cos(((double) finalIii /(double)(4*31.74))));

                            z =  ((4.5*data1.caster().getWidth() + 2*data1.caster().getWidth() * sin(20 * ((double) finalIii /(double)(4*31.74)))) * sin(((double) finalIii /(double)(4*31.74))));
                            float f7 = data1.caster().getYaw() % 360;
                            float f = data1.caster().getPitch();
                            Vec3d vec3d = Attacks.rotate(x,0,z,Math.toRadians(-f7),0,0);
                            Vec3d vec3d2 = Attacks.rotate(x2,0,z,Math.toRadians(-f7),0,0);
                            Vec3d vec3d3 = vec3d.add(data1.caster().getEyePos().getX(),data1.caster().getEyeY(),data1.caster().getEyePos().getZ());
                            Vec3d vec3d4 = vec3d2.add(data1.caster().getEyePos().getX(),data1.caster().getEyeY(),data1.caster().getEyePos().getZ());

                            double y = data1.caster().getY()+data1.caster().getHeight()/2;



                            for(ServerPlayerEntity player : PlayerLookup.tracking(data1.caster())) {
                                if (finalIi % 2 == 1) {
                                    serverWorld.spawnParticles(player, ParticleTypes.FIREWORK,true, vec3d3.getX(), y, vec3d3.getZ(), 1, 0, 0, 0, 0);
                                    serverWorld.spawnParticles(player , ParticleTypes.FIREWORK,true, vec3d4.getX(), y, vec3d4.getZ(), 1, 0, 0, 0, 0);
                                }
                                serverWorld.spawnParticles(player,Particles.arcane_spell.particleType, true, vec3d3.getX(), y, vec3d3.getZ(), 1, 0, 0, 0, 0);
                                serverWorld.spawnParticles(player,Particles.arcane_spell.particleType, true, vec3d4.getX(), y, vec3d4.getZ(), 1, 0, 0, 0, 0);
                            }
                            if(data1.caster() instanceof ServerPlayerEntity player) {
                                if (finalIi % 2 == 1) {
                                    serverWorld.spawnParticles(player, ParticleTypes.FIREWORK, true, vec3d3.getX(), y, vec3d3.getZ(), 1, 0, 0, 0, 0);
                                    serverWorld.spawnParticles(player, ParticleTypes.FIREWORK, true, vec3d4.getX(), y, vec3d4.getZ(), 1, 0, 0, 0, 0);
                                }
                                serverWorld.spawnParticles(player, Particles.arcane_spell.particleType, true, vec3d3.getX(), y, vec3d3.getZ(), 1, 0, 0, 0, 0);
                                serverWorld.spawnParticles(player, Particles.arcane_spell.particleType, true, vec3d4.getX(), y, vec3d4.getZ(), 1, 0, 0, 0, 0);
                            }

                        }
                    });

                }


            }

            return true;
        });
        CustomSpellHandler.register(Identifier.of(MOD_ID,"tempest"),(data) -> {
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;

            spellbladePassive(data1.caster(),SpellSchools.FROST,49);
            if(data1.caster().getWorld() instanceof ServerWorld world){
                if(world.getEntitiesByType(TypeFilter.instanceOf(CycloneEntity.class), cyclone -> {
                    if( cyclone.getOwner() == data1.caster()&& cyclone.getColor() != 5){
                        return true;
                    }
                    return false;
                }).isEmpty()){
                    CycloneEntity cyclone = new CycloneEntity(CYCLONEENTITY,data1.caster().getWorld());
                    cyclone.setPos(data1.caster().getX(),data1.caster().getY(),data1.caster().getZ());
                    cyclone.setColor(3);
                    cyclone.setOwner(data1.caster());

                    data1.caster().getWorld().spawnEntity(cyclone);

                }
                for(Entity entity : data1.targets()) {
                    SpellHelper.performImpacts(data1.caster().getWorld(), data1.caster(),entity,data1.caster(),new SpellInfo(
                            SpellRegistry.getSpell(Identifier.of(MOD_ID,"tempest")),Identifier.of(MOD_ID,"tempest")),data1.impactContext() );
                }
            }

            return false;
        });
        CustomSpellHandler.register(Identifier.of(MOD_ID,"whirlwind"),(data) -> {

            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;

            if(data1.caster().getWorld() instanceof ServerWorld world){
                if(world.getEntitiesByType(TypeFilter.instanceOf(CycloneEntity.class), cyclone -> {
                    if( cyclone.getOwner() == data1.caster()&& cyclone.getColor() != 5){
                        return true;
                    }
                    return false;
                }).isEmpty()){
                    CycloneEntity cyclone = new CycloneEntity(CYCLONEENTITY,data1.caster().getWorld());
                    cyclone.setPos(data1.caster().getX(),data1.caster().getY(),data1.caster().getZ());
                    cyclone.setColor(1);
                    cyclone.setOwner(data1.caster());

                    data1.caster().getWorld().spawnEntity(cyclone);

                }
                for(Entity entity : data1.targets()) {
                    SpellHelper.performImpacts(data1.caster().getWorld(), data1.caster(),entity,data1.caster(),new SpellInfo(
                            SpellRegistry.getSpell(Identifier.of(MOD_ID,"whirlwind")),Identifier.of(MOD_ID,"whirlwind")),data1.impactContext() );
                }
            }


            return false;
        });
        CustomSpellHandler.register(Identifier.of(MOD_ID,"reckoning"),(data) -> {
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;

            if(data1.caster().getWorld() instanceof ServerWorld world){
                if(world.getEntitiesByType(TypeFilter.instanceOf(CycloneEntity.class), cyclone -> {
                    if( cyclone.getOwner() == data1.caster()&& cyclone.getColor() != 5){
                        return true;
                    }
                    return false;
                }).isEmpty()){
                    CycloneEntity cyclone = new CycloneEntity(CYCLONEENTITY,data1.caster().getWorld());
                    cyclone.setPos(data1.caster().getX(),data1.caster().getY(),data1.caster().getZ());
                    cyclone.setColor(1);
                    cyclone.setOwner(data1.caster());

                    data1.caster().getWorld().spawnEntity(cyclone);

                }
                for(Entity entity : data1.targets()) {
                    SpellHelper.performImpacts(data1.caster().getWorld(), data1.caster(),entity,data1.caster(),new SpellInfo(
                            SpellRegistry.getSpell(Identifier.of(MOD_ID,"reckoning")),Identifier.of(MOD_ID,"reckoning")),data1.impactContext() );
                }
            }

            return false;
        });

        CustomSpellHandler.register(Identifier.of(MOD_ID,"maelstrom"),(data) -> {
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;

            if(data1.caster().getWorld() instanceof ServerWorld world){
                if(world.getEntitiesByType(TypeFilter.instanceOf(CycloneEntity.class), cyclone -> {
                    if( cyclone.getOwner() == data1.caster()&& cyclone.getColor() != 5){
                        return true;
                    }
                    return false;
                }).isEmpty()){
                    CycloneEntity cyclone = new CycloneEntity(CYCLONEENTITY,data1.caster().getWorld());
                    cyclone.setPos(data1.caster().getX(),data1.caster().getY(),data1.caster().getZ());
                    cyclone.setColor(2);
                    cyclone.setOwner(data1.caster());

                    data1.caster().getWorld().spawnEntity(cyclone);

                }
                for(Entity entity : data1.targets()) {
                    SpellHelper.performImpacts(data1.caster().getWorld(), data1.caster(),entity,data1.caster(),new SpellInfo(
                            SpellRegistry.getSpell(Identifier.of(MOD_ID,"maelstrom")),Identifier.of(MOD_ID,"maelstrom")),data1.impactContext() );
                }
            }

            return false;
        });
        CustomSpellHandler.register(Identifier.of(MOD_ID,"inferno"),(data) -> {
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;

            if(data1.caster().getWorld() instanceof ServerWorld world){
                if(world.getEntitiesByType(TypeFilter.instanceOf(CycloneEntity.class), cyclone -> {
                    if( cyclone.getOwner() == data1.caster()&& cyclone.getColor() != 5){
                        return true;
                    }
                    return false;
                }).isEmpty()){
                    CycloneEntity cyclone = new CycloneEntity(CYCLONEENTITY,data1.caster().getWorld());
                    cyclone.setPos(data1.caster().getX(),data1.caster().getY(),data1.caster().getZ());
                    cyclone.setColor(4);
                    cyclone.setOwner(data1.caster());

                    data1.caster().getWorld().spawnEntity(cyclone);

                }
                for(Entity entity : data1.targets()) {
                    SpellHelper.performImpacts(data1.caster().getWorld(), data1.caster(),entity,data1.caster(),new SpellInfo(
                            SpellRegistry.getSpell(Identifier.of(MOD_ID,"inferno")),Identifier.of(MOD_ID,"inferno")),data1.impactContext() );
                }
            }

            return false;
        });
        CustomSpellHandler.register(Identifier.of(MOD_ID,"smite"),(data) -> {

            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            SpellSchool actualSchool = SpellSchools.HEALING;

            SpellPower.Result power2 = SpellPower.getSpellPower(actualSchool, (LivingEntity) data1.caster());

            float modifier = SpellRegistry.getSpell(Identifier.of(MOD_ID,"smite")).impact[0].action.damage.spell_power_coefficient;
            float modifier2 = SpellRegistry.getSpell(Identifier.of(MOD_ID,"smite")).impact[1].action.damage.spell_power_coefficient;

            Attacks.attackAll(data1.caster(),data1.targets(),(float)modifier);
            for(Entity target : data1.targets()){

                if(target instanceof LivingEntity living && data1.caster() instanceof SpellCasterEntity caster && SpellRegistry.getSpell(Identifier.of(MOD_ID,"fervoussmite")) != null){
                    SpellPower.Result result = new SpellPower.Result(SpellSchools.HEALING, modifier2 * SpellPower.getSpellPower(SpellSchools.HEALING,data1.caster()).baseValue(), data1.impactContext().power().criticalChance(), data1.impactContext().power().criticalDamage());
                    SpellInfo spell = new SpellInfo(SpellRegistry.getSpell (Identifier.of(MOD_ID, "fervoussmite")),Identifier.of(MOD_ID, "fervoussmite"));
                    knockbackNearbyEntities(data1.caster().getWorld(), data1.caster(),target);

                    SpellHelper.performImpacts(data1.caster().getWorld(), data1.caster(), target, data1.caster(), spell ,
                            new SpellHelper.ImpactContext(1, 1, null, result, TargetHelper.TargetingMode.DIRECT));

                }
            }
            return true;
        });
        CustomSpellHandler.register(Identifier.of(MOD_ID,"gem_barrage"),(data) -> {

            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;
            Identifier spellID = Identifier.of(MOD_ID,"gem_barrage2");
            Spell spell = SpellRegistry.getSpell(Identifier.of(MOD_ID,"gem_barrage2"));
            for(Entity target : data1.targets()){
                SpellProjectile projectile = new SpellProjectile(data1.caster().getWorld(), data1.caster(), data1.caster().getX(), data1.caster().getEyeY(), data1.caster().getZ(), SpellProjectile.Behaviour.FLY,
                        Identifier.of(MOD_ID,"gem_barrage2"), target, data1.impactContext(), spell.release.target.projectile.projectile.perks);
                SpellProjectile projectile2 = new SpellProjectile(data1.caster().getWorld(), data1.caster(), data1.caster().getX(), data1.caster().getEyeY(), data1.caster().getZ(), SpellProjectile.Behaviour.FLY,
                        Identifier.of(MOD_ID,"gem_barrage2"), target, data1.impactContext(), spell.release.target.projectile.projectile.perks);
                SpellProjectile projectile3 = new SpellProjectile(data1.caster().getWorld(), data1.caster(), data1.caster().getX(), data1.caster().getEyeY(), data1.caster().getZ(), SpellProjectile.Behaviour.FLY,
                        Identifier.of(MOD_ID,"gem_barrage2"), target, data1.impactContext(), spell.release.target.projectile.projectile.perks);
                projectile2.setVelocity(data1.caster(),data1.caster().getPitch(),data1.caster().getYaw()-90,0,spell.release.target.projectile.launch_properties.velocity,0);
                projectile.setVelocity(data1.caster(),data1.caster().getPitch(),data1.caster().getYaw()+90,0,spell.release.target.projectile.launch_properties.velocity,0);
                projectile3.setVelocity(data1.caster(),data1.caster().getPitch()-90,data1.caster().getYaw(),0,spell.release.target.projectile.launch_properties.velocity,0);
                projectile.setPos(launchPoint(data1.caster()).getX(),launchPoint(data1.caster()).getY(),launchPoint(data1.caster()).getZ());
                projectile2.setPos(launchPoint(data1.caster()).getX(),launchPoint(data1.caster()).getY(),launchPoint(data1.caster()).getZ());
                projectile3.setPos(launchPoint(data1.caster()).getX(),launchPoint(data1.caster()).getY(),launchPoint(data1.caster()).getZ());
                data1.caster().getWorld().spawnEntity(projectile);
                data1.caster().getWorld().spawnEntity(projectile2);
                data1.caster().getWorld().spawnEntity(projectile3);
                return true;

            }
            return false;
        });

        CustomSpellHandler.register(Identifier.of(MOD_ID,"frostflourish"),(data) -> {

            SpellSchool actualSchool = SpellSchools.FROST;
            CustomSpellHandler.Data data1 = (CustomSpellHandler.Data) data;


            spellbladePassive(data1.caster(),SpellSchools.FROST,49);

            float modifier = SpellRegistry.getSpell(Identifier.of(MOD_ID, "frostflourish")).impact[0].action.damage.spell_power_coefficient;
            float modifier2 = SpellRegistry.getSpell(Identifier.of(MOD_ID, "frostflourish")).impact[0].action.damage.spell_power_coefficient;

            Attacks.attackAll(data1.caster(), data1.targets(), (float) modifier);
            for (Entity entity : data1.targets()) {
                if (data1.caster() instanceof ServerPlayerEntity serverPlayerEntity) {
                    ServerWorld serverWorld = (ServerWorld)serverPlayerEntity.getWorld();
                    if (serverPlayerEntity.shouldIgnoreFallDamageFromCurrentExplosion() && serverPlayerEntity.currentExplosionImpactPos != null) {
                        if (serverPlayerEntity.currentExplosionImpactPos.y > serverPlayerEntity.getPos().y) {
                            serverPlayerEntity.currentExplosionImpactPos = serverPlayerEntity.getPos();
                        }
                    } else {
                        serverPlayerEntity.currentExplosionImpactPos = serverPlayerEntity.getPos();
                    }

                    serverPlayerEntity.setIgnoreFallDamageFromCurrentExplosion(true);
                    serverPlayerEntity.setVelocity(serverPlayerEntity.getVelocity().withAxis(Direction.Axis.Y, 0.009999999776482582));
                    serverPlayerEntity.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverPlayerEntity));
                    if (entity.isOnGround()) {
                        serverPlayerEntity.setSpawnExtraParticlesOnFall(true);
                        SoundEvent soundEvent = serverPlayerEntity.fallDistance > 5.0F ? SoundEvents.ITEM_MACE_SMASH_GROUND_HEAVY : SoundEvents.ITEM_MACE_SMASH_GROUND;
                        serverWorld.playSound((PlayerEntity)null, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), soundEvent, serverPlayerEntity.getSoundCategory(), 1.0F, 1.0F);
                    } else {
                        serverWorld.playSound((PlayerEntity)null, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), SoundEvents.ITEM_MACE_SMASH_AIR, serverPlayerEntity.getSoundCategory(), 1.0F, 1.0F);
                    }

                }
                SpellPower.Result power = SpellPower.getSpellPower(actualSchool, (LivingEntity) data1.caster());
                SpellPower.Vulnerability vulnerability = SpellPower.Vulnerability.none;
                if (entity instanceof LivingEntity living) {
                    vulnerability = SpellPower.getVulnerability(living, actualSchool);
                }
                double amount = modifier2 * power.randomValue(vulnerability);
                entity.timeUntilRegen = 0;

                entity.damage(SpellDamageSource.player(actualSchool, data1.caster()), (float) amount);
            }
            int iii = -200;

            for (int i = 0; i < 5; i++) {

                for (int ii = 0; ii < 80; ii++) {

                    iii++;

                    int finalIii = iii;
                    int finalI = i;
                    int finalIi = ii;
                    ((WorldScheduler) data1.caster().getWorld()).schedule(i + 1, () -> {
                        if (data1.caster().getWorld() instanceof ServerWorld serverWorld) {
                            double x = 0;
                            double x2 = 0;

                            double z = 0;
                            x = ((4.5 * data1.caster().getWidth() + 2 * data1.caster().getWidth() * sin(20 * ((double) finalIii / (double) (4 * 31.74)))) * cos(((double) finalIii / (double) (4 * 31.74))));
                            x2 = -((4.5 * data1.caster().getWidth() + 2 * data1.caster().getWidth() * sin(20 * ((double) finalIii / (double) (4 * 31.74)))) * cos(((double) finalIii / (double) (4 * 31.74))));

                            z = ((4.5 * data1.caster().getWidth() + 2 * data1.caster().getWidth() * sin(20 * ((double) finalIii / (double) (4 * 31.74)))) * sin(((double) finalIii / (double) (4 * 31.74))));
                            float f7 = data1.caster().getYaw() % 360;
                            float f = data1.caster().getPitch();
                            Vec3d vec3d = Attacks.rotate(x, 0, z, Math.toRadians(-f7), 0, 0);
                            Vec3d vec3d2 = Attacks.rotate(x2, 0, z, Math.toRadians(-f7), 0, 0);
                            Vec3d vec3d3 = vec3d.add(data1.caster().getEyePos().getX(), data1.caster().getEyeY(), data1.caster().getEyePos().getZ());
                            Vec3d vec3d4 = vec3d2.add(data1.caster().getEyePos().getX(), data1.caster().getEyeY(), data1.caster().getEyePos().getZ());

                            double y = data1.caster().getY() + data1.caster().getHeight() / 2;


                            for (ServerPlayerEntity player : PlayerLookup.tracking(data1.caster())) {
                                if (finalIi % 2 == 1) {
                                    serverWorld.spawnParticles(player, Particles.snowflake.particleType, true, vec3d3.getX(), y, vec3d3.getZ(), 1, 0, 0, 0, 0);
                                    serverWorld.spawnParticles(player, Particles.snowflake.particleType, true, vec3d4.getX(), y, vec3d4.getZ(), 1, 0, 0, 0, 0);
                                }
                                serverWorld.spawnParticles(player, Particles.frost_shard.particleType, true, vec3d3.getX(), y, vec3d3.getZ(), 1, 0, 0, 0, 0);
                                serverWorld.spawnParticles(player, Particles.frost_shard.particleType, true, vec3d4.getX(), y, vec3d4.getZ(), 1, 0, 0, 0, 0);
                            }
                            if (data1.caster() instanceof ServerPlayerEntity player) {
                                if (finalIi % 2 == 1) {
                                    serverWorld.spawnParticles(player, Particles.snowflake.particleType, true, vec3d3.getX(), y, vec3d3.getZ(), 1, 0, 0, 0, 0);
                                    serverWorld.spawnParticles(player, Particles.snowflake.particleType, true, vec3d4.getX(), y, vec3d4.getZ(), 1, 0, 0, 0, 0);
                                }
                                serverWorld.spawnParticles(player, Particles.frost_shard.particleType, true, vec3d3.getX(), y, vec3d3.getZ(), 1, 0, 0, 0, 0);
                                serverWorld.spawnParticles(player, Particles.frost_shard.particleType, true, vec3d4.getX(), y, vec3d4.getZ(), 1, 0, 0, 0, 0);
                            }
                        }
                    });

                }
                ;
            }
            return true;
        });
            ;
    }
    private static void knockbackNearbyEntities(World world, PlayerEntity player, Entity attacked) {
        world.syncWorldEvent(2013, attacked.getSteppingPos(), 750);
        world.getEntitiesByClass(LivingEntity.class, attacked.getBoundingBox().expand(3.5), getKnockbackPredicate(player, attacked)).forEach((entity) -> {
            Vec3d vec3d = entity.getPos().subtract(attacked.getPos());
            double d = getKnockback(player, entity, vec3d);
            Vec3d vec3d2 = vec3d.normalize().multiply(d);
            if (d > 0.0) {
                entity.addVelocity(vec3d2.x, 0.699999988079071, vec3d2.z);
                if (entity instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
                    serverPlayerEntity.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverPlayerEntity));
                }
            }

        });
    }

    private static Predicate<LivingEntity> getKnockbackPredicate(PlayerEntity player, Entity attacked) {
        return (entity) -> {
            boolean var10000;
            boolean bl;
            boolean bl2;
            boolean bl3;
            boolean bl7;
            bl7 = TargetHelper.actionAllowed(TargetHelper.TargetingMode.AREA, TargetHelper.Intent.HARMFUL,player,attacked);
            label62: {
                bl = !entity.isSpectator();
                bl2 = entity != player && entity != attacked;
                bl3 = !player.isTeammate(entity);
                if (entity instanceof TameableEntity tameableEntity) {
                    if (tameableEntity.isTamed() && player.getUuid().equals(tameableEntity.getOwnerUuid())) {
                        var10000 = true;
                        break label62;
                    }
                }

                var10000 = false;
            }

            boolean bl4;
            label55: {
                bl4 = !var10000;
                if (entity instanceof ArmorStandEntity armorStandEntity) {
                    if (armorStandEntity.isMarker()) {
                        var10000 = false;
                        break label55;
                    }
                }

                var10000 = true;
            }

            boolean bl5 = var10000;
            boolean bl6 = attacked.squaredDistanceTo(entity) <= Math.pow(3.5, 2.0);
            return bl && bl2 && bl3 && bl4 && bl5 && bl6 && bl7;
        };
    }
    public static void spellbladePassive(LivingEntity entity, SpellSchool school, int max){
        SpellPower.Result power2 = SpellPower.getSpellPower(school, (LivingEntity) entity);
        int amp = Math.min(max, (int) power2.randomValue() / 4 - 1);
        if (amp >= 0) {

            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, (int) (4 * 20), amp));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, (int) (4 * 20), amp));

        }
    }
    public static void shootProjectile(World world, LivingEntity caster, Entity target, SpellInfo spellInfo, SpellHelper.ImpactContext context, int sequenceIndex) {
        if (!world.isClient) {
            Spell spell = spellInfo.spell();
            Vec3d launchPoint = launchPoint(caster);
            Spell.Release.Target.ShootProjectile data = spell.release.target.projectile;
            Spell.ProjectileData projectileData = data.projectile;
            Spell.ProjectileData.Perks mutablePerks = projectileData.perks.copy();
            SpellProjectile projectile = new SpellProjectile(world, caster, launchPoint.getX(), launchPoint.getY(), launchPoint.getZ(), SpellProjectile.Behaviour.FLY, spellInfo.id(), target, context, mutablePerks);
            Spell.LaunchProperties mutableLaunchProperties = data.launch_properties.copy();
            if (SpellEvents.PROJECTILE_SHOOT.isListened()) {
                SpellEvents.PROJECTILE_SHOOT.invoke((listener) -> {
                    listener.onProjectileLaunch(new SpellEvents.ProjectileLaunchEvent(projectile, mutableLaunchProperties, caster, target, spellInfo, context, sequenceIndex));
                });
            }

            float velocity = mutableLaunchProperties.velocity;
            float divergence = projectileData.divergence;
            if (data.inherit_shooter_velocity) {
                projectile.setVelocity(caster, caster.getPitch(), caster.getYaw(), 0.0F, velocity, divergence);
            } else {
                Vec3d look = caster.getRotationVector().normalize();
                projectile.setVelocity(look.x, look.y, look.z, velocity, divergence);
            }

            projectile.range = spell.range;
            projectile.setPitch(caster.getPitch());
            projectile.setYaw(caster.getYaw());
            world.spawnEntity(projectile);
            SoundHelper.playSound(world, projectile, mutableLaunchProperties.sound);
            if (sequenceIndex == 0 && mutableLaunchProperties.extra_launch_count > 0) {
                for(int i = 0; i < mutableLaunchProperties.extra_launch_count; ++i) {
                    int ticks = (i + 1) * mutableLaunchProperties.extra_launch_delay;
                    int nextSequenceIndex = i + 1;
                    ((WorldScheduler)world).schedule(ticks, () -> {
                        if (caster != null && caster.isAlive()) {
                            shootProjectile(world, caster, target, spellInfo, context, nextSequenceIndex);
                        }
                    });
                }
            }

        }
    }
    private static double getKnockback(PlayerEntity player, LivingEntity attacked, Vec3d distance) {
        return (3.5 - distance.length()) * 0.699999988079071 * (double)(player.fallDistance > 5.0F ? 2 : 1) * (1.0 - attacked.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
    }
}
