package com.github.theredbrain.mergeditems_test;

import com.github.theredbrain.mergeditems.MergedItems;
import com.github.theredbrain.mergeditems.block.ItemMergingBlock;
import com.github.theredbrain.mergeditems.component.type.MergedItemsComponent;
import com.github.theredbrain.mergeditems.registry.ItemComponentRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MergedItemsTest implements ModInitializer {
	public static final String MOD_ID = "mergeditems_test";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier MAIN_HAND_PLAYER_ENTITY_INTERACTION_RANGE_MODIFIER_ID = MergedItems.identifier("main_hand_player_entity_interaction_range");
	@Override
	public void onInitialize() {
		LOGGER.info("This test was scripted!");
	}

	public static final Block ITEM_MERGING_BLOCK = registerBlock("item_merging_block", new ItemMergingBlock(1, "gui.item_merging.title", List.of(MergedItems.identifier("mergable_items_1"), MergedItems.identifier("mergable_items_2"), MergedItems.identifier("mergable_items_3")), Block.Settings.create().mapColor(MapColor.OAK_TAN)), ItemGroups.FUNCTIONAL);

	private static Block registerBlock(String name, Block block, RegistryKey<ItemGroup> itemGroup) {
		Registry.register(Registries.ITEM, MergedItems.identifier(name), new BlockItem(block, new Item.Settings()));
		ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(block));
		return Registry.register(Registries.BLOCK, MergedItems.identifier(name), block);
	}

	private static Item registerItem(String name, Item item, @Nullable RegistryKey<ItemGroup> itemGroup) {

		if (itemGroup != null) {
			ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> {
				content.add(item);
			});
		}
		return Registry.register(Registries.ITEM, MergedItemsTest.identifier(name), item);
	}
	public static Item[] registerItemSet(String id, String meldableItemTag, AttributeModifiersComponent[] attributeModifiersComponents, RegistryKey<ItemGroup> itemGroup) {
		if (attributeModifiersComponents.length != 4) {
			return new Item[]{};
		}
		return new Item[]{
				registerItem(id + "_common", new Item(new Item.Settings()
								.maxCount(1)
								.rarity(Rarity.COMMON)
								.component(ItemComponentRegistry.MERGED_ITEMS_COMPONENT_TYPE, MergedItemsComponent.DEFAULT)
								.attributeModifiers(attributeModifiersComponents[0])
						),
						itemGroup
				),
				registerItem(id + "_uncommon", new Item(new Item.Settings()
								.maxCount(1)
								.rarity(Rarity.UNCOMMON)
								.component(ItemComponentRegistry.MERGED_ITEMS_COMPONENT_TYPE, MergedItemsComponent.DEFAULT)
								.attributeModifiers(attributeModifiersComponents[1])
						),
						itemGroup
				),
				registerItem(id + "_rare", new Item(new Item.Settings()
								.maxCount(1)
								.rarity(Rarity.RARE)
								.component(ItemComponentRegistry.MERGED_ITEMS_COMPONENT_TYPE, MergedItemsComponent.DEFAULT)
								.attributeModifiers(attributeModifiersComponents[2])
						),
						itemGroup
				),
				registerItem(id + "_epic", new Item(new Item.Settings()
								.maxCount(1)
								.rarity(Rarity.EPIC)
								.component(ItemComponentRegistry.MERGED_ITEMS_COMPONENT_TYPE, MergedItemsComponent.DEFAULT)
								.attributeModifiers(attributeModifiersComponents[3])
						),
						itemGroup
				)
		};
	}
	//region curved dagger
	public static final Item[] CURVED_DAGGERS = registerItemSet(
			"curved_dagger",
			"minecrawl:meldable_into_curved_daggers",
			new AttributeModifiersComponent[]{
					AttributeModifiersComponent.builder()
							.add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
									new EntityAttributeModifier(
											Item.BASE_ATTACK_DAMAGE_MODIFIER_ID,
											3.0,
											EntityAttributeModifier.Operation.ADD_VALUE
									), AttributeModifierSlot.MAINHAND)
							.add(EntityAttributes.GENERIC_ATTACK_SPEED,
									new EntityAttributeModifier(
											Item.BASE_ATTACK_SPEED_MODIFIER_ID,
											-2.0F,
											EntityAttributeModifier.Operation.ADD_VALUE
									), AttributeModifierSlot.MAINHAND)
							.add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
									new EntityAttributeModifier(
											MAIN_HAND_PLAYER_ENTITY_INTERACTION_RANGE_MODIFIER_ID,
											-0.5F,
											EntityAttributeModifier.Operation.ADD_VALUE
									), AttributeModifierSlot.MAINHAND)
							.build(),
					AttributeModifiersComponent.builder()
							.add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
									new EntityAttributeModifier(
											Item.BASE_ATTACK_DAMAGE_MODIFIER_ID,
											6.0,
											EntityAttributeModifier.Operation.ADD_VALUE
									), AttributeModifierSlot.MAINHAND)
							.add(EntityAttributes.GENERIC_ATTACK_SPEED,
									new EntityAttributeModifier(
											Item.BASE_ATTACK_SPEED_MODIFIER_ID,
											-2.0F,
											EntityAttributeModifier.Operation.ADD_VALUE
									), AttributeModifierSlot.MAINHAND)
							.add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
									new EntityAttributeModifier(
											MAIN_HAND_PLAYER_ENTITY_INTERACTION_RANGE_MODIFIER_ID,
											-0.5F,
											EntityAttributeModifier.Operation.ADD_VALUE
									), AttributeModifierSlot.MAINHAND)
							.build(),
					AttributeModifiersComponent.builder()
							.add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
									new EntityAttributeModifier(
											Item.BASE_ATTACK_DAMAGE_MODIFIER_ID,
											9.0,
											EntityAttributeModifier.Operation.ADD_VALUE
									), AttributeModifierSlot.MAINHAND)
							.add(EntityAttributes.GENERIC_ATTACK_SPEED,
									new EntityAttributeModifier(
											Item.BASE_ATTACK_SPEED_MODIFIER_ID,
											-2.0F,
											EntityAttributeModifier.Operation.ADD_VALUE
									), AttributeModifierSlot.MAINHAND)
							.add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
									new EntityAttributeModifier(
											MAIN_HAND_PLAYER_ENTITY_INTERACTION_RANGE_MODIFIER_ID,
											-0.5F,
											EntityAttributeModifier.Operation.ADD_VALUE
									), AttributeModifierSlot.MAINHAND)
							.build(),
					AttributeModifiersComponent.builder()
							.add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
									new EntityAttributeModifier(
											Item.BASE_ATTACK_DAMAGE_MODIFIER_ID,
											12.0,
											EntityAttributeModifier.Operation.ADD_VALUE
									), AttributeModifierSlot.MAINHAND)
							.add(EntityAttributes.GENERIC_ATTACK_SPEED,
									new EntityAttributeModifier(
											Item.BASE_ATTACK_SPEED_MODIFIER_ID,
											-2.0F,
											EntityAttributeModifier.Operation.ADD_VALUE
									), AttributeModifierSlot.MAINHAND)
							.add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
									new EntityAttributeModifier(
											MAIN_HAND_PLAYER_ENTITY_INTERACTION_RANGE_MODIFIER_ID,
											-0.5F,
											EntityAttributeModifier.Operation.ADD_VALUE
									), AttributeModifierSlot.MAINHAND)
							.build()
			},
			ItemGroups.COMBAT
	);
	//endregion curved dagger

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
