package com.cleannrooster.spellblades.items.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;

import java.util.List;

public interface PlayerDamageInterface {
    void setDamageMultiplier(float f);
    void repeat();
    int getRepeats();
    List<Identifier> getSpellstrikeSpells();
    void queueSpellStrikeSpell(Identifier spell);
    void clearSpellstrikeSpells();
    void setSpellstriking(boolean spellstriking);
    boolean getSpellstriking();
    void resetRepeats();
    int getLasthurt();
    float getDamageAbsorbed();
    void resetDamageAbsorbed();
    void absorbDamage(float i);
    int getDiebeamStacks();
    void addDiebeamStack(int i);
    void resetDiebeamStack();
    void setLasthurt(int lasthurt);
    void override(boolean bool);
    void setLastAttacked(Entity entity);
    Entity getLastAttacked();

    void shouldUnfortify(boolean bool);
    void listAdd(LivingEntity entity);
    void listRefresh();
    List<LivingEntity> getList();
    boolean listContains(LivingEntity entity);

}
