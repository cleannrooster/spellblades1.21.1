package com.cleannrooster.spellblades;

import com.cleannrooster.spellblades.Spells.SpellCustomDelivery;
import com.cleannrooster.spellblades.Spells.SpellCustomImpact;
import com.cleannrooster.spellblades.Spells.SpellbladeSpells;
import com.cleannrooster.spellblades.compat.CombatRollCompat;
import com.cleannrooster.spellblades.config.ServerConfig;
import com.cleannrooster.spellblades.config.ServerConfigWrapper;
import com.cleannrooster.spellblades.effect.*;
import com.cleannrooster.spellblades.entity.CycloneEntity;
import com.cleannrooster.spellblades.items.Items;
import com.cleannrooster.spellblades.items.MonkeyStaff;
import com.cleannrooster.spellblades.items.armor.Armors;
import com.cleannrooster.spellblades.items.loot.Default;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.spell_engine.api.config.ConfigFile;
import net.spell_engine.api.effect.Synchronized;
import net.spell_engine.api.item.SpellBooks;
import net.spell_engine.api.render.CustomModels;
import net.spell_engine.api.spell.Spell;
import net.spell_power.api.*;
import net.tiny_config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.cleannrooster.spellblades.CustomAttributes.EPHEMERAL;
import static net.spell_engine.internals.SpellHelper.launchPoint;

public class SpellbladesAndSuch  {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("spellbladenext");
	public static ItemGroup SPELLBLADES;
	public static EntityType<CycloneEntity> CYCLONEENTITY;


	public static String MOD_ID = "spellbladenext";

	public static ServerConfig config;


	public static RegistryKey<ItemGroup> KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(),Identifier.of(SpellbladesAndSuch.MOD_ID,"generic"));
	public static RegistryKey<ItemGroup> SPELLOILSKEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(),Identifier.of(SpellbladesAndSuch.MOD_ID,"oils"));
	public static RegistryKey<ItemGroup> THESISKEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(),Identifier.of(SpellbladesAndSuch.MOD_ID,"thesis"));

	public static Item RUNEBLAZE ;
	public static Item RUNEFROST ;
	public static Item RUNEGLEAM ;


	public static Item MONKEYSTAFF ;
/*
	public static Item RIFLE = new Rifle(new Item.Settings()().maxDamage(2000));
*/



	public static  RegistryEntry.Reference<StatusEffect> UNLEASH;

	public static  RegistryEntry.Reference<StatusEffect> SLAMMING;
	public static  RegistryEntry.Reference<StatusEffect> COLLAPSE;
	public static  RegistryEntry.Reference<StatusEffect> RESONATING;

	public static  RegistryEntry.Reference<StatusEffect> CHALLENGED;
	public static  RegistryEntry.Reference<StatusEffect> SPELLSTRIKE;

	public static  RegistryEntry.Reference<StatusEffect> SUNDERED;
	public static  RegistryEntry.Reference<StatusEffect> DEFIANCE;
	public static  RegistryEntry.Reference<StatusEffect> BOLSTER;
	public static  RegistryEntry.Reference<StatusEffect> INEXORABLE;
	public static  RegistryEntry.Reference<StatusEffect> BULWARK;

	private static PacketByteBuf configSerialized = PacketByteBufs.create();




	public static ConfigManager<ConfigFile.Equipment> equipmentConfig;
	public static RegistryEntry.Reference<StatusEffect> PHOENIXCURSE;
	public static  RegistryEntry.Reference<StatusEffect> DEATHCHILL;
	public static  RegistryEntry.Reference<StatusEffect> FEATHER;
	public static  RegistryEntry.Reference<StatusEffect> FEATHERHEAL;

	public static  RegistryEntry.Reference<StatusEffect> PHASEDASH;
	public static  RegistryEntry.Reference<StatusEffect> ARCTIC_ARMOR;
	public static  RegistryEntry.Reference<StatusEffect> CRASHING;

	static{

}

	public  static void onInitialize() {

		CustomModels.registerModelIds(List.of(
				Identifier.of(MOD_ID, "projectile/descry")
		));
		CustomModels.registerModelIds(List.of(
				Identifier.of(MOD_ID, "projectile/orb")
		));
		if(SpellSchools.LIGHTNING.attributeEntry != null) {
			SpellSchools.LIGHTNING.attributeEntry.value().setTracked(true);
		}
		if(FabricLoader.getInstance().isModLoaded("combat_roll")){
			CombatRollCompat.register();
		}
		AutoConfig.register(ServerConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
		config = AutoConfig.getConfigHolder(ServerConfigWrapper.class).getConfig().server;
		SpellCustomDelivery.registerDeliveries();
        SpellCustomImpact.registerImpacts();


		CustomModels.registerModelIds(List.of(
				Identifier.of(MOD_ID, "projectile/feather")
		));
		CustomModels.registerModelIds(List.of(
				Identifier.of(MOD_ID, "projectile/flamewaveprojectile")
		));
		CustomModels.registerModelIds(List.of(
				Identifier.of(MOD_ID, "projectile/amethyst")
		));
		CustomModels.registerModelIds(List.of(
				Identifier.of(MOD_ID, "projectile/gladius")
		));
		CustomModels.registerModelIds(List.of(
				Identifier.of(MOD_ID, "projectile/shield")
		));
		CustomModels.registerModelIds(List.of(
				Identifier.of(MOD_ID, "projectile/spear")
		));
		LOGGER.info("Hello Fabric world!");
	}
    public static void registerAttributes(){
        CustomAttributes.run();

        SpellSchools.LIGHTNING.addSource(SpellSchool.Trait.POWER, SpellSchool.Apply.ADD	,(queryArgs -> {
            double amount = 0;
            if(queryArgs.entity().getAttributes() != null && queryArgs.entity().getAttributeInstance(EPHEMERAL) != null &&  queryArgs.entity().getAttributeValue(EPHEMERAL) - 100 > 0) {
                amount +=  queryArgs.entity().getAbsorptionAmount() * 0.01 * (queryArgs.entity().getAttributeValue(EPHEMERAL) - 100);
            }
            return amount;
        }));
    }
	public static void registerEffects() {


		PHOENIXCURSE = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"phoenixcurse"),new PhoenixCurse(StatusEffectCategory.HARMFUL, 0xff4bdd));
		SPELLSTRIKE =  Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"spellstrike"),new Spellstrike(StatusEffectCategory.BENEFICIAL, 0xff4bdd).addAttributeModifier(SpellPowerMechanics.HASTE.attributeEntry,Identifier.of(MOD_ID,"haste"),0.2F,EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));


		DEATHCHILL = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"deathchill"),new Deathchill(StatusEffectCategory.HARMFUL, 0xfc4edd)
				.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,Identifier.of(MOD_ID,"deathchill"),-0.2F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
		CHALLENGED = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"challenged"),new Challenged(StatusEffectCategory.HARMFUL, 0xff4bad));
		SUNDERED = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"sundered"),new CustomEffect(StatusEffectCategory.HARMFUL, 0xff4bcd)
				.addAttributeModifier(EntityAttributes.GENERIC_ARMOR,Identifier.of(MOD_ID,"overpower1"),-1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
				.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS,Identifier.of(MOD_ID,"overpower1"),-1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
				.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,Identifier.of(MOD_ID,"overpower1"),-1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
				.addAttributeModifier(EntityAttributes.GENERIC_JUMP_STRENGTH,Identifier.of(MOD_ID,"overpower1"),-1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
		;
		CRASHING = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"crashing"),new CustomEffect(StatusEffectCategory.HARMFUL, 0xff4bcd).addAttributeModifier(
				EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER,Identifier.of(MOD_ID,"falldamage"),1.0F,EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
		));
		PHASEDASH = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"phase_dash"),new PhaseDash(StatusEffectCategory.BENEFICIAL, 0xffff00)
				.addAttributeModifier(EntityAttributes.GENERIC_GRAVITY,Identifier.of(MOD_ID,"phase_dash"),-1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

		UNLEASH =      Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"unleash"),new CustomEffect(StatusEffectCategory.BENEFICIAL, 0xff4add));
		DEFIANCE = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"defiance"),new Defiance(StatusEffectCategory.BENEFICIAL, 0xffff00));
		SLAMMING= Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"slamming"),new Slamming(StatusEffectCategory.BENEFICIAL, 0xff4cdd));
		BOLSTER = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"bolster"),new CustomEffect(StatusEffectCategory.BENEFICIAL, 0xffff00)
				.addAttributeModifier(EntityAttributes.GENERIC_ARMOR,Identifier.of(MOD_ID,"bolster"),1F, EntityAttributeModifier.Operation.ADD_VALUE));
		INEXORABLE = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"inexorable"),new Inexorable(StatusEffectCategory.BENEFICIAL, 0xffff00));
		FEATHER = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"feather"),new Feather(StatusEffectCategory.BENEFICIAL, 0xffff00).addAttributeModifier(
				SpellSchools.FIRE.getAttributeEntry(),Identifier.of(MOD_ID,"featherfire"),0.05F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
		).addAttributeModifier(
				SpellPowerMechanics.HASTE.attributeEntry,Identifier.of(MOD_ID,"featherhaste"),0.05F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
		));
		FEATHERHEAL = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"featherheal"),new Feather(StatusEffectCategory.BENEFICIAL, 0xffff00).addAttributeModifier(
				SpellSchools.HEALING.attributeEntry, Identifier.of(MOD_ID,"featherheal"),0.5F, EntityAttributeModifier.Operation.ADD_VALUE
		));
		BULWARK = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"bulwark"),new Bulwark(StatusEffectCategory.BENEFICIAL, 0xffff00).addAttributeModifier(EntityAttributes.GENERIC_SCALE,Identifier.of(MOD_ID,"bulwark"),0.25F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
		COLLAPSE = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"collapse"),new Collapse(StatusEffectCategory.HARMFUL, 0xffff00));
		RESONATING = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"resonating"),new Resonating(StatusEffectCategory.BENEFICIAL, 0xffff00).
				addAttributeModifier(SpellPowerMechanics.HASTE.attributeEntry,Identifier.of(MOD_ID,"resonating"),-0.5F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).
				addAttributeModifier(SpellSchools.ARCANE.attributeEntry, Identifier.of(MOD_ID,"resonating_damage"),2F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
		ARCTIC_ARMOR = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"arctic_armor"),new ArcticArmor(StatusEffectCategory.NEUTRAL, 0xffff00));
		Synchronized.configure(FEATHER.value(),true);
		Synchronized.configure(FEATHERHEAL.value(),true);
		Synchronized.configure(COLLAPSE.value(),true);
		Synchronized.configure(DEATHCHILL.value(),true);
		SpellbladeSpells.registerHandlers();

	}
	public static void registerItems(){

		SPELLBLADES = FabricItemGroup.builder()
				.icon(() -> new ItemStack(Items.arcane_blade.item()))
				.displayName(Text.translatable("itemGroup.spellbladenext.general"))
				.build();
		Registry.register(Registries.ITEM_GROUP, KEY, SPELLBLADES);

		equipmentConfig  = new ConfigManager<>
				("equipment", Default.itemConfig)
				.builder()
				.setDirectory(MOD_ID)
				.sanitize(true)
				.build();
		equipmentConfig.refresh();

		Items.register(equipmentConfig.value.weapons);
		Armors.register(equipmentConfig.value.armor_sets);
		MONKEYSTAFF = new MonkeyStaff(0,0,new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.WOOD,0,0F)).maxDamage(2048));
		RUNEBLAZE                = new Item(new Item.Settings().maxCount(64));
		RUNEFROST  = new Item(new Item.Settings().maxCount(64));
		RUNEGLEAM= new Item(new Item.Settings().maxCount(64));

		Registry.register(Registries.ITEM,Identifier.of(MOD_ID,"runeblaze_ingot"),RUNEBLAZE);
		Registry.register(Registries.ITEM,Identifier.of(MOD_ID,"runefrost_ingot"),RUNEFROST);
		Registry.register(Registries.ITEM,Identifier.of(MOD_ID,"runegleam_ingot"),RUNEGLEAM);
		Registry.register(Registries.ITEM,Identifier.of(MOD_ID,"monkeystaff"),MONKEYSTAFF);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"frost_battlemage"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"fire_battlemage"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"arcane_battlemage"),KEY);
        SpellBooks.createAndRegister(Identifier.of(MOD_ID,"lightning_battlemage"),KEY);
        SpellBooks.createAndRegister(Identifier.of(MOD_ID,"runic_echoes"),KEY);

		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"phoenix"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"deathchill"),KEY);

		ItemGroupEvents.modifyEntriesEvent(KEY).register((content) -> {
			content.add(RUNEBLAZE);

			content.add(RUNEGLEAM);
			content.add(RUNEFROST);


			content.add(MONKEYSTAFF);


			/*content.add(RIFLE);*/
		});
	}
}