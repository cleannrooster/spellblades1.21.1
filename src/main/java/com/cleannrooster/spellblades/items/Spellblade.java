package com.cleannrooster.spellblades.items;

import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.spell_engine.api.item.ConfigurableAttributes;
import net.spell_engine.api.item.weapon.SpellWeaponItem;
import net.spell_engine.api.item.weapon.StaffItem;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Spellblade extends SwordItem {
    SpellSchool school = SpellSchools.ARCANE;
    public Spellblade(ToolMaterial material, Settings settings, int damage, float speed, SpellSchool school) {
        super(material,settings);
        this.school = school;
    }
    private Multimap<EntityAttribute, EntityAttributeModifier> attributes;

    public Spellblade(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial,settings);
    }

    public void setAttributes(Multimap<EntityAttribute, EntityAttributeModifier> attributes) {
        this.attributes = attributes;
    }


    public SpellSchool getSchool() {

        AttributeModifiersComponent component = this.getComponents().getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS,AttributeModifiersComponent.DEFAULT);
        if(component.modifiers().stream().anyMatch((modifier) -> modifier.attribute().equals(SpellSchools.FIRE.attributeEntry))){
            return SpellSchools.FIRE;
        }
        if(component.modifiers().stream().anyMatch((modifier) -> modifier.attribute().equals(SpellSchools.FROST.attributeEntry))){
            return SpellSchools.FROST;
        }
        if(component.modifiers().stream().anyMatch((modifier) -> modifier.attribute().equals(SpellSchools.ARCANE.attributeEntry))){
            return SpellSchools.ARCANE;
        }

        return school;
    }

    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isOf(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            return state.isIn(BlockTags.SWORD_EFFICIENT) ? 1.5F : 1.0F;
        }
    }

    public boolean isSuitableFor(BlockState state) {
        return state.isOf(Blocks.COBWEB);
    }


    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }


}
