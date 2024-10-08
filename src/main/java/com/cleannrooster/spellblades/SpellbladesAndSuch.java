package com.cleannrooster.spellblades;

import com.cleannrooster.spellblades.Spells.Spells;
import com.cleannrooster.spellblades.config.*;
import com.cleannrooster.spellblades.effect.*;
import com.cleannrooster.spellblades.entity.CycloneEntity;
import com.cleannrooster.spellblades.items.Items;
import com.cleannrooster.spellblades.items.MonkeyStaff;
import com.cleannrooster.spellblades.items.armor.Armors;
import com.cleannrooster.spellblades.items.loot.Default;
import com.extraspellattributes.ReabsorptionInit;
import com.extraspellattributes.api.SpellStatusEffect;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.spell_engine.SpellEngineMod;
import net.spell_engine.api.item.ItemConfig;
import net.spell_engine.api.item.trinket.SpellBookItem;
import net.spell_engine.api.item.trinket.SpellBooks;
import net.spell_engine.api.loot.LootConfig;
import net.spell_engine.api.loot.LootHelper;
import net.spell_engine.api.render.CustomModels;
import net.spell_engine.api.spell.*;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.WorldScheduler;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.internals.casting.SpellCasterEntity;
import net.spell_engine.network.Packets;
import net.spell_engine.network.ServerNetwork;
import net.spell_engine.particle.ParticleHelper;
import net.spell_engine.particle.Particles;
import net.spell_engine.utils.AnimationHelper;
import net.spell_engine.utils.SoundHelper;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.SpellPowerMod;
import net.spell_power.api.*;
import net.tinyconfig.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.Reference;
import java.util.*;
import java.util.function.Supplier;

import static java.lang.Math.*;
import static net.minecraft.registry.Registries.ENTITY_TYPE;
import static net.spell_engine.api.item.trinket.SpellBooks.itemIdFor;
import static net.spell_engine.internals.SpellHelper.imposeCooldown;
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
	public static RegistryEntry<EntityAttribute> EPHEMERAL;

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
	public static  RegistryEntry.Reference<StatusEffect> CHALLENGED;
	public static  RegistryEntry.Reference<StatusEffect> SPELLSTRIKE;

	public static  RegistryEntry.Reference<StatusEffect> SUNDERED;

	private static PacketByteBuf configSerialized = PacketByteBufs.create();




	public static ConfigManager<ItemConfig> itemConfig ;
	public static RegistryEntry.Reference<StatusEffect> PHOENIXCURSE;
	public static  RegistryEntry.Reference<StatusEffect> DEATHCHILL;
static{

}

	@Override
	public void onInitialize() {
		PHOENIXCURSE = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"phoenixcurse"),new PhoenixCurse(StatusEffectCategory.HARMFUL, 0xff4bdd,SpellRegistry.getSpell(Identifier.of(SpellbladesAndSuch.MOD_ID,"combustion"))));
		SPELLSTRIKE =  Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"spellstrike"),new Spellstrike(StatusEffectCategory.BENEFICIAL, 0xff4bdd).addAttributeModifier(SpellPowerMechanics.HASTE.attributeEntry,Identifier.of(MOD_ID,"haste"),0.5F,EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

		DEATHCHILL = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"deathchill"),new Deathchill(StatusEffectCategory.HARMFUL, 0xff4edd,SpellRegistry.getSpell(Identifier.of(SpellbladesAndSuch.MOD_ID,"deathchill"))));
		CHALLENGED = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"challenged"),new Challenged(StatusEffectCategory.HARMFUL, 0xff4bad,SpellRegistry.getSpell(Identifier.of(SpellbladesAndSuch.MOD_ID,"challenge"))));
		SUNDERED = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"sundered"),new CustomEffect(StatusEffectCategory.HARMFUL, 0xff4bcd)
				.addAttributeModifier(EntityAttributes.GENERIC_ARMOR,Identifier.of(MOD_ID,"overpower1"),-1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
				.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS,Identifier.of(MOD_ID,"overpower1"),-1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
				.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,Identifier.of(MOD_ID,"overpower1"),-1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
				.addAttributeModifier(EntityAttributes.GENERIC_JUMP_STRENGTH,Identifier.of(MOD_ID,"overpower1"),-1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
		;
		if(SpellSchools.LIGHTNING.attributeEntry != null) {
			SpellSchools.LIGHTNING.attributeEntry.value().setTracked(true);
		}
		UNLEASH =      Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"unleash"),new CustomEffect(StatusEffectCategory.BENEFICIAL, 0xff4add));
		FERVOR = Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"fervor"),new Fervor(StatusEffectCategory.BENEFICIAL, 0xffff00).addAttributeModifier(ReabsorptionInit.CONVERTTOHEAL,Identifier.of(MOD_ID,"fervor"),0.1F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE));

		SLAMMING= Registry.registerReference(Registries.STATUS_EFFECT,Identifier.of(MOD_ID,"slamming"),new Slamming(StatusEffectCategory.BENEFICIAL, 0xff4cdd));

		itemConfig= new ConfigManager<ItemConfig>
			("items_v2", Default.itemConfig)
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();
		MONKEYSTAFF = new MonkeyStaff(0,0,new Item.Settings());
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
		((ClampedEntityAttribute)(EPHEMERAL.value())).setTracked(true);

		Spells.register();
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


		itemConfig.refresh();
		Items.register(itemConfig.value.weapons);
		Armors.register(itemConfig.value.armor_sets);

		CustomModels.registerModelIds(List.of(
				Identifier.of(MOD_ID, "projectile/flamewaveprojectile")
		));
		CustomModels.registerModelIds(List.of(
				Identifier.of(MOD_ID, "projectile/amethyst")
		));
		CustomModels.registerModelIds(List.of(
				Identifier.of(MOD_ID, "projectile/gladius")
		));

		Registry.register(Registries.ITEM_GROUP, KEY, SPELLBLADES);

		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"frost_battlemage"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"fire_battlemage"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"arcane_battlemage"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"runic_echoes"),KEY);

		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"phoenix"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"deathchill"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"vengeance"),KEY);
		SpellBooks.createAndRegister(Identifier.of(MOD_ID,"lightning_battlemage"),KEY);

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