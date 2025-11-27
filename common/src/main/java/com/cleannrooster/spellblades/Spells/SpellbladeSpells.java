package com.cleannrooster.spellblades.Spells;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.util.ArrayList;
import java.util.List;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.Spell.RangeMechanic;
import net.spell_engine.api.spell.Spell.Type;
import net.spell_engine.api.spell.Spell.Impact.Action.StatusEffect.ApplyMode;
import net.spell_engine.api.spell.Spell.Impact.Action.Teleport.Mode;
import net.spell_engine.api.spell.event.SpellEvents;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.spell.fx.ParticleBatch.Origin;
import net.spell_engine.api.spell.fx.ParticleBatch.Rotation;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.api.util.TriState;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.fx.SpellEngineSounds;
import net.spell_engine.fx.SpellEngineParticles.MagicParticles;
import net.spell_engine.fx.SpellEngineParticles.MagicParticles.Motion;
import net.spell_engine.fx.SpellEngineParticles.MagicParticles.Shape;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.target.SpellTarget.Intent;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import net.spell_power.api.SpellSchool.Archetype;

import static net.spell_power.api.SpellSchools.ARCANE;
import static net.spell_power.api.SpellSchools.FIRE;

public class SpellbladeSpells {
    public static final List<Entry> entries = new ArrayList();
    public static Entry amethyst_slash = add(amethyst_slash());
    public static Entry frost_slash = add(frost_slash());
    public static Entry riptide = add(riptide());
    public static Entry vault = add(vault());
    public static Entry staffspin = add(staffspin());
    public static Entry massacre = add(massacre());
    public static Entry forwardstrike = add(forwardstrike());
    public static Entry arcane_blink = add(arcane_blink());
    public static Entry arcane_blink_far = add(arcane_blink_far());
    public static Entry lightningSpellstrike = add(lightningSpellstrike());
    public static Entry arcaneSpellstrike = add(arcaneSpellstrike());
    public static Entry phasedash = addIfInstalled(phasedash(), "combat_roll");
    public static Entry fireSpellstrike = add(fireSpellstrike());
    public static Entry frostSpellstrike = add(frostSpellstrike());
    public static Entry arcaneOverdrive = add(arcaneOverdrive());
    public static Entry frostOverdrive = add(frostOverdrive());
    public static Entry flickeringflame = add(flickeringflame());
    public static Entry flameOverdrive = add(flameOverdrive());
    public static Entry flame_slash = add(flame_slash());
    public static Entry phoenix_dive = add(phoenix_dive());
    public static Entry dragon_slam = add(dragon_slam());
    public static Entry arctic_armor = add(arctic_armor());
    public static Entry wintertideBrand = add(wintertideBrand());
    public static Entry wintersgrasp = add(wintersgrasp());
    public static Entry WintersExpanse = add(WintersExpanse());
    public static Entry mass_hypothermia = add(mass_hypothermia());
    public static Entry feather_lash = add(feather_lash());
    public static Entry multilash = add(multilash());
    public static Entry exploding_feathers = add(exploding_feathers());
    public static Entry healing_feathers = add(healing_feathers());
    public static Entry amethyst_barrage = add(amethyst_barrage());
    public static Entry resonance = add(resonance());
    public static Entry echo = add(echo());
    public static Entry encore = add(encore());
    public static Entry reverb_brand = add(reverb_brand());
    public static Entry collapse =  add(collapse());
    public static Entry rime = add(rime());
    public static Entry ignite = add(ignite());
    public static Entry shatter =  add(shatter());
    public static Entry grav = add(grav());
    public static Entry greater_fireball = add(greater_fireball());

    public static Entry add(Entry entry) {
        entries.add(entry);
        return entry;
    }

    public static Entry addIfInstalled(Entry entry, String modid) {
        if (FabricLoader.getInstance().isModLoaded(modid)) {
            entries.add(entry);
        }

        return entry;
    }

    public static ParticleBatch arcaneCastingParticles() {
        return new ParticleBatch((new ParticleBatch(MagicParticles.get(Shape.ARCANE, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.WIDE_PIPE, Origin.FEET, 1.0F, 0.05F, 0.1F)).color(Color.ARCANE.toRGBA()));
    }

    public static ParticleBatch fireCastingParticles() {
        return new ParticleBatch(SpellEngineParticles.flame.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.WIDE_PIPE, Origin.FEET, 1.0F, 0.05F, 0.1F);
    }

    public static ParticleBatch frostCastingParticles() {
        return new ParticleBatch((new ParticleBatch(MagicParticles.get(Shape.FROST, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.WIDE_PIPE, Origin.FEET, 1.0F, 0.05F, 0.1F)).color(Color.FROST.toRGBA()));
    }

    public static Spell activeSpellBase() {
        Spell spell = new Spell();
        spell.range = 0.0F;
        spell.tier = 7;
        spell.learn = new Spell.Learn();
        spell.type = Type.ACTIVE;
        spell.active = new Spell.Active();
        return spell;
    }

    public static Spell passiveSpellBase() {
        Spell spell = new Spell();
        spell.range = 0.0F;
        spell.tier = 7;
        spell.type = Type.PASSIVE;
        spell.passive = new Spell.Passive();
        spell.tooltip = new Spell.Tooltip();
        spell.tooltip.name = new Spell.Tooltip.LineOptions(true, true);
        spell.tooltip.description.color = Formatting.DARK_GREEN.asString();
        spell.tooltip.description.show_in_compact = false;
        return spell;
    }

    public static Spell.Delivery createDelivery(Spell.Delivery.Type type) {
        Spell.Delivery delivery = new Spell.Delivery();
        delivery.type = type;
        return delivery;
    }

    public static Spell.Impact createImpact(Spell.Impact.Action.Type type, float coeff, float knockback) {
        Spell.Impact impact = new Spell.Impact();
        impact.action = new Spell.Impact.Action();
        impact.action.type = type;
        if (type == net.spell_engine.api.spell.Spell.Impact.Action.Type.DAMAGE) {
            impact.action.damage = new Spell.Impact.Action.Damage();
            impact.action.damage.knockback = knockback;
            impact.action.damage.spell_power_coefficient = coeff;
        }

        return impact;
    }

    public static Spell.Impact createArcaneImpact(float coeff, float knockback) {
        Spell.Impact impact = createImpact(net.spell_engine.api.spell.Spell.Impact.Action.Type.DAMAGE, coeff, knockback);
        impact.school = ARCANE;
        ParticleBatch[] hitParticles = new ParticleBatch[]{new ParticleBatch("spell_engine:magic_arcane_impact_burst", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, Rotation.LOOK, 20.0F, 0.2F, 0.7F, 360.0F)};
        impact.particles = hitParticles;
        Sound sound = new Sound(SpellEngineSounds.GENERIC_ARCANE_RELEASE.id());
        impact.sound = sound;
        return impact;
    }

    public static Spell.Impact createLightningImpact(float coeff, float knockback) {
        Spell.Impact impact = createImpact(net.spell_engine.api.spell.Spell.Impact.Action.Type.DAMAGE, coeff, knockback);
        impact.school = SpellSchools.LIGHTNING;
        ParticleBatch[] hitParticles = new ParticleBatch[]{(new ParticleBatch(SpellEngineParticles.electric_arc_A.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, Rotation.LOOK, 5.0F, 0.2F, 0.7F, 360.0F)).color(Color.HOLY.toRGBA()), (new ParticleBatch(SpellEngineParticles.electric_arc_B.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, Rotation.LOOK, 5.0F, 0.2F, 0.7F, 360.0F)).color(Color.HOLY.toRGBA())};
        impact.particles = hitParticles;
        Sound sound = new Sound(SpellEngineSounds.GENERIC_LIGHTNING_RELEASE.id());
        impact.sound = sound;
        return impact;
    }

    public static Spell.Impact createFrostImpact(float coeff, float knockback) {
        Spell.Impact impact = createImpact(net.spell_engine.api.spell.Spell.Impact.Action.Type.DAMAGE, coeff, knockback);
        impact.school = SpellSchools.FROST;
        ParticleBatch[] hitParticles = new ParticleBatch[]{new ParticleBatch("spell_engine:magic_frost_impact_burst", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, Rotation.LOOK, 20.0F, 0.2F, 0.7F, 360.0F)};
        impact.particles = hitParticles;
        Sound sound = new Sound(SpellEngineSounds.GENERIC_FROST_IMPACT.id());
        impact.sound = sound;
        return impact;
    }

    public static Spell.Impact createFireImpact(float coeff, float knockback) {
        Spell.Impact impact = createImpact(net.spell_engine.api.spell.Spell.Impact.Action.Type.DAMAGE, coeff, knockback);
        impact.school = SpellSchools.FIRE;
        ParticleBatch[] hitParticles = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.flame_spark.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, Rotation.LOOK, 20.0F, 0.2F, 0.7F, 360.0F), new ParticleBatch("minecraft:smoke", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, Rotation.LOOK, 20.0F, 0.2F, 0.7F, 360.0F)};
        impact.particles = hitParticles;
        Sound sound = new Sound("minecraft:entity.player.hurt_on_fire");
        impact.sound = sound;
        return impact;
    }

    public static Spell.Impact createHealingImpact(float coeff, float knockback) {
        Spell.Impact impact = createImpact(net.spell_engine.api.spell.Spell.Impact.Action.Type.HEAL, coeff, knockback);
        impact.school = SpellSchools.HEALING;
        impact.particles = new ParticleBatch[]{(new ParticleBatch(MagicParticles.get(Shape.HOLY, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 20.0F, 0.2F, 0.7F, 360.0F)).color(Color.HOLY.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.HOLY, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 20.0F, 0.1F, 0.35F, 360.0F)).color(Color.HOLY.toRGBA())};
        impact.sound = new Sound(SpellEngineSounds.GENERIC_HEALING_IMPACT_1.id());
        return impact;
    }

    public static Spell.Impact createPhysicalimpact(float coeff, float knockback) {
        Spell.Impact impact = createImpact(net.spell_engine.api.spell.Spell.Impact.Action.Type.DAMAGE, coeff, knockback);
        impact.school = ExternalSpellSchools.PHYSICAL_MELEE;
        return impact;
    }

    public static Spell.Active.Cast createCast(int channelticks, float duration, String sound, String animation, @Nullable SpellSchool school) {
        new Spell.Active.Cast();
        Spell.Active.Cast cast = new Spell.Active.Cast();
        cast.animation = animation;
        cast.sound = new Sound(sound);
        cast.channel_ticks = channelticks;
        cast.duration = duration;
        if (school != null) {
            if (school.equals(SpellSchools.FIRE)) {
                cast.particles = new ParticleBatch[]{fireCastingParticles()};
            }

            if (school.equals(SpellSchools.FROST)) {
                cast.particles = new ParticleBatch[]{frostCastingParticles()};
            }

            if (school.equals(ARCANE)) {
                cast.particles = new ParticleBatch[]{arcaneCastingParticles()};
            }
        }

        return cast;
    }

    public static void configureCooldown(Spell spell, float duration, boolean proportional, @Nullable String id) {
        if (spell.cost == null) {
            spell.cost = new Spell.Cost();
        }

        if (spell.cost.cooldown == null) {
            spell.cost.cooldown = new Spell.Cost.Cooldown();
        }

        if (id != null) {
            spell.cost.item = new Spell.Cost.Item();
            spell.cost.item.id = id;
            spell.cost.item.amount = 1;
        }

        if (proportional) {
            spell.cost.cooldown.proportional = true;
        }

        spell.cost.cooldown.duration = duration;
    }

    public static Spell projectileBase(SpellSchool school, Identifier projIdentifier, float velocity, float knockback) {
        Spell spell = activeSpellBase();
        spell.school = school;
        Spell.Delivery delivery = createDelivery(net.spell_engine.api.spell.Spell.Delivery.Type.PROJECTILE);
        delivery.projectile = new Spell.Delivery.ShootProjectile();
        delivery.projectile.launch_properties.velocity = velocity;
        delivery.projectile.projectile = new Spell.ProjectileData();
        delivery.projectile.projectile.client_data = new Spell.ProjectileData.Client();
        Spell.ProjectileModel model = new Spell.ProjectileModel();
        model.model_id = String.valueOf(projIdentifier);
        delivery.projectile.projectile.client_data.model = model;
        spell.deliver = delivery;
        Spell.Impact[] impact = new Spell.Impact[1];
        impact[0] = createImpact(net.spell_engine.api.spell.Spell.Impact.Action.Type.DAMAGE, 1.8F, knockback);
        spell.impacts = List.of(impact[0]);
        return spell;
    }

    public static Spell meteor_base(SpellSchool school, Identifier projIdentifier, float velocity, float knockback) {
        Spell spell = activeSpellBase();
        spell.school = school;
        Spell.Delivery delivery = createDelivery(net.spell_engine.api.spell.Spell.Delivery.Type.METEOR);
        delivery.meteor = new Spell.Delivery.Meteor();
        delivery.meteor.launch_properties.velocity = velocity;
        delivery.meteor.projectile = new Spell.ProjectileData();
        delivery.meteor.projectile.client_data = new Spell.ProjectileData.Client();
        Spell.ProjectileModel model = new Spell.ProjectileModel();
        model.model_id = String.valueOf(projIdentifier);
        delivery.meteor.projectile.client_data.model = model;
        spell.deliver = delivery;
        Spell.Impact[] impact = new Spell.Impact[1];
        impact[0] = createImpact(net.spell_engine.api.spell.Spell.Impact.Action.Type.DAMAGE, 1.8F, knockback);
        spell.impacts = List.of(impact[0]);
        return spell;
    }

    public static Entry amethyst_slash() {
        Spell spell = projectileBase(ARCANE, Identifier.tryParse("spellbladenext:projectile/amethyst"), 4.0F, 0.0F);
        spell.school = ARCANE;
        spell.deliver.projectile.projectile.perks.bounce = 4;
        spell.deliver.projectile.projectile.divergence = 15.0F;
        spell.deliver.projectile.projectile.client_data.model.scale = 2.0F;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "amethyst_slash");
        String description = "Attack with a flurry of amethyst projectiles, dealing {damage} Arcane damage per second.";
        String title = "Amethyst Slash";
        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 12.0F;
        spell.active.cast = createCast(3, 6.0F, "spell_engine:generic_arcane_casting", "spell_engine:flameslash", ARCANE);
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch("spell_engine:magic_arcane_spark_float", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 0.1F, 360.0F), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA())};
        spell.deliver.projectile.projectile.client_data.travel_particles = particlebatch;
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createArcaneImpact(1.8F, 0.0F);
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 4.0F, true, "runes:arcane_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry frost_slash() {
        Spell spell = projectileBase(ARCANE, Identifier.tryParse("spellbladenext:projectile/gladius"), 4.0F, 0.0F);
        spell.school = SpellSchools.FROST;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "frost_slash");
        String description = "Attack with a flurry of frost blade projectiles, dealing {damage} Frost damage per second.";
        String title = "Amethyst Slash";
        spell.deliver.projectile.projectile.perks.ricochet = 2;
        spell.deliver.projectile.projectile.divergence = 15.0F;
        spell.deliver.projectile.projectile.client_data.model.scale = 2.0F;
        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 12.0F;
        spell.active.cast = createCast(3, 6.0F, "spell_engine:generic_frost_casting", "spell_engine:flameslash", SpellSchools.FROST);
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch("spell_engine:magic_frost_spark_float", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 0.1F, 360.0F), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 0.1F, 360.0F)).color(Color.FROST.toRGBA())};
        spell.deliver.projectile.projectile.client_data.travel_particles = particlebatch;
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createFrostImpact(1.8F, 0.0F);
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 4.0F, true, "runes:frost_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry riptide() {
        Spell spell = activeSpellBase();
        spell.school = SpellSchools.FROST;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AIM;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "riptide");
        String description = "Perform a riptide maneuver in the targeted direction.";
        String title = "Riptide";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = id.toString();
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.required = false;
        spell.target.aim.use_caster_as_fallback = true;
        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range = 4.0F;
        spell.active.cast = createCast(0, 0.5F, "spell_engine:generic_frost_casting", "spellbladenext:one_handed_projectile_charge", SpellSchools.FROST);
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createFrostImpact(1.8F, 1.0F);
        impacts[1] = createPhysicalimpact(1.5F, 1.0F);
        spell.release = new Spell.Release();
        spell.impacts = List.of(impacts[0], impacts[1]);
        configureCooldown(spell, 4.0F, false, "runes:frost_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry vault() {
        Spell spell = activeSpellBase();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "vaulting_slam");
        String description = "Perform a riptide maneuver in the targeted direction.";
        String title = "Massacre";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.target.area = new Spell.Target.Area();
        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 6.0F;
        spell.active.cast = createCast(0, 1.25F, "spell_engine:generic_frost_casting", "spellbladenext:leapslamstaff1", (SpellSchool)null);
        spell.active.cast.movement_speed = 1.0F;
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createPhysicalimpact(6.0F, 4.0F);
        spell.release = new Spell.Release();
        spell.release.sound = new Sound("minecraft:entity.player.attack.crit");
        spell.release.particles = new ParticleBatch[]{new ParticleBatch("minecraft:smoke", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.FEET, (ParticleBatch.Rotation)null, 40.0F, 0.2F, 0.6F, 360.0F), new ParticleBatch("minecraft:cloud", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.FEET, (ParticleBatch.Rotation)null, 120.0F, 0.5F, 0.9F, 360.0F)};
        spell.release.animation = "spellbladenext:leapslamstaff2";
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 4.0F, false, (String)null);
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry staffspin() {
        Spell spell = activeSpellBase();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "whirling_assault");
        String description = "Perform a riptide maneuver in the targeted direction.";
        String title = "Massacre";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.target.area = new Spell.Target.Area();
        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range_mechanic = RangeMechanic.MELEE;
        spell.active.cast = createCast(4, 2.0F, "spell_engine:generic_frost_casting", "spellbladenext:staffspin", (SpellSchool)null);
        spell.active.cast.movement_speed = 1.0F;
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createPhysicalimpact(8.0F, 0.2F);
        spell.release = new Spell.Release();
        impacts[0].particles = new ParticleBatch[]{new ParticleBatch("minecraft:poof", net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.FEET, (ParticleBatch.Rotation)null, 3.0F, 0.1F, 0.2F, 360.0F)};
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 4.0F, false, (String)null);
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry massacre() {
        Spell spell = activeSpellBase();
        spell.school = SpellSchools.FROST;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "eviscerate");
        String description = "Perform a riptide maneuver in the targeted direction.";
        String title = "Massacre";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = id.toString();
        spell.target.area = new Spell.Target.Area();
        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 8.0F;
        spell.active.cast = createCast(2, 2.0F, "spell_engine:generic_frost_casting", "spellbladenext:staffspin", SpellSchools.FROST);
        spell.active.cast.movement_speed = 1.0F;
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createFrostImpact(2.0F, 0.2F);
        impacts[1] = createPhysicalimpact(1.8F, 0.2F);
        spell.release = new Spell.Release();
        spell.impacts = List.of(impacts[0], impacts[1]);
        configureCooldown(spell, 4.0F, false, "runes:frost_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry forwardstrike() {
        Spell spell = activeSpellBase();
        spell.school = ExternalSpellSchools.PHYSICAL_MELEE;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "advancing_strike");
        String description = "Teleport behind an enemy, dealing {damage} damage to them.";
        String title = "Rift Slash";
        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 135.0F;
        spell.target.area.include_caster = true;
        spell.learn = new Spell.Learn();
        spell.tier = 1;
        spell.range = 8.0F;
        spell.active.cast = createCast(0, 0.0F, "spell_engine:generic_frost_casting", "spell_engine:two_handed_slam_spellblade_2", (SpellSchool)null);
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createPhysicalimpact(4.0F, 2.0F);
        Spell.Impact teleport = new Spell.Impact();
        teleport.action = new Spell.Impact.Action();
        teleport.action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.TELEPORT;
        teleport.action.apply_to_caster = true;
        teleport.action.teleport = new Spell.Impact.Action.Teleport();
        spell.release = new Spell.Release();
        spell.release.animation = "spell_engine:two_handed_slam_spellblade_2";
        spell.release.sound = new Sound("minecraft:entity.player.attack.crit");
        impacts[0].particles = new ParticleBatch[]{new ParticleBatch("minecraft:poof", net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.FEET, (ParticleBatch.Rotation)null, 3.0F, 0.1F, 0.2F, 360.0F)};
        ParticleBatch[] var10000 = new ParticleBatch[]{new ParticleBatch("spell_engine:magic_arcane_spark_burst", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, Rotation.LOOK, 20.0F, 0.05F, 1.0F, 360.0F), new ParticleBatch("minecraft:firework", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, Rotation.LOOK, 20.0F, 0.05F, 0.1F, 360.0F)};
        teleport.action.teleport.mode = Mode.FORWARD;
        teleport.action.teleport.forward = new Spell.Impact.Action.Teleport.Forward();
        teleport.action.teleport.forward.distance = 2.0F;
        impacts[1] = teleport;
        spell.impacts = List.of(impacts[0], impacts[1]);
        configureCooldown(spell, 2.0F, false, (String)null);
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry arcane_blink() {
        Spell spell = activeSpellBase();
        spell.school = ARCANE;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AIM;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "arcane_blink");
        String description = "Teleport behind an enemy, dealing {damage} damage to them.";
        String title = "Rift Slash";
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.required = false;
        spell.target.aim.use_caster_as_fallback = true;
        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range = 4.0F;
        spell.active.cast = createCast(0, 0.5F, "spell_engine:generic_arcane_casting", "spellbladenext:one_handed_projectile_charge", ARCANE);
        Spell.Impact[] impacts = new Spell.Impact[3];
        impacts[0] = createArcaneImpact(2.0F, 1.0F);
        impacts[1] = createPhysicalimpact(1.2F, 1.0F);
        Spell.Impact teleport = new Spell.Impact();
        teleport.action = new Spell.Impact.Action();
        teleport.action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.TELEPORT;
        teleport.action.teleport = new Spell.Impact.Action.Teleport();
        spell.release = new Spell.Release();
        spell.release.animation = "spell_engine:dashslash";
        spell.release.sound = new Sound("minecraft:entity.player.attack.sweep");
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch("spell_engine:magic_arcane_spark_burst", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, Rotation.LOOK, 20.0F, 0.05F, 1.0F, 360.0F), new ParticleBatch("minecraft:firework", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, Rotation.LOOK, 20.0F, 0.05F, 0.1F, 360.0F)};
        teleport.action.teleport.arrive_particles = particlebatch;
        teleport.action.teleport.depart_particles = particlebatch;
        teleport.action.apply_to_caster = true;
        teleport.action.teleport.mode = Mode.FORWARD;
        teleport.action.teleport.forward = new Spell.Impact.Action.Teleport.Forward();
        teleport.action.teleport.forward.distance = 8.0F;
        impacts[2] = teleport;
        spell.impacts = List.of(impacts[0], impacts[1], impacts[2]);
        configureCooldown(spell, 4.0F, false, "runes:arcane_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry arcane_blink_far() {
        Spell spell = activeSpellBase();
        spell.school = ARCANE;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 15.0F;
        spell.target.area.include_caster = true;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "sonic_strike");
        String description = "Teleport to the furthest enemy in a line, dealing {damage} damage to all enemies in the path.";
        String title = "Sonic Strike";
        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 24.0F;
        spell.active.cast = createCast(0, 2.0F, "spell_engine:generic_arcane_casting", "spell_engine:crosscharge", ARCANE);
        Spell.Impact[] impacts = new Spell.Impact[3];
        spell.release = new Spell.Release();
        spell.release.animation = "spell_engine:dashslash";
        spell.release.sound = new Sound("minecraft:entity.warden.sonic_boom");
        impacts[0] = createArcaneImpact(4.0F, 1.0F);
        impacts[1] = createPhysicalimpact(2.2F, 1.0F);
        Spell.Impact teleport = new Spell.Impact();
        teleport.action = new Spell.Impact.Action();
        teleport.action.teleport = new Spell.Impact.Action.Teleport();
        teleport.action.teleport.forward = new Spell.Impact.Action.Teleport.Forward();
        teleport.action.teleport.forward.distance = 28.0F;
        teleport.action.apply_to_caster = true;
        teleport.action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.TELEPORT;
        ParticleBatch[] particlebatch = new ParticleBatch[]{(new ParticleBatch(MagicParticles.get(Shape.ARCANE, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, Rotation.LOOK, 20.0F, 0.05F, 1.0F, 360.0F)).color(Color.ARCANE.toRGBA()), new ParticleBatch("minecraft:firework", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, Rotation.LOOK, 20.0F, 0.05F, 0.1F, 360.0F)};
        teleport.action.teleport.arrive_particles = particlebatch;
        teleport.action.teleport.depart_particles = particlebatch;
        teleport.action.teleport.mode = Mode.FORWARD;
        impacts[2] = teleport;
        spell.impacts = List.of(impacts[0], impacts[1], teleport);
        configureCooldown(spell, 16.0F, false, "runes:arcane_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry lightningSpellstrike() {
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "lightning_spellstrike");
        String description = "On melee hit: 100% chance to deal arcane damage in an cone in front of you.";
        String title = "Arcane Spellstrike";
        Spell spell = passiveSpellBase();
        spell.group = "spellstrike";
        spell.tier = 0;
        spell.range = 6.0F;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.LIGHTNING;
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.chance = 1.0F;
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = Archetype.MAGIC;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.FROM_TRIGGER;
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.passive.triggers = List.of(trigger);
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_LIGHTNING_RELEASE.id());
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createLightningImpact(0.4F, 1.0F);
        impacts[0].action.min_power = 2.0F;
        spell.impacts = List.of(impacts);
        configureCooldown(spell, 0.0F, false, (String)null);
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry arcaneSpellstrike() {
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "arcane_spellstrike");
        String description = "On melee hit: 100% chance to deal arcane damage in an cone in front of you.";
        String title = "Arcane Spellstrike";
        Spell spell = passiveSpellBase();
        spell.group = "spellstrike";
        spell.tier = 0;
        spell.range = 6.0F;
        spell.learn = new Spell.Learn();
        spell.school = ARCANE;
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.chance = 1.0F;
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = Archetype.MAGIC;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.FROM_TRIGGER;
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = "spellbladenext:spellstrike";
        spell.passive.triggers = List.of(trigger);
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id());
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createArcaneImpact(0.4F, 1.0F);
        impacts[0].action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.CUSTOM;
        impacts[0].action.custom = new Spell.Impact.Action.Custom();
        impacts[0].action.custom.handler = "spellbladenext:spellstrike";
        impacts[0].action.min_power = 2.0F;
        spell.impacts = List.of(impacts);
        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(MagicParticles.get(Shape.ARCANE,Motion.BURST).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 30.0F, 0.1F, 0.3F, 53),
                new ParticleBatch(MagicParticles.get(Shape.SPELL,Motion.BURST).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 10.0F, 0.1F, 0.3F, 53),
                new ParticleBatch(MagicParticles.get(Shape.SPARK,Motion.BURST).toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 10.0F, 0.1F, 0.3F, 53),
        };
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id());
        configureCooldown(spell, 0.0F, false, (String)null);
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry phasedash() {
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "phase_dash");
        String description = "On melee hit: 100% chance to deal arcane damage in an cone in front of you.";
        String title = "Arcane Spellstrike";
        Spell spell = passiveSpellBase();
        spell.tier = 2;
        spell.sub_tier = 2;
        spell.range = 0.0F;
        spell.learn = new Spell.Learn();
        spell.school = ARCANE;
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.chance = 1.0F;
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.ROLL;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = Archetype.MAGIC;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.FROM_TRIGGER;
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.passive.triggers = List.of(trigger);
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = new Spell.Impact();
        impacts[0].action = new Spell.Impact.Action();
        impacts[0].action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[0].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[0].action.status_effect.effect_id = SpellbladesAndSuch.PHASEDASH.getIdAsString();
        impacts[0].action.status_effect.duration = 0.2F;
        impacts[0].action.apply_to_caster = true;
        ParticleBatch[] particlebatch = new ParticleBatch[]{(new ParticleBatch(MagicParticles.get(Shape.ARCANE, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.WIDE_PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 12.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.WIDE_PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 12.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA())};
        impacts[0].particles = particlebatch;
        spell.impacts = List.of(impacts);
        configureCooldown(spell, 0.0F, false, (String)null);
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry fireSpellstrike() {
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "fire_spellstrike");
        String description = "On melee hit: 100% chance to deal arcane damage in an cone in front of you.";
        String title = "Arcane Spellstrike";
        Spell spell = passiveSpellBase();
        spell.group = "spellstrike";
        spell.tier = 0;
        spell.range = 6.0F;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.FIRE;
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.chance = 1.0F;
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = Archetype.MAGIC;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.FROM_TRIGGER;
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = "spellbladenext:spellstrike";
        spell.passive.triggers = List.of(trigger);
        spell.release.sound = new Sound("spell_engine:generic_fire_release");
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createFireImpact(0.4F, 1.0F);
        impacts[0].action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.CUSTOM;
        impacts[0].action.custom = new Spell.Impact.Action.Custom();
        impacts[0].action.custom.handler = "spellbladenext:spellstrike";
        impacts[0].action.min_power = 2.0F;
        spell.impacts = List.of(impacts);
        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.flame.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 30.0F, 0.1F, 0.3F, 53),
                new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 10.0F, 0.1F, 0.3F, 53),
                new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 10.0F, 0.1F, 0.3F, 53),
        };
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id());
        configureCooldown(spell, 0.0F, false, (String)null);
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry frostSpellstrike() {
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "frost_spellstrike");
        String description = "On melee hit: 100% chance to deal arcane damage in an cone in front of you.";
        String title = "Arcane Spellstrike";
        Spell spell = passiveSpellBase();
        spell.group = "spellstrike";
        spell.tier = 0;
        spell.range = 6.0F;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.FROST;
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.chance = 1.0F;
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = Archetype.MAGIC;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.FROM_TRIGGER;
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = "spellbladenext:spellstrike";
        spell.passive.triggers = List.of(trigger);
        spell.release.sound = new Sound("spell_engine:generic_frost_release");
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createFrostImpact(0.4F, 1.0F);
        impacts[0].action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.CUSTOM;
        impacts[0].action.custom = new Spell.Impact.Action.Custom();
        impacts[0].action.custom.handler = "spellbladenext:spellstrike";
        impacts[0].action.min_power = 2.0F;
        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch(MagicParticles.get(Shape.FROST,Motion.BURST).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 30.0F, 0.1F, 0.3F, 53),
                new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 10.0F, 0.1F, 0.3F, 53),
                new ParticleBatch(MagicParticles.get(Shape.SPARK,Motion.BURST).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 10.0F, 0.1F, 0.3F, 53),
                };
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FROST_RELEASE.id());

        spell.impacts = List.of(impacts);
        configureCooldown(spell, 0.0F, false, (String)null);
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry arcaneOverdrive() {
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "arcane_burst");
        String description = "On melee hit: 100% chance to deal arcane damage in an cone in front of you.";
        String title = "Arcane Burst";
        Spell spell = passiveSpellBase();
        spell.group = "primary";
        spell.tier = 1;
        spell.range = 6.0F;
        spell.learn = new Spell.Learn();
        spell.school = ARCANE;
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.chance = 1.0F;
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = Archetype.MAGIC;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.cap = 8;
        spell.target.area.angle_degrees = 360.0F;
        spell.deliver = new Spell.Delivery();
        Spell.Delivery.ShootProjectile projectile = projectileBase(ARCANE, Identifier.tryParse("spellbladenext:projectile/amethyst"), 1.0F, 1.0F).deliver.projectile;
        projectile.launch_properties = new Spell.LaunchProperties();
        projectile.launch_properties.velocity = 1.0F;
        Spell.ProjectileData projectileData = new Spell.ProjectileData();
        projectileData.homing_angle = 30.0F;
        projectileData.divergence = 10.0F;
        projectile.direct_towards_target = true;
        projectileData.homing_after_relative_distance = 0.4F;
        projectileData.homing_after_absolute_distance = 4.0F;
        projectileData.client_data = new Spell.ProjectileData.Client();
        projectileData.client_data.model = new Spell.ProjectileModel();
        projectileData.client_data.model.scale = 0.0F;
        ParticleBatch[] particlebatch = new ParticleBatch[]{(new ParticleBatch(MagicParticles.get(Shape.ARCANE, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 6.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA())};
        projectileData.client_data.travel_particles = particlebatch;
        projectile.projectile = projectileData;
        spell.deliver.projectile = projectile;
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.PROJECTILE;
        spell.passive.triggers = List.of(trigger);
        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.release.sound = new Sound("spell_engine:generic_arcane_release");
        ParticleBatch[] var10000 = new ParticleBatch[]{new ParticleBatch("minecraft:dragon_breath", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 30.0F, 0.1F, 0.3F, 45.0F), new ParticleBatch("minecraft:firework", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 30.0F, 0.5F, 0.9F, 45.0F)};
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createArcaneImpact(1.0F, 1.0F);
        impacts[0].action.min_power = 2.0F;
        spell.impacts = List.of(impacts);
        configureCooldown(spell, 1.0F, false, null);
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry frostOverdrive() {
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "frost_burst");
        String description = "On melee hit: 100% chance to deal arcane damage in an cone in front of you.";
        String title = "Frost Burst";
        Spell spell = passiveSpellBase();
        spell.group = "primary";
        spell.tier = 1;
        spell.range = 16.0F;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.FROST;
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.chance = 1.0F;
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = Archetype.MAGIC;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.cap = 4;
        spell.target.area.angle_degrees = 90.0F;
        spell.deliver = new Spell.Delivery();
        Spell.Delivery.ShootProjectile projectile = projectileBase(ARCANE, Identifier.tryParse("spellbladenext:projectile/gladius"), 1.0F, 1.0F).deliver.projectile;
        projectile.launch_properties = new Spell.LaunchProperties();
        projectile.launch_properties.velocity = 1.0F;
        Spell.ProjectileData projectileData = new Spell.ProjectileData();
        projectileData.homing_angle = 15.0F;
        projectileData.divergence = 0.0F;
        projectileData.perks.pierce = 5;
        projectile.direct_towards_target = true;
        projectileData.client_data = new Spell.ProjectileData.Client();
        projectileData.client_data.model = new Spell.ProjectileModel();
        projectileData.client_data.model.scale = 1.0F;
        spell.deliver.projectile = projectile;
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.PROJECTILE;
        projectileData.client_data.travel_particles = new ParticleBatch[]{(new ParticleBatch(MagicParticles.get(Shape.FROST, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 0.1F, 360.0F)).color(Color.FROST.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 0.1F, 360.0F)).color(Color.FROST.toRGBA())};
        projectile.projectile = projectileData;
        spell.passive.triggers = List.of(trigger);
        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.release.sound = new Sound("spell_engine:generic_frost_release");
        ParticleBatch[] var10000 = new ParticleBatch[]{(new ParticleBatch(MagicParticles.get(Shape.FROST, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 30.0F, 0.1F, 0.3F, 45.0F)).color(Color.FROST.toRGBA()), new ParticleBatch("minecraft:firework", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 30.0F, 0.5F, 0.9F, 45.0F)};
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createFrostImpact(1.0F, 1.0F);
        impacts[0].action.min_power = 2.0F;
        spell.impacts = List.of(impacts);
        configureCooldown(spell, 1.0F, false, null);
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry flickeringflame() {
        Spell spell = activeSpellBase();
        spell.school = SpellSchools.FIRE;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "flickering_flame");
        String description = "Perform a riptide maneuver in the targeted direction.";
        String title = "Flickering Flame";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = id.toString();
        spell.target.area = new Spell.Target.Area();
        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 8.0F;
        spell.active.cast = createCast(0, 0.25F, "spell_engine:generic_fire_casting", "spellbladenext:one_handed_projectile_charge", SpellSchools.FIRE);
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createFireImpact(0.9F, 0.1F);
        impacts[1] = createPhysicalimpact(0.6F, 0.1F);
        spell.release = new Spell.Release();
        spell.release.sound = new Sound("minecraft:entity.player.attack.sweep");
        spell.release.animation = "spellbladenext:flourish";
        spell.impacts = List.of(impacts[0], impacts[1]);
        configureCooldown(spell, 0.25F, false, "runes:fire_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry flameOverdrive() {
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "flame_burst");
        String description = "On melee hit: 100% chance to deal fire damage in an cone in front of you.";
        String title = "Flame  Burst";
        Spell spell = passiveSpellBase();
        spell.group = "primary";
        spell.tier = 1;
        spell.range = 6.0F;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.FIRE;
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.chance = 1.0F;
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = Archetype.MAGIC;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 90.0F;
        spell.passive.triggers = List.of(trigger);
        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.release.sound = new Sound("spell_engine:generic_frost_release");
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.flame.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 30.0F, 0.1F, 0.3F, 45.0F), new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 10.0F, 0.1F, 0.3F, 45.0F), new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 10.0F, 0.1F, 0.3F, 45.0F), new ParticleBatch("minecraft:smoke", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CONE, Origin.LAUNCH_POINT, Rotation.LOOK, 30.0F, 0.5F, 0.9F, 45.0F)};
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createFireImpact(1.0F, 1.0F);
        impacts[0].action.min_power = 2.0F;
        spell.impacts = List.of(impacts);
        configureCooldown(spell, 1.0F, false, null);
        spell.release.particles = particlebatch;
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry flame_slash() {
        Spell spell = projectileBase(ARCANE, Identifier.tryParse("spellbladenext:projectile/flamewaveprojectile"), 4.0F, 0.0F);
        spell.school = SpellSchools.FIRE;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "flame_slash");
        String description = "Attack with a flurry of flame wave projectiles, dealing {damage} Fire damage per second.";
        String title = "Flame Slash";
        spell.deliver.projectile.projectile.divergence = 15.0F;
        spell.deliver.projectile.projectile.client_data.model.scale = 2.0F;
        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 12.0F;
        spell.active.cast = createCast(3, 6.0F, "spell_engine:generic_frost_casting", "spell_engine:flameslash", SpellSchools.FROST);
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.flame.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 3.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 1.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 1.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch("minecraft:smoke", net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 0.1F, 360.0F)};
        spell.deliver.projectile.projectile.client_data.travel_particles = particlebatch;
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createFireImpact(2.4F, 0.0F);
        spell.area_impact = new Spell.AreaImpact();
        spell.area_impact.area = new Spell.Target.Area();
        spell.area_impact.radius = 2.0F;
        spell.area_impact.particles = new ParticleBatch[]{new ParticleBatch("spell_engine:fire_explosion", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 1.0F, 360.0F)};
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 4.0F, true, "runes:fire_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry phoenix_dive() {
        Spell spell = activeSpellBase();
        spell.school = SpellSchools.FIRE;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "phoenix_dive");
        String description = "Teleport forward 12 blocks and deal {damage} fire damage to enemies in an area upon landing..";
        String title = "Phoenix Dive";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.include_caster = true;
        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 8.0F;
        spell.active.cast = createCast(0, 1.0F, "spell_engine:generic_fire_casting", "spell_engine:phoenixdive", SpellSchools.FIRE);
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[1] = createFireImpact(2.4F, 1.0F);
        Spell.Impact teleport = new Spell.Impact();
        teleport.action = new Spell.Impact.Action();
        teleport.action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.TELEPORT;
        teleport.action.apply_to_caster = true;
        teleport.action.teleport = new Spell.Impact.Action.Teleport();
        teleport.action.teleport.intent = Intent.HELPFUL;
        teleport.action.teleport.mode = Mode.FORWARD;
        teleport.action.teleport.forward = new Spell.Impact.Action.Teleport.Forward();
        teleport.action.teleport.forward.distance = 12.0F;
        spell.release = new Spell.Release();
        impacts[0] = teleport;
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.flame.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.FEET, (ParticleBatch.Rotation)null, 30.0F, 0.8F, 1.0F, 0.0F), new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.5F, 1.0F, 0.0F), new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.5F, 1.0F, 0.0F)};
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound("minecraft:entity.player.attack.knockback");
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 12.0F, false, "runes:fire_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry dragon_slam() {
        Spell spell = activeSpellBase();
        spell.school = SpellSchools.FIRE;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "dragon_slam");
        String description = "Perform a riptide maneuver in the targeted direction.";
        String title = "Dagon Slam";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = id.toString();
        spell.target.area = new Spell.Target.Area();
        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 8.0F;
        spell.active.cast = createCast(0, 0.0F, "spell_engine:generic_fire_casting", "spell_engine:vertspin", SpellSchools.FIRE);
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createFireImpact(1.6F, 1.0F);
        impacts[1] = createPhysicalimpact(1.0F, 1.0F);
        spell.release = new Spell.Release();
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.flame.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.FEET, (ParticleBatch.Rotation)null, 30.0F, 0.8F, 1.0F, 0.0F), new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.5F, 1.0F, 0.0F), new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.5F, 1.0F, 0.0F)};
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound("minecraft:entity.player.attack.knockback");
        spell.impacts = List.of(impacts[0], impacts[1]);
        configureCooldown(spell, 12.0F, false, "runes:fire_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry arctic_armor() {
        Spell spell = activeSpellBase();
        spell.school = SpellSchools.FROST;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.CASTER;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "arctic_armor");
        String description = "Give yourself a buff for 16 seconds that retaliates against enemies that melee attack you, dealing {damage} frost damage and high knockback.";
        String title = "Arctic Armor";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.STASH_EFFECT;
        spell.deliver.stash_effect = new Spell.Delivery.StashEffect();
        spell.deliver.stash_effect.duration = 16.0F;
        spell.deliver.stash_effect.id = Identifier.of(SpellbladesAndSuch.MOD_ID, "arctic_armor").toString();
        spell.deliver.stash_effect.consume = 0;
        List<Spell.Trigger> triggers = new ArrayList();
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.chance = 1.0F;
        trigger.cap_per_tick = 1;
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.DAMAGE_TAKEN;
        triggers.add(trigger);
        spell.deliver.stash_effect.triggers = triggers;
        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range = 6.0F;
        spell.active.cast = createCast(0, 2.0F, "spell_engine:generic_frost_casting", "spellbladenext:one_handed_area_charge", SpellSchools.FROST);
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createFrostImpact(0.6F, 2.0F);
        impacts[1] = new Spell.Impact();
        impacts[1].action = new Spell.Impact.Action();
        impacts[1].action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[1].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[1].action.status_effect.effect_id = SpellbladesAndSuch.DEATHCHILL.getIdAsString();
        impacts[1].action.status_effect.duration = 8.0F;
        spell.release = new Spell.Release();
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 30.0F, 0.1F, 0.3F, 0.0F), new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F), (new ParticleBatch(MagicParticles.get(Shape.FROST, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F)).color(Color.FROST.toRGBA())};
        impacts[1].particles = particlebatch;
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FROST_RELEASE.id());
        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.impacts = List.of(impacts[0], impacts[1]);
        configureCooldown(spell, 0.5F, false, "runes:frost_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry wintertideBrand() {
        Spell spell = activeSpellBase();
        spell.school = SpellSchools.FROST;
        spell.group = "primary";
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.required = true;
        spell.target.aim.sticky = true;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "wintertide");
        String description = "Blast an enemy for {damage} frost damage and inflict Wintertide on them, slowing them by 20%.";
        String title = "Wintertide";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.learn = new Spell.Learn();
        spell.tier = 1;
        spell.range = 8.0F;
        spell.active.cast = createCast(0, 0.5F, "spell_engine:generic_frost_casting", "spellbladenext:one_handed_area_charge", SpellSchools.FROST);
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createFrostImpact(0.5F, 0.0F);
        impacts[1] = new Spell.Impact();
        impacts[1].action = new Spell.Impact.Action();
        impacts[1].action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[1].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[1].action.status_effect.effect_id = SpellbladesAndSuch.DEATHCHILL.getIdAsString();
        impacts[1].action.status_effect.duration = 8.0F;
        spell.release = new Spell.Release();
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 30.0F, 0.1F, 0.3F, 0.0F), new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F), (new ParticleBatch(MagicParticles.get(Shape.FROST, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F)).color(Color.FROST.toRGBA())};
        impacts[1].particles = particlebatch;
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FROST_RELEASE.id());
        spell.impacts = List.of(impacts[0], impacts[1]);
        configureCooldown(spell, 0.4F, false, "runes:frost_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry wintersgrasp() {
        Spell spell = passiveSpellBase();
        spell.tier = 2;
        spell.range = 16.0F;
        spell.learn = new Spell.Learn();
        spell.school = SpellSchools.FROST;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AIM;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "shared_suffering");
        String description = "When you successfuly damage an enemy with Wintertide, enemies with Wintertide on them in an area around you take {damage} frost damage.";
        String title = "Shared Suffering";
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.chance = 1.0F;
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.id = "spellbladenext:wintertide";
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 360.0F;
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.passive.triggers = List.of(trigger);
        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.release.sound = new Sound("spell_engine:generic_frost_release");
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 30.0F, 0.1F, 0.3F, 0.0F), new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F), (new ParticleBatch(MagicParticles.get(Shape.FROST, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F)).color(Color.FROST.toRGBA())};
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createFrostImpact(0.5F, 0.0F);
        impacts[0].particles = particlebatch;
        Spell.Impact.TargetModifier targetModifier = new Spell.Impact.TargetModifier();
        targetModifier.execute = TriState.ALLOW;
        Spell.TargetCondition targetCondition = new Spell.TargetCondition();
        targetCondition.entity_predicate_id = "spell_engine:has_effect";
        targetCondition.entity_predicate_param = SpellbladesAndSuch.DEATHCHILL.getIdAsString();
        targetModifier.conditions = List.of(targetCondition);
        Spell.Impact.TargetModifier targetModifier2 = new Spell.Impact.TargetModifier();
        Spell.Impact.TargetModifier targetModifier3 = new Spell.Impact.TargetModifier();
        targetModifier2.execute = TriState.PASS;
        targetModifier3.execute = TriState.PASS;
        impacts[0].target_modifiers = List.of(targetModifier, targetModifier2, targetModifier3);
        spell.impacts = List.of(impacts);
        configureCooldown(spell, 0.0F, false, (String)null);
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry WintersExpanse() {
        Spell spell = activeSpellBase();
        spell.school = SpellSchools.FROST;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 360.0F;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "winters_expanse");
        String description = "Inflict Wintetide on all enemies in an area around you, slowing them by 20%.";
        String title = "Winter's Expanse";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 16.0F;
        spell.active.cast = createCast(0, 2.0F, "spell_engine:generic_frost_casting", "spellbladenext:one_handed_area_charge", SpellSchools.FROST);
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = new Spell.Impact();
        impacts[0].action = new Spell.Impact.Action();
        impacts[0].action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[0].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[0].action.status_effect.effect_id = SpellbladesAndSuch.DEATHCHILL.getIdAsString();
        impacts[0].action.status_effect.duration = 8.0F;
        impacts[0].particles = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 30.0F, 0.1F, 0.3F, 0.0F), new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F), (new ParticleBatch(MagicParticles.get(Shape.FROST, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F)).color(Color.FROST.toRGBA())};
        spell.release = new Spell.Release();
        spell.release.particles = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 30.0F, 0.1F, 0.3F, 0.0F), new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F), (new ParticleBatch(MagicParticles.get(Shape.FROST, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F)).color(Color.FROST.toRGBA())};
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FROST_RELEASE.id());
        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 2.0F, false, "runes:frost_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry mass_hypothermia() {
        Spell spell = activeSpellBase();
        spell.school = SpellSchools.FROST;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 360.0F;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "mass_hypothermia");
        String description = "All enemies in an area take Wintertide damage if they already have Wintertide on them.";
        String title = "Mass Hypothermia";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 16.0F;
        spell.active.cast = createCast(0, 2.0F, "spell_engine:generic_frost_casting", "spellbladenext:one_handed_area_charge", SpellSchools.FROST);
        Spell.Impact[] impacts = new Spell.Impact[]{createFrostImpact(6.0F, 0.0F)};
        impacts[0].particles = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 30.0F, 0.1F, 0.3F, 0.0F), new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F), (new ParticleBatch(MagicParticles.get(Shape.FROST, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F)).color(Color.FROST.toRGBA())};
        Spell.Impact.TargetModifier targetModifier = new Spell.Impact.TargetModifier();
        targetModifier.execute = TriState.ALLOW;
        Spell.TargetCondition targetCondition = new Spell.TargetCondition();
        targetCondition.entity_predicate_id = "spell_engine:has_effect";
        targetCondition.entity_predicate_param = SpellbladesAndSuch.DEATHCHILL.getIdAsString();
        targetModifier.conditions = List.of(targetCondition);
        impacts[0].target_modifiers = List.of(targetModifier);
        spell.release = new Spell.Release();
        spell.release.particles = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 30.0F, 0.1F, 0.3F, 0.0F), new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F), (new ParticleBatch(MagicParticles.get(Shape.FROST, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F)).color(Color.FROST.toRGBA())};
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FROST_RELEASE.id());
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 16.0F, false, "runes:frost_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry feather_lash() {
        Spell spell = projectileBase(ARCANE, Identifier.tryParse("spellbladenext:projectile/feather"), 1.6F, 0.0F);
        spell.school = SpellSchools.FIRE;
        spell.group = "primary";
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.sticky = true;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "feather_lash");
        String description = "Shoot a feather projectile, dealing {damage} fire damage to the first enemy hit.";
        String title = "Feather Lash";
        spell.deliver.projectile.projectile.divergence = 15.0F;
        spell.deliver.projectile.projectile.homing_after_absolute_distance = 8.0F;
        spell.deliver.projectile.projectile.homing_after_relative_distance = 0.4F;
        spell.deliver.projectile.projectile.homing_angle = 60.0F;
        spell.deliver.projectile.projectile.client_data.model.scale = 1.0F;
        spell.learn = new Spell.Learn();
        spell.tier = 1;
        spell.range = 64.0F;
        spell.active.cast = createCast(25, 10.0F, "spell_engine:generic_fire_casting", "spellbladenext:fullcastanimation", SpellSchools.FIRE);
        spell.active.cast.movement_speed = 0.6F;
        spell.active.cast.haste_affected = false;
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.flame.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 3.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 1.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 1.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch("minecraft:smoke", net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 0.1F, 360.0F)};
        spell.deliver.projectile.launch_properties.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id().toString());
        spell.deliver.projectile.projectile.client_data.travel_particles = particlebatch;
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createFireImpact(0.6F, 0.0F);
        spell.release = new Spell.Release();
        spell.release.animation = "spellbladenext:swish2";
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id().toString());
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 4.0F, true, "runes:fire_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry multilash() {
        Spell spell = projectileBase(ARCANE, Identifier.tryParse("spellbladenext:projectile/feather"), 1.6F, 0.0F);
        spell.school = SpellSchools.FIRE;
        spell.range = 64.0F;
        spell.tier = 3;
        spell.type = Type.PASSIVE;
        spell.passive = passiveSpellBase().passive;
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.school = SpellSchools.FIRE.id.toString();
        trigger.chance = 0.25F;
        spell.passive.triggers = List.of(trigger);
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.FROM_TRIGGER;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "featherstorm");
        String description = "Upon hitting an enemy with Feather Lash, you have a chance to shoot three more feather projectiles at the same enemy.";
        String title = "Featherstorm";
        spell.deliver.projectile.projectile.divergence = 15.0F;
        Spell.Delivery.ShootProjectile.DirectionOffset dOffset1 = new Spell.Delivery.ShootProjectile.DirectionOffset();
        Spell.Delivery.ShootProjectile.DirectionOffset dOffset2 = new Spell.Delivery.ShootProjectile.DirectionOffset();
        Spell.Delivery.ShootProjectile.DirectionOffset dOffset3 = new Spell.Delivery.ShootProjectile.DirectionOffset();
        dOffset1.yaw = -45.0F;
        dOffset2.yaw = 0.0F;
        dOffset3.yaw = 45.0F;
        spell.deliver.projectile.direction_offsets = new Spell.Delivery.ShootProjectile.DirectionOffset[]{dOffset1, dOffset2, dOffset3};
        spell.deliver.projectile.launch_properties = new Spell.LaunchProperties();
        spell.deliver.projectile.launch_properties.extra_launch_count = 2;
        spell.deliver.projectile.launch_properties.extra_launch_delay = 2;
        spell.deliver.projectile.projectile.homing_angle = 60.0F;
        spell.deliver.projectile.projectile.homing_after_absolute_distance = 8.0F;
        spell.deliver.projectile.projectile.homing_after_relative_distance = 0.4F;
        spell.deliver.projectile.projectile.client_data.model.scale = 1.0F;
        spell.learn = new Spell.Learn();
        spell.tier = 1;
        spell.range = 64.0F;
        spell.active = null;
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.flame.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 3.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 1.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 1.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch("minecraft:smoke", net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 0.1F, 360.0F)};
        spell.deliver.projectile.projectile.client_data.travel_particles = particlebatch;
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createFireImpact(0.6F, 0.0F);
        spell.release = new Spell.Release();
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id().toString());
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 1.0F, false, null);
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry exploding_feathers() {
        Spell spell = activeSpellBase();
        spell.school = SpellSchools.FIRE;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "exploding_feathers");
        String description = "Shoot a feather projectile, dealing {damage} fire damage to the first enemy hit.";
        String title = "Exploding Feathers";
        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range = 0.0F;
        spell.active.cast = createCast(0, 0.5F, "spell_engine:generic_fire_casting", "spellbladenext:flick", SpellSchools.FIRE);
        ParticleBatch[] var10000 = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.flame.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 3.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 1.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 1.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch("minecraft:firework", net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 0.1F, 360.0F)};
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.STASH_EFFECT;
        spell.deliver.stash_effect = new Spell.Delivery.StashEffect();
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.chance = 1.0F;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.id = "spellbladenext:feather_lash";
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        spell.deliver.stash_effect.triggers = List.of(trigger);
        spell.deliver.stash_effect.amplifier = 7;
        spell.deliver.stash_effect.duration = 30.0F;
        spell.deliver.stash_effect.id = SpellbladesAndSuch.FEATHER.getIdAsString();
        Spell.Impact impact = createFireImpact(0.4F, 1.0F);
        Spell.Impact[] impacts = new Spell.Impact[]{impact};
        spell.area_impact = new Spell.AreaImpact();
        spell.area_impact.area = new Spell.Target.Area();
        spell.area_impact.radius = 4.0F;
        spell.release = new Spell.Release();
        spell.area_impact.particles = new ParticleBatch[]{new ParticleBatch("spell_engine:fire_explosion", net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 1.0F, 360.0F)};
        spell.release.animation = "spellbladenext:flick2";
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id().toString());
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 4.0F, false, "runes:fire_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry healing_feathers() {
        Spell spell = activeSpellBase();
        spell.school = SpellSchools.FIRE;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "healing_feathers");
        String description = "Shoot a feather projectile, dealing {damage} fire damage to the first enemy hit.";
        String title = "Feather Lash";
        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 4.0F;
        spell.active.cast = createCast(0, 0.5F, "spell_engine:generic_fire_casting", "spellbladenext:flick", SpellSchools.FIRE);
        ParticleBatch[] var10000 = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.flame.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 3.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 1.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 1.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch("minecraft:firework", net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 0.1F, 360.0F)};
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.STASH_EFFECT;
        spell.deliver.stash_effect = new Spell.Delivery.StashEffect();
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.chance = 1.0F;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.id = "spellbladenext:feather_lash";
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        spell.deliver.stash_effect.triggers = List.of(trigger);
        spell.deliver.stash_effect.amplifier = 7;
        spell.deliver.stash_effect.duration = 30.0F;
        spell.deliver.stash_effect.id = SpellbladesAndSuch.FEATHERHEAL.getIdAsString();
        Spell.Impact impact = createHealingImpact(1.2F, 0.0F);
        impact.action.heal = new Spell.Impact.Action.Heal();
        impact.action.apply_to_caster = true;
        impact.action.heal.spell_power_coefficient = 1.2F;
        Spell.Impact[] impacts = new Spell.Impact[]{impact};
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.include_caster = true;
        spell.release = new Spell.Release();
        spell.release.animation = "spellbladenext:flick2";
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id().toString());
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 4.0F, false, "runes:fire_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry amethyst_barrage() {
        Spell spell = meteor_base(ARCANE, Identifier.tryParse("minecraft:amethyst_block"), 1.0F, 0.0F);
        spell.school = ARCANE;
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.METEOR;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.sticky = true;
        spell.deliver.meteor.launch_properties = new Spell.LaunchProperties();
        spell.deliver.meteor.launch_properties.extra_launch_delay = 2;
        spell.deliver.meteor.launch_properties.extra_launch_count = 7;
        spell.deliver.meteor.projectile.divergence = 30.0F;
        spell.deliver.meteor.projectile.client_data.model.scale = 0.0F;
        spell.group = "primary";
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "echofall");
        String description = "Send down a barrage of sound waves, each dealing {damage} damage.";
        String title = "Echofall";
        spell.learn = new Spell.Learn();
        spell.tier = 1;
        spell.range = 32.0F;
        spell.active.cast = createCast(0, 0.6F, "spell_engine:generic_arcane_casting", "spell_engine:one_handed_sky_charge", ARCANE);
        ParticleBatch[] particlebatch = new ParticleBatch[]{(new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 40.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.ARCANE, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 40.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA())};
        ParticleBatch[] particlebatch2 = new ParticleBatch[]{(new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 40.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.ARCANE, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 40.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 300.0F, 0.35F, 0.45F, 360.0F)).color(Color.ARCANE.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.ARCANE, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 150.0F, 0.35F, 0.45F, 360.0F)).color(Color.ARCANE.toRGBA())};
        spell.area_impact = new Spell.AreaImpact();
        spell.area_impact.area = new Spell.Target.Area();
        spell.area_impact.radius = 4.0F;
        spell.area_impact.particles = particlebatch2;
        spell.deliver.meteor.projectile.client_data.travel_particles = particlebatch;
        spell.area_impact.sound = new Sound("minecraft:entity.wind_charge.wind_burst");
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createArcaneImpact(0.6F, 0.5F);
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 4.0F, false, "runes:arcane_stone");
        spell.release = new Spell.Release();
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_ARCANE_RELEASE.id());
        spell.release.animation = "spellbladenext:one_handed_area_release";
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry resonance() {
        Spell spell = activeSpellBase();
        spell.school = ARCANE;
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "resonance");
        String description = "On spell hit, apply stacks of Echoes based on your arcane power for 4 seconds.  When Echoes expires, enemies in an area take 1.6 arcane damage per echo stack.";
        String title = "Echoes";
        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 16.0F;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.CASTER;
        spell.active.cast = createCast(0, 2.0F, "spell_engine:generic_arcane_casting", "spellbladenext:one_handed_area_charge", ARCANE);
        ParticleBatch[] var10000 = new ParticleBatch[]{new ParticleBatch("spell_engine:magic_arcane_spark_float", net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA())};
        var10000 = new ParticleBatch[]{(new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 100.0F, 0.35F, 0.75F, 360.0F)).color(Color.ARCANE.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 100.0F, 0.35F, 0.7F, 360.0F)).color(Color.ARCANE.toRGBA())};
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = new Spell.Impact();
        impacts[0].school = ARCANE;
        impacts[0].action = new Spell.Impact.Action();
        impacts[0].action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[0].action.apply_to_caster = true;
        impacts[0].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[0].action.status_effect.show_particles = false;
        impacts[0].action.status_effect.refresh_duration = false;
        impacts[0].action.status_effect.duration = 8.0F;
        impacts[0].action.status_effect.effect_id = SpellbladesAndSuch.RESONATING.getIdAsString();
        impacts[0].action.status_effect.apply_mode = ApplyMode.SET;
        impacts[0].action.status_effect.amplifier = 0;
        spell.impacts = List.of(impacts[0]);
        spell.release = new Spell.Release();
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_ARCANE_RELEASE.id());
        spell.release.animation = "spellbladenext:one_handed_area_release";
        configureCooldown(spell, 30.0F, false, "runes:arcane_stone");
        spell.cost.cooldown.haste_affected = false;
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry echo() {
        Spell spell = passiveSpellBase();
        spell.school = ARCANE;
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.deliver.delay = 40;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "echoes");
        String description = "On spell hit, apply stacks of Echoes based on your arcane power for 4 seconds.  When Echoes expires, enemies in an area take 1.6 arcane damage per echo stack.";
        String title = "Echoes";
        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range = 16.0F;
        spell.passive = passiveSpellBase().passive;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.FROM_TRIGGER;
        Spell.Trigger trigger = new Spell.Trigger();
        trigger.chance = 1.0F;
        trigger.type = net.spell_engine.api.spell.Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        trigger.impact = new Spell.Trigger.ImpactCondition();
        trigger.impact.impact_type = "DAMAGE";
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.id = "spellbladenext:reverberation_brand";
        Spell.Trigger trigger2 = new Spell.Trigger();
        trigger2.chance = 1.0F;
        trigger2.type = net.spell_engine.api.spell.Spell.Trigger.Type.SPELL_IMPACT_SPECIFIC;
        trigger2.impact = new Spell.Trigger.ImpactCondition();
        trigger2.impact.impact_type = "DAMAGE";
        trigger2.spell = new Spell.Trigger.SpellCondition();
        trigger2.spell.id = "spellbladenext:echofall";
        spell.passive.triggers = List.of(trigger, trigger2);
        ParticleBatch[] var10000 = new ParticleBatch[]{new ParticleBatch("spell_engine:magic_arcane_spark_float", net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA())};
        var10000 = new ParticleBatch[]{(new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 100.0F, 0.35F, 0.75F, 360.0F)).color(Color.ARCANE.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.CIRCLE, Origin.CENTER, (ParticleBatch.Rotation)null, 100.0F, 0.35F, 0.7F, 360.0F)).color(Color.ARCANE.toRGBA())};
        Spell.Impact[] impacts = new Spell.Impact[1];
        impacts[0] = createArcaneImpact(0.2F, 0.0F);
        spell.impacts = List.of(impacts[0]);
        spell.release = new Spell.Release();
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_ARCANE_RELEASE.id());
        configureCooldown(spell, 0.0F, false, (String)null);
        spell.cost.durability = 0;
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry encore() {
        Spell spell = activeSpellBase();
        spell.school = ARCANE;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.CASTER;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "encore");
        String description = "Reset all spell cooldowns.";
        String title = "Encore";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 0.0F;
        spell.active.cast = createCast(0, 0.5F, "spell_engine:generic_arcane_casting", "spellbladenext:one_handed_area_charge", ARCANE);
        Spell.Impact[] impacts = new Spell.Impact[2];
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        impacts[0] = createArcaneImpact(0.0F, 0.0F);
        impacts[0].action = new Spell.Impact.Action();
        impacts[0].action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.COOLDOWN;
        impacts[0].action.apply_to_caster = true;
        impacts[0].action.cooldown = new Spell.Impact.Action.Cooldown();
        impacts[0].action.cooldown.actives = new Spell.Impact.Action.Cooldown.Modify();
        impacts[0].action.cooldown.actives.school = "spell_engine:arcane";
        impacts[0].action.cooldown.actives.duration_add = -9999.0F;
        impacts[0].particles = new ParticleBatch[]{(new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA())};
        spell.release = new Spell.Release();
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_ARCANE_RELEASE.id());
        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 40.0F, false, "runes:arcane_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }

    public static Entry reverb_brand() {
        Spell spell = activeSpellBase();
        spell.school = ARCANE;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.sticky = true;
        spell.target.aim.required = true;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "reverberation_brand");
        String description = "Blast an enemy for {damage} frost damage and inflict Wintertide on them, slowing them by 20%.";
        String title = "Wintertide";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range = 8.0F;
        spell.active.cast = createCast(0, 2.0F, "spell_engine:generic_arcane_casting", "spellbladenext:one_handed_area_charge", ARCANE);
        Spell.Impact[] impacts = new Spell.Impact[2];
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = id.toString();
        impacts[0] = createArcaneImpact(0.12F, 0.0F);
        impacts[0].particles = new ParticleBatch[]{(new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA()), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA())};
        spell.release = new Spell.Release();
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_ARCANE_RELEASE.id());
        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 35.0F, false, "runes:arcane_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }
    public static Entry grav() {
        Spell spell = activeSpellBase();
        spell.school = ARCANE;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.required = true;
        spell.target.aim.sticky = true;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "magnify");
        String description = "Blast an enemy for {damage} frost damage and inflict Wintertide on them, slowing them by 20%.";
        String title = "Wintertide";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.learn = new Spell.Learn();
        spell.tier = 1;
        spell.range = 8.0F;
        spell.active.cast = createCast(0, 0.5F, "spell_engine:generic_arcane_casting", "spellbladenext:one_handed_area_charge", ARCANE);
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createArcaneImpact(0.4F, 0.0F);
        impacts[1] = new Spell.Impact();
        impacts[1].action = new Spell.Impact.Action();
        impacts[1].action.type = Spell.Impact.Action.Type.CUSTOM;
        impacts[1].action.custom = new Spell.Impact.Action.Custom();
        impacts[1].action.custom.intent = Intent.HARMFUL;

        impacts[1].action.custom.handler = "spellbladenext:double";

        spell.release = new Spell.Release();
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch("spell_engine:magic_arcane_spark_float", net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA())};
        impacts[1].particles = particlebatch;
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_ARCANE_RELEASE.id());
        spell.impacts = List.of(impacts[0], impacts[1]);
        configureCooldown(spell, 8F, false, "runes:arcane_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }
    public static Entry collapse() {
        Spell spell = activeSpellBase();
        spell.school = ARCANE;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.required = true;
        spell.target.aim.sticky = true;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "collapse");
        String description = "Blast an enemy for {damage} frost damage and inflict Wintertide on them, slowing them by 20%.";
        String title = "Wintertide";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.learn = new Spell.Learn();
        spell.tier = 1;
        spell.range = 8.0F;
        spell.active.cast = createCast(0, 0.5F, "spell_engine:generic_arcane_casting", "spellbladenext:one_handed_area_charge", ARCANE);
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createArcaneImpact(0.4F, 0.0F);
        impacts[1] = new Spell.Impact();
        impacts[1].action = new Spell.Impact.Action();
        impacts[1].action.type = Spell.Impact.Action.Type.CUSTOM;
        impacts[1].action.custom = new Spell.Impact.Action.Custom();
        impacts[1].action.custom.intent = Intent.HARMFUL;

        impacts[1].action.custom.handler = "spellbladenext:applyadd";

        impacts[1].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[1].action.status_effect.effect_id = SpellbladesAndSuch.COLLAPSE.getIdAsString();
        impacts[1].action.status_effect.duration = 2.0F;
        impacts[1].action.status_effect.amplifier_power_multiplier = 0.4F;
        impacts[1].action.status_effect.apply_mode = ApplyMode.ADD;
        impacts[1].action.status_effect.amplifier = 1;
        impacts[1].action.status_effect.amplifier_cap = 256;

        spell.release = new Spell.Release();
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch("spell_engine:magic_arcane_spark_float", net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F), (new ParticleBatch(MagicParticles.get(Shape.SPARK, Motion.FLOAT).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 20.0F, 0.05F, 0.1F, 360.0F)).color(Color.ARCANE.toRGBA())};
        impacts[1].particles = particlebatch;
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_ARCANE_RELEASE.id());
        spell.impacts = List.of(impacts[0], impacts[1]);
        configureCooldown(spell, 0.4F, false, "runes:arcane_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }
    public static Entry shatter() {
        Spell spell = activeSpellBase();
        spell.school = SpellSchools.FROST;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.required = true;
        spell.target.aim.sticky = true;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "shatter");
        String description = "Blast an enemy for {damage} frost damage and inflict Wintertide on them, slowing them by 20%.";
        String title = "Wintertide";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.learn = new Spell.Learn();
        spell.tier = 1;
        spell.range = 8.0F;
        spell.active.cast = createCast(0, 0.5F, "spell_engine:generic_frost_casting", "spellbladenext:one_handed_area_charge", SpellSchools.FROST);
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createFrostImpact(2F, 0.0F);
        impacts[1] = new Spell.Impact();
        impacts[1].action = new Spell.Impact.Action();
        impacts[1].action.type = net.spell_engine.api.spell.Spell.Impact.Action.Type.CUSTOM;
        impacts[1].action.custom = new Spell.Impact.Action.Custom();
        impacts[1].action.custom.intent = Intent.HARMFUL;

        impacts[1].action.custom.handler = "spellbladenext:shatter";
        spell.release = new Spell.Release();
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 30.0F, 0.1F, 0.3F, 0.0F), new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F), (new ParticleBatch(MagicParticles.get(Shape.FROST, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F)).color(Color.FROST.toRGBA())};
        impacts[1].particles = particlebatch;
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FROST_RELEASE.id());
        spell.impacts = List.of(impacts[0], impacts[1]);
        configureCooldown(spell, 0.4F, false, "runes:frost_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }
    public static Entry rime() {
        Spell spell = activeSpellBase();
        spell.school = SpellSchools.FROST;
        spell.target = new Spell.Target();
        spell.target.type = net.spell_engine.api.spell.Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.required = true;
        spell.target.aim.sticky = true;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "deathchill");
        String description = "Blast an enemy for {damage} frost damage and inflict Wintertide on them, slowing them by 20%.";
        String title = "Wintertide";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = net.spell_engine.api.spell.Spell.Delivery.Type.DIRECT;
        spell.learn = new Spell.Learn();
        spell.tier = 1;
        spell.range = 8.0F;
        spell.active.cast = createCast(0, 0.5F, "spell_engine:generic_frost_casting", "spellbladenext:one_handed_area_charge", SpellSchools.FROST);
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createFrostImpact(0.4F, 0.0F);
        impacts[1] = new Spell.Impact();
        impacts[1].action = new Spell.Impact.Action();
        impacts[1].action.type = Spell.Impact.Action.Type.CUSTOM;
        impacts[1].action.custom = new Spell.Impact.Action.Custom();
        impacts[1].action.custom.intent = Intent.HARMFUL;

        impacts[1].action.custom.handler = "spellbladenext:applyadd";
        impacts[1].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[1].action.status_effect.effect_id = SpellbladesAndSuch.DEATHCHILL.getIdAsString();
        impacts[1].action.status_effect.duration = 8.0F;
        impacts[1].action.status_effect.amplifier_cap = 2;
        impacts[1].action.status_effect.amplifier = 1;

        impacts[1].action.status_effect.apply_mode = ApplyMode.ADD;

        spell.release = new Spell.Release();
        ParticleBatch[] particlebatch = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.snowflake.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 30.0F, 0.1F, 0.3F, 0.0F), new ParticleBatch(SpellEngineParticles.frost_shard.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F), (new ParticleBatch(MagicParticles.get(Shape.FROST, Motion.ASCEND).id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.PIPE, Origin.FEET, (ParticleBatch.Rotation)null, 10.0F, 0.1F, 0.3F, 0.0F)).color(Color.FROST.toRGBA())};
        impacts[1].particles = particlebatch;
        spell.release.particles = particlebatch;
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FROST_RELEASE.id());
        spell.impacts = List.of(impacts[0], impacts[1]);
        configureCooldown(spell, 0.4F, false, "runes:frost_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }
    public static Entry ignite() {
        Spell spell = activeSpellBase();
        spell.school = SpellSchools.FIRE;
        Identifier id = Identifier.of(SpellbladesAndSuch.MOD_ID, "phoenix_down");
        String description = "Shoot a feather projectile, dealing {damage} fire damage to the first enemy hit.";
        String title = "Exploding Feathers";
        spell.learn = new Spell.Learn();
        spell.tier = 2;
        spell.range = 8F;
        spell.active.cast = createCast(0, 0.4F, "spell_engine:generic_fire_casting", "spellbladenext:flick", SpellSchools.FIRE);
        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        ParticleBatch[] var10000 = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.flame.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 3.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 1.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 1.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch("minecraft:firework", net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 0.1F, 360.0F)};
        spell.active.cast.particles = var10000;
        Spell.Impact[] impacts = new Spell.Impact[2];
        impacts[0] = createFireImpact(0.4F, 0.0F);
        impacts[1] = new Spell.Impact();
        impacts[1].action = new Spell.Impact.Action();
        impacts[1].action.apply_to_caster = true;

        impacts[1].action.type = Spell.Impact.Action.Type.CUSTOM;
        impacts[1].action.custom = new Spell.Impact.Action.Custom();
        impacts[1].action.custom.intent = Intent.HARMFUL;

        impacts[1].action.custom.handler = "spellbladenext:applyadd";
        impacts[1].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[1].action.status_effect.effect_id = SpellbladesAndSuch.FEATHER.getIdAsString();
        impacts[1].action.status_effect.duration = 8.0F;
        impacts[1].action.status_effect.amplifier_cap = 8;
        impacts[1].action.status_effect.apply_mode = ApplyMode.ADD;
        impacts[1].action.status_effect.amplifier = 1;


        spell.release = new Spell.Release();
        spell.release.animation = "spellbladenext:flick2";
        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id().toString());
        spell.impacts = List.of(impacts[0],impacts[1]);
        configureCooldown(spell, 0.4F, false, "runes:fire_stone");
        return new Entry(id, spell, title, description, (SpellTooltip.DescriptionMutator)null);
    }
    public static void registerHandlers() {
        SpellEvents.SPELL_CAST.register((SpellEvents.SpellCastEvent)(args) -> {
            if (args.spell().equals(SpellRegistry.from(args.caster().getWorld()).getEntry(phoenix_dive.id).get())) {
                Spell.AreaImpact impact = new Spell.AreaImpact();
                impact.area = new Spell.Target.Area();
                impact.radius = 8.0F;
                List<Spell.Impact> list = new ArrayList();
                list.add(createFireImpact(1.2F, 1.0F));
                SpellHelper.lookupAndPerformAreaImpact(impact, (RegistryEntry.Reference<Spell>)SpellRegistry.from(args.caster().getWorld()).getEntry(phoenix_dive.id).get(), args.caster(), args.caster(), args.caster(), list, (new SpellHelper.ImpactContext()).power(SpellPower.getSpellPower(SpellSchools.FIRE, args.caster())).position(args.caster().getPos()), false);
            }

        });
    }
    private static Entry greater_fireball() {
        var spell = activeSpellBase();
        spell.school = FIRE;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();
        spell.target.aim.required = false;
        spell.target.aim.sticky = false;

        var id = Identifier.of(SpellbladesAndSuch.MOD_ID, "combust");
        var description = "Blast all enemies in a line with arcane energies, dealing {damage} arcane damage.";
        var title = "Greater Fireball";
        spell.deliver = new Spell.Delivery();
        spell.deliver.type = Spell.Delivery.Type.DIRECT;




        spell.learn = new Spell.Learn();
        spell.tier = 3;
        spell.range = 8;
        spell.active.cast = createCast(0,1F,"spell_engine:generic_fire_casting","spellbladenext:one_handed_projectile_charge", FIRE);
        ParticleBatch[] var10000 = new ParticleBatch[]{new ParticleBatch(SpellEngineParticles.flame.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 3.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 1.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 1.0F, 0.05F, 0.1F, 360.0F), new ParticleBatch("minecraft:firework", net.spell_engine.api.spell.fx.ParticleBatch.Shape.SPHERE, Origin.CENTER, (ParticleBatch.Rotation)null, 2.0F, 0.05F, 0.1F, 360.0F)};
        spell.active.cast.particles = var10000;
        Spell.Impact[] impacts = new Spell.Impact[2];

        impacts[0] = createFireImpact(1.8F,2F);

        spell.area_impact = new Spell.AreaImpact();
        spell.area_impact.area = new Spell.Target.Area();
        spell.area_impact.area.angle_degrees = 360F;
        spell.area_impact.radius = 8;
        spell.area_impact.sound  = new Sound("entity.generic.explode");
        spell.area_impact.particles  = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.fire_explosion.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null, 2, 0.3F, 0.5F, 0),

                new ParticleBatch(SpellEngineParticles.flame.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null, 450, 0.7F, .8F, 0),
                new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null, 450, 0.7F, 0.8F, 0),
                new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null, 450, 0.7F, 0.8F, 0),
                new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null, 450, 0.4F, 0.4F, 0),
                new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER, null, 450, 0.4F, 0.4F, 0)};
        spell.release = new Spell.Release();

        spell.release.sound = new Sound(SpellEngineSounds.GENERIC_FIRE_RELEASE.id());
        spell.release.animation = "spellbladenext:one_handed_projectile_release";
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.flame.id().toString(), ParticleBatch.Shape.CONE, ParticleBatch.Origin.LAUNCH_POINT, ParticleBatch.Rotation.LOOK, 200, 2, 4, 20),
                new ParticleBatch(SpellEngineParticles.flame_medium_a.id().toString(), ParticleBatch.Shape.CONE, ParticleBatch.Origin.LAUNCH_POINT, ParticleBatch.Rotation.LOOK, 100, 1, 2, 40),
                new ParticleBatch(SpellEngineParticles.flame_medium_b.id().toString(), ParticleBatch.Shape.CONE, ParticleBatch.Origin.LAUNCH_POINT, ParticleBatch.Rotation.LOOK, 100, 0.5F, 1, 75)


        };
        spell.impacts = List.of(impacts[0]);
        configureCooldown(spell, 1F, false, null);
        spell.cost.effect_id = SpellbladesAndSuch.FEATHER.getIdAsString();
        return new Entry(id, spell, title, description, null);

    }

    public static record Entry(Identifier id, Spell spell, String title, String description, @Nullable SpellTooltip.DescriptionMutator mutator) {
    }
}
