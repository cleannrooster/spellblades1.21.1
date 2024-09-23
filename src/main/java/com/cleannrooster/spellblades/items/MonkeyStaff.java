package com.cleannrooster.spellblades.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MonkeyStaff extends AxeItem {
    public MonkeyStaff(float f, float g, Settings properties) {
        super(ToolMaterials.WOOD, properties);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext useOnContext) {
        return ActionResult.PASS;
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity livingEntity, LivingEntity attacker) {
        return true;
    }
}
