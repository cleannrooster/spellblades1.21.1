package com.cleannrooster.spellblades.items.armor;

import com.cleannrooster.spellblades.client.armor.RuneArmorRenderer;
import com.cleannrooster.spellblades.client.armor.TemplarArmorRenderer;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import mod.azure.azurelib.common.api.common.animatable.GeoItem;
import mod.azure.azurelib.common.internal.client.RenderProvider;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.spell_engine.api.item.armor.Armor;
import net.spell_power.api.SpellSchool;

import java.util.EnumMap;
import java.util.UUID;
import java.util.function.Consumer;

import static com.extraspellattributes.ReabsorptionInit.WARDING;

public class RunicArmor extends Armor.CustomItem implements GeoItem {
    public SpellSchool school;
    public RunicArmor(RegistryEntry<ArmorMaterial> material, Type slot, Settings settings, SpellSchool school) {
        super(material, slot, settings);
        this.school = school;
    }

    // MARK: GeoItem

    public SpellSchool getMagicSchool() {
        return school;
    }
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);


    @Override
    public void createRenderer(Consumer<RenderProvider> consumer) {
        consumer.accept(new RenderProvider() {
            private RuneArmorRenderer renderer;

            @Override
            public BipedEntityModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, BipedEntityModel<LivingEntity> original) {
                if (this.renderer == null) {
                    this.renderer = new RuneArmorRenderer();
                }
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    }
