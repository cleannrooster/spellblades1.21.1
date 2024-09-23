package com.cleannrooster.spellblades.items;

import com.cleannrooster.spellblades.client.item.renderer.OrbRenderer;
import mod.azure.azurelib.common.api.common.animatable.GeoItem;
import mod.azure.azurelib.common.internal.client.RenderProvider;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.Animation;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_engine.api.item.weapon.SpellWeaponItem;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Orb extends SpellWeaponItem implements GeoItem {
    public Orb(ToolMaterial material, Settings settings) {
        super(material, settings);

    }
    SpellSchool school = SpellSchools.ARCANE;
    @Override
    public void createRenderer(Consumer<RenderProvider> consumer) {
        consumer.accept(new RenderProvider() {
            private OrbRenderer renderer;

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                if (renderer == null) return new OrbRenderer();
                return this.renderer;
            }
        });
    }
    private AnimatableInstanceCache factory = AzureLibUtil.createInstanceCache(this);
    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");

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



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<Orb>(this,"idle",
                0,animationState -> {
                animationState.getController().setAnimation(RawAnimation.begin().then(
                        "idle", Animation.LoopType.LOOP));
                return PlayState.CONTINUE;})
        );
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("Orbweaver's Grace").formatted(Formatting.LIGHT_PURPLE));
        tooltip.add(Text.translatable("Move normally while casting").formatted(Formatting.GRAY));

        super.appendTooltip(stack, context, tooltip, type);
    }



}
