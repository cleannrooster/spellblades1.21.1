package com.cleannrooster.spellblades;

import com.cleannrooster.spellblades.Spells.SpellCustomDelivery;
import com.cleannrooster.spellblades.config.*;
import com.cleannrooster.spellblades.effect.*;
import com.cleannrooster.spellblades.entity.CycloneEntity;
import com.cleannrooster.spellblades.items.Items;
import com.cleannrooster.spellblades.items.MonkeyStaff;
import com.cleannrooster.spellblades.items.armor.Armors;
import com.cleannrooster.spellblades.items.loot.Default;
import com.extraspellattributes.ReabsorptionInit;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
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
import net.spell_engine.api.config.AttributeModifier;
import net.spell_engine.api.config.ConfigFile;
import net.spell_engine.api.effect.Synchronized;
import net.spell_engine.api.item.SpellBooks;
import net.spell_engine.api.render.CustomModels;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.spellbinding.SpellBindingScreen;
import net.spell_power.api.*;
import net.tinyconfig.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.cleannrooster.spellblades.CustomAttributes.EPHEMERAL;
import static net.minecraft.registry.Registries.ENTITY_TYPE;
import static net.spell_engine.internals.SpellHelper.launchPoint;

public class SpellbladesAndSuch implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("spellbladenext");
	public static ItemGroup SPELLBLADES;
	public static EntityType<CycloneEntity> CYCLONEENTITY;


	public static String MOD_ID = "spellbladenext";

	public static ServerConfig config;
	public static final ClampedEntityAttribute PURPOSE = new ClampedEntityAttribute("attribute.name.spellbladenext.purpose", 100,100,9999);


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
	public static  RegistryEntry.Reference<StatusEffect> FERVOR;

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




	public static ConfigManager<ConfigFile.Equipment> equipmentConfig = new ConfigManager<>
			("equipment", Default.itemConfig)
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();
	public static RegistryEntry.Reference<StatusEffect> PHOENIXCURSE;
	public static  RegistryEntry.Reference<StatusEffect> DEATHCHILL;
	public static  RegistryEntry.Reference<StatusEffect> FEATHER;
	public static  RegistryEntry.Reference<StatusEffect> FEATHERHEAL;

	public static  RegistryEntry.Reference<StatusEffect> SYMBOL_OF_HOPE;

	static{

}

	@Override
	public void onInitialize() {
		PHOENIXCURSE = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"phoenixcurse"),new PhoenixCurse(StatusEffectCategory.HARMFUL, 0xff4bdd));
		SPELLSTRIKE =  Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"spellstrike"),new Spellstrike(StatusEffectCategory.BENEFICIAL, 0xff4bdd).addAttributeModifier(SpellPowerMechanics.HASTE.attributeEntry,Identifier.of(MOD_ID,"haste"),0.5F,EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
		SYMBOL_OF_HOPE =  Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"symbol_of_hope"),new CustomEffect(StatusEffectCategory.BENEFICIAL, 0xff4bdd)
				.addAttributeModifier(ReabsorptionInit.RECOUP,Identifier.of(MOD_ID,"symbol_of_hope"),0.2F,EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE)
				.addAttributeModifier(SpellSchools.HEALING.attributeEntry, Identifier.of(MOD_ID,"symbol_of_hope_two"),1F,EntityAttributeModifier.Operation.ADD_VALUE));

		DEATHCHILL = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"deathchill"),new Deathchill(StatusEffectCategory.HARMFUL, 0xfc4edd)
				.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,Identifier.of(MOD_ID,"deathchill"),-0.2F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
		CHALLENGED = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"challenged"),new Challenged(StatusEffectCategory.HARMFUL, 0xff4bad));
		SUNDERED = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"sundered"),new CustomEffect(StatusEffectCategory.HARMFUL, 0xff4bcd)
				.addAttributeModifier(EntityAttributes.GENERIC_ARMOR,Identifier.of(MOD_ID,"overpower1"),-1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
				.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS,Identifier.of(MOD_ID,"overpower1"),-1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
				.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,Identifier.of(MOD_ID,"overpower1"),-1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
				.addAttributeModifier(EntityAttributes.GENERIC_JUMP_STRENGTH,Identifier.of(MOD_ID,"overpower1"),-1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
		;
		CustomModels.registerModelIds(List.of(
				Identifier.of(MOD_ID, "projectile/descry")
		));
		CustomModels.registerModelIds(List.of(
				Identifier.of(MOD_ID, "projectile/orb")
		));
		Synchronized.configure(SYMBOL_OF_HOPE.value(),true);
		Synchronized.configure(DEATHCHILL.value(),true);

		if(SpellSchools.LIGHTNING.attributeEntry != null) {
			SpellSchools.LIGHTNING.attributeEntry.value().setTracked(true);
		}
		UNLEASH =      Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"unleash"),new CustomEffect(StatusEffectCategory.BENEFICIAL, 0xff4add));
		FERVOR = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"fervor"),new Fervor(StatusEffectCategory.BENEFICIAL, 0xffff00).addAttributeModifier(ReabsorptionInit.CONVERTTOHEAL,Identifier.of(MOD_ID,"fervor"),0.1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE));
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
		RESONATING = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"resonating"),new CustomEffect(StatusEffectCategory.NEUTRAL, 0xffff00).
				addAttributeModifier(SpellPowerMechanics.HASTE.attributeEntry,Identifier.of(MOD_ID,"resonating"),4F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE));
		;

		Synchronized.configure(FEATHER.value(),true);
		Synchronized.configure(FEATHERHEAL.value(),true);
		Synchronized.configure(COLLAPSE.value(),true);

		MONKEYSTAFF = new MonkeyStaff(0,0,new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.WOOD,0,0F)).maxDamage(2048));
		RUNEBLAZE                = new Item(new Item.Settings().maxCount(64));
		RUNEFROST  = new Item(new Item.Settings().maxCount(64));
		RUNEGLEAM= new Item(new Item.Settings().maxCount(64));
		CYCLONEENTITY = Registry.register(
				ENTITY_TYPE,
				Identifier.of(MOD_ID, "cycloneentity"),
				FabricEntityTypeBuilder.<CycloneEntity>create(SpawnGroup.MISC, CycloneEntity::new)
						.dimensions(EntityDimensions.fixed(4F, 2F)) // dimensions in Minecraft units of the render
						.trackRangeBlocks(128)
						.trackedUpdateRate(1)
						.build()
		);
		SPELLBLADES = FabricItemGroup.builder()
				.icon(() -> new ItemStack(com.cleannrooster.spellblades.items.Items.arcane_blade.item()))
				.displayName(Text.translatable("itemGroup.spellbladenext.general"))
				.build();
		AutoConfig.register(ServerConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));

		config = AutoConfig.getConfigHolder(ServerConfigWrapper.class).getConfig().server;

/*
		SpellCustomHandlers.register();
*/
		SpellCustomDelivery.registerDeliveries();
		SpellSchools.LIGHTNING.addSource(SpellSchool.Trait.POWER, SpellSchool.Apply.ADD	,(queryArgs -> {
			double amount = 0;
			if(queryArgs.entity().getAttributeValue(EPHEMERAL) - 100 > 0) {
				amount +=  queryArgs.entity().getAbsorptionAmount() * 0.01 * (queryArgs.entity().getAttributeValue(EPHEMERAL) - 100);
			}
			return amount;
		}));

		Registry.register(Registries.ITEM,Identifier.of(MOD_ID,"runeblaze_ingot"),RUNEBLAZE);
		Registry.register(Registries.ITEM,Identifier.of(MOD_ID,"runefrost_ingot"),RUNEFROST);
		Registry.register(Registries.ITEM,Identifier.of(MOD_ID,"runegleam_ingot"),RUNEGLEAM);


		Registry.register(Registries.ITEM,Identifier.of(MOD_ID,"monkeystaff"),MONKEYSTAFF);

		Registry.register(Registries.ATTRIBUTE,Identifier.of(MOD_ID,"purpose"),PURPOSE);
/*
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "rifle"), RIFLE);
*/


		equipmentConfig.refresh();
		Items.register(equipmentConfig.value.weapons);
		Armors.register(equipmentConfig.value.armor_sets);
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
		Registry.register(Registries.ITEM_GROUP, KEY, SPELLBLADES);

		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"frost_battlemage"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"fire_battlemage"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"arcane_battlemage"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"runic_echoes"),KEY);

		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"phoenix"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"deathchill"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"vengeance"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"defiance"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"glory"),KEY);


		ItemGroupEvents.modifyEntriesEvent(KEY).register((content) -> {
			content.add(RUNEBLAZE);

			content.add(RUNEGLEAM);
			content.add(RUNEFROST);


			content.add(MONKEYSTAFF);


			/*content.add(RIFLE);*/
		});



		LOGGER.info("Hello Fabric world!");
	}
}