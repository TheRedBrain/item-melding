package com.github.theredbrain.mergeditems.registry;

import com.github.theredbrain.mergeditems.MergedItems;
import com.github.theredbrain.mergeditems.component.type.ItemMergingUtilityComponent;
import com.github.theredbrain.mergeditems.component.type.MergedItemsComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemComponentRegistry {
	static {
		MergedItems.MERGED_ITEMS_COMPONENT_TYPE = Registry.register(
				Registries.DATA_COMPONENT_TYPE,
				MergedItems.identifier("merged_items"),
				ComponentType.<MergedItemsComponent>builder().codec(MergedItemsComponent.CODEC).packetCodec(MergedItemsComponent.PACKET_CODEC).build()
		);
		MergedItems.ITEM_MERGING_UTILITY_COMPONENT_TYPE = Registry.register(
				Registries.DATA_COMPONENT_TYPE,
				MergedItems.identifier("item_merging_utility"),
				ComponentType.<ItemMergingUtilityComponent>builder().codec(ItemMergingUtilityComponent.CODEC).packetCodec(ItemMergingUtilityComponent.PACKET_CODEC).build()
		);
	}

	public static void init() {
	}
}
