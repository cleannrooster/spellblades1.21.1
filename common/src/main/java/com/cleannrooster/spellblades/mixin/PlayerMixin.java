package com.cleannrooster.spellblades.mixin;

import com.cleannrooster.spellblades.items.interfaces.PlayerDamageInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

@Mixin(PlayerEntity.class)
public class PlayerMixin implements PlayerDamageInterface {
    public float damageMultipler = 1F;
    public Entity lastAttacked;
    public int lasthurt;
    public float damageAbsorbed;
    public boolean spellstriking;
    public int diebeamstacks;
    public int repeats = 0;
    public List<Identifier> spells = new ArrayList<>();

    public boolean overrideDamageMultiplier = false;
    public boolean shouldUnFortify = false;
    public int timesincefirsthurt = 0;
    public boolean offhand = false;
    public List<LivingEntity> list = new ArrayList<>();
    public void repeat(){
        repeats++;
    }
    public int getRepeats(){
        return repeats;
    }

    public int getLasthurt() {
        return lasthurt;
    }



    public void setSpellstriking(boolean spellstriking) {
        this.spellstriking = spellstriking;
    }
    public boolean getSpellstriking(){
        return this.spellstriking;
    }



    @Override
    public List<Identifier> getSpellstrikeSpells() {
        return spells;
    }

    @Override
    public void clearSpellstrikeSpells() {
        spells = new ArrayList<>();
    }
    public boolean second = false;
    @Override
    public void nextSwing() {
        this.second = !this.second;

    }
    @Override
    public boolean isSecondSwing() {
        return this.second;

    }
    @Override
    public void queueSpellStrikeSpell(Identifier spellstrikeSpell) {
        this.spells.add(spellstrikeSpell);
    }







    @Override
    public void resetRepeats() {
        repeats = 0;
    }








    @Override
    public List<LivingEntity> getList() {
        return list;
    }




        @Override
    public void override(boolean bool) {
        overrideDamageMultiplier = bool;
    }

    @Override
    public void setLastAttacked(Entity entity) {
        this.lastAttacked = entity;
    }

    @Override
    public Entity getLastAttacked() {
        return lastAttacked;
    }



}