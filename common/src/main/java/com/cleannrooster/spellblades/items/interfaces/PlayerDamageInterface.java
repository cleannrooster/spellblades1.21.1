package com.cleannrooster.spellblades.items.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public interface PlayerDamageInterface {
    int getRepeats();
    List<Identifier> getSpellstrikeSpells();
    void queueSpellStrikeSpell(Identifier spell);
    void clearSpellstrikeSpells();
    void nextSwing();
    boolean isSecondSwing();
    void setSpellstriking(boolean spellstriking);
    boolean getSpellstriking();
    void resetRepeats();

    void override(boolean bool);
    void setLastAttacked(Entity entity);
    Entity getLastAttacked();

    List<LivingEntity> getList();

}
