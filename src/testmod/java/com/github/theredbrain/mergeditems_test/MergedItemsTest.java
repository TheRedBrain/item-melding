package com.github.theredbrain.mergeditems_test;

import com.github.theredbrain.mergeditems.MergedItems;
import com.github.theredbrain.mergeditems.component.type.MergedItemsComponent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergedItemsTest implements ModInitializer {
	public static final String MOD_ID = "mergeditems_test";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier BASE_MAX_HEALTH_MODIFIER_ID = identifier("base_max_health");

	@Override
	public void onInitialize() {
		LOGGER.info("Testing Merged Items!");
	}

	private static Item registerItem(String name, Item item, @Nullable RegistryKey<ItemGroup> itemGroup) {

		if (itemGroup != null) {
			ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> {
				content.add(item);
			});
		}
		return Registry.register(Registries.ITEM, MergedItemsTest.identifier(name), item);
	}

	public static Item TEST_ITEM = registerItem("test_item", new Item(new Item.Settings()
					.maxCount(1)
					.component(MergedItems.MERGED_ITEMS_COMPONENT_TYPE, MergedItemsComponent.DEFAULT)
					.attributeModifiers(
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
									.add(EntityAttributes.GENERIC_MAX_HEALTH,
											new EntityAttributeModifier(
													BASE_MAX_HEALTH_MODIFIER_ID,
													2.0F,
													EntityAttributeModifier.Operation.ADD_VALUE
											), AttributeModifierSlot.MAINHAND)
									.build())
			),
			ItemGroups.OPERATOR
	);

	public static Item TEST_ITEM_2 = registerItem("test_item_2", new Item(new Item.Settings()
					.maxCount(1)
					.component(MergedItems.MERGED_ITEMS_COMPONENT_TYPE, MergedItemsComponent.DEFAULT)
					.attributeModifiers(
							AttributeModifiersComponent.builder()
									.add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
											new EntityAttributeModifier(
													Item.BASE_ATTACK_DAMAGE_MODIFIER_ID,
													5.0,
													EntityAttributeModifier.Operation.ADD_VALUE
											), AttributeModifierSlot.MAINHAND)
									.add(EntityAttributes.GENERIC_ATTACK_SPEED,
											new EntityAttributeModifier(
													Item.BASE_ATTACK_SPEED_MODIFIER_ID,
													-1.0F,
													EntityAttributeModifier.Operation.ADD_VALUE
											), AttributeModifierSlot.MAINHAND)
									.add(EntityAttributes.GENERIC_MAX_HEALTH,
											new EntityAttributeModifier(
													BASE_MAX_HEALTH_MODIFIER_ID,
													3.0F,
													EntityAttributeModifier.Operation.ADD_VALUE
											), AttributeModifierSlot.MAINHAND)
									.build())
			),
			ItemGroups.OPERATOR
	);

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
