package com.cleannrooster.spellbladenext.fabric.Spells;

import com.cleannrooster.spellbladenext.SpellbladesAndSuch;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.event.SpellEvents;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.api.util.TriState;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.fx.SpellEngineSounds;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.target.SpellTarget;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;

import static com.cleannrooster.spellbladenext.SpellbladesAndSuch.MOD_ID;

public class SpellbladeSpells {
    public record Entry(Identifier id, Spell spell, String title, String description,
                        @Nullable SpellTooltip.DescriptionMutator mutator) {
    }

    public static final List<Entry> entries = new ArrayList<>();

    public static Entry add(Entry entry) {
        entries.add(entry);

        return entry;
    }
    public static Entry addIfInstalled(Entry entry, String modid) {
        if(FabricLoader.getInstance().isModLoaded(modid)) {
            entries.add(entry);
        }

        return entry;
    }
    public static ParticleBatch arcaneCastingParticles() {
        return new ParticleBatch(
                SpellEngineParticles.getMagicParticleVariant(
                        SpellEngineParticles.ARCANE,
                        SpellEngineParticles.MagicParticleFamily.Shape.SPELL,
                        SpellEngineParticles.MagicParticleFamily.Motion.ASCEND
                ).id().toString(),
                ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                1, 0.05F, 0.1F);
    }
    public static ParticleBatch fireCastingParticles() {
        return new ParticleBatch(
                SpellEngineParticles.flame.id().toString(),
                ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                1, 0.05F, 0.1F);
    }
    public static ParticleBatch frostCastingParticles() {
        return new ParticleBatch(
                SpellEngineParticles.getMagicParticleVariant(
                        SpellEngineParticles.FROST,
                        SpellEngineParticles.MagicParticleFamily.Shape.SPELL,
                        SpellEngineParticles.MagicParticleFamily.Motion.ASCEND
                ).id().toString(),
                ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET,
                1, 0.05F, 0.1F);
    }
    public static Spell activeSpellBase() {
        var spell = new Spell();
        spell.range = 0;
        spell.tier = 7;
        spell.learn = new Spell.Learn();
        spell.type = Spell.Type.ACTIVE;
        spell.active = new Spell.Active();
        spell.active.scroll = new Spell.Active.Scroll();


        return spell;
    }

    public static Spell passiveSpellBase() {
        var spell = new Spell();
        spell.range = 0;
        spell.tier = 7;

        spell.type = Spell.Type.PASSIVE;
        spell.passive = new Spell.Passive();
        spell.tooltip = new Spell.Tooltip();
        spell.tooltip.name = new Spell.Tooltip.LineOptions(true, true);
        spell.tooltip.description.color = Formatting.DARK_GREEN.asString();
        spell.tooltip.description.show_in_compact = false;

        return spell;
    }
    public static Spell.Delivery createDelivery(Spell.Delivery.Type type) {
        var delivery = new Spell.Delivery();
        delivery.type = type;
        return delivery;

    }
    public static Spell.Impact createImpact(Spell.Impact.Action.Type type, float coeff, float knockback) {
        var impact = new Spell.Impact();
        impact.action = new Spell.Impact.Action();
        impact.action.type = type;
        if(type == Spell.Impact.Action.Type.DAMAGE) {
            impact.action.damage = new Spell.Impact.Action.Damage();
            impact.action.damage.knockback = knockback;
            impact.action.damage.spell_power_coefficient = coeff;
        }
        return impact;
    }
    public static Spell.Impact createArcaneImpact(float coeff, float knockback) {
        var impact = createImpact(Spell.Impact.Action.Type.DAMAGE,coeff, knockback);
        impact.school = SpellSchools.ARCANE;
        ParticleBatch[] hitParticles = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_arcane_impact_burst", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, ParticleBatch.Rotation.LOOK, 20, 0.2f, 0.7F, 360)
        };
        impact.particles = hitParticles;
        var sound = new Sound(SpellEngineSounds.GENERIC_ARCANE_RELEASE.id());
        impact.sound = sound;
        return impact;
    }
    public static Spell.Impact createLightningImpact(float coeff, float knockback) {
        var impact = createImpact(Spell.Impact.Action.Type.DAMAGE,coeff, knockback);
        impact.school = SpellSchools.LIGHTNING;
        ParticleBatch[] hitParticles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.WHITE, SpellEngineParticles.MagicParticleFamily.Shape.IMPACT, SpellEngineParticles.MagicParticleFamily.Motion.BURST).id().toString(), ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, ParticleBatch.Rotation.LOOK, 20, 0.2f, 0.7F, 360)
        };
        impact.particles = hitParticles;
        var sound = new Sound(SpellEngineSounds.GENERIC_LIGHTNING_RELEASE.id());
        impact.sound = sound;
        return impact;
    }
    public static Spell.Impact createFrostImpact(float coeff, float knockback) {
        var impact = createImpact(Spell.Impact.Action.Type.DAMAGE,coeff, knockback);
        impact.school = SpellSchools.FROST;
        ParticleBatch[] hitParticles = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_frost_impact_burst", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, ParticleBatch.Rotation.LOOK, 20, 0.2f, 0.7F, 360)
        };
        impact.particles = hitParticles;
        Spell.TargetCondition targetCondition = new Spell.TargetCondition();
        targetCondition.entity_type = "#minecraft:freeze_immune_entity_types";
        Spell.Impact.TargetModifier targetModifier = new Spell.Impact.TargetModifier();

        targetModifier.conditions = List.of(targetCondition);
        targetModifier.modifier = new Spell.Impact.Modifier();
        targetModifier.modifier.power_multiplier = -0.3F;
        Spell.Impact.TargetModifier targetModifier2 = new Spell.Impact.TargetModifier();
        targetCondition.entity_type = "#minecraft:freeze_hurts_extra_types";
        targetModifier2.conditions = List.of(targetCondition);
        targetModifier2.modifier = new Spell.Impact.Modifier();
        targetModifier2.modifier.power_multiplier = 0.3F;
        impact.target_modifiers = List.of(targetModifier,targetModifier2);
        var sound = new Sound(SpellEngineSounds.GENERIC_FROST_IMPACT.id());
        impact.sound = sound;
        return impact;
    }
    public static Spell.Impact createFireImpact(float coeff, float knockback) {
        var impact = createImpact(Spell.Impact.Action.Type.DAMAGE,coeff, knockback);
        impact.school = SpellSchools.FIRE;
        ParticleBatch[] hitParticles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.flame_spark.id().toString(), ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, ParticleBatch.Rotation.LOOK, 20, 0.2f, 0.7F, 360),
                new ParticleBatch("minecraft:smoke", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, ParticleBatch.Rotation.LOOK, 20, 0.2f, 0.7F, 360)

        };
        Spell.TargetCondition targetCondition = new Spell.TargetCondition();
        targetCondition.entity_type = "#minecraft:freeze_immune_entity_types";
        Spell.Impact.TargetModifier targetModifier = new Spell.Impact.TargetModifier();

        targetModifier.conditions = List.of(targetCondition);
        targetModifier.modifier = new Spell.Impact.Modifier();
        targetModifier.modifier.critical_chance_bonus = 0.3F;

        impact.target_modifiers = List.of(targetModifier);
        impact.particles = hitParticles;

        var sound = new Sound("minecraft:entity.player.hurt_on_fire");
        impact.sound = sound;
        return impact;
    }

    public static Spell.Impact createHealingImpact(float coeff, float knockback) {
        var impact = createImpact(Spell.Impact.Action.Type.HEAL,coeff, knockback);
        impact.school = SpellSchools.HEALING;

        impact.particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.HOLY, SpellEngineParticles.MagicParticleFamily.Shape.IMPACT, SpellEngineParticles.MagicParticleFamily.Motion.ASCEND).id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null, 20, 0.2f, 0.7F, 360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.HOLY, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null, 20, 0.1f, 0.35F, 360),

        };
        impact.sound = new Sound(SpellEngineSounds.GENERIC_HEALING_IMPACT_1.id());
        return impact;
    }
    public static Spell.Impact createPhysicalimpact(float coeff, float knockback) {
        var impact = createImpact(Spell.Impact.Action.Type.DAMAGE,coeff, knockback);
        impact.school = ExternalSpellSchools.PHYSICAL_MELEE;

        return impact;
    }
    public static Spell.Active.Cast createCast(int channelticks, float duration, String sound, String animation, @Nullable SpellSchool school) {
        var cast = new Spell.Active.Cast();
        cast = new Spell.Active.Cast();
        cast.animation = animation;
        cast.sound = new Sound(sound);
        cast.channel_ticks = channelticks;
        cast.duration = duration;
        if(school != null) {
            if (school.equals(SpellSchools.FIRE)) {
                cast.particles = new ParticleBatch[]{fireCastingParticles()};
            }
            if (school.equals(SpellSchools.FROST)) {
                cast.particles = new ParticleBatch[]{frostCastingParticles()};
            }
            if (school.equals(SpellSchools.ARCANE)) {
                cast.particles = new ParticleBatch[]{arcaneCastingParticles()};
            }
        }
        return cast;
    }
    public static void configureCooldown(Spell spell, float duration, boolean proportional,@Nullable String id) {
        if (spell.cost == null) {
            spell.cost = new Spell.Cost();
        }
        if (spell.cost.cooldown == null) {
            spell.cost.cooldown = new Spell.Cost.Cooldown();
        }
        if(id != null){
            spell.cost.item = new Spell.Cost.Item();
            spell.cost.item.id = id;
            spell.cost.item.amount = 1;

        }
        if(proportional){
            spell.cost.cooldown.proportional = true;
        }
        spell.cost.cooldown.duration = duration;
    }



    public static Spell projectileBase(SpellSchool school, Identifier projIdentifier, float velocity,float knockback) {

        var spell = activeSpellBase();
        spell.school = school;


        var delivery = createDelivery(Spell.Delivery.Type.PROJECTILE);
        delivery.projectile = new Spell.Delivery.ShootProjectile();

        delivery.projectile.launch_properties.velocity = velocity;

        delivery.projectile.projectile = new Spell.ProjectileData();

        delivery.projectile.projectile.client_data = new Spell.ProjectileData.Client();

        var model = new Spell.ProjectileModel();
        model.model_id = String.valueOf(projIdentifier);
        delivery.projectile.projectile.client_data.model = model;
        spell.deliver = delivery;
        Spell.Impact[] impact = new Spell.Impact[1];
        impact[0] = createImpact(Spell.Impact.Action.Type.DAMAGE,1.8F,knockback);
        spell.impacts = List.of(impact[0]);

        return spell;
    }
    public static Spell meteor_base(SpellSchool school, Identifier projIdentifier, float velocity,float knockback) {

        var spell = activeSpellBase();
        spell.school = school;


        var delivery = createDelivery(Spell.Delivery.Type.METEOR);
        delivery.meteor = new Spell.Delivery.Meteor();
        delivery.meteor.launch_properties.velocity = velocity;

        delivery.meteor.projectile = new Spell.ProjectileData();

        delivery.meteor.projectile.client_data = new Spell.ProjectileData.Client();

        var model = new Spell.ProjectileModel();
        model.model_id = String.valueOf(projIdentifier);
        delivery.meteor.projectile.client_data.model = model;
        spell.deliver = delivery;
        Spell.Impact[] impact = new Spell.Impact[1];
        impact[0] = createImpact(Spell.Impact.Action.Type.DAMAGE,1.8F,knockback);
        spell.impacts = List.of(impact[0]);

        return spell;
    }
    public static Entry amethyst_slash = add(amethyst_slash());
    public static Entry amethyst_slash() {
        var spell = projectileBase(SpellSchools.ARCANE,Identifier.of("spellbladenext:projectile/amethyst"),4.0F,0);
        spell.school = SpellSchools.ARCANE;
        spell.deliver.projectile.projectile.perks.bounce = 4;
        spell.deliver.projectile.projectile.divergence = 15;
        spell.deliver.projectile.projectile.client_data.model.scale = 2;

        var id = Identifier.of(MOD_ID, "amethyst_slash");
        var description = "Attack with a flurry of amethyst projectiles, dealing {damage} Arcane damage per second.";
        var title = "Amethyst Slash";

        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 12;
        spell.active.cast = createCast(3,6,"spell_engine:generic_arcane_casting","spell_engine:flameslash", SpellSchools.ARCANE);

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_arcane_spark_float", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360)

                };

        spell.deliver.projectile.projectile.client_data.travel_particles = particlebatch;
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createArcaneImpact(1.8F,0F);

        spell.impacts = List.of(impacts[0]);

        configureCooldown(spell, 4, true, "runes:arcane_stone");

        return new Entry(id, spell, title, description, null);
    }
    public static Entry frost_slash = add(frost_slash());
    public static Entry frost_slash() {
        var spell = projectileBase(SpellSchools.ARCANE,Identifier.of("spellbladenext:projectile/gladius"),4.0F,0);
        spell.school = SpellSchools.FROST;

        var id = Identifier.of(MOD_ID, "frost_slash");
        var description = "Attack with a flurry of frost blade projectiles, dealing {damage} Frost damage per second.";
        var title = "Amethyst Slash";
        spell.deliver.projectile.projectile.perks.ricochet = 2;
        spell.deliver.projectile.projectile.divergence = 15;
        spell.deliver.projectile.projectile.client_data.model.scale = 2;
        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 12;
        spell.active.cast = createCast(3,6,"spell_engine:generic_frost_casting","spell_engine:flameslash",SpellSchools.FROST);

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_frost_spark_float", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.FROST, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360)

        };

        spell.deliver.projectile.projectile.client_data.travel_particles = particlebatch;
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createFrostImpact(1.8F,0F);

        spell.impacts = List.of(impacts[0]);

        configureCooldown(spell, 4, true, "runes:frost_stone");

        return new Entry(id, spell, title, description, null);
    }
    public static Entry riptide = add(riptide());
    public static Entry riptide() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.FROST;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AIM;
        var id = Identifier.of(MOD_ID, "riptide");
        var description = "Perform a riptide maneuver in the targeted direction.";
        var title = "Riptide";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = id.toString();

        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.required = false;
        spell.target.aim.use_caster_as_fallback = true;
        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range = 4;
        spell.active.cast = createCast(0,0.5F,"spell_engine:generic_frost_casting","spellbladenext:one_handed_projectile_charge",SpellSchools.FROST);
        Spell.Impact[] impacts = new Spell.Impact[2];

        impacts[0] = createFrostImpact(1.8F,1F);
        impacts[1] = createPhysicalimpact(1.5F,1F);
        spell.release = new Spell.Release();




        spell.impacts = List.of(impacts[0],impacts[1]);
        configureCooldown(spell, 4, false, "runes:frost_stone");

        return new Entry(id, spell, title, description, null);

    }
    public static Entry vault = add(vault());
    public static Entry vault() {
        var spell = activeSpellBase();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        var id = Identifier.of(MOD_ID, "vaulting_slam");
        var description = "Perform a riptide maneuver in the targeted direction.";
        var title = "Massacre";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.DIRECT;
        spell.target.area = new Spell.Target.Area();
        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 6;
        spell.active.cast = createCast(0,1.25F,"spell_engine:generic_frost_casting","spellbladenext:leapslamstaff1",null);
        spell.active.cast.movement_speed = 1F;
        Spell.Impact[] impacts = new Spell.Impact[1];

        impacts[0] = createPhysicalimpact(6F,4F);
        spell.release = new Spell.Release();
        spell.release.sound = new Sound("minecraft:entity.player.attack.crit");
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch("minecraft:smoke", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,null,40,0.2F,0.6F,360),
                new ParticleBatch("minecraft:cloud", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,null,120,0.5F,0.9F,360)

        };
        spell.release.animation = "spellbladenext:leapslamstaff2";


        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 4, false, null);

        return new Entry(id, spell, title, description, null);

    }
    public static Entry staffspin = add(staffspin());
    public static Entry staffspin() {
        var spell = activeSpellBase();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        var id = Identifier.of(MOD_ID, "whirling_assault");
        var description = "Perform a riptide maneuver in the targeted direction.";
        var title = "Massacre";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.DIRECT;
        spell.target.area = new Spell.Target.Area();

        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range_mechanic = Spell.RangeMechanic.MELEE;
        spell.active.cast = createCast(4,2F,"spell_engine:generic_frost_casting","spellbladenext:staffspin",null);
        spell.active.cast.movement_speed = 1F;
        Spell.Impact[] impacts = new Spell.Impact[1];

        impacts[0] = createPhysicalimpact(8F,0.2F);
        spell.release = new Spell.Release();
        impacts[0].particles = new ParticleBatch[]{
                new ParticleBatch("minecraft:poof", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.FEET,null,3,0.1F,0.2F,360),

        };
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 4, false, null);

        return new Entry(id, spell, title, description, null);
    }
    public static Entry massacre = add(massacre());
    public static Entry massacre() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.FROST;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        var id = Identifier.of(MOD_ID, "eviscerate");
        var description = "Perform a riptide maneuver in the targeted direction.";
        var title = "Massacre";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = id.toString();
        spell.target.area = new Spell.Target.Area();
        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 8;
        spell.active.cast = createCast(2,2F,"spell_engine:generic_frost_casting","spellbladenext:staffspin",SpellSchools.FROST);
        spell.active.cast.movement_speed = 1F;
        Spell.Impact[] impacts = new Spell.Impact[2];

        impacts[0] = createFrostImpact(2F,0.2F);
        impacts[1] = createPhysicalimpact(1.8F,0.2F);
        spell.release = new Spell.Release();




        spell.impacts = List.of(impacts[0],impacts[1]);
        configureCooldown(spell, 4, false, "runes:frost_stone");

        return new Entry(id, spell, title, description, null);

    }
    public static Entry forwardstrike = add(forwardstrike());
    public static Entry forwardstrike() {
        var spell = activeSpellBase();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        var id = Identifier.of(MOD_ID, "advancing_strike");
        var description = "Teleport behind an enemy, dealing {damage} damage to them.";
        var title = "Rift Slash";
        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 135;
        spell.target.area.include_caster = true;
        spell.learn = new Spell.Learn();
        spell.tier = 1;
        spell.range = 8;
        spell.active.cast = createCast(0,0F,"spell_engine:generic_frost_casting","spell_engine:two_handed_slam_spellblade_2",null);
        Spell.Impact[] impacts = new Spell.Impact[2];

        impacts[0] = createPhysicalimpact(4F,2F);
        Spell.Impact teleport = new Spell.Impact();
        teleport.action = new Spell.Impact.Action();
        teleport.action.type = Spell.Impact.Action.Type.TELEPORT;
        teleport.action.apply_to_caster = true;
        teleport.action.teleport = new Spell.Impact.Action.Teleport();

        spell.release = new Spell.Release();
        spell.release.animation = "spell_engine:two_handed_slam_spellblade_2";
        spell.release.sound = new Sound("minecraft:entity.player.attack.crit");
        impacts[0].particles = new ParticleBatch[]{
                new ParticleBatch("minecraft:poof", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.FEET,null,3,0.1F,0.2F,360),

        };

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_arcane_spark_burst", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, ParticleBatch.Rotation.LOOK,20,0.05f,1F,360),
                new ParticleBatch("minecraft:firework", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, ParticleBatch.Rotation.LOOK,20,0.05f,0.1F,360)

        };

        teleport.action.teleport.mode = Spell.Impact.Action.Teleport.Mode.FORWARD;
        teleport.action.teleport.forward = new Spell.Impact.Action.Teleport.Forward();
        teleport.action.teleport.forward.distance = 2;

        impacts[1] = teleport;
        spell.impacts = List.of(impacts[0],impacts[1]);
        configureCooldown(spell, 2, false, null);

        return new Entry(id, spell, title, description, null);

    }
    public static Entry arcane_blink = add(arcane_blink());
    public static Entry arcane_blink() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.ARCANE;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AIM;
        var id = Identifier.of(MOD_ID, "arcane_blink");
        var description = "Teleport behind an enemy, dealing {damage} damage to them.";
        var title = "Rift Slash";
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.required = false;
        spell.target.aim.use_caster_as_fallback = true;
        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range = 4;
        spell.active.cast = createCast(0,0.5F,"spell_engine:generic_arcane_casting","spellbladenext:one_handed_projectile_charge",SpellSchools.ARCANE);
        Spell.Impact[] impacts = new Spell.Impact[3];

        impacts[0] = createArcaneImpact(2F,1F);
        impacts[1] = createPhysicalimpact(1.2F,1F);
        Spell.Impact teleport = new Spell.Impact();
        teleport.action = new Spell.Impact.Action();
        teleport.action.type = Spell.Impact.Action.Type.TELEPORT;

        teleport.action.teleport = new Spell.Impact.Action.Teleport();

        spell.release = new Spell.Release();
        spell.release.animation = "spell_engine:dashslash";
        spell.release.sound = new Sound("minecraft:entity.player.attack.sweep");


        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_arcane_spark_burst", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, ParticleBatch.Rotation.LOOK,20,0.05f,1F,360),
                new ParticleBatch("minecraft:firework", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, ParticleBatch.Rotation.LOOK,20,0.05f,0.1F,360)

        };        teleport.action.teleport.arrive_particles = particlebatch;
        teleport.action.teleport.depart_particles = particlebatch;
        teleport.action.apply_to_caster = true;
        teleport.action.teleport.mode = Spell.Impact.Action.Teleport.Mode.FORWARD;
        teleport.action.teleport.forward = new Spell.Impact.Action.Teleport.Forward();
        teleport.action.teleport.forward.distance = 8;

        impacts[2] = teleport;
        spell.impacts = List.of(impacts[0],impacts[1],impacts[2]);
        configureCooldown(spell, 4, false, "runes:arcane_stone");

        return new Entry(id, spell, title, description, null);

    }
    public static Entry arcane_blink_far = add(arcane_blink_far());
    public static Entry arcane_blink_far() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.ARCANE;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;

        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 15;
        spell.target.area.include_caster = true;

        var id = Identifier.of(MOD_ID, "sonic_strike");
        var description = "Teleport to the furthest enemy in a line, dealing {damage} damage to all enemies in the path.";
        var title = "Sonic Strike";

        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 24;
        spell.active.cast = createCast(0,2,"spell_engine:generic_arcane_casting","spell_engine:crosscharge",SpellSchools.ARCANE);

        Spell.Impact[] impacts = new Spell.Impact[3];

        spell.release = new Spell.Release();
        spell.release.animation = "spell_engine:dashslash";
        spell.release.sound = new Sound("minecraft:entity.warden.sonic_boom");

        impacts[0] = createArcaneImpact(4F,1F);
        impacts[1] = createPhysicalimpact(2.2F,1F);
        Spell.Impact teleport = new Spell.Impact();
        teleport.action = new Spell.Impact.Action();
        teleport.action.teleport = new Spell.Impact.Action.Teleport();

        teleport.action.teleport.forward = new Spell.Impact.Action.Teleport.Forward();
        teleport.action.teleport.forward.distance = 28;

        teleport.action.apply_to_caster = true;

        teleport.action.type = Spell.Impact.Action.Type.TELEPORT;

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString(), ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, ParticleBatch.Rotation.LOOK,20,0.05f,1F,360),
                new ParticleBatch("minecraft:firework", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, ParticleBatch.Rotation.LOOK,20,0.05f,0.1F,360)

        };

        teleport.action.teleport.arrive_particles = particlebatch;
        teleport.action.teleport.depart_particles = particlebatch;

        teleport.action.teleport.mode = Spell.Impact.Action.Teleport.Mode.FORWARD;
        impacts[2] = teleport;

        spell.impacts = List.of(impacts[0],impacts[1],teleport);
        configureCooldown(spell, 16, false, "runes:arcane_stone");

        return new Entry(id, spell, title, description, null);

    }
    public static Entry lightningSpellstrike = add(lightningSpellstrike());
    public static Entry lightningSpellstrike() {
        var id = Identifier.of(MOD_ID, "lightning_spellstrike");
        var description = "On melee hit: 100% chance to deal arcane damage in an cone in front of you.";
        var title = "Arcane Spellstrike";

        var spell = passiveSpellBase();
        spell.group = "spellstrike";

        spell.tier = 0;
        spell.range = 6;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.LIGHTNING;
        var trigger = new Spell.Trigger();
        trigger.chance = 1;
        trigger.type = Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = SpellSchool.Archetype.MAGIC;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        spell.deliver = new Spell.Delivery();

        spell.deliver.type = Spell.Delivery.Type.DIRECT;
        spell.passive.triggers = List.of(trigger);

        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_LIGHTNING_RELEASE.id());

        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createLightningImpact(0.4F,1F);
        impacts[0].action.min_power = 2;
        spell.impacts = List.of(impacts);

        configureCooldown(spell, 0F,false,null);
        return new Entry(id, spell, title, description, null);
    }
    public static Entry arcaneSpellstrike = add(arcaneSpellstrike());
    public static Entry arcaneSpellstrike() {
        var id = Identifier.of(MOD_ID, "arcane_spellstrike");
        var description = "On melee hit: 100% chance to deal arcane damage in an cone in front of you.";
        var title = "Arcane Spellstrike";

        var spell = passiveSpellBase();
        spell.group = "spellstrike";

        spell.tier = 0;
        spell.range = 6;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.ARCANE;
        var trigger = new Spell.Trigger();
        trigger.chance = 1;
        trigger.type = Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = SpellSchool.Archetype.MAGIC;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        spell.deliver = new Spell.Delivery();

        spell.deliver.type = Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = "spellbladenext:spellstrike";
        spell.passive.triggers = List.of(trigger);

        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id());

        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createArcaneImpact(0.4F,1F);
        impacts[0].action.type = Spell.Impact.Action.Type.CUSTOM;

        impacts[0].action.custom = new Spell.Impact.Action.Custom();
        impacts[0].action.custom.handler = "spellbladenext:spellstrike";
        impacts[0].action.min_power = 2;
        spell.impacts = List.of(impacts);

        configureCooldown(spell, 0F,false,null);
        return new Entry(id, spell, title, description, null);
    }
    public static Entry phasedash = addIfInstalled(phasedash(),"combat_roll");
    public static Entry phasedash() {
        var id = Identifier.of(MOD_ID, "phase_dash");
        var description = "On melee hit: 100% chance to deal arcane damage in an cone in front of you.";
        var title = "Arcane Spellstrike";

        var spell = passiveSpellBase();

        spell.tier = 2;
        spell.sub_tier = 2;
        spell.range = 0;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.ARCANE;
        var trigger = new Spell.Trigger();
        trigger.chance = 1;
        trigger.type = Spell.Trigger.Type.ROLL;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = SpellSchool.Archetype.MAGIC;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        spell.deliver = new Spell.Delivery();

        spell.deliver.type = Spell.Delivery.Type.DIRECT;
        spell.passive.triggers = List.of(trigger);


        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = new Spell.Impact();
        impacts[0].action = new Spell.Impact.Action();
        impacts[0].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[0].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[0].action.status_effect.effect_id = SpellbladesAndSuch.PHASEDASH.getIdAsString();
        impacts[0].action.status_effect.duration = 0.20F;
        impacts[0].action.apply_to_caster = true;
        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString(), ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET, null,12,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.WIDE_PIPE, ParticleBatch.Origin.FEET, null,12,0.05f,0.1F,360)

        };
        impacts[0].particles = particlebatch;

        spell.impacts = List.of(impacts);
        configureCooldown(spell, 0F,false,null);
        return new Entry(id, spell, title, description, null);
    }
    public static Entry fireSpellstrike = add(fireSpellstrike());
    public static Entry fireSpellstrike() {
        var id = Identifier.of(MOD_ID, "fire_spellstrike");
        var description = "On melee hit: 100% chance to deal arcane damage in an cone in front of you.";
        var title = "Arcane Spellstrike";

        var spell = passiveSpellBase();
        spell.group = "spellstrike";

        spell.tier = 0;
        spell.range = 6;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.FIRE;
        var trigger = new Spell.Trigger();
        trigger.chance = 1;
        trigger.type = Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = SpellSchool.Archetype.MAGIC;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        spell.deliver = new Spell.Delivery();

        spell.deliver.type = Spell.Delivery.Type.CUSTOM;

        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = "spellbladenext:spellstrike";
        spell.passive.triggers = List.of(trigger);

        spell.release.sound = new Sound("spell_engine:generic_fire_release");

        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createFireImpact(0.4F,1F);
        impacts[0].action.type = Spell.Impact.Action.Type.CUSTOM;

        impacts[0].action.custom = new Spell.Impact.Action.Custom();
        impacts[0].action.custom.handler = "spellbladenext:spellstrike";
        impacts[0].action.min_power = 2;
        spell.impacts = List.of(impacts);

        configureCooldown(spell, 0F,false,null);
        return new Entry(id, spell, title, description, null);
    }
    public static Entry frostSpellstrike = add(frostSpellstrike());
    public static Entry frostSpellstrike() {
        var id = Identifier.of(MOD_ID, "frost_spellstrike");
        var description = "On melee hit: 100% chance to deal arcane damage in an cone in front of you.";
        var title = "Arcane Spellstrike";

        var spell = passiveSpellBase();
        spell.group = "spellstrike";

        spell.tier = 0;
        spell.range = 6;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.FROST;
        var trigger = new Spell.Trigger();
        trigger.chance = 1;
        trigger.type = Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = SpellSchool.Archetype.MAGIC;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        spell.deliver = new Spell.Delivery();

        spell.deliver.type = Spell.Delivery.Type.CUSTOM;

        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = "spellbladenext:spellstrike";
        spell.passive.triggers = List.of(trigger);

        spell.release.sound = new Sound("spell_engine:generic_frost_release");

        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createFrostImpact(0.4F,1F);
        impacts[0].action.type = Spell.Impact.Action.Type.CUSTOM;

        impacts[0].action.custom = new Spell.Impact.Action.Custom();
        impacts[0].action.custom.handler = "spellbladenext:spellstrike";
        impacts[0].action.min_power = 2;
        spell.impacts = List.of(impacts);

        configureCooldown(spell, 0F,false,null);
        return new Entry(id, spell, title, description, null);
    }
    public static Entry arcaneOverdrive = add(arcaneOverdrive());
    public static Entry arcaneOverdrive() {
        var id = Identifier.of(MOD_ID, "arcane_burst");
        var description = "On melee hit: 100% chance to deal arcane damage in an cone in front of you.";
        var title = "Arcane Burst";

        var spell = passiveSpellBase();
        spell.group = "primary";

        spell.tier = 1;
        spell.range = 6;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.ARCANE;
        var trigger = new Spell.Trigger();
        trigger.chance = 1;
        trigger.type = Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = SpellSchool.Archetype.MAGIC;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.cap = 8;
        spell.target.area.angle_degrees = 360;
        spell.deliver = new Spell.Delivery();
        var projectile = projectileBase(SpellSchools.ARCANE, Identifier.of("spellbladenext:projectile/amethyst"),1,1).deliver.projectile;
        projectile.launch_properties = new Spell.LaunchProperties();
        projectile.launch_properties.velocity = 1;
        var projectileData = new Spell.ProjectileData();
        projectileData.homing_angle = 30;
        projectileData.divergence = 10;
        projectile.direct_towards_target = true;
        projectileData.homing_after_relative_distance = 0.4F;
        projectileData.homing_after_absolute_distance = 4;
        projectileData.client_data = new Spell.ProjectileData.Client();
        projectileData.client_data.model = new Spell.ProjectileModel();
        projectileData.client_data.model.scale = 0;
        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString(), ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,6,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360)

        };
        projectileData.client_data.travel_particles = particlebatch;
        projectile.projectile = projectileData;
        spell.deliver.projectile = projectile;
        spell.deliver.type = Spell.Delivery.Type.PROJECTILE;
        spell.passive.triggers = List.of(trigger);

        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.release.sound = new Sound("spell_engine:generic_arcane_release");
        ParticleBatch[] particlebatch2 = new ParticleBatch[]{
                new ParticleBatch("minecraft:dragon_breath", ParticleBatch.Shape.CONE, ParticleBatch.Origin.LAUNCH_POINT, ParticleBatch.Rotation.LOOK,30,0.1f,0.3F,45),
               new ParticleBatch("minecraft:firework", ParticleBatch.Shape.CONE, ParticleBatch.Origin.LAUNCH_POINT, ParticleBatch.Rotation.LOOK,30,0.5f,0.9F,45)

        };


        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createArcaneImpact(1F,1F);
        impacts[0].action.min_power = 2;
        spell.impacts = List.of(impacts);

        configureCooldown(spell, 1F,false, "runes:arcane_stone");
        return new Entry(id, spell, title, description, null);
    }
    public static Entry frostOverdrive = add(frostOverdrive());
    public static Entry frostOverdrive() {
        var id = Identifier.of(MOD_ID, "frost_burst");
        var description = "On melee hit: 100% chance to deal arcane damage in an cone in front of you.";
        var title = "Frost Burst";

        var spell = passiveSpellBase();
        spell.group = "primary";

        spell.tier = 1;
        spell.range = 16;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.FROST;

        var trigger = new Spell.Trigger();
        trigger.chance = 1;
        trigger.type = Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = SpellSchool.Archetype.MAGIC;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.cap = 4;
        spell.target.area.angle_degrees = 90;
        spell.deliver = new Spell.Delivery();
        var projectile = projectileBase(SpellSchools.ARCANE, Identifier.of("spellbladenext:projectile/gladius"),1,1).deliver.projectile;
        projectile.launch_properties = new Spell.LaunchProperties();
        projectile.launch_properties.velocity = 1;
        var projectileData = new Spell.ProjectileData();
        projectileData.homing_angle = 15;
        projectileData.divergence = 0;
        projectileData.perks.pierce = 5;
        projectile.direct_towards_target = true;
        projectileData.client_data = new Spell.ProjectileData.Client();
        projectileData.client_data.model = new Spell.ProjectileModel();
        projectileData.client_data.model.scale = 1;
        spell.deliver.projectile = projectile;
        spell.deliver.type = Spell.Delivery.Type.PROJECTILE;
        projectileData.client_data.travel_particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.FROST, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString(), ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.FROST, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360)

        };
        projectile.projectile = projectileData;
        spell.passive.triggers = List.of(trigger);

        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.release.sound = new Sound("spell_engine:generic_frost_release");
        ParticleBatch[] particlebatch2 = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.FROST, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString(), ParticleBatch.Shape.CONE, ParticleBatch.Origin.LAUNCH_POINT, ParticleBatch.Rotation.LOOK,30,0.1f,0.3F,45),
                new ParticleBatch("minecraft:firework", ParticleBatch.Shape.CONE, ParticleBatch.Origin.LAUNCH_POINT, ParticleBatch.Rotation.LOOK,30,0.5f,0.9F,45)

        };

        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createFrostImpact(1F,1F);
        impacts[0].action.min_power = 2;
        spell.impacts = List.of(impacts);

        configureCooldown(spell, 1F,false, "runes:frost_stone");
        return new Entry(id, spell, title, description, null);
    }
    public static Entry flickeringflame = add(flickeringflame());
    public static Entry flickeringflame() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.FIRE;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        var id = Identifier.of(MOD_ID, "flickering_flame");
        var description = "Perform a riptide maneuver in the targeted direction.";
        var title = "Flickering Flame";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = id.toString();
        spell.target.area = new Spell.Target.Area();
        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 8;
        spell.active.cast = createCast(0,0.25F,"spell_engine:generic_fire_casting","spellbladenext:one_handed_projectile_charge",SpellSchools.FIRE);
        Spell.Impact[] impacts = new Spell.Impact[2];

        impacts[0] = createFireImpact(0.9F,0.1F);
        impacts[1] = createPhysicalimpact(0.6F,0.1F);
        spell.release = new Spell.Release();
        spell.release.sound =new Sound("minecraft:entity.player.attack.sweep");
        spell.release.animation =("spellbladenext:flourish");



        spell.impacts = List.of(impacts[0],impacts[1]);
        configureCooldown(spell, 0.25F, false, "runes:fire_stone");

        return new Entry(id, spell, title, description, null);

    }
    public static Entry flameOverdrive = add(flameOverdrive());
    public static Entry flameOverdrive() {
        var id = Identifier.of(MOD_ID, "flame_burst");
        var description = "On melee hit: 100% chance to deal fire damage in an cone in front of you.";
        var title = "Flame  Burst";

        var spell = passiveSpellBase();
        spell.group = "primary";

        spell.tier = 1;
        spell.range = 6;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.FIRE;

        var trigger = new Spell.Trigger();
        trigger.chance = 1;
        trigger.type = Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = SpellSchool.Archetype.MAGIC;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 90;

        spell.passive.triggers = List.of(trigger);

        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.release.sound = new Sound("spell_engine:generic_frost_release");
        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.flame.id().toString(), ParticleBatch.Shape.CONE, ParticleBatch.Origin.LAUNCH_POINT, ParticleBatch.Rotation.LOOK,30,0.1f,0.3F,45),
                new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), ParticleBatch.Shape.CONE, ParticleBatch.Origin.LAUNCH_POINT, ParticleBatch.Rotation.LOOK,10,0.1f,0.3F,45),
                new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), ParticleBatch.Shape.CONE, ParticleBatch.Origin.LAUNCH_POINT, ParticleBatch.Rotation.LOOK,10,0.1f,0.3F,45),

                new ParticleBatch("minecraft:smoke", ParticleBatch.Shape.CONE, ParticleBatch.Origin.LAUNCH_POINT, ParticleBatch.Rotation.LOOK,30,0.5f,0.9F,45)

        };

        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createFireImpact(1F,1F);
        impacts[0].action.min_power = 2;
        spell.impacts = List.of(impacts);

        configureCooldown(spell, 1F,false, "runes:fire_stone");
        spell.release.particles = particlebatch;
        return new Entry(id, spell, title, description, null);
    }
    public static Entry flame_slash = add(flame_slash());
    public static Entry flame_slash() {
        var spell = projectileBase(SpellSchools.ARCANE,Identifier.of("spellbladenext:projectile/flamewaveprojectile"),4.0F,0);
        spell.school = SpellSchools.FIRE;

        var id = Identifier.of(MOD_ID, "flame_slash");
        var description = "Attack with a flurry of flame wave projectiles, dealing {damage} Fire damage per second.";
        var title = "Flame Slash";
        spell.deliver.projectile.projectile.divergence = 15;
        spell.deliver.projectile.projectile.client_data.model.scale = 2;
        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 12;
        spell.active.cast = createCast(3,6,"spell_engine:generic_frost_casting","spell_engine:flameslash",SpellSchools.FROST);

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.flame.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,3,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,1,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,1,0.05f,0.1F,360),
                new ParticleBatch("minecraft:smoke", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360)

        };

        spell.deliver.projectile.projectile.client_data.travel_particles = particlebatch;
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createFireImpact(2.4F,0F);
        spell.area_impact = new Spell.AreaImpact();
        spell.area_impact.area = new Spell.Target.Area();
        spell.area_impact.radius = 2;
        spell.area_impact.particles = new ParticleBatch[]{
                new ParticleBatch("spell_engine:fire_explosion", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,2,0.05f,1F,360),

        };
        spell.impacts = List.of(impacts[0]);

        configureCooldown(spell, 4, true, "runes:fire_stone");

        return new Entry(id, spell, title, description, null);
    }
    public static Entry phoenix_dive = add(phoenix_dive());
    public static Entry phoenix_dive() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.FIRE;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        var id = Identifier.of(MOD_ID, "phoenix_dive");
        var description = "Teleport forward 12 blocks and deal {damage} fire damage to enemies in an area upon landing..";
        var title = "Phoenix Dive";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.DIRECT;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.include_caster = true;

        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 8;
        spell.active.cast = createCast(0,1F,"spell_engine:generic_fire_casting","spell_engine:phoenixdive",SpellSchools.FIRE);
        Spell.Impact[] impacts = new Spell.Impact[2];

        impacts[1] = createFireImpact(2.4F,1F);
        Spell.Impact teleport = new Spell.Impact();
        teleport.action = new Spell.Impact.Action();
        teleport.action.type = Spell.Impact.Action.Type.TELEPORT;

        teleport.action.apply_to_caster = true;
        teleport.action.teleport = new Spell.Impact.Action.Teleport();
        teleport.action.teleport.intent = SpellTarget.Intent.HELPFUL;
        teleport.action.teleport.mode = Spell.Impact.Action.Teleport.Mode.FORWARD;
        teleport.action.teleport.forward = new Spell.Impact.Action.Teleport.Forward();
        teleport.action.teleport.forward.distance = 12;
        spell.release = new Spell.Release();
        impacts[0] = teleport;

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.flame.id().toString(), ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET, null,30,0.8f,1F,0),
                new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET, null,10,0.5f,1F,0),
                new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET, null,10,0.5f,01F,0),

        };
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound("minecraft:entity.player.attack.knockback");
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 12, false, "runes:fire_stone");

        return new Entry(id, spell, title, description, null);

    }
    public static Entry dragon_slam = add(dragon_slam());
    public static Entry dragon_slam() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.FIRE;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        var id = Identifier.of(MOD_ID, "dragon_slam");
        var description = "Perform a riptide maneuver in the targeted direction.";
        var title = "Dagon Slam";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = id.toString();
        spell.target.area = new Spell.Target.Area();
        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 8;
        spell.active.cast = createCast(0,0F,"spell_engine:generic_fire_casting","spell_engine:vertspin",SpellSchools.FIRE);
        Spell.Impact[] impacts = new Spell.Impact[2];

        impacts[0] = createFireImpact(1.6F,1F);
        impacts[1] = createPhysicalimpact(1F,1F);
        spell.release = new Spell.Release();

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.flame.id().toString(), ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET, null,30,0.8f,1F,0),
                new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET, null,10,0.5f,1F,0),
                new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET, null,10,0.5f,01F,0),

        };
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound("minecraft:entity.player.attack.knockback");
        spell.impacts = List.of(impacts[0],impacts[1]);
        configureCooldown(spell, 12, false, "runes:fire_stone");

        return new Entry(id, spell, title, description, null);

    }
    public static Entry arctic_armor = add(arctic_armor());

    public static Entry arctic_armor() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.FROST;
        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.CASTER;
        var id = Identifier.of(MOD_ID, "arctic_armor");
        var description = "Give yourself a buff for 16 seconds that retaliates against enemies that melee attack you, dealing {damage} frost damage and high knockback.";
        var title = "Arctic Armor";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.STASH_EFFECT;
        spell.deliver.stash_effect = new Spell.Delivery.StashEffect();
        spell.deliver.stash_effect.duration = 16;
        spell.deliver.stash_effect.id = Identifier.of(MOD_ID,"arctic_armor").toString();

        spell.deliver.stash_effect.consume = 0;
        List<Spell.Trigger> triggers = new ArrayList<>();
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.chance = 1F;
        trigger.cap_per_tick = 1;
        trigger.type = Spell.Trigger.Type.DAMAGE_TAKEN;
        triggers.add(trigger);
        spell.deliver.stash_effect.triggers = triggers;

        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range = 6;
        spell.active.cast = createCast(0,2F,"spell_engine:generic_frost_casting","spellbladenext:one_handed_area_charge",SpellSchools.FROST);

        Spell.Impact[] impacts = new Spell.Impact[2];

        impacts[0] = createFrostImpact(0.6F,2F);
        impacts[1] = new Spell.Impact();

        impacts[1].action =new  Spell.Impact.Action();
        impacts[1].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[1].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[1].action.status_effect.effect_id = SpellbladesAndSuch.DEATHCHILL.getIdAsString();
        impacts[1].action.status_effect.duration = 8;

        spell.release = new Spell.Release();

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,30,0.1f,0.3F,0),
                new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,10,0.1f,0.3F,0),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.FROST, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.ASCEND).id().toString()
                        , ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,10,0.1f,0.3F,0),

        };
        impacts[1].particles = particlebatch;
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FROST_RELEASE.id());
        spell.release.animation = "spellbladenext:one_handed_area_release";

        spell.impacts = List.of(impacts[0],impacts[1]);
        configureCooldown(spell, 0.5F, false, "runes:frost_stone");

        return new Entry(id, spell, title, description, null);

    }
    public static Entry wintertideBrand = add(wintertideBrand());

    public static Entry wintertideBrand() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.FROST;
        spell.group = "primary";
        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.required = true;
        spell.target.aim.sticky = true;
        var id = Identifier.of(MOD_ID, "wintertide");
        var description = "Blast an enemy for {damage} frost damage and inflict Wintertide on them, slowing them by 20%.";
        var title = "Wintertide";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.DIRECT;
        spell.learn = new Spell.Learn();
        spell.tier = 1;
        spell.range = 8;
        spell.active.cast = createCast(0,0.5F,"spell_engine:generic_frost_casting","spellbladenext:one_handed_area_charge",SpellSchools.FROST);
        Spell.Impact[] impacts = new Spell.Impact[2];

        impacts[0] = createFrostImpact(0.5F,0F);
        impacts[1] = new Spell.Impact();

        impacts[1].action =new  Spell.Impact.Action();
        impacts[1].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[1].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[1].action.status_effect.effect_id = SpellbladesAndSuch.DEATHCHILL.getIdAsString();
        impacts[1].action.status_effect.duration = 8;

        spell.release = new Spell.Release();

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,30,0.1f,0.3F,0),
                new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,10,0.1f,0.3F,0),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.FROST, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.ASCEND).id().toString()
                        , ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,10,0.1f,0.3F,0),

        };
        impacts[1].particles = particlebatch;
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FROST_RELEASE.id());
        spell.impacts = List.of(impacts[0],impacts[1]);
        configureCooldown(spell, 0.4F, false, "runes:frost_stone");

        return new Entry(id, spell, title, description, null);

    }
    public static Entry wintersgrasp = add(wintersgrasp());

    public static Entry wintersgrasp() {
        var spell = passiveSpellBase();
        spell.tier = 2;
        spell.range = 16;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.FROST;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AIM;
        var id = Identifier.of(MOD_ID, "shared_suffering");
        var description = "When you successfuly damage an enemy with Wintertide, enemies with Wintertide on them in an area around you take {damage} frost damage.";
        var title = "Shared Suffering";


        var trigger = new Spell.Trigger();
        trigger.chance = 1;
        trigger.type = Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.id = "spellbladenext:wintertide";




        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 360;
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.DIRECT;
        spell.passive.triggers = List.of(trigger);

        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.release.sound = new Sound("spell_engine:generic_frost_release");
        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,30,0.1f,0.3F,0),
                new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,10,0.1f,0.3F,0),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.FROST, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.ASCEND).id().toString()
                        , ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,10,0.1f,0.3F,0),

        };


        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createFrostImpact(0.5F,0F);
        impacts[0].particles = particlebatch;
        Spell.Impact.TargetModifier targetModifier = new Spell.Impact.TargetModifier();
        targetModifier.execute = TriState.ALLOW;

        Spell.TargetCondition targetCondition = new Spell.TargetCondition();
        targetCondition.entity_predicate_id = "spell_engine:has_effect";
        targetCondition.entity_predicate_param = SpellbladesAndSuch.DEATHCHILL.getIdAsString();

        targetModifier.conditions = List.of(targetCondition);
        Spell.Impact.TargetModifier targetModifier2 = createFrostImpact(1.0F,1.0F).target_modifiers.get(0);
        Spell.Impact.TargetModifier targetModifier3 = createFrostImpact(1.0F,1.0F).target_modifiers.get(1);
        targetModifier2.execute = TriState.PASS;
        targetModifier3.execute = TriState.PASS;

        impacts[0].target_modifiers = List.of(targetModifier,targetModifier2,targetModifier3);
        spell.impacts = List.of(impacts);

        configureCooldown(spell, 0F,false,null);
        return new Entry(id, spell, title, description, null);
    }
    public static Entry WintersExpanse = add(WintersExpanse());

    public static Entry WintersExpanse() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.FROST;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 360;
        var id = Identifier.of(MOD_ID, "winters_expanse");
        var description = "Inflict Wintetide on all enemies in an area around you, slowing them by 20%.";
        var title = "Winter's Expanse";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.DIRECT;
        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 16;
        spell.active.cast = createCast(0,2F,"spell_engine:generic_frost_casting","spellbladenext:one_handed_area_charge",SpellSchools.FROST);
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = new Spell.Impact();

        impacts[0].action =new  Spell.Impact.Action();
        impacts[0].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[0].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[0].action.status_effect.effect_id = SpellbladesAndSuch.DEATHCHILL.getIdAsString();
        impacts[0].action.status_effect.duration = 8;
        impacts[0].particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,30,0.1f,0.3F,0),
                new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,10,0.1f,0.3F,0),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.FROST, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.ASCEND).id().toString()
                        , ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,10,0.1f,0.3F,0),

        };
        spell.release = new Spell.Release();
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,30,0.1f,0.3F,0),
                new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,10,0.1f,0.3F,0),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.FROST, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.ASCEND).id().toString()
                        , ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,10,0.1f,0.3F,0),

        };
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FROST_RELEASE.id());
        spell.release.animation = "spellbladenext:one_handed_area_release";

        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 2, false, "runes:frost_stone");

        return new Entry(id, spell, title, description, null);
    }
    public static Entry mass_hypothermia = add(mass_hypothermia());

    public static Entry mass_hypothermia() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.FROST;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 360;
        var id = Identifier.of(MOD_ID, "mass_hypothermia");
        var description = "All enemies in an area take Wintertide damage if they already have Wintertide on them.";
        var title = "Mass Hypothermia";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.DIRECT;
        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 16;
        spell.active.cast = createCast(0,2F,"spell_engine:generic_frost_casting","spellbladenext:one_handed_area_charge",SpellSchools.FROST);
        Spell.Impact[] impacts = new Spell.Impact[]{createFrostImpact(6F,0F)};

        impacts[0].particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,30,0.1f,0.3F,0),
                new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,10,0.1f,0.3F,0),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.FROST, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.ASCEND).id().toString()
                        , ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,10,0.1f,0.3F,0),

        };
        Spell.Impact.TargetModifier targetModifier = new Spell.Impact.TargetModifier();
        targetModifier.execute = TriState.ALLOW;
        Spell.TargetCondition targetCondition = new Spell.TargetCondition();

        targetCondition.entity_predicate_id = "spell_engine:has_effect";
        targetCondition.entity_predicate_param = SpellbladesAndSuch.DEATHCHILL.getIdAsString();
        targetModifier.conditions = List.of(targetCondition);
        impacts[0].target_modifiers = List.of(targetModifier, createFrostImpact(1.0F,1.0F).target_modifiers.get(0),createFrostImpact(1.0F,1.0F).target_modifiers.get(1));

  /*      impacts[1].action =new  Spell.Impact.Action();
        impacts[1].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[1].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[1].action.status_effect.effect_id = SpellbladesAndSuch.DEATHCHILL.getIdAsString();
        impacts[1].action.status_effect.remove = new Spell.Impact.Action.StatusEffect.Remove();
        impacts[1].action.status_effect.remove.selector = Spell.Impact.Action.StatusEffect.Remove.Selector.FIRST;
*/
        spell.release = new Spell.Release();
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,30,0.1f,0.3F,0),
                new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,10,0.1f,0.3F,0),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.FROST, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.ASCEND).id().toString()
                        , ParticleBatch.Shape.PIPE, ParticleBatch.Origin.FEET, null,10,0.1f,0.3F,0),

        };
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FROST_RELEASE.id());
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 16, false, "runes:frost_stone");

        return new Entry(id, spell, title, description, null);
    }
    public static Entry feather_lash = add(feather_lash());
    public static Entry feather_lash() {
        var spell = projectileBase(SpellSchools.ARCANE,Identifier.of("spellbladenext:projectile/feather"),1.6F,0);
        spell.school = SpellSchools.FIRE;
        spell.group = "primary";

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.sticky = true;
        var id = Identifier.of(MOD_ID, "feather_lash");
        var description = "Shoot a feather projectile, dealing {damage} fire damage to the first enemy hit.";
        var title = "Feather Lash";
        spell.deliver.projectile.projectile.divergence = 15;
        spell.deliver.projectile.projectile.homing_after_absolute_distance = 8;
        spell.deliver.projectile.projectile.homing_after_relative_distance = 0.4F;
        spell.deliver.projectile.projectile.homing_angle = 60;

        spell.deliver.projectile.projectile.client_data.model.scale = 1;
        spell.learn = new Spell.Learn();
        spell.tier = 1;
        spell.range = 64;
        spell.active.cast = createCast(0,0.5F,"spell_engine:generic_fire_casting","spellbladenext:wand_cast_projectile_1",SpellSchools.FIRE);

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.flame.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,3,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,1,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,1,0.05f,0.1F,360),
                new ParticleBatch("minecraft:smoke", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360)

        };
        spell.deliver.projectile.projectile.client_data.travel_particles = particlebatch;
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createFireImpact(0.6F,0F);



        spell.release = new Spell.Release();
        spell.release.animation = "spellbladenext:wand_cast_projectile_1";
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id().toString());
        spell.impacts = List.of(impacts[0]);

        configureCooldown(spell, 0F, false, "runes:fire_stone");

        return new Entry(id, spell, title, description, null);
    }
    public static Entry multilash = add(multilash());
    public static Entry multilash() {
        var spell = projectileBase(SpellSchools.ARCANE,Identifier.of("spellbladenext:projectile/feather"),1.6F,0);
        spell.school = SpellSchools.FIRE;
        spell.range = 64;
        spell.tier = 3;

        spell.type = Spell.Type.PASSIVE;
        spell.passive = passiveSpellBase().passive;
        var trigger = new Spell.Trigger();
        trigger.type = Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.id = "spellbladenext:feather_lash";
        trigger.chance = 0.25F;
        spell.passive.triggers = List.of(trigger);
        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var id = Identifier.of(MOD_ID, "featherstorm");
        var description = "Upon hitting an enemy with Feather Lash, you have a chance to shoot three more feather projectiles at the same enemy.";
        var title = "Featherstorm";

        spell.deliver.projectile.projectile.divergence = 15;
        var dOffset1 = new Spell.Delivery.ShootProjectile.DirectionOffset();
        var dOffset2 = new Spell.Delivery.ShootProjectile.DirectionOffset();
        var dOffset3 = new Spell.Delivery.ShootProjectile.DirectionOffset();
        dOffset1.yaw = -45;
        dOffset2.yaw = 0;
        dOffset3.yaw = 45;
        spell.deliver.projectile.direction_offsets = new Spell.Delivery.ShootProjectile.DirectionOffset[]{
                dOffset1,dOffset2,dOffset3
        };
        spell.deliver.projectile.launch_properties = new Spell.LaunchProperties();
        spell.deliver.projectile.launch_properties.extra_launch_count = 2;
        spell.deliver.projectile.launch_properties.extra_launch_delay = 2;
        spell.deliver.projectile.projectile.homing_angle = 60;

        spell.deliver.projectile.projectile.homing_after_absolute_distance = 8;
        spell.deliver.projectile.projectile.homing_after_relative_distance = 0.4F;

        spell.deliver.projectile.projectile.client_data.model.scale = 1;
        spell.learn = new Spell.Learn();
        spell.tier = 1;
        spell.range = 64;
        spell.active = null;
        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.flame.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,3,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,1,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,1,0.05f,0.1F,360),
                new ParticleBatch("minecraft:smoke", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360)

        };
        spell.deliver.projectile.projectile.client_data.travel_particles = particlebatch;
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createFireImpact(0.6F,0F);



        spell.release = new Spell.Release();
        spell.release.animation = "spellbladenext:wand_cast_projectile_1";
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id().toString());
        spell.impacts = List.of(impacts[0]);

        configureCooldown(spell, 0F, false, "runes:fire_stone");

        return new Entry(id, spell, title, description, null);
    }
    public static Entry exploding_feathers = add(exploding_feathers());
    public static Entry exploding_feathers() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.FIRE;

        var id = Identifier.of(MOD_ID, "exploding_feathers");
        var description = "Shoot a feather projectile, dealing {damage} fire damage to the first enemy hit.";
        var title = "Exploding Feathers";

        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range = 0;
        spell.active.cast = createCast(0,1,"spell_engine:generic_fire_casting","spellbladenext:one_handed_area_charge",SpellSchools.FIRE);

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.flame.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,3,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,1,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,1,0.05f,0.1F,360),
                new ParticleBatch("minecraft:firework", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360)

        };
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.STASH_EFFECT;
        spell.deliver.stash_effect = new Spell.Delivery.StashEffect();
        var trigger = new Spell.Trigger();
        trigger.chance = 1;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.id = "spellbladenext:feather_lash";
        trigger.type = Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        spell.deliver.stash_effect.triggers = List.of(trigger);
        spell.deliver.stash_effect.amplifier = 7;
        spell.deliver.stash_effect.duration = 30;
        spell.deliver.stash_effect.id = SpellbladesAndSuch.FEATHER.getIdAsString();
        var impact = createFireImpact(0.4F,1);
        var impacts = new Spell.Impact[]{
                impact
        };
        spell.area_impact = new Spell.AreaImpact();
        spell.area_impact.area = new Spell.Target.Area();
        spell.area_impact.radius = 4;
        spell.release = new Spell.Release();
        spell.area_impact.particles = new ParticleBatch[]{
                new ParticleBatch("spell_engine:fire_explosion", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,2,0.05f,1F,360),
        };
        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id().toString());
        spell.impacts = List.of(impacts[0]);

        configureCooldown(spell, 4, false, "runes:fire_stone");

        return new Entry(id, spell, title, description, null);
    }
    public static Entry healing_feathers = add(healing_feathers());
    public static Entry healing_feathers() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.FIRE;

        var id = Identifier.of(MOD_ID, "healing_feathers");
        var description = "Shoot a feather projectile, dealing {damage} fire damage to the first enemy hit.";
        var title = "Feather Lash";

        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 4;
        spell.active.cast = createCast(0,1,"spell_engine:generic_fire_casting","spellbladenext:one_handed_area_charge",SpellSchools.FIRE);

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.flame.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,3,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,1,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,1,0.05f,0.1F,360),
                new ParticleBatch("minecraft:firework", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360)

        };
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.STASH_EFFECT;
        spell.deliver.stash_effect = new Spell.Delivery.StashEffect();
        var trigger = new Spell.Trigger();
        trigger.chance = 1;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.id = "spellbladenext:feather_lash";
        trigger.type = Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        spell.deliver.stash_effect.triggers = List.of(trigger);
        spell.deliver.stash_effect.amplifier = 7;
        spell.deliver.stash_effect.duration = 30;
        spell.deliver.stash_effect.id = SpellbladesAndSuch.FEATHERHEAL.getIdAsString();
        var impact = createHealingImpact(1.2F,0);
        impact.action.heal = new Spell.Impact.Action.Heal();
        impact.action.apply_to_caster = true;
        impact.action.heal.spell_power_coefficient = 1.2F;
        var impacts = new Spell.Impact[]{
                impact
        };
        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.include_caster = true;
        spell.release = new Spell.Release();

        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id().toString());
        spell.impacts = List.of(impacts[0]);

        configureCooldown(spell, 4, false, "runes:fire_stone");

        return new Entry(id, spell, title, description, null);
    }
    public static Entry amethyst_barrage = add(amethyst_barrage());
    public static Entry amethyst_barrage() {
        var spell = meteor_base(SpellSchools.ARCANE,Identifier.of("minecraft:amethyst_block"),1.0F,0);
        spell.school = SpellSchools.ARCANE;
        spell.deliver.type = Spell.Delivery.Type.METEOR;
        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.sticky = true;
        spell.deliver.meteor.launch_properties = new Spell.LaunchProperties();
        spell.deliver.meteor.launch_properties.extra_launch_delay = 2;
        spell.deliver.meteor.launch_properties.extra_launch_count = 7;

        spell.deliver.meteor.projectile.divergence = 30;
        spell.deliver.meteor.projectile.client_data.model.scale = 0F;
        spell.group = "primary";

        var id = Identifier.of(MOD_ID, "echofall");
        var description = "Send down a barrage of sound waves, each dealing {damage} damage.";
        var title = "Echofall";

        spell.learn = new Spell.Learn();
        spell.tier = 1;
        spell.range = 32;
        spell.active.cast = createCast(0,0.6F,"spell_engine:generic_arcane_casting","spell_engine:one_handed_sky_charge",SpellSchools.ARCANE);

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_arcane_spark_float", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,40,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,40,0.05f,0.1F,360)

        };
        ParticleBatch[] particlebatch2 = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_arcane_spark_float", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,40,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,40,0.05f,0.1F,360),
                new ParticleBatch("spell_engine:magic_arcane_spark_float", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,300,0.35f,0.45F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPELL, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,150,0.35f,0.45F,360)


        };
        spell.area_impact = new Spell.AreaImpact();
        spell.area_impact.area = new Spell.Target.Area();
        spell.area_impact.radius = 4;
        spell.area_impact.particles = particlebatch2;
        spell.deliver.meteor.projectile.client_data.travel_particles = particlebatch;
        spell.area_impact.sound = new Sound("minecraft:entity.wind_charge.wind_burst");
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createArcaneImpact(0.6F,0.5F);

        spell.impacts = List.of(impacts[0]);

        configureCooldown(spell, 4, false, "runes:arcane_stone");
        spell.release = new Spell.Release();

        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_ARCANE_RELEASE.id());
        spell.release.animation = "spellbladenext:one_handed_area_release";

        return new Entry(id, spell, title, description, null);
    }
    public static Entry resonance = add(resonance());

    public static Entry resonance() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.ARCANE;
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.DIRECT;

        var id = Identifier.of(MOD_ID, "resonance");
        var description = "On spell hit, apply stacks of Echoes based on your arcane power for 4 seconds.  When Echoes expires, enemies in an area take 1.6 arcane damage per echo stack.";
        var title = "Echoes";

        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 16;
        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.CASTER;


        spell.active.cast = createCast(0,2F,"spell_engine:generic_arcane_casting","spellbladenext:one_handed_area_charge",SpellSchools.ARCANE);


        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_arcane_spark_float", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,20,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,20,0.05f,0.1F,360)

        };
        ParticleBatch[] particlebatch2 = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_arcane_spark_float", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,20,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,20,0.05f,0.1F,360),
                new ParticleBatch("spell_engine:magic_arcane_spark_float", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,100,0.35f,0.75F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,100,0.35f,0.7F,360)


        };
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = new Spell.Impact();
        impacts[0].school = SpellSchools.ARCANE;
        impacts[0].action = new Spell.Impact.Action();
        impacts[0].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[0].action.apply_to_caster = true;

        impacts[0].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[0].action.status_effect.show_particles = false;
        impacts[0].action.status_effect.refresh_duration = false;
        impacts[0].action.status_effect.duration = 8F;
        impacts[0].action.status_effect.effect_id = SpellbladesAndSuch.RESONATING.getIdAsString();
        impacts[0].action.status_effect.apply_mode = Spell.Impact.Action.StatusEffect.ApplyMode.SET;
        impacts[0].action.status_effect.amplifier = 0;


        spell.impacts = List.of(impacts[0]);
        spell.release = new Spell.Release();

        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_ARCANE_RELEASE.id());
        spell.release.animation = "spellbladenext:one_handed_area_release";


        configureCooldown(spell, 30, false, "runes:arcane_stone");
        spell.cost.cooldown.haste_affected = false;
        return new Entry(id, spell, title, description, null);
    }
    public static Entry echo = add(echo());

    public static Entry echo() {
        var spell = passiveSpellBase();
        spell.school = SpellSchools.ARCANE;
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.DIRECT;
        spell.deliver.delay = 40;
        var id = Identifier.of(MOD_ID, "echoes");
        var description = "On spell hit, apply stacks of Echoes based on your arcane power for 4 seconds.  When Echoes expires, enemies in an area take 1.6 arcane damage per echo stack.";
        var title = "Echoes";

        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range = 16;
        spell.passive = passiveSpellBase().passive;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.FROM_TRIGGER;
        var trigger = new Spell.Trigger();
        trigger.chance = 1.0F;
        trigger.type = Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        trigger.impact = new Spell.Trigger.ImpactCondition();
        trigger.impact.impact_type = "DAMAGE";
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.id = "spellbladenext:reverberation_brand";

        var trigger2 = new Spell.Trigger();
        trigger2.chance = 1.0F;
        trigger2.type = Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        trigger2.impact = new Spell.Trigger.ImpactCondition();
        trigger2.impact.impact_type = "DAMAGE";
        trigger2.spell = new Spell.Trigger.SpellCondition();
        trigger2.spell.id = "spellbladenext:echofall";
        spell.passive.triggers = List.of(trigger,trigger2);
        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_arcane_spark_float", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,20,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,20,0.05f,0.1F,360)

        };
        ParticleBatch[] particlebatch2 = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_arcane_spark_float", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,20,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,20,0.05f,0.1F,360),
                new ParticleBatch("spell_engine:magic_arcane_spark_float", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,100,0.35f,0.75F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,100,0.35f,0.7F,360)


        };
        Spell.Impact[] impacts = new Spell.Impact[1];

        impacts[0] = createArcaneImpact(0.2F,0);


        spell.impacts = List.of(impacts[0]);
        spell.release = new Spell.Release();

        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_ARCANE_RELEASE.id());

        configureCooldown(spell, 0, false, null);
        spell.cost.durability = 0;
        return new Entry(id, spell, title, description, null);
    }
    public static Entry encore = add(encore());

    public static Entry encore() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.ARCANE;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.CASTER;
        var id = Identifier.of(MOD_ID, "encore");
        var description = "Reset all spell cooldowns.";
        var title = "Encore";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.DIRECT;
        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 0;
        spell.active.cast = createCast(0,0.5F,"spell_engine:generic_arcane_casting","spellbladenext:one_handed_area_charge",SpellSchools.ARCANE);
        Spell.Impact[] impacts = new Spell.Impact[2];
        spell.deliver.type = Spell.Delivery.Type.DIRECT;
        impacts[0] = createArcaneImpact(0,0F);
        impacts[0].action = new Spell.Impact.Action();
        impacts[0].action.type = Spell.Impact.Action.Type.COOLDOWN;
        impacts[0].action.apply_to_caster = true;
        impacts[0].action.cooldown = new Spell.Impact.Action.Cooldown();
        impacts[0].action.cooldown.actives = new Spell.Impact.Action.Cooldown.Modify();
        impacts[0].action.cooldown.actives.school = "spell_engine:arcane";
        impacts[0].action.cooldown.actives.duration_add = -9999;

        impacts[0].particles = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_arcane_spark_float", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,20,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,20,0.05f,0.1F,360)

        };
        spell.release = new Spell.Release();

        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_ARCANE_RELEASE.id());
        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 40F, false, "runes:arcane_stone");

        return new Entry(id, spell, title, description, null);

    }
    public static Entry reverb_brand = add(reverb_brand());

    public static Entry reverb_brand() {
        var spell = activeSpellBase();
        spell.school = SpellSchools.ARCANE;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.sticky = true;
        spell.target.aim.required = true;

        var id = Identifier.of(MOD_ID, "reverberation_brand");
        var description = "Blast an enemy for {damage} frost damage and inflict Wintertide on them, slowing them by 20%.";
        var title = "Wintertide";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.DIRECT;
        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range = 8;
        spell.active.cast = createCast(0,2F,"spell_engine:generic_arcane_casting","spellbladenext:one_handed_area_charge",SpellSchools.ARCANE);
        Spell.Impact[] impacts = new Spell.Impact[2];
        spell.deliver.type = Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = id.toString();
        impacts[0] = createArcaneImpact(0.12F,0F);

        impacts[0].particles = new ParticleBatch[]{
                new ParticleBatch("spell_engine:magic_arcane_spark_float", ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,20,0.05f,0.1F,360),
                new ParticleBatch(SpellEngineParticles.getMagicParticleVariant(SpellEngineParticles.ARCANE, SpellEngineParticles.MagicParticleFamily.Shape.SPARK, SpellEngineParticles.MagicParticleFamily.Motion.FLOAT).id().toString()
                        , ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null,20,0.05f,0.1F,360)

        };
        spell.release = new Spell.Release();

        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_ARCANE_RELEASE.id());
        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 35F, false, "runes:arcane_stone");

        return new Entry(id, spell, title, description, null);

    }

    public static void registerHandlers(){
        SpellEvents.SPELL_CAST.register((args)-> {
            if(args.spell().equals(SpellRegistry.from(args.caster().getWorld()).getEntry(phoenix_dive.id).get())){
                Spell.AreaImpact impact = new Spell.AreaImpact();
                impact.area = new Spell.Target.Area();
                impact.radius = 8;
                List<Spell.Impact> list = new ArrayList<>();
                list.add(createFireImpact(1.2F,1.0F));
                boolean bool =SpellHelper.lookupAndPerformAreaImpact(impact, SpellRegistry.from(args.caster().getWorld()).getEntry(phoenix_dive.id).get(),args.caster(), args.caster(),args.caster(),list,new SpellHelper.ImpactContext().power(SpellPower.getSpellPower(SpellSchools.FIRE,args.caster())).position(args.caster().getPos()),false);
            }
        });
    }

}
