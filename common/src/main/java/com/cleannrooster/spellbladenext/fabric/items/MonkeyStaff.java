package com.cleannrooster.spellbladenext.fabric.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.ActionResult;
import net.spell_engine.api.item.weapon.Weapon;

public class MonkeyStaff extends AxeItem {
    public MonkeyStaff(float f, float g, Settings properties) {
        super(Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.STRIPPED_MANGROVE_WOOD)), properties);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext useOnContext) {
        return ActionResult.PASS;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.getItem().equals(Items.STRIPPED_MANGROVE_WOOD);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity livingEntity, LivingEntity attacker) {
        return true;
    }
}
