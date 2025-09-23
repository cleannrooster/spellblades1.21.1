package com.cleannrooster.spellblades.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.internals.casting.SpellCasterEntity;
import net.spell_engine.internals.target.EntityRelations;
import net.spell_engine.internals.target.SpellTarget;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;

public class Bulwark extends CustomEffect{
    public Bulwark(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onEntityDamage(LivingEntity entity, int amplifier, DamageSource source, float amount) {
        super.onEntityDamage(entity, amplifier, source, amount);

    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {

        int power = (int) ((int) SpellPower.getSpellPower(SpellSchools.HEALING, entity).baseValue()*1.25-1);
        if(entity instanceof PlayerEntity player && player.getWorld() instanceof ServerWorld serverWorld) {
            if(!((SpellCasterEntity) player).getCooldownManager().isCoolingDown(Identifier.of(MOD_ID,"circle_of_healing"))) {
                ArrayList<Entity> targets = (ArrayList<Entity>) TargetHelper.targetsFromArea(
                        player, player.getPos(), SpellRegistry.from(entity.getWorld()).get(Identifier.of(MOD_ID, "circle_of_healing")).range, SpellRegistry.from(entity.getWorld()).get(Identifier.of(MOD_ID, "circle_of_healing")).target.area,
                        target -> EntityRelations.actionAllowed(SpellTarget.FocusMode.AREA, SpellTarget.Intent.HELPFUL, player, target));
                targets.add(player);

                SpellHelper.performSpell(entity.getWorld(), player, SpellRegistry.from(entity.getWorld()).getEntry(Identifier.of(MOD_ID, "circle_of_healing")).get(), SpellTarget.SearchResult.of(targets), SpellCast.Action.RELEASE, 1.0F);
                for (Entity entity1 : TargetHelper.targetsFromArea(
                        entity, entity.getPos(), SpellRegistry.from(entity.getWorld()).get(Identifier.of(MOD_ID, "circle_of_healing")).range, SpellRegistry.from(entity.getWorld()).get(Identifier.of(MOD_ID, "circle_of_healing")).target.area,
                        target -> EntityRelations.actionAllowed(SpellTarget.FocusMode.AREA, SpellTarget.Intent.HELPFUL, entity, target))) {
                    if (entity1 instanceof LivingEntity living) {
                        living.setAbsorptionAmount((float) Math.min(living.getMaxAbsorption(), living.getAbsorptionAmount() + 0.25 * SpellPower.getSpellPower(SpellSchools.HEALING, entity).randomValue()));
                    }
                }
            }
        }

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 20, 0));

        return super.applyUpdateEffect(entity, amplifier);
    }
}
