package com.cleannrooster.spellblades.Spells.compat;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.cleannrooster.spellblades.Spells.SpellbladeSpells;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.elemental_wizards_rpg.spell.ElementalWizardSpells;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.item.SpellBooks;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.event.SpellEvents;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.fx.SpellEngineSounds;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.target.EntityRelations;
import net.spell_engine.internals.target.SpellTarget;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.KEY;
import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;
import static com.cleannrooster.spellblades.Spells.SpellbladeSpells.*;

public class ElementalSpells {
    public record Entry(Identifier id, Spell spell, String title, String description,
                        @Nullable SpellTooltip.DescriptionMutator mutator) {
    }

    public static final List<SpellbladeSpells.Entry> entries = new ArrayList<>();

    public static SpellbladeSpells.Entry add(SpellbladeSpells.Entry entry) {
        entries.add(entry);

        return entry;
    }
    public static SpellbladeSpells.Entry addIfInstalled(SpellbladeSpells.Entry entry, String modid) {
        if(FabricLoader.getInstance().isModLoaded(modid)) {
            entries.add(entry);
        }

        return entry;
    }
    private static Spell.Impact createAirImpact(float coeff, float knockback) {
        var impact = createImpact(Spell.Impact.Action.Type.DAMAGE,coeff, knockback);
        impact.school = MoreSpellSchools.AIR;
        ParticleBatch[] hitParticles = new ParticleBatch[]{
                new ParticleBatch("more_rpg_classes:small_gust", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, ParticleBatch.Rotation.LOOK, 20, 0.2f, 0.7F, 360),
                new ParticleBatch("minecraft:smoke", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, ParticleBatch.Rotation.LOOK, 20, 0.2f, 0.7F, 360)

        };
        Spell.TargetCondition targetCondition = new Spell.TargetCondition();


        impact.particles = hitParticles;

        var sound = new Sound("more_rpg_classes:air_magic_impact2");
        impact.sound = sound;
        return impact;
    }
    public static SpellbladeSpells.Entry the_wind_rises() {
        var spell = activeSpellBase();
        spell.school = MoreSpellSchools.AIR;

        var id = Identifier.of(MOD_ID, "the_wind_rises");
        var description = "Attack with a flurry of frost blade projectiles, dealing {damage} Frost damage per second.";
        var title = "Amethyst Slash";

        spell.learn = new Spell.Learn();
        spell.tier =3;
        spell.range = 0;
        spell.range_mechanic = Spell.RangeMechanic.MELEE;
        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        spell.deliver = createDelivery(Spell.Delivery.Type.DIRECT);
        spell.target.area = new Spell.Target.Area();

        spell.target.area.include_caster = true;
        spell.release = new Spell.Release();
        spell.release.animation = "spellbladenext:rising_slash";
        spell.active.cast = createCast(0,0.25F,"spell_engine:generic_frost_casting","spellbladenext:upward_start",MoreSpellSchools.AIR);

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch("more_rpg_classes:small_gust", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360),
                new ParticleBatch("more_rpg_classes:small_gust"
                        , ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360)

        };

        Spell.Impact[] impacts = new Spell.Impact[4];
        impacts[0] = createAirImpact(1.6F,0F);
        impacts[0].particles = particlebatch;

        impacts[1] = createPhysicalimpact(1.2F,0F);
        impacts[2] = new Spell.Impact();
        impacts[2].action = new Spell.Impact.Action();
        impacts[2].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[2].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[2].action.status_effect.effect_id = StatusEffects.LEVITATION.getIdAsString();
        impacts[2].action.status_effect.amplifier = 39;
        impacts[2].action.status_effect.duration = 0.5F;
        impacts[2].action.apply_to_caster = true;
        impacts[2].particles = particlebatch;
        impacts[3] = new Spell.Impact();
        impacts[3].action = new Spell.Impact.Action();
        impacts[3].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[3].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[3].action.status_effect.effect_id = StatusEffects.LEVITATION.getIdAsString();
        impacts[3].action.status_effect.amplifier = 39;
        impacts[3].action.status_effect.duration = 0.5F;
        impacts[3].particles = particlebatch;
        var sound = new Sound("more_rpg_classes:air_magic_impact2");
        impacts[2].sound = sound;
        impacts[3].sound = sound;


        spell.impacts = List.of(impacts[1],impacts[0],impacts[2],impacts[3]);

        configureCooldown(spell, 4, true, "more_rpg_classes:air_stone");

        return new SpellbladeSpells.Entry(id, spell, title, description, null);
    }
    public static SpellbladeSpells.Entry the_heavens_descend() {
        var spell = activeSpellBase();
        spell.school = MoreSpellSchools.AIR;

        var id = Identifier.of(MOD_ID, "the_heavens_descend");
        var description = "Attack with a flurry of frost blade projectiles, dealing {damage} Frost damage per second.";
        var title = "Amethyst Slash";

        spell.learn = new Spell.Learn();
        spell.tier = 4;
        spell.range = 0;
        spell.range_mechanic = Spell.RangeMechanic.MELEE;
        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        spell.deliver = createDelivery(Spell.Delivery.Type.DIRECT);
        spell.target.area = new Spell.Target.Area();
        spell.target.area.angle_degrees = 120;

        spell.active.cast = createCast(0,0.25F,"spell_engine:generic_frost_casting","spellbladenext:down_slash",MoreSpellSchools.AIR);

        ParticleBatch[] particlebatch = new ParticleBatch[]{
                new ParticleBatch("more_rpg_classes:small_gust", ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360),
                new ParticleBatch("more_rpg_classes:small_gust"
                        , ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER, null,2,0.05f,0.1F,360)

        };

        Spell.Impact[] impacts = new Spell.Impact[4];
        impacts[0] = createAirImpact(1.6F,0F);
        impacts[0].particles = particlebatch;
        impacts[1] = createPhysicalimpact(1.2F,0F);
        impacts[2] = new Spell.Impact();
        impacts[2].action = new Spell.Impact.Action();
        impacts[2].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[2].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[2].action.status_effect.effect_id = SpellbladesAndSuch.CRASHING.getIdAsString();
        impacts[2].action.status_effect.amplifier = 0;
        impacts[2].action.status_effect.duration = 4F;
        impacts[3] = new Spell.Impact();
        impacts[3].action = new Spell.Impact.Action();
        impacts[3].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[3].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[3].action.status_effect.effect_id = StatusEffects.SLOW_FALLING.getIdAsString();
        impacts[3].action.status_effect.amplifier = 0;
        impacts[3].action.status_effect.duration = 6F;
        impacts[3].action.apply_to_caster = true;
        spell.impacts = List.of(impacts[1],impacts[0],impacts[2],impacts[3]);

        configureCooldown(spell, 4, true, "more_rpg_classes:air_stone");

        return new SpellbladeSpells.Entry(id, spell, title, description, null);
    }
    public static SpellbladeSpells.Entry windOverdrive() {
        var id = Identifier.of(MOD_ID, "wind_burst");
        var description = "On melee hit: 100% chance to deal fire damage in an cone in front of you.";
        var title = "Wind Burst";

        var spell = passiveSpellBase();
        spell.group = "primary";

        spell.tier = 1;
        spell.range = 0;
        spell.range_mechanic = Spell.RangeMechanic.MELEE;
        spell.learn = new Spell.Learn();
        spell.school = MoreSpellSchools.AIR;

        var trigger = new Spell.Trigger();
        trigger.chance = 1;
        trigger.type = Spell.Trigger.Type.MELEE_IMPACT;
        trigger.spell = new Spell.Trigger.SpellCondition();
        trigger.spell.archetype = SpellSchool.Archetype.MAGIC;

        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.include_caster = true;

        spell.passive.triggers = List.of(trigger);

        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.release.sound = new Sound("spell_engine:generic_frost_release");

        Spell.Impact[] impacts = new Spell.Impact[4];
        impacts[0] = createAirImpact(0.2F,0F);
        impacts[0].action.min_power = 1;

        impacts[1] = new Spell.Impact();
        impacts[1].action = new Spell.Impact.Action();
        impacts[1].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[1].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[1].action.status_effect.effect_id = StatusEffects.LEVITATION.getIdAsString();
        impacts[1].action.status_effect.amplifier = 19;
        impacts[1].action.status_effect.duration = 0.25F;
        impacts[2] = new Spell.Impact();

        impacts[2].action = new Spell.Impact.Action();
        impacts[2].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[2].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[2].action.status_effect.effect_id = StatusEffects.SLOW_FALLING.getIdAsString();
        impacts[2].action.status_effect.amplifier = 0;
        impacts[2].action.status_effect.duration = 4F;
        impacts[3] = new Spell.Impact();
        impacts[3].action = new Spell.Impact.Action();
        impacts[3].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[3].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[3].action.status_effect.effect_id = StatusEffects.LEVITATION.getIdAsString();
        impacts[3].action.status_effect.amplifier = 19;
        impacts[3].action.status_effect.duration = 0.25F;
        impacts[3].action.apply_to_caster = true;
        spell.impacts = List.of(impacts);

        configureCooldown(spell, 0,false, null);
        return new SpellbladeSpells.Entry(id, spell, title, description, null);
    }
    public static SpellbladeSpells.Entry clouds() {
        var id = Identifier.of(MOD_ID, "the_clouds_gather");
        var description = "On melee hit: 100% chance to deal fire damage in an cone in front of you.";
        var title = "Wind Burst";

        var spell = activeSpellBase();

        spell.tier = 2;
        spell.range = 0;
        spell.range_mechanic = Spell.RangeMechanic.MELEE;
        spell.learn = new Spell.Learn();
        spell.school = MoreSpellSchools.AIR;


        spell.target = new Spell.Target();
        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.include_caster = true;

        spell.active.cast = createCast(1,8, SpellEngineSounds.GENERIC_FROST_CASTING.id().toString(),"spellbladenext:levitate",MoreSpellSchools.AIR);
        spell.release.animation = "spellbladenext:one_handed_area_release";
        spell.release.sound = new Sound("spell_engine:generic_frost_release");

        Spell.Impact[] impacts = new Spell.Impact[3];

        impacts[0] = new Spell.Impact();
        impacts[0].action = new Spell.Impact.Action();
        impacts[0].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[0].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[0].action.status_effect.effect_id = StatusEffects.LEVITATION.getIdAsString();
        impacts[0].action.status_effect.amplifier = 0;
        impacts[0].action.status_effect.duration = 0.25F;


        impacts[1] = new Spell.Impact();
        impacts[1].action = new Spell.Impact.Action();
        impacts[1].action.type = Spell.Impact.Action.Type.STATUS_EFFECT;
        impacts[1].action.status_effect = new Spell.Impact.Action.StatusEffect();
        impacts[1].action.status_effect.effect_id = StatusEffects.LEVITATION.getIdAsString();
        impacts[1].action.status_effect.amplifier = 0;
        impacts[1].action.status_effect.duration = 0.25F;
        impacts[1].action.apply_to_caster = true;
        spell.impacts = List.of(impacts[0],impacts[1]);

        configureCooldown(spell, 8,true, null);
        return new SpellbladeSpells.Entry(id, spell, title, description, null);
    }
    public static SpellbladeSpells.Entry the_wind_rises = addIfInstalled(ElementalSpells.the_wind_rises(),"elemental_wizards_rpg");
    public static SpellbladeSpells.Entry the_heavens_descend = addIfInstalled(ElementalSpells.the_heavens_descend(),"elemental_wizards_rpg");
    public static SpellbladeSpells.Entry wind_burst = addIfInstalled(ElementalSpells.windOverdrive(),"elemental_wizards_rpg");
    public static SpellbladeSpells.Entry clouds = addIfInstalled(ElementalSpells.clouds(),"elemental_wizards_rpg");

    public static void registerHandlers(){
        SpellBooks.createAndRegister(Identifier.of(MOD_ID,"wind_battlemage"),KEY);

        SpellEvents.SPELL_CAST.register((args)-> {
            if(args.spell().equals(SpellRegistry.from(args.caster().getWorld()).getEntry(the_heavens_descend().id()).get())){
                for(Entity entity: args.targets()){
                    if(EntityRelations.actionAllowed(SpellTarget.FocusMode.AREA, SpellTarget.Intent.HARMFUL,args.caster(),entity) && entity instanceof LivingEntity living && living.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) < 1.0F){
                        entity.setVelocity(0,-1.0F,0);
                    }
                }
            }
        });
    }
}
